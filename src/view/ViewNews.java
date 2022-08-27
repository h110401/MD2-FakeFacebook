package view;

import config.Config;
import controller.CommentController;
import controller.LikeController;
import controller.PostController;
import controller.UserController;
import dto.request.PostDTO;
import dto.response.ResponseMessenger;
import model.Comment;
import model.account.User;
import model.post.Post;
import plugin.Menu;

import static plugin.ConsoleColors.*;

import java.util.List;
import java.util.stream.Collectors;

public class ViewNews {

    private final PostController postController = new PostController();
    private final CommentController commentController = new CommentController();
    private final UserController userController = new UserController();
    private final LikeController likeController = new LikeController();

    public void menu() {

        Menu menu = new Menu();
        menu.addHeader("News");
        menu.addChoice("See All Post");
        menu.addChoice("Manage Your Post");
        menu.addChoice("Back");
        menu.print();

        switch (Config.getValidInteger()) {
            case 1:
                this.formAllPost();
                break;
            case 2:
                this.menuManageYourPost();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice");
        }

        menu();
    }

    private void menuManageYourPost() {
        Menu menu = new Menu();
        menu.addHeader("Your post");
        menu.addChoice("All your post");
        menu.addChoice("Create new post");
        menu.addChoice("Edit post");
        menu.addChoice("Delete post");
        menu.addChoice("Back");
        menu.print();

        switch (Config.getValidInteger()) {
            case 1:
                formShowYourPosts();
                break;
            case 2:
                formCreatePost();
                break;
            case 3:
                formEditPost();
                break;
            case 4:
                formDeletePost();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice");
        }
        menuManageYourPost();
    }

    private void formDeletePost() {
        formShowYourPosts();
        System.out.println("Enter id post to delete");
        int id = Config.getValidInteger();
        ResponseMessenger messenger = postController.deletePost(id);
        switch (messenger.getMessage()) {
            case "id_mismatch":
                System.out.println("ID mismatch: " + id);
                break;
            case "delete_success":
                System.out.println("Successfully deleted post " + id);
        }
    }

    private void formEditPost() {
        formShowYourPosts();
        System.out.println("Enter id post to edit");
        int id = Config.getValidInteger();
        System.out.println("Enter edit content");
        String content = Config.scanner().nextLine();
        System.out.println("Enter post status (public / private / friend)");
        String status = Config.scanner().nextLine();

        PostDTO postDTO = new PostDTO(id, content, status);
        ResponseMessenger messenger = postController.editPost(postDTO);

        switch (messenger.getMessage()) {
            case "unknown_status":
                System.out.println("Unknown status: " + status);
                break;
            case "id_mismatch":
                System.out.println("ID mismatch: " + id);
                break;
            case "edited":
                System.out.println("Your post has been edited");
                break;
        }

    }

    private void formCreatePost() {
        System.out.println("Enter content");
        String content = Config.scanner().nextLine();
        System.out.println("Enter post status (public / private / friend)");
        String status = Config.scanner().nextLine();

        PostDTO postDTO = new PostDTO(postController.getLastId(), content, status);
        ResponseMessenger messenger = postController.createPost(postDTO);

        switch (messenger.getMessage()) {
            case "created":
                System.out.println("Post created");
                break;
            case "unknown_status":
                System.out.println("Unknown status: " + status);
        }
    }

    private void showPosts(List<Post> postList) {
        for (Post post : postList) {
            User user = userController.findById(post.getIdUser());
            String timePassed = postController.getTimePassed(post.getId());

            int likeNumber = likeController.getLikesByPostId(post.getId()).size();
            int commentNumber = commentController.getCommentsByPostId(post.getId()).size();

            boolean isLiked = likeController.findLikePost(post.getId()) != -1;
            String likeCommentBar = isLiked ? "|" + BLUE + " Like: %3d" + RESET + "                  Comment: %3d          |\n" : "| Like: %3d                  Comment: %3d          |\n";

            System.out.println(".--------------------------------------------------.");
            System.out.printf("| ID: %-" + (44 - timePassed.length()) + "d%s |\n", post.getId(), timePassed);
            System.out.printf("| Name: %-42s |\n", user.getName());
            System.out.println("|--------------------------------------------------|");
            System.out.println("|                                                  |");
            System.out.printf("|    %-45s |\n", post.getContent());
            System.out.println("|                                                  |");
            System.out.println("|--------------------------------------------------|");
            System.out.printf(likeCommentBar, likeNumber, commentNumber);
            System.out.println("'--------------------------------------------------'\n");
        }
    }

    private void formShowYourPosts() {
        List<Post> yourPost = postController.getYourPosts();
        showPosts(yourPost);
        System.out.println("Enter post id to show details / 0 to back");
        int id = Config.getValidInteger();
        if (id == 0) return;
        if (!yourPost.stream().map(Post::getId).collect(Collectors.toList()).contains(id)) {
            System.out.println("ID mismatch: " + id);
        } else {
            formPostDetails(id);
        }
        formShowYourPosts();
    }

