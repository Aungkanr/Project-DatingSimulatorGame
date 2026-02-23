package UXUI.SceneNPC.Lazel;

import UXUI.MainFrame;
import UXUI.SceneNPC.BaseNPCPanel; //Parent class 
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;

public class LazelPanel extends BaseNPCPanel {

    public LazelPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getLazel(), getSchoolBgPathStatic(mainFrame.getGameTime().getTimeString()));
    }
    
    private static String getSchoolBgPathStatic(String timeString) {
        switch (timeString) {
            case "Morning": return "image\\NPCPanel\\Lazel\\LazelMorning.png";
            case "Noon":    return "image\\NPCPanel\\Lazel\\LazelNoon.png";
            default:        return "image\\Scene\\School\\โรงเรียนตอนเช้า.png";
        }
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
        if (itemName.equals("Zenith")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "โอ้... Zenith ขอบใจนะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Excalibur") ) {
            targetNPC.addAffection(15); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "งดงามมาก...ข้าชอบมัน", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Fairy Rose")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "โอ้... Fairy Rose ขอบใจนะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
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
    // --- Real-time --- update
    public void updateUI() {
        if (mainFrame == null) return;
        this.bgPath = getSchoolBgPathStatic(mainFrame.getGameTime().getTimeString());
        showInteractionMenu(); 
    }
}