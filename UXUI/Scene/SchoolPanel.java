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
        stdScreen.setBtnWHG(250, 50,    20, 0); // ตั้งขนาดปุ่มมาตรฐาน (กว้าง 250, สูง 50)

        setLayout(null);
        setBackground(new Color(12, 51, 204));
        
        // ใช้ค่า Y ที่ปลอดภัย (ลอยจากขอบล่าง 120px)
        int btnY = stdScreen.bottomY;

        int gap = 20;// ระยะห่างระหว่างปุ่ม

        // 1. ปุ่มซ้าย (Left) : "You are so cute"
        JButton btnLeft = new JButton("You are so cute");
        btnLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
        // สูตร: Center - Width - Gap
        btnLeft.setBounds(stdScreen.centerX - stdScreen.buttonWidth - gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnLeft.addActionListener(e -> mainFrame.showGame());
        add(btnLeft);

        // 2. ปุ่มกลาง (Middle) : "I am sorry" (เพิ่มมาใหม่)
        JButton btnMiddle = new JButton("I am sorry...");
        btnMiddle.setFont(new Font("Tahoma", Font.PLAIN, 14));
        // สูตร: Center
        btnMiddle.setBounds(stdScreen.centerX, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnMiddle.addActionListener(e -> mainFrame.showGame());
        add(btnMiddle);

        // 3. ปุ่มขวา (Right) : "Hi my baby"
        JButton btnRight = new JButton("Hi my baby");
        btnRight.setFont(new Font("Tahoma", Font.PLAIN, 14));
        // สูตร: Center + Width + Gap
        btnRight.setBounds(stdScreen.centerX + stdScreen.buttonWidth + gap, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnRight.addActionListener(e -> mainFrame.showGame());
        add(btnRight);

        // --- กล่องข้อความ (Dialogue Box) ---
        // วางให้อยู่เหนือปุ่มขึ้นไปประมาณ 180px
        int dialogueW = 1425;
        int dialogueH = 250;
        int dialogueX = (stdScreen.width - dialogueW) / 2; // จัดกึ่งกลางจอ
        int dialogueY = btnY - dialogueH - 50; // วางเหนือปุ่ม
        
        dialogueBox.setBounds(dialogueX, dialogueY, dialogueW, dialogueH);
        add(dialogueBox);
        dialogueBox.setText("Person", "เดินหัดดูทางบ้างซิยะ! ตาถั่วหรือไง มายืนขวางประตูหน้าตึกอยู่ได้!");

        // --- ปุ่ม Back (วางก่อน Background) ---
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> {
            mainFrame.showGame(); // กลับไปหน้าเกม
        });
        add(btnBack);

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
