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

    public void connect(String ip, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                System.out.println("[Client] Successfully connected to the room!");

                // ถ้าต่อสำเร็จ สั่งให้หน้า Lobby โชว์ว่า Connected
                javax.swing.SwingUtilities.invokeLater(() -> {
                    // (ถ้าคุณสร้างเมธอด setStatusText ไว้ใน LobbyPanel)
                    // mainFrame.getLobbyPanel().setStatusText("Connected! Waiting for other players...");
                });

                listenForMessages();

            } catch (Exception e) {
                System.err.println("[Client] Connection failed. Please check the IP address.");
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(mainFrame, "Connection Failed to IP: " + ip, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    mainFrame.showCoopMenu(); // กลับไปหน้าเลือกโหมด
                });
            }
        }).start();
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String finalMsg = message;
                // โยนเข้า SwingUtilities เพื่อความปลอดภัยเวลาแก้อินเตอร์เฟส
                javax.swing.SwingUtilities.invokeLater(() -> {
                    processCommand(finalMsg);
                });
            }
        } catch (Exception e) {
            System.out.println("[Client] Disconnected from the server.");
        }
    }

    // ==========================================
    // ศูนย์กลางกระจายคำสั่งจาก Server ไปสู่หน้าจอต่างๆ
    // ==========================================
    private void processCommand(String cmd) {
        System.out.println("[UI needs to update]: " + cmd);
        
        System.out.println("📺 [UI needs to update]: " + cmd);
        
        if (cmd.startsWith("UPDATE_PLAYERS:")) {
            int count = Integer.parseInt(cmd.split(":")[1]);
            
            // [แก้ตรงนี้] สั่งอัปเดตหน้า LobbyPanel ทันที
            if (mainFrame.getLobbyPanel() != null) {
                mainFrame.getLobbyPanel().updatePlayerCountUI(count); 
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

    // [เพิ่มใหม่] ฟังก์ชันตัดการเชื่อมต่ออย่างถูกต้อง
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