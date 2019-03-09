package Baidu;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanAddress {
    private String rawUrl;
    private String cookie;
    private String downloadUrl;
    private String finalDownloadUrl;

    public static void main(String[] args) {
        PanAddress address = new PanAddress();
        address.rawUrl = "https://pan.baidu.com/s/1dFo3sCp";
        Map<String, String> map = address.getDownloadUrl(address.rawUrl, address.cookie);
        address.downloadUrl = map.get("Url");
        address.finalDownloadUrl = address.getFinalDownloadUrl(address.downloadUrl, map.get("Cookie"));
        System.out.println(address.finalDownloadUrl);
    }

    // connect rawUrl with cookie(if provided), download html, get and compose the download url
    // return composed downloadUrl and new Cookie
    public Map<String, String> getDownloadUrl(String rawUrl, String oldCookie) {
        Map<String, String> oldMap = new HttpUtils().get(rawUrl, oldCookie);
        Map<String, String> newMap = new HashMap<>();

        String newCookie = (oldCookie == null ? "" : oldCookie) + oldMap.get("Set-Cookie").split(";")[0];
        String html = oldMap.get("Body");
        String url = "https://pan.baidu.com/api/sharedownload?" + htmlParse(html);

        System.out.println("OldCookie: " + oldCookie);
        System.out.println("NewCookie: " + newCookie);
        System.out.println("Body Length: " + html.length());

        newMap.put("url", url);
        newMap.put("Cookie", newCookie);

        return newMap;
    }

    // parse the yunData.setData json and join them
    public String htmlParse(String html) {
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile("yunData.setData\\((.*?)\\);");
        Matcher matcher = pattern.matcher(html);
        String jsonData = null;

        if (matcher.find()) {
            jsonData = matcher.group(1);

        }

        return builder.toString();
    }

    // connect downloadUrl, get response
    public String getFinalDownloadUrl(String downloadUrl, String cookie) {
        return null;
    }
}
