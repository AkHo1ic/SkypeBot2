package in.kyle.skype.skypebot2.storage;

import lombok.Data;

/**
 * Created by Kyle on 12/13/2015.
 */
@Data
public class ChatData {
    
    private final String chatId;
    private final StringList banned;
    private final StringList ignored;
    private final StringList disabledCommands;
    private transient boolean changed;
    private String prefix;
    private String topic;
    private boolean enabled;
    private boolean guestEnabled = true;
    
    public ChatData(String chatId) {
        this.chatId = chatId;
        this.banned = new StringList();
        this.ignored = new StringList();
        this.enabled = true;
        this.disabledCommands = new StringList();
    }
}
