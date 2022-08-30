package controller;

import dto.response.ResponseMessenger;
import model.Comment;
import model.Like;
import model.Notification;
import model.account.User;
import model.post.Post;
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

public class CommentController {
    IPostService postService = new PostServiceIMPL();
    INotificationService notificationService = new NotificationServiceIMPL();
    ICommentService commentService = new CommentServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    User currentUser = userService.getCurrentUser();

    ILikeService likeService = new LikeServiceIMPL();

    public void createComment(int idPost, String comment) {
        commentService.save(new Comment(commentService.getLastId(), currentUser.getId(), idPost, comment));
        int idPostUser = postService.findById(idPost).getIdUser();

        if (idPostUser == currentUser.getId()) return;

        String notification = currentUser.getName() + " commented on your post ID: " + idPost;

        for (Notification noti : notificationService.findAll()) {
            if (noti.getNotification().equals(notification)) return;
        }

        notificationService.save(new Notification(notificationService.getLastId(), idPostUser, notification));
    }

    public List<Comment> getCommentsByPostId(int id) {
        return commentService.getCommentsByPostId(id);
    }

    public ResponseMessenger deleteComment(int idComment, int idPost) {
        boolean check = false;
        for (Comment comment : commentService.getCommentsByPostId(idPost)) {
            if (comment.getId() == idComment && comment.getIdUser() == currentUser.getId()) {
                commentService.remove(idComment);
                check = true;
            }
        }
        for (Like like : likeService.findAll()) {
            if(like.getIdComment() == idComment) {
                likeService.remove(like.getId());
                break;
            }
        }
        if (check) {
            return new ResponseMessenger("delete_success");
        } else {
            return new ResponseMessenger("id_mismatch");
        }
    }
}
