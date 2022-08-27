package service.comment;

import config.Config;
import model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceIMPL implements ICommentService {

    private static final String PATH_COMMENT = "src/data/comment.txt";
    private static final Config<List<Comment>> config = new Config<>();

    private static List<Comment> commentList = config.read(PATH_COMMENT);

    static {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
    }

    @Override
    public List<Comment> findAll() {
        return commentList;
    }

    @Override
    public void save(Comment comment) {
        commentList.add(comment);
        updateData();
    }

    @Override
    public Comment findById(int id) {
        for (Comment comment : commentList) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        commentList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_COMMENT, commentList);
    }

    @Override
    public int getLastId() {
        return commentList.isEmpty() ? 1 : commentList.get(commentList.size() - 1).getId() + 1;
    }

    public List<Comment> getCommentsByPostId(int id) {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            if (comment.getIdPost() == id) {
                comments.add(comment);
            }
        }
        return comments;
    }
}
