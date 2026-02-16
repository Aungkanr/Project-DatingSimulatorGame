package Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StatusBar extends JPanel {
    private int currentEnergy;
    private int maxEnergy;
    private int targetEnergy;
    private Timer animationTimer;
    private String label; // เพิ่มตัวแปร label
    
    private static final Color ENERGY_HIGH = new Color(52, 199, 89);
    private static final Color ENERGY_MEDIUM = new Color(255, 204, 0);
    private static final Color ENERGY_LOW = new Color(255, 69, 58);
    private static final Color ENERGY_BG = new Color(60, 60, 60, 120);
    
    // เพิ่ม parameter label
    public StatusBar(int maxEnergy, String label) {
    this.maxEnergy = maxEnergy;
    this.currentEnergy = 0; // เริ่มที่ 0 แทน maxEnergy
    this.targetEnergy = 0;  // เริ่มที่ 0 แทน maxEnergy
    this.label = label;
    setOpaque(false);
    
    // สร้าง animation timer
    animationTimer = new Timer(10, e -> {
        if (currentEnergy < targetEnergy) {
            currentEnergy = Math.min(currentEnergy + 2, targetEnergy);
            repaint();
        } else if (currentEnergy > targetEnergy) {
            currentEnergy = Math.max(currentEnergy - 2, targetEnergy);
            repaint();
        } else {
            animationTimer.stop();
        }
        });
    }      
    
    public void setEnergy(int energy) {
        this.targetEnergy = Math.max(0, Math.min(energy, maxEnergy));
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int radius = 8;
        
        g2d.setColor(ENERGY_BG);
        g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, radius, radius));
        
        float percent = (float) currentEnergy / maxEnergy;
        int barWidth = (int) ((width - 4) * percent);
        
        if (barWidth > 0) {
            g2d.setColor(getEnergyColor(percent));
            g2d.fill(new RoundRectangle2D.Float(2, 2, barWidth, height - 4, radius - 2, radius - 2));
        }
        
        g2d.setFont(new Font("Tahoma", Font.BOLD, 11));
        String text = label + currentEnergy + "/" + maxEnergy; // ใช้ label
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height + fm.getAscent() - fm.getDescent()) / 2;
        
        // วาดขอบดำรอบตัวอักษร
        g2d.setColor(Color.BLACK);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2d.drawString(text, x + dx, y + dy);
                }
            }
        }
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        
        g2d.dispose();
    }
    
    private Color getEnergyColor(float percent) {
        if (percent > 0.5f) return ENERGY_HIGH;
        else if (percent > 0.25f) return ENERGY_MEDIUM;
        else return ENERGY_LOW;
    }
}