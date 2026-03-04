package UXUI.SceneNPC.Lazel;

import Relationship.Lazel;
import Relationship.LazelNoon;
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
        // --- ใส่ Effect Fade ---
        Utility.ScreenFader fader = new Utility.ScreenFader();
        mainFrame.setGlassPane(fader);
        fader.setVisible(true);
        fader.fadeInOut(400, 200, () -> {
            mainFrame.createSchoolPanel();
            mainFrame.showSchool();
        }, () -> fader.setVisible(false));
    }

    @Override
    protected void triggerSpecialScene(String text, int sceneLevel) {
        String time = mainFrame.getGameTime().getTimeString();
        if (time.equals("Morning")) {
            mainFrame.createSpecialSceneLazelPanel(
                (Lazel) targetNPC, text, sceneLevel);
        } else {
            LazelNoon noonVersion = new LazelNoon();
            mainFrame.createSpecialSceneLazelPanel( noonVersion, text, sceneLevel);
        }
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
                "น-นี่มันดาบ Zenith! ดาบสุดยอดแห่งตำนานที่หลอมรวมจากดาบนับไม่ถ้วน... เจ้าไปเอาของโคตรแรร์แบบนี้มาได้ยังไงเนี่ย! ข...ขอบใจนะ! ข้าจะใช้มันฟันศัตรูที่กล้าเข้ามาแตะต้องเจ้าให้ราบคาบเลย!", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Excalibur") ) {
            targetNPC.addAffection(15); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "โอ้โห Excalibur! สมดุลดาบดีเยี่ยม ประกายแสงก็แสบตาใช้ได้เลย หึ... รู้ใจข้าดีนี่นา ขอบใจนะ! วันนี้เราไปหาเรื่องพวกมอนสเตอร์มาเป็นกระสอบทรายลองดาบกันดีไหม?", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else if (itemName.equals("Fairy Rose")) {
            targetNPC.addAffection(10); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "ด-ดอกไม้? ให้ข้าเนี่ยนะ? ข้าเป็นนักรบนะเว้ย จะให้พกดอกไม้ไปฟันใคร... ต-แต่... กลิ่นมันก็หอมดี แสงก็สวยด้วย... ขอบใจละกัน! ห้ามเอาไปเล่าให้ใครฟังเด็ดขาดนะ!", 
                null, 
                null, 
                new SceneOption("Continue...", e -> showInteractionMenu()));
        } else {
            targetNPC.addAffection(5); 
            scene = new CreateTemplateScene(
                "image\\Scene\\Lazel\\ซีน1\\เขิน.png", 
                "Lazel", 
                "อะไรเนี่ย? ของฝากเรอะ... ปกติข้าไม่ค่อยสนใจของพวกนี้หรอกนะ แต่เอาเถอะ ขอบใจที่นึกถึงข้าละกัน", 
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