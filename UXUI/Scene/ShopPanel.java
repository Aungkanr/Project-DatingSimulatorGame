package UXUI.Scene;
import javax.swing.JButton;
import javax.swing.JPanel;
import UXUI.MainFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopPanel extends JPanel {
    public ShopPanel(MainFrame mainFrame) {
        int buttonWidth = 250;
        int buttonHeight = 60;
        int gap = 20;

        int centerX = (mainFrame.width - buttonWidth) / 2;
        int totalContentHeight = (buttonHeight * 4) + (gap * 3); 
        int currentY = (mainFrame.height - totalContentHeight) / 2;
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Hello i want to buy baby oil");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(centerX+200, currentY+400, buttonWidth, buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice1);

        JButton btnchoice2 = new JButton("give me money man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(centerX-200, currentY+400, buttonWidth, buttonHeight);
        btnchoice2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice2);   
    }
}
