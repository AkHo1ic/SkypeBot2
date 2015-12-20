package in.kyle.skype.skypebot2.commands;

import lombok.AllArgsConstructor;

/**
 * Created by Kyle on 12/15/2015.
 */
@AllArgsConstructor
public enum Message {
    IO_ERROR("An error occurred, try again later"),
    USER_NOT_FOUND("User not in this chat"),
    NOT_ADMIN("I have to be a chat admin in order to run that command");
    
    private final String value;
    
    @Override
    public String toString() {
        return value;
    }
}
