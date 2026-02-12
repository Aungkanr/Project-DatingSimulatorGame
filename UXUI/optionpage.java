import javax.swing.*;
import java.awt.*;

public class optionpage extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public optionpage() {
        setTitle("Game Project");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. สร้างหน้า Main Menu
        JPanel menuPanel = createMenuPanel();
        
        // 2. สร้างหน้า Options
        JPanel optionsPanel = createOptionsPanel();

        // เพิ่มทุกหน้าลงใน Container หลัก
        mainContainer.add(menuPanel, "Menu");
        mainContainer.add(optionsPanel, "Options");

        add(mainContainer);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));

        JButton playBtn = new JButton("PLAY");
        JButton optionBtn = new JButton("OPTIONS");
        JButton exitBtn = new JButton("EXIT");

        // เมื่อกด Option ให้สลับหน้าไปยัง "Options"
        optionBtn.addActionListener(e -> cardLayout.show(mainContainer, "Options"));
        exitBtn.addActionListener(e -> System.exit(0));

        // เพิ่มปุ่มลงใน Panel (จัดวางแนวตั้ง)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        panel.add(playBtn, gbc);
        panel.add(optionBtn, gbc);
        panel.add(exitBtn, gbc);

        return panel;
    }

    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45));

        JLabel label = new JLabel("SETTINGS", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JButton backBtn = new JButton("BACK TO MENU");
        // เมื่อกด Back ให้สลับหน้ากลับไปที่ "Menu"
        backBtn.addActionListener(e -> cardLayout.show(mainContainer, "Menu"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        panel.add(label, gbc);
        panel.add(new JCheckBox("Mute Sound"), gbc);
        panel.add(backBtn, gbc);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new optionpage().setVisible(true));
    }
}