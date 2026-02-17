package Relationship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * LazelScene1 - Special Scene เมื่อ affection ถึง 100 (lv0 → lv1)
 * ใช้ JPanel แสดงข้อความ + JButton ตัวเลือก
 * เรียกใช้จาก LazelPanel เมื่อ getDialogue() trigger special scene
 */
public class LazelScene1 {

    // --- Data Class: แทน 1 node ---
    public static class Node {
        String npcText;
        String[] choices;   // ข้อความปุ่มที่ผู้เล่นเห็น
        String[] nextIds;   // ID node ถัดไปของแต่ละตัวเลือก

        // Node ที่มีตัวเลือก
        Node(String npcText, String[] choices, String[] nextIds) {
            this.npcText = npcText;
            this.choices = choices;
            this.nextIds = nextIds;
        }

        // END Node (ไม่มีตัวเลือก)
        Node(String npcText) {
            this.npcText = npcText;
            this.choices = null;
            this.nextIds = null;
        }

        boolean isEnd() {
            return choices == null;
        }
    }

    // --- สร้าง Dialogue Tree ---
    private static Map<String, Node> buildTree() {
        Map<String, Node> tree = new HashMap<>();

        // 1.1
        tree.put("1.1", new Node(
            "(วิ่งชนคุณเต็มแรงจนล้ม) \"โอ้ย! ...เฮ้ย! เดินดูตาม้าตาเรือหน่อยสิวะ! ดาบบ้าเกือบจะกระทิ่มตาเจ้าบอดแล้วไหนเนีย!?\"",
            new String[]{
                "ขอโทษครับ ผมไม่ทันระวังเลย คุณเจ็บตรงไหนไหม?",
                "เจ้าแหละวิ่งไม่ดูทาง ยัมมัดติดกะโลก!",
                "โว้ย! ดาบสวยดีนี่... ขอดูหน่อยสิ"
            },
            new String[]{"1.2A", "1.2B", "1.2C"}
        ));

        // 1.2A
        tree.put("1.2A", new Node(
            "\"ชื้! ...รู้จักขอโทษก็ดี (ปัดฝุ่น) นึกว่าจะปากเก่งเหมือนพวกหน้าไม้คนอื่น... เจ้าเป็นเด็กใหม่เหรอ? ไม่คุ้นหน้าเลย\"",
            new String[]{
                "ใช่ครับ เพิ่งย้ายมาเรียนลำมังกร ฝากตัวด้วยนะ",
                "ข้าเป็นนักลำระดับตำนาน แต่ปลอมตัวมาดูงาน",
                "ไม่ใช่เรื่องของเจ้า"
            },
            new String[]{"1.3A", "1.3B", "1.3C"}
        ));

        // 1.2B
        tree.put("1.2B", new Node(
            "\"ทา!? ...กล้าดียังไมมาเรียกข้าแบบนั้นอะ! อยากลองชิมรสชาติดาบข้าหน่อยไหม!?\"",
            new String[]{
                "กีมาดีครับ! คิดว่ากลัวเหรอ?",
                "เฮ้ยๆ ใจเย็นๆ ผมล้อเล่นครับพี่สาว",
                "(วิ่งหนีสุดชีวิต)"
            },
            new String[]{"1.3C", "1.2A", "END_BAD"}
        ));

        // 1.2C
        tree.put("1.2C", new Node(
            "(ยิ้มมุมปาก ภูมิใจ) \"ตาถึงนี่หว่า... นี่คือดาบเนี้ยมังกร ของตกทอดจากพ่อข้าเอง อยากดูใกล้ๆ ไหมล่ะ?\"",
            new String[]{
                "เท่มาก เหมาะกับนักรบอย่างท่านเลย",
                "ดูเก่าไหนอยนะ สนิมขึ้นแล้วมั้ง",
                "กี้งั้นๆ แหละ นึกว่าดาบเทพ"
            },
            new String[]{"1.3A", "1.2B", "1.3C"}
        ));

        // 1.3A
        tree.put("1.3A", new Node(
            "\"เออ! พูดจาเข้าหูดีนี่หว่า... ข้าชื่อ Lazel เป็นเลยของที่นี่ จำชื่อนี้ใส่กะโหลกไว้ยะล้ะ!\"",
            new String[]{
                "ยินดีที่ได้รู้จัก Lazel ผมชื่อ...",
                "เอ้อ? โม้รีเปล่าเนีย?",
                "ครับๆ คุณเอย"
            },
            new String[]{"1.4A", "1.4B", "1.4B"}
        ));

        // 1.3B
        tree.put("1.3B", new Node(
            "\"ฮะ? ระดับตำนาน? สภาพแห้งๆ อย่างเจ้าเนี่ยนะ? (หัวเราะลั่น) มุกตลกใช้ได้นี่หว่า! ข้าชอบคนตลกกะนะเว้ย!\"",
            new String[]{
                "หัวเราแล้วน่ารักดีนะครับ ยิ้มบ่อยๆ สิ",
                "เดี๋ยวก็รู้ว่าตลกหรือของจริง",
                "ข้าเคยข่ามังกรด้วยมือเปล่ามาแล้ว"
            },
            new String[]{"1.4A", "1.4C", "1.4B"}
        ));

        // 1.3C
        tree.put("1.3C", new Node(
            "(แวดตาวาโรจน) \"โอ่... ท้าทายข้าเหรอ? Lazel ผู้นี้ไม่เคยปฏิเสธคำท้านะ... เย็นนี้เจอกันที่ลานประลอง ห้ามหนีนะเว้ย!\"",
            new String[]{
                "รับคำท้า! ใครแพ้เลี้ยงข้าว!",
                "ไม่เอาอะ ขี้เกียจ",
                "แน่ใจนะ? เดี๋ยวร้องให้ชิ้มุกโป้งนะ"
            },
            new String[]{"1.4C", "END_BAD", "1.4C"}
        ));

        // 1.4A
        tree.put("1.4A", new Node(
            "(หน้าแดงเล็กน้อย) \"อะ... อะไรของเจ้า... ชมกันโต้งๆ แบบนี้เลยเรอะ... อี... ก็ไม่เลว\"",
            new String[]{
                "เย็นนี้ว่างไหม? ไปหาใกินกัน",
                "งั้นไว้เจอกันนะ",
                "เป็นเหรอครับ?"
            },
            new String[]{"1.5A", "1.5B", "1.5A"}
        ));

        // 1.4B
        tree.put("1.4B", new Node(
            "\"ชี้! เตรียมตัวแพ้ได้เลย... แต่ถ้าเจ้าฝีมือดีจริง ข้าอาจจะพิจารณาให้มาร่วมปาร์ตี้กับนะ\"",
            new String[]{
                "ผมจะทำให้ท่านยอมรับเอง",
                "ใครอยากอยู่กับเธอกัน",
                "ถ้าข้าชนะ เธอต้องมาเข้าปาร์ตี้บ้านนะ"
            },
            new String[]{"1.5A", "1.5C", "1.5A"}
        ));

        // 1.4C
        tree.put("1.4C", new Node(
            "\"ปากดีนักนะ! ...หวังว่าฝีมือดาบจะดีเหมือนฝีปากละกัน อย่าตายคาดาบข้าซะก่อนนะล่ะ\"",
            new String[]{
                "สบายมาก",
                "เธอนั้นแหละระวังตัวไว้",
                "ออมมือให้ด้วยนะครับ"
            },
            new String[]{"1.5B", "1.5C", "1.5B"}
        ));

        // 1.5A
        tree.put("1.5A", new Node(
            "\"เออ! พูดได้ดี! ...อย่าทำให้ข้าผิดหวังล่ะคู่หู! เจอกันเย็นนี้!!\" (ตบหลังคุณดังปัก)",
            new String[]{
                "โอ้ย! มือหนักชะมัด... แต่ก็เจอกันครับ!",
                "ครับผม",
                "เริ่มไม่อยากไปละ"
            },
            new String[]{"END_BEST", "END_GOOD", "END_NORMAL"}
        ));

        // 1.5B
        tree.put("1.5B", new Node(
            "\"เออ... ไปละ ข้าต้องไปซ้อมต่อ\" (เดินจากไปแบบหงุดหงิดนิดๆ)",
            new String[]{
                "ไว้เจอกัน",
                "(เดินแยกทาง)",
                "อย่าลืมอาบน้ำนะ!"
            },
            new String[]{"END_GOOD", "END_NORMAL", "END_BAD"}
        ));

        // 1.5C
        tree.put("1.5C", new Node(
            "\"ชื้... เสียอารมณ์ชะมัด เจอแต่พวกไม่ได้เรื่อง\" (เดินกระแทกไหล่คุณผ่านไป)",
            new String[]{
                "นิสัยเสีย!",
                "(ไม่สนใจ)",
                "เดี๋ยวสิ!"
            },
            new String[]{"END_BAD", "END_NORMAL", "END_NORMAL"}
        ));

        // END Nodes
        tree.put("END_BEST",   new Node("END_BEST"));
        tree.put("END_GOOD",   new Node("END_GOOD"));
        tree.put("END_NORMAL", new Node("END_NORMAL"));
        tree.put("END_BAD",    new Node("END_BAD"));

        return tree;
    }

