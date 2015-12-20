package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.commands.CommandSet;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeLocalUser;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import in.kyle.skype.skypebot2.SkypeBot;
import in.kyle.skype.skypebot2.commands.Message;
import in.kyle.skype.skypebot2.storage.ChatData;
import in.kyle.skype.skypebot2.storage.StringList;

import java.util.Optional;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CAdmin {
    
    // @formatter:off
    @Command(description = "Administrator commands", permission = "admin", subCommands = {
            "ban",
            "unban",
            "ignore",
            "unignore",
            "prefix",
            "topic",
            "enabled",
            "command"
    })
    // @formatter:on
    public void admin() {
    }
    
    @Command(description = "Ban a user from the chat")
    public String ban(@Bind(name = "username") String username, SkypeConversation conversation, ChatData chatData, SkypeLocalUser 
            localUser) {
        if (conversation.isAdmin(localUser)) {
            Optional<SkypeUser> userOptional = conversation.getUser(username);
            if (userOptional.isPresent()) {
                SkypeUser user = userOptional.get();
                conversation.kick(user);
                chatData.getBanned().add(user);
                chatData.setChanged(true);
                return "User banned";
            } else {
                return Message.USER_NOT_FOUND.toString();
            }
        } else {
            return Message.NOT_ADMIN.toString();
        }
    }
    
    @Command(description = "Unban a user from the chat")
    public String unban(@Bind(name = "username") String username, ChatData chatData) {
        if (chatData.getBanned().contains(username)) {
            chatData.getBanned().remove(username);
            chatData.setChanged(true);
            return "User unbanned";
        } else {
            return Message.USER_NOT_FOUND.toString();
        }
    }
    
    @Command(description = "Ignore a user")
    public String ignore(@Bind(name = "username") String username, SkypeConversation conversation, ChatData chatData) {
        Optional<SkypeUser> userOptional = conversation.getUser(username);
        if (userOptional.isPresent()) {
            SkypeUser user = userOptional.get();
            chatData.getIgnored().add(user);
            chatData.setChanged(true);
            return "User ignored";
        } else {
            return Message.USER_NOT_FOUND.toString();
        }
    }
    
    @Command(description = "Unignore a user")
    public String unignore(@Bind(name = "username") String username, SkypeConversation conversation, ChatData chatData) {
        Optional<SkypeUser> userOptional = conversation.getUser(username);
        if (userOptional.isPresent()) {
            SkypeUser user = userOptional.get();
            chatData.getIgnored().add(user);
            chatData.setChanged(true);
            return "User ignored";
        } else {
            return Message.USER_NOT_FOUND.toString();
        }
    }
    
    @Command(description = "Set the bot prefix")
    public String prefix(@Bind(name = "prefix", infinite = true) String prefix, ChatData chatData) {
        chatData.setPrefix(prefix);
        chatData.setChanged(true);
        return "Prefix set to: " + prefix;
    }
    
    @Command(description = "Enable/disable the bot")
    public String enabled(Enabled enabled, ChatData chatData) {
        chatData.setEnabled(Boolean.valueOf(enabled.name()));
        chatData.setChanged(true);
        return "Bot enabled " + chatData.isEnabled();
    }
    
    @Command(description = "Enable/disable commands")
    public String command(@Bind(name = "command") String command, Enabled enabled, ChatData chatData, SkypeBot bot) {
        if (isCommand(command, bot.getCommandSet())) {
            boolean disabled = !enabled.toBoolean();
            StringList disabledCommands = chatData.getDisabledCommands();
            if (disabled) {
                if (disabledCommands.contains(command)) {
                    return "Command already disabled";
                } else {
                    disabledCommands.add(command);
                    chatData.setChanged(true);
                    return "Command " + command + " disabled";
                }
            } else {
                if (disabledCommands.contains(command)) {
                    disabledCommands.remove(command);
                    chatData.setChanged(true);
                    return "Command " + command + " enabled";
                } else {
                    return "Command already enabled";
                }
            }
        } else {
            return "Command not found";
        }
    }
    
    private boolean isCommand(String command, CommandSet<SkypeUser, SkypeConversation> set) {
        return set.getCommands().containsKey(command.toLowerCase());
    }
    
    @Command(description = "Topic commands", subCommands = {"lock", "unlock", "set"})
    public void topic() {
    }
    
    @Command(description = "Lock the topic")
    public String lock(ChatData chatData, SkypeConversation conversation) {
        chatData.setTopic(conversation.getTopic());
        chatData.setChanged(true);
        return "Topic locked";
    }
    
    @Command(description = "Unlock the topic")
    public String unlock(ChatData chatData) {
        chatData.setTopic(null);
        chatData.setChanged(true);
        return "Topic unlocked";
    }
    
    @Command(description = "Set the current topic")
    public String set(@Bind(name = "topic", infinite = true) String topic, SkypeConversation conversation, ChatData chatData) {
        if (chatData.getTopic() != null) {
            chatData.setTopic(topic);
            chatData.setChanged(true);
        }
        conversation.changeTopic(topic);
        return "Topic set to " + topic;
    }
    
    enum TopicAction {
        LOCK, UNLOCK, SET;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    enum Enabled {
        TRUE, FALSE;
        
        public boolean toBoolean() {
            return Boolean.valueOf(name());
        }
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
