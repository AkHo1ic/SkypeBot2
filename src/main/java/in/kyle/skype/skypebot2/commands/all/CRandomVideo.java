package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Command;
import in.kyle.ezskypeezlife.Chat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CRandomVideo {
    
    @Command(description = "Get a really random video")
    public String randomVideo() throws IOException {
        String url = getRandomURL();
        return "Random video: " + Chat.link(url, url);
    }
    
    private String getRandomURL() throws IOException {
        Document doc = Jsoup.connect("http://www.petittube.com").get();
        String s = doc.getElementsByTag("IFRAME").first().attr("src");
        String youtube = "http://youtube.com/watch?v=";
        return youtube + s.substring(s.lastIndexOf("/") + 1, s.indexOf("?"));
    }
}
