import java.io.*;
import java.util.Scanner;

class ConsoleOp {
    void consoleReadChar() throws IOException {         // read throws, so we handle it by throwing it too
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char c;
        System.out.println("Please enter chars, quit with 'q'");
        do {
            c = (char) br.read();
            System.out.println(c);
        } while ( c != 'q' );
    }

    void consoleReadString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Please enter lines, quit with \"end\"");
        do {
            str = br.readLine();
            System.out.println(str);
        } while ( !str.equals("end" ));
    }

    void fileOp() {
        try {
            byte bWrite[] = {11, 20, 3, 50, 5};
            String fileName = "test.txt";

            // file write
            OutputStream os = new FileOutputStream(fileName);
            for (int i = 0; i < bWrite.length; i++)
                os.write(bWrite[i]);
            os.close();

            // file read
            InputStream is = new FileInputStream(fileName);
            int fileSize = is.available();
            for (int i = 0; i < fileSize; i++)
                System.out.print((char)is.read() + " ");
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



public class IO {
    public static void main(String[] args){
//        ConsoleOp op = new ConsoleOp();
//        op.consoleReadChar();
//        op.consoleReadString();
//        op.fileOp();

        System.out.println(File.separator + "   " + File.separatorChar + "   " + File.pathSeparator + "   " + File.pathSeparatorChar );
    }
}
