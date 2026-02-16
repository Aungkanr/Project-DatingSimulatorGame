package Utility;

//import java.awt.Dimension;
//import java.awt.Toolkit;

public class StdAuto { 
      
    // [มาตรฐานสากล] ล็อคขนาดจอ 1280x720 (HD)
    public int width = 1600;
    public int height = 900;

    // สร้าง bounds ไว้กัน error (เผื่อ MainFrame เรียกใช้)
    public static java.awt.Rectangle bounds = new java.awt.Rectangle(0, 0, 1280, 720);

    public int buttonWidth;
    public int buttonHeight;
    public int gap;
    public int amount; 

    public int centerX;
    public int totalContentHeight;
    public int currentY;
    public int bottomY; // ตำแหน่งปุ่มด้านล่าง

    public void setBtnWHG(int buttonWidth, int buttonHeight, int gap, int amount) { 
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight; 
        this.gap = gap;
        this.amount = amount;

        this.centerX = (width - buttonWidth) / 2;

        if (amount > 0) {
            this.totalContentHeight = (buttonHeight * amount) + (gap * (amount - 1));
        } else {
            this.totalContentHeight = 0;
        }

        this.currentY = (height - totalContentHeight) / 2; 
        // [สูตรจัดปุ่มล่าง] ให้ลอยขึ้นมาจากขอบล่าง 120px (ไม่ตกขอบแน่นอน)
        this.bottomY = height - buttonHeight - 120;
    }
}

