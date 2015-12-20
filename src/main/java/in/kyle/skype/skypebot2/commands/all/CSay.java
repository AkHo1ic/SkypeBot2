package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import org.jsoup.Jsoup;

/**
 * Created by Kyle on 12/17/2015.
 */
public class CSay {
    
    @Command(description = "Have the bot say things", subCommands = {"html", "text"})
    public void say() {
    }
    
    @Command(description = "Say HTML")
    public String html(@Bind(name = "string", infinite = true) String string) {
        return string;
    }
    
    @Command(description = "Say text")
    public String text(@Bind(name = "string", infinite = true) String string) {
        return Jsoup.parse(string).text();
    }
}
