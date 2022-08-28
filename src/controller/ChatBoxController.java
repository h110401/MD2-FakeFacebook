package controller;

import model.account.User;
import model.messenger.ChatBox;
import service.chat_box.ChatBoxServiceIMPL;
import service.chat_box.IChatBoxService;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class ChatBoxController {

    private final IUserService userService = new UserServiceIMPL();
    private final User currentUser = userService.getCurrentUser();
    private final IChatBoxService chatBoxService = new ChatBoxServiceIMPL();

    public List<ChatBox> getYourChatBoxes() {
        List<ChatBox> chatBoxes = new ArrayList<>();

        for (int i : currentUser.getProfile().getFriendList()) {
            ChatBox chatBox = chatBoxService.findChatBox(i, currentUser.getId());
            if (chatBox != null) {
                chatBoxes.add(chatBox);
            }
        }

        return chatBoxes;
    }

    public User getUserChatWith(ChatBox chatBox) {
        List<Integer> chatBoxes = new ArrayList<>(chatBox.getIdUsers());
        if (userService.findById(chatBoxes.get(0)).getId() == currentUser.getId()) {
            return userService.findById(chatBoxes.get(1));
        } else {
            return userService.findById(chatBoxes.get(0));
        }
    }

    public ChatBox findById(int id) {
        return chatBoxService.findById(id);
    }
}
