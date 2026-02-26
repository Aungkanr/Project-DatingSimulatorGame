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
    private static ServerSocket serverSocket; // <--- เพิ่มตัวแปรระดับคลาสเพื่อให้สั่งปิดได้
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void startServerInBackground(int port) {
        if (isRunning) return; 
        
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                isRunning = true;
                System.out.println("🟢 [SERVER] Started in background on port: " + port);

                while (isRunning) { // <--- เช็คเงื่อนไขก่อนรับคน
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("✅ [SERVER] Player connected: " + clientSocket.getInetAddress());

                    ClientHandler handler = new ClientHandler(clientSocket);
                    clients.add(handler);
                    new Thread(handler).start();
                }
            } catch (Exception e) {
                // จะเข้า catch เมื่อ serverSocket ถูกสั่ง .close() จากด้านนอก
                System.out.println("🛑 [SERVER] Server has been shut down.");
                isRunning = false;
            }
        }).start();
    }

    // ==========================================
    // [เพิ่มใหม่] ฟังก์ชันสำหรับทำลาย Server ทิ้งเมื่อ Host ปิดห้อง
    // ==========================================
    public static void stopServer() {
        isRunning = false;
        try {
            // เตะทุกคนออกจากเซิร์ฟเวอร์
            for (ClientHandler client : clients) {
                if (client.socket != null) client.socket.close();
            }
            clients.clear();
            
            // ปิดช่องทางการรับคนเข้า
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("🧹 [SERVER] Cleared all threads and closed completely.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("⚠️ [SERVER] Player disconnected. Remaining: " + clients.size());
        if (isRunning) {
            broadcast("UPDATE_PLAYERS:" + clients.size()); 
        }
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
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                out.println("WELCOME");
                broadcast("UPDATE_PLAYERS:" + clients.size());

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("💬 [SERVER] Received: " + message);
                }
            } catch (Exception e) {
                // ผู้เล่นหลุด
            } finally {
                removeClient(this); 
            }
        }

        public void sendMessage(String msg) {
            if (out != null) out.println(msg);
        }
    }
}