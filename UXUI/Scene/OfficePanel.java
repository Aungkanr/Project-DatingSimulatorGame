package UXUI.Scene;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Player.Player;

public class OfficePanel extends JPanel {
    private MainFrame mainFrame; // เก็บ reference ของ MainFrame เพื่อใช้ในการเปลี่ยนหน้าจอ
    GamePanel realGamePanel;
    Player realPlayer;

    public OfficePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // เก็บ reference ของ MainFrame
        realGamePanel = mainFrame.getGamePanel(); 
        realPlayer = mainFrame.getPlayer();

        this.setLayout(new BorderLayout());

        showAngryScene(); // เรียกใช้เมธอดเพื่อแสดงฉากแรก
    }

    public void showAngryScene() {
        SceneUpdate scene = new SceneUpdate(
            "image\\Scene\\Office\\Barad-durWork.png", // ตำเเหน่งของภาพพื้นหลัง
            "Manager", // ชื่อผู้พูด
            "เรากำลังรับสมัครพนักงานพอดี สนใจไหม?", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            e -> mainFrame.showGame(), // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)

            // 1. ปุ่มแบบ Auto ให้ระบบจัดวางให้เอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่ม ", e ->  เมื่อกดปุ่มจะให้ทำอะไรต่อ), *** ตัวอย่างการใช้งาน
            new SceneUpdate.SceneOption("Give Job Application", e -> {
                mainFrame.showGame();
                realPlayer.increaseMoney(80);
                if (realGamePanel != null) {
                    realGamePanel.doActivity(40);
                }
            }),
            new SceneUpdate.SceneOption("I need to work here man!", e -> this.mainFrame.showGame())
        );
        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
