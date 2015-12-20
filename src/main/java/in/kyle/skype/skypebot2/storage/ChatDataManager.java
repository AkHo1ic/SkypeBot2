package in.kyle.skype.skypebot2.storage;

import in.kyle.ezskypeezlife.EzSkype;
import lombok.Cleanup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 12/15/2015.
 */
public class ChatDataManager {
    
    private final File saveFolder;
    private final Map<String, ChatData> data;
    
    public ChatDataManager(File saveFolder) {
        this.saveFolder = saveFolder;
        this.data = new HashMap<>();
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }
    }
    
    public ChatData getData(String longId) {
        if (data.containsKey(longId)) {
            return data.get(longId);
        } else {
            File file = new File(saveFolder, longId + ".json");
            ChatData chatData;
            if (file.exists()) {
                try {
                    chatData = EzSkype.GSON.fromJson(new FileReader(file), ChatData.class);
                } catch (FileNotFoundException ignored) {
                    chatData = new ChatData(longId);
                }
            } else {
                chatData = new ChatData(longId);
            }
            data.put(longId, chatData);
            return chatData;
        }
    }
    
    public void save(ChatData chatData) throws IOException {
        File file = new File(saveFolder, chatData.getChatId() + ".json");
        @Cleanup BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(EzSkype.GSON.toJson(chatData));
    }
    
    public void saveAll() {
        data.values().forEach(chatData -> {
            if (chatData.isChanged()) {
                try {
                    save(chatData);
                } catch (IOException e) {
                    EzSkype.LOGGER.error("Could not save chat data " + chatData, e);
                }
            }
        });
    }
}
