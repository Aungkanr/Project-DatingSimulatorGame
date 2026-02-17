package UXUI.SceneNPC; // Package ตาม Folder

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import UXUI.MainFrame;
import UXUI.Scene.SceneUpdate; // เรียกใช้ SceneUpdate จาก Folder Scene
import UXUI.Hovereffect;
import Utility.*;
import Relationship.Lazel; // เรียกข้อมูล NPC

public class LazelPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private Lazel lazel; // ตัวแปรเก็บข้อมูล Lazel
    private JLabel lblBg;

    public LazelPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        // ดึงข้อมูล Lazel ตัวจริงจาก Player (เพื่อให้ค่าความสัมพันธ์ไม่หาย)
        this.lazel = mainFrame.getPlayer().getLazel(); 
        
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        setLayout(null);
        setBackground(Color.BLACK);

        showInteractionMenu(); // เริ่มต้นที่หน้าเมนู
    }

    // ==========================================
    // STATE 1: เมนูโต้ตอบ (Talk / Give Gift)
    // ==========================================
    public void showInteractionMenu() {
        removeAll(); // ล้างหน้าจอ

        // 1. ปุ่ม Talk
        createButton("Talk", 1, e -> {
            String text = lazel.getDialogue(); // ดึงบทพูด (ระบบจะสุ่มหรือเลือก Scene ให้เอง)
            showDialogueMode(text);
        });

        // 2. ปุ่ม Give Gift (เช็คว่าให้ไปยัง)
        String giftText = lazel.isGiftedToday() ? "Gift Given (Daily Limit)" : "Give Gift (+20)";
        JButton btnGift = createButton("Give Gift", 2, null); 
        btnGift.setText(giftText);
        
        if (lazel.isGiftedToday()) {
            btnGift.setEnabled(false); // ปิดปุ่มถ้าให้ครบโควตาแล้ว
        } else {
            btnGift.addActionListener(e -> {
                String result = lazel.giveGift();
                JOptionPane.showMessageDialog(this, result);
                showInteractionMenu(); // รีเฟรชหน้าเพื่ออัปเดตปุ่ม
            });
        }
        add(btnGift);

        // 3. แสดง Status หัวใจ
        JLabel lblStatus = new JLabel("Relationship: Lazel (Lv." + lazel.getHeartLevel() + ")");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBounds(50, 50, 600, 40);
        add(lblStatus);

        // 4. ปุ่ม Back (กลับไปหน้าโรงเรียน)
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 30);
        btnBack.addActionListener(e -> {
            mainFrame.createSchoolPanel(); // กลับไปหน้าเลือกคน
            mainFrame.showSchool();
        });
        Hovereffect.HoverEffect(btnBack, 20, 20, 100, 30, new Color(48, 25, 82));
        add(btnBack);

        // Background
        setupBackground("image\\Scene\\School\\Angryscene.png"); 

        revalidate();
        repaint();
    }

    // ==========================================
    // STATE 2: โหมดบทสนทนา (SceneUpdate)
    // ==========================================
    private void showDialogueMode(String text) {
        removeAll();

        SceneUpdate scene = new SceneUpdate(
            "image\\Scene\\School\\Angryscene.png", // BG
            "Lazel",                                // Speaker Name
            text,                                   // Text
            
            // Back Action (กด Back กลับไปหน้าเมนู)
            e -> showInteractionMenu(), 
            
            // ปุ่ม Continue
            new SceneUpdate.SceneOption("Continue...", e -> showInteractionMenu())
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
        int gap = 20;
        int x = stdScreen.centerX;
        int y = stdScreen.bottomY - ((3-order) * (btnH + gap)); 

        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBounds(x, y, btnW, btnH);
        if(action != null) btn.addActionListener(action);
        
        // ใส่สีเขียวเข้มสไตล์ Lazel
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