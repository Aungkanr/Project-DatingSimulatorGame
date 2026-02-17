package UXUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Utility.StdAuto;

public class OptionPanel extends JPanel {

    private MainFrame parent;
    private StdAuto stdScreen = new StdAuto(); //Device screen
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();  

    // ------------------ สึปุ่ม ---------------------
    Color btnBackColor = new Color(138, 43, 226);        
    Color checkBoxBgColor = new Color(240, 240, 250);    
    
    public OptionPanel(MainFrame mainFrame) { // -------- construct--------
        this.parent = mainFrame;
        
        setBackground(new Color(0, 51, 204)); 
        setLayout(null);
        stdScreen.setBtnWHG(300, 60, 20 ,2); //ขนาด ปุ่ม และ gap , แถว

        // --- CheckBox Mute ---
        JCheckBox chckbxMute = new JCheckBox("Mute Music");
        chckbxMute.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxMute.setFont(new Font("Tahoma", Font.BOLD, 16));  
        chckbxMute.setForeground(new Color(60, 40, 80));
        chckbxMute.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(138, 43, 226), 2));  // ⬅️ เพิ่มบรรทัดนี้
        chckbxMute.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight+3);
        chckbxMute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.toggleMute(chckbxMute.isSelected()); // true / false (checkbox)
            }
        });
        add(chckbxMute);


        // --- CheckBox Mute ---
        JCheckBox chckbxMuteSFX = new JCheckBox("Mute SFX");
        chckbxMuteSFX.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxMuteSFX.setFont(new Font("Tahoma", Font.BOLD, 16));  
        chckbxMuteSFX.setForeground(new Color(60, 40, 80));
        chckbxMuteSFX.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(50, 43, 226), 2));  // ⬅️ เพิ่มบรรทัดนี้
        chckbxMuteSFX.setBounds(stdScreen.centerX, stdScreen.currentY-80, stdScreen.buttonWidth, stdScreen.buttonHeight+3);
        
        // ดึงค่าเริ่มต้น
        if (parent.getSFXManager() != null) {
            chckbxMuteSFX.setSelected(parent.getSFXManager().isMuted());
        }
        
        // ใส่ Action
        chckbxMuteSFX.addItemListener(e -> {
            boolean isMuted = (e.getStateChange() == java.awt.event.ItemEvent.SELECTED);
            parent.toggleSFX(isMuted); // เรียกฟังก์ชัน toggleSFX ใน MainFrame
        });
        add(chckbxMuteSFX);


        stdScreen.currentY += stdScreen.buttonHeight + stdScreen.gap;

        // --- Back Button ---
        JButton btnBack = new JButton("กลับเมนูหลัก");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));  
        btnBack.setBackground(btnBackColor);  
        btnBack.setForeground(Color.WHITE);  
        btnBack.setFocusPainted(false);  
        btnBack.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(106, 13, 173), 3));  // ⬅️ เพิ่มบรรทัดนี้
        btnBack.setBounds(stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight-20);
        Hovereffect.HoverEffect(btnBack, stdScreen.centerX, stdScreen.currentY, stdScreen.buttonWidth, stdScreen.buttonHeight-20, btnBackColor);  // ⬅️ เพิ่ม
        add(btnBack);
        
        // Background 
        JLabel lblMap = new JLabel("");
        String imagePath = "image\\OptionBackGround.png";
        ImageIcon originalIcon = Utility.AssetManager.getInstance().getImage(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, stdScreen.width, stdScreen.height);

        lblMap.setBounds(0, 0, stdScreen.width, stdScreen.height);
        add(lblMap);

    }
}