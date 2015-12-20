package in.kyle.skype.skypebot2.commands.all;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.EzSkype;
import in.kyle.skype.skypebot2.http.HttpRequest;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Kyle on 12/17/2015.
 */
public class CMC {
    
    @Command(description = "Minecraft commands", subCommands = {"ping", "uuid", "name", "status"})
    public void mc() {
    }
    
    @Command(description = "Ping a Minecraft server")
    public String ping(@Bind(name = "address") String address) throws IOException {
        JsonObject jsonObject = HttpRequest.builder().url(String.format("https://mcapi.ca/query/%s/info", address)).build()
                .makeRequestJson();
        if (jsonObject.get("status").getAsBoolean()) {
            Server server = EzSkype.GSON.fromJson(jsonObject, Server.class);
            
            return "MOTD: \n" + server.getMotd() + "\nVersion: " + server.getVersion() + "\nPing: " + server.getPing() + "\nPlayers: " +
                    "" + server.getPlayers().getOnline() + "/" + server.getPlayers().getMax();
        } else {
            return jsonObject.get("error").getAsString();
        }
    }
    
    @Command(description = "Get a user UUID from their name")
    public String uuid(@Bind(name = "username") String username) throws IOException {
        JsonObject jsonObject = HttpRequest.builder().url("https://mcapi.ca/uuid/player/" + username).build().makeRequestJson();
        if (!jsonObject.get("uuid").isJsonNull()) {
            return jsonObject.get("uuid_formatted").getAsString();
        } else {
            return "Invalid username";
        }
    }
    
    @Command(description = "Get a user name from their UUID")
    public String name(@Bind(name = "uuid") String uuid) throws IOException {
        JsonObject jsonObject = HttpRequest.builder().url("https://mcapi.ca/name/uuid/" + uuid.replace("-", "")).build().makeRequestJson();
        if (!jsonObject.get("uuid").isJsonNull()) {
            return jsonObject.get("name").getAsString();
        } else {
            return "Invalid UUID";
        }
    }
    
    @Command(description = "Get Minecraft service statuses")
    public String status() throws IOException {
        String servicesText = HttpRequest.builder().url("http://status.mojang.com/check").build().makeRequestText();
        JsonArray jsonArray = EzSkype.GSON.fromJson(servicesText, JsonArray.class);
        List<String> services = StreamSupport.stream(jsonArray.spliterator(), false).map(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Map.Entry<String, JsonElement> next = jsonObject.entrySet().iterator().next();
            return next.getKey() + ": " + getFormattedStatus(next.getValue().getAsString());
        }).collect(Collectors.toList());
        return StringUtils.join(services, " - ");
    }
    
    private String getFormattedStatus(String status) {
        switch (status) {
            case "green":
                return ":)";
            case "yellow":
                return "(worry)";
            default:
                return ":(";
        }
    }
    
    @Data
    private class Server {
        
        private final String motd;
        private final String version;
        private final Players players;
        private final double ping;
    }
    
    @Data
    private class Players {
        
        private final int online;
        private final int max;
    }
    
}
