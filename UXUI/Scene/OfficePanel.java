package UXUI.Scene;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import UXUI.DialoguePanel; // เพิ่ม import
import UXUI.Hovereffect;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.GameTime;
import Utility.StdAuto;
import Player.Player;
import java.awt.Color;
import java.awt.Font;

public class OfficePanel extends JPanel {
    GamePanel realGamePanel ; 
    Player realPlayer ;
    GameTime realGameTime ;
    private StdAuto stdScreen;
    private Utility.CheckImage checkImageUtil = new Utility.CheckImage();
    private DialoguePanel dialogueBox = new DialoguePanel(); // เพิ่มกล่องข้อความ
    public static final Color btn1 = new Color(55, 55, 55);
    

    public OfficePanel(MainFrame mainFrame) {
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน

        realGamePanel = mainFrame.getGamePanel(); 
        realPlayer = mainFrame.getPlayer();
        realGameTime = mainFrame.getGameTime();

        setLayout(null);
        setBackground(new Color(12, 51, 204));
        
        // ใช้ค่า Y ที่ปลอดภัย
        int btnY = stdScreen.bottomY;

        // --- ปุ่มขวา (Give Job Application) ---
        JButton btnchoice1 = new JButton("Give Job Application");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 14)); // ลดฟอนต์นิดนึงถ้ายาว
        btnchoice1.setBounds(stdScreen.centerX + 150, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        Hovereffect.HoverEffect(btnchoice1,stdScreen.centerX + 150, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, btn1);        
        btnchoice1.addActionListener(e -> {
            mainFrame.showGame();
            realPlayer.increaseMoney(80);
            if (realGamePanel != null) {
                realGamePanel.doActivity(40);
                System.out.println("Working -> Energy: "+ realPlayer.getEnergy() +" | Day : "+ realGameTime.getDay() + "\n" +
                               "          " + "Money : "+ realPlayer.getMoney()  +" | Time: "+ realGameTime.getTimeString());
            }
        });
        add(btnchoice1);

        // --- ปุ่มซ้าย (I need to work here) ---
        JButton btnchoice2 = new JButton("I need to work here man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnchoice2.setBounds(stdScreen.centerX - 150 - stdScreen.buttonWidth, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        Hovereffect.HoverEffect(btnchoice2 , stdScreen.centerX - 150 - stdScreen.buttonWidth, btnY, stdScreen.buttonWidth, stdScreen.buttonHeight, btn1);        
        btnchoice2.addActionListener(e -> mainFrame.showGame());
        add(btnchoice2);   
        
        // --- กล่องข้อความ (Dialogue Box) ---
        dialogueBox.setDefaultBounds(stdScreen, btnY);
        add(dialogueBox);
        dialogueBox.setText("Manager", "เรากำลังรับสมัครพนักงานพอดี สนใจไหม?"); // ใส่ข้อความตัวอย่าง

        // --- Background Image ---
        JLabel lblMap = new JLabel("");
        // *อย่าลืมเช็คชื่อไฟล์รูปภาพของคุณว่าชื่ออะไร* (ผมสมมติว่าเป็น OfficeScene.png)
        String imagePath = "image\\Scene\\Office\\Barad-durWork.png"; 
        ImageIcon originalIcon = new ImageIcon(imagePath);
        
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        
        // บังคับให้รูปอยู่หลังสุด
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}