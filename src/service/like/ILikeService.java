package service.like;

import model.Like;
import service.IGenericService;

import java.util.List;

public interface ILikeService extends IGenericService<Like> {
    List<Like> getLikesByPostId(int idPost);

    List<Like> getLikesByCommentId(int idComment);
}
