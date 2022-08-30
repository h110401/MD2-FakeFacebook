package controller;

import model.Notification;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.List;

public class NotificationController {

    INotificationService notificationService = new NotificationServiceIMPL();
    IUserService userService = new UserServiceIMPL();

    public List<Notification> getMyNotifications() {
        return notificationService.getNotificationById(userService.getCurrentUser().getId());
    }

    public int getUnseenNotificationsCount() {
        int count = 0;

        for (Notification notification : getMyNotifications()) {
            if (!notification.isSeen()) {
                count++;
            }
        }

        return count;
    }

    public void seenNotification() {
        for (Notification notification : getMyNotifications()) {
            if (!notification.isSeen()) {
                notification.setSeen(true);
            }
        }
        notificationService.updateData();
    }

}
