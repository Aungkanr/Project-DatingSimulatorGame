package UXUI.Scene;
import UXUI.MainFrame;
import Utility.GameTime;
import Utility.Sleep;
import Player.Player;
import Utility.StdAuto;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private StdAuto stdScreen = new StdAuto() ;
    private Sleep sleepEffect = new Sleep() ;
    private MainFrame parent;
    private JLabel lblMessage; // <--- 1. ตัวแปรสำหรับโชว์ข้อความเตือน
    private JButton btnBack; // <--- 2. ตัวแปรปุ่มกลับ (ถ้าต้องการเข้าถึงจากหลายที่)
    
    public HomePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        stdScreen.setBtnWHG(200, 60, 20 ,0); //ขนาด ปุ่ม และ gap ,แถว
        
        setLayout(null);
        setBackground(new Color(12, 51, 204));
        stdScreen.setBtnWHG(200, 60, 20, 0); 

        // --- สร้าง Label ข้อความเตือน (ซ่อนไว้ก่อน หรือเขียนว่างๆ ไว้) ------
        lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblMessage.setForeground(Color.RED); 
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessage.setBounds(stdScreen.centerX - 100, stdScreen.currentY - 50, stdScreen.buttonWidth + 200, 40);
        add(lblMessage);

        // --- ปุ่ม Sleep ----------------------------------------
        JButton btnSleep = new JButton("Sleep (Next Day)");
        btnSleep.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSleep.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameTime gameTime = parent.getGameTime();
                Player player = parent.getPlayer();
                if (gameTime.isNight_Afternoon()) { // ถ้าเป็นกลางคืน -> นอนได้
                    gameTime.nextDay();
                    btnSleep.setVisible(false); // ซ่อนปุ่มนอนหลับระหว่างที่กำลังนอน
                    btnBack.setVisible(false); // ซ่อนปุ่มกลับระหว่างที่กำลังนอน
                    sleepEffect.startSleepSequence();
                    sleepEffect.setBounds(0, 0, stdScreen.width, stdScreen.height);
                    add(sleepEffect);
                    setComponentZOrder(sleepEffect, 0);
                    player.setEnergy(100);  // restore Energy

                    new javax.swing.Timer(4000, new ActionListener() { // การทำงานหลังจากผ่านไป 4 วินาที (เวลาที่นอนหลับ) **คล้ายๆ Thread.sleep
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnSleep.setVisible(true); // แสดงปุ่มนอนหลับหลังจากที่ตื่น
                            btnBack.setVisible(true); // แสดงปุ่มกลับหลังจากที่ตื่น
                            lblMessage.setText(""); // ล้างข้อความเตือน    
                            lblMessage.setForeground(Color.GREEN);
                            lblMessage.setText("Next Day : " + gameTime.getDay());
                            
                            // อย่าลืมสั่งหยุด Timer
                            ((javax.swing.Timer)e.getSource()).setRepeats(false);
                        }
                    }).start();
                } else {
                    // ถ้า "ไม่ใช่" 
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("ยังไม่มืดเลย!");
                    // fix
                }
            }
        });
        add(btnSleep);

        // --- ปุ่ม return ----------------------------------------
        btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> {
            lblMessage.setText(""); 
            parent.showGame();
        });
        add(btnBack);
    }
}
