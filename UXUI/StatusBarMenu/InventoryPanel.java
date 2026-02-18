package UXUI.StatusBarMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Player.Player;
import UXUI.MainFrame;

public class InventoryPanel extends JPanel {

    private MainFrame parent;
    private JTextArea listArea;
    private JButton btnClose;
    private GamePanel realgamePanel;

    public InventoryPanel(MainFrame parent, int screenWidth, int screenHeight) {
        this.parent = parent;
        this.realgamePanel = parent.getGamePanel();
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); 

        initComponents(screenWidth, screenHeight);
    }

    private void initComponents(int screenWidth, int screenHeight) {
        // คำนวณตำแหน่งกึ่งกลาง
        int boxWidth = 500;
        int boxHeight = 400;
        int x = (screenWidth - boxWidth) / 2;
        int y = (screenHeight - boxHeight) / 2;

        // 1. ดึงข้อมูลของจาก Player
        Player player = parent.getPlayer();
        String myItems = player.getInventory().getAllItemsList(); 

        // 2. สร้างพื้นที่แสดงข้อความ (JTextArea)
        listArea = new JTextArea();
        listArea.setText(myItems);
        listArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
        listArea.setForeground(Color.WHITE);
        listArea.setBackground(new Color(0, 0, 0, 0)); 
        listArea.setOpaque(false);
        listArea.setEditable(false);
        listArea.setLineWrap(true);
        listArea.setWrapStyleWord(true);
        
        // ใส่ ScrollPane เผื่อของเยอะเกินกล่อง
        JScrollPane scrollPane = new JScrollPane(listArea);
        scrollPane.setBounds(x + 30, y + 60, boxWidth - 60, boxHeight - 120);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null); 
        add(scrollPane);

        // 3. ปุ่ม Close
        btnClose = new JButton("Close Bag");
        btnClose.setBounds(x + 150, y + boxHeight - 50, 200, 40);
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnClose.setBackground(new Color(255, 100, 100)); 
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closePanel(); // สั่งปิดตัวเอง
            }
        });
        add(btnClose);
    }

    
    private void closePanel() {
        this.setVisible(false);
        
        // เก็บตัวแปร Parent ไว้ก่อนสั่ง remove
        Container parentContainer = getParent();
        
        if (parentContainer != null) {
            parentContainer.remove(this); 
            parentContainer.revalidate(); 
            parentContainer.repaint();   
        }
        realgamePanel.enableAllGamePanel(); //เปิดคืนให้ทุกปุมกดได้ หลีงจาก disable
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. วาดพื้นหลังมืดจางๆ (Overlay)
        g2.setColor(new Color(0, 0, 0, 150)); 
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. วาดกล่อง Inventory (ตรงกลาง)
        int boxWidth = 500;
        int boxHeight = 400;
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2;

        // พื้นหลังกล่อง 
        g2.setColor(new Color(80, 50, 30)); 
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 30, 30);

        // ขอบกล่อง
        g2.setColor(new Color(255, 215, 0)); // สีทอง
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 30, 30);

        //"My Inventory"
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();
        String title = "My Inventory";
        int titleX = x + (boxWidth - fm.stringWidth(title)) / 2;
        g2.drawString(title, titleX, y + 40);
    }
}