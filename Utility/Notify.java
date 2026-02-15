package Utility;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notify extends JLabel {
    
    private Timer notifyTimer;
    // Constructor 
    public Notify(int screenWidth) {
        // ตั้งค่า label Default
        setHorizontalAlignment(SwingConstants.CENTER); // text Center 
        setFont(new Font("Tahoma", Font.BOLD, 24));   
        setBounds(0, 200, screenWidth, 50); 
        setVisible(false); // hide ไว้ก่อน
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

        notifyTimer = new Timer(ms  , new ActionListener() {
            @Override //ยืมจาก GmaePanel
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // hide เมื่อครบเวลา
            }
        });
        notifyTimer.setRepeats(false);
        notifyTimer.start();
    }
}