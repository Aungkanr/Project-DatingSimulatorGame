package UXUI;
import Player.Player;
import UXUI.Scene.HomePanel;
import UXUI.Scene.OfficePanel;
import UXUI.Scene.SchoolPanel;
import UXUI.Scene.ShopPanel;
import Utility.StdAuto;
import java.io.File;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip; // แก้เป็น Clip
import javax.sound.sampled.FloatControl; // เพิ่ม
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private Player player;

    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;
    private SchoolPanel school;
    private ShopPanel shop;
    private HomePanel home;
    private OfficePanel office;
    //ดึงขนาดหน้าจอจริงของคอมพิวเตอร์มาใช้
    private StdAuto stdScreen = new StdAuto(); //Device screen
    
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
    
    public MainFrame() { //con
        player = new Player(); 
        //---load soud
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(1024, 768);       // กำหนดขนาดตายตัว
        setResizable(false);      // <--- ห้ามย่อขยายหน้าต่าง
        setLocationRelativeTo(null); // ให้อยู่กลางจอเสมอ
        stdScreen.setBtnWHG(300, 60, 20 ,4); //ขนาด ปุ่ม และ gap ,แถว

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, stdScreen.width, stdScreen.height);
        setLayout(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
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

        if (school != null) school.setVisible(false);
        if (shop != null) shop.setVisible(false);
        if (home != null) home.setVisible(false);
        if (office != null) office.setVisible(false);
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

    public Player getPlayer() {
        return player;
    }
}