package view;

import config.Config;
import controller.MessageController;
import controller.UserController;
import dto.request.MessageDTO;
import dto.response.ResponseMessenger;
import model.Message;
import model.account.User;
import plugin.Menu;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMessenger {

    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final MessageController messageController = new MessageController();

    public void menu() {
        showChatBox();
        System.out.println(messageController.getMessageList());
        Menu menu = new Menu();
        menu.addHeader("MESSENGER");
        menu.addChoice("Choose a ChatBox");
        menu.addChoice("Delete a ChatBox");
        menu.addChoice("Send a message to your friend");
        menu.addChoice("Back");
        menu.print();

        switch (Config.getValidInteger()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                this.formSendMessage();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice");
        }
        menu();
    }

    private void formSendMessage() {
        List<User> friends = currentUser.getProfile().getFriendList().stream().map(userController::findById).sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        System.out.printf("%-5s%-15s%n", "ID", "NAME");
        for (User friend : friends) {
            System.out.printf("%-5d%-15s%n", friend.getId(), friend.getName());
        }
        System.out.println("Enter friend id to send message");
        int idTo = Config.getValidInteger();
        String content = Config.getUnEmptyString();
        MessageDTO msg = new MessageDTO(content, idTo);
        ResponseMessenger messenger = messageController.sendMessage(msg);
        switch (messenger.getMessage()) {
            case "success":
                System.out.println("Success!");
                break;
            case "id_mismatch":
                System.err.println("Id mismatch!");
                break;
        }
    }

    private void showChatBox() {
        List<Message> yourMessages = messageController.getYourMessages();
        for (Message yourMessage : yourMessages) {
            System.out.println(yourMessage.getContent());
        }
    }

}
