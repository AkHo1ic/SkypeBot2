package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.exception.SkypeException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CFind {
    
    @Command(description = "Find a user by their name")
    public String find(@Bind(name = "search") String term, SkypeConversation skypeConversation) throws IOException, SkypeException {
        skypeConversation.loadAllUsers();
        skypeConversation.fullyLoad();
        
        StringBuilder output = new StringBuilder("Found: ");
        List<String> users = new ArrayList<>();
        skypeConversation.getUsers().forEach(user -> {
            boolean out = false;
            boolean fl = user.getFirstName().isPresent() && user.getLastName().isPresent();
            if (has(user.getUsername(), term)) {
                out = true;
            } else if (has(user.getFirstName(), term)) {
                out = true;
            } else if (has(user.getLastName(), term)) {
                out = true;
            } else if (fl && has(user.getFirstName().get() + " " + user.getLastName().get(), term)) {
                out = true;
            }
            
            if (out) {
                users.add(user.getUsername());
            }
        });
        Collections.sort(users);
        output.append(StringUtils.join(users, ", "));
        return users.size() == 0 ? "No users found" : output.toString();
    }
    
    private boolean has(Optional<String> string, String term) {
        return string.isPresent() && string.get().toLowerCase().replace(" ", "").contains(term.toLowerCase().replace(" ", ""));
    }
    
    private boolean has(String string, String term) {
        return string.toLowerCase().replace(" ", "").contains(term.toLowerCase().replace(" ", ""));
    }
}
