package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.skype.skypebot2.http.HttpRequest;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CInsult {
    
    @Command(description = "Generate a insult")
    public String insult() throws IOException {
        Document html = HttpRequest.builder().url("http://www.insultgenerator.org/").build().makeRequestSoup();
        return html.getElementsByClass("wrap").text();
    }
}
