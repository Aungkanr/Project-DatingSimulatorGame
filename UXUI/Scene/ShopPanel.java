package UXUI.Scene;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UXUI.DialoguePanel;
import UXUI.MainFrame;
import Utility.StdAuto;

import java.awt.Color;
import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class ShopPanel extends JPanel {
    private StdAuto stdScreen;
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    DialoguePanel dialogueBox = new DialoguePanel();

    public ShopPanel(MainFrame mainFrame) {
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่ม
        
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        // [แก้] ใช้ bottomY (ความสูงจอ - 120px)
        int btnY = stdScreen.bottomY;

        // ปุ่มขวา
        JButton btnchoice1 = new JButton("Buy Item");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX + 150, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> mainFrame.showGame());
        add(btnchoice1);

        // ปุ่มซ้าย
        JButton btnchoice2 = new JButton("Give me money!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(stdScreen.centerX - 150 - stdScreen.buttonWidth, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> mainFrame.showGame());
        add(btnchoice2);   

        // กล่องข้อความ (วางเหนือปุ่ม)
        dialogueBox.setBounds((stdScreen.width - 800)/2, btnY - 180, 800, 150);
        add(dialogueBox);
        dialogueBox.setText("Diddy", "Welcome to my shop!");

        // Background
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = new ImageIcon("image\\Scene\\Shop\\ShopScene1.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}

/* 
public class ShopPanel extends JPanel {
    private StdAuto stdScreen;
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    DialoguePanel dialogueBox = new DialoguePanel();

    public ShopPanel(MainFrame mainFrame) {
        stdScreen = new StdAuto() ;
        stdScreen.setBtnWHG(250, 60, 20,0); //ขนาด ปุ่ม และ gap , แถว
        
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Hello i want to buy baby oil");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX+200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice1);

        JButton btnchoice2 = new JButton("give me money man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(stdScreen.centerX-200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice2);   

        dialogueBox.setBounds(stdScreen.centerX-200, stdScreen.currentY+200, stdScreen.buttonWidth+400, stdScreen.buttonHeight+100);
        add(dialogueBox);
        dialogueBox.setText("Diddy", "Welcome to Diddy shop! What do you want to buy?");


        // Background หน้าร้าน
        JLabel lblMap = new JLabel("");

        String imagePath = "image\\Scene\\Shop\\ShopScene1.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
    }
}
*/



