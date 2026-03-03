package UXUI;

import javax.swing.*;
import java.awt.*;
import Utility.StdAuto;

public class LobbyPanel extends JPanel {
    private MainFrame parent;
    private StdAuto stdScreen;
    private JLabel lblStatus;
    private JLabel[] playerLabels;
    private JLabel lblIPAddress;
    private JButton btnStartMatch;

    private int currentPlayers = 1;
    private int maxPlayersInRoom = 5; // ค่าเริ่มต้น
    private final int ABSOLUTE_MAX = 5; // รองรับสูงสุด 5 ช่อง

    private boolean isHostMode = false;

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
        title.setBounds(0, 50, stdScreen.width, 60);
        add(title);

        lblStatus = new JLabel("Connecting to server...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(0, 110, stdScreen.width, 40);
        add(lblStatus);

        // ป้ายแสดง IP ของห้อง
        lblIPAddress = new JLabel("", SwingConstants.CENTER);
        lblIPAddress.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblIPAddress.setForeground(new Color(173, 216, 230)); 
        lblIPAddress.setBounds(0, 150, stdScreen.width, 30);
        add(lblIPAddress);

        // สร้างกรอบรายชื่อผู้เล่นรอไว้ 5 ช่อง
        int boxW = 600, boxH = 50, gap = 15;
        int startX = (stdScreen.width - boxW) / 2;
        int startY = 200;

        playerLabels = new JLabel[ABSOLUTE_MAX];
        for (int i = 0; i < ABSOLUTE_MAX; i++) {
            playerLabels[i] = new JLabel(" Player " + (i + 1) + " : Waiting...", SwingConstants.CENTER);
            playerLabels[i].setFont(new Font("Tahoma", Font.BOLD, 22));
            playerLabels[i].setOpaque(true);
            playerLabels[i].setBackground(new Color(60, 65, 80)); 
            playerLabels[i].setForeground(Color.GRAY);
            playerLabels[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            playerLabels[i].setBounds(startX, startY + (i * (boxH + gap)), boxW, boxH);
            playerLabels[i].setVisible(false); // ซ่อนไว้ก่อนทั้งหมด
            add(playerLabels[i]);
        }

        btnStartMatch = new JButton("START MATCH");
        btnStartMatch.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStartMatch.setBackground(new Color(100, 100, 100)); 
        btnStartMatch.setForeground(Color.WHITE);
        btnStartMatch.setEnabled(false); 
        btnStartMatch.setBounds(stdScreen.width / 2 + 20, 550, 200, 60);
        btnStartMatch.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Harvest Dawn.wav");
            if (isHostMode) {
                parent.getGameClient().sendMessage("CMD:START_GAME");
            }
        });
        add(btnStartMatch);

