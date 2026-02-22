package UXUI.SceneNPC.Lazel;

import UXUI.MainFrame;
import UXUI.SceneNPC.BaseNPCPanel; //Parent class 
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;

public class LazelPanel extends BaseNPCPanel {

    public LazelPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getLazel(), "image\\Scene\\School\\Angryscene.png");
    }

    @Override
    public void returnBtn() {
        mainFrame.createSchoolPanel();
        mainFrame.showSchool();
    }

    @Override
    protected void triggerSpecialScene(String text, int sceneLevel) {
        mainFrame.createSpecialSceneLazelPanel((Relationship.Lazel) targetNPC, text, sceneLevel);
        mainFrame.showSpecialSceneLazel();
    }

    @Override
    protected void processGift(String itemName) {
        mainFrame.getPlayer().getInventory().removeItem(itemName);
        targetNPC.markAsGifted();

        removeAll(); 
        CreateTemplateScene scene;
        
        // Logic คะแนน Gift ของ Lazel
        if (itemName.equals("Fairy rose")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "โอ้... Fairy Rose ขอบใจนะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Tulip") || itemName.equals("Poppy")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "งดงามมาก...", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Lazel", 
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