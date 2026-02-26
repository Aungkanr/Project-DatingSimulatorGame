package Coop.Network;

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
            }
        }).start();
    }

    public void broadcast(String message) {
        for (PrintWriter writer : allPlayers) {
            writer.println(message);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) { e.printStackTrace(); }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("📥 [Server received message]: " + message);
                    broadcast(message); 
                }
            } catch (Exception e) {
                System.out.println("🔌 [Server] A player has disconnected.");
            }
        }
    }
}