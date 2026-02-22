package UXUI.CheatPanel;

import java.awt.*;
import javax.swing.*;
import UXUI.MainFrame;

public class Cheat extends JFrame {

    private MainFrame mainFrame;

    public Cheat(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Developer Cheat");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดหน้านี้แล้วเกมหลักยังอยู่
        setAlwaysOnTop(true); // ให้หน้าต่างนี้ลอยอยู่บนสุดเสมอ
        setLocation(50, 50);  // ให้เปิดมามุมซ้ายบน
        
        // ใช้ GridLayout 
        setLayout(new GridLayout(8, 1, 10, 10)); 

        // พื้นหลังสีดำ
        getContentPane().setBackground(new Color(30, 30, 30));

        initUI();
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("CHEAT MENU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel);

        // --- เสกเงิน ---
        JButton btnAddMoney = createCheatButton("Add Money +1000");
        btnAddMoney.addActionListener(e -> {
            mainFrame.getPlayer().increaseMoney(1000);
            mainFrame.getGamePanel().updateUI();
        });
        add(btnAddMoney);

        // --- Reset พลังงาน ---
        JButton btnMaxEnergy = createCheatButton("Reset Energy");
        btnMaxEnergy.addActionListener(e -> {
            mainFrame.getPlayer().setEnergy(100);
            mainFrame.getGamePanel().updateUI();
        });
        add(btnMaxEnergy);

        // --- ข้ามเวลา ---
        JButton btnNextDay = createCheatButton("Skip to next time");
        btnNextDay.addActionListener(e -> {
            mainFrame.getGameTime().nextTime();
            mainFrame.getGamePanel().updateUI();
        });
        add(btnNextDay);

        // --- เพิ่มความสัมพันธ์ ---
        JButton btnLazel = createCheatButton("increaseAffection (Lazel +50 point)");
        btnLazel.addActionListener(e -> {
            mainFrame.getPlayer().getLazel().addAffection(50);
            mainFrame.getGamePanel().updateUI();
            if (mainFrame.getLazelPanel() != null && mainFrame.getLazelPanel().isVisible()) {
                mainFrame.getLazelPanel().updateStatusUI();
            }
        });
        add(btnLazel);

        JButton btnGaladriel = createCheatButton("increaseAffection (Galadriel +50 point)");
        btnGaladriel.addActionListener(e -> {
            mainFrame.getGaladriel().addAffection(50);
            mainFrame.getGamePanel().updateUI();
            if (mainFrame.getGaladrielPanel() != null && mainFrame.getGaladrielPanel().isVisible()) {
                mainFrame.getGaladrielPanel().updateStatusUI();
            }
        });
        add(btnGaladriel);

        JButton btnArwen = createCheatButton("increaseAffection (Arwen +50 point)");
        btnArwen.addActionListener(e -> {
            mainFrame.getArwen().addAffection(50);
            mainFrame.getGamePanel().updateUI();
            if (mainFrame.getArwenPanel() != null && mainFrame.getArwenPanel().isVisible()) {
                mainFrame.getArwenPanel().updateStatusUI();
            }
        });
        add(btnArwen);

        // --- ปิดหน้าต่าง Cheat ---
        JButton btnClose = new JButton("Close Cheat Console");
        btnClose.setBackground(new Color(150, 50, 50));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnClose.addActionListener(e -> dispose()); // สั่งปิดเฉพาะหน้าต่างนี้
        add(btnClose);
    }

    // ฟังก์ชันช่วยสร้างปุ่ม
    private JButton createCheatButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }
}