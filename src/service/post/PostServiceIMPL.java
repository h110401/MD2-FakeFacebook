package service.post;

import config.Config;
import model.post.Post;
import model.post.PostStatus;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class PostServiceIMPL implements IPostService {


    private static final String PATH_POST = "src/data/post.txt";
    private static final Config<List<Post>> config = new Config<>();
    private static List<Post> postList = config.read(PATH_POST);

    static {
        if (postList == null) {
            postList = new ArrayList<>();
        }
    }

    @Override
    public List<Post> findAll() {
        return postList;
    }

    @Override
    public void save(Post post) {
        Post postInList = findById(post.getId());
        if (postInList == null) {
            postList.add(post);
        } else {
            postInList.setContent(post.getContent());
            postInList.setStatus(post.getStatus());
        }
        updateData();
    }

    @Override
    public Post findById(int id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        postList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_POST, postList);
    }

    @Override
    public int getLastId() {
        return postList.size() == 0 ? 1 : postList.get(postList.size() - 1).getId() + 1;
    }
}
