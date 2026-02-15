package UXUI;
import Player.Player;
import UXUI.Scene.HomePanel;
import UXUI.Scene.OfficePanel;
import UXUI.Scene.SchoolPanel;
import UXUI.Scene.ShopPanel;
import UXUI.StatusBarMenu.GamePanel;
import Utility.StdAuto;
import java.io.File;
import java.awt.EventQueue;
//import java.awt.Frame;
//import java.awt.Rectangle;//add

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip; // แก้เป็น Clip
import javax.sound.sampled.FloatControl; // เพิ่ม
import javax.swing.JFrame;
import javax.swing.JPanel;
import Utility.GameTime;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Player player;
    private GameTime gameTime;

    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;
    private SchoolPanel school;
    private ShopPanel shop;
    private HomePanel home;
    private OfficePanel office;
    
    private StdAuto stdScreen; 
    
    private Clip clip; 
    public static String filePath = "Music\\Harvest Dawn.wav";  
    public static File file = new File(filePath);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public MainFrame() { 
        // 1. โหลดค่ามาตรฐาน
        stdScreen = new StdAuto();
        
        // 2. ตั้งค่าหน้าต่าง
        setTitle("Dating Simulator Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // [สำคัญ] ใช้ขนาดจาก StdAuto (1280x720)
        setSize(stdScreen.width, stdScreen.height);
        
        // จัดกลางจอ + ห้ามย่อขยาย
        setLocationRelativeTo(null); 
        setResizable(false);

        player = new Player(); 
        gameTime = new GameTime();
        
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        // [สำคัญ] บังคับ contentPane ให้เต็มจอ
        contentPane.setBounds(0, 0, stdScreen.width, stdScreen.height);
        setContentPane(contentPane);
        
        // --- สร้าง Panel ลูกๆ โดยส่งขนาดที่ถูกต้องไป ---
        menuPanel = new MenuPanel(this); 
        menuPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        contentPane.add(menuPanel);
        
        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);
    }

    // --- ส่วนสร้าง Scene ต่างๆ (แก้ให้ใช้ stdScreen.width/height) ---
    public void createSchoolPanel() { 
        school = new SchoolPanel(this);
        school.setBounds(0, 0, stdScreen.width, stdScreen.height);
        school.setVisible(false);
        add(school);
    }
    
    public void createShopPanel() { 
        shop = new ShopPanel(this);
        shop.setBounds(0, 0, stdScreen.width, stdScreen.height);
        shop.setVisible(false);
        add(shop);
    }

    public void createHomePanel() { 
        home = new HomePanel(this);
        home.setBounds(0, 0, stdScreen.width, stdScreen.height);
        home.setVisible(false);
        add(home);
    }

    public void createOfficePanel() { 
        office = new OfficePanel(this);
        office.setBounds(0, 0, stdScreen.width, stdScreen.height);
        office.setVisible(false);
        add(office);
    }
    
    // ... (Code ฟังก์ชัน toggleMute, showGame ฯลฯ เหมือนเดิม ไม่ต้องแก้) ...
    public void toggleMute(boolean isMute) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (isMute) {
                gainControl.setValue(gainControl.getMinimum());
            } else {
                gainControl.setValue(0.0f); 
            }
        }
    }
    
    public Clip getClip() { return clip; }
    public void showMenu() { toggleVisibility(menuPanel); }
    public void showOption() { toggleVisibility(optionPanel); }
    public void showGame() { toggleVisibility(gamePanel); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showSchool() { toggleVisibility(school); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showShop() { toggleVisibility(shop); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showHome() { toggleVisibility(home); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showOffice() { toggleVisibility(office); if(gamePanel!=null) gamePanel.updateUI(); }

    // Helper function เพื่อปิด panel อื่นๆ อัตโนมัติ
    private void toggleVisibility(JPanel showPanel) {
        if(menuPanel != null) menuPanel.setVisible(false);
        if(optionPanel != null) optionPanel.setVisible(false);
        if(gamePanel != null) gamePanel.setVisible(false);
        if(school != null) school.setVisible(false);
        if(shop != null) shop.setVisible(false);
        if(home != null) home.setVisible(false);
        if(office != null) office.setVisible(false);
        
        if(showPanel != null) showPanel.setVisible(true);
    }

    public Player getPlayer() { return this.player; }
    public GameTime getGameTime() { return this.gameTime; }
    public GamePanel getGamePanel() { return this.gamePanel; }
}

/*
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Player player;
    private GameTime gameTime ;

    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;
    private SchoolPanel school;
    private ShopPanel shop;
    private HomePanel home;
    private OfficePanel office;
    //ดึงขนาดหน้าจอจริงของคอมพิวเตอร์มาใช้
    private StdAuto stdScreen ; //Device screen
    
    private Clip clip; //sound
    public static String filePath = "Music\\Harvest Dawn.wav";  
    public static File file = new File(filePath);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public MainFrame() { //-------------construct --------------
        stdScreen = new StdAuto() ;
        setSize(stdScreen.width, stdScreen.height); // ตั้งขนาดเฟรมให้เท่ากับค่ามาตรฐานที่เราเพิ่งแก้

        // เพิ่มบรรทัดนี้เพื่อให้หน้าต่างเด้งมา "ตรงกลางจอ" อัตโนมัติ
        setLocationRelativeTo(null); 
        
        // ล็อคไม่ให้ย่อขยาย (สำคัญมากสำหรับเกมแบบ Fixed Size)
        setResizable(false);

        player = new Player() ; 
        gameTime = new GameTime() ;
        //---load sound 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setSize(1024, 768);       // กำหนดขนาดตายตัว
        setLocationRelativeTo(null);      // ให้อยู่กลางจอเสมอ
        setResizable(false); // <--- ห้ามย่อขยายหน้าต่าง
        stdScreen.setBtnWHG(300, 60, 20 ,4); //ขนาด ปุ่ม และ gap ,แถว
        setTitle("Dating Simulator Game"); // เพิ่ม title

        //setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, stdScreen.width, stdScreen.height);
        setLayout(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        // บังคับให้ ContentPane ขนาดเท่ากับหน้าต่างเป๊ะๆ
        contentPane.setBounds(0, 0, stdScreen.width, stdScreen.height);
        setContentPane(contentPane);
        
        // ส่ง 'this' ไปให้ลูกๆ
        menuPanel = new MenuPanel(this); 
        menuPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        contentPane.add(menuPanel);
        
        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);
    //-----------------end construct -----------------
    }

    public void createSchoolPanel() { // สร้าง object ของ schoolpanel
        school = new SchoolPanel(this);
        school.setBounds(0, 0, stdScreen.width, stdScreen.height);
        school.setVisible(false);
        add(school);
    }
    
    public void createShopPanel() { // สร้าง object ของ shoppanel
        shop = new ShopPanel(this);
        shop.setBounds(0, 0, stdScreen.width, stdScreen.height);
        shop.setVisible(false);
        add(shop);
    }

    public void createHomePanel() { // สร้าง object ของ homepanel
        home = new HomePanel(this);
        home.setBounds(0, 0, stdScreen.width, stdScreen.height);
        home.setVisible(false);
        add(home);
    }

    public void createOfficePanel() { // สร้าง object ของ officepanel
        office = new OfficePanel(this);
        office.setBounds(0, 0, stdScreen.width, stdScreen.height);
        office.setVisible(false);
        add(office);
    }
    
    // ฟังก์ชันสำหรับสลับหน้า 
    // --- ฟังก์ชันจัดการเสียง (ให้ลูกๆ เรียกใช้) ---
    public void toggleMute(boolean isMute) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (isMute) {
                gainControl.setValue(gainControl.getMinimum());
            } else {
                // เปิดเสียง Volume ปกติ 0.0
                gainControl.setValue(0.0f); 
            }
        }
    }
    
    public Clip getClip() {
        return clip;
    }

    // --- funtion สลับ page  ---
    public void showMenu() {
        menuPanel.setVisible(true);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        
        // เช็คว่า != null ก่อนเรียกใช้
        if (school != null) school.setVisible(false);
        if (shop != null) shop.setVisible(false);
        if (home != null) home.setVisible(false);
        if (office != null) office.setVisible(false);
    }

    public void showOption() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(true);
        gamePanel.setVisible(false);
        gamePanel.updateUI();

        if (school != null) school.setVisible(false);
        if (shop != null) shop.setVisible(false);
        if (home != null) home.setVisible(false);
        if (office != null) office.setVisible(false);
    }

    public void showGame() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(true);
        gamePanel.updateUI();
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
        gamePanel.updateUI();
    }

    public void showShop() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        shop.setVisible(true);
        gamePanel.updateUI();
    }

    public void showHome() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        home.setVisible(true);
        gamePanel.updateUI();
    }

    public void showOffice() {
        menuPanel.setVisible(false);
        optionPanel.setVisible(false);
        gamePanel.setVisible(false);
        office.setVisible(true);
        gamePanel.updateUI();
    }

    public Player getPlayer() {
        return this.player;
    }
    public GameTime getGameTime() {
        return this.gameTime;
    }
    public GamePanel getGamePanel() {
        return this.gamePanel; // Return the ACTUAL game panel you created
    }
}
*/

   