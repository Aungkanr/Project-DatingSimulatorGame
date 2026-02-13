package Player;
public class Player {
    private int energy;
    private int money;

    // Constructor 
    public Player() {
        this.energy = 100; // เริ่มต้น 100
        this.money = 500;  // เริ่มต้น 500 บาท
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
}