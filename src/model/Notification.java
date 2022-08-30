package model;

import java.io.Serializable;

public class Notification implements Serializable {

    static final long serialVersionUID = 1L;

    private int id;
    private int idUser;
    private String notification;

    private boolean seen;

    public Notification() {
    }

    public Notification(int id, int idUser, String notification) {
        this.id = id;
        this.idUser = idUser;
        this.notification = notification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notification='" + notification + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
