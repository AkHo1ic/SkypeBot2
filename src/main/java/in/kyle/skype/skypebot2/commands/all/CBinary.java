package in.kyle.skype.skypebot2.commands.all;

import in.kyle.commands.Bind;
import in.kyle.commands.Command;
import in.kyle.skype.skypebot2.util.EncodeMethod;

/**
 * Created by Kyle on 12/13/2015.
 */
public class CBinary {
    
    @Command(description = "Encode a string into binary")
    public String binary(EncodeMethod encodeMethod, @Bind(name = "text", infinite = true) String text) {
        if (encodeMethod == EncodeMethod.ENCODE) {
            return toBinary(text);
        } else {
            return fromBinary(text);
        }
    }
    
    private String toBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }
    
    private String fromBinary(String text) {
        StringBuilder output = new StringBuilder();
        text = text.replaceAll(" ", "");
        for (int i = 0; i <= text.length() - 8; i += 8) {
            int k = Integer.parseInt(text.substring(i, i + 8), 2);
            output.append((char) k);
        }
        return output.toString();
    }
}
