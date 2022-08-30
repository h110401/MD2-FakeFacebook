package service.notification;

import config.Config;
import model.Notification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NotificationServiceIMPL implements INotificationService {

    private static final String PATH_NOTIFICATION = "src/data/notification.txt";
    private static final Config<List<Notification>> config = new Config<>();

    private static List<Notification> notificationList = config.read(PATH_NOTIFICATION);


    static {
        if (notificationList == null) {
            notificationList = new ArrayList<>();
        }
    }

    @Override
    public List<Notification> findAll() {
        return notificationList;
    }

    @Override
    public void save(Notification notification) {
        notificationList.add(notification);
        updateData();
    }

    @Override
    public Notification findById(int id) {
        for (Notification notification : notificationList) {
            if (notification.getId() == id) {
                return notification;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        notificationList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_NOTIFICATION, notificationList);
    }

    @Override
    public int getLastId() {
        return notificationList.isEmpty() ? 1 : notificationList.get(notificationList.size() - 1).getId() + 1;
    }

    @Override
    public List<Notification> getNotificationById(int id) {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (notification.getIdUser() == id) {
                notifications.add(notification);
            }
        }
        return notifications;
    }

}
