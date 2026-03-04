package UXUI.Scene;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Player.Player;
import UXUI.DialoguePanel;
import UXUI.Hovereffect;
import UXUI.MainFrame;
import UXUI.PauseMenuPanel;
import UXUI.StatusBarMenu.GamePanel;
import UXUI.StatusBarMenu.RoundedPanel;
import Utility.ConfirmPanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.ScreenFader;
import Utility.StdAuto;
import Utility.StatusBar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image; 
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

public class ShopPanel extends JPanel {
    private StdAuto stdScreen;
    private Notify shopNotify; // <--- ใช้ชื่อนี้
    private JLabel lblMoney;
    private StatusBar energyBar;
    private JLabel lblDay;
    private JLabel lblTime;
    public static final Color BUY_BUTTON = new Color(90, 50, 30);
    public static final Color BACK_BUTTON = new Color(48, 25, 82);    
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    ScreenFader fader = new ScreenFader();
    DialoguePanel dialogueBox;
    PauseMenuPanel pauseMenu;

    private JButton btnchoice1, btnchoice2, btnchoice3, btnchoice4, btnBack, Galadriel;
    private ConfirmPanel dialog;
    private MainFrame mainFrame ;
    
    
    public ShopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(300, 50, 20, 0); 
        
        pauseMenu = new PauseMenuPanel(mainFrame);

        setLayout(null);
        setBackground(new Color(12, 51, 204));

