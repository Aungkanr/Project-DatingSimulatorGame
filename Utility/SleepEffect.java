package Utility;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SleepEffect extends JPanel {
    private float alpha = 0f;          
    private boolean isSleeping = false; 
    private javax.swing.Timer sleepTimer;          
    private int sleepState = 0;        

    public SleepEffect() {
        // สำคัญมาก! ต้องตั้งให้ Panel นี้โปร่งใส 
        // ไม่งั้นมันจะวาดพื้นหลังทึบ บังเกมหมด หรือไม่ก็ไม่แสดงผลเลย
        setOpaque(false); 
        
        // (Optional) ตั้งสีพื้นหลังเป็นใส
        setBackground(new Color(0, 0, 0, 0)); 
    }
    // ---------------------------------

    public void startSleepSequence() {
        if (isSleeping) return; 

        isSleeping = true;
        sleepState = 1; 
        alpha = 0f;

        sleepTimer = new javax.swing.Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // STATE 1: Fade Out
                if (sleepState == 1) {
                    alpha += 0.05f;
                    if (alpha >= 1.0f) {
                        alpha = 1.0f; // ล็อคไม่ให้เกิน 1
                        sleepState = 2; 
                        
                        // playSound("sound/sleep_effect.wav"); 
                        
                        ((javax.swing.Timer)e.getSource()).setDelay(2000); 
                    }
                } 
                
                // STATE 2: Sleep
                else if (sleepState == 2) {
                    sleepState = 3; 
                    ((javax.swing.Timer)e.getSource()).setDelay(50); 
                } 
                
                // STATE 3: Fade In
                else if (sleepState == 3) {
                    alpha -= 0.05f;
                    if (alpha <= 0f) {
                        alpha = 0f; // ล็อคไม่ให้ต่ำกว่า 0
                        isSleeping = false; 
                        sleepState = 0;
                        ((javax.swing.Timer)e.getSource()).stop(); 
                    }
                }

                repaint(); 
            }
        });

        sleepTimer.start(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        if (isSleeping) {
            Graphics2D g2 = (Graphics2D) g;
            
            // ป้องกัน Error กรณีค่า Alpha เพี้ยนเกิน 1.0 หรือต่ำกว่า 0.0
            if (alpha > 1.0f) alpha = 1.0f;
            if (alpha < 0.0f) alpha = 0.0f;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}