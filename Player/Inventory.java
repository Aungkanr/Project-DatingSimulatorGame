package Player;

import java.util.HashMap;

public class Inventory { //กระเป๋า player ex {teddy bear , 2}  item , amount
    //Keys are unique, but Values can be duplicated
    private HashMap<String, Integer> items ; 

    public Inventory () { // construct 
        this.items = new HashMap<>() ;
    } 
//-----------------function -------------------
    public void addItem (String itemName) {
        if (this.items.containsKey(itemName)) { // ถ้ามีแล้ว บวกเพิ่ม + 1 
            this.items.put(itemName, items.get(itemName) + 1 ) ; //add  
        } else this.items.put(itemName, 1) ;

        System.out.println("Added " + itemName + " to inventory."); // check in Console
    }
    //check ว่า item นั้นมีใน bag ไหม 
    public boolean hasItem (String itemName) {
        return this.items.containsKey(itemName) && items.get(itemName) > 0 ; 
    } 
    //check amount สินค้า 
    public int getItemCount(String itemName) {
        return items.getOrDefault(itemName, 0);
    }
    // แสดงรายการของทั้งหมดเป็น String (เอาไว้โชว์) เปิด Bag 
    public String getAllItemsList() {
        if (items.isEmpty()) return "Empty";
        StringBuilder list = new StringBuilder();
        for (String key : items.keySet()) {
            list.append(key).append(" x").append(items.get(key)).append("\n");
        }
        return list.toString();
    }

}   
