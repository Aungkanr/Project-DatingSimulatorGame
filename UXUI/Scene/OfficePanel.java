package UXUI.Scene;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UXUI.DialoguePanel;
import UXUI.Hovereffect;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import UXUI.StatusBarMenu.RoundedPanel;
import Utility.ConfirmPanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.StatusBar;
import Utility.StdAuto;
import Player.Player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;


public class OfficePanel extends JPanel {
    GamePanel realGamePanel ;
    Player realPlayer ;
    GameTime realGameTime ;
    private String text = "";
    private JLabel lblDay;
    private JLabel lblTime;
    private JLabel lblMoney;
    private Notify shopNotify;
    private StatusBar energyBar;
    private StdAuto stdScreen;
    private ConfirmPanel dialog;
    private JButton btnchoice1;
    private JButton btnchoice2;
    private JButton btnchoice3;
    private JButton btnchoice4;
    private JButton btnBack;
    public static final Color BUY_BUTTON = new Color(90, 50, 30);
    public static final Color BACK_BUTTON = new Color(48, 25, 82);    
    public static final Color btn1 = new Color(55, 55, 55);
    private MainFrame mainFrame;

    int btnY;
    DialoguePanel dialogueBox = new DialoguePanel();
    GameTime gameTime; 

    Utility.CheckImage checkImageUtil = new Utility.CheckImage();



    public OfficePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new java.awt.BorderLayout());
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน
        
        btnY = stdScreen.bottomY;
        realGamePanel = mainFrame.getGamePanel();
        realPlayer = mainFrame.getPlayer();
        realGameTime = mainFrame.getGameTime();
        gameTime = mainFrame.getGameTime();

        BlacksmithShop();
    }

    public void BlacksmithShop() {
    // ----------------Status Energy Money Day Time -----------------------------------
        dialog = new ConfirmPanel(stdScreen.width, stdScreen.height , mainFrame);
        add(dialog);

        setBackground(new Color(12, 51, 204));

        shopNotify = new Notify(stdScreen.width);
        shopNotify.setBounds(0, 50, stdScreen.width, 50); 
        add(shopNotify);

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
    //---------------------------LogiText---------------------------
        if (realPlayer.getOfficeStage() == 0) {
            text = "ข้าต้องการพบผู้จัดการ";
        } else if (realPlayer.getOfficeStage() >= 1) {
            text = "ข้าต้องการทำงาน";
        }
        
    //---------------------------dialogueBox---------------------------
        dialogueBox.setDefaultBounds(stdScreen, btnY);
        add(dialogueBox);

        if (gameTime.getTimeSlot() < 3) {
            dialogueBox.setText("Blacksmith", "Welcome to Blacksmith shop! What do you want to buy?");
        } else dialogueBox.setText("Blacksmith", "The Blacksmith was closed.!!!!");
    //---------------------------------------------------------------
        int totalWidth = (stdScreen.buttonWidth * 4) + (20 * 3);
        int startX = (stdScreen.width - totalWidth) / 2;
        int gap = 20;

        btnchoice1 = createRoundedButton("Iron Sword $150.");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> { tryBuyItem("Iron Sword", 150, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice1,startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight , BUY_BUTTON);        
        add(btnchoice1);

        btnchoice2 = createRoundedButton("Zenith $400.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> { tryBuyItem("Zenith", 400, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice2,startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice2);

        btnchoice3 = createRoundedButton("Excalibur $250.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice3.setBounds(startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(e -> {tryBuyItem("Excalibur", 250, gameTime);});
        Hovereffect.HoverEffectRounded(btnchoice3 , startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice3);

        btnchoice4 = createRoundedButton(text);
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice4.setBounds(startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(e -> {Scene();});
        Hovereffect.HoverEffectRounded(btnchoice4,startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice4);

    //---------------------------Back Button---------------------------------------
        btnBack = createRoundedButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> mainFrame.showGame());;
        Hovereffect.HoverEffectRounded(btnBack,20, 20, 100, 30, BACK_BUTTON);        
        add(btnBack);   

    //---------------------------Background หน้าร้าน---------------------------
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage("image\\Scene\\Office\\Barad-durWork.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

        updateEnergyBar();
    }

    public void Scene() {
        if (realPlayer.getOfficeStage() == 0) {
            showOfficeScene();
        } else if (realPlayer.getOfficeStage() >= 1) {
            Work();
        }
    }

    public void showOfficeScene() {
        this.removeAll();

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWorkWithPerson.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "ว่าไงพ่อหนุ่ม... มีอะไรให้ข้าช่วยไหม?", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            null, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)
            null,

            // 1. ปุ่มแบบ Auto ให้ระบบจัดวางให้เอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่ม ", e ->  เมื่อกดปุ่มจะให้ทำอะไรต่อ), *** ตัวอย่างการใช้งาน

            // --- ปุ่มขวา (Give Job Application) ---
            new CreateTemplateScene.SceneOption("...สวัสดี ข้าชื่อ ...???", e -> {
                showOfficeScene2();
            }),
            
            // --- ปุ่มซ้าย (I need to work here) ---
            new CreateTemplateScene.SceneOption("อ่าา ที่นี่คือที่ไหน", e -> {
                showOfficeScene2_1();
            })
        );

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showOfficeScene2() {
        this.removeAll();

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWorkWithPerson.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "ส่วนข้าเป็นผู้จัดการที่นี่ เจ้ามีอะไรให้รึ", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            null, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)
            null,

            new CreateTemplateScene.SceneOption("ไม่มีอะไรครับท่าน ผมเเค่หลงทางมา", e -> {
                mainFrame.showGame();
            }),
            
            new CreateTemplateScene.SceneOption("อ่าา ที่นี่คือที่ไหน", e -> {
                showOfficeScene2_1();
            })
        );

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showOfficeScene2_1() {
        this.removeAll();

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWorkWithPerson.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "ที่นี่คือ โรงงานตีเหล็ก เจ้าสามารถซื้อดาบที่นี่ได้ ดาบที่นี่มีเเต่ดาบที่มีคุณภาพเพราะช่างตีเหล็กของข้ามีเเต่คนเชี่ยวชาญ", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            null, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)
            null,

            new CreateTemplateScene.SceneOption("ไม่มีอะไรครับท่าน ผมเเค่หลงทางมา", e -> {
                mainFrame.showGame();
            }),
            
            new CreateTemplateScene.SceneOption("ข้าอยากจะสอบถามว่าที่นี่มีงานให้ข้าทำไหม", e -> {
                realPlayer.increaseOfficeStage();
                Work();
            })
        );

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void Work() {
        this.removeAll();

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWorkWithPerson.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "จงทำงานซะ", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            e -> {mainFrame.showGame();}, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)
            "บิด",

            new CreateTemplateScene.SceneOption("work hard", e -> {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                if (realGamePanel != null) {
                    realGamePanel.doActivity(40);
                    DebugLog();
                }
            })
        );
        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void DebugLog() {
        System.out.println("Working -> Energy: "+ realPlayer.getEnergy() +" | Day : "+ realGameTime.getDay() + "\n" +"          " + "Money : "+ realPlayer.getMoney()  +" | Time: "+ realGameTime.getTimeString());
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
            dialogueBox.setText("Blacksmith", "No money? Get out!");
        } else {
            boolean success = realPlayer.buyItem(item, price); 
            if (success) { 
                shopNotify.showNotify("Purchased " + item + "!", Color.GREEN , 2000); 
                dialogueBox.setText("Blacksmith", "Thanks for buying." + item);
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