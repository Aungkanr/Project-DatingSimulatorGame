package UXUI.Scene;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Player.Player;
import UXUI.DialoguePanel;
import UXUI.Hovereffect;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import UXUI.StatusBarMenu.RoundedPanel;
import Utility.ConfirmPanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;
import Utility.StatusBar;

// เพิ่ม Import สำหรับ Image
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image; // [เพิ่มใหม่]
import java.awt.RenderingHints;

public class ShopPanel extends JPanel {
    private StdAuto stdScreen;
    private Notify shopNotify;
    private JLabel lblMoney;
    private StatusBar energyBar;
    private JLabel lblDay;
    private JLabel lblTime;
    public static final Color BUY_BUTTON = new Color(90, 50, 30);
    public static final Color BACK_BUTTON = new Color(48, 25, 82);    
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    DialoguePanel dialogueBox = new DialoguePanel();
    private GameTime realGametime ;
    private Notify realNotify;

    private JButton btnchoice1;
    private JButton btnchoice2;
    private JButton btnchoice3;
    private JButton btnchoice4;
    private JButton btnBack;
    private JButton Galadriel;     
    private ConfirmPanel dialog;
    private MainFrame mainFrame ;
    
    public ShopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame ;
        realGametime = mainFrame.getGameTime();
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(300, 50, 20, 0); 
        
        dialog = new ConfirmPanel(stdScreen.width, stdScreen.height , mainFrame);

        setLayout(null);
        setBackground(new Color(12, 51, 204));

        int btnY = stdScreen.bottomY;
        GameTime gameTime = mainFrame.getGameTime(); 
        
        stdScreen = new StdAuto() ;
        stdScreen.setBtnWHG(250, 60, 20,0); 
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        add(dialog);

        shopNotify = new Notify(stdScreen.width);
        shopNotify.setBounds(0, 50, stdScreen.width, 50); 
        add(shopNotify);

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
        // [แก้ไข] ขยับ Y เป็น 40 และความสูงเป็น 35 
        lblMoney.setBounds(20, 40, 200, 35);
        lblMoney.setIcon(getScaledIcon("image\\StatusBarIcon\\money.png", 32, 32)); // [เพิ่มใหม่]

        // Day 
        lblDay = new JLabel("Day: " + gameTime.getDay());
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDay.setForeground(Color.WHITE);
        // [แก้ไข] ขยับ Y เป็น 80 และความสูงเป็น 35
        lblDay.setBounds(20, 80, 150, 35);
        lblDay.setIcon(getScaledIcon("image\\StatusBarIcon\\day.png", 32, 32)); // [เพิ่มใหม่]

        // Time 
        lblTime = new JLabel("Time: " + gameTime.getTimeString());
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        // [แก้ไข] ขยับ Y เป็น 80 และความสูงเป็น 35
        lblTime.setBounds(250, 80, 200, 35);
        lblTime.setIcon(getTimeIcon(gameTime.getTimeString())); // [เพิ่มใหม่]
        
        statusPanel.add(lblMoney);
        statusPanel.add(lblDay);
        statusPanel.add(lblTime);
        add(statusPanel);
        
    //---------------------------dialogueBox---------------------------
        dialogueBox.setDefaultBounds(stdScreen, btnY);
        add(dialogueBox);

        if (gameTime.getTimeSlot() < 3) {
            dialogueBox.setText("Florist", "Welcome to Diddy shop! What do you want to buy?");
        } else dialogueBox.setText("Florist", "The shop was closed.!!!!");
        
    //---------------------------------------------------------------
        int totalWidth = (stdScreen.buttonWidth * 4) + (20 * 3);
        int startX = (stdScreen.width - totalWidth) / 2;
        int gap = 20;

        btnchoice1 = createRoundedButton("Blue jazz $50.");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> { tryBuyItem("Blue jazz", 50, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice1,startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight , BUY_BUTTON);        
        add(btnchoice1);

        btnchoice2 = createRoundedButton("Poppy $65.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> { tryBuyItem("Poppy", 65, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice2,startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice2);

        btnchoice3 = createRoundedButton("Tulip $90.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice3.setBounds(startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(e -> {tryBuyItem("Tulip", 90, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice3 , startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice3);

        btnchoice4 = createRoundedButton("Fairy rose $120.");
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice4.setBounds(startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(e -> {tryBuyItem("Fairy rose", 120, gameTime);});
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
        btnBack.addActionListener(e -> mainFrame.showGame());;
        Hovereffect.HoverEffectRounded(btnBack,20, 20, 100, 30, BACK_BUTTON);        
        add(btnBack);   

    //---------------------------Background หน้าร้าน---------------------------
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage("image\\Scene\\Shop\\ร้านดอกไม้ตอนเช้า.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        
        setComponentZOrder(shopNotify, 1); 
        setComponentZOrder(statusPanel, 2);
        setComponentZOrder(lblMap, getComponentCount() - 1);

        updateEnergyBar();
    }

    // [เพิ่มใหม่] ฟังก์ชันโหลดและย่อขนาดไอคอน 
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = Utility.AssetManager.getInstance().getImage(path);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null;
    }

    // [แก้ไข] ฟังก์ชันสลับรูปพระอาทิตย์-พระจันทร์ (เช็คเวลาตามชื่อใหม่)
    private ImageIcon getTimeIcon(String timeString) {
        if (timeString.equalsIgnoreCase("Morning")) {
            return getScaledIcon("image\\StatusBarIcon\\sunrise.png", 32, 32);
        } else if (timeString.equalsIgnoreCase("noon")) { // [แก้ไข] แยก Noon/Afternoon ออกจาก Evening
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

    //-------------Method to reduce code duplication-----------------
    private void tryBuyItem(String itemName, int price, GameTime gameTime) {
        if (gameTime.getTimeSlot() < 3) {
            dialog.show("Buy " + itemName + " for $" + price + "?", null, "YES", e -> Detect(itemName, price));
        } else {
            shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED, 2000);
        }
    }

    //------------------Buy Logic------------------------
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
}