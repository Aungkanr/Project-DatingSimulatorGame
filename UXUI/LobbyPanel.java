package UXUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Utility.StdAuto;

public class LobbyPanel extends JPanel {
    private MainFrame parent;
    private StdAuto stdScreen;
    private JLabel lblStatus;
    private JLabel[] playerLabels;
    private JLabel lblIPAddress;
    private JButton btnStartMatch;
    
    private int currentPlayers = 1;
    private final int MAX_PLAYERS = 3;

    // --- ตัวแปร Network (เอามาจาก SimpleClient) ---
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public LobbyPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        this.stdScreen = new StdAuto();
        setLayout(null);
        setBackground(new Color(40, 45, 60)); 
        initComponents();
    }

    private void initComponents() {
        // หัวข้อห้อง
        JLabel title = new JLabel("CO-OP LOBBY", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 45));
        title.setForeground(new Color(255, 215, 0));
        title.setBounds(0, 80, stdScreen.width, 60);
        add(title);

        lblStatus = new JLabel("Connecting to server...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(0, 150, stdScreen.width, 40);
        add(lblStatus);

        // <--- ป้ายแสดง IP ของห้อง --->
        lblIPAddress = new JLabel("", SwingConstants.CENTER);
        lblIPAddress.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblIPAddress.setForeground(new Color(173, 216, 230)); // สีฟ้าอ่อนให้เด่นๆ
        lblIPAddress.setBounds(0, 195, stdScreen.width, 30);
        add(lblIPAddress);
        // กรอบรายชื่อผู้เล่น
        int boxW = 600, boxH = 70, gap = 20;
        int startX = (stdScreen.width - boxW) / 2;
        int startY = 250;

        playerLabels = new JLabel[MAX_PLAYERS];
        for (int i = 0; i < MAX_PLAYERS; i++) {
            playerLabels[i] = new JLabel(" Player " + (i + 1) + " : Waiting...", SwingConstants.CENTER);
            playerLabels[i].setFont(new Font("Tahoma", Font.BOLD, 22));
            playerLabels[i].setOpaque(true);
            playerLabels[i].setBackground(new Color(60, 65, 80)); 
            playerLabels[i].setForeground(Color.GRAY);
            playerLabels[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            playerLabels[i].setBounds(startX, startY + (i * (boxH + gap)), boxW, boxH);
            add(playerLabels[i]);
        }

        btnStartMatch = new JButton("START MATCH");
        btnStartMatch.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStartMatch.setBackground(new Color(100, 100, 100)); 
        btnStartMatch.setForeground(Color.WHITE);
        btnStartMatch.setEnabled(false); 
        btnStartMatch.setBounds(stdScreen.width / 2 + 20, 580, 200, 60);
        btnStartMatch.addActionListener(e -> parent.showGame());
        add(btnStartMatch);

        JButton btnCancel = new JButton("DISCONNECT");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(stdScreen.width / 2 - 220, 580, 200, 60);
        btnCancel.addActionListener(e -> disconnectAndReturn());
        add(btnCancel);
    }

    // ==========================================
    // ระบบ NETWORK (เชื่อมต่อและรับข้อมูล)
    // ==========================================
    
    public void connectToServer(String ip) {
        // เพื่อไม่ให้ UI ค้าง เราต้องจับการเชื่อมต่อแยกเป็น Thread (ทำงานเบื้องหลัง)
        new Thread(() -> {
            try {
                socket = new Socket(ip, 9999);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // ถ้าต่อติดแล้ว ให้อัปเดต UI กลับไปที่หน้าจอ (ต้องใช้ invokeLater เพื่อความปลอดภัยของ Swing)
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Connected! Waiting for other players...");
                    setPlayerConnected(0, "Player 1 (You)");
                });

                // ลูปเปิดหูฟัง Server เผื่อส่งสัญญาณบอกว่ามีคนเข้ามาเพิ่ม (Listening Loop)
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server Broadcast: " + message);
                    // (อนาคต เราจะดักจับคำสั่งเช่น "NEW_PLAYER:2" เพื่ออัปเดตหน้าจอ)
                }
            } catch (Exception ex) {
                // ถ้าเชื่อมต่อล้มเหลว (เช่น ใส่ IP ผิด หรือ Server ปิดอยู่)
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Connection Failed to IP: " + ip + "\nMake sure the Server is running!", "Error", JOptionPane.ERROR_MESSAGE);
                    parent.showMenu();
                });
            }
        }).start();
    }

    // ฟังก์ชันตัดการเชื่อมต่อเวลากดปุ่ม DISCONNECT
    private void disconnectAndReturn() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.showMenu();
        lblStatus.setText("Waiting for players..."); // Reset ข้อความ
        for(int i=0; i<MAX_PLAYERS; i++) {
            playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
            playerLabels[i].setBackground(new Color(60, 65, 80));
        }
    }

    public void setPlayerConnected(int index, String name) {
        if (index >= 0 && index < MAX_PLAYERS) {
            playerLabels[index].setText(" " + name + " : CONNECTED! ");
            playerLabels[index].setBackground(new Color(50, 205, 50)); 
            playerLabels[index].setForeground(Color.WHITE);
        }
    }

    // ==========================================
    // ฟังก์ชันตั้งค่าโชว์ IP ให้เฉพาะคนที่เป็น Host
    public void setHostMode(boolean isHost) {
        if (isHost) {
            try {
                // 1. ลองค้นหา IP ของ Radmin VPN ก่อน
                String radminIP = getRadminIP(); 
                
                if (radminIP != null) {
                    // ถ้าเจอ Radmin ให้โชว์ IP นี้ให้เพื่อน
                    lblIPAddress.setText("Radmin IP for friends: " + radminIP);
                    lblIPAddress.setForeground(new Color(50, 255, 100)); // สีเขียวสว่าง
                } else {
                    // 2. ถ้าไม่เปิด Radmin ไว้ ให้ดึง IP Wi-Fi/LAN ปกติ
                    String localIP = java.net.InetAddress.getLocalHost().getHostAddress();
                    lblIPAddress.setText("Local LAN IP: " + localIP);
                    lblIPAddress.setForeground(new Color(173, 216, 230)); // สีฟ้าปกติ
                }
            } catch (Exception e) {
                lblIPAddress.setText("Room IP: Unknown (Check network)");
            }
        } else {
            lblIPAddress.setText(""); 
        }
    }

    // --- ฟังก์ชันลับสำหรับควานหา IP ของ Radmin VPN โดยเฉพาะ ---
    private String getRadminIP() {
        try {
            // ดึงรายชื่อ Network ทั้งหมดในเครื่อง (Wi-Fi, LAN, Virtual Network)
            java.util.Enumeration<java.net.NetworkInterface> interfaces = java.net.NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                java.net.NetworkInterface networkInterface = interfaces.nextElement();
                java.util.Enumeration<java.net.InetAddress> addresses = networkInterface.getInetAddresses();
                
                while (addresses.hasMoreElements()) {
                    java.net.InetAddress address = addresses.nextElement();
                    
                    // หาเฉพาะ IPv4 และต้องไม่ใช่ 127.0.0.1
                    if (!address.isLoopbackAddress() && address instanceof java.net.Inet4Address) {
                        String ip = address.getHostAddress();
                        // 📌 Radmin VPN มักจะจ่ายแจก IP ที่ขึ้นต้นด้วย "26." เสมอ (เช่น 26.155.x.x)
                        if (ip.startsWith("26.")) {
                            return ip; // เจอแล้ว ส่งกลับไปเลย!
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // ถ้าไม่เจอ Radmin ให้ส่งค่าว่างกลับไป
    }
}