package Network;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private final static int port = 9988;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.configureBlocking(false);
        serverSocket.socket().bind(new InetSocketAddress(port), 1024);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server listening on port " + port);

        while (true) {
            selector.select(1000);

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    handleAccept(key);
                }

                if (key.isReadable()) {
                    echo(key);
                }

                iterator.remove();
            }

        }

    }

    public static void handleAccept(SelectionKey key) throws IOException {
        SocketChannel client = ((ServerSocketChannel)key.channel()).accept();
        client.configureBlocking(false);
        client.register(key.selector(), SelectionKey.OP_READ);
        System.out.println("Client connected: " + client);
    }

    public static void echo(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        SocketChannel client = (SocketChannel) key.channel();

        client.read(buffer);
        if (new String(buffer.array()).equals("quit")) {
            client.close();
            System.out.println("Client quit: " + client);
        }

        buffer.flip();

        client.write(buffer);
        buffer.clear();
    }

}
