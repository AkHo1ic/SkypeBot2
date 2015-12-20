package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.skype.skypebot2.SkypeBot;
import in.kyle.skype.skypebot2.apis.UrlShortener;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CUrl {
    
    @Command(description = "Shorten a URL")
    public String url(@Bind(name = "url") String url, SkypeBot skypeBot) throws IOException {
        url = Jsoup.parse(url).text();
        return new UrlShortener().shorten(url, skypeBot.getSkypeConfiguration().getApiKeys().get("bitly"));
    }
}
