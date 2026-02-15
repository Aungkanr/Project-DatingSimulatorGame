package UXUI.StatusBarMenu;

import java.awt.BasicStroke;//เพิ่มเข้ามา
import java.awt.Color;
import java.awt.Graphics;//เพิ่มเข้ามา
import java.awt.Graphics2D;//เพิ่มเข้ามา
import java.awt.RenderingHints;//เพิ่มเข้ามา
import javax.swing.JPanel;



public class RoundedPanel extends JPanel{
    private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // วาดพื้นหลัง
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // วาดขอบขาว
            graphics.setColor(Color.WHITE);
            graphics.setStroke(new BasicStroke(4)); 
            graphics.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, cornerRadius, cornerRadius);
        }
    
}
