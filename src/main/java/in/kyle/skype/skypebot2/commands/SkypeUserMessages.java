package in.kyle.skype.skypebot2.commands;

import in.kyle.commands.CommandInfo;
import in.kyle.commands.MessageSender;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import in.kyle.ezskypeezlife.exception.SkypeException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Kyle on 12/15/2015.
 */
public class SkypeUserMessages implements MessageSender<SkypeUser, SkypeConversation> {
    
    @Override
    public void sendCommandOutput(SkypeUser user, SkypeConversation conversation, CommandInfo commandInfo, String s1) {
        conversation.sendMessage(s1);
    }
    
    @Override
    public void sendUsage(SkypeUser user, SkypeConversation conversation, CommandInfo commandInfo) {
        conversation.sendMessage("Usage: -" + buildUsage(commandInfo, true));
    }
    
    @Override
    public void sendNoPermission(SkypeUser user, SkypeConversation conversation, CommandInfo commandInfo) {
        conversation.sendMessage("You do not have permission to execute this command");
    }
    
    private String buildUsage(CommandInfo commandInfo, boolean first) {
        if (!commandInfo.getSuperCommand().isPresent()) {
            return commandInfo.getName() + (first ? " " + commandInfo.getUsage() : "");
        } else {
            return buildUsage(commandInfo.getSuperCommand().get(), false) + " " + commandInfo.getName() + (first ? " " + commandInfo
                    .getUsage() : "");
        }
    }
    
    @Override
    public void sendCommandNotFound(SkypeUser user, SkypeConversation conversation, String s1) {
        conversation.sendMessage("Command not found");
    }
    
    @Override
    public void sendArgumentError(SkypeUser user, SkypeConversation conversation, CommandInfo commandInfo, String[] strings, String s1) {
        conversation.sendMessage("Invalid argument " + s1);
    }
    
    @Override
    public void sendError(SkypeUser user, SkypeConversation conversation, CommandInfo commandInfo, Exception e) {
        if (e instanceof IOException) {
            conversation.sendMessage("A network error occurred, please try again later");
        } else if (e instanceof SkypeException) {
            conversation.sendMessage("A Skype error has occurred, please try again later");
        } else {
            conversation.sendMessage("An unknown error occurred while performing this command, please try again later");
        }
    }
    
    @Override
    public String wrapCollection(Collection collection) {
        return StringUtils.join(collection, '|');
    }
    
    @Override
    public String wrapArguments(Collection<String> collection) {
        return StringUtils.join(collection, ' ');
    }
}
