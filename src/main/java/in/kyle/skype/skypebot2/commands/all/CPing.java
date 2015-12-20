package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CPing {
    @Command(description = "Pong!")
    public String ping() {
        return "Pong!";
    }
}
