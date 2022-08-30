package controller;

import dto.request.MessageDTO;
import dto.response.ResponseMessenger;
import model.messenger.ChatBox;
import model.messenger.Message;
import model.Notification;
import model.account.User;
import service.chat_box.ChatBoxServiceIMPL;
import service.chat_box.IChatBoxService;
import service.message.IMessageService;
import service.message.MessageServiceIMPL;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.*;

public class MessageController {
    private final IUserService userService = new UserServiceIMPL();
    private final User currentUser = userService.getCurrentUser();
    private final IMessageService messageService = new MessageServiceIMPL();
    private final INotificationService notificationService = new NotificationServiceIMPL();
    private final IChatBoxService chatBoxService = new ChatBoxServiceIMPL();

    public List<Message> getMessageList() {
        return messageService.findAll();
    }

    public ResponseMessenger sendMessage(MessageDTO msg) {
        if (!currentUser.getProfile().getFriendList().contains(msg.getIdTo())) {
            return new ResponseMessenger("id_mismatch");
        }

        Message message = new Message(
                messageService.getLastId(),
                currentUser.getId(),
                msg.getIdTo(),
                msg.getContent(),
                new Date()
        );
        messageService.save(message);

        ChatBox chatBox = chatBoxService.findChatBox(currentUser.getId(), msg.getIdTo());
        if (chatBox != null) {
            chatBox.getMessages().add(message);
        } else {
            Set<Integer> idUsers = new HashSet<>();
            idUsers.add(currentUser.getId());
            idUsers.add(msg.getIdTo());
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            chatBox = new ChatBox(chatBoxService.getLastId(), idUsers, messages);
        }

        chatBoxService.save(chatBox);

        String content = currentUser.getName() + " sent you a message!";
        Notification notification = new Notification(
                notificationService.getLastId(),
                msg.getIdTo(),
                content
        );
        notificationService.save(notification);

        return new ResponseMessenger("success");
    }

    public String getTimePassed(int id) {
        Message message = messageService.findById(id);
        Date from = message.getTime();
        Date to = new Date();
        long timePassed = to.getTime() - from.getTime();
        int second = (int) (timePassed / 1000) % 60;
        int minute = (int) (timePassed / (1000 * 60) % 60);
        int hour = (int) (timePassed / (1000 * 60 * 60) % 24);
        int day = (int) (timePassed / (1000 * 60 * 60 * 24));
        return (day != 0 ? day + " day" : (hour != 0 ? hour + " hour" : (minute != 0 ? minute + " minute" : second + " second"))) + " ago";
    }

    public ResponseMessenger deleteMessage(ChatBox chatBox, int messageId) {
        boolean contain = false;
        for (Message message : chatBox.getMessages()) {
            if (message.getId() == messageId) {
                contain = true;
                break;
            }
        }
        if (!contain) {
            return new ResponseMessenger("id_mismatch");
        }
        if(messageService.findById(messageId).getIdUser() != currentUser.getId()) {
            return new ResponseMessenger("id_mismatch");
        }

        messageService.remove(messageId);

        for (Message message : chatBox.getMessages()) {
            if (message.getId() == messageId) {
                chatBox.getMessages().remove(message);
                break;
            }
        }
        new ChatBoxServiceIMPL().updateData();
        return new ResponseMessenger("success");
    }
}
