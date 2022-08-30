package view;

import config.Config;
import controller.*;
import dto.response.ResponseMessenger;
import model.account.User;
import model.friend.Friend;
import model.friend.FriendStatus;
import model.post.Post;
import plugin.Alert;

import java.util.ArrayList;
import java.util.List;

import static plugin.ConsoleColors.*;
import static plugin.ConsoleColors.RESET;

public class ViewFriend {

    private final FriendController friendController = new FriendController();
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final CommentController commentController = new CommentController();
    private final LikeController likeController = new LikeController();
    private final PostController postController = new PostController();
    private final NotificationController notificationController = new NotificationController();

    public void menu() {

        int numberNotice = notificationController.getUnseenNotificationsCount();

        int numberRequest = friendController.getRequestsToId(currentUser.getId()).size();

        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
        System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
        System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
        System.out.println("┣━━━━━━━━━┛         ┗━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
        System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
        System.out.println("┃  ┃     " + WHITE_BRIGHT + "6. Friend List" + BLUE_BRIGHT + "                         ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃     " + WHITE_BRIGHT + "7. Friend Request" + BLUE_BRIGHT + "  " + (numberRequest == 0 ? "   " : RED + "(" + numberRequest + ")" + BLUE_BRIGHT) + "                 ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃     " + WHITE_BRIGHT + "8. Search User" + BLUE_BRIGHT + "                         ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
        System.out.println("┃    " + WHITE + "< 10. Previous | ( 1 // 1 ) | 11. Next  >" + BLUE_BRIGHT + "     ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        switch (choice) {
            case 1:
                new ViewMessenger().menu();
                break;
            case 2:
                Alert.printRedAlert("Already in friend menu!");
                break;
            case 3:
                new ViewHome().mainMenu(0);
                break;
            case 4:
                new ViewNotification().formNotification(0);
                break;
            case 5:
                new ViewHome().menu();
                break;
            case 6:
                formFriendList(0);
                break;
            case 7:
                formFriendRequest();
                break;
            case 8:
                formSearchUser();
                break;
            default:
                Alert.printRedAlert("Invalid choice!");
        }
        menu();
    }

    private void formSearchUser() {
        System.out.println("*****FORM SEARCH*****");
        System.out.println("Enter user name:");
        String search = Config.scanner().nextLine();
        List<User> userSearch = userController.findUsers(search);

        if (userSearch.isEmpty()) {
            System.out.println("Search name not found");
            return;
        }

        System.out.printf("%-5s%-15s%-10s%s%n", "ID", "USERNAME", "NAME", "STATUS");
        for (User user : userSearch) {
            FriendStatus status = friendController.getFriendStatus(currentUser.getId(), user.getId());
            System.out.printf("%-5d%-15s%-10s%s%n", user.getId(), user.getUsername(), user.getName(), status);
        }

        System.out.println("Enter id");
        int id = Config.getValidInteger();
        if (userSearch.stream().noneMatch(user -> user.getId() == id)) {
            System.out.println("ID mismatch");
            return;
        }

        if (friendController.getFriendStatus(currentUser.getId(), id) == FriendStatus.WAIT_ACCEPT) {
            System.out.println("1. Accept request");
            System.out.println("2. Decline request");

            int accept = Config.getValidInteger();
            if (accept != 1 && accept != 2) {
                System.out.println("Invalid choice");
                return;
            }
            if (accept == 2) {
                System.out.println("Request declined");
                return;
            }
        }


        ResponseMessenger messenger = friendController.sendRequest(new Friend(friendController.getLastId(), currentUser.getId(), id));

        switch (messenger.getMessage()) {
            case "send_request":
                System.out.println("Send request");
                break;
            case "retrieve_request":
                System.out.println("Request Cancel");
                break;
            case "accept_request":
                System.out.println("Accept friend request");
                break;
            case "already_friend":
                System.out.println("This user is already friend");
                break;
        }

    }

    private void formFriendRequest() {
        List<Integer> listRequest = friendController.getRequestsToId(currentUser.getId());
        if (listRequest.isEmpty()) {
            System.out.println("No friend requests");
            return;
        }
        System.out.println("*****FRIEND REQUEST*****");

        System.out.printf("%-5s%-15s%n", "ID", "NAME");
        for (Integer i : listRequest) {
            User user = userController.findById(i);
            System.out.printf("%-5d%-15s%n", user.getId(), user.getName());
        }

        System.out.println("Enter id");
        int id = Config.getValidInteger();

        if (!listRequest.contains(id)) {
            System.out.println("ID mismatch");
            return;
        }

        System.out.println("1. Accept request");
        System.out.println("2. Decline request");

        switch (Config.getValidInteger()) {
            case 1:
                friendController.acceptRequest(currentUser.getId(), id);
                System.out.println("Accept friend request");
                break;
            case 2:
                friendController.removeRequest(currentUser.getId(), id);
                System.out.println("Request declined");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public void formFriendList(int index) {
        int numberNotice = notificationController.getUnseenNotificationsCount();

        List<User> friends = new ArrayList<>();
        for (Integer i : currentUser.getProfile().getFriendList()) {
            User user = userController.findById(i);
            friends.add(user);
        }
        if (friends.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┛         ┗━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃           You have no friends !            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  " + WHITE_BRIGHT + "details" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "send msg" + BLUE_BRIGHT + " ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "back" + BLUE_BRIGHT + "   ┃  ┃");
            System.out.println("┃  ┃   ( 6 )   ┃  ( 7 )   ┃   ( 8 )  ┃   ( 9 )  ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛  ┃");
            System.out.println("┃    < 10. Previous | ( 1 // 1 ) | 11. Next  >     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┛         ┗━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index).getName(), friends.get(index).getId());
            System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            if ((index + 1) > friends.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index + 1).getName(), friends.get(index + 1).getId());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 2) > friends.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index + 2).getName(), friends.get(index + 2).getId());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 3) > friends.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index + 3).getName(), friends.get(index + 3).getId());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 4) > friends.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index + 4).getName(), friends.get(index + 4).getId());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 5) > friends.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-21s  ID:%3d " + BLUE_BRIGHT + "┃  ┃\n", friends.get(index + 5).getName(), friends.get(index + 5).getId());
            }

            System.out.println("┃  ┣━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  " + WHITE_BRIGHT + "details" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "send msg" + BLUE_BRIGHT + " ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "back" + BLUE_BRIGHT + "   ┃  ┃");
            System.out.println("┃  ┃   ( 6 )   ┃  ( 7 )   ┃   ( 8 )  ┃   ( 9 )  ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 10. Previous |" + WHITE_BRIGHT + " ( " + (index / 6 + 1) + " // " + (friends.size() % 6 == 0 ? friends.size() / 6 : friends.size() / 6 + 1) + " ) " + (index + 7 > (friends.size()) ? WHITE : WHITE_BRIGHT) + "| 11.  Next  >" + BLUE_BRIGHT + "    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewMessenger().menu();
                break;
            case 2:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printCyanAlert("Already in friend page");
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
                System.out.print("┃  Enter friend ID to show details: ");
                int id = Config.getValidInteger();
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (friends.stream().noneMatch(u -> u.getId() == id)) {
                    Alert.printRedAlert("ID mismatch!!!");
                    break;
                }
                printPost(id, 0);
                break;
            case 7:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewMessenger().formSendMessage();
                break;
            case 8:
                formDeleteFriend();
                break;
            case 9:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                return;
            case 10:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous page!");
                } else {
                    index -= 6;
                }
                break;
            case 11:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index + 7 > (friends.size())) {
                    Alert.printCyanAlert("There's no more next comments");
                } else {
                    index += 6;
                }
                break;
            default:
                Alert.printRedAlert("Invalid choice!");
        }
        formFriendList(index);
    }


    private void formDeleteFriend() {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter friend ID to delete: ");
        int id = Config.getValidInteger();
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Are you sure to delete ? (Y / N): ");
        String confirm = Config.scanner().nextLine();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        if (confirm.trim().equalsIgnoreCase("n")) {
            Alert.printCyanAlert("Delete cancelled");
            return;
        }
        if (!confirm.trim().equalsIgnoreCase("y") && !confirm.trim().equalsIgnoreCase("n")) {
            Alert.printRedAlert("Invalid confirmation!!!!");
            return;
        }

        if (!currentUser.getProfile().getFriendList().contains(id)) {
            Alert.printRedAlert("ID mismatch");
            return;
        }
        friendController.deleteFriend(id);
        Alert.printCyanAlert("You have deleted friend " + userController.findById(id).getName());
    }

    private void printPost(int id, int index) {
        int numberNotice = notificationController.getUnseenNotificationsCount();

        List<Post> postList = postController.getYourPosts(id);
        if (postList.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┛         ┗━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-19s ┃ 6. BACK " + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(id).getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃          Your friend have no post          ┃  ┃");
            System.out.println("┃  ┃             on the wall !!!                ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃     " + WHITE + "< 9. Previous |" + WHITE_BRIGHT + " ( 1 // 1 ) " + WHITE + "| 10. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            Post post = postList.get(index);
            int likeNumber = likeController.getLikesByPostId(postList.get(index).getId()).size();
            int commentNumber = commentController.getCommentsByPostId(postList.get(index).getId()).size();
            boolean isLiked = likeController.findLikePost(postList.get(index).getId()) != -1;

            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┛         ┗━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-19s ┃ 6. BACK " + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(id).getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.printf("┃  ┃" + WHITE_BRIGHT + "  %-20s                      " + BLUE_BRIGHT + "┃  ┃\n", postController.getTimePassed(post.getId()));
            System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-36s    " + BLUE_BRIGHT + "┃  ┃\n", post.getContent());
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            System.out.printf("┃  ┃  " + (isLiked ? CYAN_BOLD : WHITE_BRIGHT) + "7. Like: %2d               " + WHITE_BRIGHT + "8. Comment: %2d" + BLUE_BRIGHT + "  ┃  ┃\n", likeNumber, commentNumber);
            System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃     " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 9. Previous |" + WHITE_BRIGHT + " ( " + (index + 1) + " // " + postList.size() + " ) " + (index == postList.size() - 1 ? WHITE : WHITE_BRIGHT) + "| 10. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewMessenger().menu();
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
                return;
            case 7:
                if (postList.isEmpty()) {
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                new ViewNews().likePost(postList.get(index).getId());
                break;
            case 8:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (postList.isEmpty()) {
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                new ViewNews().printPostComment(id, index);
                break;
            case 9:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous post");
                } else {
                    index--;
                }
                break;
            case 10:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index + 1 > (postList.size()) - 1) {
                    Alert.printCyanAlert("There's no more next post");
                } else {
                    index++;
                }
                break;
        }

        printPost(id, index);
    }
}
