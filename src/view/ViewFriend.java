package view;

import config.Config;
import controller.FriendController;
import controller.UserController;
import dto.response.ResponseMessenger;
import model.account.User;
import model.friend.Friend;
import model.friend.FriendStatus;
import plugin.Menu;

import java.util.List;

public class ViewFriend {

    private final FriendController friendController = new FriendController();
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();

    public void menu() {

        int numberRequest = friendController.getRequestsToId(currentUser.getId()).size();
        String friendRequest = "Friend Request" + (numberRequest == 0 ? "" : " (" + numberRequest + ")");

        Menu menu = new Menu();
        menu.addHeader("FRIEND MENU");
        menu.addChoice("Search User");
        menu.addChoice(friendRequest);
        menu.addChoice("Friend List");
        menu.addChoice("Delete Friend");
        menu.addChoice("Back");

        menu.print();

        int choice = Config.getValidInteger();

        if (menu.indexOf("Search User") == choice) this.formSearchUser();
        if (menu.indexOf(friendRequest) == choice) this.formFriendRequest();
        if (menu.indexOf("Friend List") == choice) this.formFriendList();
        if (menu.indexOf("Delete Friend") == choice) this.formDeleteFriend();
        if (menu.indexOf("Back") == choice) return;
        if (choice > menu.choiceCount()) System.err.println("Invalid choice");
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
        System.out.println("*****FRIEND REQUEST*****");
        List<Integer> listRequest = friendController.getRequestsToId(currentUser.getId());

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

        int accept = Config.getValidInteger();

        switch (accept) {
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

    private void formFriendList() {
        System.out.println("*****FORM FRIEND LIST*****");
        System.out.printf("%-5s%-15s%n", "ID", "NAME");
        for (Integer i : currentUser.getProfile().getFriendList()) {
            User user = userController.findById(i);
            System.out.printf("%-5d%-15s%n", user.getId(), user.getName());
        }
    }

    private void formDeleteFriend() {
        formFriendList();
        System.out.println("Enter friend ID to delete:");
        int id = Config.getValidInteger();
        if (!currentUser.getProfile().getFriendList().contains(id)) {
            System.out.println("ID mismatch");
            return;
        }
        friendController.deleteFriend(id);
        System.out.println("You have deleted friend " + userController.findById(id).getName());
    }
}
