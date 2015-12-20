package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CID {
    
    @Command(description = "Get the chat id")
    public String id(SkypeConversation conversation) {
        return conversation.getLongId();
    }
}
