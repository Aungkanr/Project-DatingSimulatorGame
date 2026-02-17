package UXUI.Scene;

import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import UXUI.MainFrame;
import UXUI.StatusBarMenu.Story;

public class SchoolPanel extends JPanel {

    private MainFrame mainFrame;
    private JLayeredPane layeredPane;

    // ดึง data จาก Story
    private final String[][] dialogues  = Story.LAZEL_CH1;
    private final int[]      mainScenes = Story.LAZEL_CH1_MAIN_SCENES;

    // ===============================================================
    // Constructor
    // ===============================================================
    public SchoolPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        this.add(layeredPane, BorderLayout.CENTER);

        showScene(0);
    }

    // ===============================================================
    // แสดง scene ตาม index จาก Story
    // ===============================================================
    private void showScene(int index) {
        String[] data = dialogues[index];
        SceneUpdate scene;

        if (data.length == 1) {
            // Reaction scene: มีแค่ปุ่ม "ต่อไป"
            int next = getNextMainScene(index);
            scene = new SceneUpdate(
                "image\\Scene\\School\\Angryscene.png",
                "Lazel", data[0], null,
                next >= 0
                    ? new SceneUpdate.SceneOption("ต่อไป", e -> showScene(next))
                    : new SceneUpdate.SceneOption("จบ Chapter 1", e -> endChapter1())
            );
        } else {
            // Choice scene: มีปุ่ม A B C
            scene = new SceneUpdate(
                "image\\Scene\\School\\Angryscene.png",
                "Lazel", data[0], null,
                new SceneUpdate.SceneOption(data[1], e -> showScene(index + 1)), // A
                new SceneUpdate.SceneOption(data[2], e -> showScene(index + 2)), // B
                new SceneUpdate.SceneOption(data[3], e -> showScene(index + 3))  // C
            );
        }

        changeScene(scene);
    }

    // หา scene หลักถัดไปหลังจาก reaction จบ
    private int getNextMainScene(int reactionIndex) {
        for (int i = 0; i < mainScenes.length - 1; i++) {
            if (reactionIndex > mainScenes[i] && reactionIndex < mainScenes[i + 1]) {
                return mainScenes[i + 1];
            }
        }
        return -1; // จบ chapter แล้ว
    }

    private void changeScene(SceneUpdate scene) {
        SwingUtilities.invokeLater(() -> {
            for (java.awt.Component c : layeredPane.getComponents()) {
                layeredPane.remove(c);
            }
            scene.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            layeredPane.add(scene, JLayeredPane.DEFAULT_LAYER);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    private void endChapter1() {
        SwingUtilities.invokeLater(() -> mainFrame.showGame());
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        if (layeredPane != null) {
            layeredPane.setBounds(0, 0, w, h);
            for (java.awt.Component c : layeredPane.getComponents()) {
                c.setBounds(0, 0, w, h);
            }
        }
    }
}