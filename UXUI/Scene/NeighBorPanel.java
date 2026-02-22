package UXUI.Scene;

import javax.swing.JPanel;
import UXUI.MainFrame;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;
import java.awt.Color;

public class NeighBorPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private GameTime realGameTime ;
    private Notify realNotify ;

    public NeighBorPanel(MainFrame mainFrame) {
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
}
