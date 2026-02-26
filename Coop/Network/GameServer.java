package Coop.Network;

<<<<<<< HEAD
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
=======
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class GameServer {
    private ServerSocket serverSocket;
    private ArrayList<PrintWriter> allPlayers = new ArrayList<>();

    public void startServer(int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("🏠 [Server] Hosting successful! Waiting for players on Port: " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept(); 
                    System.out.println("✅ [Server] A player has connected!");

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    allPlayers.add(out); 

                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch (Exception e) {
                System.out.println("❌ [Server] Server closed or an error occurred.");
>>>>>>> 14e428a5b0b0f2ba89cc9e245f4ae48901a0c586
            }
        }).start();
    }

<<<<<<< HEAD
    // --- คลาสย่อยสำหรับดูแลผู้เล่นแต่ละคน ---
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
=======
    public void broadcast(String message) {
        for (PrintWriter writer : allPlayers) {
            writer.println(message);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
>>>>>>> 14e428a5b0b0f2ba89cc9e245f4ae48901a0c586
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
<<<<<<< HEAD
=======
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) { e.printStackTrace(); }
>>>>>>> 14e428a5b0b0f2ba89cc9e245f4ae48901a0c586
        }

        @Override
        public void run() {
<<<<<<< HEAD
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
=======
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("📥 [Server received message]: " + message);
                    broadcast(message); 
                }
            } catch (Exception e) {
                System.out.println("🔌 [Server] A player has disconnected.");
>>>>>>> 14e428a5b0b0f2ba89cc9e245f4ae48901a0c586
            }
        }
    }
}