package UXUI.SceneNPC.Galadriel;

import UXUI.MainFrame;
import UXUI.SceneNPC.BaseNPCPanel; //Parent class 
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;

public class GaladrielPanel extends BaseNPCPanel {

    public GaladrielPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getGaladriel(), "image\\NPCPanel\\Galadriel\\GaladrielPanel.png");
    }

    @Override
    public void returnBtn() {
        // --- ใส่ Effect Fade ---
        Utility.ScreenFader fader = new Utility.ScreenFader();
        mainFrame.setGlassPane(fader);
        fader.setVisible(true);
        fader.fadeInOut(400, 200, () -> {
            mainFrame.createShopPanel();
            mainFrame.showShop();
        }, () -> fader.setVisible(false));
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
        if (itemName.equals("Fairy Rose")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Galadriel", 
                "งื้อออ! Fairy Rose! ดอกไม้ของเหล่าภูติ! มันเปล่งประกายสวยมากเลย ท่านไปหามาได้ยังไงเนี่ย ข้ารักมันที่สุดเลย... รักท่านด้วย!", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Tulip") || itemName.equals("Poppy")) {
            targetNPC.addAffection(10); // แก้ไขจาก 1 เป็น 10 ตามเงื่อนไขของคุณ
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Galadriel", 
                "ว้าว ทิวลิป! สีสดใสเหมือนพระอาทิตย์ยามเช้าเลยค่ะ ขอบคุณนะคะ! ข้าจะเอามันไปทัดหูไว้ มันต้องเข้ากับชุดข้าแน่ๆ เลย", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Galadriel", 
                "เอ๊ะ ให้ข้าเหรอคะ? ดีใจจังเลย! แค่เป็นของที่ท่านตั้งใจให้ ข้าก็ชอบหมดแหละค่ะ ขอบคุณนะคะ!", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        }
        
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }
}