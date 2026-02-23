package UXUI.SceneNPC.Lazel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import UXUI.MainFrame;
import UXUI.Hovereffect;
import Utility.*;
import Relationship.Lazel;
import Relationship.DialogueNode;

public class SpecialSceneLazelPanel extends JPanel {

    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private Lazel lazel;
    private JLabel lblBg;

    private final String bgPath = "image\\Scene\\School\\Angryscene.png";

    public SpecialSceneLazelPanel(MainFrame mainFrame, Lazel lazel, String sceneText, int sceneLevel) {
        this.mainFrame = mainFrame;
        this.lazel     = lazel;
        this.stdScreen = new StdAuto();

        setLayout(null);
        setBackground(Color.BLACK);

        DialogueNode root = lazel.getDialogueTree(sceneLevel);
        showNode(root);
    }

    // ==========================================
    // Core: แสดง node ปัจจุบัน
    // ==========================================
    private void showNode(DialogueNode node) {
        removeAll();

        // ถ้า node มี imagePath → ใช้รูปนั้น, ถ้าไม่มี → ใช้ default
        String currentBg = (node.imagePath != null) ? node.imagePath : bgPath;
        setupBackground(currentBg);

        // --- กล่อง Dialogue ด้านบน ---
        JPanel dialogueBox = createDialogueBox(node.speaker, node.text);
        add(dialogueBox);

        if (node.isEnd) {
            showContinueButton();
        } else {
            JLabel lblPrompt = new JLabel("คุณจะตอบว่า...");
            lblPrompt.setFont(new Font("Tahoma", Font.BOLD, 18));
            lblPrompt.setForeground(new Color(255, 230, 150));
            lblPrompt.setBounds(50, 195, 400, 30);
            add(lblPrompt);

            for (int i = 0; i < node.choices.size(); i++) {
                final DialogueNode.Choice choice = node.choices.get(i);
                JButton btn = createChoiceButton(choice.label, i, e -> showNode(choice.next));
                add(btn);
            }
        }

        setComponentZOrder(lblBg, getComponentCount() - 1);
        revalidate();
        repaint();
    }

    // ==========================================
    // Helper: ปุ่ม Continue (END node)
    // ==========================================
    private void showContinueButton() {
        int btnW = 200;
        int btnH = 50;
        int btnX = (stdScreen.width - btnW) / 2;
        int btnY = stdScreen.height - 80 - btnH;

        JButton btnContinue = new JButton("Continue...");
        btnContinue.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnContinue.setBounds(btnX, btnY, btnW, btnH);
        btnContinue.addActionListener(e -> {
            mainFrame.createLazelPanel();
            mainFrame.showLazel();
        });
        Hovereffect.HoverEffect(btnContinue, btnX, btnY, btnW, btnH, new Color(85, 107, 47));
        add(btnContinue);
    }

    // ==========================================
    // Helper: กล่อง Dialogue ด้านบนจอ
    // ==========================================
    private JPanel createDialogueBox(String speaker, String text) {
        int boxH = 160;
        int boxY = 20;
        int boxW = stdScreen.width - 60;

        JPanel box = new JPanel(null);
        box.setBounds(30, boxY, boxW, boxH);
        box.setBackground(new Color(0, 0, 0, 200));
        box.setBorder(BorderFactory.createLineBorder(new Color(200, 170, 100), 2));

        JLabel lblName = new JLabel(speaker);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblName.setForeground(new Color(255, 210, 100));
        lblName.setBounds(15, 8, 200, 25);
        box.add(lblName);

        JTextArea txtDialogue = new JTextArea(text);
        txtDialogue.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtDialogue.setForeground(Color.WHITE);
        txtDialogue.setOpaque(false);
        txtDialogue.setEditable(false);
        txtDialogue.setLineWrap(true);
        txtDialogue.setWrapStyleWord(true);
        txtDialogue.setBounds(15, 40, boxW - 30, boxH - 50);
        box.add(txtDialogue);

        return box;
    }

    // ==========================================
    // Helper: ปุ่ม Choice
    // ==========================================
    private JButton createChoiceButton(String text, int index, ActionListener action) {
        int btnW   = stdScreen.width - 100;
        int btnH   = 52;
        int btnX   = 50;
        int gap    = 8;
        int startY = stdScreen.height - 80 - (3 * (btnH + gap));
        int btnY   = startY + index * (btnH + gap);

        JButton btn = new JButton((index + 1) + ".  " + text);
        btn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 130, 80), 1),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        btn.setBounds(btnX, btnY, btnW, btnH);
        btn.addActionListener(action);
        Hovereffect.HoverEffect(btn, btnX, btnY, btnW, btnH, new Color(60, 80, 40));
        return btn;
    }

    // ==========================================
    // Helper: Background
    // ==========================================
    private void setupBackground(String path) {
        lblBg = new JLabel("");
        ImageIcon icon = AssetManager.getInstance().getImage(path);
        new Utility.CheckImage().checkImage(icon, lblBg, stdScreen.width, stdScreen.height);
        lblBg.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblBg);
        setComponentZOrder(lblBg, getComponentCount() - 1);
    }
}