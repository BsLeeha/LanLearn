package Baidu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    private String cookie = "Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1552228211; PANPSC=3862593845058001318%3AZuGp9t0KCyfGhsyADQaQdqgNktgs3FWQlO37VjnrUDt8dOu%2BPsSlwUci2O0YbEjpFKb5DZ1SOOIfNDna3pZOuaKK%2FFH5WUxXsm1Aekt7PPOIIPVU%2BTdmeXZNBoZ8i0CTW8pfgMw2m%2FrQ8rlESRQE3CZTnz2RR6t%2BZFCUk453j8DrbkBUnMiFfw%3D%3D; STOKEN=c5b32234308074841c26f2bab2e0acd539aa2d6a7565a9be738aa8281517dd9b; SCRC=d4bf0e271ab57450eb546b23f1112c17; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1551875336,1552045487,1552054717,1552227480; PANWEB=1; BDCLND=STM2ll%2Bx%2Fy%2BLH5%2FGymhd9PCkOQeMPrUuYVh%2FUYlFPHY%3D; BAIDUID=0459535EE1385316AC07440FFD81A789:FG=1; BIDUPSID=1BAD3CFF31FEC5035127153391495319; PSTM=1518094751; BDUSS=zA2NXF5a3ZYSWc3dXlUejVvWmhzeVJsVGxEQ1NqSVF5U3YzcjI3VFlNbHJyS3hjQVFBQUFBJCQAAAAAAAAAAAEAAADpdfEySGFpSEhISEgxMjM0NQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGsfhVxrH4VcV";
//    private String downloadUrl;       // temp
    private String finalDownloadUrl;

    public static void main(String[] args) {
        PanAddress address = new PanAddress();
        address.rawUrl = "https://pan.baidu.com/s/1dFo3sCp";
        Map<String, String> map = address.getDownloadUrl(address.rawUrl, address.cookie);
//        address.finalDownloadUrl = address.getFinalDownloadUrl(map.get("Url"),  map.get("Cookie"), map.get("QueryParas"));
//        System.out.println(address.finalDownloadUrl);
    }

    // connect rawUrl with cookie(if provided), download html, get and compose the download url
    // return composed downloadUrl and new Cookie
    public Map<String, String> getDownloadUrl(String rawUrl, String oldCookie) {
        Map<String, String> oldMap = new HttpUtils().get(rawUrl, oldCookie);
        Map<String, String> newMap = new HashMap<>();

        String newCookie = (oldCookie == null ? "" : oldCookie) + oldMap.get("Set-Cookie").split(";")[0];
        String html = oldMap.get("Body");
        Map<String, String> tempMap = htmlParse(html);
        String url = "https://pan.baidu.com/api/sharedownload?" + tempMap.get("Url");

        System.out.println("OldCookie: " + oldCookie);
        System.out.println("NewCookie: " + newCookie);
        System.out.println("Body Length: " + html.length());
        System.out.println("New Url: " + url);
        System.out.println("Post paras: " + tempMap.get("QueryParas"));

        newMap.put("Url", url);
        newMap.put("Cookie", newCookie);
        newMap.put("QueryParas", tempMap.get("QueryParas"));

        return newMap;
    }

    // parse the yunData.setData json and join them
    public Map<String, String> htmlParse(String html) {
        Map<String, String> map = new HashMap<>();
        StringBuilder url = new StringBuilder();
        StringBuilder queryParas = new StringBuilder();

        Pattern pattern = Pattern.compile("yunData.setData\\((.*?)\\);");
        Matcher matcher = pattern.matcher(html);
        String jsonData = null;

        if (matcher.find()) {
            jsonData = matcher.group(1);
            System.out.println(jsonData);

            JsonObject jsonObject = new Gson().fromJson(jsonData, JsonObject.class);

            url.append("sign=" + jsonObject.get("sign").getAsString())
                    .append("&timestamp=" + jsonObject.get("timestamp"))
                    .append("&channel=chunlei")
                    .append("&web=1")
                    .append("&bdstoken=" + jsonObject.get("bdstoken").getAsString())
                    .append("&app_id=" + jsonObject.get("file_list").getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject().get("app_id").getAsString())
                    .append("&logid=" + "MTU1MjIyODQyNzI0MzAuOTA3MTYwMzk3MjAxMDU1Ng==")
                    .append("&clienttype=0");

            map.put("Url", url.toString());

            queryParas.append("encrypt=0")
                    .append("&product=share")
                    .append("&uk=" + jsonObject.get("uk"));
//                    .append("&primaryid=" + jsonObject.get("primaryid"))
//                    .append("&fid_list=" + jsonObject.get("fid_list"))
//                    .append("&path_list=" + jsonObject.get("path_list"))
//                    .append("&vip=" + jsonObject.get("vip"));

            map.put("QueryParas", queryParas.toString());
        }

        return map;
    }

    // connect downloadUrl with cookie, get response
    public String getFinalDownloadUrl(String downloadUrl, String cookie, String queryParas) {
        Map<String, String> postParameters = new HashMap<>();
        String responseJson = new HttpUtils().post(postParameters);
        String url = null;

        System.out.println("Response Json: " + responseJson);

        JsonObject jsonObject = new Gson().fromJson(responseJson, JsonObject.class);
//        url = jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("dlink").getAsString();

        System.out.println("Final url: " + url);

        return url;
    }
}
