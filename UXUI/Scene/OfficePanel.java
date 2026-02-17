package UXUI.Scene;

import javax.swing.JPanel;
import UXUI.DialoguePanel; // เพิ่ม import
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.GameTime;
import Utility.StdAuto;
import Player.Player;
import java.awt.Color;

public class OfficePanel extends JPanel {
    GamePanel realGamePanel ; 
    Player realPlayer ;
    GameTime realGameTime ;
    private StdAuto stdScreen;
    public static final Color btn1 = new Color(55, 55, 55);
    private MainFrame mainFrame;

    public OfficePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new java.awt.BorderLayout());
        stdScreen = new StdAuto();
        stdScreen.setBtnWHG(250, 50, 20, 0); // ตั้งขนาดปุ่มมาตรฐาน

        realGamePanel = mainFrame.getGamePanel(); 
        realPlayer = mainFrame.getPlayer();
        realGameTime = mainFrame.getGameTime();

        setBackground(new Color(12, 51, 204));

        showOfficeScene();
    }

    public void showOfficeScene() {
        this.removeAll();

        

        SceneUpdate scene = new SceneUpdate(
            "image\\Scene\\Office\\Barad-durWork.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "เรากำลังรับสมัครพนักงานพอดี สนใจไหม?", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            null, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)

            // 1. ปุ่มแบบ Auto ให้ระบบจัดวางให้เอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่ม ", e ->  เมื่อกดปุ่มจะให้ทำอะไรต่อ), *** ตัวอย่างการใช้งาน

            // --- ปุ่มขวา (Give Job Application) ---
            new SceneUpdate.SceneOption("Give Job Application", e -> {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                if (realGamePanel != null) {
                    realGamePanel.doActivity(40);
                    System.out.println("Working -> Energy: "+ realPlayer.getEnergy() +" | Day : "+ realGameTime.getDay() + "\n" +
                                "          " + "Money : "+ realPlayer.getMoney()  +" | Time: "+ realGameTime.getTimeString());
                }
            }),
            
            // --- ปุ่มซ้าย (I need to work here) ---
            new SceneUpdate.SceneOption("I need to work here man!", e -> {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                if (realGamePanel != null) {
                    realGamePanel.doActivity(40);
                    System.out.println("Working -> Energy: "+ realPlayer.getEnergy() +" | Day : "+ realGameTime.getDay() + "\n" +
                                "          " + "Money : "+ realPlayer.getMoney()  +" | Time: "+ realGameTime.getTimeString());
                }
            })

            // 2.ปุ่มแบบ Custom กำหนดตำแหน่งเองเเละขนาดเอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่", ตำเเหน่งปุ่มX, ตำเเหน่งปุ่มY, ขนาดปุ่มW, ขนาดปุ่มH, e -> เมื่อกดปุ่มจะให้ทำอะไรต่อ) *** ตัวอย่างการใช้งานเเบบ custom
        );

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
