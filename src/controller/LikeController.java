package controller;

import dto.response.ResponseMessenger;
import model.Comment;
import model.Like;
import model.Notification;
import model.account.User;
import service.comment.CommentServiceIMPL;
import service.comment.ICommentService;
import service.like.ILikeService;
import service.like.LikeServiceIMPL;
import service.notification.INotificationService;
import service.notification.NotificationServiceIMPL;
import service.post.IPostService;
import service.post.PostServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.List;

public class LikeController {
    private final IPostService postService = new PostServiceIMPL();
    private final INotificationService notificationService = new NotificationServiceIMPL();
    private final ILikeService likeService = new LikeServiceIMPL();
    private final IUserService userService = new UserServiceIMPL();
    private final ICommentService commentService = new CommentServiceIMPL();


    public List<Like> getLikeList() {
        return likeService.findAll();
    }

    public List<Like> getLikesByPostId(int idPost) {
        return likeService.getLikesByPostId(idPost);
    }

    public List<Like> getLikesByCommentId(int idComment) {
        return likeService.getLikesByCommentId(idComment);
    }

    public ResponseMessenger createLikePost(int idPost) {
        int idLike = findLikePost(idPost);
        if (idLike != -1) {
            likeService.remove(idLike);
            return new ResponseMessenger("dislike");
        } else {
            User currentUser = userService.getCurrentUser();
            likeService.save(new Like(likeService.getLastId(), currentUser.getId(), idPost, 0));
            User postUser = userService.findById(postService.findById(idPost).getIdUser());
            if (postUser != currentUser) {
                String notification = currentUser.getName() + " liked your post ID: " + idPost;
                notificationService.save(new Notification(postService.getLastId(), postUser.getId(), notification));
            }
            return new ResponseMessenger("like");
        }
    }

    public int findLikePost(int idPost) {
        User currentUser = userService.getCurrentUser();
        for (Like like : getLikeList()) {
            if (like.getIdUser() == currentUser.getId() && like.getIdPost() == idPost) {
                return like.getId();
            }
        }
        return -1;
    }

    public ResponseMessenger createLikeComment(int idPost, int idComment) {
        List<Comment> comments = commentService.getCommentsByPostId(idPost);
        if (comments.stream().noneMatch(comment -> comment.getId() == idComment)) {
            return new ResponseMessenger("id_mismatch");
        }
        int idLike = findLikeComment(idComment);
        if (idLike != -1) {
            likeService.remove(idLike);
            return new ResponseMessenger("dislike");
        } else {
            User currentUser = userService.getCurrentUser();
            likeService.save(new Like(likeService.getLastId(), currentUser.getId(), 0, idComment));
            User commentUser = userService.findById(commentService.findById(idComment).getIdUser());
            if (commentUser != currentUser) {
                String notification = currentUser.getName() + " liked your comment ID: " + idComment + ", in post ID: " + idPost;
                notificationService.save(new Notification(notificationService.getLastId(), commentUser.getId(), notification));
            }
            return new ResponseMessenger("like");
        }
    }

    public int findLikeComment(int idComment) {
        User currentUser = userService.getCurrentUser();
        for (Like like : getLikeList()) {
            if (like.getIdComment() == idComment && like.getIdUser() == currentUser.getId()) {
                return like.getId();
            }
        }
        return -1;
    }
}
