package controller;

import dto.request.PostDTO;
import dto.response.ResponseMessenger;
import model.account.User;
import model.post.Post;
import model.post.PostStatus;
import service.post.IPostService;
import service.post.PostServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostController {

    private final IPostService postService = new PostServiceIMPL();
    private final IUserService userService = new UserServiceIMPL();
    private final User currentUser = userService.getCurrentUser();

    private List<Post> getPostList() {
        return postService.findAll();
    }

    public ResponseMessenger createPost(PostDTO postDTO) {
        PostStatus postStatus;
        switch (postDTO.getStatus()) {
            case "public":
                postStatus = PostStatus.PUBLIC;
                break;
            case "private":
                postStatus = PostStatus.PRIVATE;
                break;
            case "friend":
                postStatus = PostStatus.FRIEND;
                break;
            default:
                return new ResponseMessenger("unknown_status");
        }
        Post post = new Post(
                postDTO.getId(),
                currentUser.getId(),
                postDTO.getContent(),
                postStatus
        );
        postService.save(post);
        return new ResponseMessenger("created");
    }

    public List<Post> getAvailablePosts() {
        List<Post> availablePost = new ArrayList<>();

        for (Post post : getPostList()) {
            boolean available = post.getStatus() == PostStatus.PUBLIC
                    ||
                    (post.getStatus() == PostStatus.FRIEND && userService.findById(post.getIdUser()).getProfile().getFriendList().contains(currentUser.getId()))
                    ||
                    (post.getStatus() == PostStatus.PRIVATE && post.getIdUser() == currentUser.getId());

            if (available) {
                availablePost.add(post);
            }
        }
        return availablePost;
    }

    public List<Post> getYourPosts() {
        List<Post> yourPosts = new ArrayList<>();
        for (Post post : getPostList()) {
            if (post.getIdUser() == currentUser.getId()) {
                yourPosts.add(post);
            }
        }
        return yourPosts;
    }
    public List<Post> getYourPosts(int id) {
        List<Post> yourPosts = new ArrayList<>();
        for (Post post : getPostList()) {
            if (post.getIdUser() == id) {
                yourPosts.add(post);
            }
        }
        return yourPosts;
    }

    public int getLastId() {
        return postService.getLastId();
    }

    public ResponseMessenger editPost(PostDTO postDTO) {
        if (postNotExistsById(postDTO.getId(), getYourPosts())) {
            return new ResponseMessenger("id_mismatch");
        }
        PostStatus postStatus;
        switch (postDTO.getStatus()) {
            case "public":
                postStatus = PostStatus.PUBLIC;
                break;
            case "private":
                postStatus = PostStatus.PRIVATE;
                break;
            case "friend":
                postStatus = PostStatus.FRIEND;
                break;
            default:
                return new ResponseMessenger("unknown_status");
        }
        Post post = new Post(
                postDTO.getId(),
                currentUser.getId(),
                postDTO.getContent(),
                postStatus
        );
        postService.save(post);
        return new ResponseMessenger("edited");
    }

    public ResponseMessenger deletePost(int id) {
        if (postNotExistsById(id, getYourPosts())) {
            return new ResponseMessenger("id_mismatch");
        }
        postService.remove(id);
        return new ResponseMessenger("delete_success");
    }

    private boolean postNotExistsById(int id, List<Post> list) {
        for (Post post : list) {
            if (post.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public Post findById(int id) {
        return postService.findById(id);
    }

    public String getTimePassed(int id) {
        Post post = findById(id);
        Date from = post.getTime();
        Date to = new Date();
        long timePassed = to.getTime() - from.getTime();
        int second = (int) (timePassed / 1000) % 60;
        int minute = (int) (timePassed / (1000 * 60) % 60);
        int hour = (int) (timePassed / (1000 * 60 * 60) % 24);
        int day = (int) (timePassed / (1000 * 60 * 60 * 24));
        return (day != 0 ? day + " day" : (hour != 0 ? hour + " hour" : (minute != 0 ? minute + " minute" : second + " second"))) + " ago";
    }
}
