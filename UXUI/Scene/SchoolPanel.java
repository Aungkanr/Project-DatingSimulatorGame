package UXUI.Scene;

import javax.swing.*;
import java.awt.Color;
import UXUI.MainFrame;
import Utility.*;

public class SchoolPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private GameTime realGameTime ;
    private Notify realNotify ;

    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        this.setLayout(new java.awt.BorderLayout());
        setBackground(Color.BLACK);

        this.realGameTime = mainFrame.getGameTime(); // ดึงเวลามาจาก MainFrame
        this.realNotify = new Notify(stdScreen.width); // สร้างตัวแจ้งเตือน
        this.realNotify.setBounds(0, 50, stdScreen.width, 50); // กำหนดตำแหน่ง
        add(realNotify); // *** อย่าลืม add เข้า Panel ***

        // ---------------------------------------------------------
        initComponents();
        setComponentZOrder(realNotify, 0);
    }

    private void initComponents() {

        CreateTemplateScene scene = new CreateTemplateScene("image\\Scene\\School\\โรงเรียนตอนเช้า.png", null, null, e -> mainFrame.showGame() , "Back to Town", 
        new CreateTemplateScene.SceneOption("Talk to Lazel", e -> {
            if (realGameTime.getTimeSlot() < 2) {
                // สั่ง MainFrame ให้สร้างและโชว์หน้า Lazel
                //go to scene Lazel
                mainFrame.createLazelPanel(); 
                mainFrame.showLazel();
            } else realNotify.showNotify("Lazel is not here, she has left for a while.", Color.RED, 2050);
        }
        ));

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}