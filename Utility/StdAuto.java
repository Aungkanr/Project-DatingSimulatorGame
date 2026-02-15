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

/* 
public class StdAuto { 
     
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int width = (int) screenSize.getWidth();
    public int height = (int) screenSize.getHeight();

    public int buttonWidth;
    public int buttonHeight;
    public int gap;
    public int amount; // จำนวนปุ่ม/จำนวนแถว

    public int centerX;
    public int totalContentHeight;
    public int currentY;

    // [เพิ่มใหม่] ตัวแปรสำหรับตำแหน่งปุ่มด้านล่าง
    public int bottomY;

    public void setBtnWHG(int buttonWidth, int buttonHeight, int gap, int amount) { 
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight; 
        this.gap = gap;
        this.amount = amount;

        this.centerX = (width - buttonWidth) / 2;

        // หาความสูงรวมของเนื้อหาทั้งหมด (ปุ่มทุกอัน + ช่องว่าง)
        if (amount > 0) {
            this.totalContentHeight = (buttonHeight * amount) + (gap * (amount - 1));
        } else {
            this.totalContentHeight = 0;
        }

        // หาจุดเริ่ม Y เพื่อให้เนื้อหาทั้งหมดอยู่กลางจอ (บน-ล่าง)
        // สูตร: (ความสูงจอ - ความสูงเนื้อหารวม) / 2
        this.currentY = (height - totalContentHeight) / 2; 

        // [เพิ่มใหม่] คำนวณตำแหน่งปุ่มให้ลอยเหนือขอบล่างขึ้นมา 120px 
        // (ค่า 120 คือ Safe Zone เพื่อไม่ให้ปุ่มโดน Taskbar บัง หรือจมหายไป)
        this.bottomY = height - buttonHeight - 100;
    }
}
*/
