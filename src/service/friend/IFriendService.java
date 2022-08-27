package service.friend;

import model.friend.Friend;
import model.friend.FriendStatus;
import service.IGenericService;

import java.util.List;


public interface IFriendService extends IGenericService<Friend> {

    FriendStatus getFriendStatus(int idCurrent, int idTo);

    Friend getFriendRequest(int idCurrent, int idTo);

    List<Integer> getRequestsToUserId(int id);

    List<Friend> getRequestSentOfUserId(int id);

}
