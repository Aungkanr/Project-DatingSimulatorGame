package Utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SFXManager {
    
    // map เก็บเสียงที่เคยโหลดแล้ว (Key = ชื่อไฟล์, Value = ตัวเล่นเสียง)
    private Map<String, Clip> sfxCache = new HashMap<>();
    private boolean isMuted = false;

    // สั่งเล่นเสียง Effect
    public void playSFX(String filePath) {
        if (isMuted) return; // ถ้าปิดเสียงอยู่ ไม่ต้องทำอะไรเลย

        try {
            Clip clip;
            
            // 1. เช็คว่าเคยโหลดเสียงนี้หรือยัง?
            if (sfxCache.containsKey(filePath)) {
                clip = sfxCache.get(filePath);
                
                // ถ้าเสียงกำลังเล่นอยู่ ให้หยุดและกรอกลับไปจุดเริ่มต้น
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0); // รีเซ็ตไปวิที่ 0
                
            } else {
                // 2. ถ้ายังไม่เคยโหลด ให้โหลดใหม่จากไฟล์
                File soundFile = new File(filePath);
                if (!soundFile.exists()) {
                    System.err.println("❌ SFX not found: " + filePath);
                    return;
                }
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                // เก็บลง Cache ไว้ใช้รอบหน้า (จะได้ไม่แลค)
                sfxCache.put(filePath, clip);
            }

            // 3. เล่นเสียง
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // เปิด/ปิด เสียง Effect
    public void setMute(boolean mute) {
        this.isMuted = mute;
        // SFX ปกติเล่นแล้วจบเลย ไม่จำเป็นต้องไปสั่ง stop ตัวที่เล่นค้างอยู่ก็ได้
        // แต่ถ้าอยากให้เงียบกริบทันทีที่ติ๊ก ก็เพิ่มโค้ดวนลูป stop ใน sfxCache.values() ได้ครับ
    }

    public boolean isMuted() {
        return isMuted;
    }
}