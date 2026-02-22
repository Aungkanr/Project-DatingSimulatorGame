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
    public Clip getSound(String path) {
        // 1. เช็คว่ามีของในโกดัง (Cache) หรือยัง?
        if (soundCache.containsKey(path)) {
            return soundCache.get(path); // เจอแล้ว! ส่ง Clip ไปให้เลย
        }

        // 2. ถ้าไม่มี ให้ไปโหลดจากไฟล์ (Load from Disk)
        try {
            File f = new File(path);
            if (!f.exists()) {
                System.err.println("!Sound not found: " + path);
                return null;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            // 3. เก็บเข้าโกดัง (Cache)
            soundCache.put(path, clip);
            System.out.println("Loaded Sound to Memory: " + path);

            return clip; // ส่ง Clip ที่เพิ่งโหลดเสร็จกลับไป

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public ImageIcon getScaledImage(String fullPath, int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScaledImage'");
    }
}