package UXUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.Clip; // import แค่นี้พอ
import javax.swing.*;

import Utility.StdAuto;

public class MenuPanel extends JPanel {
    
    private MainFrame parent;
    private StdAuto stdScreen = new StdAuto(); //Device screen
    
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    public MenuPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(new Color(173, 216, 230));
        setLayout(null);
        stdScreen.setBtnWHG(300, 60, 30, 3); //ขนาด ปุ่ม และ gap ,แถว
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 1. START Button
        JButton btnStart = new JButton("START");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showGame();
                
                // สั่งเล่นเพลงผ่าน parent
                Clip c = parent.getClip();
                if (c != null) {
                    c.setMicrosecondPosition(0); // เริ่มเพลงใหม่ (ถ้าต้องการ)
                    c.loop(Clip.LOOP_CONTINUOUSLY); // เล่นวนซ้ำ
                    c.start();
                }
            }
        });
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStart.setBounds(stdScreen.centerX+600, stdScreen.currentY-300, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnStart);
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 2. SETTING Button
        JButton btnSetting = new JButton("SETTING");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showOption();
            }
        });
        btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSetting.setBounds(stdScreen.centerX+600, stdScreen.currentY-300, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnSetting);
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 3. EXIT Button
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnExit.setBounds(stdScreen.centerX+600, stdScreen.currentY-300, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnExit);

        // Background 
        JLabel lblMap = new JLabel("");

        String imagePath = "image\\MenuBackground.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
    }
}