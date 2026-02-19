package UXUI.StatusBarMenu;

import Player.Player;
import UXUI.Hovereffect;
import UXUI.LowEnergyPanel;
import UXUI.MainFrame;
import Utility.*;

// เพิ่ม Import สำหรับวาดกราฟิก และ Image
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image; // [เพิ่มใหม่] สำหรับจัดการย่อขนาดรูปภาพ
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon; // [เพิ่มใหม่]
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    private JButton btnOffice;
    private JButton btnShop;
    private JButton btnHome;
    private JButton btnSchool;
    private JButton btnNeighbor;
    private JButton btnExitGame;

    // ------------------ Object ---------------------
    Utility.AssetManager asset = Utility.AssetManager.getInstance();
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    ScreenFader fader = new ScreenFader();
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
        GameTime gTime = mainFrame.getGameTime(); 
        lblTime = new JLabel("Time: Morning");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 80, 200, 35); // [แก้ไข] ปรับขนาดให้พอดีไอคอน
        lblTime.setIcon(getTimeIcon(gTime.getTimeString())); // [เพิ่มใหม่] 
        statusPanel.add(lblTime);

        add(statusPanel);

        // ==========================================
        // 2. ปุ่ม Exit Game
        // ==========================================
        btnExitGame = createRoundedButton("Return to Menu");
        Hovereffect.HoverEffectRounded(btnExitGame, 20, 20, 150, 30, ExitGameColor);        
        add(btnExitGame);
        
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                fader.fadeInOut(500, 500, ()->{parent.showMenu();}, null);
            }
        });

    //------------------- ส่วนของปุ่มที่แสดงบนแมพ --------------
        stdScreen.setBtnWHG(200, 30, 20, 0);

        btnSchool = createRoundedButton("School");
        btnSchool.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnSchool, stdScreen.centerX+80, stdScreen.currentY-280, stdScreen.buttonWidth, stdScreen.buttonHeight, schoolColor);
        add(btnSchool);

        btnHome = createRoundedButton("Home");
        btnHome.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnHome, stdScreen.centerX-20, stdScreen.currentY+180, stdScreen.buttonWidth, stdScreen.buttonHeight, homeColor);
        add(btnHome);

        btnShop = createRoundedButton("Shop");
        btnShop.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnShop, stdScreen.centerX-380, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight, shopColor);
        add(btnShop);

        btnOffice = createRoundedButton("Office");
        btnOffice.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnOffice, stdScreen.centerX+300, stdScreen.currentY-100, stdScreen.buttonWidth, stdScreen.buttonHeight, officeColor);
        add(btnOffice);

        btnNeighbor = createRoundedButton("Neighbor");
        btnNeighbor.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnNeighbor, stdScreen.centerX-300, stdScreen.currentY+240, stdScreen.buttonWidth, stdScreen.buttonHeight, neightborColor);
        add(btnNeighbor);
        
        btnBag = createRoundedButton("Bag");
        btnBag.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffectRounded(btnBag, 180, 20, 100, 30, bagBtnColor); 
        add(btnBag);

        //--------------Action--------------------
        btnSchool.addActionListener(e ->  {
            fader.fadeInOut(500, 500, ()->{parent.createSchoolPanel(); parent.showSchool();}, null);
        });

        btnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fader.fadeInOut(500, 500, ()->{parent.createHomePanel(); parent.showHome();}, null);
            }
        });

        btnShop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fader.fadeInOut(500, 500, ()->{parent.createShopPanel(); parent.showShop();}, null);
            }
        });

        btnOffice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fader.fadeInOut(500, 500, ()->{parent.createOfficePanel(); parent.showOffice();}, null);
            }
        });
        
        btnNeighbor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fader.fadeInOut(500, 500, ()->{parent.createNeightBorPanel(); parent.showNeighbor();}, null);
            }
        });

        btnBag.addActionListener(e -> {  
            InventoryPanel invPanel = new InventoryPanel(parent, stdScreen.width, stdScreen.height); 
            add(invPanel); 
            setComponentZOrder(invPanel, 0); 
            invPanel.setVisible(true);
            disableAllGamePanel();
            repaint();
        });

        lblMap = new JLabel("");  
        ChangeImageMap.updateMapImage("Morning", lblMap, checkImageUtil, stdScreen);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
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

        ChangeImageMap.updateMapImage(gTime.getTimeString(), lblMap, checkImageUtil, stdScreen);
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
    
    public void enableAllGamePanel () {
        btnBag.setEnabled(true);
        btnOffice.setEnabled(true);
        btnShop.setEnabled(true);
        btnHome.setEnabled(true);
        btnSchool.setEnabled(true);
        btnNeighbor.setEnabled(true);
        btnExitGame.setEnabled(true);
    }
    public void disableAllGamePanel() {
        btnBag.setEnabled(false);
        btnOffice.setEnabled(false);
        btnShop.setEnabled(false);
        btnHome.setEnabled(false);
        btnSchool.setEnabled(false);
        btnNeighbor.setEnabled(false);
        btnExitGame.setEnabled(false);
    }
}