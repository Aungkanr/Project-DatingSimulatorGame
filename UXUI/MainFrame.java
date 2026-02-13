package UXUI;
import Player.Player;
import UXUI.Scene.HomePanel;
import UXUI.Scene.OfficePanel;
import UXUI.Scene.SchoolPanel;
import UXUI.Scene.ShopPanel;
import java.awt.Dimension; 
import java.awt.Toolkit;   
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Frame;


public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Player player;
    
    // ประกาศตัวแปรหน้าต่างๆ ไว้ตรงนี้
    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;
    private SchoolPanel school;
    private ShopPanel shop;
    private HomePanel home;
    private OfficePanel office;
    //ดึงขนาดหน้าจอจริงของคอมพิวเตอร์มาใช้
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int width = (int) screenSize.getWidth();
    public int height = (int) screenSize.getHeight();

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
        player = new Player(); // ตอนนี้ energy=100, money=500
    
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, width, height);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        

        // --- สร้างหน้าต่างๆ และส่ง 'this' (ตัวแม่) ไปให้ลูกๆ รู้จัก ---
        
        // 1. สร้างหน้า Menu
        menuPanel = new MenuPanel(this); 
        menuPanel.setBounds(0, 0, width, height);
        contentPane.add(menuPanel); // โชว์หน้าเมนูก่อน
        
        // 2. สร้างหน้า Option (ซ่อนไว้ก่อน)
        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, width, height);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        // 3. สร้างหน้า Game (ซ่อนไว้ก่อน)
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, width, height);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);

    }

    public void createSchoolPanel() { // สร้าง object ของ schoolpanel
        school = new SchoolPanel(this);
        school.setBounds(0, 0, width, height);
        school.setVisible(false);
        add(school);
    }
    
    public void createShopPanel() { // สร้าง object ของ shoppanel
        shop = new ShopPanel(this);
        shop.setBounds(0, 0, width, height);
        shop.setVisible(false);
        add(shop);
    }

    public void createHomePanel() { // สร้าง object ของ homepanel
        home = new HomePanel(this);
        home.setBounds(0, 0, width, height);
        home.setVisible(false);
        add(home);
    }

    public void createOfficePanel() { // สร้าง object ของ officepanel
        office = new OfficePanel(this);
        office.setBounds(0, 0, width, height);
        office.setVisible(false);
        add(office);
    }
    
    // ฟังก์ชันสำหรับสลับหน้า 
    
    public void showMenu() {
        menuPanel.setVisible(true);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        school.setVisible(false);
        shop.setVisible(false);
        home.setVisible(false);
        office.setVisible(false);
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

        // ซ่อน scene school, shop, home, office ถ้ามันเปิดอยู่
        if (school != null && school.isVisible()) {
            school.setVisible(false);
        }
        if (shop != null && shop.isVisible()) {
            shop.setVisible(false);
        }
        if (home != null && home.isVisible()) {
            home.setVisible(false);
        }
        if (office != null && office.isVisible()) {
            office.setVisible(false);
        }
    }

    public void showSchool() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        school.setVisible(true);
    }

    public void showShop() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        shop.setVisible(true);
    }

    public void showHome() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        home.setVisible(true);
    }

    public void showOffice() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        office.setVisible(true);
    }

//--------getPlayer
    public Player getPlayer() {
        return player;
    }
}


