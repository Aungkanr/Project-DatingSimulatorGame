package UXUI;
import Player.*;
import Relationship.Arwen;
import Relationship.Galadriel;
import Relationship.Lazel;
import UXUI.CheatPanel.Cheat;
import UXUI.Scene.*;
import UXUI.SceneNPC.Arwen.ArwenPanel;
import UXUI.SceneNPC.Arwen.SpecialSceneArwenPanel;
import UXUI.SceneNPC.Galadriel.GaladrielPanel;
import UXUI.SceneNPC.Galadriel.SpecialSceneGaladrielPanel;
import UXUI.SceneNPC.Lazel.LazelPanel;
import UXUI.SceneNPC.Lazel.SpecialSceneLazelPanel;
import UXUI.StatusBarMenu.GamePanel;
import java.io.File;
import java.awt.EventQueue;

import javax.sound.sampled.Clip; // แก้เป็น Clip
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Utility.*;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private Player player;
    private GameTime gameTime;

    private MenuPanel menuPanel;
    private LobbyPanel lobbyPanel;
    private OptionPanel optionPanel;
    private GamePanel gamePanel;
    private SchoolPanel school;
    private ShopPanel shop;
    private HomePanel home;
    private OfficePanel office;
    private NeighBorPanel neighbor;
    private StdAuto stdScreen; 
    private Clip clip; 
    public static String filePath = "Music\\Harvest Dawn.wav";  
    public static File file = new File(filePath);
    Utility.AssetManager asset = Utility.AssetManager.getInstance();
    // 2. ประกาศตัวแปร SoundManager
    private MusicManager soundManager;
    private SFXManager sfxManager;
    //NPC Panel 
    private LazelPanel lazelPanel;//X
    private GaladrielPanel galadrielPanel ; //x
    private ArwenPanel arwenPanel;//x
    private SpecialSceneGaladrielPanel specialSceneGaladrielPanel; //x
    private SpecialSceneLazelPanel specialSceneLazelPanel; //x
    private SpecialSceneArwenPanel specialSceneArwenPanel; //x
    

    public static void main(String[] args) {
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
        PreLoad(); // โหลด asset ล่วงหน้า (ถ้ามี) ***ควรอยู่ลำดับเเรกของโค้ดเสมอเพราะต้องโหลดก่อนเข้าเกม***

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

        //X
        lobbyPanel = new LobbyPanel(this);
        lobbyPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        lobbyPanel.setVisible(false);
        contentPane.add(lobbyPanel);

        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);



        // กด F12 เพื่อเปิกหน้าเมนู Cheat
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("F12"), "openCheat");
        actionMap.put("openCheat", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // เปิดหน้าต่าง Cheat
                Cheat cheatPanel = new Cheat(MainFrame.this);
                cheatPanel.setVisible(true);
            }
        });
    }

    public void PreLoad() {
        System.out.println("---------- Start Preloading ----------");
        asset.loadFolderParallel("image");
        asset.loadFolderParallel("Music");
        System.out.println("---------- Preloading Finished ----------");
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

    public void createNeightBorPanel() { 
        neighbor = new NeighBorPanel(this);
        neighbor.setBounds(0, 0, stdScreen.width, stdScreen.height);
        neighbor.setVisible(false);
        add(neighbor);
    }
    //=========================================================================================================================================================================================
    // --- เพิ่มฟังก์ชันสร้าง Galadriel Panel --------------------------------------  //X
    public void createGaladrielPanel() {
        if (galadrielPanel != null) contentPane.remove(galadrielPanel);
        galadrielPanel = new GaladrielPanel(this);
        galadrielPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        galadrielPanel.setVisible(false);
        contentPane.add(galadrielPanel); // Add เข้า contentPane
    }

    // --- เพิ่มฟังก์ชั่น SpecialScene ของ galadriel -------------------------------------- //X
    public void createSpecialSceneGaladrielPanel(Galadriel galadriel, String sceneText, int sceneLevel) {
        if (specialSceneGaladrielPanel != null) contentPane.remove(specialSceneGaladrielPanel);
        specialSceneGaladrielPanel = new SpecialSceneGaladrielPanel(this, galadriel, sceneText, sceneLevel);
        specialSceneGaladrielPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        specialSceneGaladrielPanel.setVisible(false);
        contentPane.add(specialSceneGaladrielPanel);
    }

    // --- เพิ่มฟังก์ชัน Showgaladriel ------------------------------------------------------ //X
    public void showGaladriel() {
        toggleVisibility(galadrielPanel);
        if(gamePanel != null) gamePanel.updateUI(); // เผื่ออัปเดตค่าอื่นๆ
    }
    public void showSpecialSceneGaladriel() { 
        toggleVisibility(specialSceneGaladrielPanel);
        if(gamePanel != null) gamePanel.updateUI();
    }
    //=========================================================================================================================================================================================
    // --- เพิ่มฟังก์ชันสร้าง Lazel Panel --------------------------------------  //X
    public void createLazelPanel() {
        if (lazelPanel != null) contentPane.remove(lazelPanel);
        lazelPanel = new LazelPanel(this);
        lazelPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        lazelPanel.setVisible(false);
        contentPane.add(lazelPanel); // Add เข้า contentPane
    }

    // --- เพิ่มฟังก์ชั่น SpecialScene ของ lazel -------------------------------------- //X
    public void createSpecialSceneLazelPanel(Lazel lazel, String sceneText, int sceneLevel) {
        if (specialSceneLazelPanel != null) contentPane.remove(specialSceneLazelPanel);
        specialSceneLazelPanel = new SpecialSceneLazelPanel(this, lazel, sceneText, sceneLevel);
        specialSceneLazelPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        specialSceneLazelPanel.setVisible(false);
        contentPane.add(specialSceneLazelPanel);
    }

    // --- เพิ่มฟังก์ชัน Show ------------------------------------------------------ //X
    public void showLazel() {
        toggleVisibility(lazelPanel);
        if(gamePanel != null) gamePanel.updateUI(); // เผื่ออัปเดตค่าอื่นๆ
    }
    //------showSpecialSceneLazel---------------------------------------------------
    public void showSpecialSceneLazel() { 
        toggleVisibility(specialSceneLazelPanel);
        if(gamePanel != null) gamePanel.updateUI();
    }
    //=========================================================================================================================================================================================
    // --- เพิ่มฟังก์ชันสร้าง Arwen Panel --------------------------------------  //X
    public void createArwenPanel() {
        if (arwenPanel != null) contentPane.remove(arwenPanel);
        arwenPanel = new ArwenPanel(this);
        arwenPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        arwenPanel.setVisible(false);
        contentPane.add(arwenPanel); // Add เข้า contentPane
    }

    // --- เพิ่มฟังก์ชั่น SpecialScene ของ Arwen -------------------------------------- //X
    public void createSpecialSceneArwenPanel(Arwen arwen, String sceneText, int sceneLevel) {
        if (specialSceneArwenPanel != null) contentPane.remove(specialSceneArwenPanel);
        specialSceneArwenPanel = new SpecialSceneArwenPanel(this, arwen, sceneText, sceneLevel);
        specialSceneArwenPanel.setBounds(0, 0, stdScreen.width, stdScreen.height);
        specialSceneArwenPanel.setVisible(false);
        contentPane.add(specialSceneArwenPanel);
    }

    // --- เพิ่มฟังก์ชัน Show ------------------------------------------------------ //X
    public void showArwen() {
        toggleVisibility(arwenPanel);
        if(gamePanel != null) gamePanel.updateUI(); // เผื่ออัปเดตค่าอื่นๆ
    }
    //------showSpecialSceneArwen---------------------------------------------------
    public void showSpecialSceneArwen() { 
        toggleVisibility(specialSceneArwenPanel);
        if(gamePanel != null) gamePanel.updateUI();
    }
    //=========================================================================================================================================================================================

    // 4. แก้ไขฟังก์ชัน Mute ให้เรียกผ่าน Manager
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

    //ตั้งค่าเสียงให้ปรับลดลงได้ทั้งของ MUSIC และ SFX 
    public void setMusicVolume(float volume) {
        if (soundManager != null) soundManager.setVolume(volume);
    }

    public void setSFXVolume(float volume) {
        if (sfxManager != null) sfxManager.setVolume(volume);
    }
    
    // เพิ่ม Getter เผื่อเอาไปใช้ที่อื่น
    public MusicManager getSoundManager() {
        return soundManager;
    }

    public Utility.SFXManager getSFXManager() {
        return sfxManager;
    }
    
    public Clip getClip() { return clip; }
    public void showMenu() { toggleVisibility(menuPanel); }
    public void showOption() { toggleVisibility(optionPanel); }
    public void showGame() { toggleVisibility(gamePanel); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showSchool() { toggleVisibility(school); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showShop() { toggleVisibility(shop); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showHome() { toggleVisibility(home); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showOffice() { toggleVisibility(office); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showNeighbor() { toggleVisibility(neighbor); if(gamePanel!=null) gamePanel.updateUI(); }
    public void showLobby() { toggleVisibility(lobbyPanel); } 

    // Helper function เพื่อปิด panel อื่นๆ อัตโนมัติ
    private void toggleVisibility(JPanel showPanel) {
        if(menuPanel != null) menuPanel.setVisible(false);
        if(optionPanel != null) optionPanel.setVisible(false);
        if(gamePanel != null) gamePanel.setVisible(false);
        if(school != null) school.setVisible(false);
        if(shop != null) shop.setVisible(false);
        if(home != null) home.setVisible(false);
        if(office != null) office.setVisible(false);
        if(neighbor != null) neighbor.setVisible(false);
        if(lobbyPanel != null) lobbyPanel.setVisible(false);
        //Lazel
        if(lazelPanel != null) lazelPanel.setVisible(false); 
        if(specialSceneLazelPanel != null) specialSceneLazelPanel.setVisible(false);
        //Galadrie
        if(galadrielPanel != null) galadrielPanel.setVisible(false); 
        if(specialSceneGaladrielPanel != null) specialSceneGaladrielPanel.setVisible(false);
        //Arwen
        if(arwenPanel != null) arwenPanel.setVisible(false); 
        if(specialSceneArwenPanel != null) specialSceneArwenPanel.setVisible(false);

        if(showPanel != null) showPanel.setVisible(true);
    }
    //Panel
    public Player getPlayer() { return this.player; }
    public GameTime getGameTime() { return this.gameTime; }
    public GamePanel getGamePanel() { return this.gamePanel; }
    public ShopPanel getShopPanel() { return this.shop;}
    public OfficePanel getOfficePanel() {return this.office;}
    public SchoolPanel getSchoolPanel() {return this.school;}
    public NeighBorPanel getNeighBorPanel () {return this.neighbor;}
    public LobbyPanel getLobbyPanel() { return this.lobbyPanel; }
    //NPC Panel
    public LazelPanel getLazelPanel() { return lazelPanel; }
    public GaladrielPanel getGaladrielPanel() { return galadrielPanel; }
    public ArwenPanel getArwenPanel() { return arwenPanel; }
    //NPC UI status Ralationship
    public Lazel getLazel() { return this.player.getLazel(); }
    public Galadriel getGaladriel() { return this.player.getGaladriel() ;}
    public Arwen getArwen() { return this.player.getArwen() ;}
}