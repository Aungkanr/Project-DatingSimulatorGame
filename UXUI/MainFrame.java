import Player.Player; 
import java.awt.Dimension; 
import java.awt.Toolkit;
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

    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int width = (int) screenSize.getWidth();
    public int height = (int) screenSize.getHeight();
    
    private Clip clip; //sound
    public static String filePath = "DatingSimulatorGame\\Music\\Harvest Dawn.wav";
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

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, width, height);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // ส่ง 'this' ไปให้ลูกๆ
        menuPanel = new MenuPanel(this); 
        menuPanel.setBounds(0, 0, width, height);
        contentPane.add(menuPanel);
        
        optionPanel = new OptionPanel(this);
        optionPanel.setBounds(0, 0, width, height);
        optionPanel.setVisible(false);
        contentPane.add(optionPanel);
        
        gamePanel = new GamePanel(this);
        gamePanel.setBounds(0, 0, width, height);
        gamePanel.setVisible(false);
        contentPane.add(gamePanel);
    }

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

    public Player getPlayer() {
        return player;
    }
}