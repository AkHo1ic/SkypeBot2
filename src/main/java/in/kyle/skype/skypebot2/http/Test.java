package in.kyle.skype.skypebot2.http;

import java.io.IOException;

/**
 * Created by Kyle on 11/16/2015.
 */
public class Test {
    
    public static void main(String[] args) throws IOException {
        String s = HttpRequest.builder().url("http://www.insultgenerator.org/").build().makeRequestText();
        System.out.println(s);
    }
}
