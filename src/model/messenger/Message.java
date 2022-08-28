package model.messenger;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idUser;
    private int idTo;
    private String content;
    private Date time;
    private boolean seen;

    public Message() {
    }

    public Message(int id, int idUser, int idTo, String content, Date time) {
        this.id = id;
        this.idUser = idUser;
        this.idTo = idTo;
        this.time = time;
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

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idTo=" + idTo +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", seen=" + seen +
                '}';
    }
}
