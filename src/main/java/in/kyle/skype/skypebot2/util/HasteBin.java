package in.kyle.skype.skypebot2.util;

import com.google.gson.JsonObject;
import in.kyle.skype.skypebot2.http.ContentType;
import in.kyle.skype.skypebot2.http.HttpRequest;
import in.kyle.skype.skypebot2.http.HttpRequestType;

import java.io.IOException;

public class HasteBin {
    
    public static String postString(String string) throws IOException {
        String url = "http://hastebin.com/documents";
        HttpRequest httpRequest = HttpRequest.builder().url(url).requestType(HttpRequestType.POST).contentType(ContentType.TEXT_PLAIN)
                .postBytes(string.getBytes("UTF-8")).header("charset", "utf-8").build();
        JsonObject jsonObject = httpRequest.makeRequestJson();
        String f = jsonObject.get("key").getAsString().replace("\"", "");
        return url.substring(0, url.lastIndexOf("/") + 1) + f + ".hs";
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println(postString("lmao u fookin wot?"));
    }
}