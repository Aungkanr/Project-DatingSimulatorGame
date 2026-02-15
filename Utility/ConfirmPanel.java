package Utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfirmPanel extends JPanel {
    private StdAuto stdScreen ;
    private JLabel lblMessage;
    private JButton btnYes;
    private JButton btnNo;
    // ตัวแปรเก็บ "สิ่งที่ต้องทำ" เมื่อกด Yes
    private ActionListener onYesAction;

    public ConfirmPanel () {
        stdScreen = new StdAuto();
        // setting Panel หลัก
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        setBounds( (stdScreen.width - 400 ) / 2, (stdScreen.height - 200 )/ 2, 400, 200); // ขนาด default setting default
        setVisible(false); // ซ่อนไว้ก่อน

        // Message 
        lblMessage = new JLabel("Confirm?", SwingConstants.CENTER);
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMessage.setBounds(20, 20, 360, 40);
        add(lblMessage);

        // ปุ่ม Yes
        btnYes = new JButton("Yes");
        btnYes.setBounds(50, 100, 100, 40);
        btnYes.addActionListener(e -> {
            setVisible(false); // ปิดหน้าต่าง
            if (onYesAction != null) {
                onYesAction.actionPerformed(e); // *** ทำคำสั่งที่ฝากไว้ ***
            }
        });
        add(btnYes);

        // ปุ่ม No
        btnNo = new JButton("No");
        btnNo.setBounds(250, 100, 100, 40);
        btnNo.addActionListener(e -> {
            setVisible(false); // ปิดหน้าต่างเฉยๆ ไม่ทำอะไร
        });
        add(btnNo);
    }
    
    public void showConfirm(String message, ActionListener action) {
        lblMessage.setText(message);
        this.onYesAction = action; // เก็บคำสั่งไว้ก่อน รอคนกดปุ่ม
        setVisible(true);
    }
}
