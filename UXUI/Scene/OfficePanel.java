package UXUI.Scene;
import UXUI.GamePanel;
import UXUI.MainFrame;
import Utility.StdAuto;

import javax.swing.JButton;
import javax.swing.JPanel;

import Player.Player;
import Utility.GameTime;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OfficePanel extends JPanel {
    private StdAuto stdScreen ;
    
    public OfficePanel(MainFrame mainFrame ) {
        stdScreen = new StdAuto() ;
        GamePanel realGamePanel = mainFrame.getGamePanel(); // ---update UI and doActivity
        Player realPlayer = mainFrame.getPlayer();
        GameTime gameTime = mainFrame.getGameTime(); 

        stdScreen.setBtnWHG(200, 60, 20, 0); //ขนาด ปุ่ม และ gap ,แถว
        setLayout(null);
        setBackground(new Color(12, 51, 204));

        JButton btnchoice1 = new JButton("Give Job Application");
        btnchoice1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice1.setBounds(stdScreen.centerX-200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
                if (gameTime.getTimeSlot() < 3) {
                    realPlayer.increaseMoney(80);
                }
                realGamePanel.doActivity(40);
            }
        });
        add(btnchoice1);

        JButton btnchoice2 = new JButton("I need to work here man!");
        btnchoice2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnchoice2.setBounds(stdScreen.centerX+200, stdScreen.currentY+400, stdScreen.buttonWidth, stdScreen.buttonHeight);
        btnchoice2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.showGame();
            }
        });
        add(btnchoice2);   
    }
}