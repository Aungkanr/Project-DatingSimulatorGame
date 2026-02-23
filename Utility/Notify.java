package Utility;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.*;

public class Notify extends JLabel {
    
    private Timer notifyTimer;
    private Color bgColor = new Color(0, 0, 0, 180); //background โปร่งแสง
    // Constructor 
    public Notify(int screenWidth) {
        // ตั้งค่า label Default
        setHorizontalAlignment(SwingConstants.CENTER); // text Center 
        setFont(new Font("Tahoma", Font.BOLD, 24));   
        setBounds(0, 100, screenWidth, 60); 
        setVisible(false);
        setOpaque(false);//จะวาดพื้นหลังเองให้มน
    }
//--------------functiom --------------------
    //show message 
    public void showNotify(String message, Color color , int ms ) {
        setText(message);      
        setForeground(color);  
        setVisible(true);  //hide -> show

        // ดึงมาหน้าสุด (Front layer)  
        if (getParent() != null) {
            getParent().setComponentZOrder(this, 0);
        }

        // --- Logic Timer ---
        if (notifyTimer != null && notifyTimer.isRunning()) {
            notifyTimer.stop();
        }

        notifyTimer = new Timer(ms, e -> setVisible(false));
        notifyTimer.setRepeats(false);
        notifyTimer.start();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        // วาดพื้นหลังก่อนตัวอักษร
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // คำนวณขนาดกล่องพื้นหลังให้พอดีกับข้อความ (หรือจะทำเป็นแถบยาวเต็มจอก็ได้)
        int margin = 20;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int boxWidth = textWidth + (margin * 2);
        int boxHeight = getHeight() - 10;
        int x = (getWidth() - boxWidth) / 2;
        int y = 5;

        // วาดสี่เหลี่ยมมุมมน
        g2.setColor(bgColor);
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        
        // วาดเส้นขอบสีขาวบางๆ ให้ดูมีมิติเหมือน UI ส่วนอื่น
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);

        g2.dispose();
        
        // เรียก super เพื่อวาดตัวอักษรทับลงไป
        super.paintComponent(g);
    }
}