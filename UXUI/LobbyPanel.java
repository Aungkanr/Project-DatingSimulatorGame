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
    private int maxPlayersInRoom = 5; // ค่าเริ่มต้น
    private final int ABSOLUTE_MAX = 5; // รองรับสูงสุด 5 ช่อง

    private boolean isHostMode = false;

    // --- ตัวแปร Network ---
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

        // สร้างกรอบรายชื่อผู้เล่นรอไว้ 5 ช่อง (ปรับขนาดให้เล็กลงนิดนึงเพื่อไม่ให้ล้นจอเวลาเปิด 5 คน)
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
        btnStartMatch.addActionListener(e -> parent.showGame());
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

        // จัดการ IP และการเชื่อมต่อ
        if (isHost) {
            try {
                String myIP = java.net.InetAddress.getLocalHost().getHostAddress();
                lblIPAddress.setText("Room IP for friends to join: " + myIP);
            } catch (Exception e) {
                lblIPAddress.setText("Room IP: Unknown (Check network)");
            }
            connectToServer("localhost");
        } else {
            lblIPAddress.setText(""); 
            connectToServer(ipToConnect);
        }
    }

    // ==========================================
    // ระบบ NETWORK (เชื่อมต่อและรับข้อมูล)
    public void connectToServer(String ip) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, 9999);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Connected! Waiting for other players...");
                });

                String message;
                // ลูปฟังข้อความจาก Server แบบ Real-time
                while ((message = in.readLine()) != null) {
                    System.out.println("Server Broadcast: " + message);
                    
                    // [อัปเดต UI เมื่อมีคนเข้าหรือออก] ดักจับคำว่า UPDATE_PLAYERS:
                    if (message.startsWith("UPDATE_PLAYERS:")) {
                        // ดึงตัวเลขจำนวนคนปัจจุบันออกมา (เช่น ส่งมา UPDATE_PLAYERS:2 จะได้เลข 2)
                        int playerCount = Integer.parseInt(message.split(":")[1]);
                        
                        SwingUtilities.invokeLater(() -> {
                            currentPlayers = playerCount;
                            lblStatus.setText("Waiting for players... (" + currentPlayers + "/" + maxPlayersInRoom + ")");
                            
                            // 1. รีเซ็ตสีทุกช่องให้กลับเป็นสีเทาก่อน
                            for (int i = 0; i < maxPlayersInRoom; i++) {
                                playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
                                playerLabels[i].setBackground(new Color(60, 65, 80));
                                playerLabels[i].setForeground(Color.GRAY);
                            }
                            
                            // 2. เปิดไฟสีเขียวไล่ตามจำนวนคนที่อยู่ในห้องจริงๆ
                            for (int i = 0; i < currentPlayers; i++) {
                                if (i < maxPlayersInRoom) { // กัน Error เวลาคนเกินขีดจำกัดห้อง
                                    String playerName = (i == 0) ? "Host (Player 1)" : "Player " + (i + 1);
                                    setPlayerConnected(i, playerName);
                                }
                            }
                            
                            // 3. ตรวจสอบปุ่ม START MATCH (ให้กดได้ก็ต่อเมื่อมี 2 คนขึ้นไป)
                            if (currentPlayers >= 2) {
                                // ถ้ามี 2 คนขึ้นไป
                                if (isHostMode) {
                                    btnStartMatch.setEnabled(true);
                                    btnStartMatch.setBackground(new Color(255, 140, 0)); // สีส้ม
                                    btnStartMatch.setText("START MATCH");
                                } else {
                                    // ถ้าเป็น Client ปล่อยให้รอ
                                    btnStartMatch.setEnabled(false);
                                    btnStartMatch.setBackground(new Color(100, 100, 100)); // สีเทา
                                    btnStartMatch.setText("WAITING FOR HOST...");
                                }
                            } else {
                                // ถ้ายังไม่มีคนเข้า
                                btnStartMatch.setEnabled(false);
                                btnStartMatch.setBackground(new Color(100, 100, 100)); 
                                btnStartMatch.setText(isHostMode ? "START MATCH" : "WAITING FOR HOST...");
                            }
                        });
                    }
                }
            } catch (Exception ex) {
                // ถ้าหลุดเชื่อมต่อ ให้เด้งกลับไปหน้าเมนู
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Connection Failed or Disconnected from IP: " + ip, "Network Error", JOptionPane.ERROR_MESSAGE);
                    parent.showCoopMenu(); // เปลี่ยนให้เด้งกลับไปหน้าเมนู Co-op
                });
            }
        }).start();
    }

    private void disconnectAndReturn() {
        try {
            // ปิดช่องทางสตรีมให้หมดก่อนปิด Socket
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // [สำคัญมาก] ถ้าคนที่กดออกคือ HOST ต้องสั่งระเบิดเซิร์ฟเวอร์ทิ้งด้วย!
        if (isHostMode) {
            Coop.Network.GameServer.stopServer();
        }

        parent.showCoopMenu(); // กลับไปหน้าเลือก Host/Join
        
        // รีเซ็ต UI
        lblStatus.setText("Waiting for players..."); 
        for(int i=0; i<ABSOLUTE_MAX; i++) {
            playerLabels[i].setText(" Player " + (i + 1) + " : Waiting...");
            playerLabels[i].setBackground(new Color(60, 65, 80));
            playerLabels[i].setVisible(false); // ซ่อนช่องไว้เผื่อรอบหน้าสุ่มจำนวนใหม่
        }
    }

    public void setPlayerConnected(int index, String name) {
        if (index >= 0 && index < maxPlayersInRoom) {
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
                        // Radmin VPN มักจะจ่ายแจก IP ที่ขึ้นต้นด้วย "26." เสมอ (เช่น 26.155.x.x)
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