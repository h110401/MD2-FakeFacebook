package controller;

import dto.response.ResponseMessenger;
import model.Notification;
import model.friend.Friend;
import model.friend.FriendStatus;
import service.friend.FriendServiceIMPL;
import service.friend.IFriendService;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.List;

public class FriendController {

    IFriendService friendService = new FriendServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    INotificationService notificationService = new NotificationServiceIMPL();


    public FriendStatus getFriendStatus(int idCurrent, int idTo) {
        return friendService.getFriendStatus(idCurrent, idTo);
    }

    public ResponseMessenger sendRequest(Friend friend) {
        int idCurrent = friend.getIdUser();
        int idTo = friend.getIdTo();
        int idNotification = notificationService.getLastId();
        String notification;
        switch (getFriendStatus(idCurrent, idTo)) {
            case NOT_FRIEND:
                friendService.save(friend);
                friendService.updateData();

                notification = userService.getCurrentUser().getName() + " sent you a friend request!";
                notificationService.save(new Notification(idNotification, idTo, notification));
                return new ResponseMessenger("send_request");
            case SENT_REQUEST:
                removeRequest(idTo, idCurrent);
                friendService.updateData();
                return new ResponseMessenger("retrieve_request");
            case WAIT_ACCEPT:
                acceptRequest(idCurrent, idTo);

                notification = userService.getCurrentUser().getName() + " accepted your friend request!";
                notificationService.save(new Notification(idNotification, idTo, notification));
                return new ResponseMessenger("accept_request");
        }

        return new ResponseMessenger("already_friend");
    }

    public void acceptRequest(int idCurrent, int idTo) {
        removeRequest(idCurrent, idTo);
        userService.getCurrentUser().getProfile().getFriendList().add(idTo);
        userService.findById(idTo).getProfile().getFriendList().add(idCurrent);
        friendService.updateData();
        userService.updateData();
    }

    public int getLastId() {
        return friendService.getLastId();
    }


    public void removeRequest(int idCurrent, int idTo) {
        friendService.remove(friendService.getFriendRequest(idTo, idCurrent).getId());
    }

    public List<Integer> getRequestsToId(int id) {
        return friendService.getRequestsToUserId(id);
    }

    public void deleteFriend(int idTo) {
        userService.getCurrentUser().getProfile().getFriendList().remove(idTo);
        userService.findById(idTo).getProfile().getFriendList().remove(userService.getCurrentUser().getId());
        userService.updateData();
    }
}
