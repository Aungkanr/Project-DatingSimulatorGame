package UXUI.Scene;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import UXUI.MainFrame;
import UXUI.PauseMenuPanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class NeighBorPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private GameTime realGameTime ;
    private Notify realNotify ;
    PauseMenuPanel pauseMenu;

    public NeighBorPanel(MainFrame mainFrame) {
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

    public void initComponents() {
        String currentBgPath = getNeighborBgPath(realGameTime.getTimeString());
        CreateTemplateScene scene = new CreateTemplateScene(currentBgPath, null, null, e -> mainFrame.showGame() , "Back to Town", 
        new CreateTemplateScene.SceneOption("Talk to Arwen", e -> {
            if (realGameTime.getTimeSlot() == 0 || realGameTime.getTimeSlot() == 2 ) {
                mainFrame.createArwenPanel(); 
                mainFrame.showArwen();
            } else if(realGameTime.getTimeSlot() == 3 )  realNotify.showNotify("Arwen is resting.", Color.RED, 2050);
            else realNotify.showNotify("Maybe she is cooking crystal meth.!!!LOL", Color.RED, 2050);
        }
        ));

        CreateESC(); // เรียกใช้แค่ตัวจับปุ่ม

        pauseMenu.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(pauseMenu);

        add(scene, java.awt.BorderLayout.CENTER);        
    }
    // --- เลือก Path รูปภาพตาม Time ---
    private String getNeighborBgPath(String timeString) {
        switch (timeString) {
            case "Morning": return "image\\Scene\\NeighBor\\บ้านเพื่อนตอนเช้า.png";
            case "Noon":    return "image\\Scene\\NeighBor\\บ้านเพื่อนตอนเที่ยง.png";
            case "Evening": return "image\\Scene\\NeighBor\\บ้านเพื่อนตอนเย็น.png";
            case "Night":   return "image\\Scene\\NeighBor\\บ้านเพื่อนตอนกลางคืน.png";
            default:        return "image\\Scene\\NeighBor\\บ้านเพื่อนตอนเช้า.png";
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
