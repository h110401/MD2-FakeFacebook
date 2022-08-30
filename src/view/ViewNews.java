package view;

import config.Config;
import controller.*;
import dto.request.PostDTO;
import dto.response.ResponseMessenger;
import model.Comment;
import model.account.User;
import model.post.Post;
import plugin.Alert;

import static plugin.ConsoleColors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewNews {
    private final NotificationController notificationController = new NotificationController();

    private final PostController postController = new PostController();
    private final CommentController commentController = new CommentController();
    private final UserController userController = new UserController();
    private final User currentUser = userController.getCurrentUser();
    private final LikeController likeController = new LikeController();


    private void formDeletePost(int idPost) {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃  Are you sure to delete this post?: (Y / N)      ┃");
        System.out.print("┃      ");
        String confirm = Config.scanner().nextLine();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        if (confirm.trim().equalsIgnoreCase("n")) {
            Alert.printCyanAlert("Delete cancelled");
            return;
        }
        if (!confirm.trim().equalsIgnoreCase("y") && !confirm.trim().equalsIgnoreCase("n")) {
            Alert.printRedAlert("Invalid confirmation!!!!");
            return;
        }
        ResponseMessenger messenger = postController.deletePost(idPost);
        switch (messenger.getMessage()) {
            case "id_mismatch":
                Alert.printRedAlert("ID mismatch: " + idPost);
                break;
            case "delete_success":
                Alert.printCyanAlert("Successfully deleted post " + idPost);
        }
    }

    private void formEditPost(int idPost) {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter content: ");
        String content = Config.scanner().nextLine();
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃  Enter post status (public / private / friend):  ┃");
        System.out.print("┃      ");
        String status = Config.scanner().nextLine();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        PostDTO postDTO = new PostDTO(idPost, content, status);
        ResponseMessenger messenger = postController.editPost(postDTO);

        switch (messenger.getMessage()) {
            case "unknown_status":
                Alert.printRedAlert("Unknown status: " + status);
                break;
            case "id_mismatch":
                Alert.printRedAlert("Id mismatch: " + idPost);
                break;
            case "edited":
                Alert.printCyanAlert("Your post has been edited");
        }
    }

    private void formCreatePost() {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter content: ");
        String content = Config.scanner().nextLine();
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃  Enter post status (public / private / friend): ");
        System.out.print("┃      ");
        String status = Config.scanner().nextLine();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        PostDTO postDTO = new PostDTO(postController.getLastId(), content, status);
        ResponseMessenger messenger = postController.createPost(postDTO);

        switch (messenger.getMessage()) {
            case "created":
                Alert.printCyanAlert("Post created");
                break;
            case "unknown_status":
                Alert.printRedAlert("Unknown status: " + status);
        }
    }


    public void formShowYourPosts(int index) {
        List<Post> yourPost = new ArrayList<>(postController.getYourPosts());
        Collections.reverse(yourPost);
        int numberNotice = notificationController.getUnseenNotificationsCount();

        if (yourPost.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-28s  " + BLUE_BRIGHT + " ┃  ┃\n", currentUser.getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃             You have no post               ┃  ┃");
            System.out.println("┃  ┃             on your wall !!!               ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  "+WHITE_BRIGHT+"create"+BLUE_BRIGHT+"   ┃ "+WHITE_BRIGHT+"edit post"+BLUE_BRIGHT+"┃  "+WHITE_BRIGHT+"delete"+BLUE_BRIGHT+"  ┃   "+WHITE_BRIGHT+"back"+BLUE_BRIGHT+"   ┃  ┃");
            System.out.println("┃  ┃   ( 8 )   ┃  ( 9 )   ┃  ( 10 )  ┃  ( 11 )  ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + WHITE + "< 12. Previous |" + WHITE_BRIGHT + " ( 1 // 1 ) " + WHITE + "| 13. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            int likeNumber = likeController.getLikesByPostId(yourPost.get(index).getId()).size();
            int commentNumber = commentController.getCommentsByPostId(yourPost.get(index).getId()).size();
            boolean isLiked = likeController.findLikePost(yourPost.get(index).getId()) != -1;

            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( ' _ ')   %-28s  " + BLUE_BRIGHT + " ┃  ┃\n", currentUser.getName());
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.printf("┃  ┃" + WHITE_BRIGHT + "  %-20s          %10s  " + BLUE_BRIGHT + "┃  ┃\n", postController.getTimePassed(yourPost.get(index).getId()), yourPost.get(index).getStatus());
            System.out.println("┃  ┃  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-36s    " + BLUE_BRIGHT + "┃  ┃\n", yourPost.get(index).getContent());
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
            System.out.printf("┃  ┃  " + (isLiked ? CYAN_BOLD : WHITE_BRIGHT) + "6. Like: %2d               " + WHITE_BRIGHT + "7. Comment: %2d" + BLUE_BRIGHT + "  ┃  ┃\n", likeNumber, commentNumber);
            System.out.println("┃  ┣━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  "+WHITE_BRIGHT+"create"+BLUE_BRIGHT+"   ┃ "+WHITE_BRIGHT+"edit post"+BLUE_BRIGHT+"┃  "+WHITE_BRIGHT+"delete"+BLUE_BRIGHT+"  ┃   "+WHITE_BRIGHT+"back"+BLUE_BRIGHT+"   ┃  ┃");
            System.out.println("┃  ┃   ( 8 )   ┃  ( 9 )   ┃  ( 10 )  ┃  ( 11 )  ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 12. Previous |" + WHITE_BRIGHT + " ( " + (index + 1) + " // " + yourPost.size() + " ) " + (index == yourPost.size() - 1 ? WHITE : WHITE_BRIGHT) + "| 13. Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }


        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewMessenger().menu();
            case 2:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewFriend().menu();
                break;
            case 3:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().mainMenu(0);
                break;
            case 4:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewNotification().formNotification(0);
                break;
            case 5:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().menu();
                break;
            case 6:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (yourPost.isEmpty()) {
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                likePost(yourPost.get(index).getId());
                break;
            case 7:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (yourPost.isEmpty()) {
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                new ViewNews().printPostComment(yourPost.get(index).getId(), 0);
                break;
            case 8:
                formCreatePost();
                break;
            case 9:
                if (yourPost.isEmpty()) {
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                formEditPost(yourPost.get(index).getId());
                break;
            case 10:
                if (yourPost.isEmpty()) {
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    Alert.printRedAlert("Invalid choice !!!");
                    break;
                }
                formDeletePost(yourPost.get(index).getId());
                break;
            case 11:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                return;
            case 12:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous posts");
                } else {
                    index--;
                }
                break;
            case 13:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == yourPost.size() - 1) {
                    Alert.printCyanAlert("There's no more next posts");
                } else {
                    index++;
                }
                break;
            default:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printRedAlert("Invalid choice!!!");
        }

        formShowYourPosts(index);
    }


    private void formLikeComment(int idPost) {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter comment id to like: ");
        int idComment = Config.getValidInteger();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        ResponseMessenger messenger = likeController.createLikeComment(idPost, idComment);
        switch (messenger.getMessage()) {
            case "like":
                Alert.printCyanAlert("You have just liked comment " + idComment);
                break;
            case "dislike":
                Alert.printCyanAlert("You have just disliked comment " + idComment);
                break;
            case "id_mismatch":
                Alert.printRedAlert("Comment " + idComment + " is not found!!!");
        }
    }

    public void likePost(int idPost) {
        ResponseMessenger messenger = likeController.createLikePost(idPost);
        switch (messenger.getMessage()) {
            case "like":
                Alert.printCyanAlert("You have just liked this post");
                break;
            case "dislike":
                Alert.printCyanAlert("You have just disliked this post");
        }
    }

    private void formDeleteComment(int idPost) {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter comment id to delete: ");
        int idComment = Config.getValidInteger();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        ResponseMessenger messenger = commentController.deleteComment(idComment, idPost);
        switch (messenger.getMessage()) {
            case "delete_success":
                Alert.printCyanAlert("Comment " + idComment + " has been successfully deleted");
                break;
            case "id_mismatch":
                Alert.printRedAlert("Comment " + idComment + " is not found!!!");
        }
    }

    public void formComment(int idPost) {
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print("┃  Enter comment: ");
        String comment = Config.getUnEmptyString();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        commentController.createComment(idPost, comment);
        Alert.printCyanAlert("Comment created");
    }

    public void printPostComment(int idPost, int index) {
        List<Comment> commentList = new ArrayList<>(commentController.getCommentsByPostId(idPost));
        Collections.reverse(commentList);
        int numberNotice = notificationController.getUnseenNotificationsCount();

        if (commentList.isEmpty()) {
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃          There's no one commented          ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┃                                            ┃  ┃");
            System.out.println("┃  ┣━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  " + WHITE_BRIGHT + "comment" + BLUE_BRIGHT + " ┃ " + WHITE_BRIGHT + "like cmt" + BLUE_BRIGHT + " ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "back" + BLUE_BRIGHT + "    ┃  ┃");
            System.out.println("┃  ┃   ( 6 )  ┃  ( 7 )   ┃  ( 8 )   ┃  ( 9 )    ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + WHITE + "< 10. Previous |" + WHITE_BRIGHT + " ( 1 // 1 ) " + WHITE + "| 11.  Next  >" + BLUE_BRIGHT + "     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        } else {
            boolean isLiked = likeController.findLikeComment(commentList.get(index).getId()) != -1;
            System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
            System.out.println("┃   " + WHITE_BRIGHT + "Mess" + BLUE_BRIGHT + "  ┃ " + WHITE_BRIGHT + "Friends" + BLUE_BRIGHT + " ┃   " + WHITE_BRIGHT + "Home" + BLUE_BRIGHT + "   ┃" + WHITE_BRIGHT + (numberNotice == 0 ? " Notice  " : " Noti" + RED_BOLD_BRIGHT + "(" + numberNotice + ") ") + BLUE_BOLD_BRIGHT + BLUE_BRIGHT + "┃  " + WHITE_BRIGHT + "Menu" + BLUE_BRIGHT + "   ┃");
            System.out.println("┃  ( 1 )  ┃  ( 2 )  ┃  (  3 )  ┃  ( 4 )  ┃  ( 5 )  ┃");
            System.out.println("┣━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━┻━━━━━━━━━┫");
            System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");

            System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')  %-20s┃   ID:%3d" + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(commentList.get(index).getIdUser()).getName(), commentList.get(index).getId());
            System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-28s ┃ " + (isLiked ? CYAN_BOLD_BRIGHT + "Like:%3d " : "Like:%3d ") + BLUE_BRIGHT + "┃  ┃\n", commentList.get(index).getContent(), likeController.getLikesByCommentId(commentList.get(index).getId()).size());
            System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            if ((index + 1) > commentList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                isLiked = likeController.findLikeComment(commentList.get(index + 1).getId()) != -1;
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')  %-20s┃   ID:%3d" + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(commentList.get(index + 1).getIdUser()).getName(), commentList.get(index + 1).getId());
                System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-28s ┃ " + (isLiked ? CYAN_BOLD_BRIGHT + "Like:%3d " : "Like:%3d ") + BLUE_BRIGHT + "┃  ┃\n", commentList.get(index + 1).getContent(), likeController.getLikesByCommentId(commentList.get(index + 1).getId()).size());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 2) > commentList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                isLiked = likeController.findLikeComment(commentList.get(index + 2).getId()) != -1;
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')  %-20s┃   ID:%3d" + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(commentList.get(index + 2).getIdUser()).getName(), commentList.get(index + 2).getId());
                System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-28s ┃ " + (isLiked ? CYAN_BOLD_BRIGHT + "Like:%3d " : "Like:%3d ") + BLUE_BRIGHT + "┃  ┃\n", commentList.get(index + 2).getContent(), likeController.getLikesByCommentId(commentList.get(index + 2).getId()).size());
                System.out.println("┃  ┃  " + WHITE + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + BLUE_BRIGHT + "  ┃  ┃");
            }
            if ((index + 3) > commentList.size() - 1) {
                System.out.println("┃  ┃                                            ┃  ┃");
                System.out.println("┃  ┃                                            ┃  ┃");
            } else {
                isLiked = likeController.findLikeComment(commentList.get(index + 3).getId()) != -1;
                System.out.printf("┃  ┃  " + WHITE_BRIGHT + "( '  _ ')  %-20s┃   ID:%3d" + BLUE_BRIGHT + " ┃  ┃\n", userController.findById(commentList.get(index + 3).getIdUser()).getName(), commentList.get(index + 3).getId());
                System.out.printf("┃  ┃" + WHITE_BRIGHT + "    %-28s ┃ " + (isLiked ? CYAN_BOLD_BRIGHT + "Like:%3d " : "Like:%3d ") + BLUE_BRIGHT + "┃  ┃\n", commentList.get(index + 3).getContent(), likeController.getLikesByCommentId(commentList.get(index + 3).getId()).size());
            }
            System.out.println("┃  ┣━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━┫  ┃");
            System.out.println("┃  ┃  " + WHITE_BRIGHT + "comment" + BLUE_BRIGHT + " ┃ " + WHITE_BRIGHT + "like cmt" + BLUE_BRIGHT + " ┃  " + WHITE_BRIGHT + "delete" + BLUE_BRIGHT + "  ┃   " + WHITE_BRIGHT + "back" + BLUE_BRIGHT + "    ┃  ┃");
            System.out.println("┃  ┃   ( 6 )  ┃  ( 7 )   ┃  ( 8 )   ┃  ( 9 )    ┃  ┃");
            System.out.println("┃  ┗━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━┛  ┃");
            System.out.println("┃    " + (index == 0 ? WHITE : WHITE_BRIGHT) + "< 10. Previous |" + WHITE_BRIGHT + " ( " + (index / 4 + 1) + " // " + (commentList.size() % 4 == 0 && commentList.size() != 0 ? commentList.size() / 4 : commentList.size() / 4 + 1) + " ) " + (index + 5 > (commentList.size()) ? WHITE : WHITE_BRIGHT) + "| 11.  Next  >" + BLUE_BRIGHT + "    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }


        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();

        switch (choice) {
            case 3:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printCyanAlert("Already in home page");
                break;
            case 4:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewNotification().formNotification(0);
                break;
            case 5:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                new ViewHome().menu();
                break;
            case 6:
                formComment(idPost);
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                break;
            case 7:
                formLikeComment(idPost);
                break;
            case 8:
                formDeleteComment(idPost);
                break;
            case 9:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                return;
            case 10:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index == 0) {
                    Alert.printCyanAlert("There's no more previous comments");
                } else {
                    index -= 4;
                }
                break;
            case 11:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                if (index + 5 > (commentList.size())) {
                    Alert.printCyanAlert("There's no more next comments");
                } else {
                    index += 4;
                }
                break;
            default:
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                Alert.printRedAlert("Invalid choice!");
        }
        printPostComment(idPost, index);
    }


}
