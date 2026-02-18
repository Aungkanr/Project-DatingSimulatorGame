package Utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl; // Import ตัวนี้เพิ่ม

public class SFXManager {
    
    // map เก็บเสียงที่เคยโหลดแล้ว
    private Map<String, Clip> sfxCache = new HashMap<>();
    private boolean isMuted = false;
    
    // เก็บระดับเสียงปัจจุบัน (0.0f ถึง 1.0f) Default = 1.0f (ดังสุด)
    private float currentVolume = 1.0f; 

    // สั่งเล่นเสียง Effect
    public void playSFX(String filePath) {
        if (isMuted) return; 

        try {
            Clip clip;
            
            // 1. เช็ค Cache
            if (sfxCache.containsKey(filePath)) {
                clip = sfxCache.get(filePath);
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0); 
                
            } else {
                // 2. โหลดใหม่
                File soundFile = new File(filePath);
                if (!soundFile.exists()) {
                    System.err.println("❌ SFX not found: " + filePath);
                    return;
                }
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                sfxCache.put(filePath, clip);
            }

            // [สำคัญ] 3. ปรับระดับเสียงก่อนเล่นเสมอ
            updateClipVolume(clip);

            // 4. เล่นเสียง
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- ฟังก์ชันปรับระดับเสียง (0.0f - 1.0f) ---
    public void setVolume(float volume) {
        // กันค่าไม่ให้เกินขอบเขต
        if (volume < 0.0f) volume = 0.0f;
        if (volume > 1.0f) volume = 1.0f;
        
        this.currentVolume = volume;

        // อัปเดตเสียงให้กับทุก Clip ที่อยู่ใน Cache ทันที
        for (Clip clip : sfxCache.values()) {
            updateClipVolume(clip);
        }
    }

    // Helper: คำนวณและยัดค่า dB ใส่ Clip
    private void updateClipVolume(Clip clip) {
        try {
            // เช็คว่า Clip นี้รองรับการปรับเสียงไหม
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                
                // ถ้าปิดเสียง หรือ Volume เป็น 0 ให้ลดเสียงต่ำสุด
                if (isMuted || currentVolume <= 0.0f) {
                     gainControl.setValue(gainControl.getMinimum()); // -80.0 dB
                } else {
                    // สูตรแปลง 0.0-1.0 เป็น Decibel (Logarithmic)
                    // Math.log10(volume) จะได้ค่าติดลบ เมื่อคูณ 20 จะได้ค่า dB ที่ถูกต้อง
                    float dB = (float) (Math.log10(currentVolume) * 20.0);
                    gainControl.setValue(dB);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // เปิด/ปิด เสียง (Mute)
    public void setMute(boolean mute) {
        this.isMuted = mute;
        // เมื่อกด Mute ให้อัปเดตระดับเสียงทุกตัวทันที
        for (Clip clip : sfxCache.values()) {
            updateClipVolume(clip);
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
    
    // ดึงค่าความดังปัจจุบัน (เผื่อเอาไปโชว์ใน Slider)
    public float getVolume() {
        return currentVolume;
    }
}