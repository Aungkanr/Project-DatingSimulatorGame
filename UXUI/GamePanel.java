package UXUI;

import Player.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon; // เช็คว่า import ครบไหม
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MainFrame parent;
    private JLabel lblEnergy;
    private JLabel lblMoney;

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        
        // --- ส่วนแสดงสถานะ (HUD) ---
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 60, 200, 30);
        add(lblEnergy);

        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMoney.setForeground(Color.YELLOW);
        lblMoney.setBounds(20, 90, 200, 30);
        add(lblMoney);

        JButton btnExitGame = new JButton("Return to Menu");
        btnExitGame.setBounds(20, 20, 150, 30);
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        add(btnExitGame);

        JLabel lblMap = new JLabel("");

        String imagePath = "DatingSimulatorGame/image/Map.png"; 
        ImageIcon icon = new ImageIcon(imagePath);
        
        lblMap.setBounds(0, 0, 1540, 835);
        add(lblMap);
    }
    //update - ค่า
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            Player p = parent.getPlayer();
            lblEnergy.setText("Energy: " + p.getEnergy());
            lblMoney.setText("Money: " + p.getMoney());
        }
    }
}