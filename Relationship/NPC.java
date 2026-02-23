package Relationship;

public abstract class NPC {
    protected String name;
    protected int affectionPoints = 0; // คะแนน 0 - 500
    protected int heartLevel = 0;      // 0 - 5
    
    // สถานะประจำวัน
    private boolean giftedToday = false;

    // Flag: ตรวจว่า getDialogue() ล่าสุดคือ Special Scene หรือไม่
    // ใช้โดย LazelPanel เพื่อแยก flow ว่าต้องแสดง choice ไหม
    private boolean lastDialogueWasSpecial = false;

    // เก็บ heartLevel ณ ตอนที่ trigger special scene (สำหรับส่งให้ choice system)
    private int lastSpecialSceneLevel = 0;
    
    // Scene Tracker: เก็บว่าดูฉากพิเศษของเลเวลนั้นๆ ไปหรือยัง
    // index 0 = lv1, index 4 = lv5. false = ยังไม่ดู, true = ดูแล้ว
    protected boolean[] sceneUnlocked = {false, false, false, false, false}; 

    // เกณฑ์คะแนน (100 แต้ม = 1 หัวใจ)
    protected final int POINTS_PER_HEART = 100;

    public NPC(String name) {
        this.name = name;
    }

    // --- Action Methods ---
    
    public void markAsGifted() {
        this.giftedToday = true;
    }

    public void addAffection(int amount) {
        this.affectionPoints += amount;
        updateHeartLevel();
    }

    public void decreaseAffection(int amount) {
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
            return !sceneUnlocked[heartLevel - 1]; //true
        }
        return false;
    }

    public String getDialogue() {
        if (checkSpecialSceneTrigger()) {
            // ถ้าเงื่อนไขครบ ให้เล่นฉากพิเศษ และมาร์คว่าดูแล้ว
            sceneUnlocked[heartLevel - 1] = true; 
            lastDialogueWasSpecial = true;
            lastSpecialSceneLevel = heartLevel;
            return getSpecialScene(heartLevel);
        } else {
            // ถ้าไม่เข้าเงื่อนไข ให้สุ่มคุยปกติ
            lastDialogueWasSpecial = false;
            lastSpecialSceneLevel = 0;
            return getRandomDialogue(heartLevel);
        }
    }

    // --- Abstract Methods (class child ต้องไปเขียนเอง) ---
    protected abstract String getRandomDialogue(int level);
    protected abstract String getSpecialScene(int level);

    // --- Getters ---
    public String getName() { return name; }
    public int getHeartLevel() { return heartLevel; }
    public int getAffection() { return affectionPoints; }
    public boolean isGiftedToday() { return giftedToday; }

    /**
     * ตรวจว่า getDialogue() ครั้งล่าสุดเป็น Special Scene ไหม
     * LazelPanel ใช้ method นี้เพื่อแยกว่าต้องแสดง choice หรือเปล่า
     */
    public boolean isLastDialogueSpecial() {
        return lastDialogueWasSpecial;
    }

    /**
     * คืนค่า heartLevel ตอนที่ trigger special scene
     * ใช้ส่งต่อให้ getSpecialSceneChoices / getChoiceResponse
     */
    public int getLastSpecialSceneLevel() {
        return lastSpecialSceneLevel;
    }

    /**
     * Reset flag หลังจาก LazelPanel อ่านค่าไปแล้ว
     * เรียกหลังจาก showSpecialSceneWithChoices() เสร็จ
     */
    public void resetSpecialSceneFlag() {
        lastDialogueWasSpecial = false;
    }

    protected abstract DialogueNode getDialogueTree(int sceneLevel);
    
}