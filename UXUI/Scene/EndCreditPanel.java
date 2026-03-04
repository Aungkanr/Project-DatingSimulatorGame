package UXUI.Scene;

import java.awt.*;
import javax.swing.*;
import UXUI.MainFrame;
import UXUI.Hovereffect;

public class EndCreditPanel extends JPanel {
    private MainFrame mainFrame;
    private Timer scrollTimer;
    private int textY;
    
    private String[] credits = {
        "THE END",
        "",
        "Thank you for playing our game",
        "",
        "=== Scrum Master ===",
        "Aungkanr sakunbundi",
        "",
        "=== Story + Characters + Background ===",
        "Natthawut Juntaya",
        "",
        "=== UI / UX Design ===",
        "Kittitat khantham (UI)",
        "Mongkon Arsakit (UX/UI)",
        "",
        "=== Code System ===",
        "Mongkon Arsakit (Mini game)",
        "Natthakit Rodruuean (Game Code System)",
        "",
        "",
        "",
        "The journey continues..."
    };

    public EndCreditPanel(MainFrame mainFrame, int width, int height) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBackground(Color.BLACK); 
        setBounds(0, 0, width, height); // ใช้ขนาดที่ส่งมา
        setVisible(false);


        JButton btnSkip = new JButton("SKIP");
        btnSkip.setFont(new Font("Tahoma", Font.BOLD, 16));
        int btnW = 120, btnH = 40;
        Hovereffect.HoverEffectRounded(btnSkip, width - btnW - 30, height - btnH - 50, btnW, btnH, new Color(100, 100, 100));
        btnSkip.addActionListener(e -> {
            mainFrame.getSFXManager().playSFX("Music\\Mouse_Click_Sound_Effect_128k.wav");
            finishCredits();
        });
        add(btnSkip);

        scrollTimer = new Timer(30, e -> {
            textY -= 2; 
            if (textY < -(credits.length * 60)) {
                finishCredits();
            }
            repaint();
        });
    }

    public void startCredits() {
        this.textY = getHeight(); // เริ่มเลื่อนจากขอบล่างสุดของจอจริง
        this.setVisible(true);
        scrollTimer.start();
    }

    private void finishCredits() {
        scrollTimer.stop();
        this.setVisible(false);
        
        mainFrame.getSoundManager().stopMusic();
        mainFrame.getSoundManager().playMusic(MainFrame.filePath); 
        
        mainFrame.showGame(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = textY;
        for (String line : credits) {
            if (line.equals("THE END")) {
                g2.setColor(new Color(255, 215, 0)); 
                g2.setFont(new Font("Tahoma", Font.BOLD, 60));
            } else if (line.startsWith("===")) {
                g2.setColor(new Color(150, 150, 250)); 
                g2.setFont(new Font("Tahoma", Font.BOLD, 24));
            } else {
                g2.setColor(Color.WHITE); 
                g2.setFont(new Font("Tahoma", Font.PLAIN, 28));
            }
            
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(line)) / 2;
            g2.drawString(line, x, y);
            
            y += 60; 
        }
    }
}