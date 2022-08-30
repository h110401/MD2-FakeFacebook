package view;

import config.Config;
import controller.ChatBoxController;
import controller.MessageController;
import controller.NotificationController;
import controller.UserController;
import dto.request.MessageDTO;
import dto.response.ResponseMessenger;
import model.messenger.ChatBox;
import model.messenger.Message;
import model.account.User;
import plugin.Alert;
import plugin.Menu;

import static plugin.ConsoleColors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMessenger {

    private final ChatBoxController chatBoxController = new ChatBoxController();
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final MessageController messageController = new MessageController();
    private final NotificationController notificationController = new NotificationController();

    public void menu() {

        mainMenu(0);

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

    public void mainMenu(int index) {
        List<ChatBox> chatBoxes = chatBoxController.getYourChatBoxes();
        int numberNotice = notificationController.getUnseenNotificationsCount();

        if (chatBoxes.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┃         ┗━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃          There's no one chatting           ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃ " + WHITE_BRIGHT + "send msg" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "reply" + BLUE_BRIGHT + "  ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "find" + BLUE_BRIGHT + "    ┃  ┃");
            System.out.println("┃  ┃   ( 6 )  ┃   ( 7 )  ┃   ( 8 )  ┃   ( 9 )   ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + WHITE + "< 10. Previous |" + WHITE_BRIGHT + " ( 1 // 1 ) " + WHITE + "| 11.  Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┃         ┗━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");

            Message last = chatBoxes.get(index).getMessages().get(chatBoxes.get(index).getMessages().size() - 1);
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s        ID: %3d" + BLUE_BRIGHT + "  ┃  ┃\n", chatBoxController.getUserChatWith(chatBoxes.get(index)).getName(), chatBoxes.get(index).getId());
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s%15s" + BLUE_BRIGHT + "  ┃  ┃\n", last.getContent(), messageController.getTimePassed(last.getId()));
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");

            if ((index + 1) > chatBoxes.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                last = chatBoxes.get(index + 1).getMessages().get(chatBoxes.get(index + 1).getMessages().size() - 1);
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s        ID: %3d" + BLUE_BRIGHT + "  ┃  ┃\n", chatBoxController.getUserChatWith(chatBoxes.get(index + 1)).getName(), chatBoxes.get(index + 1).getId());
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s%15s" + BLUE_BRIGHT + "  ┃  ┃\n", last.getContent(), messageController.getTimePassed(last.getId()));
                System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            }
            if ((index + 2) > chatBoxes.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                last = chatBoxes.get(index + 2).getMessages().get(chatBoxes.get(index + 2).getMessages().size() - 1);
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s        ID: %3d" + BLUE_BRIGHT + "  ┃  ┃\n", chatBoxController.getUserChatWith(chatBoxes.get(index + 2)).getName(), chatBoxes.get(index + 2).getId());
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s%15s" + BLUE_BRIGHT + "  ┃  ┃\n", last.getContent(), messageController.getTimePassed(last.getId()));
                System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            }
            if ((index + 3) > chatBoxes.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                last = chatBoxes.get(index + 3).getMessages().get(chatBoxes.get(index + 3).getMessages().size() - 1);
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s        ID: %3d" + BLUE_BRIGHT + "  ┃  ┃\n", chatBoxController.getUserChatWith(chatBoxes.get(index + 3)).getName(), chatBoxes.get(index + 3).getId());
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-25s%15s" + BLUE_BRIGHT + "  ┃  ┃\n", last.getContent(), messageController.getTimePassed(last.getId()));
            }

            System.out.println("┃  ┣━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃ " + WHITE_BRIGHT + "send msg" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "reply" + BLUE_BRIGHT + "  ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "find" + BLUE_BRIGHT + "    ┃  ┃");
            System.out.println("┃  ┃   ( 6 )  ┃   ( 7 )  ┃   ( 8 )  ┃   ( 9 )   ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 10. Previous |" + WHITE_BRIGHT + " ( " + (index / 4 + 1) + " // " + (chatBoxes.size() % 4 == 0 && chatBoxes.size() != 0 ? chatBoxes.size() / 4 : chatBoxes.size() / 4 + 1) + " ) " + (index + 5 > (chatBoxes.size()) ? WHITE : WHITE_BRIGHT) + "| 11.  Next  >" + BLUE_BRIGHT + "    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printCyanAlert("Already in message page");
                break;
            case 2:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewFriend().menu();
                break;
            case 3:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().mainMenu(0);
                break;
            case 4:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewNotification().formNotification(0);
                break;
            case 5:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().menu();
                break;
            case 6:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printCyanAlert("Choose friend to start chatting");
                new ViewFriend().formFriendList(0);
                break;
            case 7:
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.print("┃  Enter id to reply: ");
                int id = Config.getValidInteger();
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (id == -1) {
                    Alert.printRedAlert("ID not valid!!!");
                    break;
                }
                formChatBox(chatBoxController.findById(id), 0);
                break;
            case 8:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                formDeleteChatBox();
                break;
            case 9:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                formSearchChatBox();
                break;
            case 10:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous chat box");
                } else {
                    index -= 4;
                }
                break;
            case 11:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index + 5 > (chatBoxes.size())) {
                    Alert.printCyanAlert("There's no more next chat box");
                } else {
                    index += 4;
                }
                break;
            default:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printRedAlert("Invalid choice!");
        }
        mainMenu(index);
    }

    private void formSearchChatBox() {

    }

    private void formDeleteChatBox() {

    }

    public void formSendMessage() {
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
        formChatBox(chatBox, 0);

    }

    private void formChatBox(ChatBox chatBox, int index) {

        User userChatWith = chatBoxController.getUserChatWith(chatBox);
        int numberNotice = notificationController.getUnseenNotificationsCount();

        List<Message> messageList = new ArrayList<>(chatBox.getMessages());
        Collections.reverse(messageList);

        if (messageList.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┃         ┗━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-29s " + BLUE_BRIGHT + " ┃  ┃\n", userChatWith.getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃          There's no message !!             ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃   send msg   ┃    delete    ┃     back     ┃  ┃");
            System.out.println("┃  ┃     ( 6 )    ┃    ( 7 )     ┃     ( 8 )    ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + WHITE + "< 9.  Previous |" + WHITE_BRIGHT + " ( 1 // 1 ) " + WHITE + "| 10.  Next  >" + BLUE_BRIGHT + "    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┃         ┗━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-29s " + BLUE_BRIGHT + " ┃  ┃\n", userChatWith.getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");

            if ((index + 4) > messageList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
            } else if (messageList.get(index + 4).getIdUser() == currentUser.getId()) {
                System.out.printf("┃  ┃  " + WHITE + "ID:%3d" + WHITE_BRIGHT + " %33s" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 4).getId(), messageList.get(index + 4).getContent());
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-33s " + WHITE + "ID:%3d" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 4).getContent(), messageList.get(index + 4).getId());
            }

            System.out.println("┃  ┃                                            ┃  ┃");
            if ((index + 3) > messageList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
            } else if (messageList.get(index + 3).getIdUser() == currentUser.getId()) {
                System.out.printf("┃  ┃  " + WHITE + "ID:%3d" + WHITE_BRIGHT + " %33s" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 3).getId(), messageList.get(index + 3).getContent());
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-33s " + WHITE + "ID:%3d" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 3).getContent(), messageList.get(index + 3).getId());
            }

            System.out.println("┃  ┃                                            ┃  ┃");
            if ((index + 2) > messageList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
            } else if (messageList.get(index + 2).getIdUser() == currentUser.getId()) {
                System.out.printf("┃  ┃  " + WHITE + "ID:%3d" + WHITE_BRIGHT + " %33s" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 2).getId(), messageList.get(index + 2).getContent());
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-33s " + WHITE + "ID:%3d" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 2).getContent(), messageList.get(index + 2).getId());
            }

            System.out.println("┃  ┃                                            ┃  ┃");
            if ((index + 1) > messageList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
            } else if (messageList.get(index + 1).getIdUser() == currentUser.getId()) {
                System.out.printf("┃  ┃  " + WHITE + "ID:%3d" + WHITE_BRIGHT + " %33s" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 1).getId(), messageList.get(index + 1).getContent());
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-33s " + WHITE + "ID:%3d" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index + 1).getContent(), messageList.get(index + 1).getId());
            }

            System.out.println("┃  ┃                                            ┃  ┃");
            if (messageList.get(index).getIdUser() == currentUser.getId()) {
                System.out.printf("┃  ┃  " + WHITE + "ID:%3d" + WHITE_BRIGHT + " %33s" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index).getId(), messageList.get(index).getContent());
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "%-33s " + WHITE + "ID:%3d" + BLUE_BRIGHT + "  ┃  ┃\n", messageList.get(index).getContent(), messageList.get(index).getId());
            }

            System.out.println("┃  ┣━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃   " + WHITE_BRIGHT + "send msg" + BLUE_BRIGHT + "   ┃    " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "    ┃     " + WHITE_BRIGHT + "back" + BLUE_BRIGHT + "     ┃  ┃");
            System.out.println("┃  ┃     ( 6 )    ┃    ( 7 )     ┃     ( 8 )    ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 9.  Previous |" + WHITE_BRIGHT + " ( " + (index / 5 + 1) + " // " + (messageList.size() % 5 == 0 ? messageList.size() / 5 : messageList.size() / 5 + 1) + " ) " + (index + 6 > (messageList.size()) ? WHITE : WHITE_BRIGHT) + "| 10.  Next  >" + BLUE_BRIGHT + "    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewMessenger().mainMenu(0);
                break;
            case 2:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewFriend().menu();
                break;
            case 3:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().mainMenu(0);
                break;
            case 4:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewNotification().formNotification(0);
                break;
            case 5:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().menu();
                break;
            case 6:
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.print("┃  Enter content: ");
                String content = Config.getUnEmptyString();
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                MessageDTO msg = new MessageDTO(content, userChatWith.getId());
                messageController.sendMessage(msg);
                Alert.printCyanAlert("Send message success!");
                break;
            case 7:
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.print("┃  Enter message id to delete: ");
                int messageId = Config.getValidInteger();
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                ResponseMessenger messenger = messageController.deleteMessage(chatBox, messageId);
                if (messenger.getMessage().equals("id_mismatch")) {
                    Alert.printRedAlert("ID mismatch!");
                } else {
                    Alert.printCyanAlert("Deleted message " + messageId);
                }
                break;
            case 8:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                return;
            case 9:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous messages");
                } else {
                    index -= 5;
                }
                break;
            case 10:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index + 6 > (messageList.size())) {
                    Alert.printCyanAlert("There's no more next message");
                } else {
                    index += 5;
                }
                break;
            default:
                Alert.printRedAlert("Invalid choice!");
        }
        formChatBox(chatBox, index);

    }


}
