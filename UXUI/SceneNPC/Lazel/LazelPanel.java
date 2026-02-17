package UXUI.SceneNPC.Lazel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import UXUI.MainFrame;
import UXUI.Scene.CreateTemplateScene;
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
    // STATE 1: เมนูโต้ตอบ (Talk / Give Gift)
    // ==========================================
    public void showInteractionMenu() {
        removeAll();

        // 1. ปุ่ม Talk
        createButton("Talk", 1, e -> {
            String text = lazel.getDialogue();

            // ถ้า dialogue นี้มาจาก Special Scene → ส่งไป SpecialScenePanel
            if (lazel.isLastDialogueSpecial()) {
                int sceneLevel = lazel.getLastSpecialSceneLevel();
                lazel.resetSpecialSceneFlag();
                mainFrame.createSpecialScenePanel(lazel, text, sceneLevel);
                mainFrame.showSpecialScene();
            } else {
                showDialogueMode(text);
            }
        });

        // 2. ปุ่ม Give Gift
        String giftText = lazel.isGiftedToday() ? "Gift Given (Daily Limit)" : "Give Gift (+20)";
        JButton btnGift = createButton("Give Gift", 2, null);
        btnGift.setText(giftText);
        
        if (lazel.isGiftedToday()) {
            btnGift.setEnabled(false);
        } else {
            btnGift.addActionListener(e -> {
                String result = lazel.giveGift();
                JOptionPane.showMessageDialog(this, result);
                showInteractionMenu();
            });
        }
        add(btnGift);

        // 3. แสดง Status หัวใจ
        JLabel lblStatus = new JLabel("Relationship: Lazel (Lv." + lazel.getHeartLevel() + ")");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(50, 50, 600, 40);
        add(lblStatus);

        // 4. ปุ่ม Back
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> {
            mainFrame.createSchoolPanel();
            mainFrame.showSchool();
        });
        Hovereffect.HoverEffect(btnBack, 20, 20, 100, 30, new Color(48, 25, 82));
        add(btnBack);

        setupBackground("image\\Scene\\School\\Angryscene.png");
        revalidate();
        repaint();
    }

    // ==========================================
    // STATE 2: โหมดบทสนทนาธรรมดา (ไม่มี Choice)
    // ==========================================
    private void showDialogueMode(String text) {
        removeAll();

        CreateTemplateScene scene = new CreateTemplateScene(
            "image\\Scene\\School\\Angryscene.png",
            "Lazel",
            text,
            null,
            text,
            new CreateTemplateScene.SceneOption("Continue...", e -> showInteractionMenu())
        );
        
        scene.setBounds(0, 0, getWidth(), getHeight());
        add(scene);

        revalidate();
        repaint();
    }

    // --- Helper Methods ---
    private JButton createButton(String text, int order, ActionListener action) {
        int btnW = stdScreen.buttonWidth;
        int btnH = stdScreen.buttonHeight;
        int gap  = 20;
        int x    = stdScreen.centerX;
        int y    = stdScreen.bottomY - ((3 - order) * (btnH + gap));

        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBounds(x, y, btnW, btnH);
        if (action != null) btn.addActionListener(action);
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