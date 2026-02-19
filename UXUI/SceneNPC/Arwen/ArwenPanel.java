package UXUI.SceneNPC.Arwen;

import UXUI.MainFrame;
import UXUI.SceneNPC.BaseNPCPanel; //Parent class 
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;

public class ArwenPanel extends BaseNPCPanel {

    public ArwenPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getArwen(), "image\\Scene\\Bedroom\\ห้องนอน.png");
    }

    @Override
    public void returnBtn() {
        mainFrame.createNeightBorPanel();
        mainFrame.showNeighbor();
    }

    @Override
    protected void triggerSpecialScene(String text, int sceneLevel) {
        mainFrame.createSpecialSceneArwenPanel((Relationship.Arwen) targetNPC, text, sceneLevel);
        mainFrame.showSpecialSceneArwen();
    }

    @Override
    protected void processGift(String itemName) {
        mainFrame.getPlayer().getInventory().removeItem(itemName);
        targetNPC.markAsGifted();

        removeAll(); 
        CreateTemplateScene scene;
        
        // Logic คะแนน Gift ของ Arwen
        if (itemName.equals("Fairy rose")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "โอ้... Fairy Rose ขอบใจนะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Tulip") || itemName.equals("Poppy")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "งดงามมาก...", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "ขยะ... แต่ข้าจะรับไว้พิจารณา", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        }
        
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }
}