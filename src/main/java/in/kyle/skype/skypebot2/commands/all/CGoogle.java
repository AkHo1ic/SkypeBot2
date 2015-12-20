package in.kyle.skype.skypebot2.commands.all;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.skype.skypebot2.http.HttpRequest;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CGoogle {
    
    @Command(description = "Google stuff", aliases = {"g"})
    public String google(@Bind(name = "term") String term) throws IOException {
        JsonObject response = HttpRequest.builder().url("http://ajax.googleapis.com/ajax/services/search/web?v=1.0").urlParameter("q", 
                term).build().makeRequestJson();
        
        int responseCode = response.get("responseStatus").getAsInt();
        
        if (responseCode == 200) {
            JsonArray responseArray = response.getAsJsonObject("responseData").getAsJsonArray("results");
            if (responseArray.size() > 0) {
                StringBuilder builder = new StringBuilder(term);
                for (int i = 0; i < 3 && i < responseArray.size(); i++) {
                    JsonObject result = responseArray.get(i).getAsJsonObject();
                    String url = result.get("url").getAsString();
                    String title = result.get("title").getAsString();
                    builder.append("\n").append("<a href=\"").append(url).append("\">").append(Jsoup.parse(title).text()).append("</a>");
                }
                return builder.toString();
            } else {
                return "No results found";
            }
        } else {
            return "Unknown response code " + response + "\n" + response.get("responseDetails").getAsString();
        }
    }
}
