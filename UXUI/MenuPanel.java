package UXUI; 

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPanel extends JPanel {
    
    private MainFrame parent; 

    public MenuPanel(MainFrame mainFrame) {
        this.parent = mainFrame; 
    
        setBackground(new Color(173, 216, 230)); 
        setLayout(null);

        // กำหนดขนาดปุ่มและระยะห่าง
        int buttonWidth = 300;
        int buttonHeight = 60;
        int gap = 20; // ระยะห่างระหว่างแต่ละปุ่ม 

        int centerX = (parent.width - buttonWidth) / 2;
        int totalContentHeight = (buttonHeight * 4) + (gap * 3); 
        int currentY = (parent.height - totalContentHeight) / 2; // จุดเริ่ม Y

        // --- compoment ---

        // 1. Label (หัวข้อ)
        JLabel lblHome = new JLabel("หน้าเมนูหลัก");
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHome.setHorizontalAlignment(SwingConstants.CENTER);
        lblHome.setBounds(centerX, currentY, buttonWidth, buttonHeight);
        add(lblHome);

        // ขยับ Y ลงมาสำหรับปุ่มถัดไป
        currentY += buttonHeight + gap; 
        // 2. ปุ่ม START
        JButton btnStart = new JButton("START");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showGame(); 
            }
        });
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStart.setBounds(centerX, currentY, buttonWidth, buttonHeight);
        add(btnStart);
        
        // ขยับ Y ลงมา
        currentY += buttonHeight + gap;

        // 3. ปุ่ม SETTING
        JButton btnSetting = new JButton("SETTING");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showOption(); 
            }
        });
        btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSetting.setBounds(centerX, currentY, buttonWidth, buttonHeight);
        add(btnSetting);
        
        // ขยับ Y ลงมา
        currentY += buttonHeight + gap;

        //ปุ่ม EXIT
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnExit.setBounds(centerX, currentY, buttonWidth, buttonHeight);
        add(btnExit);
    }
}