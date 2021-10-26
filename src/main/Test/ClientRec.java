import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class ClientRec implements Runnable{
    @Override
    public void run() {

        try {
            byte buffer[]= new byte[1024];
            DatagramSocket socket = new DatagramSocket(9091);
            while (true){
                Arrays.fill(buffer,(byte)0);
                DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
                socket.receive(packet);
                System.out.println("客户端接受："+new String(packet.getData()));
                System.out.println("客户端发送");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
