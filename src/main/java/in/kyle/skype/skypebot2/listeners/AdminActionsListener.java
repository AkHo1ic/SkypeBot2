package in.kyle.skype.skypebot2.listeners;

import in.kyle.ezskypeezlife.api.user.SkypeLocalUser;
import in.kyle.ezskypeezlife.events.conversation.SkypeConversationUpdateTopicEvent;
import in.kyle.ezskypeezlife.events.conversation.SkypeConversationUserJoinEvent;
import in.kyle.skype.skypebot2.SkypeBot;
import in.kyle.skype.skypebot2.storage.ChatData;
import in.kyle.skype.skypebot2.storage.ChatDataManager;

/**
 * Created by Kyle on 12/17/2015.
 */
public class AdminActionsListener {
    
    private final ChatDataManager chatDataManager;
    
    public AdminActionsListener(SkypeBot bot) {
        this.chatDataManager = bot.getChatDataManager();
    }
    
    public void onPlayerJoin(SkypeConversationUserJoinEvent e) {
        ChatData chatData = chatDataManager.getData(e.getConversation().getLongId());
        if (chatData.getBanned().contains(e.getUser())) {
            e.getConversation().sendMessage("You are banned");
            e.getConversation().kick(e.getUser());
        }
    }
    
    public void onTopicChange(SkypeConversationUpdateTopicEvent e) {
        ChatData chatData = chatDataManager.getData(e.getConversation().getLongId());
        String topic = chatData.getTopic();
        if (!(e.getUser() instanceof SkypeLocalUser)) {
            if (topic != null) {
                e.getConversation().changeTopic(topic);
                e.getConversation().sendMessage("Topic is locked");
            }
        }
    }
}
