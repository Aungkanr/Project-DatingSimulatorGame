package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;

public class AssetManager {

    // --- Singleton Pattern ---
    private static AssetManager instance = new AssetManager();

    // Using ConcurrentHashMap for thread-safe parallel loading
    private Map<String, ImageIcon> imageCache;
    private Map<String, Clip> soundCache;

    private AssetManager() {
        imageCache = new ConcurrentHashMap<>();
        soundCache = new ConcurrentHashMap<>();
    }

    public static AssetManager getInstance() {
        return instance;
    }

    // ==========================================
    // Parallel Folder Loading
    // ==========================================
    public void loadFolderParallel(String folderPath) {
        System.out.println("Starting parallel load for folder: " + folderPath);
        long startTime = System.currentTimeMillis();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                .parallel() // Use all available CPU cores
                .filter(Files::isRegularFile) // Ignore directories
                .forEach(path -> {
                    String filePath = path.toString();
                    String lowerPath = filePath.toLowerCase();

                    // Load based on file extension
                    if (lowerPath.endsWith(".png") || lowerPath.endsWith(".jpg") || lowerPath.endsWith(".jpeg")) {
                        getImage(filePath);
                    } else if (lowerPath.endsWith(".wav")) {
                        getSound(filePath);
                    }
                });
                
            long endTime = System.currentTimeMillis();
            System.out.println("Successfully loaded folder: " + folderPath + " | Time taken: " + (endTime - startTime) + " ms");

        } catch (IOException e) {
            System.err.println("Failed to access or open folder: " + folderPath);
            e.printStackTrace();
        }
    }

    // ==========================================
    // Image Management
    // ==========================================
    public ImageIcon getImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        try {
            File f = new File(path);
            if (!f.exists()) {
                System.err.println("! Image not found: " + path);
                return null;
            }
            BufferedImage img = ImageIO.read(f);
            ImageIcon icon = new ImageIcon(img);

            imageCache.put(path, icon);
            return icon;

        } catch (IOException e) {
            System.err.println("! Error reading image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================
    // Sound Management
    // ==========================================
    public Clip getSound(String path) {
        if (soundCache.containsKey(path)) {
            return soundCache.get(path);
        }

        try {
            File f = new File(path);
            if (!f.exists()) {
                System.err.println("! Sound not found: " + path);
                return null;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            soundCache.put(path, clip);
            return clip;

        } catch (Exception e) {
            System.err.println("! Error reading sound: " + path);
            e.printStackTrace();
            return null;
        }
    }

    // ==========================================
    // Memory Management
    // ==========================================
    public void clearCache() {
        imageCache.clear();
        for (Clip clip : soundCache.values()) {
            if (clip.isRunning()) clip.stop();
            clip.close();
        }
        soundCache.clear();
        System.out.println("Memory Cleared!");
    }

    // ==========================================
    // Placeholder Methods
    // ==========================================
    public ImageIcon getScaledImage(String fullPath, int i, int j) {
        throw new UnsupportedOperationException("Unimplemented method 'getScaledImage'");
    }
}