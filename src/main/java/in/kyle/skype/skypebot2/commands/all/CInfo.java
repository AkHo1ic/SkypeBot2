package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CInfo {
    
    @Command(description = "Get info about the current chat", aliases = {"i"})
    public String info(SkypeConversation conversation) {
        return "Chat Information" + "\n" +
                Chat.bold("Users in chat: ") + conversation.getUsers().size() + "\n" +
                Chat.bold("Chat ID: ") + conversation.getLongId() + "\n" +
                Chat.bold("Topic: ") + conversation.getTopic();
    }
}
