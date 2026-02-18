package Player;
import Relationship.*; 
public class Player {
    private int energy;
    private int money;
    private Inventory inventory;

    private Lazel lazel;
    // private Arwen arwen;     //X
    private Galadriel galadriel; 

    // Constructor 
    public Player() {
        this.energy = 100; // เริ่มต้น 100
        this.money = 100;  // เริ่มต้น 500 บาท
        this.inventory = new Inventory() ; //create obj bag

        this.lazel = new Lazel();
        // this.arwen = new Arwen();//X
        this.galadriel = new Galadriel();
    }   
    //------------------------ (Getters & Setter)------------------------
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy > 100) this.energy = 100;
        else if (energy < 0) this.energy = 0;
        else this.energy = energy;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Lazel getLazel() { return lazel; }
    //public Arwen getArwen() { return arwen; } //X
    public Galadriel galadriel() { return galadriel ;} 

    
    //-----------------------------เน้นใช้ ------------------------
    public void decreaseEnergy(int amount) {
        setEnergy(this.energy - amount);
    }
    public void increaseEnergy(int amount) {
        setEnergy(this.energy + amount);
    }
    public void decreaseMoney(int amount) {
        this.money -= amount;
    }
    public void increaseMoney(int amount) {
        this.money += amount;
    }

    public boolean buyItem(String itemName, int price) {
        if (this.money >= price) {
            this.money -= price;      
            inventory.addItem(itemName); // method เพิ่มของเข้ากระเป๋า
            return true;
        } else {
            return false; 
        }
    }
    // เพิ่ม Method รีเซ็ตของขวัญประจำวัน 
    public void resetDailyRelationships() {
        lazel.resetDaily();
        // arwen.resetDaily();//X
        galadriel.resetDaily();
    }

    //-----------------------------------------------
}