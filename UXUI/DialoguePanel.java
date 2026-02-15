package UXUI; // เปลี่ยนเป็น package ของคุณ

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DialoguePanel extends JPanel {

    // --- Components ---
    private JLabel nameLabel;
    private JTextArea textArea;
    private Timer timer;

    // --- Data Variables ---
    private String fullText = "";     // ข้อความเต็มๆ ที่จะโชว์
    private String currentText = "";  // ข้อความที่กำลังค่อยๆ พิมพ์
    private int charIndex = 0;        // ตัวนับตำแหน่งตัวอักษร
    private boolean isTyping = false; // สถานะว่ากำลังพิมพ์อยู่ไหม

    // --- Config ---
    private int typingSpeed = 30;     // ความเร็วในการพิมพ์ (ms) ยิ่งน้อยยิ่งเร็ว

    //ตัวแปรตั้งค่าความกว่างความสูงกล่อง เพื่อความง่ายต่อการใช้
    private int nameBoxHeight = 60;
    private int nameBoxWidth = 300;
    private int gap = 10;
    private int topMargin = nameBoxHeight + gap;

    public DialoguePanel() {
        // 1. ตั้งค่า Panel หลัก
        setLayout(null); // ใช้ null layout เพื่อจัดวางตำแหน่งเอง (หรือใช้ Layout อื่นก็ได้)
        setOpaque(false); // พื้นหลังใส (เพื่อให้เราวาดกรอบสีชมพูเองใน paintComponent)
        
        // เพิ่ม Mouse Listener เพื่อทำระบบ Skip
        MouseAdapter skipAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isTyping) {
                    skipAnimation(); // ถ้ากำลังพิมพ์ ให้ข้ามไปจบเลย
                } else {
                    // System.out.println("Next Dialogue..."); 
                }
            }
        };
        addMouseListener(skipAction);

        initUI();

        textArea.addMouseListener(skipAction);
    }

    private void initUI() {
        // 2. ป้ายชื่อ (ขยับลงมาให้อยู่ในกรอบ)
        nameLabel = new JLabel("Suzuka Ayaka");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        nameLabel.setForeground(Color.WHITE);
        
        // แก้: y จาก -25 เป็น 5 (เพื่อให้ตรงกับกรอบป้ายชื่อใหม่)
        nameLabel.setBounds(30, 10, 200, 40); 

        // 3. พื้นที่ข้อความ (ขยับลงตามกล่องหลัก)
        textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true); // ปิดการตัดบรรทัด
        textArea.setWrapStyleWord(true);// ตัดเป็นคำๆ
        textArea.setOpaque(false);// พื้นหลังใส
        textArea.setEditable(false);   // ห้ามพิมพ์แก้
        textArea.setHighlighter(null); // ปิดการไฮไลท์
        textArea.setBounds(30, nameBoxHeight + gap + 20, 1400, 120);
        add(nameLabel);
        add(textArea);

        // ตั้งค่า Timer สำหรับ Typewriter Effect
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

    // --- ฟังก์ชันเรียกใช้จากภายนอก ---

    // สั่งให้พูดประโยคใหม่
    public void setText(String name, String text) {
        this.nameLabel.setText(name);
        this.fullText = text;
        this.currentText = "";
        this.charIndex = 0;
        this.isTyping = true;
        
        textArea.setText("");
        timer.start();
    }

    // ฟังก์ชัน Skip (กดแล้วข้อความมาครบเลย)
    public void skipAnimation() {
        timer.stop();
        textArea.setText(fullText);
        isTyping = false;
        charIndex = fullText.length();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color pinkBg = new Color(230, 100, 160, 200); 
        Color whiteBorder = Color.WHITE;

        // 1. วาดกล่องข้อความหลัก (Main Box) - ดันลงมาที่ y = topMargin
        g2.setColor(pinkBg);
        g2.fillRect(10, topMargin, getWidth() - 20, getHeight() - topMargin - 10);  
        g2.setColor(whiteBorder);
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(10, topMargin, getWidth() - 20, getHeight() - topMargin - 10);  

        // 2. วาดกล่องชื่อ (Name Tag) - วาดที่ y = 0 (จุดยอดสุดของ Panel)
        g2.setColor(pinkBg);
        g2.fillRect(10, 0, nameBoxWidth, nameBoxHeight);  // ลบ , 15, 15 ออก
        g2.setColor(whiteBorder);
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(10, 0, nameBoxWidth, nameBoxHeight);  // ลบ , 15, 15 ออก
    }
}