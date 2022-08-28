package view;

import config.Config;
import controller.ChatBoxController;
import controller.MessageController;
import controller.UserController;
import dto.request.MessageDTO;
import dto.response.ResponseMessenger;
import model.messenger.ChatBox;
import model.messenger.Message;
import model.account.User;
import plugin.Menu;

import static plugin.ConsoleColors.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMessenger {

    private final ChatBoxController chatBoxController = new ChatBoxController();
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final MessageController messageController = new MessageController();

    public void menu() {
        Menu menu = new Menu();
        menu.addHeader("MESSENGER");
        menu.addChoice("Show all ChatBox");
        menu.addChoice("Delete a ChatBox");
        menu.addChoice("Send a message to your friend");
        menu.addChoice("Back");
        menu.print();

        switch (Config.getValidInteger()) {
            case 1:
                showChatBox();
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
        System.out.println("Enter content");
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
        List<ChatBox> chatBoxes = chatBoxController.getYourChatBoxes();
        for (ChatBox chatBox : chatBoxes) {
            System.out.println("ID: " + chatBox.getId());
            System.out.println("User: " + chatBoxController.getUserChatWith(chatBox).getName());
            Message last = chatBox.getMessages().get(chatBox.getMessages().size() - 1);
            System.out.println(last.getContent());
            System.out.println(messageController.getTimePassed(last.getId()) + "\n");
        }
        System.out.println("Enter chatBox id to show details / 0 to back");
        int id = Config.getValidInteger();
        if (id == 0) return;

        ChatBox chatBox = chatBoxController.findById(id);
        formChatBox(chatBox);

    }

    private void formChatBox(ChatBox chatBox) {
        User userChatWith = chatBoxController.getUserChatWith(chatBox);
        System.out.println(userChatWith.getName());
        for (Message message : chatBox.getMessages()) {
            String idText = message.getIdUser() == currentUser.getId() ? BLUE + "%30s" + RESET : "%-30s";
            System.out.printf(idText + "\n", "ID: " + message.getId());
            String messageText = message.getIdUser() == currentUser.getId() ? BLUE + "%30s" + RESET : "%-30s";
            System.out.printf(messageText + "\n", message.getContent());
        }
        System.out.println("1. Send message");
        System.out.println("2. Delete message");
        System.out.println("3. Back");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("Enter content");
                String content = Config.getUnEmptyString();
                MessageDTO msg = new MessageDTO(content, userChatWith.getId());
                messageController.sendMessage(msg);
                break;
            case 2:
                System.out.println("Enter message id to delete");
                int messageId = Config.getValidInteger();
                ResponseMessenger messenger = messageController.deleteMessage(chatBox, messageId);
                if (messenger.getMessage().equals("id_mismatch")) System.err.println("ID mismatch!");
                break;
            case 3:
                return;
        }
        formChatBox(chatBox);

    }


}
