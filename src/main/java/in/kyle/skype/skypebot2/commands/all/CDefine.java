package in.kyle.skype.skypebot2.commands.all;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.skype.skypebot2.http.HttpRequest;

import java.io.IOException;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CDefine {
    
    @Command(description = "Define a term")
    public String define(@Bind(name = "term") String term) throws IOException {
        JsonObject object = HttpRequest.builder().url("http://api.urbandictionary.com/v0/define").urlParameter("term", "ass").build()
                .makeRequestJson();
        JsonArray list = object.getAsJsonArray("list");
        if (list.size() != 0) {
            JsonObject word = list.get(0).getAsJsonObject();
            String link = word.get("permalink").getAsString();
            String definition = word.get("definition").getAsString();
            int likes = word.get("thumbs_up").getAsInt();
            int dislikes = word.get("thumbs_down").getAsInt();
            return Chat.bold(term) + "\n" + definition + "\n[" + Chat.link("link", link) + "] " + likes + " (y) " + dislikes + " (n)";
        } else {
            return "No definition found";
        }
    }
}
