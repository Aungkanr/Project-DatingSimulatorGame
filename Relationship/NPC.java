package Relationship;

import javax.swing.JPanel;

public abstract class NPC {
    protected String name;
    protected int affectionPoints = 98; // คะแนน 0 - 500
    protected int heartLevel = 0;      // 0 - 5
    
    // สถานะประจำวัน
    private boolean giftedToday = false;
    
    // Scene Tracker: เก็บว่าดูฉากพิเศษของเลเวลนั้นๆ ไปหรือยัง
    // index 0 = lv1, index 4 = lv5. false = ยังไม่ดู, true = ดูแล้ว
    protected boolean[] sceneUnlocked = {false, false, false, false, false}; 

    // เกณฑ์คะแนน (100 แต้ม = 1 หัวใจ)
    protected final int POINTS_PER_HEART = 100;

    public NPC(String name) {
        this.name = name;
    }

    // --- Action Methods ---
    
    public String giveGift() {
        if (giftedToday) {
            return "ให้ไปแล้วนี่นา... ไว้พรุ่งนี้ค่อยให้ใหม่นะ";
        }
        
        giftedToday = true;
        addAffection(20); // ให้ของขวัญ +20 แต้ม
        return "มอบของขวัญให้ " + name + " เรียบร้อย! (+20 ความชอบ)";
    }

    public void addAffection(int amount) {
        this.affectionPoints += amount;
        updateHeartLevel();
    }

    private void updateHeartLevel() {
        int oldLevel = this.heartLevel;
        this.heartLevel = this.affectionPoints / POINTS_PER_HEART;
        if (this.heartLevel > 5) this.heartLevel = 5; // Max 5
    }

    public void resetDaily() {
        this.giftedToday = false;
    }

    // --- Logic การดึงบทพูด ---
    
    // เช็คว่าควรเล่น Special Scene ไหม?
    // เงื่อนไข: คะแนนถึงระดับนั้นๆ AND ยังไม่เคยดูฉากนั้น
    public boolean checkSpecialSceneTrigger() {
        if (heartLevel > 0 && heartLevel <= 5) {
            // heartLevel 1 ดู index 0, heartLevel 5 ดู index 4
            return !sceneUnlocked[heartLevel - 1]; 
        }
        return false;
    }

    // getDialogue() ยังคง return String เหมือนเดิม
    public String getDialogue() {
        // ถ้า special scene pending อยู่ ให้ return String บอกสถานะ
        if (checkSpecialSceneTrigger()) {
            return "SPECIAL_SCENE"; // สัญญาณให้ UI ไปเรียก getSpecialScene แทน
        }
        return getRandomDialogue(heartLevel);
    }

    // เพิ่ม method ใหม่สำหรับดึง JPanel
    public JPanel getSpecialScenePanel() {
        if (checkSpecialSceneTrigger()) {
            sceneUnlocked[heartLevel - 1] = true;
            return getSpecialScene(heartLevel);
        }
        return null;
    }

    // --- Abstract Methods (class child ต้องไปเขียนเอง) ---
    protected abstract String getRandomDialogue(int level);
    protected abstract JPanel getSpecialScene(int level);

    // --- Getters ---
    public String getName() { return name; }
    public int getHeartLevel() { return heartLevel; }
    public int getAffection() { return affectionPoints; }
    public boolean isGiftedToday() { return giftedToday; }
}