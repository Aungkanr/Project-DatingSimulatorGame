package Utility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ScreenFader extends JPanel {
    
    // ค่าความโปร่งแสงปัจจุบัน (0.0 = ใส, 1.0 = ทึบ)
    private float alpha = 0.0f;
    
    // สีที่จะใช้ Fade (Default = ดำ)
    private Color fadeColor = Color.BLACK;
    
    private Timer timer;
    private final int FPS = 60; // ความลื่นไหล (60 เฟรมต่อวิ)
    private final int DELAY = 1000 / FPS; // คำนวณ Delay (ประมาณ 16ms)

    public ScreenFader() {
        setOpaque(false); // พื้นหลังโปร่งใส
        setBackground(new Color(0, 0, 0, 0));
        
        // ป้องกันการกดทะลุ Panel ตอนจอมืด (Optional: ถ้าต้องการบล็อคเมาส์)
        //addMouseListener(new java.awt.event.MouseAdapter() {});
    }

    // ==========================================================
    // 1. FADE OUT: ค่อยๆ มืดลง (Transparent -> Opaque)
    // ==========================================================
    public void fadeOut(int durationMs, Runnable onComplete) {
        startFadeAnimation(0.0f, 1.0f, durationMs, onComplete);
    }

    // ==========================================================
    // 2. FADE IN: ค่อยๆ สว่างขึ้น (Opaque -> Transparent)
    // ==========================================================
    public void fadeIn(int durationMs, Runnable onComplete) {
        startFadeAnimation(1.0f, 0.0f, durationMs, onComplete);
    }

// ==========================================================
    // 3. FADE IN-OUT: วูบดับ -> ทำอะไรสักอย่าง -> วูบตื่น (Sleep Effect)
    // ==========================================================
    /**
     * @param fadeDuration  เวลาในการ Fade (ms)
     * @param holdDuration  เวลาที่หน้าจอจะดำค้างไว้ (ms)
     * @param onDarkAction  คำสั่งที่จะให้ทำตอนจอมืดสนิท
     * @param onFinish      คำสั่งที่จะให้ทำตอนจอกลับมาสว่างเสร็จแล้ว (เพิ่มใหม่)
     */
    public void fadeInOut(int fadeDuration, int holdDuration, Runnable onDarkAction, Runnable onFinish) {
        // Step 1: Fade Out (มืดลง)
        fadeOut(fadeDuration, () -> {
            
            // ตอนนี้จอมืดสนิทแล้ว -> ทำคำสั่ง onDarkAction
            if (onDarkAction != null) onDarkAction.run();

            // Step 2: Hold (ค้างไว้)
            Timer holdTimer = new Timer(holdDuration, e -> {
                
                // Step 3: Fade In (สว่างขึ้น)
                // ส่ง onFinish ไปทำต่อหลังจากสว่างเสร็จ
                fadeIn(fadeDuration, onFinish); 
                
                ((Timer)e.getSource()).stop(); // หยุด Timer ค้าง
            });
            holdTimer.setRepeats(false);
            holdTimer.start();
        });
    }

    // ==========================================================
    // CORE LOGIC: ตัวคำนวณ Animation
    // ==========================================================
    private void startFadeAnimation(float startAlpha, float targetAlpha, int durationMs, Runnable onComplete) {
        // หยุด Timer เก่าถ้ามี
        if (timer != null && timer.isRunning()) timer.stop();

        this.alpha = startAlpha;
        
        // คำนวณว่าต้องขยับ Alpha ทีละเท่าไหร่ต่อเฟรม
        int totalFrames = durationMs / DELAY;
        float step = (targetAlpha - startAlpha) / totalFrames;

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += step;

                // เช็คขอบเขต (Boundary Check)
                if (step > 0) { // กำลังเพิ่ม (Fade Out)
                    if (alpha >= targetAlpha) finish(targetAlpha);
                } else { // กำลังลด (Fade In)
                    if (alpha <= targetAlpha) finish(targetAlpha);
                }

                repaint(); // วาดใหม่
            }

            // ฟังก์ชันจบการทำงาน
            private void finish(float finalAlpha) {
                alpha = finalAlpha;
                timer.stop();
                if (onComplete != null) onComplete.run(); // เรียก Callback
            }
        });
        timer.start();
    }
    
    // ตั้งค่าสี Fade (เช่น สีขาวสำหรับ Flashbang)
    public void setFadeColor(Color color) {
        this.fadeColor = color;
    }

    @Override
    public boolean contains(int x, int y) {
        // ถ้าค่า Alpha > 0 (เริ่มมืด หรือ มืดสนิท) -> return true (รับเมาส์ = บล็อคปุ่มข้างล่าง)
        // ถ้าค่า Alpha <= 0 (ใสสนิท) -> return false (ไม่รับเมาส์ = เมาส์ทะลุไปกดปุ่มข้างล่างได้)
        return alpha > 0.0f; 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // สำคัญมาก
        
        // ถ้า Alpha เป็น 0 ไม่ต้องวาดอะไรเลย (ประหยัดทรัพยากร)
        if (alpha <= 0.0f) return;

        Graphics2D g2 = (Graphics2D) g;
        
        // ป้องกันค่า Error
        if (alpha > 1.0f) alpha = 1.0f;
        if (alpha < 0.0f) alpha = 0.0f;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(fadeColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}