package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * network communication framework: Raw socket
 * multithreading framework: Executor + Runnable(task) + Thread pool(workers)
 *
 * socket communication: byte based -> to string, buffered -> to flush
 *
 * tools: netstat -an | grep 9118; nc localhost 9118; telnet localhost 9118
 */

public class ChatServer {
    // keeps all online users' names, add when user login, remove when user logout
    private static Set<String> names = new HashSet<String>();

    // keep all online users' outputStream to broadcast, add when user login, remove when user logout
    private static Set<PrintWriter> printWriters = new HashSet<PrintWriter>();

    public static void main(String[] args) {
        // create a server socket listening at port 9118
        try (var serverSocket = new ServerSocket(9118)) {           // try-with-resource, avoid explicit close
            System.out.println("Listening at port: " + serverSocket.getLocalPort() + "...");

            int i = 1;

            // thread pool(workers) and executor
            ExecutorService executor = Executors.newFixedThreadPool(20);

            while (true) {
                // wait and accept a client socket
                var clientSocket = serverSocket.accept();                 // clientSocket is not finished in this block, so cannot use try-with-resource here
                System.out.println("Spawned: " + i + ", " + new Date().toString());

                executor.execute(new RunnableTask(clientSocket));

                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    // private static inner class
    private static class RunnableTask implements Runnable {
        private Socket socket;
        private String name;
        private Scanner in;
        private PrintWriter out;

        // called from the server's main thread, so do as little as possible
        public RunnableTask(Socket client) {
            this.socket = client;
        }

        // cannot add throws here
        @Override
        public void run() {
            try {
                // inputStream(byte based) -> Scanner(char based, handy for string op, auto decode and encode)
                in = new Scanner(socket.getInputStream(), "UTF-8");
                // auto flush the socket buffer for every print
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

                // keep querying user's name until a valid name accepted
                while(true) {
                    out.println("Enter your name: ");
                    name = in.nextLine();
                    if (name == null) return;

                    synchronized (names) {
                        if (!name.isBlank() && !names.contains(name)) {
                            names.add(name);
                            out.println("Welcome " + name + ", " + new Date());
                            break;
                        }
                    }
                }

                // broadcast all other users and add user to the broadcast set
                for (var writer : printWriters) {
                    writer.println("MESSAGE " + name + " has joined in!");
                }
                printWriters.add(out);

                // if we use a flag instead just break, then judge situation:
                // 1. in.hasNextLine() && !finished -> even if we type "bye"(finished -> true), it will judge in first, so we need to type another string first
                // 2. !finished && in.hasNextLine() -> exit immediately after we type "bye"(finished -> true)
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    // trim leading and trailing space
                    if (line.trim().equalsIgnoreCase("/quit")) break;
                    for (var printWriter : printWriters) {
                        printWriter.println("MESSAGE " + name + ": " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) printWriters.remove(out);
                if (name != null) {
                    names.remove(name);
                    for (var writer : printWriters)
                        writer.println("MESSAGE " + name + " has left");
                }
                try {
                    socket.close();             // socket is created on main thread, so try-with-resource cannot be used here
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
