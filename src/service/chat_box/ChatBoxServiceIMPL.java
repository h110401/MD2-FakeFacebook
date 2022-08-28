package service.chat_box;

import config.Config;
import model.messenger.ChatBox;

import java.util.ArrayList;
import java.util.List;

public class ChatBoxServiceIMPL implements IChatBoxService {

    private static final String PATH_CHAT_BOX = "src/data/chat_box.txt";
    private static final Config<List<ChatBox>> config = new Config<>();
    private static List<ChatBox> chatBoxList = config.read(PATH_CHAT_BOX);

    static {
        if (chatBoxList == null) {
            chatBoxList = new ArrayList<>();
        }
    }

    @Override
    public List<ChatBox> findAll() {
        return chatBoxList;
    }

    @Override
    public void save(ChatBox newChatBox) {
        ChatBox chatBox = findById(newChatBox.getId());
        if (chatBox == null) {
            chatBoxList.add(newChatBox);
        } else {
            chatBox.setIdUsers(newChatBox.getIdUsers());
            chatBox.setMessages(newChatBox.getMessages());
        }
        updateData();
    }

    @Override
    public ChatBox findById(int id) {
        for (ChatBox chatBox : chatBoxList) {
            if (chatBox.getId() == id) {
                return chatBox;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        chatBoxList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_CHAT_BOX, chatBoxList);
    }

    @Override
    public int getLastId() {
        return chatBoxList.isEmpty() ? 1 : chatBoxList.get(chatBoxList.size() - 1).getId() + 1;
    }

    @Override
    public ChatBox findChatBox(int idUser, int idTo) {
        for (ChatBox chatBox : chatBoxList) {
            if (chatBox.getIdUsers().contains(idUser) && chatBox.getIdUsers().contains(idTo)) {
                return chatBox;
            }
        }
        return null;
    }
}
