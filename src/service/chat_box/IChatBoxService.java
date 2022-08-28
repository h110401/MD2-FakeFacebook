package service.chat_box;

import model.messenger.ChatBox;
import service.IGenericService;

public interface IChatBoxService extends IGenericService<ChatBox> {
    ChatBox findChatBox(int idUser, int idTo);
}
