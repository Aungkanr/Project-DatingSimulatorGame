package UXUI.Scene;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UXUI.DialoguePanel; // เพิ่ม import
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.StdAuto;
import Player.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfficePanel extends JPanel {
    
    private StdAuto stdScreen;
    private Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    private DialoguePanel dialogueBox = new DialoguePanel(); // เพิ่มกล่องข้อความ

    public OfficePanel(MainFrame mainFrame) {
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน

        GamePanel realGamePanel = mainFrame.getGamePanel(); 
        Player realPlayer = mainFrame.getPlayer();

        setLayout(null);
        setBackground(new Color(12, 51, 204));
        
        // ใช้ค่า Y ที่ปลอดภัย
        int btnY = stdScreen.bottomY;

        // --- ปุ่มขวา (Give Job Application) ---
        JButton btnchoice1 = new JButton("Give Job Application");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 14)); // ลดฟอนต์นิดนึงถ้ายาว
        btnchoice1.setBounds(stdScreen.centerX + 150, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                if (realGamePanel != null) {
                    realGamePanel.doActivity(40);
                }
            }
        });
        add(btnchoice1);

        // --- ปุ่มซ้าย (I need to work here) ---
        JButton btnchoice2 = new JButton("I need to work here man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnchoice2.setBounds(stdScreen.centerX - 150 - stdScreen.buttonWidth, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> mainFrame.showGame());
        add(btnchoice2);   
        
        // --- กล่องข้อความ (Dialogue Box) ---
        int dialogueW = 800;
        int dialogueH = 150;
        dialogueBox.setBounds((stdScreen.width - dialogueW)/2, btnY - dialogueH - 30, dialogueW, dialogueH);
        add(dialogueBox);
        dialogueBox.setText("Manager", "เรากำลังรับสมัครพนักงานพอดี สนใจไหม?"); // ใส่ข้อความตัวอย่าง

        // --- Background Image ---
        JLabel lblMap = new JLabel("");
        // *อย่าลืมเช็คชื่อไฟล์รูปภาพของคุณว่าชื่ออะไร* (ผมสมมติว่าเป็น OfficeScene.png)
        String imagePath = "image\\Scene\\Office\\OfficeScene.png"; 
        ImageIcon originalIcon = new ImageIcon(imagePath);
        
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        
        // บังคับให้รูปอยู่หลังสุด
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}


/* 
package UXUI.Scene;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.StdAuto;

import UXUI.DialoguePanel; // เพิ่ม import
import javax.swing.JButton;
import javax.swing.JPanel;

import Player.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfficePanel extends JPanel {
    private StdAuto stdScreen ;
    
    public OfficePanel(MainFrame mainFrame ) {
        stdScreen = new StdAuto() ;
        GamePanel realGamePanel = mainFrame.getGamePanel(); // ---update UI and doActivity
        Player realPlayer = mainFrame.getPlayer();

        stdScreen.setBtnWHG(200, 60, 20, 0); //ขนาด ปุ่ม และ gap ,แถว
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Give Job Application");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX-200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                realGamePanel.doActivity(40);
            }
        });
        add(btnchoice1);

        JButton btnchoice2 = new JButton("I need to work here man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(stdScreen.centerX+200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice2);   
    }
}

*/