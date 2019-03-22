package Concurrent;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/*
 * Thread t = new subThread();
 * Thread t = new Thread(new Runnable());
 */


public class ThreadUsage {
    public static void main(String[] args) {
        try {
            List<String> files = new ArrayList<>();
            List<DigestJob> jobs = new ArrayList<>();

            // list files in current directory
            for (File file : new File(".").listFiles()) {
                if (file.isFile())
                    files.add(file.toString());
            }

            // list files in current directory and nested ones
//           files = Files.walk(Paths.get("."))
//                        .filter(Files::isRegularFile)
//                        .map(Path::toString)
//                        .collect(Collectors.toList());

            for (String file : files) {
                DigestJob job = new DigestJob(file);
                jobs.add(job);
                new Thread(job).start();
            }

            for (DigestJob job : jobs) {
                while (job.digest == null);
//                TimeUnit.SECONDS.sleep(2);
                System.out.println(job.fileName + ": " + DatatypeConverter.printHexBinary(job.digest));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class DigestJob implements Runnable {
        private String fileName;
        private volatile byte[] digest = null;

        public DigestJob(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                // establish a hash function
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

                // use the streams to finish the read and computation of each byte
                FileInputStream fileIn = new FileInputStream(fileName);
                DigestInputStream digestIn = new DigestInputStream(fileIn, messageDigest);
                while (digestIn.read() != -1);
                digestIn.close();
                fileIn.close();

                // finish the computation
                digest = messageDigest.digest();

                // print message
//                System.out.println(fileName + ": " + DatatypeConverter.printHexBinary(digest));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
