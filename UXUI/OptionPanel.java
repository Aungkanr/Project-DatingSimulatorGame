package UXUI; 

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OptionPanel extends JPanel {

    private MainFrame parent;

    public OptionPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(new Color(0, 51, 204)); // สีน้ำเงิน
        setLayout(null);

        JCheckBox chckbxMute = new JCheckBox("Mute Music");
        chckbxMute.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxMute.setFont(new Font("Tahoma", Font.PLAIN, 15));
        chckbxMute.setBounds(640, 257, 200, 63);
        add(chckbxMute);

        JButton btnBack = new JButton("กลับเมนูหลัก");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu(); // กลับไปหน้าเมนู
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBounds(640, 377, 200, 40);
        add(btnBack);
    }
}