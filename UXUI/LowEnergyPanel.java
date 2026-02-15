package UXUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Player.Player;
import Utility.GameTime;
import Utility.SleepEffect;
import Utility.StdAuto;
import Utility.Notify;

public class LowEnergyPanel extends JPanel {
    private SleepEffect sleepEffect = new SleepEffect();
    private StdAuto stdScreen = new StdAuto() ;
    private Notify notify = new Notify(stdScreen.width);
    private JButton btnSleep;
    private JButton btnCancel;
    private MainFrame parent;
    // 1. เพิ่มตัวแปรเช็คสถานะ
    private boolean isSleepingMode = false; 

    public LowEnergyPanel(int screenWidth, int screenHeight, MainFrame parent) {
        this.parent = parent;
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); // พื้นหลังโปร่งใส

        initComponents(screenWidth, screenHeight);
        
        // เพิ่ม Sleep Effect เตรียมไว้เลย (แต่ซ่อนไว้ก่อน หรือให้มันใสๆ ไว้)
        sleepEffect.setBounds(0, 0, screenWidth, screenHeight);
        add(sleepEffect);
    }

    private void initComponents(int screenWidth, int screenHeight) {
        Player player = parent.getPlayer();
        GameTime gameTime = parent.getGameTime();
        int boxWidth = 400;
        int boxHeight = 250;
        int x = (screenWidth - boxWidth) / 2;
        int y = (screenHeight - boxHeight) / 2;

        btnSleep = new JButton("นอนพักผ่อน");
        btnSleep.setBounds(x + 50, y + 150, 140, 40);
        btnSleep.setBackground(new Color(100, 200, 100));
        btnSleep.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        btnSleep.addActionListener(e -> {
            if (gameTime.isNight_Afternoon()) {
                // --- เริ่มกระบวนการนอน ---
                isSleepingMode = true; // บอกว่าเข้าโหมดนอนแล้วนะ
                
                // ซ่อนปุ่มทันที
                btnSleep.setVisible(false);
                btnCancel.setVisible(false);
                
                // สั่งวาดหน้าจอใหม่ (เพื่อให้กล่องข้อความหายไปตาม logic ใน paintComponent)
                repaint(); 

                // Logic เกม
                gameTime.nextDay();
                player.setEnergy(100);

                // เริ่ม Effect
                sleepEffect.startSleepSequence();

                // ตั้งเวลาปิดหน้านี้
                new javax.swing.Timer(4000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        isSleepingMode = false; 
                        btnSleep.setVisible(true);
                        btnCancel.setVisible(true);
                        parent.showGame();
                        ((javax.swing.Timer)e.getSource()).stop();
                    }
                }).start();
                new javax.swing.Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        notify.showNotify("Next Day : " + gameTime.getDay(), Color.GREEN, 800);
                        add(notify); // เพิ่ม notify เข้ามาใน panel นี้ (ถ้ายังไม่เพิ่ม)
                        setComponentZOrder(notify, 0);
                    }
                }).start();
            }
        });
        add(btnSleep);
        
        btnCancel = new JButton("ยกเลิก");
        btnCancel.setBounds(x + 210, y + 150, 140, 40);
        btnCancel.setBackground(new Color(200, 100, 100));
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancel.addActionListener(e -> {
            this.setVisible(false);
            parent.showGame();
        });
        add(btnCancel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 2. ถ้ากำลังนอนอยู่ (isSleepingMode = true) ไม่ต้องวาดอะไรเลย (ปล่อยให้ Sleep effect วาดทับ)
        if (isSleepingMode) {
            return; 
        }

        // --- ข้างล่างนี้คือโค้ดเดิม (วาดกล่องข้อความ) จะทำงานเฉพาะตอน isSleepingMode = false ---
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // วาดพื้นหลัง "มืดๆ"
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // วาดกล่องแจ้งเตือน
        int boxWidth = 400;
        int boxHeight = 250;
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2;

        g2.setColor(new Color(40, 40, 40));
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        // วาดข้อความ
        g2.setColor(new Color(255, 100, 100));
        g2.setFont(new Font("Tahoma", Font.BOLD, 22));
        drawCenteredString(g2, "พลังงานหมด!", x + boxWidth/2, y + 50);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        drawCenteredString(g2, "ร่างกายของคุณต้องการพักผ่อนทันที", x + boxWidth/2, y + 90);
        drawCenteredString(g2, "ต้องการนอนหลับหรือไม่?", x + boxWidth/2, y + 120);
    }

    private void drawCenteredString(Graphics g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int drawX = x - (metrics.stringWidth(text) / 2);
        g.drawString(text, drawX, y);
    }
}