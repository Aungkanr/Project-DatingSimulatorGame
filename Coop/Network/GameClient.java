package Coop.Network;

import java.io.*;
import java.net.*;
import UXUI.MainFrame;

public class GameClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private MainFrame mainFrame; 

    public GameClient(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    // รับพารามิเตอร์ playerName เพื่อส่งชื่อตอน Connect
    public void connect(String ip, int port, String playerName) {
        new Thread(() -> {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000); 
                
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                // พอต่อติดปุ๊บ ส่งชื่อตัวเองให้ Server ปั๊บ
                out.println("SET_NAME:" + playerName);
                
                System.out.println("[Client] Successfully connected to the room!");
                listenForMessages();

            } catch (Exception e) {
                System.err.println("[Client] Connection failed. Room not found.");
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(mainFrame, "Room not found or Server is offline! (IP: " + ip + ")", "Connection Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
                    mainFrame.showCoopMenu(); 
                });
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
            handleServerDisconnect();
            
        } catch (Exception e) {
            System.out.println("[Client] Disconnected from the server.");
            handleServerDisconnect();
        }
    }

    private void handleServerDisconnect() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(
                mainFrame, 
                "The Host has closed the room or connection was lost.", 
                "Disconnected", 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            disconnect();
            mainFrame.showCoopMenu(); 
        });
    }

    // ==========================================
    // ศูนย์กลางกระจายคำสั่งจาก Server ไปสู่หน้าจอต่างๆ
    // ==========================================
    private void processCommand(String cmd) {
        System.out.println("[UI needs to update]: " + cmd);
        
        // [อัปเดตใหม่] ดักจับ UPDATE_LOBBY ที่ส่งรายชื่อมา
        if (cmd.startsWith("UPDATE_LOBBY:")) {
            String nameData = cmd.substring(13); // ตัดคำว่า UPDATE_LOBBY: ออก
            String[] playerNames = nameData.split(","); // แยกชื่อด้วยลูกน้ำ
            
            if (mainFrame.getLobbyPanel() != null) {
                mainFrame.getLobbyPanel().updateLobbyNamesUI(playerNames); 
            }
        } 
        else if (cmd.startsWith("ADD_MONEY:")) {
            String[] parts = cmd.split(":"); 
            int amount = Integer.parseInt(parts[1]);
            System.out.println("Shared wallet received: " + amount + " coins!");
        }
        else if (cmd.equals("START_GAME_NOW")) {
            mainFrame.showGame(); 
        }
        else if (cmd.startsWith("LEADERBOARD:")) {
            String data = cmd.substring(12);
            if (mainFrame.getLeaderboardPanel() != null) {
                mainFrame.getLeaderboardPanel().updateData(data); // ส่งข้อมูลไปวาดลงตาราง
            }
        }
    }

    public void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }

    public void disconnect() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("[Client] Connection closed cleanly.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}