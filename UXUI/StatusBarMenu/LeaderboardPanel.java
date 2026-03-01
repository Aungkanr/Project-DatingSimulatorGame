package UXUI.StatusBarMenu;

import java.awt.*;
import javax.swing.*;
import UXUI.Hovereffect;
import UXUI.MainFrame;

public class LeaderboardPanel extends JPanel {
    private MainFrame mainFrame;
    private String leaderboardData = ""; // เก็บข้อมูลรอดึงมาวาด

    public LeaderboardPanel(MainFrame mainFrame, int screenWidth, int screenHeight) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); // พื้นหลังโปร่งแสง
        setVisible(false); // ซ่อนไว้ก่อน

        // ปุ่มปิด
        JButton btnClose = new JButton("CLOSE");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 18));
        int btnW = 120, btnH = 40;
        int x = (screenWidth - btnW) / 2;
        int y = (screenHeight / 2) + 220; 
        
        Hovereffect.HoverEffectRounded(btnClose, x, y, btnW, btnH, new Color(200, 50, 50));
        btnClose.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            this.setVisible(false);
        });
        add(btnClose);

        // ดักเมาส์ไม่ให้คลิกทะลุ
        addMouseListener(new java.awt.event.MouseAdapter() {});
    }

    // ฟังก์ชันรับข้อมูลจาก Server มาอัปเดตหน้าจอ
    public void updateData(String data) {
        this.leaderboardData = data;
        repaint(); // สั่งวาดหน้าจอใหม่
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
        String title = "🏆 ROMANCE LEADERBOARD 🏆";
        FontMetrics fm = g2.getFontMetrics();
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
                g2.drawString(info[0], startX + 40, textY); // ชื่อ
                g2.drawString(info[1] + " pts", startX + 250, textY); // คะแนน Lazel
                g2.drawString(info[2] + " pts", startX + 350, textY); // คะแนน Galadriel
                g2.drawString(info[3] + " pts", startX + 480, textY); // คะแนน Arwen
                textY += 40; // เลื่อนบรรทัดลง
            }
        }
    }
}