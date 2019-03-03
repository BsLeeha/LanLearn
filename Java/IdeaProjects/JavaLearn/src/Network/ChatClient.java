package Network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static Boolean isClosed = false;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9118);

            Scanner inServer = new Scanner(socket.getInputStream());
            PrintWriter outServer = new PrintWriter(socket.getOutputStream(), true);
            Scanner inKey = new Scanner(System.in);


            new Thread(new UserHandler(socket, inKey, outServer)).start();

            new Thread(new ServerHandler(inServer)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static class ServerHandler implements Runnable {

        private Scanner in;

        public ServerHandler(Scanner in) {
            this.in = in;
        }

        @Override
        public void run() {
            while (true) {
                if (in.hasNextLine())
                    System.out.println(in.nextLine());

                synchronized (isClosed) {
                    if (isClosed) break;
                }
            }

            in.close();
        }
    }

    public static class UserHandler implements Runnable {

        private Socket socket;
        private final Scanner in;
        private final PrintWriter out;

        public UserHandler(Socket socket, Scanner in, PrintWriter out) {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            String line;
            while (true) {
                    line = in.nextLine();

                    if ("quit".equals(line)) break;
                    else out.println(line);
            }

            synchronized (isClosed) {
                isClosed = true;
            }

            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
