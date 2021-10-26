import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ServerSend implements Runnable{
    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("服务端发送：");
                String data = sc.nextLine();
                byte[] datas = data.getBytes();
                DatagramPacket packet = new DatagramPacket(datas, 0,
                        datas.length, InetAddress.getByName("localhost"), 9091);
                socket.send(packet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
