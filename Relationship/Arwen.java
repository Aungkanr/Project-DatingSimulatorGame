package Relationship;

import UXUI.MainFrame;
import java.util.Random;

public class Arwen extends NPC {
    public String imagePath;

    public Arwen() {
        super("Arwen");
    }

    @Override
    protected String getRandomDialogue(int level) {
        String[] dialogues;
        if (level < 2) {
            dialogues = new String[]{
                "อรุณสวัสดิ์ค่ะท่านนักเดินทาง... วันนี้ลมดีนะ ท่านว่าไหม?",
                "หากท่านบาดเจ็บจากการล่าอสูร... มาหาข้าได้นะ ข้ามียาสมานแผลอยู่บ้าง",
                "หมู่บ้านของเราเงียบสงบดีใช่ไหมคะ? หวังว่าท่านจะชอบที่นี่นะ",
                "ระวังตัวด้วยนะคะ ป่าหมอกด้านนอกมีสัตว์ร้ายเพ่นพ่านเต็มไปหมด",
                "ท่านดูเหนื่อยล้านะ... อยากได้น้ำสมุนไพรสักแก้วไหมคะ?",
                "ยินดีที่ได้พบท่านค่ะ... แม้เราจะยังไม่คุ้นเคยกันนัก",
                "ข้ากำลังตากสมุนไพรอยู่... กลิ่นอาจจะฉุนไปหน่อย ต้องขออภัยด้วยนะคะ",
                "เสื้อผ้าท่านเปื้อนโคลนหมดแล้ว... การผจญภัยคงหนักหนาสินะคะ",
                "ขอให้ท่านเทพธิดาคุ้มครองทุกย่างก้าวของท่านนะคะ",
                "มีอะไรให้เพื่อนบ้านอย่างข้าช่วย ก็บอกได้เลยนะคะ"
            };
        } else if (level < 4) {
            dialogues = new String[]{
                "อ้าว! ท่านนั่นเอง... เห็นหน้าท่านแล้วข้ารู้สึกอุ่นใจจัง",
                "วันนี้ข้าอบขนมปังสูตรพิเศษมาเผื่อท่านด้วยนะ ลองชิมดูสิ",
                "ท่านว่างไหมคะ? ข้าอยากชวนมาจิบชายามบ่ายที่ระเบียงสักหน่อย",
                "การมีท่านอยู่ข้างบ้าน... ทำให้ข้าไม่ต้องกลัวเสียงฟ้าร้องอีกแล้วล่ะ",
                "ท่านเก่งจังเลยนะ ทั้งล่ามังกร ทั้งดูแลตัวเอง... ข้านับถือท่านจริงๆ",
                "เมื่อคืนข้าฝันร้าย... แต่พอตื่นมาเจอท่าน ข้าก็ลืมมันไปหมดเลย",
                "วันนี้ท่านดูสดใสนะ... ไปเจอเรื่องดีๆ มาหรือคะ?",
                "ข้าปลูกสมุนไพรต้นใหม่ได้แล้วนะ! อยากให้ท่านมาดูเป็นคนแรกเลย",
                "อย่าหักโหมนักนะคะ... พักผ่อนบ้าง ร่างกายท่านสำคัญนะ",
                "รอยยิ้มของท่าน... ทำให้วันธรรมดาของข้ามีความหมายขึ้นมาเลยค่ะ"
            };
        } else {
            dialogues = new String[]{
                "กลับมาแล้วหรือคะที่รัก? ข้าเตรียมน้ำอุ่นไว้รอท่านแล้วนะ",
                "ข้าเป็นห่วงแทบแย่ตอนท่านออกไป... สัญญาได้ไหมว่าจะกลับมาหาข้าเสมอ?",
                "ท่านคือดวงดาวนำทางของข้า... ไม่ว่าจะมืดมิดแค่ไหน ข้าก็ไม่หลงทางอีกแล้ว",
                "ขอแค่มีท่านอยู่ข้างกาย... กระท่อมหลังน้อยนี้ก็คือวิมานสำหรับข้าค่ะ",
                "มาให้ข้ากอดหน่อยสิ... ท่านตัวหอมกลิ่นการผจญภัยจังเลย",
                "ในสายตาข้า... ไม่มีอัศวินคนใดสง่างามไปกว่าท่านอีกแล้วค่ะ",
                "ข้ารักท่านนะ... มากกว่าเวทมนตร์บทใดๆ ในโลกนี้เสียอีก",
                "ทุกวินาทีที่ได้อยู่กับท่าน... คือของขวัญล้ำค่าที่สุดจากสวรรค์ค่ะ",
                "ไม่ว่าวันข้างหน้าจะเจออุปสรรคใด... ข้าจะยืนเคียงข้างท่านตลอดไปนะ",
                "เหนื่อยไหมคะคนดี? ...หนุนตักข้าพักผ่อนก่อนนะ"
            };
        }
        return dialogues[new Random().nextInt(dialogues.length)];
    }

    @Override
    protected String getSpecialScene(int level) {
        return switch (level) {
            case 1 -> "— พบกับ Arwen เป็นครั้งแรก —";
            case 2 -> "— Arwen เริ่มไว้ใจเจ้า —";
            case 3 -> "— Arwen ยอมรับเจ้าเป็นสหาย —";
            case 4 -> "— Arwen เปิดใจให้เจ้า —";
            case 5 -> "— Arwen สารภาพรัก —";
            default -> "...";
        };
    }

    @Override
    public DialogueNode getDialogueTree(int level, MainFrame mainFrame) {
        return switch (level) {
            case 1 -> buildLevel1Tree(mainFrame);
            case 2 -> buildLevel2Tree(mainFrame);
            case 3 -> buildLevel3Tree(mainFrame);
            case 4 -> buildLevel4Tree(mainFrame);
            case 5 -> buildLevel5Tree(mainFrame);
            default -> buildLevel1Tree(mainFrame);
        };
    }

