package UXUI.CheatPanel;

import java.awt.*;
import javax.swing.*;
import UXUI.MainFrame;
import Utility.Notify;
import Utility.StdAuto;

public class Cheat extends JFrame {

    private MainFrame mainFrame;
    private StdAuto stdScreen;
    private Notify realNotify;

    public Cheat(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.stdScreen = new StdAuto();
        this.stdScreen.setBtnWHG(250, 60, 20, 0);

        setTitle("Developer Cheat");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดหน้านี้แล้วเกมหลักยังอยู่
        setAlwaysOnTop(true); // ให้หน้าต่างนี้ลอยอยู่บนสุดเสมอ
        setLocation(50, 50);  // ให้เปิดมามุมซ้ายบน
        
        // ใช้ GridLayout 
        setLayout(new GridLayout(8, 1, 10, 10)); 

        // พื้นหลังสีดำ
        getContentPane().setBackground(new Color(30, 30, 30));
        //notify
        realNotify = new Notify(stdScreen.width);
        realNotify.setBounds(0, 50, stdScreen.width, 50); 
        add(realNotify);

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
            if (mainFrame.getGamePanel() != null) mainFrame.getGamePanel().updateUI();//Game Panel
            if (mainFrame.getOfficePanel() != null) mainFrame.getOfficePanel().updateUI();//Office Panel

        });
        add(btnAddMoney);

        // --- Reset พลังงาน ---
        JButton btnMaxEnergy = createCheatButton("Reset Energy");
        btnMaxEnergy.addActionListener(e -> {
            mainFrame.getPlayer().setEnergy(100);
            if (mainFrame.getGamePanel() != null) mainFrame.getGamePanel().updateUI();//Game Panel
            if (mainFrame.getOfficePanel() != null) mainFrame.getOfficePanel().updateUI();//Office Panel
        });
        add(btnMaxEnergy);

        // --- ข้ามเวลา ---
        JButton btnNextDay = createCheatButton("Skip to next time");
        btnNextDay.addActionListener(e -> {
            if (mainFrame.getGameTime().getTimeSlot() >= 3) {
                realNotify.showNotify("Sleep to skip day!!!.", Color.RED, 2050);
            } else mainFrame.getGameTime().nextTime();
            if (mainFrame.getGamePanel() != null) mainFrame.getGamePanel().updateUI();//Game Panel
            if (mainFrame.getOfficePanel() != null) mainFrame.getOfficePanel().updateUI();//Office Panel
            if (mainFrame.getSchoolPanel() != null) mainFrame.getSchoolPanel().updateUI();//School Panel
            if (mainFrame.getNeighBorPanel() != null) mainFrame.getNeighBorPanel().updateUI();//Neighbor Panel
            if (mainFrame.getShopPanel() != null) mainFrame.getShopPanel().updateUI();//Shop Panel
        });
        add(btnNextDay);

        // --- เพิ่มความสัมพันธ์ ---
        JButton btnLazel = createCheatButton("Affection(Lazel +50)");
        btnLazel.addActionListener(e -> {
            mainFrame.getPlayer().getLazel().addAffection(50);
            if (mainFrame.getLazelPanel() != null && mainFrame.getLazelPanel().isVisible()) mainFrame.getLazelPanel().updateStatusUI();//LazelPanel
            
        });
        add(btnLazel);

        JButton btnGaladriel = createCheatButton("Affection(Galadriel +50)");
        btnGaladriel.addActionListener(e -> {
            mainFrame.getGaladriel().addAffection(50);
            if (mainFrame.getGaladrielPanel() != null && mainFrame.getGaladrielPanel().isVisible()) mainFrame.getGaladrielPanel().updateStatusUI();//GaladrielPanel
    
        });
        add(btnGaladriel);

        JButton btnArwen = createCheatButton("Affection(Arwen +50)");
        btnArwen.addActionListener(e -> {
            mainFrame.getArwen().addAffection(50);
            if (mainFrame.getArwenPanel() != null && mainFrame.getArwenPanel().isVisible()) mainFrame.getArwenPanel().updateStatusUI();//ArwenPanel

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