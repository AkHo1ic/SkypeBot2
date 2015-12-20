package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.commands.CommandInfo;
import in.kyle.commands.CommandSet;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import in.kyle.skype.skypebot2.SkypeBot;
import in.kyle.skype.skypebot2.util.HasteBin;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CHelp {
    
    private Optional<String> helpLink;
    
    public CHelp() {
        helpLink = Optional.empty();
    }
    
    @Command(description = "Show command help")
    public String help(SkypeBot skypeBot) throws IOException {
        if (!helpLink.isPresent()) {
            generateUrl(skypeBot.getCommandSet());
        }
        return "Commands: " + helpLink.get();
    }
    
    private void generateUrl(CommandSet<SkypeUser, SkypeConversation> commandSet) throws IOException {
        StringBuilder message = new StringBuilder("Command Help:").append("\n");
        
        List<CommandInfo> commands = new ArrayList<>(commandSet.getCommands().values());
        commands.sort((o1, o2) -> new CompareToBuilder().append(o1.getName(), o2.getName()).toComparison());
        
        for (CommandInfo commandInfo : commands) {
            message.append(tree(commandInfo, ""));
        }
        
        helpLink = Optional.of(HasteBin.postString(message.toString()));
    }
    
    private String tree(CommandInfo commandInfo, String prefix) {
        StringBuilder output = new StringBuilder();
        StringBuilder line = new StringBuilder().append(prefix).append(commandInfo.getName()).append(" - ").append(commandInfo
                .getDescription());
        matchLength(line, 50);
        output.append(line.toString()).append("\n");
        if (commandInfo.getSubCommands().size() != 0) {
            Collections.sort(commandInfo.getSubCommands(), (o1, o2) -> new CompareToBuilder().append(o1.getName(), o2.getName())
                    .toComparison());
            commandInfo.getSubCommands().forEach(command -> {
                output.append(tree(command, prefix + " | "));
            });
        }
        return output.toString();
    }
    
    private void matchLength(StringBuilder string, int length) {
        while (string.length() < length) {
            string.append(" ");
        }
    }
}
