package UXUI; 

import Player.Player; 
import java.awt.Color;
import java.awt.Font; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;



public class MenuPanel extends JPanel {
    
    private MainFrame parent; // ตัวแปรเก็บตัวแม่

    public MenuPanel(MainFrame mainFrame) {
        this.parent = mainFrame; // จำไว้ว่าใครคือตัวแม่
        
        setBackground(new Color(173, 216, 230)); // สีฟ้าอ่อน
        setLayout(null);

        JLabel lblHome = new JLabel("หน้าเมนูหลัก");
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHome.setHorizontalAlignment(SwingConstants.CENTER);
        lblHome.setBounds(640, 118, 200, 50);
        add(lblHome);

        // ปุ่ม START
        JButton btnStart = new JButton("START");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showGame(); // สั่งตัวแม่ให้เปลี่ยนไปหน้า Game
            }
        });
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStart.setBounds(640, 200, 200, 40);
        add(btnStart);
        
        // ปุ่ม SETTING
        JButton btnSetting = new JButton("SETTING");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showOption(); // สั่งตัวแม่ให้เปลี่ยนไปหน้า Option
            }
        });
        btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSetting.setBounds(640, 262, 200, 40);
        add(btnSetting);
        
        // ปุ่ม EXIT
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnExit.setBounds(640, 325, 200, 40);
        add(btnExit);
    }
}