    // --- Affection Bonus ตาม END ---
    public static int getAffectionBonus(String endId) {
        return switch (endId) {
            case "END_BEST"   -> 30;
            case "END_GOOD"   -> 15;
            case "END_NORMAL" -> 5;
            default           -> -10;
        };
    }

    // =========================================================
    // สร้าง JPanel ของ Scene นี้
    // callback รับ endId เมื่อ scene จบ เพื่อให้ LazelPanel ไป addAffection
    // =========================================================
    public static JPanel createPanel(ActionListener onSceneEnd) {
        Map<String, Node> tree = buildTree();

        // Panel หลัก
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(20, 20, 30));

        // --- ข้อความ NPC ---
        JTextArea txtDialogue = new JTextArea();
        txtDialogue.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtDialogue.setForeground(Color.WHITE);
        txtDialogue.setBackground(new Color(20, 20, 30));
        txtDialogue.setLineWrap(true);
        txtDialogue.setWrapStyleWord(true);
        txtDialogue.setEditable(false);
        txtDialogue.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // --- Panel ปุ่มตัวเลือก ---
        JPanel panelChoices = new JPanel();
        panelChoices.setLayout(new GridLayout(0, 1, 5, 5));
        panelChoices.setBackground(new Color(20, 20, 30));
        panelChoices.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        panel.add(txtDialogue, BorderLayout.CENTER);
        panel.add(panelChoices, BorderLayout.SOUTH);

