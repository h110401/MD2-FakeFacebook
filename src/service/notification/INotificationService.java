package service.notification;

import model.Notification;
import service.IGenericService;

import java.util.List;

public interface INotificationService extends IGenericService<Notification> {

    List<Notification> getNotificationById(int id);

}
