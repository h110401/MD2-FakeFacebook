package view;

import config.Config;
import controller.NotificationController;
import controller.UserController;
import model.Notification;
import model.account.User;
import plugin.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static plugin.ConsoleColors.*;

public class ViewNotification {
    private final NotificationController notificationController = new NotificationController();
    int numberNotice = notificationController.getUnseenNotificationsCount();

    public void formNotification(int index) {
        List<Notification> notificationList = new ArrayList<>(notificationController.getMyNotifications());
        Collections.reverse(notificationList);
        notificationController.seenNotification();

        if (notificationList.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┛         ┗━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃           Everything is clear!             ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
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
            System.out.println("┃     " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 9. Previous |" + WHITE_BRIGHT + " ( " + (index / 5 + 1) + " // " + (notificationList.size() % 5 == 0 && notificationList.size() != 0 ? notificationList.size() / 5 : notificationList.size() / 5 + 1) + " ) " + (index + 6 > (notificationList.size()) ? WHITE : WHITE_BRIGHT) + "| 10. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┛         ┗━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃   " + WHITE_BRIGHT + "ID:  %2d" + BLUE_BRIGHT + "                                  ┃  ┃\n", notificationList.get(index).getId());
            System.out.printf("┃  ┃   " + WHITE_BRIGHT + "- %-37s" + BLUE_BRIGHT + "  ┃  ┃\n", notificationList.get(index).getNotification());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            if ((index + 1) > notificationList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "ID:  %2d" + BLUE_BRIGHT + "                                  ┃  ┃\n", notificationList.get(index + 1).getId());
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "- %-37s" + BLUE_BRIGHT + "  ┃  ┃\n", notificationList.get(index + 1).getNotification());
                System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            }
            if ((index + 2) > notificationList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "ID:  %2d" + BLUE_BRIGHT + "                                  ┃  ┃\n", notificationList.get(index + 2).getId());
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "- %-37s" + BLUE_BRIGHT + "  ┃  ┃\n", notificationList.get(index + 2).getNotification());
                System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            }
            if ((index + 3) > notificationList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "ID:  %2d" + BLUE_BRIGHT + "                                  ┃  ┃\n", notificationList.get(index + 3).getId());
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "- %-37s" + BLUE_BRIGHT + "  ┃  ┃\n", notificationList.get(index + 3).getNotification());
                System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            }
            if ((index + 4) > notificationList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "ID:  %2d" + BLUE_BRIGHT + "                                  ┃  ┃\n", notificationList.get(index + 4).getId());
                System.out.printf("┃  ┃   " + WHITE_BRIGHT + "- %-37s" + BLUE_BRIGHT + "  ┃  ┃\n", notificationList.get(index + 4).getNotification());
            }
            System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
            System.out.println("┃     " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 9. Previous |" + WHITE_BRIGHT + " ( " + (index / 5 + 1) + " // " + (notificationList.size() % 5 == 0 && notificationList.size() != 0 ? notificationList.size() / 5 : notificationList.size() / 5 + 1) + " ) " + (index + 6 > (notificationList.size()) ? WHITE : WHITE_BRIGHT) + "| 10. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }


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
                new ViewHome().mainMenu(0);
                break;
            case 4:
                new ViewNotification().formNotification(0);
            case 5:
                new ViewHome().menu();
                break;
            case 9:
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous notifications");
                } else {
                    index -= 5;
                }
                break;
            case 10:
                if (index + 6 > (notificationList.size())) {
                    Alert.printCyanAlert("There's no more next notifications");
                } else {
                    index += 5;
                }
                break;
        }
        formNotification(index);
    }

}
