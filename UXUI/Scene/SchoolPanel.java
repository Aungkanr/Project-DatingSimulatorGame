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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchoolPanel extends JPanel {

    private StdAuto stdScreen = new StdAuto() ;
    DialoguePanel dialogueBox = new DialoguePanel();
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    public SchoolPanel(MainFrame mainFrame) {
        stdScreen.setBtnWHG(200, 60, 20 ,0); //ขนาด ปุ่ม และ gap ,แถว

        setLayout(null);
        setBackground(new Color(12, 51, 204));
        JButton btnchoice1 = new JButton("Hi my beaby");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX+200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice1);

        JButton btnchoice2 = new JButton("You are so cute");
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
        dialogueBox.setText("Perseone", "เดินหัดดูทางบ้างซิยะ! ตาถั่วหรือไง มายืนขวางประตูหน้าตึกอยู่ได้!");


        // Background หน้าร้าน
        JLabel lblMap = new JLabel("");

        String imagePath = "image\\Scene\\School\\Angryscene.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

    }
}
