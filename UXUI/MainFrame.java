package UXUI;
import Player.*;
import UXUI.Scene.*;
import UXUI.SceneNPC.*;
import UXUI.StatusBarMenu.GamePanel;
import java.io.File;
import java.awt.EventQueue;

import javax.sound.sampled.Clip; 
import javax.swing.JFrame;
import javax.swing.JPanel;
import Utility.*;

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
    Utility.AssetManager asset = Utility.AssetManager.getInstance();
    
    // 2. ประกาศตัวแปร SoundManager
    private MusicManager soundManager;
    private SFXManager sfxManager;
    private LazelPanel lazelPanel; //X


    public static void main(String[] args) {
        // [เพิ่มบรรทัดนี้] สั่งให้ Java ไม่ต้องขยายตาม Windows Scale (แก้ภาพแตก/เบลอ)
        System.setProperty("sun.java2d.uiScale", "1.0"); 

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

        PreLoad(); // โหลด asset ล่วงหน้า
        
        // 2. ตั้งค่าหน้าต่าง
        setTitle("Dating Simulator Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // [สำคัญ] ใช้ขนาดจาก StdAuto
        setSize(stdScreen.width, stdScreen.height);
        
        // จัดกลางจอ + ห้ามย่อขยาย
        setLocationRelativeTo(null); 
        setResizable(false);

        player = new Player(); 
        gameTime = new GameTime();
        
        soundManager = new MusicManager();
        sfxManager = new SFXManager();

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

    public void PreLoad() {
        asset.getImage("image\\Map\\Afternoon.png");
        asset.getImage("image\\Map\\Night.png");
        asset.getImage("image\\Map\\Morning.png");
        asset.getImage("image\\Map\\Evening.png");
        asset.getImage("image\\MenuBackground.png");
        asset.getImage("image\\Scene\\Office\\Barad-durWork.png");
        asset.getImage("image\\Scene\\Shop\\ร้านดอกไม้ตอนเช้า.png");
        asset.getImage("image\\Scene\\Bedroom\\ห้องนอน.png");
        asset.getImage("image\\Scene\\School\\Angryscene.png");
        asset.getImage("image\\Scene\\School\\โรงเรียนตอนเช้า.png");
    }

    // --- ส่วนสร้าง Scene ต่างๆ ---
    public void createSchoolPanel() { 
        if (school != null) contentPane.remove(school); // ลบของเก่าก่อนสร้างใหม่เพื่อความชัวร์
        school = new SchoolPanel(this);
        school.setBounds(0, 0, stdScreen.width, stdScreen.height);
        school.setVisible(false);
        contentPane.add(school); // แนะนำให้ add ใส่ contentPane เพื่อความเป็นระเบียบ
    }
    
    public void createShopPanel() { 
        if (shop != null) contentPane.remove(shop);
        shop = new ShopPanel(this);
        shop.setBounds(0, 0, stdScreen.width, stdScreen.height);
        shop.setVisible(false);
        contentPane.add(shop);
    }

    public void createHomePanel() { 
        if (home != null) contentPane.remove(home);
        home = new HomePanel(this);
        home.setBounds(0, 0, stdScreen.width, stdScreen.height);
        home.setVisible(false);
        contentPane.add(home);
    }

    public void createOfficePanel() { 
        if (office != null) contentPane.remove(office);
        office = new OfficePanel(this);
        office.setBounds(0, 0, stdScreen.width, stdScreen.height);
        office.setVisible(false);
        contentPane.add(office);
    }
    
    // --- เพิ่มฟังก์ชันสร้าง Panel ---  //X
    public void createLazelPanel() {
        if (lazelPanel != null) contentPane.remove(lazelPanel);
        lazelPanel = new LazelPanel(this);
        lazelPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        lazelPanel.setVisible(false);
        contentPane.add(lazelPanel); 
    }

    // --- เพิ่มฟังก์ชัน Show ---
    public void showLazel() {
        toggleVisibility(lazelPanel);
        if(gamePanel != null) gamePanel.updateUI(); 
    }

    public void toggleMute(boolean isMute) {
        if (soundManager != null) {
            soundManager.setMute(isMute);
        }
    }

    public void toggleSFX(boolean isMute) {
        if (sfxManager != null) {
            sfxManager.setMute(isMute);
        }
    }
    
    public MusicManager getSoundManager() { return soundManager; }
    public Utility.SFXManager getSFXManager() { return sfxManager; }
    
    public Clip getClip() { return clip; }
    public void showMenu() { toggleVisibility(menuPanel); }
    public void showOption() { toggleVisibility(optionPanel); }
    public void showGame() { toggleVisibility(gamePanel); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showSchool() { toggleVisibility(school); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showShop() { toggleVisibility(shop); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showHome() { toggleVisibility(home); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showOffice() { toggleVisibility(office); if(gamePanel!=null) gamePanel.updateUI(); }

    private void toggleVisibility(JPanel showPanel) {
        if(menuPanel != null) menuPanel.setVisible(false);
        if(optionPanel != null) optionPanel.setVisible(false);
        if(gamePanel != null) gamePanel.setVisible(false);
        if(school != null) school.setVisible(false);
        if(shop != null) shop.setVisible(false);
        if(home != null) home.setVisible(false);
        if(office != null) office.setVisible(false);
        if(lazelPanel != null) lazelPanel.setVisible(false); 
        
        if(showPanel != null) showPanel.setVisible(true);
    }

    public Player getPlayer() { return this.player; }
    public GameTime getGameTime() { return this.gameTime; }
    public GamePanel getGamePanel() { return this.gamePanel; }
}