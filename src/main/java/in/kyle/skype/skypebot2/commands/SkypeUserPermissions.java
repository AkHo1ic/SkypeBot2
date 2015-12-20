package in.kyle.skype.skypebot2.commands;

import in.kyle.commands.CommandPermission;
import in.kyle.commands.PermissionsChecker;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.user.SkypeUser;

/**
 * Created by Kyle on 12/15/2015.
 */
public class SkypeUserPermissions implements PermissionsChecker<SkypeUser, SkypeConversation> {
    
    @Override
    public boolean hasPermission(SkypeUser user, SkypeConversation conversation, CommandPermission commandPermission) {
        if (commandPermission.getName().equals("owner")) {
            return user.getUsername().equals("rulingkyle1496");
        } else if (commandPermission.getName().equals("admin")) {
            return conversation.isAdmin(user);
        } else {
            return true;
        }
    }
}
