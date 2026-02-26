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
    Color multiBtnColor = new Color(50, 205, 50);    // สีเขียวสำหรับปุ่ม Multiplayer
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

        // ตำแหน่งเดิมที่คุณตั้งไว้
        int btnX = stdScreen.width - btnW - 150; 
        int startY = 220; 

        // 1. START (Solo)
        JButton btnStart = createRoundedButton("START");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
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
        
       // 2. MULTIPLAYER (CO-OP)
        JButton btnMulti = createRoundedButton("CO-OP (MULTIPLAYER)");
        btnMulti.setFont(new Font("Tahoma", Font.BOLD, 20));
        int multiY = startY + btnH + gap;
        Hovereffect.HoverEffectRounded(btnMulti, btnX, multiY, btnW, btnH, multiBtnColor);

        // --- แก้ไข Action ตรงนี้ (ลบ Popup เดิมทิ้ง เปลี่ยนเป็นเฟดหน้าจอ) ---
        btnMulti.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            
            // เปลี่ยนไปหน้า CoopMenuPanel แบบเต็มจอ
            fader.fadeOut(250, () -> {
                parent.showCoopMenu(); // เรียกหน้าต่าง Co-op ที่เราเพิ่งสร้าง
                fader.fadeIn(250, null);
            });
        });
        /* 
        
            
            // สร้างปุ่มตัวเลือก Host หรือ Join เหมือน Stardew Valley
            Object[] options = {"Host Game (สร้างห้อง)", "Join Game (เข้าร่วม)", "Cancel"};
            int choice = JOptionPane.showOptionDialog(
                parent, 
                "Do you want to Host a new game or Join an existing one?", 
                "Co-op Mode",
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, options, options[0]
            );

            if (choice == 0) { 
                // --- กรณีเลือก HOST (สร้างห้อง) ---
                // 1. สั่งเปิดเซิร์ฟเวอร์เบื้องหลังทันที
                Coop.Network.GameServer.startServerInBackground(9999); 
                
                // 2. พาตัวเองเข้าห้อง Lobby โดยต่อเข้า "localhost" อัตโนมัติ
                fader.fadeOut(500, () -> {
                    parent.showLobby();
                    parent.getLobbyPanel().setHostMode(true); // <--- สั่งให้แสดง IP ตัวเอง
                    parent.getLobbyPanel().connectToServer("localhost"); 
                    fader.fadeIn(500, null);
                });
                
            } else if (choice == 1) { 
                // --- กรณีเลือก JOIN (เข้าร่วม) ---
                String ipAddress = JOptionPane.showInputDialog(
                    parent, 
                    "Enter Host IP Address:", 
                    "Join Game", 
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (ipAddress != null && !ipAddress.trim().isEmpty()) {
                    fader.fadeOut(500, () -> {
                        parent.showLobby();
                        parent.getLobbyPanel().setHostMode(false); // <--- ซ่อนการแสดง IP
                        parent.getLobbyPanel().connectToServer(ipAddress.trim());
                        fader.fadeIn(500, null);
                    });
                }
            }
        });
    */
        add(btnMulti);

        // 3. SETTING (เลื่อนลงมาเป็นตำแหน่งที่ 3)
        JButton btnSetting = createRoundedButton("SETTING");
        btnSetting.setFont(new Font("Tahoma", Font.BOLD, 20));
        int settingY = multiY + btnH + gap; // คำนวณ Y ต่อจากปุ่ม Multiplayer
        Hovereffect.HoverEffectRounded(btnSetting, btnX, settingY, btnW, btnH, settingBtnColor);
        btnSetting.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeOut(250, () -> {
                parent.showOption();
                fader.fadeIn(250, null);
            });
        });
        add(btnSetting);
        
        // 4. EXIT (เลื่อนลงมาเป็นตำแหน่งที่ 4)
        JButton btnExit = createRoundedButton("EXIT");
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        int exitY = settingY + btnH + gap; // คำนวณ Y ต่อจากปุ่ม Setting
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

    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);

                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}