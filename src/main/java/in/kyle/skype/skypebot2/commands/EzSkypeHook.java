package in.kyle.skype.skypebot2.commands;

import in.kyle.commands.CommandSet;
import in.kyle.ezskypeezlife.EzSkype;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import in.kyle.ezskypeezlife.events.conversation.message.SkypeMessageReceivedEvent;
import in.kyle.skype.skypebot2.storage.ChatData;
import in.kyle.skype.skypebot2.storage.ChatDataManager;
import lombok.Data;

/**
 * Created by Kyle on 12/15/2015.
 */
@Data
public class EzSkypeHook {
    
    private final CommandSet<SkypeUser, SkypeConversation> commandSet;
    private final ChatDataManager dataManager;
    
    public void onSkypeMessage(SkypeMessageReceivedEvent e) {
        ChatData chatData = dataManager.getData(e.getConversation().getLongId());
        String prefix = chatData.getPrefix();
        EzSkype.LOGGER.info("Message {}<-{}", e.getUser().getUsername(), e.getMessage().getMessage());
        if (prefix == null) {
            prefix = "-";
        }
        if (e.getMessage().getMessage().startsWith(prefix)) {
            if (chatData.isEnabled()) {
                EzSkype.LOGGER.info("Command {}", e.getMessage().getMessage());
                
                String command = e.getMessage().getMessage().substring(prefix.length());
                if (command.contains(" ")) {
                    command = command.substring(0, command.indexOf(" "));
                }
                
                if (chatData.getDisabledCommands().contains(command)) {
                    e.getConversation().sendMessage("That command is disabled");
                } else {
                    commandSet.execute(e.getMessage().getMessage().substring(prefix.length()), e.getUser(), e.getConversation());
                }
            } else {
                if (e.getMessage().getMessage().toLowerCase().substring(prefix.length()).equals("admin enabled true")) {
                    commandSet.execute(e.getMessage().getMessage().substring(prefix.length()), e.getUser(), e.getConversation());
                }
            }
        }
    }
}
