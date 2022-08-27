package model;

import java.io.Serializable;

public class Like implements Serializable {

    private int id;
    private int idUser;
    private int idPost;
    private int idComment;

    public Like() {
    }

    public Like(int id, int idUser, int idPost, int idComment) {
        this.id = id;
        this.idUser = idUser;
        this.idPost = idPost;
        this.idComment = idComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idPost=" + idPost +
                ", idComment=" + idComment +
                '}';
    }
}
