package Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * โหนดหนึ่งใน Dialogue Tree
 * 
 * โครงสร้าง:
 *   - speaker  : ผู้พูด ("Lazel" หรือ "Player")
 *   - text     : บทพูด
 *   - choices  : ตัวเลือกที่ผู้เล่นกดได้ (ถ้าว่าง = จบบท / END)
 *   - isEnd    : true = จบ scene แล้ว (แสดงปุ่ม Continue)
 */
public class DialogueNode {

    public String speaker;    // "Lazel" หรือ "Player"
    public String text;       // บทพูด
    public boolean isEnd;     // true = END node (จบ scene)
    public String imagePath;  // null = ใช้รูป default, ใส่ path = เปลี่ยนรูป

    // รายการ choice ที่แตกออกจาก node นี้
    public List<Choice> choices = new ArrayList<>();

    // ======================================
    // Inner class: Choice แต่ละตัวเลือก
    // ======================================
    public static class Choice {
        public String label;          // ข้อความบนปุ่ม (สิ่งที่ผู้เล่นพูด)
        public DialogueNode next;     // node ถัดไปหลังเลือก

        public Choice(String label, DialogueNode next) {
            this.label = label;
            this.next  = next;
        }
    }

    // ======================================
    // Constructor
    // ======================================

    /** Node ทั่วไป (มี choice ต่อ) */
    public DialogueNode(String speaker, String text) {
        this.speaker = speaker;
        this.text    = text;
        this.isEnd   = false;
    }

    /** END node (จบ scene) */
    public DialogueNode(String speaker, String text, boolean isEnd) {
        this.speaker = speaker;
        this.text    = text;
        this.isEnd   = isEnd;
    }

    // ======================================
    // Helper: เพิ่ม choice
    // ======================================
    public DialogueNode addChoice(String label, DialogueNode next) {
        choices.add(new Choice(label, next));
        return this; // คืน this เพื่อ chain ได้
    }
}