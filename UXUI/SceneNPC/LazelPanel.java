package UXUI.SceneNPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import UXUI.MainFrame;
import UXUI.Scene.CreateTemplateScene; 
import UXUI.Scene.CreateTemplateScene.SceneOption; // Import เพื่อใช้ปุ่ม
import UXUI.Hovereffect;
import Utility.*;
import Relationship.Lazel;

public class LazelPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private Lazel lazel;
    private JLabel lblBg;

    public LazelPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.lazel = mainFrame.getPlayer().getLazel(); 
        
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        setLayout(null);
        setBackground(Color.BLACK);

        showInteractionMenu();
    }

    // ==========================================
    // STATE 1: เมนูหลัก (Talk / Give Gift)
    // ==========================================
    public void showInteractionMenu() {
        removeAll();

        // ปุ่ม Talk
        createButton("Talk", 1, e -> {
            String text = lazel.getDialogue();
            showDialogueMode(text);
        });

        // ปุ่ม Give Gift
        String giftText = lazel.isGiftedToday() ? "Gift Given (Daily Limit)" : "Give Gift";
        JButton btnGift = createButton("Give Gift", 2, null); 
        btnGift.setText(giftText);
        
        if (lazel.isGiftedToday()) {
            btnGift.setEnabled(false);
        } else {
            // *** เมื่อกดปุ่มนี้ ให้เรียกฟังก์ชันสร้างหน้าเลือกของ ***
            btnGift.addActionListener(e -> showGiftSelectionMode());
        }
        add(btnGift);

        // Status Label
        JLabel lblStatus = new JLabel("Relationship: Lazel (Lv." + lazel.getHeartLevel() + ")");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(50, 50, 600, 40);
        add(lblStatus);

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> {
            mainFrame.createSchoolPanel();
            mainFrame.showSchool();
        });
        Hovereffect.HoverEffect(btnBack, 20, 20, 100, 30, new Color(48, 25, 82));
        add(btnBack);

        setupBackground("image\\Scene\\School\\Angryscene.png"); //ตอน interactionMenu
        revalidate();
        repaint();
    }

    // ==========================================
    // STATE 2: โหมดบทสนทนา (CreateTemplateScene)
    // ==========================================
    private void showDialogueMode(String text) {
        removeAll();
        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\School\\Angryscene.png", "Lazel", text, 
            null, null, 
            new SceneOption("Continue...", e -> showInteractionMenu())
        );
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); 
        repaint();
    }

    // ==========================================
    // STATE 3: โหมดเลือกของขวัญ (Dynamic Inventory Buttons)
    // ==========================================
    private void showGiftSelectionMode() {
        removeAll();
        // 1. ดึงข้อมูลของจาก Inventory
        Map<String, Integer> items = mainFrame.getPlayer().getInventory().getItemsMap();
        List<SceneOption> optionsList = new ArrayList<>();
        if (items.isEmpty()) { // 2. ถ้าไม่มีของ
            optionsList.add(new SceneOption("Back", e -> showInteractionMenu()));
            CreateTemplateScene scene = new CreateTemplateScene(
                "image\\Scene\\School\\Angryscene.png", "System", 
                "กระเป๋าของคุณว่างเปล่า...", 
                null, null, 
                optionsList.toArray(new SceneOption[0])
            );
            scene.setBounds(0, 0, getWidth(), getHeight());
            add(scene);
        } 
        // 3. ถ้ามีของ -> วนลูปสร้างปุ่ม
        else {
            for (String itemName : items.keySet()) {
                int qty = items.get(itemName); //get value (จำนวน)
                // สร้างปุ่ม: ชื่อของ (xจำนวน) -> กดแล้วเรียก processGift
                optionsList.add(new SceneOption(itemName + " (x" + qty + ")", e -> {
                    processGift(itemName);
                }));
            }
            // เพิ่มปุ่ม Cancel
            optionsList.add(new SceneOption("Cancel", e -> showInteractionMenu()));
            // สร้าง Scene
            CreateTemplateScene scene = new CreateTemplateScene(
                "image\\Scene\\School\\Angryscene.png", "System", 
                "เลือกของขวัญที่จะให้ Lazel:", 
                null, null, 
                optionsList.toArray(new SceneOption[0])
            );
            scene.setBounds(0, 0, getWidth(), getHeight());
            add(scene);
        }
        revalidate();
        repaint();
    }

    // --- Logic การให้ของขวัญ ---
    private void processGift(String itemName) {
        // 1. กำหนดคะแนน
        int points = 5; // Default
        if (itemName.equals("Poppy")) points = 10;
        if (itemName.equals("Tulip")) points = 15;
        if (itemName.equals("Fairy rose")) points = 20;
        
        // 2. ลบของ + เพิ่มคะแนน
        mainFrame.getPlayer().getInventory().removeItem(itemName);
        lazel.addAffection(points);
        lazel.markAsGifted();

        // 3. แสดงผลลัพธ์
        String resultText = "ให้ " + itemName + " แก่ Lazel แล้ว! (ความชอบ +" + points + ")";
        
        removeAll();
        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\School\\Angryscene.png", "System", 
            resultText, 
            null, null, 
            new SceneOption("OK", e -> showInteractionMenu())
        );
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }

    // --- Helper Methods ---
    private JButton createButton(String text, int order, ActionListener action) {
        int btnW = stdScreen.buttonWidth;
        int btnH = stdScreen.buttonHeight;
        int gap = 20;
        int x = stdScreen.centerX;
        int y = stdScreen.bottomY - ((3-order) * (btnH + gap)); 

        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBounds(x, y, btnW, btnH);
        if(action != null) btn.addActionListener(action);
        Hovereffect.HoverEffect(btn, x, y, btnW, btnH, new Color(85, 107, 47)); 
        add(btn);
        return btn;
    }

    private void setupBackground(String path) {
        lblBg = new JLabel("");
        ImageIcon icon = AssetManager.getInstance().getImage(path);
        new Utility.CheckImage().checkImage(icon, lblBg, stdScreen.width, stdScreen.height);
        lblBg.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblBg);
        setComponentZOrder(lblBg, getComponentCount() - 1);
    }
}