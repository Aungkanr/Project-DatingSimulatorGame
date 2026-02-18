package UXUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Utility.StdAuto;

public class DialoguePanel extends JPanel {

    // --- Components ---
    private JLabel nameLabel;
    private JTextArea textArea;
    private Timer timer;
    private Timer cursorTimer; // ไทม์เมอร์สำหรับกระพริบไอคอน >>

    // --- Data Variables ---
    private String fullText = "";
    private String currentText = "";
    private int charIndex = 0;
    private boolean isTyping = false;
    private boolean showCursor = false; // สำหรับทำ effect กระพริบ

    // --- Config ---
    private int typingSpeed = 30;

    // --- UI Config (ปรับตามรูป Belle) ---
    private int nameBoxHeight = 40;
    private int nameBoxWidth = 200; // ความกว้างป้ายชื่อ
    private int topMargin = 25; // ระยะห่างจากขอบบนสุดถึงกล่องข้อความหลัก

    // สีตามสไตล์ใหม่ (ดำ-ขาว)
    private Color bgBlack = new Color(20, 20, 20, 240); // ดำเกือบสนิท
    private Color nameBgColor = new Color(40, 40, 40, 255); // เทาเข้มสำหรับป้ายชื่อ
    private Color borderColor = new Color(80, 80, 80); // ขอบสีเทาจางๆ
    private Color textColor = Color.WHITE;

    public DialoguePanel() {
        setLayout(null);
        setOpaque(false);

        // Mouse Listener สำหรับ Skip
        MouseAdapter skipAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isTyping) {
                    skipAnimation();
                }
            }
        };
        addMouseListener(skipAction);

        initUI();

        textArea.addMouseListener(skipAction);
        
        // Timer สำหรับทำไอคอน >> กระพริบ
        cursorTimer = new Timer(500, e -> {
            showCursor = !showCursor;
            repaint(); // วาดหน้าจอใหม่เพื่อกระพริบไอคอน
        });
        cursorTimer.start();
    }

    private void initUI() {
        // 2. ป้ายชื่อ (จัดกึ่งกลางข้อความ)
        nameLabel = new JLabel("Belle", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        nameLabel.setForeground(textColor);
        
        // เราจะคำนวณตำแหน่ง X ใน paintComponent หรือตอน setBounds แต่ตั้งค่าเริ่มต้นไว้ก่อน
        // ให้มันอยู่ตรงกลางคร่าวๆ (เดี๋ยวไปจัดเป๊ะๆ ใน paint)
        nameLabel.setBounds(0, 0, nameBoxWidth, nameBoxHeight); 
        
        // 3. พื้นที่ข้อความ
        textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.BOLD, 22)); // ตัวหนาแบบในรูป
        textArea.setForeground(textColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setHighlighter(null);
        
        // จัดตำแหน่ง textArea ให้มี Padding เข้ามาจากขอบ
        textArea.setBounds(40, topMargin + 35, 1170, 110);
        
        add(nameLabel);
        add(textArea);

        // Timer พิมพ์ข้อความ
        timer = new Timer(typingSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < fullText.length()) {
                    currentText += fullText.charAt(charIndex);
                    textArea.setText(currentText);
                    charIndex++;
                } else {
                    isTyping = false;
                    timer.stop();
                }
            }
        });
    }

    public void setDefaultBounds(StdAuto stdScreen, int btnY) {
        int dialogueW = 1250;
        int dialogueH = 220; // ลดความสูงลงนิดหน่อยให้ดูเรียวเหมือนในรูป
        int dialogueX = (stdScreen.width - dialogueW) / 2;
        int dialogueY = btnY - dialogueH - 20;

        setBounds(dialogueX, dialogueY, dialogueW, dialogueH);
        
        // ปรับตำแหน่งป้ายชื่อให้อยู่กึ่งกลาง Panel พอดี
        int nameX = (dialogueW - nameBoxWidth) / 2;
        // y = 0 คือติดขอบบนสุด (เหนือกล่องข้อความเล็กน้อย)
        nameLabel.setBounds(nameX, 0, nameBoxWidth, nameBoxHeight);
    }

    public void setText(String name, String text) {
        this.nameLabel.setText(name);
        this.fullText = text;
        this.currentText = "";
        this.charIndex = 0;
        this.isTyping = true;
        textArea.setText("");
        timer.start();
    }

    public void skipAnimation() {
        timer.stop();
        textArea.setText(fullText);
        isTyping = false;
        charIndex = fullText.length();
        repaint();
    }

    // ==========================================================
    // ส่วนวาดกราฟิกใหม่ (Rounded Black Box)
    // ==========================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = 40; // ความโค้งของมุม

        // --- 1. วาดกล่องข้อความหลัก (Main Box) ---
        // วาดให้เริ่มต่ำลงมาจากขอบบน (topMargin) เพื่อให้ป้ายชื่อแปะอยู่ข้างบนแบบคร่อมๆ หรือแยกกัน
        g2.setColor(bgBlack);
        g2.fillRoundRect(0, topMargin, w, h - topMargin, arc, arc);

        // วาดเส้นขอบบางๆ (ถ้าต้องการ)
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(0, topMargin, w, h - topMargin, arc, arc);

        // --- 2. วาดกล่องชื่อ (Name Tag) ---
        // วาดให้อยู่กึ่งกลางด้านบน
        int nameX = (w - nameBoxWidth) / 2;
        
        g2.setColor(nameBgColor);
        // วาดเป็นแคปซูล (Rounded Rectangle)
        g2.fillRoundRect(nameX, 0, nameBoxWidth, nameBoxHeight, 30, 30);
        
        g2.setColor(borderColor);
        g2.drawRoundRect(nameX, 0, nameBoxWidth, nameBoxHeight, 30, 30);
        
        // --- 3. วาดไอคอน >> (Next Indicator) ---
        // จะวาดเฉพาะตอนพิมพ์เสร็จแล้ว หรือจะวาดตลอดก็ได้
        if (!isTyping && showCursor) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Tahoma", Font.BOLD, 20));
            g2.drawString(">>", w - 60, h - 30);
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        
        // 1. เช็คว่ามีป้ายชื่อไหม
        if (nameLabel != null) {
            // 2. คำนวณจุดกึ่งกลาง (สูตรเดียวกับที่วาดกล่อง)
            int w = getWidth();
            int nameX = (w - nameBoxWidth) / 2;
            
            // 3. สั่งย้าย JLabel ไปที่จุดกึ่งกลางนั้น
            nameLabel.setBounds(nameX, 0, nameBoxWidth, nameBoxHeight);
        }
        
        // (ส่วน TextArea จัดการตำแหน่งของตัวเองอยู่แล้วใน initUI หรือจะย้ายมาคำนวณตรงนี้ด้วยก็ได้ถ้าต้องการ)
    }
}