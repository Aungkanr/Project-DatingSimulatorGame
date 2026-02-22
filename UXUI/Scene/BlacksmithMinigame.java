package UXUI.Scene;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Player.Player;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.GamePanel;
import Utility.GameTime;
import Utility.Notify;
import Utility.StdAuto;

public class BlacksmithMinigame extends JPanel {

    private Timer gameLoop;
    
    // ตั้งค่าเป้าหมาย
    private final int TARGET_RADIUS = 60;   
    private double currentRadius = 250;     
    private double shrinkSpeed = 2.0;       
    
    // พิกัดวงกลม (ใช้สำหรับสุ่ม)
    private int targetX;
    private int targetY;

    // ระบบคะแนน
    private int score = 0;
    private final int WIN_SCORE = 10; 
    
    // สถานะเกม
    private boolean isPlaying = false;
    private boolean isGameOver = false; 
    
    // 🔥 [แก้ไขเกมค้าง] เพิ่มตัวแปรเช็คสถานะการพักเบรค
    private boolean isPaused = false; 
    
    private String resultMessage = "Click to Start!";
    private Color resultColor = Color.WHITE;

    private Notify Notify;
    private StdAuto stdScreen;

    Player realPlayer ;
    OfficePanel office;
    GamePanel realGamePanel ;
    MainFrame mainFrame;
    GameTime realGameTime ;

