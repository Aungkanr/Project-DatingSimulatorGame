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
        int dialogueW = 1425;
        int dialogueH = 200;
        int dialogueX = (stdScreen.width - dialogueW) / 2; // จัดกึ่งกลางจอ
        int dialogueY = btnY - dialogueH - 50; // วางเหนือปุ่ม

        dialogueBox.setBounds(dialogueX, dialogueY, dialogueW, dialogueH);
        add(dialogueBox);
        dialogueBox.setText("Diddy", "Welcome to my shop!");
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = new ImageIcon("image\\Scene\\Shop\\ShopScene1.png");
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}