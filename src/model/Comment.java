package model;

import java.io.Serializable;

public class Comment implements Serializable {

    private int id;
    private int idUser;
    private int idPost;
    private String content;

    public Comment() {
    }

    public Comment(int id, int idUser, int idPost, String content) {
        this.id = id;
        this.idUser = idUser;
        this.idPost = idPost;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idPost=" + idPost +
                ", content='" + content + '\'' +
                '}';
    }
}
