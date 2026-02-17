package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;

public class AssetManager {

    // --- Singleton Pattern (สร้างตัวเดียวใช้ทั้งเกม) ---
    private static AssetManager instance;

    // ระบบ Cache:
    // String (Key) = path ของไฟล์ (เช่น "image/bg.png")
    // Value = ข้อมูลที่โหลดลง Ram แล้ว
    private Map<String, ImageIcon> imageCache;
    private Map<String, Clip> soundCache;

    // Private Constructor (ห้ามใคร new เล่น)
    private AssetManager() {
        imageCache = new HashMap<>();
        soundCache = new HashMap<>();
    }

    // วิธีเรียกใช้: AssetManager.getInstance()
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    // ==========================================
    // ส่วนจัดการรูปภาพ (Images)
    // ==========================================
    public ImageIcon getImage(String path) {
        // 1. เช็คว่าเคยโหลดรูปนี้หรือยัง?
        if (imageCache.containsKey(path)) {
            return imageCache.get(path); // ส่งของที่มีใน Ram กลับไปเลย (เร็วมาก)
        }

        // 2. ถ้ายังไม่เคย ให้โหลดจากไฟล์
        try {
            File f = new File(path);
            if (!f.exists()) {
                System.err.println("!Image not found: " + path);
                return null;
            }
            // โหลดรูปภาพ
            BufferedImage img = ImageIO.read(f);
            ImageIcon icon = new ImageIcon(img);

            // 3. เก็บลง Cache ไว้ใช้ครั้งหน้า
            imageCache.put(path, icon);
            
            System.out.println("Loaded Image to Memory: " + path);
            return icon;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================
    // ส่วนจัดการเสียง (Sounds)
    // ==========================================
    public void playSound(String path, boolean loop) {
        try {
            Clip clip;
            
            // 1. เช็คว่าเคยโหลดเสียงนี้หรือยัง
            if (soundCache.containsKey(path)) {
                clip = soundCache.get(path);
                
                // ถ้าเสียงกำลังเล่นอยู่ ให้หยุดก่อน (หรือจะประยุกต์ให้เล่นซ้อนก็ได้)
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0); // กรอเทปกลับไปจุดเริ่มต้น

            } else {
                // 2. โหลดใหม่ถ้ายังไม่มีใน Cache
                File f = new File(path);
                if (!f.exists()) {
                    System.err.println("!Sound not found: " + path);
                    return;
                }
                
                AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                clip = AudioSystem.getClip();
                clip.open(ais);

                // 3. เก็บลง Cache
                soundCache.put(path, clip);
                System.out.println("Loaded Sound to Memory: " + path);
            }

            // 4. สั่งเล่น
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // สั่งหยุดเสียง (ใช้สำหรับ BGM)
    public void stopSound(String path) {
        if (soundCache.containsKey(path)) {
            Clip clip = soundCache.get(path);
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    // สั่งเคลียร์ RAM (เช่น ตอนปิดเกม หรือเปลี่ยนด่านใหญ่ๆ)
    public void clearCache() {
        imageCache.clear();
        for (Clip clip : soundCache.values()) {
            if (clip.isRunning()) clip.stop();
            clip.close();
        }
        soundCache.clear();
        System.out.println("🧹 Memory Cleared!");
    }
}