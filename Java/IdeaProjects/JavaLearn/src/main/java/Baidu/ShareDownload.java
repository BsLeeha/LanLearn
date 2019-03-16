package Baidu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShareDownload {
    private String rawUrl;
    private int threadNumber;

    public static void main(String[] args) {
        ShareDownload crack = new ShareDownload();
        crack.rawUrl = "https://pan.baidu.com/share/init?surl=j4jkC12bt1lV_aBgx74qpQ";
        crack.threadNumber = 2;
        crack.passwordGenerate();
        crack.cracking();
    }

    public void passwordGenerate() {
        String pattern = "0123456789abcdefghijklmnopqrstuvwxyz";

    }

    public void cracking() {

    }

    public int postPassword(String password) {
        Map<String, String> postParameters = new HashMap<>();

        StringBuilder postUrl = new StringBuilder();
        StringBuilder postMessage = new StringBuilder();

        postUrl.append(rawUrl.replace("init", "verify"))
                .append("&t=" + new Date().getTime())
                .append("&channel=chunlei&web=1&app_id=250528&bdstoken=null&logid=MTU1MjI3MDk0NTE4MDAuNDE2NzAyOTY1Mjc1MzY5NzY=&clienttype=0");

        postMessage.append("pwd=" + password)
                .append("&vcode=&vcode_str=");


        postParameters.put("Url", postUrl.toString());
        postParameters.put("Referer", rawUrl);
        postParameters.put("PostMessage", postMessage.toString());

        String jsonBody = new HttpUtils().post(postParameters);

        int errno = new Gson().fromJson(jsonBody, JsonObject.class).get("errno").getAsInt();
        System.out.println(errno);

        return errno;
    }

}
