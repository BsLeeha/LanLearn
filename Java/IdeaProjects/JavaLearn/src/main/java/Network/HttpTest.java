package Network;

import java.io.*;
import java.net.*;
import java.util.*;

class Usage1 {
    public static void run() {
        try {
            URL url = new URL("https://www.vogella.com/");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            String lineSeparator = System.getProperty("line.separator");
//            for (int i = 0; i < lineSeparator.length(); i++)
//                System.out.printf("%d ", (int)lineSeparator.charAt(i));     // linux: \n

            StringBuilder content = new StringBuilder();
            String temp = "";

            while((temp = in.readLine()) != null)
                content.append(temp + lineSeparator);

            System.out.println(content);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Usage2 {
    public static void run() {
        try {
            InputStream inputStream = new URL("https://www.vogella.com/").openStream();
            Scanner in = new Scanner(inputStream, "UTF-8");
            String content = in.useDelimiter("\\A").next();
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class PostTest {
    public static void run() throws IOException {
        String urlString = "https://tools.usps.com/go/ZipLookupAction.action";
        Object userAgent = "HTTPie/0.9.2";
        Object redirects = "1";
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        Map<String, String> params = new HashMap<>();
        params.put("tAddress", "1 Market Street");
        params.put("tCity", "San Francisco");
        params.put("tState", "CA");

        String result = doPost(new URL(urlString), params,
                userAgent == null ? null : userAgent.toString(),
        redirects == null ? -1 : Integer.parseInt(redirects.toString()));

//        Files.writeString(Paths.get("./test.html"), result);
    }

    public static String doPost(URL url, Map<String, String> params, String userAgent, int redirects) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (userAgent != null) connection.setRequestProperty("User-Agent", userAgent);
        if (redirects >= 0) connection.setInstanceFollowRedirects(false);
        connection.setDoOutput(true);

        try(PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            boolean first = true;
            // a=1&b=2
            for (Map.Entry<String, String> pair : params.entrySet()) {
                if (first) first = false;
                else out.print("&");

                out.print(pair.getKey() + '=' + URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
        }

        String encoding = connection.getContentEncoding();
        if (encoding == null) encoding = "UTF-8";

        if (redirects > 0) {
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
             || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
             || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                String location = connection.getHeaderField("Location");
                if (location != null) {
                    URL base = connection.getURL();
                    connection.disconnect();
                    return doPost(new URL(base, location), params, userAgent, redirects - 1);
                }
            }

        } else if (redirects == 0){
            throw new IOException("Too many redirects");
        }

        StringBuilder result = new StringBuilder();

        try (Scanner in = new Scanner(connection.getInputStream(), encoding)) {
            while(in.hasNextLine()) {
                result.append(in.nextLine());
                result.append("\n");
            }
        } catch (IOException e) {
            InputStream err = connection.getErrorStream();
            if (err == null) throw e;
            try (Scanner in = new Scanner(err)) {
                result.append(in.nextLine());
                result.append("\n");
            }
        }

        return result.toString();
    }
}

class ResponseBuilder {
    private HttpURLConnection conn;

    public ResponseBuilder(HttpURLConnection conn) {
        this.conn = conn;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        try {
            // response line
            builder.append(conn.getResponseCode())
                    .append(" ")
                    .append(conn.getResponseMessage())
                    .append("\n");
            // response body
            Map<String, List<String>> headerFields = conn.getHeaderFields();
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

public class HttpTest {
    public static void main(String[] args) throws IOException {
        // URL: basic io
//        URL url = new URL("http://www.baidu.com");
//        try (InputStream in = url.openStream()) {
//            Path path = Paths.get("baidu.html");
//            if (Files.exists(path))
//                Files.delete(path);
//            Files.copy(in, Paths.get("baidu.html"));
//        }
//
//         // URL+URLConnection: basic io + response parse
//        URLConnection connection = url.openConnection();
//        String type = connection.getContentType();
//        String encoding = connection.getContentEncoding();
//        InputStream in1 = connection.getInputStream();

        // URL+HttpURLConnection
        String urlStr = "https://www.baidu.com/s";
        String param = "百度";
        String charset = "UTF-8";
        String queryStr = String.format("wd=%s", URLEncoder.encode(param, charset));
        HttpURLConnection conn = (HttpURLConnection) (new URL(urlStr + "?" + queryStr)).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3726.0 Safari/537.36");
        conn.setRequestProperty("Cookie", "BAIDUID=5B0E7165E002D5163FDE857726E77FF0:FG=1; BIDUPSID=5B0E7165E002D5163FDE857726E77FF0; PSTM=1551860106; PANWEB=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDUSS=EpDQWNoQWJ-OENueFd5MldNNjN5RUNQYnNmfmdmV0dvellOY01xMXp0ZXNSS2RjQVFBQUFBJCQAAAAAAAAAAAEAAADpdfEySGFpSEhISEgxMjM0NQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKy3f1yst39cR; pan_login_way=1; STOKEN=da9f1e282c52387e6223fc79fb02675b2e28b8c27a680edf87731e4c84302853; SCRC=c83d95fc983f10e45aeac0bc115d92a7; BDCLND=STM2ll%2Bx%2Fy%2BLH5%2FGymhd9PCkOQeMPrUuYVh%2FUYlFPHY%3D; recommendTime=android2019-03-04%2020%3A00%3A00; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=1; H_PS_PSSID=1429_21098_28608_28584_28639_26350_28518_28625_28606; cflag=13%3A3; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1551961156,1551965929,1551965945,1551965961; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1552013170; PANPSC=3189228777227310906%3AZuGp9t0KCyfGhsyADQaQdqgNktgs3FWQlO37VjnrUDt8dOu%2BPsSlwUci2O0YbEjpAZ5h3Ti%2BfiEhubspyRpUdqKK%2FFH5WUxXsm1Aekt7PPOIIPVU%2BTdmeXZNBoZ8i0CTW8pfgMw2m%2FrQ8rlESRQE3CZTnz2RR6t%2BZFCUk453j8DrbkBUnMiFfw%3D%3D");

        System.out.println(new ResponseBuilder(conn).build());

        try (Scanner in = new Scanner(conn.getInputStream())) {
            try (PrintWriter out = new PrintWriter("baiduQuery.html")) {
                out.write(in.useDelimiter("\\A").next());
            }
        }


    }
}
