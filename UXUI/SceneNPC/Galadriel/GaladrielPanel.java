package UXUI.SceneNPC.Galadriel;

import UXUI.MainFrame;
import UXUI.SceneNPC.BaseNPCPanel; //Parent class 
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;

public class GaladrielPanel extends BaseNPCPanel {

    public GaladrielPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getGaladriel(), "image\\Scene\\Galadriel\\ShyGaladriel.png");
    }

    @Override
    public void returnBtn() {
        mainFrame.createShopPanel();
        mainFrame.showShop();
    }

    @Override
    protected void triggerSpecialScene(String text, int sceneLevel) {
        mainFrame.createSpecialSceneGaladrielPanel((Relationship.Galadriel) targetNPC, text, sceneLevel);
        mainFrame.showSpecialSceneGaladriel();
    }

    @Override
    protected void processGift(String itemName) {
        mainFrame.getPlayer().getInventory().removeItem(itemName);
        targetNPC.markAsGifted();

        removeAll(); 
        CreateTemplateScene scene;
        
        // Logic คะแนน Gift ของ Galadriel
        if (itemName.equals("Fairy rose")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Galadriel\\ShyGaladriel.png", 
                "Galadriel", 
                "อะ... ให้ข้าหรอ Fairy rose นี้สวยจัง", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Tulip") || itemName.equals("Poppy")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Galadriel\\ShyGaladriel.png", 
                "Galadriel", 
                "ดอกไม้นี้งดงามดีนะข้าจะเก็บมันไว้", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Galadriel\\ShyGaladriel.png", 
                "Galadriel", 
                "อะ..ข้าไม่ค่อยรู้จักมันแต่จะรับไว้ก็ได้เพื่อไม่ทำลายน้ำใจเจ้า", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        }
        
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }
}