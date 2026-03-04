package UXUI.SceneNPC.Arwen;

import UXUI.MainFrame;
import UXUI.Scene.CreateTemplateScene; //Parent class
import UXUI.Scene.CreateTemplateScene.SceneOption;
import UXUI.SceneNPC.BaseNPCPanel;

public class ArwenPanel extends BaseNPCPanel {

    public ArwenPanel(MainFrame mainFrame) {
        super(mainFrame, mainFrame.getPlayer().getArwen(), "image\\NPCPanel\\Arwen\\ArwenTalkScene.png");
    }

    @Override
    public void returnBtn() {
        // --- ใส่ Effect Fade ---
        Utility.ScreenFader fader = new Utility.ScreenFader();
        mainFrame.setGlassPane(fader);
        fader.setVisible(true);
        fader.fadeInOut(400, 200, () -> {
            mainFrame.createNeightBorPanel();
            mainFrame.showNeighbor();
        }, () -> fader.setVisible(false));
    }

   @Override
    protected void triggerSpecialScene(String text, int sceneLevel) {
        String time = mainFrame.getGameTime().getTimeString();
        if (time.equals("Morning")) {
            mainFrame.createSpecialSceneArwenPanel(
                (Relationship.Arwen) targetNPC, text, sceneLevel);
        } else {
            Relationship.ArwenEven evenVersion = new Relationship.ArwenEven();
            mainFrame.createSpecialSceneArwenPanel(evenVersion, text, sceneLevel);
        }
        mainFrame.showSpecialSceneArwen();
    }

    @Override
    protected void processGift(String itemName) {
        mainFrame.getPlayer().getInventory().removeItem(itemName);
        targetNPC.markAsGifted();

        removeAll(); 
        CreateTemplateScene scene;
        
        // Logic คะแนน Gift ของ Arwen
        if (itemName.equals("Fairy Rose")) {
            targetNPC.addAffection(20); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "นี่มัน Fairy Rose... ดอกไม้เวทมนตร์ที่หายากมาก ท่านไปหามาได้ยังไงคะเนี่ย? ขอบคุณมากเลยนะคะ ข้าจะเก็บรักษามันไว้อย่างดีที่สุดเลยค่ะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Tulip") || itemName.equals("Poppy")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "ดอกทิวลิปสีสวยจังเลยค่ะ... ท่านช่างใส่ใจรายละเอียดจริงๆ ข้าจะเอาไปปักแจกันไว้ที่โต๊ะปรุงยานะคะ จะได้มองเห็นมันทุกวัน", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\LazelScene1\\เขิน.png", 
                "Arwen", 
                "ขอบคุณสำหรับของขวัญนะคะ ท่านช่างมีน้ำใจจริงๆ... ข้าจะเก็บมันไว้อย่างดีค่ะ", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        }
        
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }
}