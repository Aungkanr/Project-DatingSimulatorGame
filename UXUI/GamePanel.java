package UXUI;
import Player.Player;
import Utility.GameTime;
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
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    private StdAuto stdScreen ; //Device screen

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        stdScreen = new StdAuto() ;

        setBackground(Color.DARK_GRAY);
        setLayout(null);

        // --- ส่วนแสดงสถานะ (HUD) ---
        // --------------- show status player ----------------
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 60, 300, 30);
        add(lblEnergy);

        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMoney.setForeground(Color.WHITE);
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
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        add(btnExitGame);

        //------------------- ส่วนของปุ่มที่เเสดงบนเเมพ--------------
        stdScreen.setBtnWHG(200, 30, 20,0); //ขนาด ปุ่ม และ gap ,แถว
        JButton btnSchool = new JButton("School");
        btnSchool.setBounds(stdScreen.centerX-20, stdScreen.currentY-280, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnSchool);
        JButton btnHome = new JButton("Home");
        btnHome.setBounds(stdScreen.centerX-20, stdScreen.currentY+140, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnHome);
        JButton btnShop = new JButton("Shop");
        btnShop.setBounds(stdScreen.centerX-380, stdScreen.currentY-40, stdScreen.buttonWidth, stdScreen.buttonHeight);
        add(btnShop);
        JButton btnOffice = new JButton("Office ");
        btnOffice.setBounds(stdScreen.centerX+340, stdScreen.currentY-100, stdScreen.buttonWidth, stdScreen.buttonHeight);
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
        lblMoney.setText("Money: " + player.getMoney());
        lblDay.setText("Day: " + gTime.getDay());
        lblTime.setText("Time: " + gTime.getTimeString());
    }
    public boolean doActivity(int energyCost) { 
        Player player = parent.getPlayer();
        GameTime gTime = parent.getGameTime(); 

        if (player.getEnergy() < 10) {
            // ... code แจ้งเตือน Energy หมด ...
            return false;
        } 
        gTime.advanceTime(player,energyCost);// ถ้า Time อยู่ที่ Night ให้ค้างที่ Night wait untill click Sleep.
        updateUI();
        return true;
    }
}