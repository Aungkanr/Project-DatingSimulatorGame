package UXUI.Scene;

import java.awt.BorderLayout; // 1. import BorderLayout
import javax.swing.JPanel;

import UXUI.MainFrame;

public class SchoolPanel extends JPanel {
    private MainFrame mainFrame;
    private int SaveScene = 1;
    
    
    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // 1. ตั้งค่า Layout ของ SchoolPanel ให้เป็น BorderLayout
        this.setLayout(new BorderLayout());

        if (SaveScene == 1) {
            showAngryScene(); 
        }
    }

    public void showAngryScene() {
        this.removeAll();

        
        SceneUpdate scene = new SceneUpdate(
            "image\\Scene\\School\\Angryscene.png", // ตำเเหน่งของภาพพื้นหลัง
            "Lazel", // ชื่อผู้พูด
            "หัดเดินดูทางหน่อย! ขวางมากๆเดี๋ยวกูเอาดาบฟันมึงนะ!", // ข้อความที่ต้องการให้แสดงในกล่องข้อความ
            // diaX, diaY, diaW, diaH, // กำหนดตำแหน่งและขนาดของ Dialogue Box
            null, // ActionListener สำหรับปุ่ม "กลับไปที่เกม" (เมื่อกดปุ่มนี้จะกลับไปที่หน้าจอเกม)

            // 1. ปุ่มแบบ Auto ให้ระบบจัดวางให้เอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่ม ", e ->  เมื่อกดปุ่มจะให้ทำอะไรต่อ), *** ตัวอย่างการใช้งาน
            new SceneUpdate.SceneOption("You are so cute", e -> this.mainFrame.showGame()),
            new SceneUpdate.SceneOption("I am sorry...", e -> this.mainFrame.showGame())

            // 2.ปุ่มแบบ Custom กำหนดตำแหน่งเองเเละขนาดเอง
            // *** new SceneUpdate.SceneOption(" ข้อความในปุ่", ตำเเหน่งปุ่มX, ตำเเหน่งปุ่มY, ขนาดปุ่มW, ขนาดปุ่มH, e -> เมื่อกดปุ่มจะให้ทำอะไรต่อ) *** ตัวอย่างการใช้งานเเบบ custom
        );

        add(scene, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}