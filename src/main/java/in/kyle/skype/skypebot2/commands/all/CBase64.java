package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.skype.skypebot2.util.EncodeMethod;

import java.util.Base64;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CBase64 {
    
    @Command(description = "Base64 encryption")
    public String base64(EncodeMethod encodeMethod, @Bind(name = "text", infinite = true) String text) {
        if (encodeMethod == EncodeMethod.ENCODE) {
            return Base64.getEncoder().encodeToString(text.getBytes());
        } else {
            return new String(Base64.getDecoder().decode(text));
        }
    }
    
    
}
