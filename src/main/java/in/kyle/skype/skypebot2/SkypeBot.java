package in.kyle.skype.skypebot2;

import in.kyle.commands.CommandSet;
import in.kyle.ezskypeezlife.EzSkype;
import in.kyle.ezskypeezlife.api.conversation.SkypeConversation;
import in.kyle.ezskypeezlife.api.errors.SkypeCaptcha;
import in.kyle.ezskypeezlife.api.errors.SkypeErrorHandler;
import in.kyle.ezskypeezlife.api.skype.SkypeCredentials;
import in.kyle.ezskypeezlife.api.user.SkypeLocalUser;
import in.kyle.ezskypeezlife.api.user.SkypeUser;
import in.kyle.ezskypeezlife.exception.SkypeException;
import in.kyle.skype.skypebot2.commands.EzSkypeHook;
import in.kyle.skype.skypebot2.commands.SkypeUserMessages;
import in.kyle.skype.skypebot2.commands.SkypeUserPermissions;
import in.kyle.skype.skypebot2.listeners.AdminActionsListener;
import in.kyle.skype.skypebot2.storage.ChatData;
import in.kyle.skype.skypebot2.storage.ChatDataManager;
import in.kyle.skype.skypebot2.util.CommandList;
import in.kyle.skype.skypebot2.util.FileUtils;
import lombok.Data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kyle on 11/28/2015.
 */
@Data
public class SkypeBot implements SkypeErrorHandler {
    
    private final EzSkype ezSkype;
    private final InputHandler inputHandler;
    private final SkypeConfiguration skypeConfiguration;
    private final CommandSet<SkypeUser, SkypeConversation> commandSet;
    private final ChatDataManager chatDataManager;
    
    public SkypeBot() throws IOException {
        inputHandler = new InputHandler();
        
        File configurationFile = new File("skype.json");
        if (!configurationFile.exists()) {
            FileUtils.copy("skype.json", configurationFile);
        }
        
        chatDataManager = new ChatDataManager(new File("skype-data"));
        skypeConfiguration = EzSkype.GSON.fromJson(new FileReader(configurationFile), SkypeConfiguration.class);
        ezSkype = new EzSkype(new SkypeCredentials(skypeConfiguration.getUsername(), skypeConfiguration.getPassword()));
        commandSet = new CommandSet<>(new SkypeUserMessages(), new SkypeUserPermissions());
        commandSet.addParameterType(SkypeUser.class, (k, v) -> k);
        commandSet.addParameterType(SkypeBot.class, (k, v) -> this);
        commandSet.addParameterType(EzSkype.class, (k, v) -> ezSkype);
        commandSet.addParameterType(ChatData.class, (k, v) -> chatDataManager.getData(v.getLongId()));
        commandSet.addParameterType(SkypeLocalUser.class, (k, v) -> ezSkype.getLocalUser());
        commandSet.addParameterType(SkypeConversation.class, (k, v) -> v);
        
        CommandList.COMMANDS.forEach(o -> {
            try {
                commandSet.register(o);
            } catch (NullPointerException e) {
                EzSkype.LOGGER.error("Could not load command {} reason {}", o.getClass().getName(), e.getCause());
            }
        });
        
        try {
            ezSkype.login();
            ezSkype.getEventManager().registerEvents(new EzSkypeHook(commandSet, chatDataManager));
            ezSkype.getEventManager().registerEvents(new AdminActionsListener(this));
            ezSkype.joinSkypeConversation("https://join.skype.com/C7cv09jyAcbx");
        } catch (SkypeException e) {
            EzSkype.LOGGER.error("Could not login to Skype", e);
        }
        
        new Thread(() -> {
            while (ezSkype.getActive().get()) {
                synchronized (chatDataManager) {
                    chatDataManager.saveAll();
                }
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }
    
    public static void main(String[] args) throws IOException {
        new SkypeBot();
    }
    
    @Override
    public String solve(SkypeCaptcha skypeCaptcha) {
        System.out.println("In order to continue this login you must complete this captcha");
        System.out.println(skypeCaptcha.getUrl());
        System.out.print("Solution: ");
        Object lock = new Object();
        inputHandler.addHook(s -> {
            skypeCaptcha.setSolution(s);
            lock.notify();
        });
        try {
            lock.wait();
        } catch (InterruptedException ignored) {
        }
        return skypeCaptcha.getSolution();
    }
    
    @Override
    public String setNewPassword() {
        System.out.println("Your Skype password must be changed");
        return null;
    }
}
