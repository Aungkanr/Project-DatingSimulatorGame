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


public class SchoolPanel extends JPanel {

    private StdAuto stdScreen;
    private DialoguePanel dialogueBox = new DialoguePanel();
    private Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    public SchoolPanel(MainFrame mainFrame) {
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน (กว้าง 250, สูง 50)

        setLayout(null);
        setBackground(new Color(12, 51, 204));
        
        // ใช้ค่า Y ที่ปลอดภัย (ลอยจากขอบล่าง 120px)
        int btnY = stdScreen.bottomY;

        // --- ปุ่มขวา (Right Button) ---
        JButton btnchoice1 = new JButton("Hi my baby");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // วางที่ Center + 150 (เยื้องขวา)
        btnchoice1.setBounds(stdScreen.centerX + 150, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(e -> mainFrame.showGame());
        add(btnchoice1);

        // --- ปุ่มซ้าย (Left Button) ---
        JButton btnchoice2 = new JButton("You are so cute");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        // วางที่ Center - 150 - ความกว้างปุ่ม (เยื้องซ้าย)
        btnchoice2.setBounds(stdScreen.centerX - 150 - stdScreen.buttonWidth, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(e -> mainFrame.showGame());
        add(btnchoice2);

        // --- กล่องข้อความ (Dialogue Box) ---
        // วางให้อยู่เหนือปุ่มขึ้นไปประมาณ 180px
        int dialogueW = 800;
        int dialogueH = 150;
        int dialogueX = (stdScreen.width - dialogueW) / 2; // จัดกึ่งกลางจอ
        int dialogueY = btnY - dialogueH - 30; // วางเหนือปุ่ม
        
        dialogueBox.setBounds(dialogueX, dialogueY, dialogueW, dialogueH);
        add(dialogueBox);
        dialogueBox.setText("Person", "เดินหัดดูทางบ้างซิยะ! ตาถั่วหรือไง มายืนขวางประตูหน้าตึกอยู่ได้!");

        // --- Background Image ---
        JLabel lblMap = new JLabel("");
        // ตรวจสอบชื่อไฟล์ภาพให้ถูกต้องนะครับ
        String imagePath = "image\\Scene\\School\\Angryscene.png"; 
        ImageIcon originalIcon = new ImageIcon(imagePath);
        
        // สั่งยืดภาพให้เต็มจอ 1280x720
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        
        // บังคับให้รูปภาพอยู่เลเยอร์หลังสุด
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}
/* 
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


*/