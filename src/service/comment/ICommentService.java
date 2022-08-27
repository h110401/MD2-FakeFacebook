package service.comment;

import model.Comment;
import service.IGenericService;

import java.util.ArrayList;
import java.util.List;

public interface ICommentService extends IGenericService<Comment> {

    List<Comment> getCommentsByPostId(int id);
}
