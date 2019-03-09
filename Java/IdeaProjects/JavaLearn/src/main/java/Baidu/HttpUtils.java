package Baidu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpUtils {
    // GET connect to url with cookie(if provided), return set-cookie in the header and the body
    Map<String, String> get(String url, String cookie) {
        Map<String, String> map = new HashMap<>();

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            if (cookie != null)
                conn.setRequestProperty("Cookie", cookie);

            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Response Code Error: " + conn.getResponseCode());
            }

//            System.out.println(getHeader(conn));

            map.put("Set-Cookie", conn.getHeaderField("Set-Cookie"));

            try (Scanner in = new Scanner(conn.getInputStream())) {
                map.put("Body", in.useDelimiter("\\A").next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    // POST connect to url with cookie and query parameters, return response body
    public String post(String url, String cookie, String queryParas) {
        String response = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
            if (cookie != null)
                conn.setRequestProperty("Cookie", cookie);

            conn.connect();

            try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
                out.write(queryParas);
            }

            getHeader(conn);

            try (Scanner in = new Scanner(conn.getInputStream())) {
                response = in.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getHeader(HttpURLConnection conn) {
        StringBuilder builder = new StringBuilder();
        try {
            // build response line
            builder.append(conn.getResponseCode())
                    .append(" ")
                    .append(conn.getResponseMessage())
                    .append("\n");

            Map<String, List<String>> headerFields = conn.getHeaderFields();

            // build response header
            for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
                if (entry.getKey() == null)
                    continue;

                builder.append(entry.getKey())
                        .append(": ");

                Iterator<String> it = entry.getValue().iterator();
                if (it.hasNext()) {
                    builder.append(it.next());
                    while (it.hasNext()) {
                        builder.append(", ")
                                .append(it.next());
                    }
                }

                builder.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
