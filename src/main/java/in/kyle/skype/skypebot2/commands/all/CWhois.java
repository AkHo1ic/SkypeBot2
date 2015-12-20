package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import in.kyle.skype.skypebot2.apis.DomainWhoisLookup;
import in.kyle.skype.skypebot2.util.HasteBin;

import java.io.IOException;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CWhois {
    
    @Command(description = "Get domain info")
    public String whois(@Bind(name = "domain") String domain) throws IOException {
        String whois = new DomainWhoisLookup(domain).getWhois();
        if (whois.length() > 3) {
            return "Whois: " + Chat.link("whois", HasteBin.postString(whois));
        } else {
            return "No results found";
        }
    }
}
