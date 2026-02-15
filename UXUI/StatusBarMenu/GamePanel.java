package UXUI.StatusBarMenu;
import Player.Player;
import UXUI.Hovereffect;
import UXUI.MainFrame;
import Utility.ChangeImageMap;
import Utility.GameTime;
import Utility.Notify;
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
    private JLabel lblEnergy;
    private JLabel lblMoney;
    private JLabel lblDay;
    private JLabel lblTime;
    private JLabel lblMap;
    private ChangeImageMap mapChanger;  
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    private StdAuto stdScreen ; //Device screen
    private Notify notification ; //ตัวแจ้งเตือน

    // เพิ่มใหม่ ธีมสีชมพู (Pink Theme)
    Color themePink = new Color(219, 134, 163); // add
    Color themeBorder = Color.WHITE;// add

    // ------------------ สีปุ่ม ---------------------
    Color ExitGameColor = new Color(48, 25, 82);    
    //Color MoneyColor = new Color(255, 215, 0);
    Color MoneyColor = new Color(255, 223, 0); // ปรับเหลืองให้สว่างขึ้นบนพื้นชมพู
    Color schoolColor = new Color(41, 128, 185);     
    Color homeColor = new Color(230, 126, 34);        
    Color shopColor = new Color(46, 204, 113);        
    Color officeColor = new Color(142, 68, 173);      

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        stdScreen = new StdAuto();

        setBackground(new Color(245, 240, 240));//add
        //setBackground(Color.DARK_GRAY);
        setLayout(null);
        notification = new Notify(stdScreen.width);
        add(notification);

        // ==========================================
        // 1. สร้างกล่องสถานะ (RoundedPanel)
        // ==========================================
        // สร้าง Panel สีชมพูขึ้นมา
        RoundedPanel statusPanel = new RoundedPanel(30, themePink); 
        statusPanel.setBounds(20, 60, 450, 110); // ตำแหน่งกล่องบนหน้าจอ
        statusPanel.setLayout(null); // จัดวางของในกล่องเอง

        // --- ยัด Label เข้าไปใน statusPanel ---
        
        // Energy
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 15, 200, 30); // พิกัดเทียบกับกล่องชมพู
        statusPanel.add(lblEnergy);

        // Money
        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMoney.setForeground(MoneyColor);
        lblMoney.setBounds(20, 55, 200, 30);
        statusPanel.add(lblMoney);

        // Day (อยู่ขวาบนของกล่อง)
        lblDay = new JLabel("Day: 1");
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(250, 15, 150, 30);
        statusPanel.add(lblDay);

        // Time (อยู่ขวาล่างของกล่อง)
        lblTime = new JLabel("Time: Morning");
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 55, 200, 30); 
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


    //--------------------------image Map update ตามเวลา (โดยส่งข้อมูล StringของGame Time ไป)------------------
        lblMap = new JLabel("");  
        ChangeImageMap.updateMapImage("Morning", lblMap, checkImageUtil, stdScreen);
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
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());

        mapChanger.updateMapImage(gTime.getTimeString() , lblMap , checkImageUtil , stdScreen);
    }
    public boolean doActivity(int energyCost) { 
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime(); 

        if (player.getEnergy() < 10) {
            notification.show("Energy is too low!", Color.RED);
            return false;
        } 
        gTime.advanceTime(player,energyCost);// ถ้า Time อยู่ที่ Night ให้ค้างที่ Night wait untill click Sleep.
        updateUI();
        return true;
    }
    
}