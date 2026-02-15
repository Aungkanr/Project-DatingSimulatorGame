package Utility;

import Player.Player;

public class GameTime {
    private int day ; 
    private int timeSlot ; // 0 = Morning, 1 = Noon, 2 = Afternoon, 3 = Night
    public static final String[] TimeText = {"Morning", "Noon", "Afternoon", "Night"}; //ห้ามแก้

    public GameTime() { //construct ลอง configure ค่าเริ่มต้นได้ (อยากลอง cheat)
        this.day = 1;      
        this.timeSlot = 0; 
    }

    public void resetTime() {
        this.day = 1 ;
        this.timeSlot = 0 ; 
    }

    public void advanceTime(Player player, int energyCost) {
        if (this.timeSlot < 3) {
            this.timeSlot++;
            // ลด energy ผ่าน object player ที่ส่งเข้ามา
            player.decreaseEnergy(energyCost); 
        } else {
            timeSlot = 3; // ค้างที่ Night
        }
    }

    public void nextDay () {
        this.day++ ;
        this.timeSlot = 0 ; // reset to Moring 
    } 

    // ------------------ Getters ------------------------
    public int getDay() {
        return day;
    }

    public String getTimeString() {
        return TimeText[timeSlot];
    }
    
    public int getTimeSlot() {
        return timeSlot;
    }
    
    // เช็คว่าเป็นตอนกลางคืนไหม 
    public boolean isNight_Afternoon() {
        return (timeSlot >= 2) && (timeSlot <=3) ;
    }
}
