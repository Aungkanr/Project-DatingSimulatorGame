package UXUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Utility.*;

public class PauseMenuPanel extends JPanel {

    private MainFrame mainFrame;
    private StdAuto stdScreen = new StdAuto();


    // สีปุ่มต่างๆ
    private Color btnResumeColor = new Color(138, 43, 226); // สีม่วง
    private Color btnNormalColor = new Color(100, 60, 150); // สีม่วงเข้ม
    private Color btnExitColor = new Color(200, 50, 50);    // สีแดง

    public PauseMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setOpaque(false); // สำคัญมาก: ทำให้พื้นหลังโปร่งใส
        stdScreen.setBtnWHG(300, 60, 20, 2);
        setBounds(0, 0, stdScreen.width, stdScreen.height);

        // ดักจับเมาส์ไม่ให้คลิกทะลุไปโดนปุ่มของเกมด้านหลัง
        addMouseListener(new MouseAdapter() {});

        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // วาดฟิล์มสีดำโปร่งใสทับหน้าจอเกม (ความทึบ 180)
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void initComponents() {
        int centerX = stdScreen.width / 2;
        int centerY = stdScreen.height / 2;

        int btnW = 300;
        int btnH = 60;
        int gap = 20;

        // คำนวณจุดเริ่มต้นของปุ่มให้อยู่กึ่งกลางหน้าจอพอดี
        int startY = centerY - ((btnH * 4 + gap * 3) / 2);

        // หัวข้อเมนู
        JLabel lblTitle = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, startY - 80, stdScreen.width, 50);
        add(lblTitle);

        // 1. ปุ่ม RESUME
        JButton btnResume = createRoundedButton("RESUME");
        btnResume.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnResume, centerX - (btnW / 2), startY, btnW, btnH, btnResumeColor);
        btnResume.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            this.setVisible(false); // ซ่อนหน้านี้เพื่อกลับไปเล่นต่อ
        });
        add(btnResume);

        // 2. ปุ่ม SETTING ในหน้า Pause Menu
        JButton btnSetting = createRoundedButton("SETTING");
        btnSetting.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnSetting, centerX - (btnW / 2), startY + (btnH + gap), btnW, btnH, btnNormalColor);
        
        btnSetting.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            
            mainFrame.showOption(); // สลับไปหน้าจอตั้งค่า
        });
        add(btnSetting);

        // 3. ปุ่ม EXIT TO MENU
        JButton btnExitMenu = createRoundedButton("EXIT TO MENU");
        btnExitMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnExitMenu, centerX - (btnW / 2), startY + (btnH + gap) * 2, btnW, btnH, btnNormalColor);
        
        btnExitMenu.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            // --- สั่งเคลียร์ Network Threads ---
            if (mainFrame.getGameClient() != null) {
                mainFrame.getGameClient().disconnect(); 
            }
            Coop.Network.GameServer.stopServer(); 
            
            this.setVisible(false);
            mainFrame.showMenu(); // สั่งให้ MainFrame กลับไปหน้าเมนูหลัก
        });
        add(btnExitMenu);

        // 4. ปุ่ม EXIT (ปิดเกม)
        JButton btnExit = createRoundedButton("EXIT DESKTOP");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnExit, centerX - (btnW / 2), startY + (btnH + gap) * 3, btnW, btnH, btnExitColor);
        
        btnExit.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");

            // --- สั่งเคลียร์ Network Threads ก่อนปิดโปรแกรม ---
            if (mainFrame.getGameClient() != null) {
                mainFrame.getGameClient().disconnect(); // บอกลาเซิร์ฟเวอร์
            }
            Coop.Network.GameServer.stopServer(); // ระเบิดเซิร์ฟเวอร์ตัวเองทิ้ง (ทำงานเฉพาะถ้าเป็น Host)

            Utility.AssetManager.getInstance().clearCache();
            System.exit(0); // ปิดเกม
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
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        return btn;
    }
}