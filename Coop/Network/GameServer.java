package Coop.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static boolean isRunning = false;
    private static List<ClientHandler> clients = new ArrayList<>();

    // ฟังก์ชันนี้จะถูกเรียกตอนที่ผู้เล่นกดปุ่ม "Host Game"
    public static void startServerInBackground(int port) {
        if (isRunning) return; // ป้องกันการเปิดเซิร์ฟเวอร์ซ้อนกัน
        
        // สร้าง Thread เพื่อให้เซิร์ฟเวอร์รันเบื้องหลัง โดยไม่ทำให้หน้าจอเกมค้าง
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                isRunning = true;
                System.out.println("🟢 [SERVER] Started in background on port: " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("✅ [SERVER] Player connected: " + clientSocket.getInetAddress());

                    ClientHandler handler = new ClientHandler(clientSocket);
                    clients.add(handler);
                    new Thread(handler).start();
                }
            } catch (Exception e) {
                System.out.println("❌ [SERVER] Error or Server closed.");
                isRunning = false;
            }
        }).start();
    }

    // --- คลาสย่อยสำหรับดูแลผู้เล่นแต่ละคน ---
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
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // ส่งข้อความทักทายกลับไปให้คนที่เพิ่งเข้ามา
                out.println("WELCOME");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("💬 [SERVER] Received: " + message);
                    // อนาคตเราจะกระจายข้อความตรงนี้
                }
            } catch (Exception e) {
                System.out.println("❌ [SERVER] Player disconnected.");
            }
        }
    }
}