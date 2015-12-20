package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import org.apache.commons.lang.StringUtils;

import java.util.stream.Collectors;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CList {
    
    @Command(description = "List all users in this Skype chat")
    public String list(SkypeConversation conversation) {
        return "Users: " + StringUtils.join(conversation.getUsers().stream().map(SkypeUser::getUsername).sorted().collect(Collectors
                .toList()), ", ");
        
    }
}
