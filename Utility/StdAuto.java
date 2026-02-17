package Utility;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public class StdAuto { 
      
    // 1. หาขนาดหน้าจอที่ใช้งานได้จริง (พื้นที่ที่มีอยู่)
    private static Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

    // 2. กำหนดขนาด "เป้าหมาย" ที่คุณอยากได้ (ใช้ double เพื่อการคำนวณที่แม่นยำ)
    private double targetWidth = 1550.0;
    private double targetHeight = 1000.0;

    // ============================================================
    //  โซนคำนวณรักษาสัดส่วน (Keep Aspect Ratio) - แก้ภาพยืด
    // ============================================================
    private double calculateScaleFactor() {
        // หาอัตราส่วนของจอจริง เทียบกับ เป้าหมาย ในแต่ละด้าน
        // เช่น จอจริงกว้าง 1200 แต่เป้าคือ 1550 -> ratioW จะได้ประมาณ 0.77
        double ratioW = screenBounds.width / targetWidth;
        double ratioH = screenBounds.height / targetHeight;

        // [หัวใจสำคัญ] เลือกใช้อัตราส่วนที่ "น้อยที่สุด"
        // เพื่อให้แน่ใจว่าเมื่อย่อแล้ว ภาพจะลงล็อคทั้งความกว้างและความสูง โดยไม่ล้น
        double minRatio = Math.min(ratioW, ratioH);

        // แต่ถ้าจอใหญ่มาก เราจะไม่ขยายเกิน 100% (ไม่เกิน 1.0) เดี๋ยวภาพแตก
        return Math.min(minRatio, 1.0); 
    }

    // คำนวณตัวคูณ final (เช่น ได้ 0.8 คือย่อเหลือ 80%)
    private double finalScale = calculateScaleFactor();

    // 3. เอาตัวคูณไปคูณกับขนาดเป้าหมาย
    // ผลลัพธ์: กว้างและสูงจะถูกย่อลงด้วยอัตราส่วนเดียวกันเป๊ะ ภาพจึงไม่ยืด
    public int width = (int)(targetWidth * finalScale);
    public int height = (int)(targetHeight * finalScale);
    // ============================================================


    // สร้าง bounds ไว้ให้ MainFrame เรียกใช้ (มันจะมาอ่านค่า width/height ที่เราคำนวณไว้ด้านบน)
    public static Rectangle bounds = new Rectangle(0, 0, 0, 0); 

    public int buttonWidth;
    public int buttonHeight;
    public int gap;
    public int amount; 

    public int centerX;
    public int totalContentHeight;
    public int currentY;
    public int bottomY; 

    public void setBtnWHG(int buttonWidth, int buttonHeight, int gap, int amount) { 
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight; 
        this.gap = gap;
        this.amount = amount;

        // คำนวณจุดกึ่งกลางจาก width ที่เราคำนวณมาใหม่
        this.centerX = (width - buttonWidth) / 2;

        if (amount > 0) {
            this.totalContentHeight = (buttonHeight * amount) + (gap * (amount - 1));
        } else {
            this.totalContentHeight = 0;
        }

        this.currentY = (height - totalContentHeight) / 2; 

        // [สูตรปุ่มลอย] ใช้ 15% ของความสูงใหม่ที่คำนวณได้
        this.bottomY = height - buttonHeight - (int)(height * 0.15);
    }
}