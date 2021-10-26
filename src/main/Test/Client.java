import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        new Thread(new ClientRec()).start();
        new Thread(new ClientSend()).start();
        System.out.println("客户端开启成功...");

    }
}
