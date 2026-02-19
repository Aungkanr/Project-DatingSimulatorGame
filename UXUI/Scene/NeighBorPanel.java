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
        add(realNotify); 
        // ---------------------------------------------------------
        initComponents();
        setComponentZOrder(realNotify, 0);
    }

    public void initComponents() {
        CreateTemplateScene scene = new CreateTemplateScene("image\\Scene\\School\\โรงเรียนตอนเช้า.png", null, null, e -> mainFrame.showGame() , "Back to Town", 
        new CreateTemplateScene.SceneOption("Talk to Arwen", e -> {
            if (realGameTime.getTimeSlot() == 0 || realGameTime.getTimeSlot() == 2 ) {
                mainFrame.createArwenPanel(); 
                mainFrame.showArwen();
            } else if(realGameTime.getTimeSlot() == 3 )  realNotify.showNotify("Arwen is resting.", Color.RED, 2050);
            else realNotify.showNotify("Maybe she is cooking crystal meth.!!!LOL", Color.RED, 2050);
        }
        ));

        add(scene, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();  
        
    }

}
