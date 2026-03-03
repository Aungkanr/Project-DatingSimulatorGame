package UXUI.StatusBarMenu;

import java.awt.*;
import javax.swing.*;
import UXUI.MainFrame;

public class LeaderboardPanel extends JPanel {
    private MainFrame mainFrame;
    private String leaderboardData = ""; 

    public LeaderboardPanel(MainFrame mainFrame, int screenWidth, int screenHeight) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); 
        setVisible(false); 

        // ---------------- สร้างปุ่ม Close ----------------
        int btnW = 120, btnH = 40;
        int x = (screenWidth - btnW) / 2;
        int y = (screenHeight / 2) + 210; // ปรับตำแหน่งให้อยู่ใต้กล่องพอดี

        JButton btnClose = new JButton("close") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // เปลี่ยนสีเมื่อเอาเมาส์ชี้ (Hover)
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 100, 100)); // สีแดงอ่อนลงเมื่อชี้
                } else {
                    g2.setColor(new Color(248, 86, 96)); // สีแดงตามรูป
                }
                
                // วาดพื้นหลังปุ่มโค้งมน (ทรงแคปซูล)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // ขอบสีขาว
                g2.setStroke(new BasicStroke(2)); // ความหนาของขอบ
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 40, 40);
                g2.dispose();
            }
        };

        // ตั้งค่าปุ่มพื้นฐาน
        btnClose.setBounds(x, y, btnW, btnH);
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnClose.setForeground(Color.WHITE); // ตัวหนังสือสีขาว
        btnClose.setContentAreaFilled(false); // ปิดพื้นหลังเดิมของ Swing
        btnClose.setFocusPainted(false); // ปิดกรอบตอนคลิก
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR)); // เปลี่ยนเคอร์เซอร์เป็นรูปมือ

        // ใส่ Action ให้ปุ่ม
        btnClose.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            this.setVisible(false);
        });

        add(btnClose);
        // ---------------------------------------------------

        // ดักเมาส์ไม่ให้คลิกทะลุ
        addMouseListener(new java.awt.event.MouseAdapter() {});
    }

    // ฟังก์ชันรับข้อมูลจาก Server มาอัปเดตหน้าจอ
    public void updateData(String data) {
        this.leaderboardData = data;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. พื้นหลังมืดๆ
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. กล่อง Leaderboard
        int boxW = 600, boxH = 400;
        int startX = (getWidth() - boxW) / 2;
        int startY = (getHeight() - boxH) / 2;

        g2.setColor(new Color(30, 30, 50));
        g2.fillRoundRect(startX, startY, boxW, boxH, 20, 20);
        g2.setColor(new Color(255, 215, 0)); // ขอบสีทอง
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(startX, startY, boxW, boxH, 20, 20);

        // 3. หัวข้อ
        g2.setFont(new Font("Tahoma", Font.BOLD, 30));
        String title = " ROMANCE LEADERBOARD ";
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(new Color(255, 215, 0)); // เปลี่ยนสีหัวข้อให้เป็นสีทองให้เข้ากับรูป
        g2.drawString(title, startX + (boxW - fm.stringWidth(title))/2, startY + 50);

        // 4. หัวตาราง
        g2.setFont(new Font("Tahoma", Font.BOLD, 16));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("PLAYER", startX + 40, startY + 100);
        g2.drawString("LAZEL", startX + 250, startY + 100);
        g2.drawString("GALADRIEL", startX + 350, startY + 100);
        g2.drawString("ARWEN", startX + 480, startY + 100);
        g2.drawLine(startX + 20, startY + 110, startX + boxW - 20, startY + 110);

        // 5. วาดรายชื่อผู้เล่นและคะแนน
        if (leaderboardData.isEmpty()) return;

        g2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        int textY = startY + 150;

        String[] players = leaderboardData.split("\\|");
        for (String p : players) {
            if (p.isEmpty()) continue;
            String[] info = p.split(",");
            if (info.length >= 4) {
                g2.drawString(info[0], startX + 40, textY); 
                g2.drawString(info[1] + " pts", startX + 250, textY); 
                g2.drawString(info[2] + " pts", startX + 350, textY); 
                g2.drawString(info[3] + " pts", startX + 480, textY); 
                textY += 40; 
            }
        }
    }
}