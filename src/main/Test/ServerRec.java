import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class ServerRec implements Runnable{
    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(9090);
            byte [] buffer = new byte[1024];
            while (true){
                Arrays.fill(buffer,(byte)0);
                DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
                socket.receive(packet);
                System.out.println("服务端接受:"+new String(packet.getData()));
                System.out.println("服务端发送：");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
