package model.friend;

import java.io.Serializable;

public class Friend implements Serializable {

    static final long serialVersionUID = 1L;

    private int id;
    private int idUser;
    private int idTo;

    public Friend() {
    }

    public Friend(int id, int idUser, int idTo) {
        this.id = id;
        this.idUser = idUser;
        this.idTo = idTo;
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

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idTo=" + idTo +
                '}';
    }
}
