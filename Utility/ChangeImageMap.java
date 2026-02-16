package Utility;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChangeImageMap {

    /**
     * อัปเดตรูป Map ตามเวลา
     * @param timeString เวลา เช่น "Morning", "Afternoon", "Evening", "Night"
     * @param lblMap JLabel ที่จะแสดงรูป
     * @param checkImageUtil Utility สำหรับ check image
     * @param screenWidth ความกว้างหน้าจอ
     * @param screenHeight ความสูงหน้าจอ
     */
    public static void updateMapImage(String timeString, JLabel lblMap, CheckImage checkImageUtil, int screenWidth, int screenHeight) {
        String imagePath = "";
        
        switch (timeString) {
            case "Morning":
                imagePath = "image\\Map\\Morning.png";
                break;
            case "Noon":
                imagePath = "image\\Map\\Afternoon.png";
                break;
            case "Evening":
                imagePath = "image\\Map\\Evening.png";
                break;
            case "Night":
                imagePath = "image\\Map\\Night.png";
                break;
            default:
                imagePath = "image\\Map\\Morning.pn"; // ค่า default
                break;
        }
        
        ImageIcon originalIcon = new ImageIcon(imagePath);
        checkImageUtil.checkImage(originalIcon, lblMap, screenWidth, screenHeight);
        lblMap.repaint(); // วาดใหม่
    }
    
    /**
     * เวอร์ชันที่รับ StdAuto แทน width/height แยก
     */
    public static void updateMapImage(String timeString, JLabel lblMap, CheckImage checkImageUtil, StdAuto stdScreen) {
        updateMapImage(timeString, lblMap, checkImageUtil,stdScreen.width, stdScreen.height);
    }
}