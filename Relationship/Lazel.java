package Relationship;

import java.util.Random;

import javax.swing.JPanel;

public class Lazel extends NPC {

    public Lazel() {
        super("Lazel");
        //heartLevel = 2  ;
    }

    @Override
    protected String getRandomDialogue(int level) {
        String[] dialogues;
        if (level < 2) {
            dialogues = new String[]{
                "มองหน้าทำไม? อยากโดนดาบเฉือนจมูกเล่นหรือไงฮะ?" ,
                "หลีกไป! ข้าต้องรีบไปลับดาบ... อย่ามาเกะกะขวางทาง!",
                "เจ้า... ดูท่าทางอ่อนแอชะมัด จะรอดคืนนี้ไหมเนี่ย?" ,
                "ถ้าเจอสไลม์ก็อย่าร้องไห้ล่ะ... ข้าไม่ว่างไปช่วยเช็ดน้ำตาหรอกนะ" ,
                "ดาบของเจ้า... สนิมเขรอะเชียว... ดูแลอาวุธบ้างนะเจ้าบื้อ" ,
                "ข้าคือ Lazel นักล่ามังกรที่เก่งที่สุด! จำชื่อใส่กะโหลกไว้ซะ!" ,
                "อย่ามาเดินตามข้า! ข้าไม่ใช่พี่เลี้ยงเด็กนะเว้ย!" ,
                "อะไร? จะท้าดวลเหรอ? เข้ามาสิ... ข้าต่อให้ใช้มือเดียวเลย!" ,
                "น่ารำคาญจริง... ไปให้พ้นหน้าข้าได้แล้ว!" ,
                "หมู่นี้พวกมือสมัครเล่นเยอะจริง... เจ้าก็หนึ่งในนั้นสินะ"
            };
        } else if (level < 4) {
            dialogues = new String[]{
                "ฝีมือการต่อสู้ของเจ้า... ก็พอใช้ได้ ไม่เลวนัก",
                "วันนี้เจ้าดู... แข็งแกร่งขึ้นนะ (หลบตา)",
                "เราจะล่านกอะไรมากินดีเย็นนี้?"
            };
        } else {
            dialogues = new String[]{
                "เจ้าคือคู่หูที่แข็งแกร่งที่สุดเท่าที่ข้าเคยมี",
                "ใครหน้าไหนกล้าแตะต้องเจ้า มันต้องผ่านดาบข้าไปก่อน",
                "มานี่สิ... ข้าอยาก... ดมกลิ่นเจ้าใกล้ๆ (หน้าแดง)"
            };
        }
        return dialogues[new Random().nextInt(dialogues.length)];
    }

    @Override
    protected JPanel getSpecialScene(int level) {
        // คืนค่าเป็น Text ยาวๆ สำหรับใส่ใน SceneUpdate
        return switch (level) {
            case 1 -> LazelScene1.createPanel(e -> {
            String endId = e.getActionCommand();
            addAffection(LazelScene1.getAffectionBonus(endId));
        });
             default -> null; // level อื่นที่ยังไม่มี scene
        };
    }
}