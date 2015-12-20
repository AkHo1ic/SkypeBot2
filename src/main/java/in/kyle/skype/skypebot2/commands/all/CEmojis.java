package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.ezskypeezlife.EzSkype;

/**
 * Created by Kyle on 12/17/2015.
 */
public class CEmojis {
    
    @Command(description = "List all emojis")
    public String emojis(EzSkype ezSkype) {
        StringBuilder message = new StringBuilder();
        ezSkype.getSkypeData().getTextElementManager().getEmojis().forEach(skypeEmoji -> message.append(Chat.emoji(skypeEmoji))
                .append(" "));
        return message.toString();
    }
}
