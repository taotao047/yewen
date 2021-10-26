import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientSend implements Runnable {
    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();


            Scanner sc = new Scanner(System.in);
            while (true) {
                String data = sc.nextLine();
                byte[] datas = data.getBytes();
                System.out.println("客户端发送：");
                DatagramPacket packet = new DatagramPacket(datas, 0,
                        datas.length, InetAddress.getByName("localhost"), 9090);
                socket.send(packet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
