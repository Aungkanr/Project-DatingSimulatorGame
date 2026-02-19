package UXUI.StatusBarMenu;

import java.awt.*;
import java.io.File;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Player.Player;
import UXUI.Hovereffect;
import UXUI.MainFrame;

public class InventoryPanel extends JPanel {

    private MainFrame parent;
    private JButton btnClose;
    private GamePanel realgamePanel;

    // --- สีและธีม ---
    private Color bgDarkBrown = new Color(80, 50, 30);
    private Color borderGold = new Color(255, 215, 0);
    private Color btnRed = new Color(255, 100, 100);

    public InventoryPanel(MainFrame parent, int screenWidth, int screenHeight) {
        this.parent = parent;
        this.realgamePanel = parent.getGamePanel();
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); // ให้พื้นหลังเป็น Overlay สีดำโปร่งแสง

        initComponents(screenWidth, screenHeight);
    }

    private void initComponents(int screenWidth, int screenHeight) {
        int boxWidth = 500;
        int boxHeight = 400;
        int x = (screenWidth - boxWidth) / 2;
        int y = (screenHeight - boxHeight) / 2;

        // --- 1. กรอบกระเป๋า (ใช้ JPanel ธรรมดาเพื่อความง่ายในการวาง Layout ทับซ้อน) ---
        JPanel bgBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // พื้นหลังสีน้ำตาล
                g2.setColor(bgDarkBrown);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                // ขอบสีทอง
                g2.setColor(borderGold);
                g2.setStroke(new BasicStroke(4));
                g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 30, 30);
            }
        };
        bgBox.setLayout(null);
        bgBox.setBounds(x, y, boxWidth, boxHeight);
        bgBox.setOpaque(false);
        add(bgBox);

        // --- 2. หัวข้อ "My Inventory" ---
        JLabel lblTitle = new JLabel("My Inventory", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 20, boxWidth, 40);
        bgBox.add(lblTitle);

        // --- 3. พื้นที่แสดงไอเทม (Scroll Pane) ---
        // ใช้ BoxLayout เรียงลงมาตามแนวตั้ง
        JPanel itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setOpaque(false); // ให้โปร่งใสเห็นสีน้ำตาล

        Player player = parent.getPlayer();
        Map<String, Integer> items = player.getInventory().getItemsMap();

        if (items.isEmpty()) {
            JLabel emptyLbl = new JLabel("Empty");
            emptyLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
            emptyLbl.setForeground(Color.LIGHT_GRAY);
            emptyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLbl.setBorder(new EmptyBorder(50, 0, 0, 0)); // ดันลงมานิดนึง
            itemsContainer.add(emptyLbl);
        } else {
            // วนลูปสร้างแถวสำหรับแต่ละไอเทม
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                String itemName = entry.getKey();
                int amount = entry.getValue();
                
                // ถ้ามีของถึงจะแสดง
                if(amount > 0){
                    itemsContainer.add(createItemRow(itemName, amount));
                    // เพิ่มช่องว่างระหว่างแถว
                    itemsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
        }

        // จับยัดลง JScrollPane
        JScrollPane scrollPane = new JScrollPane(itemsContainer);
        scrollPane.setBounds(30, 80, boxWidth - 60, 220); 
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        scrollPane.setBorder(null); 
        
        // ปรับแต่ง Scrollbar ให้ดูเรียบๆ
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getVerticalScrollBar().setOpaque(false);

        // ----> ปรับความเร็วในการเลื่อนลูกกลิ้งเมาส์ (ยิ่งเลขเยอะยิ่งเร็ว) <----
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); 
        
        // ปิดแนวนอน
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bgBox.add(scrollPane);

        // --- 4. ปุ่ม Close Bag ---
        int btnW = 200;
        int btnH = 40;
        // เปลี่ยนเป็นปุ่มมนแบบใน GamePanel
        btnClose = new JButton("Close Bag") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // มนเล็กน้อย
                super.paintComponent(g);
            }
        };
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        
        btnClose.setBounds((boxWidth - btnW) / 2, boxHeight - 70, btnW, btnH);
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 18));
        
        // ใช้ HoverEffect ธรรมดา หรือ Rounded ก็ได้
        Hovereffect.HoverEffectRounded(btnClose, (boxWidth - btnW) / 2, boxHeight - 70, btnW, btnH, btnRed);
        
        btnClose.addActionListener(e -> closePanel());
        bgBox.add(btnClose);
    }

    // --- ฟังก์ชันสร้าง 1 แถวของไอเทม (Icon | Name | Amount) ---
    private JPanel createItemRow(String itemName, int amount) {
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout());
        row.setOpaque(false); // โปร่งใส
        row.setMaximumSize(new Dimension(400, 60)); // บังคับขนาดสูงสุดของแถว
        row.setBorder(new EmptyBorder(5, 10, 5, 10)); // Padding ซ้ายขวา

        // 1. Icon (ซ้าย)
        JLabel lblIcon = new JLabel();
        String path = "image\\Flower\\"; // Path ตามที่คุณระบุ (รบกวนตรวจสอบอีกทีว่าตอนรันจริง มันมองเห็น Drive E ไหม แนะนำให้ย้ายเข้าโฟลเดอร์โปรเจค เช่น "image/Flower/")
        
        // แปลงชื่อให้ตรงกับไฟล์ (ลบเว้นวรรค)
        String fileName = itemName.replace(" ", "") + ".png"; 
        File imgFile = new File(path + fileName);
        
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imgFile.getPath());
            // ย่อรูปให้ขนาดพอดี
            Image img = icon.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH); 
            lblIcon.setIcon(new ImageIcon(img));
        } else {
            // ถ้าหารูปไม่เจอ ให้เว้นว่าง หรือใส่รูป default
            lblIcon.setPreferredSize(new Dimension(40, 60));
            lblIcon.setText("?"); // กันเหนียว
            lblIcon.setForeground(Color.WHITE);
        }
        row.add(lblIcon, BorderLayout.WEST);

        // 2. ชื่อ (ตรงกลาง ค่อนมาทางซ้าย)
        JLabel lblName = new JLabel("   " + itemName.toUpperCase()); // เว้นวรรคหน้านิดนึง
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblName.setForeground(Color.WHITE);
        row.add(lblName, BorderLayout.CENTER);

        // 3. จำนวน (ขวาสุด)
        JLabel lblAmount = new JLabel("X" + amount);
        lblAmount.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAmount.setForeground(Color.WHITE);
        lblAmount.setBorder(new EmptyBorder(0, 0, 0, 20)); // ถอยจากขอบขวานิดนึง
        row.add(lblAmount, BorderLayout.EAST);

        return row;
    }

    private void closePanel() {
        if(parent.getSFXManager() != null) {
            parent.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
        }
        this.setVisible(false);
        Container parentContainer = getParent();
        if (parentContainer != null) {
            parentContainer.remove(this); 
            parentContainer.revalidate(); 
            parentContainer.repaint();   
        }
        realgamePanel.enableAllGamePanel(); 
    }

    // --- วาด Overlay มืดๆ ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 150)); 
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    // --- คลาสตกแต่ง Scrollbar ---
    private class CustomScrollBarUI extends BasicScrollBarUI {
        private final Dimension d = new Dimension();

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return new JButton() {
                @Override
                public Dimension getPreferredSize() { return d; }
            };
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return new JButton() {
                @Override
                public Dimension getPreferredSize() { return d; }
            };
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // ปล่อยว่างไว้ให้พื้นหลังโปร่งใส หรือทาสีจางๆ
            // g.setColor(new Color(50, 30, 20));
            // g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // แถบเลื่อนสีขาวมนๆ
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 10, 10);
            g2.dispose();
        }
    }
}