package UXUI;
import java.awt.Color;
import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.*;

import Utility.ScreenFader;
import Utility.StdAuto;

public class MenuPanel extends JPanel {
    private String musicPath = "Music\\Harvest Dawn.wav"; // แนะนำใช้ / แทน \\ เพื่อรองรับทุก OS
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

        // [แก้ตำแหน่งปุ่ม] ให้ชิดขวา และเรียงจากล่างขึ้นบน
        int btnX = stdScreen.width - btnW - 150; // ห่างจากขอบขวา 80px
        int startY = 220; // เริ่มวางปุ่ม Start ที่ความสูง 350 (กลางๆ ค่อนไปทางล่าง)

        parent.getSFXManager().setVolume(0.1f);
        // 1. START
        JButton btnStart = new JButton("START");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStart.setBounds(btnX, startY, btnW, btnH);
        Hovereffect.HoverEffect(btnStart, btnX, startY, btnW, btnH, startBtnColor);
        btnStart.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeInOut(500, 500, ()->{                
            parent.showGame();
            if (parent.getSoundManager() != null) {
                parent.getSoundManager().playMusic(musicPath);
            }}, null);
        });
        add(btnStart);
        
        // 2. SETTING
        JButton btnSetting = new JButton("SETTING");
        btnSetting.setFont(new Font("Tahoma", Font.BOLD, 20));
        int settingY = startY + btnH + gap;
        btnSetting.setBounds(btnX, settingY, btnW, btnH);
        Hovereffect.HoverEffect(btnSetting, btnX, settingY, btnW, btnH, settingBtnColor);
        btnSetting.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeOut(250, () -> {
                parent.showOption();
                fader.fadeIn(250, null);
            });
        });
        add(btnSetting);
        
        // 3. EXIT
        JButton btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        int exitY = settingY + btnH + gap;
        btnExit.setBounds(btnX, exitY, btnW, btnH);
        Hovereffect.HoverEffect(btnExit, btnX, exitY, btnW, btnH, exitBtnColor);
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
}


