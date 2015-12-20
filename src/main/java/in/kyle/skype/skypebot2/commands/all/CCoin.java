package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CCoin {
    @Command(description = "Flip a coin")
    public String coin() {
        return RandomUtils.nextBoolean() ? "heads" : "tails";
    }
}
