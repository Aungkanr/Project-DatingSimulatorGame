package Coop.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 9999; // กำหนดช่องทาง (Port) ที่จะใช้คุยกัน

        System.out.println("🟢 Server is starting...");
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("🏠 Server is open on port " + port + ". Waiting for player 2...");

            // คำสั่ง accept() จะทำให้โปรแกรม "หยุดรอ" จนกว่าจะมีคนทักเข้ามา
            Socket clientSocket = serverSocket.accept(); 
            System.out.println("✅ Player 2 connected from: " + clientSocket.getInetAddress());

            // ตัวอ่านข้อมูลที่ Client ส่งมาให้
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            
            // ลูปอ่านข้อความที่ส่งมาเรื่อยๆ
            while ((message = in.readLine()) != null) {
                System.out.println("💬 Player 2 says: " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}