        // --- Function แสดง node ---
        showNode(tree, "1.1", txtDialogue, panelChoices, onSceneEnd);

        return panel;
    }

    // แสดง node ปัจจุบัน และสร้างปุ่มตัวเลือก
    private static void showNode(Map<String, Node> tree, String nodeId,
                                  JTextArea txtDialogue, JPanel panelChoices,
                                  ActionListener onSceneEnd) {
        Node node = tree.get(nodeId);
        if (node == null) return;

        // แสดงข้อความ
        if (node.isEnd()) {
            // ถ้าเป็น END ให้ callback กลับไป LazelPanel
            ActionEvent fakeEvent = new ActionEvent(nodeId, ActionEvent.ACTION_PERFORMED, nodeId);
            onSceneEnd.actionPerformed(fakeEvent);
            return;
        }

        txtDialogue.setText("Lazel:\n" + node.npcText);

        // ล้างปุ่มเก่า
        panelChoices.removeAll();

        // สร้างปุ่มใหม่
        for (int i = 0; i < node.choices.length; i++) {
            final String nextId = node.nextIds[i];
            JButton btn = new JButton(node.choices[i]);
            btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(50, 50, 80));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            btn.addActionListener(e ->
                showNode(tree, nextId, txtDialogue, panelChoices, onSceneEnd)
            );
            panelChoices.add(btn);
        }

        panelChoices.revalidate();
        panelChoices.repaint();
    }
}