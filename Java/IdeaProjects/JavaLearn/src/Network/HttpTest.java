package Network;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

public class HttpTest {
    public static void main(String[] args) throws IOException {
        PostTest.run();
    }
}
