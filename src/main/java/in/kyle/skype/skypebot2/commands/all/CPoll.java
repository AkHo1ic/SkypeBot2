package in.kyle.skype.skypebot2.commands.all;


import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.skype.skypebot2.apis.StrawPoll;

import java.io.IOException;

/**
 * Created by Kyle on 12/14/2015.
 */
public class CPoll {
    
    @Command(description = "Strawpoll commands", subCommands = {"view", "create"})
    public void strawPoll() {
    }
    
    @Command(description = "View a poll stats")
    public String view(@Bind(name = "id", infinite = true) String idText) throws IOException {
        int id = -1;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            if (idText.contains("/")) {
                String cut = idText.substring(idText.indexOf("/") + 1);
                if (cut.contains("/")) {
                    cut = cut.substring(0, cut.indexOf("/"));
                }
                try {
                    id = Integer.parseInt(cut);
                } catch (NumberFormatException ignored) {
                    return "The poll ID must be an integer or URL";
                }
            }
        }
        
        if (id > 0) {
            StrawPoll poll = new StrawPoll(id);
            poll.refresh();
            StringBuilder message = new StringBuilder();
            message.append(poll.getTitle()).append("\n");
            poll.getOptions().forEach((k, v) -> message.append(Chat.bold(k)).append(": ").append(v).append("\n"));
            return message.toString();
        } else {
            return "Invalid id";
        }
    }
    
    @Command(description = "Create a new poll")
    public String create(@Bind(name = "id") String id) {
        return "Not yet implemented";
    }
}
