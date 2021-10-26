import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        new Thread(new ServerRec()).start();
        new Thread(new ServerSend()).start();
        System.out.println("服务器开启成功...");
    }
}
