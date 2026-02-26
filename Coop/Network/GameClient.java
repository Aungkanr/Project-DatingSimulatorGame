package Coop.Network;

import java.io.*;
import java.net.*;

public class GameClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                System.out.println("🌐 [Client] Successfully connected to the room!");

                listenForMessages();

            } catch (Exception e) {
                System.err.println("❌ [Client] Connection failed. Please check the IP address.");
            }
        }).start();
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String finalMsg = message;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    processCommand(finalMsg);
                });
            }
        } catch (Exception e) {
            System.out.println("🔌 [Client] Disconnected from the server.");
        }
    }

    private void processCommand(String cmd) {
        System.out.println("📺 [UI needs to update]: " + cmd);
        
        if (cmd.startsWith("ADD_MONEY:")) {
            String[] parts = cmd.split(":"); 
            int amount = Integer.parseInt(parts[1]);
            System.out.println("💰 Shared wallet received: " + amount + " coins!");
            // TODO: Update UI money text here
        }
    }

    public void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }
}