    private void formAllPost() {
        List<Post> availablePosts = postController.getAvailablePosts();
        showPosts(availablePosts);
        System.out.println("Enter post id to show details / 0 to back");
        int id = Config.getValidInteger();
        if (id == 0) return;
        if (!availablePosts.stream().map(Post::getId).collect(Collectors.toList()).contains(id)) {
            System.out.println("ID mismatch: " + id);
        } else {
            formPostDetails(id);
        }
        formAllPost();
    }

    private void formPostDetails(int id) {
        Post post = postController.findById(id);
        User user = userController.findById(post.getIdUser());
        String timePassed = postController.getTimePassed(id);
        int likeNumber = likeController.getLikesByPostId(post.getId()).size();
        List<Comment> commentList = commentController.getCommentsByPostId(post.getId());

        boolean isLiked = likeController.findLikePost(id) != -1;
        String likeCommentBar = isLiked ? "|" + BLUE + " Like: %3d" + RESET + "                  Comment: %3d          |\n" : "| Like: %3d                  Comment: %3d          |\n";

        System.out.println(".--------------------------------------------------.");
        System.out.printf("| Name: %-" + (42 - timePassed.length()) + "s%s |\n", user.getName(), timePassed);
        System.out.println("|--------------------------------------------------|");
        System.out.println("|                                                  |");
        System.out.printf("|    %-45s |\n", post.getContent());
        System.out.println("|                                                  |");
        System.out.println("|--------------------------------------------------|");
        System.out.printf(likeCommentBar, likeNumber, commentList.size());
        System.out.println("|--------------------------------------------------|");

        int i = 0;
        for (; i < commentList.size() - 1 && commentList.size() - 1 >= 0; i++) {
            boolean isLikedComment = likeController.findLikeComment(commentList.get(i).getId()) != -1;
            String likeComment = isLikedComment ? "| %-39s" + BLUE + "Like: %3d" + RESET + " |\n" : "| %-39sLike: %3d |\n";

            User userComment = userController.findById(commentList.get(i).getIdUser());
            System.out.printf("| %-41sID: %3d |\n", userComment.getName(), commentList.get(i).getId());
            System.out.printf(likeComment, commentList.get(i).getContent(), likeController.getLikesByCommentId(commentList.get(i).getId()).size());
            System.out.println("|                                                  |");
        }
        boolean isLikedComment = likeController.findLikeComment(commentList.get(i).getId()) != -1;
        String likeComment = isLikedComment ? "| %-39s" + BLUE + "Like: %3d" + RESET + " |\n" : "| %-39sLike: %3d |\n";
        System.out.printf("| %-41sID: %3d |\n", userController.findById(commentList.get(i).getIdUser()).getName(), commentList.get(i).getId());
        System.out.printf(likeComment, commentList.get(i).getContent(), likeController.getLikesByCommentId(commentList.get(i).getId()).size());

        System.out.println("'--------------------------------------------------'");

        System.out.println("1. Like");
        System.out.println("2. Comment");
        System.out.println("3. Like comments");
        System.out.println("4. Delete comment");
        System.out.println("5. Back");
        int choice = Config.getValidInteger();
        switch (choice) {
            case 1:
                likePost(id);
                break;
            case 2:
                formComment(id);
                break;
            case 3:
                formLikeComment(id);
                break;
            case 4:
                formDeleteComment(id);
                break;
            case 5:
                return;
            default:
                System.err.println("Invalid choice");
        }
        formPostDetails(id);
    }

    private void formLikeComment(int idPost) {
        System.out.println("Enter comment id to like:");
        int idComment = Config.getValidInteger();
        ResponseMessenger messenger = likeController.createLikeComment(idPost, idComment);
        switch (messenger.getMessage()) {
            case "like":
                System.out.println("You have just liked comment " + idComment);
                break;
            case "dislike":
                System.out.println("You have just disliked comment " + idComment);
                break;
            case "id_mismatch":
                System.out.println("Comment " + idComment + " is not in this post");
                break;
        }
    }

    private void likePost(int idPost) {
        ResponseMessenger messenger = likeController.createLikePost(idPost);
        switch (messenger.getMessage()) {
            case "like":
                System.out.println("You have just liked this post");
                break;
            case "dislike":
                System.out.println("You have just disliked this post");
        }
    }

    private void formDeleteComment(int idPost) {
        System.out.println("Enter comment id to delete");
        int idComment = Config.getValidInteger();
        ResponseMessenger messenger = commentController.deleteComment(idComment, idPost);
        switch (messenger.getMessage()) {
            case "delete_success":
                System.out.println("Comment " + idComment + " has been successfully deleted");
                break;
            case "id_mismatch":
                System.err.println("ID mismatch!");
        }
    }

    private void formComment(int id) {
        System.out.println("Enter comment:");
        String comment = Config.getUnEmptyString();
        commentController.createComment(id, comment);
    }
}