    public BlacksmithMinigame(MainFrame mainFrame, OfficePanel office) {
        this.mainFrame = mainFrame;
        this.office = office;
        setLayout(null);
        setOpaque(false);
        realGamePanel = mainFrame.getGamePanel();
        realPlayer = mainFrame.getPlayer();
        realGameTime = mainFrame.getGameTime();
        stdScreen = new StdAuto();
        Notify = new Notify(stdScreen.width);
        Notify.setBounds(0, 50, stdScreen.width, 50); 
        add(Notify);

        // ตั้งค่า Game Loop (ทำงาน 60 เฟรมต่อวินาที)
        gameLoop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // อัปเดตขนาดวงกลมเฉพาะตอนเล่น และไม่ได้หยุดพัก
                if (isPlaying && !isGameOver && !isPaused) {
                    currentRadius -= shrinkSpeed; 
                    
                    // ถ้าวงกลมหดเล็กกว่าเป้าหมายมากเกินไป = พลาด (Too Late)
                    if (currentRadius < TARGET_RADIUS - 15) {
                        handleResult("Too Late! (0)", Color.RED, 0);
                    }
                    repaint();
                }
            }
        });

        // รับค่าการคลิกเมาส์
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
                // 🔥 1. ถ้าเกมอยู่ในช่วงพัก (แสดงผลลัพธ์) จะบล็อกการคลิกทันที (แก้บัคเกมรวน/ค้างจากการกดรัวๆ)
                if (isPaused) return;

                if (isGameOver) {
                    // ถ้าเกมจบแล้ว กดเพื่อเริ่มใหม่
                    office.Work();
                } else if (!isPlaying) {
                    // กดเริ่มเกมครั้งแรก (ตอนขึ้นคำว่า Click to Start!)
                    startGame();
                } else {
                    
                    // 🔥 2. เช็คว่าคลิก "โดนในวงกลมเป้าหมาย (วงใน)" หรือไม่
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    
                    // ใช้ทฤษฎีพิทาโกรัส หาระยะห่างระหว่างจุดที่เมาส์คลิก กับ จุดศูนย์กลางวงกลม
                    double distance = Math.sqrt(Math.pow(mouseX - targetX, 2) + Math.pow(mouseY - targetY, 2));
                    
                    if (distance <= TARGET_RADIUS) {
                        // กดโดนในวงเป้าหมาย -> เช็คความแม่นยำ!
                        checkTiming();
                    } else {
                        // กดนอกวง -> เมินเฉย (ผู้เล่นต้องกดในวงเท่านั้น)
                        // (ถ้าอยากให้กดพลาดแล้วโดนหักแต้ม ให้เรียก handleResult("Miss Click", Color.RED, 0); ตรงนี้แทนได้)
                    }
                }
            }
        });
    }

    public void startGame() {
        score = 0;
        isGameOver = false;
        isPaused = false;
        resultMessage = "";
        spawnTarget();
        isPlaying = true;
        gameLoop.start();
    }

    // ฟังก์ชันสุ่มตำแหน่งวงกลม
    private void spawnTarget() {
        int w = getWidth();
        int h = getHeight();
        
        currentRadius = 250; // รีเซ็ตวงนอกสุด
        
        // 🔥 3. ป้องกันวงกลมเลยขอบจอ
        // ตั้งระยะขอบ (Margin) ให้เท่ากับขนาดวงกลมที่กว้างที่สุด (250) 
        int margin = (int) currentRadius; 
        
        if (w <= margin * 2 || h <= margin * 2) {
            // ป้องกัน Error กรณีหน้าจอเล็กกว่าวงกลม ให้มันอยู่กลางจอไปเลย
            targetX = w / 2; 
            targetY = h / 2;
        } else {
            // สุ่มตำแหน่ง X, Y ให้อยู่ในกรอบที่ปลอดภัย 100%
            targetX = margin + (int)(Math.random() * (w - margin * 2));
            targetY = margin + (int)(Math.random() * (h - margin * 2));
        }

        shrinkSpeed = 2.0 + (Math.random() * 2.5); // สุ่มความเร็วหด (2.0 ถึง 4.5)
    }

    private void checkTiming() {
        double difference = Math.abs(currentRadius - TARGET_RADIUS);

        if (difference <= 10) {
            handleResult("Perfect!! (+3)", Color.ORANGE, 3);
        } else if (difference <= 30) {
            handleResult("Good! (+1)", Color.GREEN, 1);
        } else {
            handleResult("Miss! (0)", Color.RED, 0);
        }
    }

    private void handleResult(String msg, Color color, int pointsAdded) {
        isPlaying = false; 
        isPaused = true; // ล็อคเมาส์ทันที
        resultMessage = msg;
        resultColor = color;
        score += pointsAdded; 

        repaint();

        if (score >= WIN_SCORE) {
            isGameOver = true;
            isPaused = false; // ปลดล็อค
            resultMessage = "SUCCESS! Passed!";
            resultColor = Color.YELLOW;
            gameLoop.stop();
            repaint();
            realPlayer.increaseMoney(80);
            Notify.showNotify("Good boy  Money: " + realPlayer.getMoney(), Color.GREEN , 2000); 
            if (realGamePanel != null) {
                realGamePanel.doActivity(40);
                DebugLog();
            }
        } else {
            // หน่วงเวลาให้ผู้เล่นดูผลลัพธ์แปปนึง ก่อนสุ่มวงกลมใหม่
            Timer pauseTimer = new Timer(600, e -> {
                if (!isGameOver) {
                    spawnTarget();
                    isPlaying = true;
                    isPaused = false; // ปลดล็อคเมาส์ให้เริ่มกดใหม่ได้
                    resultMessage = ""; 
                }
                repaint();
            });
            // 🔥 [แก้ไขเกมรวน] สั่งให้ Timer ตัวนี้ทำงานแค่ "รอบเดียว" แล้วทิ้งเลย
            pauseTimer.setRepeats(false); 
            pauseTimer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. วาดพื้นหลังมืดๆ
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. วาดคะแนนมุมซ้ายบน
        g2.setFont(new Font("Tahoma", Font.BOLD, 28));
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + score + " / " + WIN_SCORE, 30, 100);

        // แสดงผลลัพธ์ตรงกลางจอด้านบน (Perfect, Good, etc.)
        if (!resultMessage.isEmpty()) {
            g2.setFont(new Font("Tahoma", Font.BOLD, 40));
            g2.setColor(resultColor);
            FontMetrics fm = g2.getFontMetrics();
            int msgX = (getWidth() - fm.stringWidth(resultMessage)) / 2;
            int msgY = getHeight() / 2 - 100;
            g2.drawString(resultMessage, msgX, msgY);
        }

        // 3. วาดเป้าหมายวงกลม
        if (!isGameOver && !resultMessage.equals("Click to Start!")) {
            // วงเป้าหมาย (วงใน)
            g2.setColor(new Color(255, 255, 255, 80)); 
            g2.fillOval(targetX - TARGET_RADIUS, targetY - TARGET_RADIUS, TARGET_RADIUS * 2, TARGET_RADIUS * 2);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(targetX - TARGET_RADIUS, targetY - TARGET_RADIUS, TARGET_RADIUS * 2, TARGET_RADIUS * 2);

            // วงนอก (กำลังหด)
            if (isPlaying || currentRadius > 0) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(5));
                int currentR = (int) currentRadius;
                g2.drawOval(targetX - currentR, targetY - currentR, currentR * 2, currentR * 2);
            }
        } else if (isGameOver || resultMessage.equals("Click to Start!")) {
            // คำแนะนำเมื่อจบเกม หรือก่อนเริ่มเกม
            g2.setFont(new Font("Tahoma", Font.PLAIN, 24));
            g2.setColor(Color.WHITE);
            String subMsg = (isGameOver) ? "(Click to exit)" : "";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(subMsg, (getWidth() - fm.stringWidth(subMsg))/2, getHeight() / 2 + 50);
        }
    }

    // เพิ่มเมธอดนี้ไว้หยุดเกมเวลาผู้เล่นกดปุ่มหนี (บิด) กลางคัน
    public void stopGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        isPlaying = false;
    }

    public void DebugLog() {
        System.out.println("Working -> Energy: "+ realPlayer.getEnergy() +" | Day : "+ realGameTime.getDay() + "\n" +"          " + "Money : "+ realPlayer.getMoney()  +" | Time: "+ realGameTime.getTimeString());
    }
}