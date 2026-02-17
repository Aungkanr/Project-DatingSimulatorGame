package UXUI.Scene;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import UXUI.MainFrame;
import UXUI.Hovereffect;
import Utility.*;

public class SchoolPanel extends JPanel {
    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private JLabel lblBg;
    private GameTime realGameTime ;
    private Notify realNotify ;

    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        setLayout(null);
        setBackground(Color.BLACK);

        this.realGameTime = mainFrame.getGameTime(); // ดึงเวลามาจาก MainFrame
        this.realNotify = new Notify(stdScreen.width); // สร้างตัวแจ้งเตือน
        this.realNotify.setBounds(0, 50, stdScreen.width, 50); // กำหนดตำแหน่ง
        add(realNotify); // *** อย่าลืม add เข้า Panel ***

        // ---------------------------------------------------------
        initComponents();
        setComponentZOrder(realNotify, 0);
    }

    private void initComponents() {
        JButton btnLazel = createButton("Talk to Lazel", 1, e -> {
            if (realGameTime.getTimeSlot() < 3) {
                // สั่ง MainFrame ให้สร้างและโชว์หน้า Lazel
                //go to scene Lazel
                mainFrame.createLazelPanel(); 
                mainFrame.showLazel();
            } else realNotify.showNotify("Night has fallen, go to sleep.", Color.RED, 2000);
        } );
        add(btnLazel);

        // --- ปุ่ม Back to Town ---
        JButton btnBack = new JButton("Back to Town");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBack.setBounds(20, 20, 150, 30);
        btnBack.addActionListener(e -> mainFrame.showGame());
        Hovereffect.HoverEffect(btnBack, 20, 20, 150, 30, new Color(48, 25, 82));
        add(btnBack);

        // --- Background ---
        setupBackground("image\\Scene\\School\\โรงเรียนตอนเช้า.png");
    }
    //แก้ Button ตรงนี้
    private JButton createButton(String text, int order, ActionListener action) {
        int btnW = stdScreen.buttonWidth;
        int btnH = stdScreen.buttonHeight;
        int gap = 20;
        int x = stdScreen.centerX;
        int y = stdScreen.bottomY - ((3 - order) * (btnH + gap));

        JButton btn = new JButton(text);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBounds(x, y, btnW, btnH);
        btn.addActionListener(action);
        
        // ใส่สีให้ปุ่มสวยๆ
        Hovereffect.HoverEffect(btn, x, y, btnW, btnH, new Color(41, 128, 185));
        
        return btn;
    }

    private void setupBackground(String path) {
        lblBg = new JLabel("");
        ImageIcon icon = AssetManager.getInstance().getImage(path);
        if (icon != null) {
            new Utility.CheckImage().checkImage(icon, lblBg, stdScreen.width, stdScreen.height);
        }
        lblBg.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblBg);
        setComponentZOrder(lblBg, getComponentCount() - 1);
        setComponentZOrder(realNotify, 0); 
    }
}