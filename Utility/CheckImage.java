package Utility;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;


public class CheckImage {
    // method เช็คภาพ
    public void checkImage(ImageIcon originalIcon, JLabel lblMap, int w, int h) {
         if (originalIcon.getIconWidth() > 0) { 
            Image img = originalIcon.getImage();
            Image newImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lblMap.setIcon(new ImageIcon(newImg));
        } else {
            lblMap.setText("Image not found!!!!!!!!");
            lblMap.setFont(new Font("Tahoma", Font.BOLD, 100));
            lblMap.setForeground(Color.RED);
            System.out.println("หาไฟล์รูปไม่เจอ หรือ Path ผิด");
        }
    }
}