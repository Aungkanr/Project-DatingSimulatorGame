package UXUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Hovereffect {
    
    // แบบเดิม (สี่เหลี่ยม) - เก็บไว้ใช้กับปุ่มอื่น
    public static void HoverEffect(JButton button, int x, int y, int w, int h, Color baseColor) {
        Color hoverColor = baseColor.brighter();
        Color borderColor = new Color(255, 215, 0); // สีทอง

        button.setBounds(x, y, w, h);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 3)); 
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                int extra = 10;
                button.setBounds(x - (extra / 2), y - (extra / 2), w + extra, h + extra);
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createLineBorder(borderColor, 3)); 
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBounds(x, y, w, h);
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createLineBorder(borderColor, 1));
            }
        });
    }

    // --- [เพิ่มใหม่] สำหรับปุ่มมน (Rounded Button) ---
    public static void HoverEffectRounded(JButton button, int x, int y, int w, int h, Color baseColor) {
        Color hoverColor = baseColor.brighter();
        
        button.setBounds(x, y, w, h);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        
        // ปิดการวาดพื้นหลังสี่เหลี่ยมแบบเดิม
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                int extra = 10;
                // ขยายปุ่มตอนเอาเมาส์ชี้
                button.setBounds(x - (extra / 2), y - (extra / 2), w + extra, h + extra);
                button.setBackground(hoverColor);
                button.repaint(); // สั่งวาดใหม่เพื่อให้สีเปลี่ยน
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                // คืนค่าเดิม
                button.setBounds(x, y, w, h);
                button.setBackground(baseColor);
                button.repaint(); // สั่งวาดใหม่
            }
        });
    }
}