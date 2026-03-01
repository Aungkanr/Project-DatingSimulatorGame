package UXUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Utility.*;

public class PauseMenuPanel extends JPanel {

    private MainFrame mainFrame;
    private StdAuto stdScreen = new StdAuto();

    // สีปุ่มต่างๆ
    private Color btnResumeColor = new Color(138, 43, 226); 
    private Color btnNormalColor = new Color(100, 60, 150); 
    private Color btnExitColor = new Color(200, 50, 50);    
    private Color btnLeaderboardColor = new Color(50, 150, 200); // สีฟ้าสว่างสำหรับตารางคะแนน

    public PauseMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setOpaque(false); // สำคัญมาก: ทำให้พื้นหลังโปร่งใส
        
        // [อัปเดต] เปลี่ยนเลข 2 เป็น 5 เพราะตอนนี้เรามี 5 ปุ่มแล้ว
        stdScreen.setBtnWHG(300, 60, 20, 5); 
        setBounds(0, 0, stdScreen.width, stdScreen.height);

        // ดักจับเมาส์ไม่ให้คลิกทะลุไปโดนปุ่มของเกมด้านหลัง
        addMouseListener(new MouseAdapter() {});

        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // วาดฟิล์มสีดำโปร่งใสทับหน้าจอ
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void initComponents() {
        int btnW = stdScreen.buttonWidth;
        int btnH = stdScreen.buttonHeight;
        int gap = stdScreen.gap;
        int centerX = stdScreen.width / 2;
        
        // คำนวณจุดกึ่งกลางหน้าจอให้ปุ่มเรียงสวยงาม
        int totalHeight = (btnH * 5) + (gap * 4);
        int startY = (stdScreen.height - totalHeight) / 2;

        // 1. ปุ่ม RESUME (เล่นต่อ)
        JButton btnResume = createRoundedButton("RESUME");
        btnResume.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnResume, centerX - (btnW / 2), startY, btnW, btnH, btnResumeColor);
        btnResume.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            this.setVisible(false);
        });
        add(btnResume);

        // 2. ปุ่ม OPTION (ตั้งค่า)
        JButton btnOption = createRoundedButton("OPTION");
        btnOption.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnOption, centerX - (btnW / 2), startY + (btnH + gap) * 1, btnW, btnH, btnNormalColor);
        btnOption.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            mainFrame.showOption();
        });
        add(btnOption);

        // 3. ปุ่ม LEADERBOARD (ดูตารางคะแนน) 
        JButton btnLeaderboard = createRoundedButton("VIEW LEADERBOARD");
        btnLeaderboard.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnLeaderboard, centerX - (btnW / 2), startY + (btnH + gap) * 2, btnW, btnH, btnLeaderboardColor);
        btnLeaderboard.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            
            // ดึงคะแนนสาวๆ ของตัวเอง
            int lScore = mainFrame.getPlayer().getLazel().getAffection();
            int gScore = mainFrame.getPlayer().getGaladriel().getAffection();
            int aScore = mainFrame.getPlayer().getArwen().getAffection();
            
            // ส่งคะแนนไปอัปเดตที่ Server
            if(mainFrame.getGameClient() != null) {
                mainFrame.getGameClient().sendMessage("SYNC_SCORE:" + lScore + "," + gScore + "," + aScore);
            }
            
            // ปิดหน้า Pause และเปิดหน้า Leaderboard
            this.setVisible(false);
            if (mainFrame.getLeaderboardPanel() != null) {
                mainFrame.getLeaderboardPanel().setVisible(true);
            }
        });
        add(btnLeaderboard);

        // 4. ปุ่ม EXIT TO MENU (กลับเมนูหลัก)
        JButton btnExitMenu = createRoundedButton("EXIT TO MENU");
        btnExitMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnExitMenu, centerX - (btnW / 2), startY + (btnH + gap) * 3, btnW, btnH, btnNormalColor);
        btnExitMenu.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            if (mainFrame.getGameClient() != null) mainFrame.getGameClient().disconnect(); 
            Coop.Network.GameServer.stopServer(); 
            this.setVisible(false);
            mainFrame.showMenu(); 
        });
        add(btnExitMenu);

        // 5. ปุ่ม EXIT DESKTOP (ปิดเกม)
        JButton btnExit = createRoundedButton("EXIT DESKTOP");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnExit, centerX - (btnW / 2), startY + (btnH + gap) * 4, btnW, btnH, btnExitColor);
        btnExit.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            if (mainFrame.getGameClient() != null) mainFrame.getGameClient().disconnect(); 
            Coop.Network.GameServer.stopServer(); 
            Utility.AssetManager.getInstance().clearCache();
            System.exit(0);
        });
        add(btnExit);
    }

    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setForeground(Color.WHITE);
        return btn;
    }
}