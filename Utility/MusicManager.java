package Utility;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicManager {
    private Clip clip ;
    private boolean isMuted = false;
    
    // 1. เพิ่มตัวแปรเก็บชื่อเพลงล่าสุด
    private String currentMusicPath = ""; 

    public void playMusic(String filePath) {
        clip = AssetManager.getInstance().getSound(filePath);
        try {
            // -----------------------------------------------------------
            // 2. เช็คว่าเพลงที่จะเปิด คือเพลงเดิมที่เล่นอยู่ไหม?
            // -----------------------------------------------------------
            if (filePath.equals(currentMusicPath) && clip != null) {
                // ถ้าเป็นเพลงเดิม และ clip ยังอยู่
                if (!isMuted && !clip.isRunning()) {
                    // ถ้าเสียงไม่ปิด แต่เพลงหยุดอยู่ -> ให้เล่นต่อ
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                // ถ้าเพลงกำลังเล่นอยู่แล้ว -> ไม่ต้องทำอะไรเลย (return ออกไปเลย)
                return; 
            }

            // -----------------------------------------------------------
            // ถ้าไม่ใช่เพลงเดิม หรือเพิ่งเริ่มเล่นครั้งแรก ให้โหลดใหม่ตามปกติ
            // -----------------------------------------------------------
            
            // หยุดเพลงเก่า
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                // อัปเดตชื่อเพลงปัจจุบัน
                currentMusicPath = filePath; 

                // เช็ค Mute ก่อนเล่น
                applyMute(); 

                // ถ้าไม่ได้ Mute ให้เล่นเลย
                if (!isMuted) {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            } else {
                System.out.println("Can't find file: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMute(boolean mute) {
        this.isMuted = mute;
        applyMute();
    }

    public boolean isMuted() {
        return isMuted;
    }

    private void applyMute() {
        if (clip == null) return;
        
        if (isMuted) {
            if (clip.isRunning()) clip.stop();
        } else {
            if (!clip.isRunning()) {
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
}