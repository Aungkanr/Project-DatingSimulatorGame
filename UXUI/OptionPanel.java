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
        
        int buttonWidth = 300;
        int buttonHeight = 60;
        int gap = 20; // ระยะห่างระหว่างแต่ละปุ่ม 

        int centerX = (parent.width - buttonWidth) / 2;
        int totalContentHeight = (buttonHeight * 4) + (gap * 3); 
        int currentY = (parent.height - totalContentHeight) / 2; // จุดเริ่ม Y

        setBackground(new Color(0, 51, 204)); // สีน้ำเงิน
        setLayout(null);

        JCheckBox chckbxMute = new JCheckBox("Mute Music");
        chckbxMute.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxMute.setFont(new Font("Tahoma", Font.PLAIN, 15));
        chckbxMute.setBounds(centerX, currentY, buttonWidth, buttonHeight+3);
        add(chckbxMute);

        currentY += buttonHeight + gap; //เว้น auto

        JButton btnBack = new JButton("กลับเมนูหลัก");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu(); // กลับไปหน้าเมนู
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBounds(centerX, currentY, buttonWidth, buttonHeight-20);
        add(btnBack);
    }
}