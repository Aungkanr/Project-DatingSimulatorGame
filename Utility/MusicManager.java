package Utility;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl; // 1. เพิ่ม Import นี้

public class MusicManager {
    private Clip clip;
    private boolean isMuted = false;
    private String currentMusicPath = ""; 
    
    // 2. เพิ่มตัวแปรเก็บระดับเสียง (0.0 ถึง 1.0)
    private float currentVolume = 0.5f; 

    public void playMusic(String filePath) {
        // ... (โค้ดส่วนนี้เหมือนเดิม ไม่ต้องแก้) ...
        try {
            if (filePath.equals(currentMusicPath) && clip != null) {
                if (!isMuted && !clip.isRunning()) {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                return; 
            }

            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                currentMusicPath = filePath; 

                // 3. เพิ่มบรรทัดนี้: ตั้งค่าเสียงเริ่มต้น
                updateVolume(); 
                
                applyMute(); 

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

    // 4. เพิ่มฟังก์ชัน setVolume และ updateVolume
    public void setVolume(float volume) {
        if (volume < 0.0f) volume = 0.0f;
        if (volume > 1.0f) volume = 1.0f;
        this.currentVolume = volume;
        updateVolume();
    }

    public float getVolume() {
        return currentVolume;
    }

    private void updateVolume() {
        if (clip == null) return;
        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                
                if (isMuted || currentVolume <= 0.0f) {
                    gainControl.setValue(gainControl.getMinimum());
                } else {
                    // แปลง Scale 0-1 เป็น Decibel (สูตรมาตรฐาน)
                    float dB = (float) (Math.log10(currentVolume) * 20.0);
                    gainControl.setValue(dB);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMute(boolean mute) {
        this.isMuted = mute;
        applyMute();
        updateVolume(); // อัปเดต Gain ตอนกด Mute ด้วย
    }
    
    // ... (ส่วนอื่นๆ เหมือนเดิม) ...
    public boolean isMuted() { return isMuted; }

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