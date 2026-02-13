package UXUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Utility.StdAuto;

public class OptionPanel extends JPanel {

    private MainFrame parent;
    private StdAuto stdScreen = new StdAuto(); //Device screen
    
    public OptionPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(new Color(0, 51, 204)); 
        setLayout(null);

        // --- CheckBox Mute ---
        JCheckBox chckbxMute = new JCheckBox("Mute Music");
        chckbxMute.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxMute.setFont(new Font("Tahoma", Font.PLAIN, 15));
        chckbxMute.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight+3);
        
        chckbxMute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.toggleMute(chckbxMute.isSelected()); // true / false (checkbox)
            }
        });
        add(chckbxMute);

        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;

        // --- Back Button ---
        JButton btnBack = new JButton("กลับเมนูหลัก");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight-20);
        add(btnBack);
    }
}