package UXUI.Scene;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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
import java.awt.event.ActionEvent;


public class OfficePanel extends JPanel {
    GamePanel realGamePanel ;
    Player realPlayer ;
    GameTime realGameTime ;
    private String text = "";
    private JLabel lblDay;
    private JLabel lblTime;
    private JLabel lblMoney;
    private Notify shopNotify;
    private JLabel lblEnergy;  
    private StatusBar energyBar;
    private JLabel lblMap ;
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
    ScreenFader fader = new ScreenFader();

    

    int btnY;
    DialoguePanel dialogueBox = new DialoguePanel();
    PauseMenuPanel pauseMenu;
    GameTime gameTime; 

    Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    public OfficePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new java.awt.BorderLayout());
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน
        
        pauseMenu = new PauseMenuPanel(mainFrame);

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

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

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

        lblEnergy = new JLabel("Energy: " + mainFrame.getPlayer().getEnergy());
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(250, 40, 200, 35); // วางตำแหน่งข้างๆ lblMoney
        statusPanel.add(lblEnergy);

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
        btnchoice1.addActionListener(e -> { tryBuyItem("Iron Sword", 150 );});
        Hovereffect.HoverEffectRounded(btnchoice1,startX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight , BUY_BUTTON);        
        add(btnchoice1);

        btnchoice2 = createRoundedButton("Zenith $400.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> { tryBuyItem("Zenith", 400);});
        Hovereffect.HoverEffectRounded(btnchoice2,startX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice2);

        btnchoice3 = createRoundedButton("Excalibur $250.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice3.setBounds(startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(e -> {tryBuyItem("Excalibur", 250);});
        Hovereffect.HoverEffectRounded(btnchoice3 , startX + (stdScreen.buttonWidth * 2) + (gap * 2), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice3);

        btnchoice4 = createRoundedButton(text);
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice4.setBounds(startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(e -> {
            if (gameTime.getTimeSlot() < 3) {
                Scene();
            } else shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED, 2000);
            
        });
        Hovereffect.HoverEffectRounded(btnchoice4,startX + (stdScreen.buttonWidth * 3) + (gap * 3), btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, BUY_BUTTON);        
        add(btnchoice4);

    //---------------------------Back Button---------------------------------------
        btnBack = createRoundedButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> fader.fadeInOut(250, 250, ()->{mainFrame.showGame();}, null));;
        Hovereffect.HoverEffectRounded(btnBack,20, 20, 100, 30, BACK_BUTTON);        
        add(btnBack);   

    //---------------------------Background หน้าร้าน---------------------------
        lblMap = new JLabel("");
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage("image\\Scene\\Office\\Barad-durWork.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

        updateEnergyBar();

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(pauseMenu);

        setComponentZOrder(shopNotify, 0);       // ให้แจ้งเตือนอยู่หน้าสุด
        setComponentZOrder(pauseMenu, 1);        // 🔥 ให้แผ่น Pause อยู่ชั้นที่ 1 (รองจากแจ้งเตือนนิดเดียว หรือจะให้เป็น 0 แทน Notify เลยก็ได้!)
        setComponentZOrder(dialog, 2);           // กล่องยืนยันการซื้อ
        setComponentZOrder(statusPanel, 3);      // แถบสเตตัส
        setComponentZOrder(lblMap, getComponentCount() - 1); // ภาพพื้นหลังอยู่ล่างสุด
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

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        scene.add(pauseMenu);
        scene.setComponentZOrder(pauseMenu, 0);

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

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        scene.add(pauseMenu);
        scene.setComponentZOrder(pauseMenu, 0);

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showOfficeScene2_1() {
        this.removeAll();

        CreateESC();
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
        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        scene.add(pauseMenu);
        scene.setComponentZOrder(pauseMenu, 0);

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void Work() {
        this.removeAll();
        this.setLayout(new java.awt.BorderLayout());
        CreateESC();
        // 🔥 1. สร้าง Array มารับค่าชั่วคราวเพื่อหลบ Error ของ Java
        final CreateTemplateScene[] sceneRef = new CreateTemplateScene[1];

        // 2. สร้าง Scene และเก็บลงใน Array ช่องที่ 0
        sceneRef[0] = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWorkWithPerson.png", 
            "Manager", 
            "จงทำงานซะ", 
            e -> {mainFrame.showGame();}, 
            "บิด",

            new CreateTemplateScene.SceneOption("work hard", e -> {
                if (realPlayer.getEnergy() < 40) {
                    
                    // 🔥 3. เรียกใช้ scene ผ่าน array แทน
                    sceneRef[0].add(shopNotify);
                    sceneRef[0].setComponentZOrder(shopNotify, 0);
                    
                    shopNotify.showNotify("Not Enough Energy to Work", Color.red, 3000);
                    
                    sceneRef[0].revalidate();
                    sceneRef[0].repaint();
                    
                } else { 
                    showMiniGame(); 
                }
            })
        );

        // 4. ดึงค่ากลับมาใส่ตัวแปร scene แบบปกติ เพื่อแอดเข้าหน้าต่าง
        CreateTemplateScene scene = sceneRef[0];

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        scene.add(pauseMenu);
        scene.setComponentZOrder(pauseMenu, 0);
        
        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showMiniGame() {
        this.removeAll();
        this.setLayout(null); // บังคับ null layout ป้องกันบัคตำแหน่งเพี้ยน
        CreateESC();
        BlacksmithMinigame minigame = new BlacksmithMinigame(mainFrame ,this);
        minigame.setBounds(0, 0, stdScreen.width, stdScreen.height); 

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Office\\Barad-durWork.png", 
            null, 
            null, 
            e -> {
                minigame.stopGame(); 
                Work();
            },
            "บิด"
        );
        scene.setBounds(0, 0, stdScreen.width, stdScreen.height);

        scene.add(minigame);
        scene.setComponentZOrder(minigame, 0); // ตั้งให้ Minigame อยู่หน้าสุด

        for (java.awt.Component c : scene.getComponents()) {
            if (c instanceof javax.swing.JButton) {
                scene.setComponentZOrder(c, 0);
            }
        }

        add(scene);
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
    private void tryBuyItem(String itemName, int price) {
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

    public void updateUI() {
        if (mainFrame == null ) return ;
        Player player = mainFrame.getPlayer();
        GameTime gTime = mainFrame.getGameTime(); 

        energyBar.setEnergy(player.getEnergy());
        lblEnergy.setText("Energy: " + player.getEnergy());

        lblEnergy.setForeground(Color.white);
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());
        // [เพิ่มใหม่] อัปเดตไอคอนเวลาทุกครั้งที่เวลาเดิน
        lblTime.setIcon(getTimeIcon(gTime.getTimeString()));
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