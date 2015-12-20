package in.kyle.skype.skypebot2;

import lombok.Data;

import java.util.Map;

/**
 * Created by Kyle on 11/28/2015.
 */
@Data
public class SkypeConfiguration {
    
    private String username;
    private String password;
    private String relogInterval;
    private Map<String, String> apiKeys;
}
