package UXUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


import Utility.*;

public class CoopMenuPanel extends JPanel {
    
    private MainFrame mainFrame;
    private StdAuto stdScreen = new StdAuto();
    ScreenFader fader = new ScreenFader();

    // --- ธีมสี (อิงจาก Setting) ---
    Color boxOuterColor = new Color(30, 30, 60, 230); 
    Color boxInnerColor = new Color(20, 20, 40);      
    Color inactiveTabColor = new Color(80, 40, 120); 
    Color btnBackBgColor = new Color(100, 60, 150);
    Color btnActionColor = new Color(138, 43, 226); // สีม่วงสำหรับปุ่ม Join/Create
    Color inputBgColor = new Color(40, 40, 70); 

    // ตัวแปรสลับหน้าจอ (CardLayout)
    private JPanel contentCards; 
    private CardLayout mainCardLayout;

    // ตัวสลับหน้าจอย่อยของ Tab Join และ Host
    private JPanel joinCards;
    private CardLayout joinCardLayout;
    
    private JPanel hostCards;
    private CardLayout hostCardLayout;

    private JButton btnTabJoin, btnTabHost;
    
    private CheckImage checkImageUtil;

    
    public CoopMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        stdScreen.setBtnWHG(300, 60, 20, 2);

        fader.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(fader);

        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void initComponents() {
        int centerX = stdScreen.width / 2;
        int centerY = stdScreen.height / 2;

        int outerW = 800;
        int outerH = 500;
        int outerX = centerX - (outerW / 2);
        int outerY = centerY - (outerH / 2);

        // =========================================
        // 1. สร้าง TABS (Join / Host)
        // =========================================
        int tabW = 120;
        int tabH = 50;
        int tabY = outerY - tabH + 10; 

        btnTabJoin = createTabButton("Join", outerX + 20, tabY, tabW, tabH, true);
        btnTabHost = createTabButton("Host", outerX + 20 + tabW + 5, tabY, tabW, tabH, false);

        add(btnTabJoin);
        add(btnTabHost);

        checkImageUtil = new CheckImage();

        // =========================================
        // 2. กล่องหลัก (Outer Box)
        // =========================================
        JPanel outerPanel = createRoundedPanel(boxOuterColor, 40);
        outerPanel.setBounds(outerX, outerY, outerW, outerH);
        add(outerPanel);

        // =========================================
        // 3. พื้นที่เนื้อหาหลัก
        // =========================================
        mainCardLayout = new CardLayout();
        contentCards = createRoundedPanel(boxInnerColor, 30);
        contentCards.setLayout(mainCardLayout);
        int margin = 30;
        contentCards.setBounds(margin, margin, outerW - (margin * 2), outerH - (margin * 2));
        outerPanel.add(contentCards);

        // --- สร้างระบบย่อยของ Tab JOIN ---
        joinCardLayout = new CardLayout();
        joinCards = new JPanel(joinCardLayout);
        joinCards.setOpaque(false);
        joinCards.add(createJoinMenuPanel(), "JOIN_MENU");
        joinCards.add(createJoinInputPanel(), "JOIN_INPUT");

        // --- สร้างระบบย่อยของ Tab HOST ---
        hostCardLayout = new CardLayout();
        hostCards = new JPanel(hostCardLayout);
        hostCards.setOpaque(false);
        hostCards.add(createHostMenuPanel(), "HOST_MENU");
        hostCards.add(createHostInputPanel(), "HOST_INPUT");

        // ยัดลงกล่องหลัก
        contentCards.add(joinCards, "JOIN_TAB");
        contentCards.add(hostCards, "HOST_TAB");

        // =========================================
        // 4. Logic การสลับ Tab
        // =========================================
        btnTabJoin.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            updateTabStyles(true); 
            joinCardLayout.show(joinCards, "JOIN_MENU");
            mainCardLayout.show(contentCards, "JOIN_TAB");
        });

        btnTabHost.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            updateTabStyles(false); 
            hostCardLayout.show(hostCards, "HOST_MENU");
            mainCardLayout.show(contentCards, "HOST_TAB");
        });

        // =========================================
        // 5. ปุ่ม Back ออกจากโหมด Co-op
        // =========================================
        int btnBackW = 200;
        int btnBackH = 50;
        int btnBackY = outerY + outerH + 30;
        JButton btnMainBack = createRoundedButton("BACK");
        btnMainBack.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnMainBack, centerX - (btnBackW/2), btnBackY, btnBackW, btnBackH, btnBackBgColor);
        btnMainBack.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            fader.fadeInOut(250, 250, ()-> {mainFrame.showMenu();}, null);
        });
        add(btnMainBack);


        // =========================================
        // 6. แผ่นฟิล์มสีดำโปร่งใส (Dark Overlay)
        // =========================================
        JPanel darkOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // ระบายสีดำ ความโปร่งใสระดับ 150 (ปรับมืด/สว่างได้ที่ตัวเลขนี้ 0-255)
                g.setColor(new Color(0, 0, 0, 150)); 
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        darkOverlay.setOpaque(false); // ต้องตั้งเป็น false เพื่อไม่ให้จอกระพริบ (บัคของ Swing)
        darkOverlay.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(darkOverlay);

        // =========================================
        // 7. รูปภาพ Background ของคุณ (อยู่ล่างสุด)
        // =========================================
        JLabel lblMap = new JLabel("");
        String imagePath = "image\\MenuBackground.png";
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);
        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

        // =========================================
        // จัดลำดับชั้นความลึก (Z-Order) สำคัญมาก!
        // =========================================
        setComponentZOrder(fader, 0);                             // ชั้นที่ 0 (หน้าสุด): เอฟเฟกต์เฟดจอ
        setComponentZOrder(darkOverlay, getComponentCount() - 1); // ชั้นเกือบสุดท้าย: แผ่นฟิล์มสีดำ
        setComponentZOrder(lblMap, getComponentCount() - 1);      // ชั้นล่างสุด: รูปภาพพื้นหลัง (ดันฟิล์มดำขึ้นไป 1 สเตป)
    }

    // ===========================================================================
    // UI ย่อย: JOIN
    // ===========================================================================
    private JPanel createJoinMenuPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(60, 40, 40, 40)); 

        p.add(createMenuItemButton("Join LAN Game...", e -> {
            JOptionPane.showMessageDialog(this, "Searching for LAN games...");
        }));
        p.add(Box.createRigidArea(new Dimension(0, 30))); 
        
        p.add(createMenuItemButton("Enter IP Address...", e -> {
            joinCardLayout.show(joinCards, "JOIN_INPUT");
        }));
        return p;
    }

    private JPanel createJoinInputPanel() {
        JPanel p = new JPanel(null);
        p.setOpaque(false);

        JLabel lblTitle = new JLabel("Enter Host IP Address:", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 50, 740, 40);
        p.add(lblTitle);

        JTextField ipField = new JTextField("Enter IP ...");
        ipField.setBounds(170, 120, 400, 60);
        ipField.setBackground(inputBgColor);
        ipField.setForeground(new Color(200, 200, 200));
        ipField.setFont(new Font("Tahoma", Font.PLAIN, 24));
        ipField.setHorizontalAlignment(JTextField.CENTER);
        ipField.setBorder(new EmptyBorder(5, 15, 5, 15)); 
        ipField.setCaretColor(Color.WHITE);
        
        ipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ipField.getText().equals("Enter IP ...")) {
                    ipField.setText("");
                    ipField.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (ipField.getText().isEmpty()) {
                    ipField.setForeground(new Color(200, 200, 200));
                    ipField.setText("Enter IP ...");
                }
            }
        });
        p.add(ipField);

        JLabel lblError = new JLabel("error : Invalid IP Can't Found ROOM!!", SwingConstants.CENTER);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblError.setForeground(new Color(255, 100, 100));
        lblError.setBounds(0, 190, 740, 30);
        lblError.setVisible(false);
        p.add(lblError);

        JButton btnJoin = createRoundedButton("Join");
        btnJoin.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnJoin, 170, 240, 190, 50, btnActionColor);
        btnJoin.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            String ip = ipField.getText().trim();
            if (ip.isEmpty() || ip.equals("Enter IP ...")) {
                lblError.setVisible(true); 
            } else {
                lblError.setVisible(false);
                fader.fadeOut(500, () -> {
                    // [อัปเดตใหม่] คน Join จะเปิดหน้า Lobby โดยมี 5 ช่องโชว์ไว้ก่อน 
                    // (อนาคตตอนเชื่อม Network เสร็จ Server จะเป็นคนสั่งอีกทีว่ามีกี่คน)
                    mainFrame.getLobbyPanel().setupRoom(5, false, ip);
                    mainFrame.showLobby();
                    fader.fadeIn(500, null);
                });
            }
        });
        p.add(btnJoin);

        JButton btnCancel = createRoundedButton("Cancel");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnCancel, 380, 240, 190, 50, btnBackBgColor);
        btnCancel.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            lblError.setVisible(false);
            joinCardLayout.show(joinCards, "JOIN_MENU");
        });
        p.add(btnCancel);

        return p;
    }

    // ===========================================================================
    // UI ย่อย: HOST
    // ===========================================================================
    private JPanel createHostMenuPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(60, 40, 40, 40)); 

        p.add(createMenuItemButton("Host New Game...", e -> {
            hostCardLayout.show(hostCards, "HOST_INPUT");
        }));
        return p;
    }

    private JPanel createHostInputPanel() {
        JPanel p = new JPanel(null);
        p.setOpaque(false);

        JLabel lblTitle = new JLabel("Max Players (1-5)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 60, 740, 40);
        p.add(lblTitle);

        // [อัปเดตใหม่] Slider (ค่าน้อยสุด 1, สูงสุด 5, เริ่มต้นที่ 2)
        JSlider slider = new JSlider(1, 5, 2); 
        slider.setBounds(170, 120, 400, 60);
        slider.setOpaque(false);
        slider.setForeground(Color.WHITE);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setFont(new Font("Tahoma", Font.BOLD, 16));
        p.add(slider);

        JButton btnCreate = createRoundedButton("Create Room");
        btnCreate.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnCreate, 170, 220, 190, 50, btnActionColor);
        
        btnCreate.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            
            // ดึงค่าจำนวนคนที่ต้องการจาก Slider
            int maxP = slider.getValue(); 
            
            Coop.Network.GameServer.startServerInBackground(9999); 
            fader.fadeOut(500, () -> {
                // [อัปเดตใหม่] ส่งจำนวน maxP ไปบอกให้ LobbyPanel สร้างช่องผู้เล่นตามจำนวน
                mainFrame.getLobbyPanel().setupRoom(maxP, true, "localhost"); 
                mainFrame.showLobby();
                fader.fadeIn(500, null);
            });
        });
        p.add(btnCreate);

        JButton btnCancel = createRoundedButton("Cancel");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
        Hovereffect.HoverEffectRounded(btnCancel, 380, 220, 190, 50, btnBackBgColor);
        btnCancel.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            hostCardLayout.show(hostCards, "HOST_MENU"); 
        });
        p.add(btnCancel);

        return p;
    }

    // ===========================================================================
    // Helper Functions
    // ===========================================================================
    private void updateTabStyles(boolean isJoinActive) {
        if (isJoinActive) {
            btnTabJoin.setBackground(boxOuterColor); 
            btnTabHost.setBackground(inactiveTabColor); 
        } else {
            btnTabJoin.setBackground(inactiveTabColor);
            btnTabHost.setBackground(boxOuterColor);
        }
        btnTabJoin.repaint();
        btnTabHost.repaint();
    }

    private JPanel createRoundedPanel(Color color, int radius) {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            }
        };
        p.setBackground(color);
        p.setOpaque(false);
        p.setLayout(null);
        return p;
    }

    private JButton createTabButton(String text, int x, int y, int w, int h, boolean isActive) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight() + 20, 30, 30);
                
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 - 5;
                g2.drawString(getText(), textX, textY);
            }
        };
        btn.setBounds(x, y, w, h);
        btn.setBackground(isActive ? boxOuterColor : inactiveTabColor);
        btn.setFont(new Font("Tahoma", Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        return btn;
    }

    private JButton createMenuItemButton(String text, ActionListener action) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 70));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 215, 0)); 
                    g2.setStroke(new BasicStroke(3));
                    g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 20, 20);
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Tahoma", Font.BOLD, 24));
        btn.setForeground(Color.WHITE);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(600, 80)); 
        btn.setPreferredSize(new Dimension(600, 80));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            action.actionPerformed(e);
        });
        return btn;
    }

    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        return btn;
    }
}