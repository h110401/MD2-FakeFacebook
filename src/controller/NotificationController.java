package controller;

import model.Notification;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;

import java.util.List;

public class NotificationController {

    INotificationService notificationService = new NotificationServiceIMPL();

    public List<Notification> getNotificationById(int id) {
        return notificationService.getNotificationById(id);
    }

    public void clear(int id) {
        notificationService.clear(id);
    }
}
