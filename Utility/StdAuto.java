package Utility;

import java.awt.Dimension;
import java.awt.Toolkit;

public class StdAuto { //standard Auto space  
     
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int width = (int) screenSize.getWidth();
    public int height = (int) screenSize.getHeight();

    public int buttonWidth = 300;
    public int buttonHeight = 60;
    public int gap = 20;
    //เว้น auto + center  
    public int centerX = (width - buttonWidth) / 2;
    public int totalContentHeight = (buttonHeight * 4) + (gap * 3); 
    public int currentY = (height - totalContentHeight) / 2;

}
