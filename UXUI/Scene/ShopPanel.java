package UXUI.Scene;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Player.Player;
import UXUI.DialoguePanel;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import UXUI.StatusBarMenu.RoundedPanel;
import Utility.ConfirmPanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopPanel extends JPanel {
    private StdAuto stdScreen;
    private Notify shopNotify;
    private JLabel lblMoney;
    private JLabel lblEnergy;
    private JLabel lblDay;
    private JLabel lblTime;
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    DialoguePanel dialogueBox = new DialoguePanel();

    private MainFrame mainFrame ;
    ConfirmPanel confirmPanel = new ConfirmPanel(); //pull data จาก Confir Panel มาใช้ 
    
    public ShopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame ;

        GameTime gameTime = mainFrame.getGameTime(); 
        Player player = mainFrame.getPlayer();

        stdScreen = new StdAuto() ;
        stdScreen.setBtnWHG(250, 60, 20,0); //ขนาด ปุ่ม และ gap , แถว
        setLayout(null);
        setBackground(new Color(12, 51, 204));

    // -------------ConfirmPanel-----------------
        add(confirmPanel);  //ไป setting scale, height width  ที่ ConfrimPanel.java
    //----------------ข้อความเตื่อน-----------------
        shopNotify = new Notify(stdScreen.width);
        shopNotify.setBounds(0, 50, stdScreen.width, 50); // *อยากเปลี่ยนตำแหน่งก็ได้*
        add(shopNotify);
    // ----------------Status Energy Money Day Time -----------------------------------
        RoundedPanel statusPanel = new RoundedPanel(30, GamePanel.themePink); 
        statusPanel.setBounds(20, 60, 450, 110); // ตำแหน่งกล่องบนหน้าจอ
        statusPanel.setLayout(null);

        // Energy
        lblEnergy = new JLabel("Energy: " + player.getEnergy());
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 15, 200, 30); // พิกัดเทียบกับกล่องชมพู
        //money
        Player initialPlayer = mainFrame.getPlayer(); 
        lblMoney = new JLabel("Money: " + initialPlayer.getMoney());
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMoney.setForeground(GamePanel.MoneyColor);
        lblMoney.setBounds(20, 55, 200, 30);
        // Day (อยู่ขวาบนของกล่อง)
        lblDay = new JLabel("Day: " + gameTime.getDay());
        lblDay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDay.setForeground(Color.WHITE);
        lblDay.setBounds(250, 15, 150, 30);
        // Time (อยู่ขวาล่างของกล่อง)
        lblTime = new JLabel("Time: " + gameTime.getTimeString());
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setBounds(250, 55, 200, 30); 
        
        statusPanel.add(lblEnergy);
        statusPanel.add(lblMoney);
        statusPanel.add(lblDay);
        statusPanel.add(lblTime);
        add(statusPanel);
    //---------------------------dialogueBox---------------------------
        dialogueBox.setBounds(stdScreen.centerX-200, stdScreen.currentY+200, stdScreen.buttonWidth+400, stdScreen.buttonHeight+100);
        add(dialogueBox);
        if (gameTime.getTimeSlot() < 3) {
            dialogueBox.setText("Florist", "Welcome to Diddy shop! What do you want to buy?");
        } else dialogueBox.setText("Florist", "The shop was closed.!!!!");
        
    //---------------------------choice 1---------------------------
        JButton btnchoice1 = new JButton("Blue jazz $50.");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX-400, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameTime.getTimeSlot() < 3) {
                    // เรียกใช้ ConfirmPanel
                    confirmPanel.showConfirm("Buy Blue jazz $50.?", new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // โค้ดนี้จะทำงานเมื่อกด Yes เท่านั้น
                            Detect("Blue jazz", 50); 
                        }
                    });
                } else shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED ,2000);
            }
        });
        add(btnchoice1);
    //---------------------------choice 2---------------------------
        JButton btnchoice2 = new JButton("Poppy $65.");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(stdScreen.centerX-200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameTime.getTimeSlot() < 3) {
                    confirmPanel.showConfirm("Buy Poppy $65.?", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // โค้ดนี้จะทำงานเมื่อกด Yes เท่านั้น
                        Detect("Poppy", 65); 
                    }
                });
                } else shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED ,2000);
            }
        });
        add(btnchoice2);
    //---------------------------choice 3---------------------------
        JButton btnchoice3 = new JButton("Tulip $90.");
        btnchoice3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice3.setBounds(stdScreen.centerX+200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameTime.getTimeSlot() < 3) {
                    confirmPanel.showConfirm("Buy Tulip $90.?", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // โค้ดนี้จะทำงานเมื่อกด Yes เท่านั้น
                        Detect("Tulip", 90); 
                    }
                });
                } else shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED ,2000);

                }
        });
        add(btnchoice3);
    //---------------------------choice 4---------------------------
        JButton btnchoice4 = new JButton("Fairy rose $120.");
        btnchoice4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice4.setBounds(stdScreen.centerX+400, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameTime.getTimeSlot() < 3) {
                    confirmPanel.showConfirm("Buy Fairy rose $120.?", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // โค้ดนี้จะทำงานเมื่อกด Yes เท่านั้น
                        Detect("Fairy rose", 120); 
                    }});
                } else shopNotify.showNotify("Night has fallen, go to sleep.", Color.RED ,2000);
            }
        });
        add(btnchoice4);
    //---------------------------Exit---------------------------------------
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice2);   

        dialogueBox.setBounds(stdScreen.centerX-700, stdScreen.currentY+ 90 , stdScreen.buttonWidth+1425, stdScreen.buttonHeight+200);
        add(dialogueBox);
        dialogueBox.setText("Diddy", "Welcome to Diddy shop! What do you want to buy?");


        // Background หน้าร้าน
        add(btnBack);   
    //---------------------------Background หน้าร้าน---------------------------
        JLabel lblMap = new JLabel("");

        String imagePath = "image\\Scene\\Shop\\ShopScene1.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        //layer
        setComponentZOrder(confirmPanel, 0); 
        setComponentZOrder(shopNotify, 1); 
    }
    //------functiom ซื้อของ------------------------
    public void Detect (String item , int price) { //  code ซื้อของ
        
        Player realPlayer = mainFrame.getPlayer(); // PULL data from Player มาใช้ (getMoney ,  buyItem )
        if (realPlayer.getMoney() < price) {
            shopNotify.showNotify("Not enough money!", Color.RED , 2000);
            dialogueBox.setText("Florist", "No money? Get out!");
        } else {
            boolean success = realPlayer.buyItem(item, price); 
        
            if (success) { // ถ้าซื้อสำเร็จ
                shopNotify.showNotify("Purchased " + item + "!", Color.GREEN , 2000); 
                dialogueBox.setText("Florist", "Thanks for buying " + item);
                // 3. *** อัปเดตตัวเลขเงินในหน้า Shop โดยตรง ***
                lblMoney.setText("Money: " + realPlayer.getMoney());
                // บังคับวาดหน้าจอใหม่ทันที 
                lblMoney.repaint();
            }
        }
    }
}