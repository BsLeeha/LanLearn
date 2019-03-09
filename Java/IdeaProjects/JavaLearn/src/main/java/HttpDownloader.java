import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HttpDownloader {
    private URL url;
    private File localFile;
    private boolean resumable;
    private int threadNum = 5;
    private long fileSize;
    private final int MIN_SIZE = 2 << 20;   // 2M
    private boolean multithreaded;
    private int timeOut = 5000;
    private long[] downloadInterval;
    private AtomicLong downloadBytes = new AtomicLong(0);
    private AtomicInteger workingThreads = new AtomicInteger(0);
    private AtomicBoolean finished = new AtomicBoolean(false);

    public static void main(String[] args) throws MalformedURLException {
        String url = "http://mirrors.163.com/debian/ls-lR.gz";
        String path = "./ls-lR.gz";         // . -> src/ of intellij idea
        new HttpDownloader(url, path, 1, 5000).download();
    }

    public HttpDownloader(String url, String path, int threadNum, int timeOut) throws MalformedURLException {
        this.url = new URL(url);
        this.localFile = new File(path);
        this.threadNum = threadNum;
        this.timeOut = timeOut;

        try {
            if (localFile.exists())
                localFile.delete();
            localFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download() {
        long startTime = System.currentTimeMillis();

        /*
         * allocate threads and download
         */
        // check multithread or not
        resumable = isResumable();
        if (!resumable) {
            System.out.println("File doesn't support resumable download! Thread number -> 1");
            threadNum =  1;
        }
        else
            System.out.println("File supports resumable download!");

        if (resumable && threadNum != 1 && fileSize >= MIN_SIZE) multithreaded = true;
        else {
            multithreaded = false;
            threadNum = 1;
        }

        // single thread
        if (!multithreaded)
            new Thread(new DownloadTask(0, 0, fileSize - 1)).start();
        // multithread
        else {
            // allocate multithreads
            long blockSize = fileSize / threadNum;      // int
            downloadInterval = new long[threadNum + 1];
            downloadInterval[threadNum] = fileSize - 1;

            for (int i = 0; i < threadNum; i++)
                downloadInterval[i] = blockSize * i;

            for (int i = 0; i < threadNum-1; i++)
                new Thread(new DownloadTask(i, downloadInterval[i], downloadInterval[i + 1] - 1)).start();


            new Thread(new DownloadTask(threadNum-1, downloadInterval[threadNum-1], fileSize-1)).start();
        }

        // create monitor thread
        new Thread(new MonitorTask()).start();

        // print info of this download
        System.out.printf("Start download, File Size: %s, Thread number: %d\n",
                unify(fileSize), threadNum);

        // waiting until finished
        synchronized (finished) {
            try {
                finished.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // summary
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("*** File has been downloaded successfully!");
        System.out.printf("*** Time Used: %.3f s, Average speed: %d kB/s\n",
                (float)timeElapsed/1000, downloadBytes.get()/timeElapsed);

        // merge temp files
        mergeTempFiles();
    }

    public boolean isResumable() {
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");

            int responseCode;

            while (true) {
                try {
                    connection.connect();

                    fileSize = connection.getContentLength();
                    responseCode = connection.getResponseCode();

                    connection.disconnect();
                    break;
                } catch (ConnectException e) {
                    System.out.println("Retry to connect due to connection problem");
                }
            }

            if (responseCode == 206) return true;
            else return false;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String unify(long num) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        float temp = num;
        for (String unit : units) {
            if (temp < 1024) return String.format("%.2f %s", temp, unit);
            else temp /= 1024;
        }
        return null;
    }

    public void mergeTempFiles() {
        try (OutputStream out = new FileOutputStream(localFile, true)) {
            int n;
            for (int i = 0; i < threadNum; i++) {
                File tempFile = new File(localFile + "." + i + ".tmp");
                byte[] bytes = new byte[1024];
                try (InputStream in = new FileInputStream(tempFile)) {
                    while ((n = in.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                        out.flush();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MonitorTask implements Runnable {
        @Override
        public void run() {
            long prev = 0;
            long curr = 0;
            long speed = 0;

            while (true) {

                prev = curr;

                try {
                    Thread.sleep(1000);     // sleep 1s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                curr = downloadBytes.get();

                speed = (curr-prev) >> 10;      // KB/s

                System.out.printf("Speed: %d KB/s, Downloaded: %s (%.2f%%), Working threads: %d\n",
                        speed, unify(curr), (float)curr/fileSize * 100,  workingThreads.get());

                if (workingThreads.get() == 0) {
                    synchronized (finished) {
                        finished.notifyAll();
                    }
                    break;
                }
            }
        }
    }

    public class DownloadTask implements Runnable {
        private int id;
        private long start;
        private long end;
        private File tempFile;

        public DownloadTask(int id, long start, long end) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.tempFile = new File(localFile + "." + id + ".tmp");
            workingThreads.incrementAndGet();
        }

        @Override
        public void run() {
            boolean finished = false;

            while (!finished) {
                finished = connDown();

                if (!finished)
                    System.out.println("Retry to download part " + id);
            }

            System.out.println("Downloaded part " + id + ", part size: " + unify(tempFile.length()));
            workingThreads.decrementAndGet();
        }

        // connect and download
        public boolean connDown() {
            try {
                // connect
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Range", String.format("bytes=%d-%d", start, end));
                connection.setConnectTimeout(timeOut);
                connection.setReadTimeout(timeOut);
                connection.connect();

                // downloading
                long totalSize = connection.getHeaderFieldLong("Content-Length", -1);
                if (totalSize != end - start + 1) return false;

                try (InputStream in = connection.getInputStream()) {
                    try (OutputStream out = new FileOutputStream(tempFile)) {
                        byte[] bytes = new byte[1024];  // 1KB
                        long size;

                        while(start <= end && (size = in.read(bytes)) != -1) {
                            out.write(bytes);
                            out.flush();
                            downloadBytes.addAndGet(size);
                            start += size;
                        }
                    }
                }

                // download finished
                connection.disconnect();

                // check download integrity
                return start > end;

            } catch (SocketTimeoutException e) {
                System.out.println("Part " + id + "  reading timeout.");
                return false;
            } catch (IOException e) {
                System.out.println("Part " + id + " encountered error.");
                return false;
            }
        }
    }
}
