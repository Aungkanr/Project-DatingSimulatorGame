package UXUI;
import Player.Player;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon; 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MainFrame parent;
    private JLabel lblEnergy;
    private JLabel lblMoney;
    private JLabel lblDay;
    private JLabel lblTime;
    private StdAuto stdScreen ; //Device screen
    private Notify notification ; //ตัวแจ้งเตือน

    // ------------------ Object ---------------------
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    // ------------------ สีปุ่ม ---------------------
    Color ExitGameColor = new Color(48, 25, 82);    
    Color MoneyColor = new Color(255, 215, 0);
    Color schoolColor = new Color(41, 128, 185);     
    Color homeColor = new Color(230, 126, 34);        
    Color shopColor = new Color(46, 204, 113);        
    Color officeColor = new Color(142, 68, 173);      

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        stdScreen = new StdAuto();

        setBackground(Color.DARK_GRAY);
        setLayout(null);

        notification = new Notify(stdScreen.width);
        add(notification);

        // --- ส่วนแสดงสถานะ (HUD) ---
        // --------------- show status player ----------------
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 60, 300, 30);
        add(lblEnergy);

        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMoney.setForeground(MoneyColor);
        lblMoney.setBounds(20, 90, 300, 30);
        add(lblMoney);

        lblDay = new JLabel("Day: 1");
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(250, 60, 150, 30);
        add(lblDay);

        lblTime = new JLabel("Time: Morning");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 90, 200, 30); 
        add(lblTime);

        JButton btnExitGame = new JButton("Return to Menu");
        btnExitGame.setBounds(20, 20, 150, 30);
        Hovereffect.HoverEffect(btnExitGame, 20, 20, 150, 30, ExitGameColor);        
        add(btnExitGame);
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        add(btnExitGame);
        // เพิ่ม Hover Effect ให้กับปุ่มแต่ละตัว (แทนที่โค้ดเดิมในส่วนปุ่ม)

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

        btnSchool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.createSchoolPanel();
                parent.showSchool();
            }
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


//--------------------------image------------------
        JLabel lblMap = new JLabel("");

        String imagePath = "image\\Map.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
    }
//--------------------------image------------------
    //------------update - ค่า--------------------
    public void updateUI() {
        if (parent == null ) return ;
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime(); // เรียก Time มา

        lblEnergy.setText("Energy: " + player.getEnergy());
        lblEnergy.setForeground(Color.white);
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());
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
            return false;
        } 
        gTime.advanceTime(player,energyCost);// ถ้า Time อยู่ที่ Night ให้ค้างที่ Night wait untill click Sleep.
        updateUI();
        return true;
    }
}