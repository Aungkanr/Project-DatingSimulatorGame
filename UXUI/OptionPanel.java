package UXUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

import Utility.ScreenFader;
import Utility.StdAuto;
// import UXUI.StatusBarMenu.RoundedPanel; // ไม่ได้ใช้แล้ว

public class OptionPanel extends JPanel {

    private MainFrame parent;
    ScreenFader fader = new ScreenFader(); 
    private StdAuto stdScreen = new StdAuto();
    
    // รูปพื้นหลัง
    private Image bgImage;

    // --- ธีมสีใหม่ (โทนม่วง/น้ำเงิน) ---
    Color boxOuterColor = new Color(30, 30, 60, 230); // สีน้ำเงินเข้มอมม่วง (โปร่งแสง)
    Color boxInnerColor = new Color(20, 20, 40);      // สีน้ำเงินเข้มจัด (สำหรับกล่องใน)
    Color titleBgColor = new Color(80, 40, 120);      // สีม่วงเข้ม (หัวข้อ)
    Color btnBackBgColor = new Color(100, 60, 150);   // สีม่วง (ปุ่มกลับ)
    Color sliderColor = new Color(50, 200, 255);      // สีฟ้าสว่าง (Slider)
    // Color borderColor = new Color(120, 100, 180);  // ไม่ได้ใช้แล้ว
    
    public OptionPanel(MainFrame mainFrame) { 
        this.parent = mainFrame;
        
        setLayout(null);
        stdScreen.setBtnWHG(300, 60, 20 ,2);

        // 1. โหลดรูปพื้นหลัง
        ImageIcon icon = Utility.AssetManager.getInstance().getImage("image\\OptionBackGround.png"); 
        if (icon != null) {
            bgImage = icon.getImage();
        }

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        initComponents();
    }

