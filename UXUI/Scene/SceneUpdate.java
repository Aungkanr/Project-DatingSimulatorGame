package UXUI.Scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UXUI.Hovereffect;
import UXUI.DialoguePanel;
import Utility.StdAuto;

public class SceneUpdate extends JPanel {

    // --- Inner Class: SceneOption (เหมือนเดิม) ---
    public static class SceneOption {
        String text;
        ActionListener action;
        Integer x, y, w, h; 

        // แบบ Auto Layout
        public SceneOption(String text, ActionListener action) {
            this.text = text;
            this.action = action;
            this.x = null; 
        }

        // แบบ Custom Layout
        public SceneOption(String text, int x, int y, int w, int h, ActionListener action) {
            this.text = text;
            this.action = action;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
    // ---------------------------------------------

    private StdAuto stdScreen;
    private DialoguePanel dialogueBox;
    private Utility.CheckImage checkImageUtil;
    
    // ตัวแปรพื้นฐาน
    private String bgPath;
    private String speakerName;
    private String dialogueText;
    private SceneOption[] options;
    private ActionListener backAction;

    // ตัวแปรสำหรับ Dialogue Box
    private int diaX, diaY, diaW, diaH;
    private boolean isCustomDialogue = false; // ตัวเช็คว่าผู้ใช้กำหนดขนาดเองไหม

// --- Constructor 1: แบบ Auto (ไม่ระบุขนาด) ---
    public SceneUpdate(String bgPath, String speakerName, String dialogueText, ActionListener backAction, SceneOption... options) {
        this.bgPath = bgPath;
        this.speakerName = speakerName;
        this.dialogueText = dialogueText;
        this.backAction = backAction;
        this.options = options;
        
        this.isCustomDialogue = false; // บอกระบบว่า "ฉันไม่ได้กำหนดขนาดนะ คำนวณให้หน่อย"
        
        initialize(); // เริ่มสร้างหน้าจอ
    }
    //Overloading
    // --- Constructor 2: แบบ Custom (กำหนดขนาดเอง) ---
    public SceneUpdate(String bgPath, String speakerName, String dialogueText, int dX, int dY, int dW, int dH, ActionListener backAction, SceneOption... options) {
        this.bgPath = bgPath;
        this.speakerName = speakerName;
        this.dialogueText = dialogueText;
        this.backAction = backAction;
        this.options = options;
        
        // รับค่าขนาดมา
        this.diaX = dX;
        this.diaY = dY;
        this.diaW = dW;
        this.diaH = dH;
        this.isCustomDialogue = true; // บอกระบบว่า "ใช้ขนาดตามที่บอกนี้นะ"
        
        initialize(); // เริ่มสร้างหน้าจอ
    }

    private void initialize() {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0));
        
        initTools();
        initComponents();   // คำนวณและสร้าง Dialogue ตรงนี้
        initBackground();
    }

    private void initTools() {
        stdScreen = new StdAuto();
        checkImageUtil = new Utility.CheckImage();
        stdScreen.setBtnWHG(250, 50, 20, 0);
    }

    private void initComponents() {
        // --- 1. จัดการ Dialogue Box ---
        dialogueBox = new DialoguePanel();
        
        // ถ้าผู้ใช้ไม่ได้กำหนดขนาดมา (isCustomDialogue เป็น false) ให้คำนวณใหม่
        if (!isCustomDialogue) {
            this.diaW = 1425;
            this.diaH = 250;
            // ใช้ stdScreen ในการคำนวณ (ต้องมั่นใจว่า initTools ถูกเรียกก่อนหน้านี้แล้ว ซึ่งใน initialize() เราเรียกลำดับถูกแล้ว)
            this.diaX = (stdScreen.width - this.diaW) / 2;
            this.diaY = stdScreen.bottomY - this.diaH - 50;
        } 
        
        // ตั้งค่าตำแหน่ง (ตอนนี้ค่าทุกอย่างถูกต้องแล้วแน่นอน)
        dialogueBox.setBounds(diaX, diaY, diaW, diaH);
        dialogueBox.setText(speakerName, dialogueText);
        add(dialogueBox);

        // --- 2. จัดการปุ่ม (เหมือนเดิม) ---
        if (options != null && options.length > 0) {
            List<SceneOption> autoLayoutOptions = new ArrayList<>();
            
            for (SceneOption opt : options) {
                if (opt.x != null) {
                    JButton btn = createButton(opt.text, opt.x, opt.y, opt.w, opt.h, opt.action);
                    add(btn);
                } else {
                    autoLayoutOptions.add(opt);
                }
            }

            if (!autoLayoutOptions.isEmpty()) {
                int btnW = stdScreen.buttonWidth;
                int btnH = stdScreen.buttonHeight;
                int gap = 20;
                int numButtons = autoLayoutOptions.size();
                
                int totalWidth = (numButtons * btnW) + ((numButtons - 1) * gap);
                int startX = (stdScreen.width - totalWidth) / 2;

                for (int i = 0; i < numButtons; i++) {
                    SceneOption opt = autoLayoutOptions.get(i);
                    int btnX = startX + (i * (btnW + gap));
                    
                    // ใช้ Y เดียวกับ Dialogue ถ้ามันบังกันต้องระวัง แต่ปกติปุ่มอยู่ด้านล่าง
                    // เพื่อความปลอดภัย ใช้ stdScreen.bottomY เป็นฐาน
                    JButton btn = createButton(opt.text, btnX, stdScreen.bottomY, btnW, btnH, opt.action);
                    add(btn);
                }
            }
        }

        // --- 3. ปุ่ม Back ---
        if (backAction != null) {
            JButton btnBack = createButton("Back", 20, 20, 100, 30, backAction);
            btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16)); 
            add(btnBack);
        }
    }

    private JButton createButton(String text, int x, int y, int w, int h, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(action);
        btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        Hovereffect.HoverEffect(btn, x, y, w, h, new Color(55, 55, 55));
        adjustFontSizeToFit(btn); // ปรับขนาดตัวอักษร
        return btn;
    }

    private void adjustFontSizeToFit(JButton btn) {
        Font originalFont = btn.getFont();
        String text = btn.getText();
        int btnWidth = btn.getWidth();
        int availableWidth = btnWidth - 20; 

        if (availableWidth <= 0) return;

        FontMetrics fm = btn.getFontMetrics(originalFont);
        int textWidth = fm.stringWidth(text);

        int newSize = originalFont.getSize();
        while (textWidth > availableWidth && newSize > 8) {
            newSize--;
            Font newFont = new Font(originalFont.getName(), originalFont.getStyle(), newSize);
            fm = btn.getFontMetrics(newFont);
            textWidth = fm.stringWidth(text);
            btn.setFont(newFont);
        }
    }

    private void initBackground() {
        JLabel lblMap = new JLabel("");
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage(bgPath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);
        setComponentZOrder(lblMap, getComponentCount() - 1);
    }
}