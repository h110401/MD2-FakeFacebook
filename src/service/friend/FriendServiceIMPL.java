package service.friend;

import config.Config;
import model.friend.Friend;
import model.friend.FriendStatus;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class FriendServiceIMPL implements IFriendService {

    private static final IUserService userService = new UserServiceIMPL();
    private static final String PATH_FRIEND = "src/data/friend.txt";
    private static final Config<List<Friend>> config = new Config<>();
    private static List<Friend> friendList = config.read(PATH_FRIEND);

    static {
        if (friendList == null) {
            friendList = new ArrayList<>();
        }
    }

    @Override
    public List<Friend> findAll() {
        return friendList;
    }

    @Override
    public void save(Friend friend) {
        friendList.add(friend);
        updateData();
    }

    @Override
    public Friend findById(int id) {
        for (Friend friend : friendList) {
            if (friend.getId() == id) {
                return friend;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        friendList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_FRIEND, friendList);
    }

    @Override
    public int getLastId() {

        return friendList.isEmpty() ? 1 : friendList.get(friendList.size() - 1).getId() + 1;
    }

    @Override
    public FriendStatus getFriendStatus(int idCurrent, int idTo) {
        if (userService.getCurrentUser().getProfile().getFriendList().contains(idTo)) {
            return FriendStatus.FRIEND;
        }
        for (Friend friend : friendList) {
            if (friend.getIdUser() == idCurrent && friend.getIdTo() == idTo) {
                return FriendStatus.SENT_REQUEST;
            }
            if (friend.getIdUser() == idTo && friend.getIdTo() == idCurrent) {
                return FriendStatus.WAIT_ACCEPT;
            }
        }
        return FriendStatus.NOT_FRIEND;
    }

    @Override
    public Friend getFriendRequest(int idCurrent, int idTo) {
        for (Friend friend : friendList) {
            if (friend.getIdUser() == idCurrent && friend.getIdTo() == idTo) {
                return friend;
            }
        }
        return null;
    }

    @Override
    public List<Integer> getRequestsToUserId(int id) {
        List<Integer> request = new ArrayList<>();

        for (Friend friend : friendList) {
            if (friend.getIdTo() == id) {
                request.add(friend.getIdUser());
            }
        }

        return request;
    }

    @Override
    public List<Friend> getRequestSentOfUserId(int id) {
        List<Friend> request = new ArrayList<>();
        for (Friend friend : friendList) {
            if (friend.getIdUser() == id) {
                request.add(friend);
            }
        }
        return request;
    }

}
