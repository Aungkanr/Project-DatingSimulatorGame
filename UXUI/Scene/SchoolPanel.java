package UXUI.Scene;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;

import UXUI.MainFrame;
import UXUI.PauseMenuPanel;
import Utility.*;

public class SchoolPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private GameTime realGameTime;
    private Notify realNotify;
    PauseMenuPanel pauseMenu;
    ScreenFader fader = new ScreenFader();

    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        pauseMenu = new PauseMenuPanel(mainFrame);

        this.setLayout(new java.awt.BorderLayout());
        setBackground(Color.BLACK);

        this.realGameTime = mainFrame.getGameTime();
        this.realNotify = new Notify(stdScreen.width); 
        this.realNotify.setBounds(0, 50, stdScreen.width, 50); 

        updateUI(); 
    }

    private void initComponents() {

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        String currentBgPath = getSchoolBgPath(realGameTime.getTimeString());
        CreateTemplateScene scene = new CreateTemplateScene(currentBgPath, null, null, e -> {fader.fadeInOut(250, 250, ()->{mainFrame.showGame();}, null);} , "Back to Town", 
        new CreateTemplateScene.SceneOption("Talk to Lazel", e -> {
            if (realGameTime.getTimeSlot() < 2) {
                mainFrame.createLazelPanel(); 
                mainFrame.showLazel();
            } else realNotify.showNotify("Lazel is not here, she has left for a while.", Color.RED, 2050);
        }));
        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(pauseMenu);

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

    public void CreateESC() {
        //---------------------------ESC Event---------------------------------------
        pauseMenu.setVisible(false); // เริ่มมาให้ซ่อนไว้ก่อน

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "smartEsc");
        this.getActionMap().put("smartEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // 🔥 1. เช็คก่อนว่า "หน้า Option ลอยทับอยู่หรือเปล่า?"
                if (mainFrame.getOptionPanel() != null && mainFrame.getOptionPanel().isVisible()) {
                    // ถ้าลอยอยู่ -> ให้กด ESC เพื่อ "ปิดหน้า Option" อย่างเดียว
                    mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
                    mainFrame.getOptionPanel().setVisible(false); 
                }
                // 🔥 2. ถ้า Option ไม่ได้เปิดอยู่ -> ค่อยสลับเปิด/ปิด หน้า Pause ตามปกติ
                else {
                    boolean isCurrentlyVisible = pauseMenu.isVisible();
                    pauseMenu.setVisible(!isCurrentlyVisible);
                }
                
            }
        });
    }
}