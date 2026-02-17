package UXUI.StatusBarMenu;
import Player.Player;
import UXUI.Hovereffect;
import UXUI.LowEnergyPanel;
import UXUI.MainFrame;
import Utility.ChangeImageMap;
import Utility.GameTime;
import Utility.Notify;
import Utility.StatusBar;
import Utility.StdAuto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private ChangeImageMap mapChanger;
    private StdAuto stdScreen ; //Device screen
    private Notify notification ; //ตัวแจ้งเตือน

    // ------------------ Object ---------------------
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    // เพิ่มใหม่ ธีมสีชมพู (Pink Theme)
    public static final Color themePink = new Color(30, 25, 50, 220);      // ม่วงเข้มโปร่งแสง
    public static final Color themeBorder = new Color(100, 150, 255, 180); // ฟ้าเรืองแสง

    // ------------------ สีปุ่ม ---------------------
    public static final Color ExitGameColor = new Color(48, 25, 82);    
    //Color MoneyColor = new Color(255, 215, 0);
    public static final Color MoneyColor = new Color(255, 215, 80);  // เหลืองทองสว่าง
    public static final Color schoolColor = new Color(41, 128, 185);     
    public static final Color homeColor = new Color(230, 126, 34);        
    public static final Color shopColor = new Color(46, 204, 113);        
    public static final Color officeColor = new Color(142, 68, 173);    
    public static final Color bagBtnColor = new Color(139, 69, 19);  // สีน้ำตาล

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        stdScreen = new StdAuto();

        setBackground(new Color(245, 240, 240));//add
        //setBackground(Color.DARK_GRAY);
        setLayout(null);
        notification = new Notify(stdScreen.width);
        add(notification);

        notification = new Notify(stdScreen.width);
        add(notification);

        // ==========================================
        // 1. สร้างกล่องสถานะ (RoundedPanel)
        // ==========================================
        // สร้าง Panel สีชมพูขึ้นมา
        RoundedPanel statusPanel = new RoundedPanel(30, themePink); 
        statusPanel.setBounds(20, 60, 450, 120); // ตำแหน่งกล่องบนหน้าจอ (เพิ่มความสูงเป็น 120)
        statusPanel.setLayout(null); // จัดวางของในกล่องเอง

        // --- ยัด Label เข้าไปใน statusPanel ---
        
        // Energy Bar (หลอดพลังงาน - เพิ่มใหม่)
        energyBar = new StatusBar(100 , "Energy"); // สร้างหลอด Energy ที่มี max = 100
        energyBar.setBounds(20, 12, 410, 22); // พิกัดเทียบกับกล่องชมพู
        statusPanel.add(energyBar);

        // Energy (Label เก็บไว้แต่ซ่อน)
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 15, 200, 30); // พิกัดเทียบกับกล่องชมพู
        lblEnergy.setVisible(false); // ซ่อนเพราะมี EnergyBar แล้ว
        statusPanel.add(lblEnergy);

        // Money
        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblMoney.setForeground(MoneyColor);
        lblMoney.setBounds(20, 45, 200, 25);
        statusPanel.add(lblMoney);

        // Day (อยู่ขวาบนของกล่อง)
        lblDay = new JLabel("Day: 1");
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(20, 75, 150, 25); // ย้ายลงมา
        statusPanel.add(lblDay);

        // Time (อยู่ขวาล่างของกล่อง)
        lblTime = new JLabel("Time: Morning");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 75, 200, 25); // ย้ายข้างๆ Day
        statusPanel.add(lblTime);

        // นำกล่อง statusPanel ไปแปะบนหน้าจอหลัก
        add(statusPanel);

        // ==========================================
        // 2. ปุ่ม Exit Game
        // ==========================================
        JButton btnExitGame = new JButton("Return to Menu");
        btnExitGame.setBounds(20, 20, 150, 30);
        Hovereffect.HoverEffect(btnExitGame, 20, 20, 150, 30, ExitGameColor);        
        add(btnExitGame);
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });

    //------------------- ส่วนของปุ่มที่แสดงบนแมพ--------------
        stdScreen.setBtnWHG(200, 30, 20, 0);

        JButton btnSchool = new JButton("School");
        btnSchool.setBounds(stdScreen.centerX-20, stdScreen.currentY-280, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnSchool.setBackground(schoolColor);
        btnSchool.setForeground(Color.WHITE);
        btnSchool.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffect(btnSchool, stdScreen.centerX-20, stdScreen.currentY-280, stdScreen.buttonWidth, stdScreen.buttonHeight, schoolColor);
        add(btnSchool);

        JButton btnHome = new JButton("Home");
        btnHome.setBounds(stdScreen.centerX-20, stdScreen.currentY+140, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnHome.setBackground(homeColor);
        btnHome.setForeground(Color.WHITE);
        btnHome.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffect(btnHome, stdScreen.centerX-20, stdScreen.currentY+140, stdScreen.buttonWidth, stdScreen.buttonHeight, homeColor);
        add(btnHome);

        JButton btnShop = new JButton("Shop");
        btnShop.setBounds(stdScreen.centerX-380, stdScreen.currentY-40, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnShop.setBackground(shopColor);
        btnShop.setForeground(Color.WHITE);
        btnShop.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffect(btnShop, stdScreen.centerX-380, stdScreen.currentY-40, stdScreen.buttonWidth, stdScreen.buttonHeight, shopColor);
        add(btnShop);

        JButton btnOffice = new JButton("Office");
        btnOffice.setBounds(stdScreen.centerX+340, stdScreen.currentY-100, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnOffice.setBackground(officeColor);
        btnOffice.setForeground(Color.WHITE);
        btnOffice.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffect(btnOffice, stdScreen.centerX+340, stdScreen.currentY-100, stdScreen.buttonWidth, stdScreen.buttonHeight, officeColor);
        add(btnOffice);
        
        JButton btnBag = new JButton("Bag");
        btnBag.setBounds(180, 20, 100, 30); // วางข้างๆ ปุ่ม Return Menu
        btnBag.setFont(new Font("Tahoma", Font.BOLD, 14));
        Hovereffect.HoverEffect(btnBag, 180, 20, 100, 30, bagBtnColor); // ใช้ HoverEffect สีน้ำตาล
        add(btnBag);
        //--------------Action--------------------
        btnSchool.addActionListener(e ->  {
            parent.createSchoolPanel();
            parent.showSchool();
        });

        btnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.createHomePanel();
                parent.showHome();
            }
        });

        btnShop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.createShopPanel();
                parent.showShop();
            }
        });

        btnOffice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.createOfficePanel();
                parent.showOffice();
            }
        });

        btnBag.addActionListener(e -> {  //-------inventory----------------
            InventoryPanel invPanel = new InventoryPanel(parent, stdScreen.width, stdScreen.height); // 1. สร้าง InventoryPanel
            
            add(invPanel); // 2. แปะลงไปใน GamePanel
            
            setComponentZOrder(invPanel, 0); // 3. ดึงมาหน้าสุด (Layer 0) และสั่งวาดใหม่
            invPanel.setVisible(true);
            repaint();
        });
    //--------------------------image Map update ตามเวลา (โดยส่งข้อมูล StringของGame Time ไป)------------------
        lblMap = new JLabel("");  
        ChangeImageMap.updateMapImage("Morning", lblMap, checkImageUtil, stdScreen);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
    }
    //------------update - ค่า--------------------
    public void updateUI() {
        if (parent == null ) return ;
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime(); // เรียก Time มา

        // อัพเดทหลอด Energy and Money
        energyBar.setEnergy(player.getEnergy());


        lblEnergy.setText("Energy: " + player.getEnergy());
        lblEnergy.setForeground(Color.white);
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());
        ChangeImageMap.updateMapImage(gTime.getTimeString(), lblMap, checkImageUtil, stdScreen);
    }
    
    // method energy bar //
    public void updateEnergyBar() {
        Player player = parent.getPlayer();
        energyBar.setEnergy(player.getEnergy());
    }

    public boolean doActivity(int energyCost) { 
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime();
        lblEnergy.setForeground(Color.WHITE); // Reset สี Energy ก่อนตรวจสอบ
        if (player.getEnergy() < 10) {
            // ... code แจ้งเตือน Energy หมด ...
            LowEnergyPanel energyPanel = new LowEnergyPanel(stdScreen.width, stdScreen.height, parent);
            add(energyPanel);
            energyPanel.setVisible(true);
            setComponentZOrder(energyPanel, 0);
            lblEnergy.setForeground(Color.red);
            
            // อัพเดทหลอด Energy เป็น 0
            energyBar.setEnergy(0);
            
            return false;
        } 
        gTime.advanceTime(player,energyCost);// ถ้า Time อยู่ที่ Night ให้ค้างที่ Night wait untill click Sleep.
        updateUI();
        return true;
    }
    
}