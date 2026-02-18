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


import java.awt.Color;
import java.awt.Font;
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
    // ConfirmPanel confirmPanel = new ConfirmPanel(); //pull data จาก Confir Panel มาใช้ 
    
    public ShopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame ;
        realGametime = mainFrame.getGameTime();
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(300, 50, 20, 0); // ตั้งขนาดปุ่ม
        
        dialog = new ConfirmPanel(stdScreen.width, stdScreen.height , mainFrame);

        setLayout(null);
        setBackground(new Color(12, 51, 204));

        // ใช้ bottomY (ความสูงจอ - 120px)
        int btnY = stdScreen.bottomY;
        GameTime gameTime = mainFrame.getGameTime(); 
        
        stdScreen = new StdAuto() ;
        stdScreen.setBtnWHG(250, 60, 20,0); //ขนาด ปุ่ม และ gap , แถว
        setLayout(null);
        setBackground(new Color(12, 51, 204));

    // -------------ConfirmPanel-----------------
        add(dialog);
        // add(confirmPanel);  //ไป setting scale, height width  ที่ ConfrimPanel.java
    //----------------ข้อความเตื่อน-----------------
        shopNotify = new Notify(stdScreen.width);
        shopNotify.setBounds(0, 50, stdScreen.width, 50); // *อยากเปลี่ยนตำแหน่งก็ได้*
        add(shopNotify);
    // ----------------Status Energy Money Day Time -----------------------------------
        RoundedPanel statusPanel = new RoundedPanel(30, GamePanel.themePink); 
        statusPanel.setBounds(20, 60, 450, 120); // ตำแหน่งกล่องบนหน้าจอ
        statusPanel.setLayout(null);

        // Energy
        energyBar = new StatusBar(100, "Energy");
        energyBar.setBounds(20, 10, 410, 20);
        statusPanel.add(energyBar);
        //money
        Player initialPlayer = mainFrame.getPlayer(); 
        lblMoney = new JLabel("Money: " + initialPlayer.getMoney());
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMoney.setForeground(GamePanel.MoneyColor);
        lblMoney.setBounds(20, 40, 200, 25);
        // Day (อยู่ขวาบนของกล่อง)
        lblDay = new JLabel("Day: " + gameTime.getDay());
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(20, 72, 150, 25);
        // Time (อยู่ขวาล่างของกล่อง)
        lblTime = new JLabel("Time: " + gameTime.getTimeString());
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 72, 200, 25);
        
        statusPanel.add(lblMoney);
        statusPanel.add(lblDay);
        statusPanel.add(lblTime);
        add(statusPanel);
    //---------------------------dialogueBox---------------------------
        // กล่องข้อความ (วางเหนือปุ่ม)
        dialogueBox.setDefaultBounds(stdScreen, btnY);
        add(dialogueBox);

        if (gameTime.getTimeSlot() < 3) {
            dialogueBox.setText("Florist", "Welcome to Diddy shop! What do you want to buy?");
        } else dialogueBox.setText("Florist", "The shop was closed.!!!!");
    //---------------------------------------------------------------
        // จัดปุ่ม 1-4 ให้สมมาตร ตรงกลางจอ
        // คำนวณจุดเริ่มต้น X ของปุ่มซ้ายสุด เพื่อให้ทั้งแผงอยู่กลางจอ
        // ความกว้างรวม = (4 ปุ่ม * กว้าง) + (3 ช่องว่าง * gap)
        int totalWidth = (stdScreen.buttonWidth * 4) + (20 * 3);
        int startX = (stdScreen.width - totalWidth) / 2;
        int gap = 20;
   //---------------------------choice 1---------------------------
        btnchoice1 = new JButton("Blue jazz $50.");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // ปุ่มที่ 1 วางที่ startX
        btnchoice1.setBounds(startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> { tryBuyItem("Blue jazz", 50, gameTime);});
        Hovereffect.HoverEffect(btnchoice1,startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight , BUY_BUTTON);        
        add(btnchoice1);
    //---------------------------choice 2---------------------------
        btnchoice2 = new JButton("Poppy $65.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // ปุ่มที่ 2 วางถัดจากปุ่ม 1 + gap
        btnchoice2.setBounds(startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> { tryBuyItem("Poppy", 65, gameTime);});
        Hovereffect.HoverEffect(btnchoice2,startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice2);
    //---------------------------choice 3---------------------------
        btnchoice3 = new JButton("Tulip $90.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // ปุ่มที่ 3 วางถัดจากปุ่ม 2 + gap
        btnchoice3.setBounds(startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(e -> {tryBuyItem("Tulip", 90, gameTime);});
        Hovereffect.HoverEffect(btnchoice3 , startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice3);
    //---------------------------choice 4---------------------------
        btnchoice4 = new JButton("Fairy rose $120.");
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // ปุ่มที่ 4 วางถัดจากปุ่ม 3 + gap
        btnchoice4.setBounds(startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(e -> {tryBuyItem("Fairy rose", 120, gameTime);});
        Hovereffect.HoverEffect(btnchoice4,startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice4);
    //---------------------Talk to Galadriel----------------------------
        Galadriel = new JButton("Talk to Galadriel");
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
        
        // ใช้สีปุ่ม Talk ให้ต่างจากปุ่มซื้อ (เช่น สีทอง)
        Color talkColor = new Color(218, 165, 32); 
        Hovereffect.HoverEffect(Galadriel, talkX, talkY, stdScreen.buttonWidth, stdScreen.buttonHeight, talkColor);        
        add(Galadriel);
    //---------------------------Back Button---------------------------------------
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> mainFrame.showGame());;
        Hovereffect.HoverEffect(btnBack,20, 20, 100, 30, BACK_BUTTON);        
        add(btnBack);   
    //---------------------------Background หน้าร้าน---------------------------
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage("image\\Scene\\Shop\\ร้านดอกไม้ตอนเช้า.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        //layer
        // setComponentZOrder(confirmPanel, 0); 
        setComponentZOrder(shopNotify, 1); 
        setComponentZOrder(statusPanel, 2);
        setComponentZOrder(lblMap, getComponentCount() - 1);

        updateEnergyBar();
    }
    //-------------Method to reduce code duplication-----------------
    private void tryBuyItem(String itemName, int price, GameTime gameTime) {
        if (gameTime.getTimeSlot() < 3) {
            dialog.show("Buy " + itemName + " for $" + price + "?", null, "YES", e -> Detect(itemName, price));
            // confirmPanel.showConfirm("Buy " + itemName + " for $" + price + "?", e -> Detect(itemName, price));
        } else {
            shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED, 2000);
        }
    }
    //------------------Buy Logic------------------------
    public void Detect (String item , int price) { //  code ซื้อของ
        
        Player realPlayer = mainFrame.getPlayer(); // PULL data from Player มาใช้ (getMoney ,  buyItem )
        
        if (realPlayer.getMoney() < price) {
            shopNotify.showNotify("Not enough money!", Color.RED , 2000);
            dialogueBox.setText("Florist", "No money? Get out!");
        } else {
            boolean success = realPlayer.buyItem(item, price); 
            if (success) { 
                
                shopNotify.showNotify("Purchased " + item + "!", Color.GREEN , 2000); 
                dialogueBox.setText("Florist", "Thanks for buying." + item);
                // *** อัปเดตตัวเลขเงินในหน้า Shop โดยตรง ***
                lblMoney.setText("Money: " + realPlayer.getMoney());
                // บังคับวาดหน้าจอใหม่ทันที 
                lblMoney.repaint();
            }
        }
    }

    public void updateEnergyBar() {
            Player player = mainFrame.getPlayer();
            energyBar.setEnergy(player.getEnergy());
    }
}