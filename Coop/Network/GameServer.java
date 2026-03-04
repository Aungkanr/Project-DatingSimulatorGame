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
    private static ServerSocket serverSocket; 
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void startServerInBackground(int port) {
        if (isRunning) return; 
        
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                isRunning = true;
                System.out.println("[SERVER] Started in background on port: " + port);

                while (isRunning) { 
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[SERVER] Player connected: " + clientSocket.getInetAddress());

                    ClientHandler handler = new ClientHandler(clientSocket);
                    clients.add(handler);
                    new Thread(handler).start();
                }
            } catch (Exception e) {
                System.out.println("[SERVER] Server has been shut down.");
                isRunning = false;
            }
        }).start();
    }

    public static void stopServer() {
        try {
            isRunning = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (ClientHandler client : clients) {
                client.socket.close();
            }
            clients.clear();
            System.out.println("[SERVER] Cleared all threads and closed completely.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }


    public static void broadcastLobbyState() {
        StringBuilder sb = new StringBuilder("UPDATE_LOBBY:");
        for (int i = 0; i < clients.size(); i++) {
            sb.append(clients.get(i).playerName);
            if (i < clients.size() - 1) sb.append(","); // คั่นชื่อด้วยลูกน้ำ
        }
        broadcast(sb.toString()); 
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("[SERVER] Player disconnected. Remaining: " + clients.size());
        if (isRunning) {
            broadcastLobbyState(); // อัปเดตรายชื่อใหม่ตอนมีคนออก
        }
    }

    // --- คลาสย่อยดูแลแต่ละผู้เล่น ---
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public String playerName = "Unknown";
        public int scoreLazel = 0;       
        public int scoreGaladriel = 0;  
        public int scoreArwen = 0;       

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                out.println("WELCOME");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[SERVER] Received: " + message);
                    
                    if (message.equals("CMD:START_GAME")) {
                        broadcast("START_GAME_NOW");
                    }
                    // ดักจับคำสั่งตั้งชื่อ
                    else if (message.startsWith("SET_NAME:")) {
                        this.playerName = message.substring(9);
                        broadcastLobbyState(); // พอคนนี้ตั้งชื่อเสร็จ ให้บอกทุกคน!
                    } else if (message.startsWith("SYNC_SCORE:")) {
                        // แยกคะแนนออกมา: SYNC_SCORE:10,20,30
                        String[] scores = message.substring(11).split(",");
                        this.scoreLazel = Integer.parseInt(scores[0]);
                        this.scoreGaladriel = Integer.parseInt(scores[1]);
                        this.scoreArwen = Integer.parseInt(scores[2]);
                        
                        // สร้างประโยคสรุปคะแนนทุกคน แล้วกระจายกลับไป
                        StringBuilder sb = new StringBuilder("LEADERBOARD:");
                        for (int i = 0; i < clients.size(); i++) {
                            ClientHandler c = clients.get(i);
                            sb.append(c.playerName).append(",")
                              .append(c.scoreLazel).append(",")
                              .append(c.scoreGaladriel).append(",")
                              .append(c.scoreArwen);
                            if (i < clients.size() - 1) sb.append("|");
                        }
                        broadcast(sb.toString()); // ส่งให้ทุกคน!
                    }
                }
            } catch (Exception e) {
            } finally {
                removeClient(this); 
            }
        }

        public void sendMessage(String msg) {
            if (out != null) out.println(msg);
        }
    }
}