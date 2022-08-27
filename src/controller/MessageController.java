package controller;

import dto.request.MessageDTO;
import dto.response.ResponseMessenger;
import model.Message;
import model.Notification;
import model.account.User;
import service.message.IMessageService;
import service.message.MessageServiceIMPL;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageController {
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final IMessageService messageService = new MessageServiceIMPL();
    private final INotificationService notificationService = new NotificationServiceIMPL();

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

        String content = currentUser.getName() + " has sent you a message!";
        Notification notification = new Notification(
                notificationService.getLastId(),
                msg.getIdTo(),
                content
        );
        notificationService.save(notification);
        return new ResponseMessenger("success");
    }

    public List<Message> getYourMessages() {
        List<Message> messages = new ArrayList<>();

        for (Message message : getMessageList()) {
            if (message.getIdTo() == currentUser.getId()) {
                messages.add(message);
            }
        }

        return messages;
    }
}
