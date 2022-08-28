package service.message;

import config.Config;
import model.messenger.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceIMPL implements IMessageService {

    private static final String PATH_MESSAGE = "src/data/message.txt";
    private static final Config<List<Message>> config = new Config<>();
    private static List<Message> messageList = config.read(PATH_MESSAGE);

    static {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
    }

    @Override
    public List<Message> findAll() {
        return messageList;
    }

    @Override
    public void save(Message message) {
        messageList.add(message);
        updateData();
    }

    @Override
    public Message findById(int id) {
        for (Message message : messageList) {
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        messageList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_MESSAGE, messageList);
    }

    @Override
    public int getLastId() {
        return messageList.isEmpty() ? 1 : messageList.get(messageList.size() - 1).getId() + 1;
    }
}
