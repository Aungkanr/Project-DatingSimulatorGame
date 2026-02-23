package UXUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;

import Utility.ScreenFader;
import Utility.StdAuto;

public class MenuPanel extends JPanel {
    private String musicPath = "Music/Harvest Dawn.wav"; 
    private MainFrame parent;
    private StdAuto stdScreen;
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    ScreenFader fader = new ScreenFader();

    Color startBtnColor = new Color(255, 105, 180);        
    Color settingBtnColor = new Color(138, 43, 226);       
    Color exitBtnColor = new Color(70, 70, 90);           

    public MenuPanel(MainFrame mainFrame) {
        stdScreen = new StdAuto();
        this.parent = mainFrame;
        
        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        setLayout(null);
        setBackground(new Color(173, 216, 230));
        
        int btnW = 250;
        int btnH = 60;
        int gap = 20;

        int btnX = stdScreen.width - btnW - 150; 
        int startY = 220; 

        // ลบบรรทัดนี้ออก เพื่อไม่ให้เสียงถูกรีเซ็ตเบาลงทุกครั้งที่กลับมาหน้าเมนู
        // parent.getSFXManager().setVolume(0.1f); 

        // 1. START (ปรับเป็นปุ่มมน)
        JButton btnStart = createRoundedButton("START");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
        // ใช้ HoverEffectRounded แทน
        Hovereffect.HoverEffectRounded(btnStart, btnX, startY, btnW, btnH, startBtnColor);
        btnStart.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeInOut(500, 500, ()->{                
            parent.showGame();
            if (parent.getSoundManager() != null) {
                parent.getSoundManager().playMusic(musicPath);
            }}, null);
        });
        add(btnStart);
        
        // 2. SETTING (ปรับเป็นปุ่มมน)
        JButton btnSetting = createRoundedButton("SETTING");
        btnSetting.setFont(new Font("Tahoma", Font.BOLD, 20));
        int settingY = startY + btnH + gap;
        // ใช้ HoverEffectRounded แทน
        Hovereffect.HoverEffectRounded(btnSetting, btnX, settingY, btnW, btnH, settingBtnColor);
        btnSetting.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeOut(250, () -> {
                parent.showOption();
                fader.fadeIn(250, null);
            });
        });
        add(btnSetting);
        
        // 3. EXIT (ปรับเป็นปุ่มมน)
        JButton btnExit = createRoundedButton("EXIT");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        int exitY = settingY + btnH + gap;
        // ใช้ HoverEffectRounded แทน
        Hovereffect.HoverEffectRounded(btnExit, btnX, exitY, btnW, btnH, exitBtnColor);
        btnExit.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            Utility.AssetManager.getInstance().clearCache();
            System.exit(0);
        });
        add(btnExit);

        // Background
        JLabel lblMap = new JLabel("");
        String imagePath = "image\\MenuBackground.png";
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }

    // [เพิ่มใหม่] Helper Method สำหรับสร้างปุ่มมน (เหมือนใน GamePanel)
    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // วาดพื้นหลังมน (ใช้สีจาก getBackground() ซึ่งจะถูกเปลี่ยนโดย HoverEffectRounded)
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // ความโค้ง 30

                // วาดขอบสีขาว
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);

                super.paintComponent(g);
            }
        };
        // ตั้งค่าเริ่มต้นเพื่อไม่ให้ Swing วาดปุ่มสี่เหลี่ยมทับ
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}