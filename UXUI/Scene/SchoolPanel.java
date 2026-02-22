package UXUI.Scene;

import javax.swing.*;
import java.awt.Color;
import UXUI.MainFrame;
import Utility.*;

public class SchoolPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private GameTime realGameTime;
    private Notify realNotify;

    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        this.setLayout(new java.awt.BorderLayout());
        setBackground(Color.BLACK);

        this.realGameTime = mainFrame.getGameTime();
        this.realNotify = new Notify(stdScreen.width); 
        this.realNotify.setBounds(0, 50, stdScreen.width, 50); 

        updateUI(); 
    }

    private void initComponents() {
        String currentBgPath = getSchoolBgPath(realGameTime.getTimeString());
        CreateTemplateScene scene = new CreateTemplateScene(currentBgPath, null, null, e -> mainFrame.showGame() , "Back to Town", 
        new CreateTemplateScene.SceneOption("Talk to Lazel", e -> {
            if (realGameTime.getTimeSlot() < 2) {
                mainFrame.createLazelPanel(); 
                mainFrame.showLazel();
            } else realNotify.showNotify("Lazel is not here, she has left for a while.", Color.RED, 2050);
        }));
        add(scene, java.awt.BorderLayout.CENTER);
    }

    // --- เลือก Path รูปภาพตาม Time ---
    private String getSchoolBgPath(String timeString) {
        switch (timeString) {
            case "Morning": return "image\\Scene\\School\\โรงเรียนตอนเช้า.png";
            case "Noon":    return "image\\Scene\\School\\โรงเรียนตอนกลางวัน.png";
            case "Evening": return "image\\Scene\\School\\โรงเรียนตอนเย็น.png";
            case "Night":   return "image\\Scene\\School\\โรงเรียนตอนกลางคืน.png";
            default:        return "image\\Scene\\School\\โรงเรียนตอนเช้า.png";
        }
    }

    // --- Real-time --- update
    public void updateUI() {
        if (mainFrame == null) return;
        removeAll();
        add(realNotify);
        initComponents(); 
        setComponentZOrder(realNotify, 0); 
        revalidate(); 
        repaint();    
    }
}