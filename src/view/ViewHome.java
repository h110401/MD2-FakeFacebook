package view;

import config.Config;
import controller.*;
import dto.response.ResponseMessenger;
import model.post.Post;
import model.role.RoleName;
import model.account.User;
import plugin.Alert;
import plugin.Menu;

import java.util.*;

import static plugin.ConsoleColors.*;

public class ViewHome {

    private final NotificationController notificationController = new NotificationController();
    private final UserController userController = new UserController();
    private final List<User> userList = userController.getUserList();
    private final User currentUser = userController.getCurrentUser();
    private final PostController postController = new PostController();
    private final CommentController commentController = new CommentController();
    private final LikeController likeController = new LikeController();

    public void mainMenu(int index) {
        List<Post> availablePosts = new ArrayList<>(postController.getAvailablePosts());
        Collections.reverse(availablePosts);

        printPost(availablePosts, index);

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        switch (choice) {
            case 1:
                new ViewMessenger().menu();
                break;
            case 2:
                new ViewFriend().menu();
                break;
            case 3:
                Alert.printCyanAlert("Already in home page!");
                break;
            case 4:
                new ViewNotification().formNotification(0);
                break;
            case 5:
                menu();
                break;
            case 6:
                new ViewNews().formShowYourPosts(0);
                break;
            case 7:
                new ViewNews().likePost(availablePosts.get(index).getId());
                break;
            case 8:
                new ViewNews().printPostComment(availablePosts.get(index).getId(), 0);
                break;
            case 9:
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous posts");
                } else {
                    index--;
                }
                break;
            case 10:
                if (index == availablePosts.size() - 1) {
                    Alert.printCyanAlert("There's no more next posts");
                } else {
                    index++;
                }
                break;
            default:
                Alert.printRedAlert("Invalid choice!");
        }
        mainMenu(index);
    }

    public void menu() {

        RoleName maxRole = currentUser.getMaxRole();

        if (maxRole == RoleName.USER) {

        } else {

        }

        int numberNotifications = notificationController.getMyNotifications().size();
        String notification = "Notification" + (numberNotifications == 0 ? "" : " (" + numberNotifications + ")");


        Menu menu = new Menu();
        menu.addHeader("Welcome " + maxRole + ": " + currentUser.getName());
        menu.addChoice("News");
        menu.addChoice("Messenger");
        menu.addChoice(notification);
        menu.addChoice("Friend Manager");
        if (maxRole == RoleName.ADMIN || maxRole == RoleName.PM) {
            menu.addChoice("User Manage");
        }
        menu.addChoice("Log out");
        menu.addChoice("Exit");
        menu.print();

        int choice = Config.getValidInteger();

        if (menu.indexOf("News") == choice) new ViewNews().formShowYourPosts(0);
        if (menu.indexOf("Messenger") == choice) new ViewMessenger().menu();
        if (menu.indexOf(notification) == choice) new ViewNotification().formNotification(0);
        if (menu.indexOf("Friend Manager") == choice) {
            new ViewFriend().menu();
        }
        if (menu.indexOf("Log out") == choice) {
            userController.logout();
            new ViewMainMenu().menu();
            return;
        }
        if (menu.indexOf("User Manage") == choice) this.formUserManage();
        if (menu.indexOf("Exit") == choice) System.exit(0);
        if (choice > menu.choiceCount()) System.err.println("Invalid choice");

        menu();
    }

    private void showUserList() {
        System.out.printf("%-5s%-15s%s%n", "ID", "USERNAME", "ROLE");
        for (User user : userList) {
            System.out.printf("%-5d%-15s%s%n", user.getId(), user.getUsername(), user.getMaxRole());
        }
    }


    private void formUserManage() {

        RoleName maxRole = currentUser.getMaxRole();
        if (maxRole == RoleName.USER) {
            return;
        }
        Menu menu = new Menu();
        menu.addHeader("User");
        menu.addChoice("Show List User");
        menu.addChoice("Block User");
        menu.addChoice("Delete User");
        if (maxRole == RoleName.ADMIN) {
            menu.addChoice("Change Role");
        }
        menu.addChoice("Back");
        menu.print();

        int choice = Config.getValidInteger();

        if (menu.indexOf("Back") == choice) return;
        else if (menu.indexOf("Show List User") == choice) this.showUserList();
        else if (menu.indexOf("Block User") == choice) this.formBlockUser();
        else if (menu.indexOf("Delete User") == choice) this.formDeleteUser();
        else if (menu.indexOf("Change Role") == choice) this.formChangeRole();
        else System.err.println("Invalid choice");
        formUserManage();
    }

    private void formChangeRole() {
        System.out.println("*****CHANGE ROLE FORM*****");
        List<User> userList = new ArrayList<>(this.userList);

        if (userList.isEmpty()) {
            System.out.println("There is no user!");
            return;
        }

        User current = null;
        for (User user : userList) {
            if (user.getUsername().equals(currentUser.getUsername())) {
                current = user;
            }
        }
        userList.remove(current);

        System.out.printf("%-5s%-15s%s%n", "ID", "USERNAME", "ROLE");
        for (User user : userList) {
            System.out.printf("%-5d%-15s%s%n", user.getId(), user.getUsername(), user.getMaxRole());
        }

        System.out.println("Enter user id to edit role");
        int id = Config.getValidInteger();

        if (!userList.contains(userController.findById(id))) {
            System.out.println("ID not found");
            return;
        }

        System.out.println("Enter role");
        String role = Config.scanner().nextLine();
        Set<String> strRoles = new HashSet<>();
        strRoles.add(role);
        userController.setRole(id, strRoles);

        System.out.printf("%-5s%-15s%s%n", "ID", "USERNAME", "ROLE");
        for (User user : userList) {
            System.out.printf("%-5d%-15s%s%n", user.getId(), user.getUsername(), user.getMaxRole());
        }
    }


    private void formDeleteUser() {

        System.out.println("*****DELETE USER FORM*****");
        List<User> userList = getAvailable();
        if (userList.isEmpty()) {
            System.out.println("There is no user to delete");
            return;
        }

        System.out.printf("%-5s%-15s%s%n", "ID", "USERNAME", "STATUS");

        for (User user : userList) {
            System.out.printf("%-5d%-15s%s%n", user.getId(), user.getUsername(), user.isPrevent());
        }

        System.out.println("Enter user id to delete");

        int id = Config.getValidInteger();

        ResponseMessenger messenger = userController.deleteUser(id);
        switch (messenger.getMessage()) {
            case "id_mismatch":
                System.out.println("ID mismatch" + id);
                break;
            case "delete_success":
                System.out.printf("User %d has been successfully deleted!\n", id);
        }
    }

    private void formBlockUser() {
        System.out.println("*****BLOCK USER FORM*****");
        List<User> userList = getAvailable();
        if (userList.isEmpty()) {
            System.out.println("There is no user to block");
            return;
        }

        System.out.printf("%-5s%-15s%s%n", "ID", "USERNAME", "STATUS");

        for (User user : userList) {
            System.out.printf("%-5d%-15s%s%n", user.getId(), user.getUsername(), user.isPrevent());
        }

        System.out.println("Enter user id to block");

        int id = Config.getValidInteger();

        ResponseMessenger messenger = userController.blockUser(id);
        switch (messenger.getMessage()) {
            case "id_mismatch":
                System.out.println("ID mismatch" + id);
                break;
            case "block_success":
                System.out.printf("User %d has been blocked!\n", id);
                break;
            case "unblocked":
                System.out.printf("User %d has been unblocked!\n", id);
        }
    }

    private List<User> getAvailable() {
        List<User> userList;
        RoleName maxRole = currentUser.getMaxRole();
        if (maxRole == RoleName.ADMIN) {
            userList = new ArrayList<>(this.userList);
            User current = null;
            for (User user : userList) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    current = user;
                }
            }
            userList.remove(current);
        } else {
            userList = userController.findByRoleName(RoleName.USER);
        }
        return userList;
    }

    private void printPost(List<Post> postList, int index) {
        int numberNotice = notificationController.getUnseenNotificationsCount();

        if (postList.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┛          ┗━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃ " + WHITE_BRIGHT + "( '  _ ')   %-20s" + PURPLE_BRIGHT + "┃ " + WHITE_BRIGHT + " 6. (+) " + BLUE_BRIGHT + " ┃  ┃\n", currentUser.getName());
            System.out.println("┃  ┣" + PURPLE_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━" + BLUE_BRIGHT + "┫  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃             Have a good day!               ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃       You have no post to reade !          ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃             Let's create one...            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃           >.<                              ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃     " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 9. Previous |" + WHITE_BRIGHT + " ( " + (index + 1) + " // " + postList.size() + " ) " + (index == postList.size() - 1 ? WHITE : WHITE_BRIGHT) + "| 10. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            return;
        }

        Post post = postList.get(index);
        int likeNumber = likeController.getLikesByPostId(post.getId()).size();
        int commentNumber = commentController.getCommentsByPostId(post.getId()).size();
        boolean isLiked = likeController.findLikePost(post.getId()) != -1;

        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
        System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
        System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
        System.out.println("┣━━━━━━━━━┻━━━━━━━━━┛          ┗━━━━━━━━━┻━━━━━━━━━┫");
        System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━┓  ┃");
        System.out.printf("┃  ┃ " + WHITE_BRIGHT + "( '  _ ')   %-20s" + PURPLE_BRIGHT + "┃ " + WHITE_BRIGHT + " 6. (+) " + BLUE_BRIGHT + " ┃  ┃\n", currentUser.getName());
        System.out.println("┃  ┣" + PURPLE_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━" + BLUE_BRIGHT + "┫  ┃");
        System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')   %-29s" + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(post.getIdUser()).getName());
        System.out.printf("┃  ┃" + WHITE + "  %-10s          " + WHITE_BRIGHT + "%20s  " + BLUE_BRIGHT + "┃  ┃\n", post.getStatus(), postController.getTimePassed(post.getId()));
        System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-36s    " + BLUE_BRIGHT + "┃  ┃\n", post.getContent());
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

}
