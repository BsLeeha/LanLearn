package Network.DoubanScrapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WithJDK {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://movie.douban.com/top250");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8,zh-Hans-CN;q=0.5,zh-Hans;q=0.3");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        conn.connect();

        StringBuilder html = new StringBuilder();
        try (Scanner in = new Scanner(conn.getInputStream())) {
            while (in.hasNextLine()) {
                html.append(in.nextLine());
            }
        }

        System.out.println(html);
    }
}
