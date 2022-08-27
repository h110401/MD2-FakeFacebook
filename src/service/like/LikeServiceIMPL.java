package service.like;

import config.Config;
import model.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeServiceIMPL implements ILikeService {

    private static final String PATH_LIKE = "src/data/like.txt";
    private static final Config<List<Like>> config = new Config<>();
    private static List<Like> likeList = config.read(PATH_LIKE);

    static {
        if (likeList == null) {
            likeList = new ArrayList<>();
        }
    }

    @Override
    public List<Like> findAll() {
        return likeList;
    }

    @Override
    public void save(Like like) {
        likeList.add(like);
        updateData();
    }

    @Override
    public Like findById(int id) {
        for (Like like : likeList) {
            if (like.getId() == id) {
                return like;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        likeList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_LIKE, likeList);
    }

    @Override
    public int getLastId() {
        return likeList.isEmpty() ? 1 : likeList.get(likeList.size() - 1).getId() + 1;
    }

    @Override
    public List<Like> getLikesByPostId(int idPost) {
        List<Like> list = new ArrayList<>();

        for (Like like : likeList) {
            if (like.getIdPost() == idPost) list.add(like);
        }

        return list;
    }

    @Override
    public List<Like> getLikesByCommentId(int idComment) {
        List<Like> list = new ArrayList<>();

        for (Like like : likeList) {
            if (like.getIdComment() == idComment) list.add(like);
        }

        return list;
    }
}