        JButton btnCancel = new JButton("DISCONNECT");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(stdScreen.width / 2 - 220, 550, 200, 60);
        btnCancel.addActionListener(e -> disconnectAndReturn());
        add(btnCancel);
    }

    // ==========================================
    // ฟังก์ชันใหม่: เรียกก่อนโชว์หน้า Lobby เพื่อเซ็ตค่าทุกอย่างให้ตรง
    // ==========================================
    public void setupRoom(int maxPlayers, boolean isHost, String ipToConnect) {
        this.maxPlayersInRoom = maxPlayers;
        this.currentPlayers = 1;
        this.isHostMode = isHost;

        // โชว์กล่องผู้เล่นเท่ากับจำนวนที่ลากจาก Slider
        for (int i = 0; i < ABSOLUTE_MAX; i++) {
            if (i < maxPlayersInRoom) {
                playerLabels[i].setVisible(true);
                playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
                playerLabels[i].setBackground(new Color(60, 65, 80));
                playerLabels[i].setForeground(Color.GRAY);
            } else {
                playerLabels[i].setVisible(false);
            }
        }

        // ---------------------------------------------------------
        //  ให้เด้งขึ้นมาก่อนทำการเชื่อมต่อ
        String myName = JOptionPane.showInputDialog(this, "Enter your Name:", "Player Setup", JOptionPane.QUESTION_MESSAGE);
        
        // ถ้าผู้เล่นกด Cancel หรือไม่ยอมพิมพ์ชื่อ ให้ตั้งชื่ออัตโนมัติให้ 
        if (myName == null || myName.trim().isEmpty()) {
            myName = isHost ? "HostPlayer" : "Guest_" + (int)(Math.random() * 1000);
        }
        // บันทึกชื่อลง Player ของเครื่องเรา
        parent.getPlayer().setPlayerName(myName); 
        // ---------------------------------------------------------

        if (isHost) {
            try {
                String radminIP = getRadminIP(); 
                if (radminIP != null) {
                    lblIPAddress.setText("Radmin IP for friends: " + radminIP);
                    lblIPAddress.setForeground(new Color(50, 255, 100)); 
                } else {
                    String localIP = java.net.InetAddress.getLocalHost().getHostAddress();
                    lblIPAddress.setText("Local LAN IP: " + localIP);
                    lblIPAddress.setForeground(new Color(173, 216, 230)); 
                }
            } catch (Exception e) {
                lblIPAddress.setText("Room IP: Unknown");
            }
            
            // สั่ง GameClient ให้เชื่อมต่อเข้าเครื่องตัวเอง (Host) + ส่งชื่อไปด้วย
            parent.getGameClient().connect("localhost", 9999, myName);
        } else {
            lblIPAddress.setText(""); 
            
            // สั่ง GameClient ให้เชื่อมต่อไปยังเครื่องเพื่อน (Client) + ส่งชื่อไปด้วย
            parent.getGameClient().connect(ipToConnect, 9999, myName);
        }
    }

    // ==========================================
    // ฟังก์ชันตัดการเชื่อมต่อและกลับเมนู
    // ==========================================
    private void disconnectAndReturn() {
        // สั่งให้ GameClient ตัดการเชื่อมต่ออย่างปลอดภัย
        parent.getGameClient().disconnect(); 

        if (isHostMode) {
            Coop.Network.GameServer.stopServer(); // ถ้าเป็น Host ให้ปิด Server ด้วย
        }

        parent.showCoopMenu(); 
        lblStatus.setText("Waiting for players..."); 
        for(int i = 0; i < ABSOLUTE_MAX; i++) {
            playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
            playerLabels[i].setBackground(new Color(60, 65, 80));
            playerLabels[i].setVisible(false); 
        }
    }

    // ==========================================
    // ฟังก์ชันสำหรับเซ็ตหน้าจอให้เป็นสีเขียว
    // ==========================================
    public void setPlayerConnected(int index, String name) {
        if (index >= 0 && index < maxPlayersInRoom) {
            playerLabels[index].setText(" " + name + " : CONNECTED! ");
            playerLabels[index].setBackground(new Color(50, 205, 50)); 
            playerLabels[index].setForeground(Color.WHITE);
        }
    }

    // ==========================================
    // ฟังก์ชันอัปเดต UI แบบแสดงชื่อจริง
    // ==========================================
    public void updateLobbyNamesUI(String[] playerNames) {
        this.currentPlayers = playerNames.length;
        lblStatus.setText("Waiting for players... (" + currentPlayers + "/" + maxPlayersInRoom + ")");
        
        // 1. รีเซ็ตสีทุกช่อง
        for (int i = 0; i < maxPlayersInRoom; i++) {
            playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
            playerLabels[i].setBackground(new Color(60, 65, 80));
            playerLabels[i].setForeground(Color.GRAY);
        }
        
        // 2. เปิดไฟสีเขียวและใส่ "ชื่อผู้เล่นจริงๆ"
        for (int i = 0; i < currentPlayers; i++) {
            if (i < maxPlayersInRoom) { 
                String role = (i == 0) ? "[HOST] " : "";
                String actualName = playerNames[i];
                
                playerLabels[i].setText(" " + role + actualName + " : CONNECTED! ");
                playerLabels[i].setBackground(new Color(50, 205, 50)); 
                playerLabels[i].setForeground(Color.WHITE);
            }
        }
        
        // 3. ตรวจสอบปุ่ม START MATCH
        if (currentPlayers >= 2) {
            if (isHostMode) {
                btnStartMatch.setEnabled(true);
                btnStartMatch.setBackground(new Color(255, 140, 0));
                btnStartMatch.setText("START MATCH");
            } else {
                btnStartMatch.setEnabled(false);
                btnStartMatch.setBackground(new Color(100, 100, 100));
                btnStartMatch.setText("WAITING FOR HOST...");
            }
        } else {
            btnStartMatch.setEnabled(false);
            btnStartMatch.setBackground(new Color(100, 100, 100)); 
            btnStartMatch.setText(isHostMode ? "START MATCH" : "WAITING FOR HOST...");
        }
    }

    // --- ฟังก์ชันลับสำหรับควานหา IP ของ Radmin VPN โดยเฉพาะ ---
    private String getRadminIP() {
        try {
            java.util.Enumeration<java.net.NetworkInterface> interfaces = java.net.NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                java.net.NetworkInterface networkInterface = interfaces.nextElement();
                java.util.Enumeration<java.net.InetAddress> addresses = networkInterface.getInetAddresses();
                
                while (addresses.hasMoreElements()) {
                    java.net.InetAddress address = addresses.nextElement();
                    
                    if (!address.isLoopbackAddress() && address instanceof java.net.Inet4Address) {
                        String ip = address.getHostAddress();
                        if (ip.startsWith("26.")) {
                            return ip; 
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    // ไว้ใช้เปลี่ยนข้อความโชว์ตอนเชื่อมต่อได้สำเร็จ
    public void setStatusText(String text) {
        lblStatus.setText(text);
    }
}