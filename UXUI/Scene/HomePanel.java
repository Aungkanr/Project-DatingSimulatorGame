package UXUI.Scene;
import UXUI.MainFrame; 
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    public HomePanel(MainFrame mainFrame) {
        int buttonWidth = 150;
        int buttonHeight = 50;
        int gap = 20;

        int centerX = (mainFrame.width - buttonWidth) / 2;
        int totalContentHeight = (buttonHeight * 4) + (gap * 3); 
        int currentY = (mainFrame.height - totalContentHeight) / 2;
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Sleep!");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(centerX, currentY, buttonWidth, buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice1);
    }
}
