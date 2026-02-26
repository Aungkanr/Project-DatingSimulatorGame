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
    // ==========================================
    public void connectToServer(String ip) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, 9999);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Connected! Waiting for other players...");
                    setPlayerConnected(0, "Player 1 (You)");
                });

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server Broadcast: " + message);
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Connection Failed to IP: " + ip + "\nMake sure the Server is running!", "Error", JOptionPane.ERROR_MESSAGE);
                    parent.showMenu();
                });
            }
        }).start();
    }

    private void disconnectAndReturn() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.showMenu();
        lblStatus.setText("Waiting for players..."); 
    }

    public void setPlayerConnected(int index, String name) {
        if (index >= 0 && index < maxPlayersInRoom) {
            playerLabels[index].setText(" " + name + " : CONNECTED! ");
            playerLabels[index].setBackground(new Color(50, 205, 50)); 
            playerLabels[index].setForeground(Color.WHITE);
        }
    }
}