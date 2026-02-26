package Coop.Network;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        // 📌 ถ้าเทสในคอมเครื่องเดียวกัน ให้ใช้ "localhost"
        // 📌 ถ้าเล่นกับเพื่อนผ่าน LAN (Wi-Fi เดียวกัน) ให้เปลี่ยนเป็นเลข IP ของเพื่อน เช่น "192.168.1.45"
        String serverIP = "localhost"; 
        int port = 9999;

        try (Socket socket = new Socket(serverIP, port)) {
            System.out.println("✅ Successfully connected to Server!");

            // ตัวส่งข้อมูลไปหา Server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("⌨️ Type a message and press Enter (Type 'exit' to quit):");
            
            // ลูปเปิดให้พิมพ์ข้อความส่งไปเรื่อยๆ
            while (true) {
                String text = scanner.nextLine();
                if ("exit".equalsIgnoreCase(text)) break;
                
                out.println(text); // ส่งข้อความพุ่งปรี๊ดไปหา Server!
            }

        } catch (Exception e) {
            System.out.println("❌ Could not connect to Server. Is the Server running?");
        }
    }
}