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

    public void connect(String ip, int port ,String playerName ) {
        new Thread(() -> {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000); 
                
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("SET_NAME:" + playerName);

                System.out.println("[Client] Successfully connected to the room!");

                javax.swing.SwingUtilities.invokeLater(() -> {
            
                });

                listenForMessages();

            } catch (Exception e) {
                // ถ้าหาห้องไม่เจอใน 2 วินาที !
                System.err.println("[Client] Connection failed. Room not found.");
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    // โชว์แจ้งเตือนว่าหาห้องไม่เจอ
                    javax.swing.JOptionPane.showMessageDialog(mainFrame, "Room not found or Server is offline! (IP: " + ip + ")", "Connection Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
                    mainFrame.showCoopMenu(); 
                });
            }
        }).start();
    }

    private void listenForMessages() {
        try {
            String message;
            // ลูปนี้จะหลุดทันทีเมื่อ Host ปิด Server (message จะกลายเป็น null หรือเกิด Exception)
            while ((message = in.readLine()) != null) {
                final String finalMsg = message;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    processCommand(finalMsg);
                });
            }
            handleServerDisconnect();
            
        } catch (Exception e) {
            // ถ้าเกิด Error
            System.out.println("[Client] Disconnected from the server.");
            handleServerDisconnect();
        }
    }

    // --- ฟังก์ชันจัดการเวลาโดนเตะ หรือ Host ปิดห้อง ---
    private void handleServerDisconnect() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // แจ้งเตือนผู้เล่นว่าห้องโดนปิดแล้ว
            javax.swing.JOptionPane.showMessageDialog(
                mainFrame, 
                "The Host has closed the room or connection was lost.", 
                "Disconnected", 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            
            disconnect();
            // เตะกลับไปหน้าเมนู Co-op
            mainFrame.showCoopMenu(); 
        });
    }

    
    // ==========================================
    // ศูนย์กลางกระจายคำสั่งจาก Server ไปสู่หน้าจอต่างๆ
    // ==========================================
    private void processCommand(String cmd) {
        System.out.println("[UI needs to update]: " + cmd);
        
        System.out.println("[UI needs to update]: " + cmd);
        // ดักจับ UPDATE_LOBBY ที่ส่งชื่อมา
        if (cmd.startsWith("UPDATE_LOBBY:")) {
            // ตัดคำว่า "UPDATE_LOBBY:" ออก จะเหลือรายชื่อ คั่นด้วยลูกน้ำ
            String nameData = cmd.substring(13); 
            String[] playerNames = nameData.split(","); // แยกรายชื่อเป็น Array
            
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
    }

    public void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }

    // ฟังก์ชันตัดการเชื่อมต่อ
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