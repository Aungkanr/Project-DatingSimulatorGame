package Coop.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {
    private static boolean isRunning = false;
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void startServerInBackground(int port) {
        if (isRunning) return; 
        
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                isRunning = true;
                System.out.println("[SERVER] Started in background on port: " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[SERVER] Player connected: " + clientSocket.getInetAddress());

                    ClientHandler handler = new ClientHandler(clientSocket);
                    clients.add(handler);
                    new Thread(handler).start();
                    // (ลบ broadcast ตรงนี้ออก เพื่อย้ายไปไว้ด้านล่างแทน)
                }
            } catch (Exception e) {
                System.out.println("[SERVER] Server closed.");
                isRunning = false;
            }
        }).start();
    }

    // ฟังก์ชันตะโกนบอกทุกคน (Broadcast)
    public static void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    // ฟังก์ชันลบผู้เล่นออกเวลามีคนปิดเกม
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("[SERVER] Player disconnected. Remaining: " + clients.size());
        broadcast("UPDATE_PLAYERS:" + clients.size()); // อัปเดตให้คนที่เหลือรู้เมื่อมีคนหลุด
    }

    // --- คลาสย่อยดูแลแต่ละผู้เล่น ---
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // 1. เปิดช่องทางรับ-ส่งข้อความให้เสร็จเรียบร้อยก่อน
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                // 2. ทักทายคนที่เพิ่งเข้ามา
                out.println("WELCOME");

                // 3.[แก้ปัญหา] พอมั่นใจว่าช่องทางเชื่อมต่อเสร็จสมบูรณ์ ค่อยตะโกนบอกทุกคน!
                broadcast("UPDATE_PLAYERS:" + clients.size());

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[SERVER] Received: " + message);
                }
            } catch (Exception e) {
                // ถ้าเกิด Error ตรงนี้แปลว่าผู้เล่นหลุดหรือกดออกเกม
            } finally {
                removeClient(this); // เตะออกจากรายชื่อเมื่อออกเกม
            }
        }

        public void sendMessage(String msg) {
            if (out != null) out.println(msg);
        }
    }
}