package UXUI.SceneNPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import UXUI.MainFrame;
import UXUI.Scene.CreateTemplateScene;
import UXUI.Scene.CreateTemplateScene.SceneOption;
import UXUI.Hovereffect;
import Utility.*;
import Relationship.NPC; 

public abstract class BaseNPCPanel extends JPanel {
    protected MainFrame mainFrame;
    protected StdAuto stdScreen;
    protected NPC targetNPC; // Lazel/Galadriel/Arwen
    protected String bgPath;
    protected JLabel lblBg;

    // Constructor (Parent class)  
    public BaseNPCPanel(MainFrame mainFrame, NPC targetNPC, String bgPath) {
        this.mainFrame = mainFrame;
        this.targetNPC = targetNPC;
        this.bgPath = bgPath;
        
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        setLayout(null);
        setBackground(Color.BLACK);

        showInteractionMenu();
    }

    // ==========================================
    // abstract class --> child class เขียนเอง
    // ==========================================
    protected abstract void returnBtn(); // return previous page 
    protected abstract void triggerSpecialScene(String text, int sceneLevel); // เรียก Scene ยังไง
    protected abstract void processGift(String itemName); // give gift แล้ว NPC จะ reaction ยังไง  


    public void showInteractionMenu() {
        removeAll();
        
        // --------------------- ปุ่ม Talk ---------------------
        createButton("Talk", 1, e -> {
            String text = targetNPC.getDialogue();
            if (targetNPC.isLastDialogueSpecial()) {
                int sceneLevel = targetNPC.getLastSpecialSceneLevel();
                targetNPC.resetSpecialSceneFlag();
                triggerSpecialScene(text, sceneLevel); // เรียก Method abstract เรียก Scene
            } else {
                showDialogueMode(text);
            }
        });

        // --------------------- ปุ่ม Give Gift ---------------------
        String giftText = targetNPC.isGiftedToday() ? "Gift Given (Daily Limit)" : "Give Gift";
        JButton btnGift = createButton("Give Gift", 2, null);
        btnGift.setText(giftText);
        
        if (targetNPC.isGiftedToday()) {
            btnGift.setEnabled(false);
        } else {
            btnGift.addActionListener(e -> showGiftSelectionMode());
        }
        add(btnGift);

        // --------------------- Status Label ---------------------
        JLabel lblStatus = new JLabel("Relationship: " + targetNPC.getName() + " (Lv." + targetNPC.getHeartLevel() + ")");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(50, 50, 600, 40);
        add(lblStatus);

        // --------------------- ปุ่ม Back ---------------------
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> returnBtn()); // เรียก Method abstract ย้อนหน้า
        Hovereffect.HoverEffect(btnBack, 20, 20, 100, 30, new Color(48, 25, 82));
        add(btnBack);

        setupBackground(bgPath);
        revalidate(); repaint();
    }

    protected void showDialogueMode(String text) {
        removeAll();
        CreateTemplateScene scene = new CreateTemplateScene(bgPath, targetNPC.getName(), text, null, null,  
            new SceneOption("Continue...", e -> showInteractionMenu()));
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);
        revalidate(); repaint();
    }
    // ------------ give gift ------------------
    protected void showGiftSelectionMode() {
        removeAll();
        Map<String, Integer> items = mainFrame.getPlayer().getInventory().getItemsMap();
        List<SceneOption> optionsList = new ArrayList<>();
        
        if (items.isEmpty()) {
            optionsList.add(new SceneOption("Back", e -> showInteractionMenu()));
            CreateTemplateScene scene = new CreateTemplateScene(bgPath, "System", "กระเป๋าของคุณว่างเปล่า...", null, null, optionsList.toArray(new SceneOption[0]));
            scene.setBounds(0, 0, getWidth(), getHeight());
            add(scene);
        } else {
            for (String itemName : items.keySet()) {
                int qty = items.get(itemName);
                optionsList.add(new SceneOption(itemName + " (x" + qty + ")", e -> processGift(itemName)));
            }
            optionsList.add(new SceneOption("Cancel", e -> showInteractionMenu()));
            CreateTemplateScene scene = new CreateTemplateScene(bgPath, "System", "เลือกของขวัญที่จะให้ " + targetNPC.getName() + ":", null, null, optionsList.toArray(new SceneOption[0]));
            scene.setBounds(0, 0, getWidth(), getHeight());
            add(scene);
        }
        revalidate(); repaint();
    }
    // =========== Helper ==============
    protected JButton createButton(String text, int order, ActionListener action) {
        int btnW = stdScreen.buttonWidth;
        int btnH = stdScreen.buttonHeight;
        int gap  = 20;
        int x    = stdScreen.centerX;
        int y    = stdScreen.bottomY - ((3 - order) * (btnH + gap));

        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBounds(x, y, btnW, btnH);
        if(action != null) btn.addActionListener(action);
        Hovereffect.HoverEffect(btn, x, y, btnW, btnH, new Color(85, 107, 47)); // สามารถแก้ให้ดึงสีจาก NPC ได้ทีหลัง
        add(btn);
        return btn;
    }

    protected void setupBackground(String path) {
        lblBg = new JLabel("");
        ImageIcon icon = AssetManager.getInstance().getImage(path);
        new Utility.CheckImage().checkImage(icon, lblBg, stdScreen.width, stdScreen.height);
        lblBg.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblBg);
        setComponentZOrder(lblBg, getComponentCount() - 1);
    }
}