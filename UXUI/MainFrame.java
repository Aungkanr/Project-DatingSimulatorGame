import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Frame;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    
    // ประกาศตัวแปรหน้าต่างๆ ไว้ตรงนี้
    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1540, 835);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- สร้างหน้าต่างๆ และส่ง 'this' (ตัวแม่) ไปให้ลูกๆ รู้จัก ---
        
        // 1. สร้างหน้า Menu
        menuPanel = new MenuPanel(this); 
        menuPanel.setBounds(0, 0, 1540, 835);
        contentPane.add(menuPanel); // โชว์หน้าเมนูก่อน
        
        // 2. สร้างหน้า Option (ซ่อนไว้ก่อน)
        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, 1540, 835);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        // 3. สร้างหน้า Game (ซ่อนไว้ก่อน)
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, 1540, 835);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);
    }

    // --- ฟังก์ชันสำหรับสลับหน้า (ให้ไฟล์อื่นเรียกใช้) ---
    
    public void showMenu() {
        menuPanel.setVisible(true);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
    }

    public void showOption() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(true);
        gamePanel.setVisible(false);
    }

    public void showGame() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(true);
    }
}
