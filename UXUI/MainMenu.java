import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import java.awt.Checkbox;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.ImageIcon;

public class MainMenu extends JFrame {

    private JPanel contentPane;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MainMenu frame = new MainMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MainMenu() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 1000, 2000, 1000);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        // สร้าง "กระดานหลัก" (mainPanel) และตั้งค่าเป็น CardLayout
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1540, 835); // กำหนดขนาดพื้นที่ที่จะแสดงผล
        contentPane.add(mainPanel);
        
        // สร้างตัวจัดการ CardLayout ---
        cardLayout = new CardLayout(); 
        mainPanel.setLayout(cardLayout);

        // -------------------------------------------------------
        // 1.สร้าง "หน้าแรก" (Page 1: Home)
        // -------------------------------------------------------
        JPanel Menu = new JPanel();
        Menu.setBackground(new Color(173, 216, 230)); // สีฟ้าอ่อน
        Menu.setLayout(null);
        // เพิ่มหน้าแรกลงใน mainPanel พร้อมตั้งชื่อว่า "home"
        mainPanel.add(Menu, "home"); 

        JLabel lblHome = new JLabel("หน้าเมนูหลัก");
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblHome.setHorizontalAlignment(SwingConstants.CENTER);
        lblHome.setBounds(640, 118, 200, 50);
        Menu.add(lblHome);

        JButton btnStart = new JButton("START");
        btnStart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // โชว์หน้าที่ชื่อ "StartGame"
                cardLayout.show(mainPanel, "StartGame");
        	}
        });
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStart.setBounds(640, 200, 200, 40);
        Menu.add(btnStart);
        
        
        
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnExit.setBounds(640, 325, 200, 40);
        Menu.add(btnExit);
        
        JButton btnSetting = new JButton("SETTING");
        btnSetting.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSetting.setBounds(640, 262, 200, 40);
        Menu.add(btnSetting);
        
        // คำสั่งให้ปุ่ม Setting เปลี่ยนหน้า
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // คำสั่งสลับหน้า: ให้โชว์หน้าที่ชื่อ "Setting"
                cardLayout.show(mainPanel, "Setting");
            }
        });

        // -------------------------------------------------------
        // 2. สร้าง หน้าการตั้งค่า
        // -------------------------------------------------------
        JPanel Option = new JPanel();
        Option.setBackground(new Color(0, 51, 204)); // สีส้มอ่อน
        Option.setLayout(null);
        // เพิ่มหน้าลงใน mainPanel พร้อมตั้งชื่อว่า "game"
        mainPanel.add(Option, "Setting");

        JButton btnBack = new JButton("กลับเมนูหลัก");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBounds(640, 377, 200, 40);
        Option.add(btnBack);
        
        JCheckBox chckbxNewCheckBox = new JCheckBox("Mute Music");
        chckbxNewCheckBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("Mute Music Initial");
        	}
        });
        chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        chckbxNewCheckBox.setBounds(640, 257, 200, 63);
        Option.add(chckbxNewCheckBox);

        // คำสั่งให้ปุ่ม Back กลับหน้าเดิม เมื่ออยู่หน้า setting อยู่
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ให้หลังไปโชว์หน้าที่ชื่อ "home"
                cardLayout.show(mainPanel, "home");
            }
        });
        
        
        // -------------------------------------------------------
        // 3. สร้าง หน้าเริ่มเกม
        // -------------------------------------------------------
        JPanel StartGame = new JPanel();
        StartGame.setBackground(Color.DARK_GRAY); // สีส้มอ่อน
        StartGame.setLayout(null);
        // เพิ่มลงใน mainPanel พร้อมตั้งชื่อว่า "StartGame"
        mainPanel.add(StartGame, "StartGame");
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\LOQ\\Downloads\\Map.png"));
        lblNewLabel.setBounds(0, 0, 1540, 835);
        StartGame.add(lblNewLabel);
    }
}