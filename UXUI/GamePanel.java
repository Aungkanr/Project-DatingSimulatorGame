import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MainFrame parent;

    public GamePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        
        JButton btnExitGame = new JButton("ออกเกม");
        btnExitGame.setBounds(20, 20, 100, 30);
        btnExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMenu(); // กลับไปหน้าเมนู
            }
        });
        add(btnExitGame);

        JLabel lblMap = new JLabel("");
        lblMap.setIcon(new ImageIcon("C:\\Users\\LOQ\\git\\Project-DatingSimulatorGame\\UXUI\\Map.png")); 
        lblMap.setBounds(0, 0, 1540, 835);
        add(lblMap);
    }
}