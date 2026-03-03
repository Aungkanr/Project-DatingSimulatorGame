package UXUI.StatusBarMenu;

import Player.Player;
import UXUI.Hovereffect;
import UXUI.LowEnergyPanel;
import UXUI.MainFrame;
import UXUI.PauseMenuPanel;
import Utility.*;

// เพิ่ม Import สำหรับวาดกราฟิก และ Image
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image; // [เพิ่มใหม่] สำหรับจัดการย่อขนาดรูปภาพ
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon; // [เพิ่มใหม่]
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel {

    private MainFrame parent;
    private JLabel lblEnergy;      // Label Energy (เก็บไว้แต่ซ่อน)
    private StatusBar energyBar;   // หลอด Energy Bar
    private JLabel lblMoney;
    private JLabel lblDay;
    private JLabel lblTime;
    private JLabel lblMap;
    private StdAuto stdScreen ; //Device screen
    private Notify notification ; //ตัวแจ้งเตือน
    private JButton btnBag;
    private JLabel hitboxOffice;
    private JLabel hitboxShop;
    private JLabel hitboxHome;
    private JLabel hitboxSchool;
    private JLabel hitboxNeighbor;
    private JLabel tagOffice;
    private JLabel tagShop;
    private JLabel tagHome;
    private JLabel tagSchool;
    private JLabel tagNeighbor;
    private JLabel glowOffice;
    private JLabel glowShop;
    private JLabel glowHome;
    private JLabel glowSchool;
    private JLabel glowNeighbor;    

    // ------------------ Object ---------------------
    private InventoryPanel currentInvPanel;
    Utility.AssetManager asset = Utility.AssetManager.getInstance();
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    ScreenFader fader = new ScreenFader();
    GameTime gTime;
    // เพิ่มใหม่ ธีมสีชมพู (Pink Theme)
    public static final Color themePink = new Color(30, 25, 50, 220);      // ม่วงเข้มโปร่งแสง
    public static final Color themeBorder = new Color(100, 150, 255, 180); // ฟ้าเรืองแสง

    // ------------------ สีปุ่ม ---------------------
    public static final Color ExitGameColor = new Color(48, 25, 82);    
    public static final Color MoneyColor = new Color(255, 215, 80);  // เหลืองทองสว่าง
    public static final Color schoolColor = new Color(41, 128, 185);     
    public static final Color homeColor = new Color(230, 126, 34);
    public static final Color shopColor = new Color(46, 204, 113); 
    public static final Color officeColor = new Color(142, 68, 173);
    public static final Color neightborColor = new Color(255, 192, 203);       
    public static final Color bagBtnColor = new Color(139, 69, 19);  // สีน้ำตาล

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        this.stdScreen = new StdAuto();

        setBackground(new Color(245, 240, 240));
        setLayout(null);
        notification = new Notify(stdScreen.width);
        add(notification);

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        // ==========================================
        // 1. สร้างกล่องสถานะ (RoundedPanel)
        // ==========================================
        RoundedPanel statusPanel = new RoundedPanel(30, themePink); 
        statusPanel.setBounds(20, 60, 450, 120); 
        statusPanel.setLayout(null); 

        // Energy Bar
        energyBar = new StatusBar(100 , "Energy"); 
        energyBar.setBounds(20, 12, 410, 22); 
        statusPanel.add(energyBar);

        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 15, 200, 30); 
        lblEnergy.setVisible(false); 
        statusPanel.add(lblEnergy);

        // Money
        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblMoney.setForeground(MoneyColor);
        lblMoney.setBounds(20, 40, 200, 35); // [แก้ไข] ปรับขนาดให้พอดีไอคอน
        lblMoney.setIcon(getScaledIcon("image\\StatusBarIcon\\money.png", 32, 32)); // [เพิ่มใหม่] ใส่ไอคอนเงิน
        statusPanel.add(lblMoney);

        // Day
        lblDay = new JLabel("Day: 1");
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(20, 80, 150, 35); // [แก้ไข] ปรับขนาดให้พอดีไอคอน
        lblDay.setIcon(getScaledIcon("image\\StatusBarIcon\\day.png", 32, 32)); // [เพิ่มใหม่] ใส่ไอคอนวัน
        statusPanel.add(lblDay);

        // Time
        gTime = mainFrame.getGameTime();
        lblTime = new JLabel("Time: Morning");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 80, 200, 35); // [แก้ไข] ปรับขนาดให้พอดีไอคอน
        lblTime.setIcon(getTimeIcon(gTime.getTimeString())); // [เพิ่มใหม่] 
        statusPanel.add(lblTime);

        add(statusPanel);

    //------------------- ส่วนของปุ่มที่แสดงบนแมพ --------------
        stdScreen.setBtnWHG(200, 30, 20, 0);

        glowNeighbor = new JLabel("");
        glowNeighbor.setIcon(getScaledIcon(GetHighlightMap("Neighbor"), stdScreen.width, stdScreen.height));
        glowNeighbor.setBounds(0, 0, stdScreen.width, stdScreen.height);
        glowNeighbor.setVisible(false);
        add(glowNeighbor);

        tagNeighbor = createFloatingTag("Neighbor", neightborColor);
        tagNeighbor.setBounds(stdScreen.centerX-260, stdScreen.currentY+240, 140, 35); 
        add(tagNeighbor);

        hitboxNeighbor = new JLabel("");
        hitboxNeighbor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hitboxNeighbor.setBounds(stdScreen.centerX-280, stdScreen.currentY+200, 180, 120);
        add(hitboxNeighbor);

        glowHome = new JLabel("");
        glowHome.setIcon(getScaledIcon(GetHighlightMap("Home"), stdScreen.width, stdScreen.height));
        glowHome.setBounds(0, 0, stdScreen.width, stdScreen.height);
        glowHome.setVisible(false);
        add(glowHome);

        tagHome = createFloatingTag("Home", homeColor);
        tagHome.setBounds(stdScreen.centerX-80, stdScreen.currentY+160, 140, 35); 
        add(tagHome);

        hitboxHome = new JLabel("");
        hitboxHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hitboxHome.setBounds(stdScreen.centerX-90, stdScreen.currentY+120, 160, 100);
        add(hitboxHome);


        glowSchool = new JLabel("");
        glowSchool.setIcon(getScaledIcon(GetHighlightMap("School"), stdScreen.width, stdScreen.height));
        glowSchool.setBounds(0, 0, stdScreen.width, stdScreen.height);
        glowSchool.setVisible(false);
        add(glowSchool);

        tagSchool = createFloatingTag("School", officeColor);
        tagSchool.setBounds(stdScreen.centerX+110, stdScreen.currentY-280, 140, 35); 
        add(tagSchool);

        hitboxSchool = new JLabel("");
        hitboxSchool.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hitboxSchool.setBounds(stdScreen.centerX+100, stdScreen.currentY-300, 160, 100);
        add(hitboxSchool);

        glowShop = new JLabel("");
        glowShop.setIcon(getScaledIcon(GetHighlightMap("Shop"), stdScreen.width, stdScreen.height));
        glowShop.setBounds(0, 0, stdScreen.width, stdScreen.height);
        glowShop.setVisible(false);
        add(glowShop);

        tagShop = createFloatingTag("Shop", officeColor);
        tagShop.setBounds(stdScreen.centerX-560, stdScreen.currentY+100, 140, 35); 
        add(tagShop);

        hitboxShop = new JLabel("");
        hitboxShop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hitboxShop.setBounds(stdScreen.centerX-580, stdScreen.currentY+50, 180, 140);
        add(hitboxShop);


        glowOffice = new JLabel("");
        glowOffice.setIcon(getScaledIcon(GetHighlightMap("BlackSmith"), stdScreen.width, stdScreen.height));
        glowOffice.setBounds(0, 0, stdScreen.width, stdScreen.height);
        glowOffice.setVisible(false);
        add(glowOffice);

        tagOffice = createFloatingTag("Blacksmith", officeColor);
        tagOffice.setBounds(stdScreen.centerX + 300, stdScreen.currentY - 100, 140, 35); 
        add(tagOffice);

        hitboxOffice = new JLabel("");
        hitboxOffice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hitboxOffice.setBounds(stdScreen.centerX + 250, stdScreen.currentY - 200, 250, 300);
        add(hitboxOffice);


        btnBag = createRoundedButton("Bag");
        btnBag.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnBag, 20, 20, 100, 30, bagBtnColor); 
        add(btnBag);

        //--------------Action--------------------

        hitboxNeighbor.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Valorant - Choose Hover - Gaming Sound Effect Valorant (HD)  Sound Effects_01.wav");
                glowNeighbor.setVisible(true); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                glowNeighbor.setVisible(false); 
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                glowNeighbor.setVisible(false);
                fader.fadeInOut(250, 250, ()->{
                    parent.createNeightBorPanel();
                    parent.showNeighbor();
                }, null);
            }
        });

        hitboxHome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Valorant - Choose Hover - Gaming Sound Effect Valorant (HD)  Sound Effects_01.wav");
                glowHome.setVisible(true); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                glowHome.setVisible(false); 
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                glowHome.setVisible(false);
                fader.fadeInOut(250, 250, ()->{
                    parent.createHomePanel();
                    parent.showHome();
                }, null);
            }
        });

        hitboxSchool.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Valorant - Choose Hover - Gaming Sound Effect Valorant (HD)  Sound Effects_01.wav");
                glowSchool.setVisible(true); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                glowSchool.setVisible(false); 
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                glowSchool.setVisible(false);
                fader.fadeInOut(250, 250, ()->{
                    parent.createSchoolPanel();
                    parent.showSchool();
                }, null);
            }
        });


        hitboxShop.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Valorant - Choose Hover - Gaming Sound Effect Valorant (HD)  Sound Effects_01.wav");
                glowShop.setVisible(true); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                glowShop.setVisible(false); 
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                glowShop.setVisible(false);
                fader.fadeInOut(250, 250, ()->{
                    parent.createShopPanel();
                    parent.showShop();
                }, null);
            }
        });


        hitboxOffice.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Valorant - Choose Hover - Gaming Sound Effect Valorant (HD)  Sound Effects_01.wav");
                glowOffice.setVisible(true); 
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                glowOffice.setVisible(false); 
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                glowOffice.setVisible(false);
                fader.fadeInOut(250, 250, ()->{
                    parent.createOfficePanel(); 
                    parent.showOffice();
                }, null);
            }
        });

        btnBag.addActionListener(e -> {  
            if (currentInvPanel != null && currentInvPanel.isVisible()) {return;}
            InventoryPanel invPanel = new InventoryPanel(parent, stdScreen.width, stdScreen.height); 
            add(invPanel); 
            setComponentZOrder(invPanel, 0); 
            invPanel.setVisible(true);
            disableAllGamePanel();


            revalidate();//สั่งให้ Swing จัด Layout ทันที ของจะได้เด้งขึ้นมาเลยไม่ล่องหน!

            repaint();
        });

        lblMap = new JLabel("");  
        ChangeImageMap.updateMapImage("Morning", lblMap, checkImageUtil, stdScreen);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

        PauseMenuPanel pauseMenu = new PauseMenuPanel(mainFrame);
        pauseMenu.setVisible(false); // เริ่มมาให้ซ่อนไว้ก่อน
        add(pauseMenu);
        setComponentZOrder(pauseMenu, 0); // ดันให้อยู่หน้าสุดเสมอ จะได้บังทุกอย่างตอนกด ESC

        // ระบบจับปุ่ม ESC (Key Bindings - เสถียรกว่า KeyListener)
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "smartEsc");
        this.getActionMap().put("smartEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // 🔥 1. เช็คก่อนว่า "หน้า Option ลอยทับอยู่หรือเปล่า?"
                if (mainFrame.getOptionPanel() != null && mainFrame.getOptionPanel().isVisible()) {
                    // ถ้าลอยอยู่ -> ให้กด ESC เพื่อ "ปิดหน้า Option" อย่างเดียว
                    mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                    mainFrame.getOptionPanel().setVisible(false); 
                } else if (currentInvPanel != null && currentInvPanel.isVisible()) {
                    // ปิดหน้ากระเป๋า เคลียร์ออกจากหน้าจอ และปลดล็อคปุ่ม
                    currentInvPanel.setVisible(false);
                    remove(currentInvPanel);
                    currentInvPanel = null;
                    enableAllGamePanel(); // เปิดปุ่มต่างๆ บนแมพกลับมา
                    
                    revalidate();
                    repaint();
                }
                // 🔥 2. ถ้า Option ไม่ได้เปิดอยู่ -> ค่อยสลับเปิด/ปิด หน้า Pause ตามปกติ
                else {
                    boolean isCurrentlyVisible = pauseMenu.isVisible();
                    pauseMenu.setVisible(!isCurrentlyVisible);
                }
            }
        });

        setComponentZOrder(fader, 0);

        setComponentZOrder(tagNeighbor, 2);  
        setComponentZOrder(hitboxNeighbor, 3); 
        setComponentZOrder(glowNeighbor, 4);   

        setComponentZOrder(tagHome, 2);  
        setComponentZOrder(hitboxHome, 3); 
        setComponentZOrder(glowHome, 4);   

        setComponentZOrder(tagSchool, 2);  
        setComponentZOrder(hitboxSchool, 3); 
        setComponentZOrder(glowSchool, 4);   
        
        setComponentZOrder(tagShop, 2);  
        setComponentZOrder(hitboxShop, 3); 
        setComponentZOrder(glowShop, 4);   

        setComponentZOrder(tagOffice, 2);  
        setComponentZOrder(hitboxOffice, 3); 
        setComponentZOrder(glowOffice, 4);   
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }

    // [เพิ่มใหม่] ฟังก์ชันสำหรับโหลดและย่อขนาดรูปไอคอน
    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = Utility.AssetManager.getInstance().getImage(path);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null; // ถ้าไม่เจอรูป จะปล่อยว่างไม่แสดง Error ค้าง
    }

    // [แก้ไข] ฟังก์ชันเช็คเวลาเพื่อสลับรูปไอคอนเวลาให้ตรงกับไฟล์ที่คุณมี
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

    // Helper Method สำหรับสร้างปุ่มมน
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

    //------------update - ค่า--------------------
    public void updateUI() {
        if (parent == null ) return ;
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime(); 

        energyBar.setEnergy(player.getEnergy());
        lblEnergy.setText("Energy: " + player.getEnergy());

        lblEnergy.setForeground(Color.white);
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());
        // [เพิ่มใหม่] อัปเดตไอคอนเวลาทุกครั้งที่เวลาเดิน
        lblTime.setIcon(getTimeIcon(gTime.getTimeString()));

        Utility.ChangeImageMap.updateMapImage(gTime.getTimeString(), lblMap, checkImageUtil, stdScreen);

        if (glowNeighbor != null) glowNeighbor.setIcon(getScaledIcon(GetHighlightMap("Neighbor"), stdScreen.width, stdScreen.height));
        if (glowHome != null) glowHome.setIcon(getScaledIcon(GetHighlightMap("Home"), stdScreen.width, stdScreen.height));
        if (glowSchool != null) glowSchool.setIcon(getScaledIcon(GetHighlightMap("School"), stdScreen.width, stdScreen.height));
        if (glowShop != null) glowShop.setIcon(getScaledIcon(GetHighlightMap("Shop"), stdScreen.width, stdScreen.height));
        if (glowOffice != null) glowOffice.setIcon(getScaledIcon(GetHighlightMap("BlackSmith"), stdScreen.width, stdScreen.height));

        repaint();
    }
    
    public void updateEnergyBar() {
        Player player = parent.getPlayer();
        energyBar.setEnergy(player.getEnergy());
    }

    public boolean doActivity(int energyCost) { 
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime();
        lblEnergy.setForeground(Color.WHITE); 
        if (player.getEnergy() < 10) {
            LowEnergyPanel energyPanel = new LowEnergyPanel(stdScreen.width, stdScreen.height, parent);
            add(energyPanel);
            energyPanel.setVisible(true);
            setComponentZOrder(energyPanel, 0);
            lblEnergy.setForeground(Color.red);
            
            energyBar.setEnergy(0);
            
            return false;
        } 
        gTime.advanceTime(player,energyCost);
        updateUI();
        return true;
    }
    
    public void enableAllGamePanel() {
        btnBag.setEnabled(true);
        if(hitboxOffice != null) hitboxOffice.setVisible(true);
        if(hitboxShop != null) hitboxShop.setVisible(true);
        if(hitboxHome != null) hitboxHome.setVisible(true);
        if(hitboxSchool != null) hitboxSchool.setVisible(true);
        if(hitboxNeighbor != null) hitboxNeighbor.setVisible(true);
        if(tagOffice != null) tagOffice.setVisible(true);
        if(tagShop != null) tagShop.setVisible(true);
        if(tagHome != null) tagHome.setVisible(true);
        if(tagSchool != null) tagSchool.setVisible(true);
        if(tagNeighbor != null) tagNeighbor.setVisible(true);
    }
    
    public void disableAllGamePanel() {
        btnBag.setEnabled(false);
        if(hitboxOffice != null) hitboxOffice.setVisible(false);
        if(hitboxShop != null) hitboxShop.setVisible(false);
        if(hitboxHome != null) hitboxHome.setVisible(false);
        if(hitboxSchool != null) hitboxSchool.setVisible(false);
        if(hitboxNeighbor != null) hitboxNeighbor.setVisible(false);
        if(tagOffice != null) tagOffice.setVisible(false);
        if(tagShop != null) tagShop.setVisible(false);
        if(tagHome != null) tagHome.setVisible(false);
        if(tagSchool != null) tagSchool.setVisible(false);
        if(tagNeighbor != null) tagNeighbor.setVisible(false);
    }

    public String GetHighlightMap(String place) {
        String timestring = gTime.getTimeString();

        if (place.equalsIgnoreCase("BlackSmith")) {
            switch (timestring) {
                case "Noon":
                    return "image\\Map\\Afternoon-BlackSmith_Glow.png";
                case "Night":
                    return "image\\Map\\Night-BlackSmith_Glow.png";
                case "Morning":
                    return "image\\Map\\Morning-BlackSmith_Glow.png";
                case "Evening":
                    return "image\\Map\\Evening-BlackSmith_Glow.png";
                default:
                    return "image\\Map\\Morning-BlackSmith_Glow.png";
            }
        } else if (place.equalsIgnoreCase("Home")) {
            switch (timestring) {
                case "Noon":
                    return "image\\Map\\Afternoon-Home_Glow.png";
                case "Evening":
                    return "image\\Map\\Evening-Home_Glow.png";
                case "Night":
                    return "image\\Map\\Night-Home_Glow.png";
                case "Morning":
                    return "image\\Map\\Morning-Home_Glow.png";
                default:
                    return "image\\Map\\Morning-Home_Glow.png";
            }
        } else if (place.equalsIgnoreCase("Neighbor")) {
            switch (timestring) {
                case "Noon":
                    return "image\\Map\\Afternoon-Neighbor_Glow.png";
                case "Evening":
                    return "image\\Map\\Evening-Neighbor_Glow.png";
                case "Night":
                    return "image\\Map\\Night-Neighbor_Glow.png";
                case "Morning":
                    return "image\\Map\\Morning-Neighbor_Glow.png";
                default:
                    return "image\\Map\\Morning-Neighbor_Glow.png";
            }
        } else if (place.equalsIgnoreCase("School")) {
            switch (timestring) {
                case "Noon":
                    return "image\\Map\\Afternoon-School_Glow.png";
                case "Evening":
                    return "image\\Map\\Evening-School_Glow.png";
                case "Night":
                    return "image\\Map\\Night-School_Glow.png";
                case "Morning":
                    return "image\\Map\\Morning-School_Glow.png";
                default:
                    return "image\\Map\\Morning-School_Glow.png";
            }
        } else if (place.equalsIgnoreCase("Shop")) {
            switch (timestring) {
                case "Noon":
                    return "image\\Map\\Afternoon-Shop_Glow.png";
                case "Evening":
                    return "image\\Map\\Evening-Shop_Glow.png";
                case "Night":
                    return "image\\Map\\Night-Shop_Glow.png";
                case "Morning":
                    return "image\\Map\\Morning-Shop_Glow.png";
                default:
                    return "image\\Map\\Morning-Shop_Glow.png";
            }
        }
        return "Null";
    }
    
    private JLabel createFloatingTag(String text, Color tagColor) {
        JLabel tag = new JLabel("") {
            @Override
            public boolean contains(int x, int y) {
                return false; 
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // จัดตำแหน่งตัวหนังสือ
                g2.setFont(new Font("Tahoma", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                int textX = (w - fm.stringWidth(text)) / 2;
                int textY = ((h - fm.getHeight()) / 2) + fm.getAscent();

                // เงาตัวหนังสือสีดำ (ช่วยให้อ่านง่ายขึ้นเวลาฉากหลังสว่าง)
                g2.setColor(new Color(0, 0, 0, 150)); 
                g2.drawString(text, textX + 2, textY + 2);

                // ตัวหนังสือสีขาว
                g2.setColor(Color.WHITE);
                g2.drawString(text, textX, textY);
            }
        };
        return tag;
    }
}