        shopNotify = new Notify(stdScreen.width);
        shopNotify.setBounds(0, 50, stdScreen.width, 50); 

        
        updateUI();
    }

    private void initComponents() {
        int btnY = stdScreen.bottomY;
        GameTime gameTime = mainFrame.getGameTime(); 
        
        dialog = new ConfirmPanel(stdScreen.width, stdScreen.height , mainFrame);
        add(dialog);

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        // ----------------Status Energy Money Day Time -----------------------------------
        RoundedPanel statusPanel = new RoundedPanel(30, GamePanel.themePink); 
        statusPanel.setBounds(20, 60, 450, 120); 
        statusPanel.setLayout(null);

        // Energy
        energyBar = new StatusBar(100, "Energy");
        energyBar.setBounds(20, 10, 410, 20);
        statusPanel.add(energyBar);

        // money
        Player initialPlayer = mainFrame.getPlayer(); 
        lblMoney = new JLabel("Money: " + initialPlayer.getMoney());
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMoney.setForeground(GamePanel.MoneyColor);
        lblMoney.setBounds(20, 40, 200, 35);
        lblMoney.setIcon(getScaledIcon("image\\StatusBarIcon\\money.png", 32, 32)); 

        // Day 
        lblDay = new JLabel("Day: " + gameTime.getDay());
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(20, 80, 150, 35);
        lblDay.setIcon(getScaledIcon("image\\StatusBarIcon\\day.png", 32, 32)); 

        // Time 
        lblTime = new JLabel("Time: " + gameTime.getTimeString());
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 80, 200, 35);
        lblTime.setIcon(getTimeIcon(gameTime.getTimeString())); 
        
        statusPanel.add(lblMoney);
        statusPanel.add(lblDay);
        statusPanel.add(lblTime);
        add(statusPanel);
        
        //---------------------------dialogueBox---------------------------
        dialogueBox = new DialoguePanel();
        dialogueBox.setDefaultBounds(stdScreen, btnY);
        add(dialogueBox);

        if (gameTime.getTimeSlot() < 3) {
            dialogueBox.setText("Florist", "Welcome to Diddy shop! What do you want to buy?");
        } else dialogueBox.setText("Florist", "The shop was closed.!!!!");
        
        //---------------------------------------------------------------
        int totalWidth = (stdScreen.buttonWidth * 4) + (20 * 3);
        int startX = (stdScreen.width - totalWidth) / 2;
        int gap = 20;

        btnchoice1 = createRoundedButton("Blue Jazz $50.");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> tryBuyItem("Blue Jazz", 50, gameTime));
        Hovereffect.HoverEffectRounded(btnchoice1,startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight , BUY_BUTTON);        
        add(btnchoice1);

        btnchoice2 = createRoundedButton("Poppy $65.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> tryBuyItem("Poppy", 65, gameTime));
        Hovereffect.HoverEffectRounded(btnchoice2,startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice2);

        btnchoice3 = createRoundedButton("Tulip $90.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice3.setBounds(startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(e -> tryBuyItem("Tulip", 90, gameTime));
        Hovereffect.HoverEffectRounded(btnchoice3 , startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice3);

        btnchoice4 = createRoundedButton("Fairy Rose $120.");
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice4.setBounds(startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(e -> tryBuyItem("Fairy Rose", 120, gameTime));
        Hovereffect.HoverEffectRounded(btnchoice4,startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice4);

        //---------------------Talk to Galadriel----------------------------
        Galadriel = createRoundedButton("Talk to Galadriel");
        Galadriel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
        int talkX = stdScreen.width - stdScreen.buttonWidth - 30; 
        int talkY = 20; 
        Galadriel.setBounds(talkX, talkY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        
        Galadriel.addActionListener(e -> {
            if (mainFrame.getGameTime().getTimeSlot() < 3) { 
                mainFrame.createGaladrielPanel(); 
                mainFrame.showGaladriel();
            } else shopNotify.showNotify("Galadriel is resting.", Color.RED, 2000);
        });
        
        Color talkColor = new Color(218, 165, 32); 
        Hovereffect.HoverEffectRounded(Galadriel, talkX, talkY, stdScreen.buttonWidth, stdScreen.buttonHeight, talkColor);        
        add(Galadriel);

        //---------------------------Back Button---------------------------------------
        btnBack = createRoundedButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        
        btnBack.addActionListener(e -> {
            fader.fadeInOut(250, 250, ()->{
                if(mainFrame.getGamePanel() != null) {
                    mainFrame.getGamePanel().updateUI(); 
                }
                mainFrame.showGame();
            }, null);
        });
        
        Hovereffect.HoverEffectRounded(btnBack,20, 20, 100, 30, BACK_BUTTON);        
        add(btnBack);   

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(pauseMenu);

        //---------------------------Background หน้าร้าน---------------------------
        JLabel lblMap = new JLabel("");
        String currentBg = getShopBgPath(gameTime.getTimeString());
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage(currentBg);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        
        setComponentZOrder(shopNotify, 0);       // ให้แจ้งเตือนอยู่หน้าสุด
        setComponentZOrder(pauseMenu, 1);        // 🔥 ให้แผ่น Pause อยู่ชั้นที่ 1 (รองจากแจ้งเตือนนิดเดียว หรือจะให้เป็น 0 แทน Notify เลยก็ได้!)
        setComponentZOrder(dialog, 2);           // กล่องยืนยันการซื้อ
        setComponentZOrder(statusPanel, 3);      // แถบสเตตัส
        setComponentZOrder(lblMap, getComponentCount() - 1); // ภาพพื้นหลังอยู่ล่างสุด

        updateEnergyBar();
    }
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = Utility.AssetManager.getInstance().getImage(path);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null;
    }

    private ImageIcon getTimeIcon(String timeString) {
        if (timeString.equalsIgnoreCase("Morning")) {
            return getScaledIcon("image\\StatusBarIcon\\sunrise.png", 32, 32);
        } else if (timeString.equalsIgnoreCase("noon")) { 
            return getScaledIcon("image\\StatusBarIcon\\sun.png", 32, 32);
        } else if (timeString.equalsIgnoreCase("Evening")) {
            return getScaledIcon("image\\StatusBarIcon\\sunset.png", 32, 32);
        } else {
            return getScaledIcon("image\\StatusBarIcon\\moon.png", 32, 32);
        }
    }

    // Helper สร้างปุ่มมน
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

    private void tryBuyItem(String itemName, int price, GameTime gameTime) {
        if (gameTime.getTimeSlot() < 3) {
            dialog.show("Buy " + itemName + " for $" + price + "?", null, "YES", e -> Detect(itemName, price));
        } else {
            shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED, 2000);
        }
    }

    public void Detect (String item , int price) { 
        Player realPlayer = mainFrame.getPlayer(); 
        
        if (realPlayer.getMoney() < price) {
            shopNotify.showNotify("Not enough money!", Color.RED , 2000);
            dialogueBox.setText("Florist", "No money? Get out!");
        } else {
            boolean success = realPlayer.buyItem(item, price); 
            if (success) { 
                shopNotify.showNotify("Purchased " + item + "!", Color.GREEN , 2000); 
                dialogueBox.setText("Florist", "Thanks for buying." + item);
                lblMoney.setText("Money: " + realPlayer.getMoney());
                lblMoney.repaint();
            }
        }
    }

    public void updateEnergyBar() {
        Player player = mainFrame.getPlayer();
        energyBar.setEnergy(player.getEnergy());
    }

    // --- เปลี่ยนชื่อเมธอดให้ตรงกับหน้าร้านดอกไม้ ---
    private String getShopBgPath(String timeString) {
        switch (timeString) {
            case "Morning": return "image\\Scene\\Shop\\ร้านดอกไม้ตอนเช้า.png";
            case "Noon":    return "image\\Scene\\Shop\\ร้านดอกไม้ตอนกลางวัน.png";
            case "Evening": return "image\\Scene\\Shop\\ร้านดอกไม้ตอนเย็น.png";
            case "Night":   return "image\\Scene\\Shop\\ร้านดอกไม้ตอนกลางคืน.png";
            default:        return "image\\Scene\\Shop\\ร้านดอกไม้ตอนเช้า.png";
        }
    }

    // --- Real-time update ---
    public void updateUI() {
        if (mainFrame == null) return;
        removeAll();
        add(shopNotify); 
        initComponents(); 
        revalidate(); 
        repaint();    
    }
    
    public void CreateESC() {
        //---------------------------ESC Event---------------------------------------
        pauseMenu.setVisible(false); // เริ่มมาให้ซ่อนไว้ก่อน

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "smartEsc");
        this.getActionMap().put("smartEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // 🔥 1. เช็คก่อนว่า "หน้า Option ลอยทับอยู่หรือเปล่า?"
                if (mainFrame.getOptionPanel() != null && mainFrame.getOptionPanel().isVisible()) {
                    // ถ้าลอยอยู่ -> ให้กด ESC เพื่อ "ปิดหน้า Option" อย่างเดียว
                    mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                    mainFrame.getOptionPanel().setVisible(false); 
                }
                // 🔥 2. ถ้า Option ไม่ได้เปิดอยู่ -> ค่อยสลับเปิด/ปิด หน้า Pause ตามปกติ
                else {
                    boolean isCurrentlyVisible = pauseMenu.isVisible();
                    pauseMenu.setVisible(!isCurrentlyVisible);
                }
                
            }
        });
    }
}