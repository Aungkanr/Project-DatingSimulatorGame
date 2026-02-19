package UXUI.Scene;

import javax.swing.JPanel;

import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.GameTime;
import Utility.StdAuto;
import Player.Player;
import java.awt.Color;

public class NeighBorPanel extends JPanel {
    GamePanel realGamePanel ; 
    Player realPlayer ;
    GameTime realGameTime ;
    private StdAuto stdScreen;
    public static final Color btn1 = new Color(55, 55, 55);
    private MainFrame mainFrame;

    public NeighBorPanel(MainFrame mainFrame) {
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

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\Bedroom\\ห้องนอน.png", // ตำเเหน่งของภาพพื้นหลัง
            "เพื่อนบ้านที่เเสนดี", // ชื่อผู้พูด
            "ว่าไงสุดหล่อ", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            e -> {mainFrame.showGame();}, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)
            "Back to Town",

            // 1. ปุ่มแบบ Auto ให้ระบบจัดวางให้เอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่ม ", e ->  เมื่อกดปุ่มจะให้ทำอะไรต่อ), *** ตัวอย่างการใช้งาน

            // --- ปุ่มขวา (Give Job Application) ---
            new CreateTemplateScene.SceneOption("ว่าไงสุดสวย", e -> {
                mainFrame.showGame();
            }),
            
            // --- ปุ่มซ้าย (I need to work here) ---
            new CreateTemplateScene.SceneOption("sf#PFJO#oi$#$#%R_$%$#@%*@#)(!", e -> {
                mainFrame.showGame();
            })

            // 2.ปุ่มแบบ Custom กำหนดตำแหน่งเองเเละขนาดเอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่", ตำเเหน่งปุ่มX, ตำเเหน่งปุ่มY, ขนาดปุ่มW, ขนาดปุ่มH, e -> เมื่อกดปุ่มจะให้ทำอะไรต่อ) *** ตัวอย่างการใช้งานเเบบ custom
        );

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
