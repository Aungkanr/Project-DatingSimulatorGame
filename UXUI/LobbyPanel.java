package UXUI;

import javax.swing.*;
import java.awt.*;
import Utility.StdAuto;

public class LobbyPanel extends JPanel {
    private MainFrame parent;
    private StdAuto stdScreen;
    private JLabel lblStatus;
    private JLabel[] playerLabels;
    private JButton btnStartMatch;

    private int currentPlayers = 1; // เริ่มต้นเราเข้ามา 1 คน
    private final int MAX_PLAYERS = 3;

    public LobbyPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        this.stdScreen = new StdAuto();
        setLayout(null);
        setBackground(new Color(40, 45, 60)); // สีพื้นหลังมืดๆ เท่ๆ

        initComponents();
    }

    private void initComponents() {
        // 1. หัวข้อห้อง
        JLabel title = new JLabel("CO-OP LOBBY", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 45));
        title.setForeground(new Color(255, 215, 0)); // สีทอง
        title.setBounds(0, 80, stdScreen.width, 60);
        add(title);

        // 2. ป้ายบอกสถานะ
        lblStatus = new JLabel("Waiting for players... (1/3)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(0, 150, stdScreen.width, 40);
        add(lblStatus);

        // 3. กรอบแสดงผู้เล่น 3 ช่อง
        int boxW = 600, boxH = 70, gap = 20;
        int startX = (stdScreen.width - boxW) / 2;
        int startY = 250;

        playerLabels = new JLabel[MAX_PLAYERS];
        for (int i = 0; i < MAX_PLAYERS; i++) {
            playerLabels[i] = new JLabel(" Player " + (i + 1) + " : Waiting...", SwingConstants.CENTER);
            playerLabels[i].setFont(new Font("Tahoma", Font.BOLD, 22));
            playerLabels[i].setOpaque(true);
            playerLabels[i].setBackground(new Color(60, 65, 80)); // สีเทา (ว่าง)
            playerLabels[i].setForeground(Color.GRAY);
            playerLabels[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            playerLabels[i].setBounds(startX, startY + (i * (boxH + gap)), boxW, boxH);
            add(playerLabels[i]);
        }

        // สมมติว่าตัวเองคือ Player 1 (ออนไลน์แล้ว)
        setPlayerConnected(0, "Player 1 (You)");

        // 4. ปุ่มเริ่มเกม (ล็อคไว้ก่อน รอคนครบ)
        btnStartMatch = new JButton("START MATCH");
        btnStartMatch.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStartMatch.setBackground(new Color(100, 100, 100)); // สีเทา
        btnStartMatch.setForeground(Color.WHITE);
        btnStartMatch.setEnabled(false); // ปิดไม่ให้กด
        btnStartMatch.setBounds(stdScreen.width / 2 + 20, 580, 200, 60);
        btnStartMatch.addActionListener(e -> {
            parent.showGame(); // เข้าเกมหลัก
        });
        add(btnStartMatch);

        // 5. ปุ่มกดยกเลิกกลับเมนู
        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(stdScreen.width / 2 - 220, 580, 200, 60);
        btnCancel.addActionListener(e -> parent.showMenu());
        add(btnCancel);
    }

    // ฟังก์ชันสำหรับเปลี่ยนสีช่องเมื่อมีคนเข้าห้องมา
    public void setPlayerConnected(int index, String name) {
        if (index >= 0 && index < MAX_PLAYERS) {
            playerLabels[index].setText(" " + name + " : CONNECTED! ");
            playerLabels[index].setBackground(new Color(50, 205, 50)); // สีเขียว
            playerLabels[index].setForeground(Color.WHITE);
        }
    }

    // (เดี๋ยวเราใช้ตอนต่อ Network) เช็คว่าคนครบ 2-3 คน ให้เปิดปุ่ม Start
    public void updatePlayerCount(int count) {
        this.currentPlayers = count;
        lblStatus.setText("Waiting for players... (" + count + "/" + MAX_PLAYERS + ")");
        if (count >= 2) {
            btnStartMatch.setEnabled(true);
            btnStartMatch.setBackground(new Color(255, 140, 0)); // เปลี่ยนเป็นสีส้ม
        }
    }
}