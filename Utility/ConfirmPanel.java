package Utility;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConfirmPanel extends JPanel {

    // ตัวแปรเก็บค่าข้อความ (เปลี่ยนค่าได้ตลอด)
    private String title = "";
    private String[] messageLines = {};
    
    // ปุ่ม
    private JButton btnConfirm;
    private JButton btnCancel;
    
    // ตัวเก็บคำสั่ง (Callback) ที่จะเปลี่ยนไปตามสถานการณ์
    private ActionListener onConfirmAction;

    public ConfirmPanel(int screenWidth, int screenHeight) {
        setLayout(null);
        setBounds(0, 0, screenWidth, screenHeight);
        setOpaque(false); // พื้นหลังโปร่งใส
        setVisible(false); // ซ่อนไว้ก่อนเสมอตอนสร้าง

        initComponents(screenWidth, screenHeight);
    }

    private void initComponents(int screenWidth, int screenHeight) {
        int boxWidth = 400;
        int boxHeight = 250;
        int centerX = (screenWidth - boxWidth) / 2;
        int centerY = (screenHeight - boxHeight) / 2;

        // --- ปุ่มยืนยัน (Confirm) ---
        btnConfirm = new JButton("ตกลง");
        btnConfirm.setBounds(centerX + 50, centerY + 160, 130, 40);
        btnConfirm.setBackground(new Color(100, 200, 100)); // สีเขียว
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnConfirm.setFocusPainted(false);
        
        // เมื่อกดปุ่มนี้ ให้ไปเรียกคำสั่งที่เก็บไว้ใน onConfirmAction
        btnConfirm.addActionListener(e -> {
            setVisible(false); // ปิดหน้าต่างก่อน
            if (onConfirmAction != null) {
                onConfirmAction.actionPerformed(e); // รันคำสั่งที่ฝากไว้
            }
        });
        add(btnConfirm);

        // --- ปุ่มยกเลิก (Cancel) ---
        btnCancel = new JButton("NO");
        btnCancel.setBounds(centerX + 220, centerY + 160, 130, 40);
        btnCancel.setBackground(new Color(200, 100, 100)); // สีแดง
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        
        // ปุ่มยกเลิก มีหน้าที่แค่ปิดหน้าต่าง
        btnCancel.addActionListener(e -> setVisible(false));
        add(btnCancel);
    }

    
    /**
     * เรียกใช้เมธอดนี้เมื่อต้องการให้กล่องเด้งขึ้นมา
     * @param titleText - หัวข้อ (เช่น "ยืนยันการซื้อ")
     * @param message - ข้อความบรรยาย (ใส่เป็น array เพื่อขึ้นบรรทัดใหม่)
     * @param confirmBtnText - ข้อความบนปุ่มยืนยัน (เช่น "ซื้อเลย", "นอนหลับ")
     * @param action - คำสั่งที่จะให้ทำเมื่อกดยืนยัน (Lambda Expression)
     */
    public void show(String titleText, String[] message, String confirmBtnText, ActionListener action) {
        this.title = titleText;
        this.messageLines = message;
        this.onConfirmAction = action; // ฝากคำสั่งไว้
        
        // อัปเดตข้อความบนปุ่ม
        this.btnConfirm.setText(confirmBtnText);
        
        // สั่งวาดหน้าจอใหม่เพื่อให้ข้อความเปลี่ยน
        repaint();
        
        // เปิดหน้าต่าง
        setVisible(true);
    }

    // ========================================================
    // ส่วนวาดกราฟิก (เหมือนเดิม)
    // ========================================================
    @Override
    protected void paintComponent(Graphics g) {
        if (!isVisible()) return; // ถ้าซ่อนอยู่ไม่ต้องวาด

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. พื้นหลังมืด (Dim)
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // คำนวณตำแหน่ง
        int boxWidth = 400;
        int boxHeight = 250;
        int x = (getWidth() - boxWidth) / 2;
        int y = (getHeight() - boxHeight) / 2;

        // 2. ตัวกล่อง
        g2.setColor(new Color(40, 40, 40));
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        // 3. วาดหัวข้อ (Title) ที่รับค่ามาใหม่
        g2.setColor(new Color(255, 100, 100));
        g2.setFont(new Font("Tahoma", Font.BOLD, 24));
        drawCenteredString(g2, title, x + boxWidth/2, y + 50);

        // 4. วาดเนื้อหา (Message) ที่รับค่ามาใหม่
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        int textY = y + 90;
        if (messageLines != null) {
            for (String line : messageLines) {
                drawCenteredString(g2, line, x + boxWidth/2, textY);
                textY += 30;
            }
        }
    }

    private void drawCenteredString(Graphics g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int drawX = x - (metrics.stringWidth(text) / 2);
        g.drawString(text, drawX, y);
    }
}