
import Player.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon; 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private MainFrame parent;
    private JLabel lblEnergy;
    private JLabel lblMoney;
    Utility.CheckImage checkImageUtil = new Utility.CheckImage();

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;

        setBackground(Color.DARK_GRAY);
        setLayout(null);

// --------------- show status player ----------------
        lblEnergy = new JLabel("Energy: 0");
        lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblEnergy.setForeground(Color.WHITE);
        lblEnergy.setBounds(20, 60, 300, 30);
        add(lblEnergy);

        lblMoney = new JLabel("Money: 0");
        lblMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMoney.setForeground(Color.YELLOW);
        lblMoney.setBounds(20, 90, 300, 30);
        add(lblMoney);

        JButton btnExitGame = new JButton("Return to Menu");
        btnExitGame.setBounds(20, 20, 150, 30);
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu();
            }
        });
        add(btnExitGame);

//------------------- ส่วนของปุ่มที่เเสดงบนเเมพ--------------
        JButton btnSchool = new JButton("School");
        btnSchool.setBounds(680, 180, 100, 30);
        add(btnSchool);
        JButton btnHome = new JButton("Home");
        btnHome.setBounds(720, 520, 100, 30);
        add(btnHome);
        JButton btnShop = new JButton("Shop");
        btnShop.setBounds(420, 360, 100, 30);
        add(btnShop);
        JButton btnOffice = new JButton("Office ");
        btnOffice.setBounds(980, 340, 100, 30);
        add(btnOffice);
//--------------------------image------------------
        JLabel lblMap = new JLabel("");

        String imagePath = "DatingSimulatorGame\\image\\Map.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, parent.width, parent.height);

        lblMap.setBounds(0, 0, parent.width, parent.height);
        add(lblMap);
    }
//--------------------------image------------------
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