package view;

import config.Config;
import controller.NotificationController;
import controller.UserController;
import dto.response.ResponseMessenger;
import model.Notification;
import model.role.RoleName;
import model.account.User;
import plugin.Menu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewHome {

    private final NotificationController notificationController = new NotificationController();
    private final UserController userController = new UserController();
    private final List<User> userList = userController.getUserList();
    private final User currentUser = userController.getCurrentUser();


    public void menu() {

        int numberNotifications = notificationController.getNotificationById(currentUser.getId()).size();
        String notification = "Notification" + (numberNotifications == 0 ? "" : " (" + numberNotifications + ")");

        RoleName maxRole = currentUser.getMaxRole();

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

        if (menu.indexOf("News") == choice) new ViewNews().menu();
        if(menu.indexOf("Messenger") == choice) new ViewMessenger().menu();
        if (menu.indexOf(notification) == choice) this.formNotification();
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

    private void formNotification() {
        List<Notification> notificationList = notificationController.getNotificationById(currentUser.getId());

        if (notificationList.size() == 0) {
            System.out.println("Everything is clear!");
            return;
        }

        System.out.println("Notification:");
        for (Notification notification : notificationList) {
            System.out.println("- " + notification.getNotification());
        }
        notificationController.clear(currentUser.getId());
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

}
