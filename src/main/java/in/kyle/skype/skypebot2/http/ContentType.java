package in.kyle.skype.skypebot2.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Kyle on 11/16/2015.
 */
@AllArgsConstructor
public enum ContentType {
    JSON("application/json"),
    WWW_FORM("application/x-www-form-urlencoded"),
    OCTET_STREAM("application/octet-stream"),
    TEXT_PLAIN("text/plain");
    
    @Getter
    private final String value;
}