    // ============================================================
    // Level 1 Dialogue Tree
    // ============================================================
    private DialogueNode buildLevel1Tree(MainFrame mainFrame) {

        DialogueNode endBad    = new DialogueNode("Arwen", "(เดินจากไปเงียบๆ)", true);
        endBad.imagePath = "image\\Scene\\Arwen\\Scene1\\เดินจากไปเงียบๆ.png";
        DialogueNode endNormal = new DialogueNode("Arwen", "(ยิ้มตามมารยาท) ค่ะ... ไว้เจอกันโอกาสหน้านะคะ", true);
        endNormal.imagePath = "image\\Scene\\Arwen\\Scene1\\ยิ้มตามมารยาท.png";
        DialogueNode endGood   = new DialogueNode("Arwen", "(ยื่นของให้) ยินดีต้อนรับสู่หมู่บ้านของเรานะคะ ขอบคุณที่คุยด้วย", true);
        endGood.imagePath = "image\\Scene\\Arwen\\Scene1\\ยื่นของให้ฉากจบ.png";
        DialogueNode endBest   = new DialogueNode("Arwen", "(ยื่นของให้พร้อมรอยยิ้มหวานที่สุด) นี่ค่ะ... ยินดีต้อนรับสู่หมู่บ้านของเรานะคะ หวังว่าเราจะได้คุยกันบ่อยๆ นะคะท่าน", true);
        endBest.imagePath = "image\\Scene\\Arwen\\Scene1\\(ยื่นของให้พร้อมรอยยิ้มหวานที่สุด) _นี่ค่ะ... ยินดีต้อนรับสู่หมู่บ้านของเรานะคะ หวังว่าเราจะได้คุยกันบ่อยๆ นะคะท่าน_.png";

        DialogueNode n1_5A = new DialogueNode("Arwen",
            "(ยื่นของให้พร้อมรอยยิ้มหวานที่สุด) นี่ค่ะ... ยินดีต้อนรับสู่หมู่บ้านของเรานะคะ หวังว่าเราจะได้คุยกันบ่อยๆ นะคะท่าน");
        n1_5A.imagePath = "image\\Scene\\Arwen\\Scene1\\ยื่นของให้ยิ้มหวานฉากจบ.png";
        n1_5A.addChoice("แน่นอนครับ ผมจะมาหาทุกวันเลย", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ Arwen", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ไปล่ะครับ ไว้เจอกัน", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n1_5B = new DialogueNode("Arwen",
            "ถ้าขาดเหลืออะไร บอกข้าได้เสมอเลยนะคะ... เดินทางกลับบ้านปลอดภัยนะคะ ท่านเพื่อนบ้าน");
        n1_5B.imagePath = "image\\Scene\\Arwen\\Scene1\\_ถ้าขาดเหลืออะไร บอกข้าได้เสมอเลยนะคะ... เดินทางกลับบ้านปลอดภัยนะคะ ท่านเพื่อนบ้าน_.png";
        n1_5B.addChoice("ขอบคุณครับ เจอกันใหม่นะ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("อืม บาย", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("มีอะไรให้กินอีกไหม?", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n1_5C = new DialogueNode("Arwen",
            "ค่ะ... ไว้เจอกันโอกาสหน้านะคะ (ยิ้มตามมารยาท)");
        n1_5C.imagePath = "image\\Scene\\Arwen\\Scene1\\_ค่ะ... ไว้เจอกันโอกาสหน้านะคะ (ยิ้มตามมารยาท)_.png";
        n1_5C.addChoice("ครับ สวัสดี", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("(เดินจากไปเงียบๆ)", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("เสียเวลาชะมัด", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n1_4A = new DialogueNode("Arwen",
            "เย้! ข้าดีใจที่ท่านชอบ! ...งั้นเดี๋ยวข้าจะแบ่งใส่ขวดให้ท่านเอากลับไปทานที่บ้านด้วยนะคะ รอสักครู่นะ");
        n1_4A.imagePath = "image\\Scene\\Arwen\\Scene1\\_เย้! ข้าดีใจที่ท่านชอบ! ...งั้นเดี๋ยวข้าจะแบ่งใส่ขวดให้ท่านเอากลับไปทานที่บ้านด้วยนะคะ รอสักครู่นะ_.png";
        n1_4A.addChoice("ขอบคุณครับ รบกวนด้วยนะ", n1_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ไม่ต้องลำบากหรอกครับ แค่นี้ก็พอแล้ว", n1_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ถ้าท่านเป็นคนป้อน ผมถึงจะเอานะ", n1_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5));

        DialogueNode n1_4B = new DialogueNode("Arwen",
            "ข้าหวังว่าคราวหน้าข้าจะทำสิ่งที่ถูกปากท่านได้บ้าง... ข้าอยากเป็นเพื่อนบ้านที่ดีของท่านนะคะ");
        n1_4B.imagePath = "image\\Scene\\Arwen\\Scene1\\_ข้าหวังว่าคราวหน้าข้าจะทำสิ่งที่ถูกปากท่านได้บ้าง... ข้าอยากเป็นเพื่อนบ้านที่ดีของท่านนะคะ_.png";
        n1_4B.addChoice("แค่นี้ก็ดีมากแล้วครับ Arwen ไม่ต้องกังวลนะ", n1_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("พยายามเข้านะครับ คราวหน้าขออร่อยกว่านี้นะ", n1_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ครับ หวังว่านะ", n1_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n1_4C = new DialogueNode("Arwen",
            "(ยิ้มบางๆ) ท่านเป็นคนพูดตรงดีนะคะ... ข้าชอบคนจริงใจแบบท่านนะ สบายใจดีที่ได้คุยด้วย");
        n1_4C.imagePath = "image\\Scene\\Arwen\\Scene1\\(ยิ้มกว้าง) _ท่านจมูกไวมาก! นี่คือ _ยาวิเศษคลายกังวล_ ค่ะ ข้าปรุงไว้แจกคนในหมู่บ้าน... ท่านสนใจจะลองชิมเป็นคนแรกไหมคะ_.png";
        n1_4C.addChoice("ผมก็ชอบคุยกับท่านครับ", n1_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมก็พูดไปตามเนื้อผ้าแหละครับ", n1_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ชมเกินไปแล้วครับ", n1_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n1_3A = new DialogueNode("Arwen",
            "(ตักยาใส่ถ้วยยื่นให้) ระวังร้อนนะคะ... ค่อยๆ จิบนะ... รสชาติเป็นอย่างไรบ้างคะ?");
        n1_3A.imagePath = "image\\Scene\\Arwen\\Scene1\\(ตักยาใส่ถ้วยยื่นให้) _ระวังร้อนนะคะ... ค่อยๆ จิบนะ... รสชาติเป็นอย่างไรบ้างคะ_.png";
        n1_3A.addChoice("อึก... รสชาติหวานชุ่มคอมาก! สุดยอดไปเลย!", n1_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขมปี๋เลย! ...แต่ก็รู้สึกสดชื่นนะ", n1_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("อร่อยครับ... (ฝืนยิ้มทั้งน้ำตา)", n1_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n1_3B = new DialogueNode("Arwen",
            "น่าเสียดายจัง... แต่ไม่เป็นไรค่ะ งั้นข้าจะห่อขนมปังอบแห้งให้ท่านแทนละกันนะคะ รับไว้เถอะนะ ถือเป็นการต้อนรับ");
        n1_3B.imagePath = "image\\Scene\\Arwen\\Scene1\\_น่าเสียดายจัง... แต่ไม่เป็นไรค่ะ งั้นข้าจะห่อขนมปังอบแห้งให้ท่านแทนละกันนะคะ รับไว้เถอะนะ ถือเป็นการต้อนรับ_.png";
        n1_3B.addChoice("ขอบคุณครับ ท่านใจดีจัง", n1_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ไม่เอาครับ ผมไม่หิว", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("รับแค่น้ำใจก็พอครับ เก็บไว้ทานเองเถอะ", n1_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n1_3C = new DialogueNode("Arwen",
            "ช่วยให้นอนหลับสบายและฝันดีค่ะ... ข้าตั้งใจปรุงสุดฝีมือเลยนะ ท่านไม่ลองจริงๆ เหรอ?");
        n1_3C.imagePath = "image\\Scene\\Arwen\\Scene1\\_ช่วยให้นอนหลับสบายและฝันดีค่ะ... ข้าตั้งใจปรุงสุดฝีมือเลยนะ ท่านไม่ลองจริงๆ เหรอ_.png";
        n1_3C.addChoice("โอเคครับ เห็นแก่ความตั้งใจของท่านนะ", n1_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ไม่ล่ะครับ ขอบคุณ", n1_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("บ้านท่านสวยดีนะครับ ปลูกดอกไม้เยอะจัง", n1_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n1_2A = new DialogueNode("Arwen",
            "(ยิ้มกว้าง) ท่านจมูกไวมาก! นี่คือ 'ยาวิเศษคลายกังวล' ค่ะ ข้าปรุงไว้แจกคนในหมู่บ้าน... ท่านสนใจจะลองชิมเป็นคนแรกไหมคะ?");
        n1_2A.imagePath = "image\\Scene\\Arwen\\Scene1\\(ยิ้มกว้าง) _ท่านจมูกไวมาก! นี่คือ _ยาวิเศษคลายกังวล_ ค่ะ ข้าปรุงไว้แจกคนในหมู่บ้าน... ท่านสนใจจะลองชิมเป็นคนแรกไหมคะ_.png";
        n1_2A.addChoice("ด้วยความยินดีครับ ถ้าท่านปรุงต้องดีแน่ๆ", n1_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("เกรงใจครับ ผมเพิ่งทานข้าวมา", n1_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("กินแล้วจะกลายเป็นกบไหมเนี่ย?", n1_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n1_2B = new DialogueNode("Arwen",
            "(หน้าสลดลง) ข...ขอโทษจริงๆ ค่ะ! ข้าจะรีบดับไฟเดี๋ยวนี้แหละ... ท่านอย่าโกรธเลยนะ");
        n1_2B.imagePath = "image\\Scene\\Arwen\\Scene1\\(หน้าสลดลง) _ข...ขอโทษจริงๆ ค่ะ! ข้าจะรีบดับไฟเดี๋ยวนี้แหละ... ท่านอย่าโกรธเลยนะ_.png";
        n1_2B.addChoice("เอ่อ... ผมพูดแรงไป ขอโทษครับ", n1_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ดีครับ ดับไปเลย รบกวนคนอื่น", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20))
             .addChoice("ช่างเถอะ คราวหลังระวังหน่อย", n1_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n1_2C = new DialogueNode("Arwen",
            "มันคือน้ำสมุนไพรสูตรคุณยายค่ะ สีอาจจะดูน่ากลัวไปหน่อย แต่สรรพคุณดีมากนะคะ... ท่านกล้าลองไหม?");
        n1_2C.imagePath = "image\\Scene\\Arwen\\Scene1\\_เย้! ข้าดีใจที่ท่านชอบ! ...งั้นเดี๋ยวข้าจะแบ่งใส่ขวดให้ท่านเอากลับไปทานที่บ้านด้วยนะคะ รอสักครู่นะ_.png";
        n1_2C.addChoice("ส่งมาเลยครับ ผมจะดื่มโชว์ให้ดู", n1_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("สีเหมือนยาพิษเลย ขอบายดีกว่า", n1_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("มันช่วยเรื่องอะไรบ้างเหรอครับ?", n1_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode root = new DialogueNode("Arwen",
            "(เธอกำลังคนหม้อปรุงยาใบใหญ่ที่ส่งควันโขมงหน้าบ้าน) \"อ๊ะ! ท่าน... ท่านคือเพื่อนบ้านคนใหม่ใช่ไหมคะ? ข้าขอโทษด้วยนะคะถ้ากลิ่นสมุนไพรรบกวนท่าน\"");
        root.imagePath = "image\\Scene\\Arwen\\Scene1\\(เธอกำลังคนหม้อปรุงยาใบใหญ่ที่ส่งควันโขมงหน้าบ้าน) อ๊ะ! ท่าน... ท่านคือเพื่อนบ้านคนใหม่ใช่ไหมคะ_ ข้าขอโทษด้วยนะคะถ้ากลิ่นสมุนไพรรบกวนท่าน.png";
        root.addChoice("ไม่รบกวนเลยครับ กลิ่นหอมเหมือนลาเวนเดอร์เลย", n1_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("กลิ่นเหม็นมากเลยครับ ทำอะไรอยู่เนี่ย?", n1_2B,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20))
             .addChoice("ท่านกำลังต้มอะไรอยู่เหรอครับ? ควันโขมงเชียว", n1_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        return root;
    }

    // ============================================================
    // Level 2 Dialogue Tree
    // ============================================================
    private DialogueNode buildLevel2Tree(MainFrame mainFrame) {

        DialogueNode endBad    = new DialogueNode("Arwen", "(เดินกลับบ้านไปเงียบๆ)", true);
        endBad.imagePath = "image\\Scene\\Arwen\\Scene2\\เดินจากไปแบบเงียบๆ.png";
        DialogueNode endNormal = new DialogueNode("Arwen", "ค่ะ... เข้าใจแล้วค่ะ ข้าคงจุ้นจ้านเกินไป... ขอตัวนะคะ", true);
        endNormal.imagePath = "image\\Scene\\Arwen\\Scene2\\ขออภัยendscene.png";
        DialogueNode endGood   = new DialogueNode("Arwen", "ดูแลตัวเองดีๆ นะคะ ถ้าต้องการสมุนไพรเพิ่ม มาหาข้าได้เสมอนะ", true);
        endGood.imagePath = "image\\Scene\\Arwen\\Scene2\\ดูแลตัวเองดีๆนะ.png";
        DialogueNode endBest   = new DialogueNode("Arwen", "(ทายาให้อย่างเบามือแล้วเป่าเบาๆ) เรียบร้อยค่ะ... หายไวๆ นะคะท่านอัศวิน... ข้า... ข้ามีความสุขที่ได้ดูแลท่านนะ", true);
        endBest.imagePath = "image\\Scene\\Arwen\\Scene2\\ทายา endscene.png";

        DialogueNode n2_5A = new DialogueNode("Arwen",
            "(ทายาให้อย่างเบามือแล้วเป่าเบาๆ) เรียบร้อยค่ะ... หายไวๆ นะคะท่านอัศวิน... (หน้าแดงเล็กน้อย) ข้า... ข้ามีความสุขที่ได้ดูแลท่านนะ");
        n2_5A.imagePath = "image\\Scene\\Arwen\\Scene2\\หายไวๆนะคะท่านอัศวิน.png";
        n2_5A.addChoice("ผมก็มีความสุขครับที่ได้ท่านดูแล", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("มือท่านเบามากเลย ขอบคุณครับ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เสร็จแล้วใช่ไหม? ขอบคุณ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n2_5B = new DialogueNode("Arwen",
            "ดูแลตัวเองดีๆ นะคะ ข้าไม่อยากเห็นท่านเจ็บตัวเลย... ถ้าต้องการสมุนไพรเพิ่ม มาหาข้าได้เสมอนะ");
        n2_5B.imagePath = "image\\Scene\\Arwen\\Scene2\\ดูแลตัวเองดีๆนะคะท่าน.png";
        n2_5B.addChoice("แน่นอนครับ จะไปหาบ่อยๆ เลย", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณที่เป็นห่วงครับ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ไม่รบกวนหรอกครับ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_5C = new DialogueNode("Arwen",
            "ค่ะ... เข้าใจแล้วค่ะ ข้าคงจุ้นจ้านเกินไป... ขอตัวนะคะ (เดินกลับบ้านไปเงียบๆ)");
        n2_5C.imagePath = "image\\Scene\\Arwen\\Scene2\\เดินกลับบ้านไปแบบเงียบๆ.png";
        n2_5C.addChoice("เดี๋ยว Arwen! ผมไม่ได้หมายความแบบนั้น", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("(มองตามเฉยๆ)", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("ไปซะที", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n2_4A = new DialogueNode("Arwen",
            "ไม่ได้นะคะ! แผลเล็กๆ ก็ติดเชื้อได้... รอเดี๋ยวนะคะ ข้าพกขี้ผึ้งสมานแผลมาด้วยพอดี (หยิบตลับยาออกมา)");
        n2_4A.imagePath = "image\\Scene\\Arwen\\Scene2\\ไม่ได้นะคะแผลเล็กๆก็ติดเชื้อได้.png";
        n2_4A.addChoice("ยื่นมือให้ \"รบกวนด้วยนะครับ คุณพยาบาลส่วนตัว\"", n2_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ ท่านรอบคอบจัง", n2_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ไม่เป็นไรครับ ผมทาเองได้", n2_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_4B = new DialogueNode("Arwen",
            "ถึงจะไกลหัวใจก็ต้องดูแลนะคะ... มาค่ะ ข้าจะเป่าเพี้ยงให้... เหมือนตอนข้าทำให้หลานๆ มันช่วยให้หายเจ็บได้จริงๆ นะ");
        n2_4B.imagePath = "image\\Scene\\Arwen\\Scene2\\ถึงจะไกลหัวใจแต่ต้องดูแลตัวเองนะคะ.png";
        n2_4B.addChoice("ฮ่าๆ เอาสิครับ เพี้ยง!", n2_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมไม่ใช่เด็กนะครับ Arwen", n2_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ไร้สาระครับ", n2_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n2_4C = new DialogueNode("Arwen",
            "ท่านนี่ดื้อเงียบเหมือนกันนะคะเนี่ย... เอาล่ะ ข้าไม่กวนแล้ว ทานขนมปังให้อร่อยนะคะ");
        n2_4C.imagePath = "image\\Scene\\Arwen\\Scene2\\ท่านนี้คือเงียบเหมือนกันนะคะเนี่ย.png";
        n2_4C.addChoice("ขอบคุณครับ ไว้คุยกันใหม่", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ครับผม", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("บาย", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_3A = new DialogueNode("Arwen",
            "ทานตอนร้อนๆ อร่อยที่สุดนะคะ... เอ๊ะ? มือท่าน... มีแผลนี่คะ? ไปโดนอะไรมา?");
        n2_3A.imagePath = "image\\Scene\\Arwen\\Scene2\\_ทานตอนร้อนๆ อร่อยที่สุดนะคะ... เอ๊ะ_ มือท่าน... มีแผลนี่คะ_ ไปโดนอะไรมา_.png";
        n2_3A.addChoice("แค่อุบัติเหตุนิดหน่อยตอนซ้อมดาบครับ", n2_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เรื่องเล็กน้อยครับ ไกลหัวใจ", n2_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("อย่าสนใจเลยครับ รีบกินกันเถอะ", n2_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_3B = new DialogueNode("Arwen",
            "สูตรนี้ใส่ผลไม้แห้งด้วยนะคะ รับรองว่าอร่อย... แต่ว่า... สีหน้าท่านดูเพลียๆ นะคะ ไม่สบายตรงไหนหรือเปล่า?");
        n2_3B.imagePath = "image\\Scene\\Arwen\\Scene2\\_สูตรนี้ใส่ผลไม้แห้งด้วยนะคะ รับรองว่าอร่อย... แต่ว่า... สีหน้าท่านดูเพลียๆ นะคะ ไม่สบายตรงไหนหรือเปล่า_.png";
        n2_3B.addChoice("แค่เหนื่อยนิดหน่อยครับ ขอบคุณที่เป็นห่วง", n2_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("สบายมากครับ แข็งแรงจะตาย", n2_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ยุ่งน่า", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_3C = new DialogueNode("Arwen",
            "(วางตะกร้าไว้ให้) งั้นข้าวางไว้ตรงนี้นะคะ... ทานให้อร่อยนะ (ทำท่าจะเดินกลับ)");
        n2_3C.imagePath = "image\\Scene\\Arwen\\Scene2\\(วางตะกร้าไว้ให้) _งั้นข้าวางไว้ตรงนี้นะคะ... ทานให้อร่อยนะ_ (ทำท่าจะเดินกลับ).png";
        n2_3C.addChoice("เดี๋ยวครับ! ...รับไว้ทานด้วยกันไหม?", n2_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("(ปิดประตูบ้าน)", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n2_2A = new DialogueNode("Arwen",
            "(ยิ้มโล่งอก) ดีจังเลยค่ะ... คือว่าเมื่อเช้าข้าอบขนมปังธัญพืชสูตรพิเศษ กลิ่นมันหอมฟุ้งจนข้าอดคิดถึงท่านไม่ได้ เลยอยากเอามาแบ่งน่ะค่ะ");
        n2_2A.imagePath = "image\\Scene\\Arwen\\Scene2\\(ยิ้มโล่งอก) _ดีจังเลยค่ะ... คือว่าเมื่อเช้าข้าอบขนมปังธัญพืชสูตรพิเศษ กลิ่นมันหอมฟุ้งจนข้าอดคิดถึงท่านไม่ได้ เลยอยากเอามาแบ่งน่ะค่ะ_.png";
        n2_2A.addChoice("ท่านใจดีเสมอเลย ขอบคุณมากครับ", n2_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("น่าทานจัง! ผมกำลังหิวพอดีเลย", n2_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("หวังว่าคราวนี้คงไม่มีสมุนไพรแปลกๆ นะ", n2_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n2_2B = new DialogueNode("Arwen",
            "ว้าย! ข้าขอโทษจริงๆ ค่ะที่มารบกวนเวลาพักผ่อน... ข้าแค่ตั้งใจจะเอาขนมปังมาแขวนไว้ให้เฉยๆ ไม่นึกว่าท่านจะออกมา");
        n2_2B.imagePath = "image\\Scene\\Arwen\\Scene2\\_ว้าย! ข้าขอโทษจริงๆ ค่ะที่มารบกวนเวลาพักผ่อน... ข้าแค่ตั้งใจจะเอาขนมปังมาแขวนไว้ให้เฉยๆ ไม่นึกว่าท่านจะออกมา_.png";
        n2_2B.addChoice("ไม่เป็นไรครับ ไหนๆ ก็ตื่นแล้ว ขอดูกหน่อยสิ", n2_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ วางไว้ตรงนั้นแหละ", n2_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ทีหลังมาสายกว่านี้นะครับ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n2_2C = new DialogueNode("Arwen",
            "ข้าเอาขนมปังอบใหม่ๆ มาฝากค่ะ... เห็นท่านดูยุ่งๆ กับการจัดบ้าน เลยคิดว่าอาจจะยังไม่ได้ทานอะไร");
        n2_2C.imagePath = "image\\Scene\\Arwen\\Scene2\\_ข้าเอาขนมปังอบใหม่ๆ มาฝากค่ะ... เห็นท่านดูยุ่งๆ กับการจัดบ้าน เลยคิดว่าอาจจะยังไม่ได้ทานอะไร_.png";
        n2_2C.addChoice("ท่านช่างสังเกตจัง ขอบคุณครับ", n2_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมทานแล้วครับ แต่จะรับไว้ก็ได้", n2_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ไม่ล่ะครับ ผมไม่ชอบขนมปัง", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode root = new DialogueNode("Arwen",
            "(ยืนถือตะกร้าหวายที่มีผ้าคลุมอยู่หน้าบ้านคุณ) \"สวัสดีค่ะท่านเพื่อนบ้าน! ข้า... ข้ามารบกวนท่านหรือเปล่าคะ?\"");
        root.imagePath = "image\\Scene\\Arwen\\Scene2\\(ยืนถือตะกร้าหวายที่มีผ้าคลุมอยู่หน้าบ้านคุณ) _สวัสดีค่ะท่านเพื่อนบ้าน! ข้า... ข้ามารบกวนท่านหรือเปล่าคะ_.png";
        root.addChoice("ไม่รบกวนเลยครับ ผมตื่นนานแล้ว", n2_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("นิดหน่อยครับ ผมยังง่วงอยู่เลย", n2_2B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("มีธุระอะไรเหรอครับ?", n2_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        return root;
    }

    // ============================================================
    // Level 3 Dialogue Tree
    // ============================================================
    private DialogueNode buildLevel3Tree(MainFrame mainFrame) {

        DialogueNode endBad    = new DialogueNode("Arwen", "(ยิ้มเศร้าๆ) เหนื่อยฟรีจริงๆ เลย", true);
        endBad.imagePath = "image\\Scene\\Arwen\\Scene3\\ยิ้มเศร้าๆเหนื่อยฟรีจริงๆเลย.png";
        DialogueNode endNormal = new DialogueNode("Arwen", "(ยิ้มเศร้าๆ) ถึงจะไม่ได้สมุนไพรครบ... แต่ข้าก็ขอบคุณที่ท่านอุตส่าห์มาเป็นเพื่อนนะคะ", true);
        endNormal.imagePath = "image\\Scene\\Arwen\\Scene3\\ยิ้มเศร้าๆเหนื่อยฟรีจริงๆเลย.png";
        DialogueNode endGood   = new DialogueNode("Arwen", "วันนี้เหนื่อยหน่อยนะคะ... กลับไปถึงบ้าน ข้าจะทำซุปอุ่นๆ ให้ท่านทานเป็นการตอบแทนนะ", true);
        endGood.imagePath = "image\\Scene\\Arwen\\Scene3\\endgood.png";
        DialogueNode endBest   = new DialogueNode("Arwen", "(หลังจากรอดมาได้ เธอเข้ามากอดคุณแน่น) ขอบคุณจริงๆ ค่ะ... ถ้าไม่มีท่าน ข้าต้องแย่แน่ๆ... ท่านคือผู้ช่วยชีวิตของข้าเลยนะคะ", true);
        endBest.imagePath = "image\\Scene\\Arwen\\Scene3\\endBest.png";

        DialogueNode n3_5A = new DialogueNode("Arwen",
            "(หลังจากรอดมาได้ เธอเข้ามากอดคุณแน่น) ขอบคุณจริงๆ ค่ะ... ถ้าไม่มีท่าน ข้าต้องแย่แน่ๆ... ท่านคือผู้ช่วยชีวิตของข้าเลยนะคะ");
        n3_5A.imagePath = "image\\Scene\\Arwen\\Scene3\\(หลังจากรอดมาได้ เธอเข้ามากอดคุณแน่น) _ขอบคุณจริงๆ ค่ะ... ถ้าไม่มีท่าน ข้าต้องแย่แน่ๆ... ท่านคือผู้ช่วยชีวิตของข้าเลยนะคะ_.png";
        n3_5A.addChoice("ผมยินดีดูแลคุณตลอดไปครับ", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ยินดีที่ได้ช่วยครับ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("คราวหลังอย่าทำหายอีกล่ะ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n3_5B = new DialogueNode("Arwen",
            "วันนี้เหนื่อยหน่อยนะคะ... กลับไปถึงบ้าน ข้าจะทำซุปอุ่นๆ ให้ท่านทานเป็นการตอบแทนนะ");
        n3_5B.imagePath = "image\\Scene\\Arwen\\Scene3\\_วันนี้เหนื่อยหน่อยนะคะ... กลับไปถึงบ้าน ข้าจะทำซุปอุ่นๆ ให้ท่านทานเป็นการตอบแทนนะ.png";
        n3_5B.addChoice("แค่มีคุณอยู่ด้วย ผมก็หายเหนื่อยแล้ว", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ จะรอนะ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ขอเพิ่มขนมหวานด้วยนะ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n3_5C = new DialogueNode("Arwen",
            "(ยิ้มเศร้าๆ) ถึงจะไม่ได้สมุนไพรครบ... แต่ข้าก็ขอบคุณที่ท่านอุตส่าห์มาเป็นเพื่อนนะคะ...");
        n3_5C.imagePath = "image\\Scene\\Arwen\\Scene3\\_ถึงจะไม่ได้สมุนไพรครบ... แต่ข้าก็ขอบคุณที่ท่านอุตส่าห์มาเป็นเพื่อนนะคะ... (ยิ้มเศร้าๆ)_.png";
        n3_5C.addChoice("ขอโทษนะที่ช่วยได้ไม่เต็มที่", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ไม่เป็นไรครับ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("เหนื่อยฟรีจริงๆ เลย", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n3_4A = new DialogueNode("Arwen",
            "(เจอสมุนไพรแล้ว!) เจอแล้ว! อยู่นี่เอง! ...แต่ เอ๊ะ? มีมอนสเตอร์ตัวใหญ่ขวางอยู่! เราจะทำยังไงดีคะ?");
        n3_4A.imagePath = "image\\Scene\\Arwen\\Scene3\\(เจอสมุนไพรแล้ว!) _เจอแล้ว! อยู่นี่เอง! ...แต่ เอ๊ะ_ มีมอนสเตอร์ตัวใหญ่ขวางอยู่! เราจะทำยังไงดีคะ_.png";
        n3_4A.addChoice("เดี๋ยวผมล่อมันเอง คุณรีบไปหยิบสมุนไพรนะ", n3_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("เราสู้มันตรงๆ เลยดีกว่า ผมพร้อมแล้ว!", n3_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ถอยก่อนเถอะ มันดูน่ากลัวเกินไป", n3_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n3_4B = new DialogueNode("Arwen",
            "ท่านสู้เก่งมากเลยค่ะ! (มองดูคุณสู้กับพวกมอนสเตอร์) ข้าไม่เคยเห็นใครเท่เท่าท่านมาก่อนเลย...");
        n3_4B.imagePath = "image\\Scene\\Arwen\\Scene3\\_ท่านสู้เก่งมากเลยค่ะ! (มองดูคุณสู้กับพวกมอนสเตอร์) ข้าไม่เคยเห็นใครเท่เท่าท่านมาก่อนเลย..._.png";
        n3_4B.addChoice("ก็แค่โชว์ฝีมือให้คุณดูน่ะครับ", n3_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณที่ชมครับ ผมเขินนะเนี่ย", n3_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("รีบเก็บของเถอะ อย่ามัวแต่ชม", n3_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n3_4C = new DialogueNode("Arwen",
            "ขอโทษค่ะ... ข้าจะพยายามเดินให้เร็วขึ้น... (เธอกึ่งวิ่งกึ่งเดินจนเกือบจะสะดุดล้ม)");
        n3_4C.imagePath = "image\\Scene\\Arwen\\Scene3\\ขอโทษค่ะ... ข้าจะพยายามเดินให้เร็วขึ้น... (เธอกึ่งวิ่งกึ่งเดินจนเกือบจะสะดุดล้ม).png";
        n3_4C.addChoice("(เข้าไปประคอง) ระวัง! บาดเจ็บตรงไหนไหม?", n3_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("บอกแล้วไงว่าให้ระวัง", n3_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("ซุ่มซ่ามจริงๆ เลย", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode n3_3A = new DialogueNode("Arwen",
            "(ขณะที่เดินไปในป่า เธอกุมมือคุณแน่น) ขอบคุณที่อยู่ข้างๆ นะคะ... ท่านดูพึ่งพาได้มากเลย ข้ารู้สึกปลอดภัยจัง");
        n3_3A.imagePath = "image\\Scene\\Arwen\\Scene3\\(ขณะที่เดินไปในป่า เธอกุมมือคุณแน่น) _ขอบคุณที่อยู่ข้างๆ นะคะ... ท่านดูพึ่งพาได้มากเลย ข้ารู้สึกปลอดภัยจัง_.png";
        n3_3A.addChoice("ผมจะไม่มีวันปล่อยให้คุณเป็นอันตรายเด็ดขาด", n3_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("มือคุณสั่นหมดแล้วนะ ใจเย็นๆ", n3_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เดินเร็วๆ หน่อยสิ เดี๋ยวจะมืดก่อน", n3_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n3_3B = new DialogueNode("Arwen",
            "ระวังตัวด้วยนะคะ! พวกมันนิสัยเสียชอบลอบกัด... ข้าจะเป็นกำลังใจให้ท่านอยู่ตรงนี้นะคะ");
        n3_3B.imagePath = "image\\Scene\\Arwen\\Scene3\\_ระวังตัวด้วยนะคะ! พวกมันนิสัยเสียชอบลอบกัด... ข้าจะเป็นกำลังใจให้ท่านอยู่ตรงนี้นะคะ_.png";
        n3_3B.addChoice("เพื่อคุณ... แค่นี้สบายมากครับ", n3_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("เตรียมยาแผลไว้รอผมได้เลย", n3_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("อย่าลืมทำขนมรอเป็นรางวัลด้วยล่ะ", n3_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n3_3C = new DialogueNode("Arwen",
            "(ถอนหายใจยาว) ถ้าท่านไม่สะดวกก็ไม่เป็นไรค่ะ... ข้าจะลองหาทางอื่นดู... ขอบคุณที่รับฟังนะคะ");
        n3_3C.imagePath = "image\\Scene\\Arwen\\Scene3\\(ถอนหายใจยาว) _ถ้าท่านไม่สะดวกก็ไม่เป็นไรค่ะ... ข้าจะลองหาทางอื่นดู... ขอบคุณที่รับฟังนะคะ_.png";

        DialogueNode n3_2A = new DialogueNode("Arwen",
            "(เงยหน้ามองคุณอย่างมีความหวัง) ขอบคุณนะคะ! ข้าจำได้ว่าเอาไปวางไว้แถวๆ สวนหลังหมู่บ้าน... แต่แถวนั้นมอนสเตอร์เยอะมาก ข้าเลยไม่กล้าเข้าไป");
        n3_2A.imagePath = "image\\Scene\\Arwen\\Scene3\\เงยหน้ามองคุณอย่างมีความหวัง) _ขอบคุณนะคะ! ข้าจำได้ว่าเอาไปวางไว้แถวๆ สวนหลังหมู่บ้าน... แต่แถวนั้นมอนสเตอร์เยอะมาก ข้าเลยไม่กล้าเข้าไป_.png";

        DialogueNode n3_2B = new DialogueNode("Arwen",
            "ข้านึกจนหัวจะระเบิดแล้วค่ะ... (กุมขมับ) หรือว่าข้าจะโดนพวกก็อบลินขโมยไปนะ? ข้าเห็นมันด้อมๆ มองๆ อยู่เมื่อเช้า");
        n3_2B.imagePath = "image\\Scene\\Arwen\\Scene3\\ข้านึกจนหัวจะระเบิดแล้วค่ะ... (กุมขมับ) หรือว่าข้าจะโดนพวกก็อบลินขโมยไปนะ_ ข้าเห็นมันด้อมๆ มองๆ อยู่เมื่อเช้า_.png";

        DialogueNode n3_2C = new DialogueNode("Arwen",
            "(น้ำตาคลอเบ้า) ข้า... ข้าขอโทษค่ะ ข้าไม่ได้ตั้งใจ... มันสำคัญมากจริงๆ เพราะถ้าไม่มีมัน คนป่วยในหมู่บ้านจะแย่ลง...");
        n3_2C.imagePath = "image\\Scene\\Arwen\\Scene3\\(น้ำตาคลอเบ้า) _ข้า... ข้าขอโทษค่ะ ข้าไม่ได้ตั้งใจ... มันสำคัญมากจริงๆ เพราะถ้าไม่มีมัน คนป่วยในหมู่บ้านจะแย่ลง..._.png";

        n3_3C.addChoice("เปลี่ยนใจแล้วครับ ผมช่วยคุณเอง", n3_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("โชคดีนะ Arwen", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("อืม บาย", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        n3_2A.addChoice("ไม่ต้องกลัวครับ ผมจะคุ้มกันคุณเอง", n3_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("คุณรออยู่นี่นะ เดี๋ยวผมไปเอามาให้เอง", n3_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("มอนสเตอร์เยอะเหรอ? งั้นจ้างคนอื่นไปไหม?", n3_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        n3_2B.addChoice("ถ้าเป็นฝีมือมัน ผมจะไปถล่มรังมันให้เอง", n3_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("งั้นเราลองไปสืบดูแถวชายป่าไหม?", n3_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("สมุนไพรแค่นั้น ปล่อยมันไปเถอะ ปรุงใหม่ดีกว่า", n3_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        n3_2C.addChoice("ผมพูดแรงไปเอง ขอโทษครับ มา... ผมช่วยหา", n3_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ร้องไห้ไปก็ไม่ได้อะไรขึ้นมาหรอกนะ", n3_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15))
             .addChoice("งั้นก็เรื่องของคุณละกัน ผมไปล่ะ", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-20));

        DialogueNode root = new DialogueNode("Arwen",
            "(เธอนั่งอยู่กลางกองสมุนไพรที่กระจัดกระจาย หน้าตาดูเคร่งเครียด) \"แย่แล้วค่ะ... ยาสมุนไพรที่ข้าเตรียมไว้ให้คนป่วยหายไปไหนหมดไม่รู้! ข้าต้องหาให้เจอภายในเย็นนี้\"");
        root.imagePath = "image\\Scene\\Arwen\\Scene3\\เธอนั่งอยู่กลางกองสมุนไพรที่กระจัดกระจาย หน้าตาดูเคร่งเครียด) _แย่แล้วค่ะ... ยาสมุนไพรที่ข้าเตรียมไว้ให้คนป่วยหายไปไหนหมดไม่รู้! ข้าต้องหาให้เจอภายในเย็นนี้_.png";
        root.addChoice("มาครับ ผมช่วยหาเอง", n3_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ลืมไว้ที่ไหนรึเปล่า? ลองนึกดูดีๆ", n3_2B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ทำหายได้ยังไงเนี่ย? สะเพร่าจัง", n3_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        return root;
    }

    // ============================================================
    // Level 4 Dialogue Tree
    // ============================================================
    private DialogueNode buildLevel4Tree(MainFrame mainFrame) {

        DialogueNode endBad    = new DialogueNode("Arwen", "(เดินจากไปเงียบๆ)", true);
        endBad.imagePath = "image\\Scene\\Arwen\\Scene4\\ENDBAD.png";
        DialogueNode endNormal = new DialogueNode("Arwen", "(ยืนมองแสงจันทร์เพียงลำพัง) ไม่ว่ายังไง... ข้าจะยังรอท่านอยู่ที่นี่เสมอนะคะ", true);
        endNormal.imagePath = "image\\Scene\\Arwen\\Scene4\\(ยืนมองแสงจันทร์เพียงลำพัง) _ไม่ว่ายังไง... ข้าจะยังรอท่านอยู่ที่นี่เสมอนะคะ_2.png";
        DialogueNode endGood   = new DialogueNode("Arwen", "(มองตามหลังคุณไป) หวังว่าพรุ่งนี้จะเป็นวันที่ดีของเราสองคนนะคะ...", true);
        endGood.imagePath = "image\\Scene\\Arwen\\Scene4\\(มองตามหลังคุณไป) _หวังว่าพรุ่งนี้จะเป็นวันที่ดีของเราสองคนนะคะ..._.png";
        DialogueNode endBest   = new DialogueNode("Arwen", "(กระซิบข้างหูเบาๆ ก่อนแยกจากกัน) ข้ารักท่านนะ... เจ้ามนุษย์ใจดีของข้า", true);
        endBest.imagePath = "image\\Scene\\Arwen\\Scene4\\(กระซิบข้างหูเบาๆ ก่อนแยกจากกัน) _ข้ารักท่านนะ... เจ้ามนุษย์ใจดีของข้า_.png";

        DialogueNode n4_5A = new DialogueNode("Arwen",
            "(กระซิบข้างหูเบาๆ ก่อนแยกจากกัน) ข้ารักท่านนะ... เจ้ามนุษย์ใจดีของข้า");
        n4_5A.imagePath = "image\\Scene\\Arwen\\Scene4\\(กระซิบข้างหูเบาๆ ก่อนแยกจากกัน) _ข้ารักท่านนะ... เจ้ามนุษย์ใจดีของข้า_.png";
        n4_5A.addChoice("ผมก็รักคุณเช่นกัน Arwen", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("(ยิ้มให้ด้วยความอบอุ่น)", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เจอกันพรุ่งนี้เช้านะ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n4_5B = new DialogueNode("Arwen",
            "(มองตามหลังคุณไป) หวังว่าพรุ่งนี้จะเป็นวันที่ดีของเราสองคนนะคะ...");
        n4_5B.imagePath = "image\\Scene\\Arwen\\Scene4\\(มองตามหลังคุณไป) _หวังว่าพรุ่งนี้จะเป็นวันที่ดีของเราสองคนนะคะ..._.png";
        n4_5B.addChoice("แน่นอนครับ ผมเชื่ออย่างนั้น", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เราจะทำให้มันเป็นวันที่ดีเอง", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("(โบกมือลา)", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_5C = new DialogueNode("Arwen",
            "(ยืนมองแสงจันทร์เพียงลำพัง) ไม่ว่ายังไง... ข้าจะยังรอท่านอยู่ที่นี่เสมอนะคะ");
        n4_5C.imagePath = "image\\Scene\\Arwen\\Scene4\\(ยืนมองแสงจันทร์เพียงลำพัง) _ไม่ว่ายังไง... ข้าจะยังรอท่านอยู่ที่นี่เสมอนะคะ1.png";
        n4_5C.addChoice("(หันกลับไปยิ้มให้ครั้งสุดท้าย)", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ดูแลตัวเองด้วยนะ", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("(เดินจากไปเงียบๆ)", endBad,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n4_4A = new DialogueNode("Arwen",
            "(ยื่นแหวนเถาวัลย์ถักขนาดเล็กให้) นี่คือแหวนแห่งพันธสัญญาของคนป่าค่ะ... ใครที่สวมมันไว้ จะไม่มีวันหลงทางกลับมาหาคนที่ให้... ท่านจะลองสวมมันดูไหม?");
        n4_4A.imagePath = "image\\Scene\\Arwen\\Scene4\\(ยื่นแหวนเถาวัลย์ถักขนาดเล็กให้) _นี่คือแหวนแห่งพันธสัญญาของคนป่าค่ะ... ใครที่สวมมันไว้ จะไม่มีวันหลงทางกลับมาหาคนที่ให้... ท่านจะลองสวมมันดูไหม_.png";
        n4_4A.addChoice("(สวมแหวนให้เธอเห็น) แบบนี้ผมก็ไปไหนไม่ได้แล้วสิ", n4_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมจะเก็บมันไว้ในกระเป๋าที่ใกล้หัวใจที่สุด", n4_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ขอบคุณนะ ผมจะระวังไม่ให้มันหาย", n4_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_4B = new DialogueNode("Arwen",
            "ถ้าท่านยังไม่พร้อมสวมมันตอนนี้ก็ไม่เป็นไรค่ะ... แค่ท่านพกมันไว้ ข้าก็อุ่นใจแล้ว... คืนนี้ท่านพักผ่อนเถอะนะคะ พรุ่งนี้ยังมีเรื่องให้ทำอีกเยอะ");
        n4_4B.imagePath = "image\\Scene\\Arwen\\Scene4\\_ถ้าท่านยังไม่พร้อมสวมมันตอนนี้ก็ไม่เป็นไรค่ะ... แค่ท่านพกมันไว้ ข้าก็อุ่นใจแล้ว... คืนนี้ท่านพักผ่อนเถอะนะคะ พรุ่งนี้ยังมีเรื่องให้ทำอีกเยอะ_.png";
        n4_4B.addChoice("คืนนี้ผมนอนหลับฝันดีแน่นอน เพราะมีคุณ", n4_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ราตรีสวัสดิ์นะ Arwen", n4_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("คุณก็พักผ่อนด้วยนะ", n4_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_4C = new DialogueNode("Arwen",
            "ข้าเข้าใจค่ะ... บางทีความสัมพันธ์ก็เหมือนการปลูกยา ต้องใช้เวลา... ข้าจะรอวันที่ท่านพร้อมนะคะ คืนนี้ฝันดีค่ะ");
        n4_4C.imagePath = "image\\Scene\\Arwen\\Scene4\\_ข้าเข้าใจค่ะ... บางทีความสัมพันธ์ก็เหมือนการปลูกยา ต้องใช้เวลา... ข้าจะรอวันที่ท่านพร้อมนะคะ คืนนี้ฝันดีค่ะ_.png";
        n4_4C.addChoice("ขอบคุณที่เข้าใจผมนะ", n4_5A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ฝันดีครับ Arwen", n4_5B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("(พยักหน้าแล้วเดินเข้าห้องนอน)", n4_5C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_3A = new DialogueNode("Arwen",
            "(ขยับเข้ามาใกล้ขึ้น) ที่ข้าทำทั้งหมด... ก็เพราะท่านสำคัญสำหรับข้ามากนะคะ ถ้าวันหนึ่งข้าขอให้ท่านอยู่ช่วยข้าสร้างสวนสมุนไพรที่นี่... ท่านจะยินดีไหม?");
        n4_3A.imagePath = "image\\Scene\\Arwen\\Scene4\\(ขยับเข้ามาใกล้ขึ้น) _ที่ข้าทำทั้งหมด... ก็เพราะท่านสำคัญสำหรับข้ามากนะคะ ถ้าวันหนึ่งข้าขอให้ท่านอยู่ช่วยข้าสร้างสวนสมุนไพรที่นี่... ท่านจะยินดีไหม_.png";
        n4_3A.addChoice("ยินดีที่สุดครับ ผมจะสร้างมันไปพร้อมกับคุณ", n4_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมขอเวลาคิดสักนิดได้ไหม?", n4_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ผมอาจจะช่วยได้แค่ชั่วคราวนะ", n4_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n4_3B = new DialogueNode("Arwen",
            "(มองตาคุณ) ข้าเข้าใจค่ะ... ทุกคนมีความฝันของตัวเอง แต่ถ้าความฝันของข้ามีท่านอยู่ด้วย มันคงจะวิเศษมากเลย ท่านว่าไหมคะ?");
        n4_3B.imagePath = "image\\Scene\\Arwen\\Scene4\\(มองตาคุณ) _ข้าเข้าใจค่ะ... ทุกคนมีความฝันของตัวเอง แต่ถ้าความฝันของข้ามีท่านอยู่ด้วย มันคงจะวิเศษมากเลย ท่านว่าไหมคะ_.png";
        n4_3B.addChoice("ผมเองก็อยากให้ความฝันของคุณมีผมอยู่ด้วย", n4_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ฝันของคุณน่ารักดีนะ", n4_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("เราต้องทำปัจจุบันให้ดีก่อนสิ", n4_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_3C = new DialogueNode("Arwen",
            "(ยิ้มเศร้าๆ) นั่นสินะคะ... ข้าคงจะคาดหวังมากไป แต่ไม่ว่ายังไง ข้าก็ดีใจที่เราได้พบกัน... ท่านจะรับสิ่งนี้ไว้แทนคำสัญญาของข้าได้ไหม?");
        n4_3C.imagePath = "image\\Scene\\Arwen\\Scene4\\(ยิ้มเศร้าๆ) _นั่งสินะคะ... ข้าคงจะคาดหวังมากไป แต่ไม่ว่ายังไง ข้าก็ดีใจที่เราได้พบกัน... ท่านจะรับสิ่งนี้ไว้แทนคำสัญญาของข้าได้ไหม_.png";
        n4_3C.addChoice("(รับของมา) ผมจะเก็บมันไว้อย่างดีที่สุด", n4_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("มันคืออะไรเหรอครับ?", n4_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ขอบคุณนะ Arwen", n4_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_2A = new DialogueNode("Arwen",
            "(หน้าแดงระเรื่อ) ท่านพูดแบบนี้... หัวใจข้าก็สั่นสิคะ จริงๆ แล้วข้ามีความลับหนึ่งที่ยังไม่เคยบอกใคร ข้าแอบปรุงยา 'แห่งความทรงจำ' ไว้ให้ท่านด้วย...");
        n4_2A.imagePath = "image\\Scene\\Arwen\\Scene4\\(หน้าแดงระเรื่อ) _ท่านพูดแบบนี้... หัวใจข้าก็สั่นสิคะ จริงๆ แล้วข้ามีความลับหนึ่งที่ยังไม่เคยบอกใคร ข้าแอบปรุงยา _แห่งความทรงจำ_ ไว้ให้ท่านด้วย..._.png";
        n4_2A.addChoice("ยาตัวนี้ไว้ใช้ทำอะไรเหรอครับ?", n4_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ความลับเยอะจริงนะเราน่ะ ไหนขอดูหน่อยสิ", n4_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ผมไม่ต้องใช้ยาหรอก แค่เห็นหน้าคุณผมก็จำไม่ลืมแล้ว", n4_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(5));

        DialogueNode n4_2B = new DialogueNode("Arwen",
            "ท่านช่างเป็นคนที่มีทัศนคติดีจัง... การมีท่านอยู่ด้วยทำให้ป่าที่เคยเงียบเหงาดูมีชีวิตชีวาขึ้นมาทันที ท่าน... เคยคิดเรื่องการลงหลักปักฐานบ้างไหมคะ?");
        n4_2B.imagePath = "image\\Scene\\Arwen\\Scene4\\_ท่านช่างเป็นคนที่มีทัศนคติดีจัง... การมีท่านอยู่ด้วยทำให้ป่าที่เคยเงียบเหงาดูมีชีวิตชีวาขึ้นมาทันที ท่าน... เคยคิดเรื่องการลงหลักปักฐานบ้างไหมคะ_.png";
        n4_2B.addChoice("ถ้าที่นี่มีคุณ ผมก็อยากหยุดการเดินทางไว้ตรงนี้", n4_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมยังรักอิสระอยู่นะ แต่ก็ชอบที่นี่เหมือนกัน", n4_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ชีวิตนักเดินทางมันเลือกที่อยู่ยากครับ", n4_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n4_2C = new DialogueNode("Arwen",
            "(หัวเราะเบาๆ) นั่นสินะคะ ท่านคงเหนื่อยมากจริงๆ มานั่งนี่เถอะค่ะ... ข้าเตรียมน้ำมันหอมระเหยสูตรพิเศษไว้ให้ท่านผ่อนคลายโดยเฉพาะเลย");
        n4_2C.imagePath = "image\\Scene\\Arwen\\Scene4\\(หัวเราะเบาๆ) _นั่นสินะคะ ท่านคงเหนื่อยมากจริงๆ มานั่งนี่เถอะค่ะ... ข้าเตรียมน้ำมันหอมระเหยสูตรพิเศษไว้ให้ท่านผ่อนคลายโดยเฉพาะเลย_.png";
        n4_2C.addChoice("กลิ่นนี้หอมจัง เหมือนกลิ่นตัวคุณเลย", n4_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("คุณดูแลคนอื่นเก่งแบบนี้เสมอเลยเหรอ?", n4_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ขอบใจนะ ผมกำลังปวดเมื่อยพอดีเลย", n4_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode root = new DialogueNode("Arwen",
            "(ยืนพิงระเบียงบ้าน มองดูดาว) \"ในที่สุดเรื่องวุ่นๆ ในหมู่บ้านก็จบลงเสียที... ข้าไม่เคยคิดเลยว่าจะมีใครสักคนยอมเหนื่อยเพื่อข้าขนาดนี้ ขอบคุณจริงๆ นะคะ\"");
        root.imagePath = "image\\Scene\\Arwen\\Scene4\\(ยืนพิงระเบียงบ้าน มองดูดาว) _ในที่สุดเรื่องวุ่นๆ ในหมู่บ้านก็จบลงเสียที... ข้าไม่เคยคิดเลยว่าจะมีใครสักคนยอมเหนื่อยเพื่อข้าขนาดนี้ ขอบคุณจริงๆ นะคะ_.png";
        root.addChoice("เพราะเป็นคุณยังไงล่ะ ผมถึงยอมทำทุกอย่าง", n4_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ไม่เป็นไรหรอกครับ ผมเองก็ได้เรียนรู้อะไรเยอะเลย", n4_2B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("หวังว่าคราวหน้าจะไม่มีมอนสเตอร์โผล่มาอีกนะ", n4_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        return root;
    }

    // ============================================================
    // Level 5 Dialogue Tree
    // ============================================================
    private DialogueNode buildLevel5Tree(MainFrame mainFrame) {

        DialogueNode endBad    = new DialogueNode("Arwen", "(ยืนมองตามหลัง เงียบ)", true);
        endBad.imagePath = "image\\Scene\\Arwen\\Scene5\\ENDBAD.png";
        DialogueNode endNormal = new DialogueNode("Arwen", "(ภาพตัดไปที่ทั้งคู่ช่วยกันปรุงยาแบบเพื่อน)\n\n— NORMAL ENDING: Friendship —", true);
        endNormal.imagePath = "image\\Scene\\Arwen\\Scene5\\_ขอบคุณที่รักษาสัญญาค่ะ..._ (ภาพตัดไปที่ทั้งคู่ช่วยกันปรุงยาแบบเพื่อน).png";
        DialogueNode endGood   = new DialogueNode("Arwen", "(ภาพตัดไปที่ทั้งคู่นั่งดูดาวด้วยกัน)\n\n— GOOD ENDING: Partner —", true);
        endGood.imagePath = "image\\Scene\\Arwen\\Scene5\\_ท่านนี่ตลกเสมอเลยนะ... แต่ข้าก็ชอบ_ (ภาพตัดไปที่ทั้งคู่นั่งดูดาวด้วยกัน).png";
        DialogueNode endBest   = new DialogueNode("Arwen", "ข้ารักท่านที่สุดเลยค่ะ (ภาพตัดไปที่ทั้งคู่สร้างบ้านหลังใหม่ด้วยกัน)\n\n— HAPPY ENDING: Marriage —", true);
        endBest.imagePath = "image\\Scene\\Arwen\\Scene5\\_ข้ารักท่านที่สุดเลยค่ะ แชมป์..._ (ภาพตัดไปที่ทั้งคู่สร้างบ้านหลังใหม่ด้วยกัน).png";

        DialogueNode n5_4A = new DialogueNode("Arwen",
            "(ซบลงที่อกคุณ) ข้าแอบทำน้ำหอมกลิ่น 'นิรันดร์' ไว้ให้ท่านด้วย... กลิ่นนี้จะติดตัวเราไปจนแก่เฒ่า ท่านจะอยู่ดมกลิ่นนี้กับข้าทุกวันไหม?");
        n5_4A.imagePath = "image\\Scene\\Arwen\\Scene5\\(ซบลงที่อกคุณ) _ข้าแอบทำน้ำหอมกลิ่น _นิรันดร์_ ไว้ให้ท่านด้วย... กลิ่นนี้จะติดตัวเราไปจนแก่เฒ่า ท่านจะอยู่ดมกลิ่นนี้กับข้าทุกวันไหม_.png";
        n5_4A.addChoice("ทุกวัน ทุกนาที และตลอดไปครับ", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ถ้าผมไม่จมูกดับไปก่อนนะ (หัวเราะ)", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("แน่นอนสิ ผมสัญญาแล้วนี่นา", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n5_4B = new DialogueNode("Arwen",
            "ข้าจะปลูกดอกไม้ให้เต็มสวนเลย... เพื่อต้อนรับทุกเช้าวันใหม่ของเรา ท่านอยากช่วยข้าเลือกเมล็ดพันธุ์ไหม?");
        n5_4B.imagePath = "image\\Scene\\Arwen\\Scene5\\_ข้าจะปลูกดอกไม้ให้เต็มสวนเลย... เพื่อต้อนรับทุกเช้าวันใหม่ของเรา ท่านอยากช่วยข้าเลือกเมล็ดพันธุ์ไหม_.png";
        n5_4B.addChoice("ด้วยความยินดีครับ ที่รัก", endBest,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("เอาไว้พรุ่งนี้นะ", endGood,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ตามใจคุณเลย", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n5_4C = new DialogueNode("Arwen",
            "ขอให้โชคดีนะคะ... และจำไว้ว่า ที่นี่ต้อนรับท่านเสมอ ไม่ว่าเมื่อไหร่");
        n5_4C.imagePath = "image\\Scene\\Arwen\\Scene5\\_ขอให้โชคดีนะคะ... และจำไว้ว่า ที่นี่ต้อนรับท่านเสมอ ไม่ว่าเมื่อไหร่_.png";
        n5_4C.addChoice("ลาก่อนนะ Arwen", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ดูแลตัวเองด้วย", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("(พยักหน้าแล้วเดินจากไป)", endNormal,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n5_3A = new DialogueNode("Arwen",
            "(หยิบแหวนเถาวัลย์สวมให้คุณ) นี่ไม่ใช่แค่ของที่ระลึกแล้วนะ แต่มันคือพันธสัญญา... ว่าข้าจะเป็นของท่าน และท่านจะเป็นของข้าตลอดไป");
        n5_3A.imagePath = "image\\Scene\\Arwen\\Scene5\\(หยิบแหวนเถาวัลย์สวมให้คุณ) _นี่ไม่ใช่แค่ของที่ระลึกแล้วนะ แต่มันคือพันธสัญญา... ว่าข้าจะเป็นของท่าน และท่านจะเป็นของข้าตลอดไป_.png";
        n5_3A.addChoice("ผมจะรักและปกป้องคุณด้วยชีวิต", n5_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("สัญญาครับ ว่าผมจะไม่ไปไหนอีก", n5_4A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("แหวนสวยจัง ขอบคุณนะ", n5_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n5_3B = new DialogueNode("Arwen",
            "แค่ได้ยินว่าท่านจะอยู่... ข้าก็ดีใจมากแล้วค่ะ ข้าจะทำทุกวันให้ดีที่สุด เพื่อให้ท่านมีความสุขที่นี่");
        n5_3B.imagePath = "image\\Scene\\Arwen\\Scene5\\แค่ได้ยินว่าท่านจะอยู่... ข้าก็ดีใจมากแล้วค่ะ ข้าจะทำทุกวันให้ดีที่สุด เพื่อให้ท่านมีความสุขที่นี่_.png";
        n5_3B.addChoice("ผมเชื่อว่าผมต้องมีความสุขมากแน่ๆ", n5_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ฝากเนื้อฝากตัวด้วยนะ", n5_4B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอบคุณครับ", n5_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0));

        DialogueNode n5_3C = new DialogueNode("Arwen",
            "(ยิ้มเศร้าๆ) ข้าเข้าใจค่ะ... วิถีของนักเดินทางคงหยุดนิ่งไม่ได้ ข้าจะเตรียมเสบียงไว้ให้ท่านสำหรับการเดินทางนะคะ");
        n5_3C.imagePath = "image\\Scene\\Arwen\\Scene5\\(ยิ้มเศร้าๆ) _ข้าเข้าใจค่ะ... วิถีของนักเดินทางคงหยุดนิ่งไม่ได้ ข้าจะเตรียมเสบียงไว้ให้ท่านสำหรับการเดินทางนะคะ_.png";
        n5_3C.addChoice("คุณเป็นเพื่อนที่ดีที่สุดของผมเลย", n5_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ขอบคุณนะ Arwen", n5_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ไม่ต้องลำบากหรอก", n5_4C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n5_2A = new DialogueNode("Arwen",
            "(หน้าแดงระเรื่อ) สวนของเรา... ฟังแล้วอบอุ่นหัวใจจังค่ะ ท่าน... พร้อมที่จะทิ้งการเดินทางที่แสนวุ่นวาย เพื่อมาอยู่ที่นี่กับข้าไหม?");
        n5_2A.imagePath = "image\\Scene\\Arwen\\Scene5\\(หน้าแดงระเรื่อ) _สวนของเรา... ฟังแล้วอบอุ่นหัวใจจังค่ะ ท่าน... พร้อมที่จะทิ้งการเดินทางที่แสนวุ่นวาย เพื่อมาอยู่ที่นี่กับข้าไหม_.png";
        n5_2A.addChoice("ผมเลือกคุณ มากกว่าการเดินทางทุกที่ในโลก", n5_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมจะอยู่ที่นี่ และออกไปหาของป่ามาให้คุณ", n5_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอผมลองใช้ชีวิตแบบนี้ไปสักพักนะ", n5_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        DialogueNode n5_2B = new DialogueNode("Arwen",
            "ขอบคุณค่ะ... ถ้าไม่มีท่าน ข้าคงทำไม่ได้แน่ๆ ท่านคะ... จากนี้ไปท่านมีแผนจะเดินทางไปที่ไหนต่อหรือเปล่า?");
        n5_2B.imagePath = "image\\Scene\\Arwen\\Scene5\\_ขอบคุณค่ะ... ถ้าไม่มีท่าน ข้าคงทำไม่ได้แน่ๆ ท่านคะ... จากนี้ไปท่านมีแผนจะเดินทางไปที่ไหนต่อหรือเปล่า_.png";
        n5_2B.addChoice("แผนของผมคือการอยู่เคียงข้างคุณครับ", n5_3A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ผมยังไม่ได้คิดเลย อาจจะอยู่ที่นี่สักพัก", n5_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("คงต้องออกเดินทางต่อเร็วๆ นี้", n5_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode n5_2C = new DialogueNode("Arwen",
            "นั่นสินะคะ... ท่านคงเหนื่อยมามาก เชิญท่านพักตามสบายเถอะค่ะ ข้าเตรียมชาสมุนไพรไว้ให้แล้ว");
        n5_2C.imagePath = "image\\Scene\\Arwen\\Scene5\\นั่นสินะคะ... ท่านคงเหนื่อยมามาก เชิญท่านพักตามสบายเถอะค่ะ ข้าเตรียมชาสมุนไพรไว้ให้แล้ว_.png";
        n5_2C.addChoice("ขอบคุณนะ Arwen ชาของคุณดีที่สุดเสมอ", n5_3B,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ขอนอนพักยาวๆ เลยนะ", n5_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(0))
             .addChoice("ผมขอดื่มแล้วรีบไปนะ", n5_3C,
                () -> mainFrame.getPlayer().getArwen().addAffection(-15));

        DialogueNode root = new DialogueNode("Arwen",
            "(ยืนอยู่กลางสวนยาที่บานสะพรั่ง) \"ในที่สุดสวนที่ข้าฝันถึงก็สำเร็จแล้ว ข้าไม่เคยคิดเลยว่าจะมีวันที่ได้ยืนอยู่ตรงนี้พร้อมกับท่าน...\"");
        root.imagePath = "image\\Scene\\Arwen\\Scene5\\(ยืนอยู่กลางสวนยาที่บานสะพรั่ง) _แชมป์คะ... ในที่สุดสวนที่ข้าฝันถึงก็สำเร็จแล้ว ข้าไม่เคยคิดเลยว่าจะมีวันที่ได้ยืนอยู่ตรงนี้พร้อมกับท่าน..._.png";
        root.addChoice("มันไม่ใช่แค่สวนของคุณนะ แต่มันคือสวนของเรา", n5_2A,
                () -> mainFrame.getPlayer().getArwen().addAffection(5))
             .addChoice("ดีใจด้วยนะ Arwen คุณทำสำเร็จแล้ว", n5_2B,
                () -> mainFrame.getPlayer().getArwen().addAffection(3))
             .addChoice("ภารกิจสำเร็จแล้วนะ เราพักผ่อนกันเถอะ", n5_2C,
                () -> mainFrame.getPlayer().getArwen().addAffection(3));

        return root;
    }
}