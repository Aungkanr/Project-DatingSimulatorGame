package UXUI.Scene;
import UXUI.MainFrame;
import Utility.StdAuto;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private StdAuto stdScreen = new StdAuto() ;
    public HomePanel(MainFrame mainFrame) {
        
        stdScreen.setBtnWHG(200, 60, 20 ,0); //ขนาด ปุ่ม และ gap ,แถว
        
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Sleep!");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice1);
    }
}
