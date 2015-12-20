package in.kyle.skype.skypebot2.apis;

import com.google.gson.JsonObject;
import in.kyle.skype.skypebot2.http.HttpRequest;

import java.io.IOException;

/**
 * Created by Kyle on 12/15/2015.
 */
public class UrlShortener {
    
    
    public String shorten(String url, String key) throws IOException {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        JsonObject jsonObject = HttpRequest.builder().url("https://api-ssl.bitly.com/v3/shorten").urlParameter("access_token", key)
                .urlParameter("longUrl", url).build().makeRequestJson();
        if (jsonObject.get("status_code").getAsInt() == 200) {
            return jsonObject.get("data").getAsJsonObject().get("url").getAsString();
        } else {
            throw new IOException("An error occurred while shortening url " + jsonObject.get("status_txt").getAsString() + " " +
                    "original url " + url);
        }
    }
}