    // 2. วาดพื้นหลัง + สีดำจางๆ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (bgImage != null) {
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }

        // สีดำจางๆ
        g2.setColor(new Color(0, 0, 0, 100)); 
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void initComponents() {
        int centerX = stdScreen.width / 2;
        int centerY = stdScreen.height / 2;

        int outerW = 600;
        int outerH = 380;
        int startY = centerY - (outerH / 2) - 25; 

        // 1. ป้าย Setting (ใช้ JPanel วาดเองให้สีทึบ)
        JPanel titlePill = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        titlePill.setBackground(titleBgColor);
        titlePill.setOpaque(false);
        titlePill.setBounds(centerX - 100, startY - 25, 200, 50); 
        titlePill.setLayout(new GridBagLayout()); 
        JLabel lblTitle = new JLabel("Setting");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        titlePill.add(lblTitle);
        add(titlePill); 

        // 2. กล่องหลัก (ใช้ JPanel วาดเองให้สีทึบ)
        JPanel outerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        outerPanel.setBackground(boxOuterColor);
        outerPanel.setOpaque(false);
        outerPanel.setLayout(null);
        outerPanel.setBounds(centerX - (outerW/2), startY, outerW, outerH);
        // outerPanel.setBorder(...) <-- เอาออกแล้ว เพื่อไม่ให้มีกรอบสี่เหลี่ยมทับ
        add(outerPanel); 

        // 3. กล่องเนื้อหาด้านใน (ใช้ JPanel วาดเองให้สีทึบ)
        int innerW = 500;
        int innerH = 220; 
        JPanel innerPanel = new JPanel() {
             @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        innerPanel.setBackground(boxInnerColor);
        innerPanel.setOpaque(false);
        innerPanel.setLayout(null);
        innerPanel.setBounds((outerW - innerW)/2, (outerH - innerH)/2, innerW, innerH);
        outerPanel.add(innerPanel);

        // --- ปุ่มควบคุมเสียง ---
        createSliderControl(innerPanel, "SFX", 40, true);
        createSliderControl(innerPanel, "MUSIC", 120, false);

        // 4. ปุ่มกลับเมนูหลัก (แก้ไขให้เป็นปุ่มมนสีทึบ) --------------------------------------
        JButton btnBack = new JButton("กลับเมนูหลัก") {
            // Override การวาดเพื่อให้ปุ่มเป็นทรงมน
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // วาดพื้นหลังมนๆ (ใช้สีจาก setBackground ที่ Hovereffect ส่งมา)
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 30 คือความมน

                // --- เอาส่วนที่วาดขอบสีขาวออก ---
                // g2.setColor(Color.WHITE); 
                // g2.setStroke(new BasicStroke(2));
                // g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 30, 30);
                // ------------------------------

                // วาดตัวหนังสือ
                super.paintComponent(g);
            }
        };
        
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnBack.setForeground(Color.WHITE);
        // ตั้งค่าจำเป็นสำหรับปุ่มมน
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        
        int btnY = outerH - 65; 
        
        // ** เรียกใช้ HoverEffectRounded **
        Hovereffect.HoverEffectRounded(btnBack, (outerW - 250)/2, btnY, 250, 50, btnBackBgColor);
        
        btnBack.addActionListener(e -> {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeInOut(250, 250, ()-> {parent.showMenu();}, null);
        });
        outerPanel.add(btnBack);
        // --------------------------------------------------------------------------

        // จัดลำดับการซ้อนทับ (Z-Order)
        setComponentZOrder(fader, 0);      
        setComponentZOrder(titlePill, 1);  
        setComponentZOrder(outerPanel, 2); 
    }

    private void createSliderControl(JPanel panel, String labelText, int yPos, boolean isSFX) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(40, yPos, 100, 30);
        panel.add(lbl);

        JLabel icon = new JLabel("\uD83D\uDD0A");
        icon.setForeground(Color.WHITE);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        icon.setBounds(15, yPos, 30, 30);
        panel.add(icon);

        JSlider slider = new JSlider(0, 100, 50);
        slider.setOpaque(false);
        slider.setBounds(40, yPos + 30, 300, 30);
        slider.setUI(new CustomSliderUI(slider, sliderColor));
        panel.add(slider);

        JCheckBox chkMute = new JCheckBox("MUTE " + labelText);
        chkMute.setFont(new Font("Tahoma", Font.BOLD, 12));
        chkMute.setForeground(Color.WHITE);
        chkMute.setOpaque(false);
        chkMute.setBounds(360, yPos + 30, 120, 30);
        
        if (isSFX) {
            float vol = parent.getSFXManager().getVolume();
            slider.setValue((int)(vol * 100));
            chkMute.setSelected(parent.getSFXManager().isMuted());
        } else {
            float vol = parent.getSoundManager().getVolume();
            slider.setValue((int)(vol * 100));
            chkMute.setSelected(parent.getSoundManager().isMuted());
        }

        slider.addChangeListener(e -> {
            float volume = slider.getValue() / 100f;
            if (isSFX) parent.setSFXVolume(volume);
            else parent.setMusicVolume(volume);
        });

        chkMute.addActionListener(e -> {
            if (isSFX) parent.toggleSFX(chkMute.isSelected());
            else parent.toggleMute(chkMute.isSelected());
        });
        panel.add(chkMute);
    }

    private class CustomSliderUI extends BasicSliderUI {
        Color progressColor;
        public CustomSliderUI(JSlider b, Color c) { super(b); this.progressColor = c; }
        @Override public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle trackBounds = trackRect;
            g2.setColor(new Color(40, 40, 60));
            g2.fillRoundRect(trackBounds.x, trackBounds.y + (trackBounds.height/2) - 4, trackBounds.width, 8, 8, 8);
            int fillWidth = xPositionForValue(slider.getValue()) - trackBounds.x;
            g2.setColor(progressColor);
            g2.fillRoundRect(trackBounds.x, trackBounds.y + (trackBounds.height/2) - 4, fillWidth, 8, 8, 8);
        }
        @Override public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillOval(thumbRect.x, thumbRect.y + (thumbRect.height/2) - 8, 16, 16);
        }
    }
}