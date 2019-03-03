import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("time-a.nist.gov", 1999);
            Scanner in = new Scanner(socket.getInputStream(), "UTF-8");
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
