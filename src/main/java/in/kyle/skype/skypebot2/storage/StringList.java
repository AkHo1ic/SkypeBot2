package in.kyle.skype.skypebot2.storage;

import in.kyle.ezskypeezlife.api.user.SkypeUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 12/13/2015.
 */
public class StringList {
    
    private List<String> strings;
    
    public StringList() {
        strings = new ArrayList<>();
    }
    
    public boolean contains(SkypeUser user) {
        return contains(user.getUsername());
    }
    
    public boolean contains(String string) {
        return strings.contains(string);
    }
    
    public void add(SkypeUser user) {
        add(user.getUsername());
    }
    
    public void add(String string) {
        strings.add(string);
    }
    
    public void remove(SkypeUser user) {
        remove(user.getUsername());
    }
    
    public void remove(String string) {
        strings.remove(string);
    }
}
