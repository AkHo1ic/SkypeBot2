package in.kyle.skype.skypebot2.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import in.kyle.skype.skypebot2.http.ContentType;
import in.kyle.skype.skypebot2.http.HttpRequest;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrawPoll {
    
    @Getter
    private String title;
    @Getter
    private Map<String, Integer> options;
    @Getter
    private boolean multiVoting, captcha;
    @Getter
    private int id;
    
    
    public StrawPoll(String title, List<String> options) {
        this.title = title;
        this.options = new HashMap<>();
        options.forEach(o -> this.options.put(o, 0));
    }
    
    public StrawPoll(int id) {
        this.id = id;
    }
    
    public void create() throws IOException {
        JsonObject obj = new JsonObject();
        obj.addProperty("title", title);
        JsonArray arr = new JsonArray();
        options.forEach((k, v) -> arr.add(new JsonPrimitive(k.replace("\n", ""))));
        obj.add("options", arr);
        obj.addProperty("multi", multiVoting);
        obj.addProperty("permissive", false);
        
        JsonObject jsonObject = HttpRequest.builder().url("http://strawpoll.me/api/v2/polls").contentType(ContentType.JSON).postData(obj
                .toString()).build().makeRequestJson();
        id = jsonObject.get("id").getAsInt();
    }
    
    public void refresh() throws IOException {
        JsonObject jsonObject = HttpRequest.builder().url("https://strawpoll.me/api/v2/polls/" + id).build().makeRequestJson();
        if (title == null) {
            title = jsonObject.get("title").getAsString();
        }
        options = new HashMap<>();
        JsonArray picks = jsonObject.getAsJsonArray("options");
        JsonArray votes = jsonObject.getAsJsonArray("votes");
        for (int i = 0; i < picks.size(); i++) {
            options.put(picks.get(i).getAsString(), votes.get(i).getAsInt());
        }
        multiVoting = jsonObject.get("multi").getAsBoolean();
        captcha = jsonObject.has("captcha") && jsonObject.get("captcha").getAsBoolean();
    }
    
    public String getUrl() {
        return "http://strawpoll.me/" + id;
    }
}