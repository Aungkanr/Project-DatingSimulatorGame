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
 
    public MenuPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(new Color(173, 216, 230));
        setLayout(null);
        
        // 1. Label
        JLabel lblHome = new JLabel("หน้าเมนูหลัก");
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHome.setHorizontalAlignment(SwingConstants.CENTER);
        lblHome.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(lblHome);
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 2. START Button
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
        btnStart.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnStart);
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 3. SETTING Button
        JButton btnSetting = new JButton("SETTING");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showOption();
            }
        });
        btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSetting.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnSetting);
        
        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;
        
        // 4. EXIT Button
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnExit.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnExit);
    }
}