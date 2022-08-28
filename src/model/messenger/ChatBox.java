package model.messenger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatBox implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Set<Integer> idUsers;
    private List<Message> messages = new ArrayList<>();

    public ChatBox() {
    }

    public ChatBox(int id, Set<Integer> idUsers, List<Message> messages) {
        this.id = id;
        this.idUsers = idUsers;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Set<Integer> idUsers) {
        this.idUsers = idUsers;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatBox{" +
                "id=" + id +
                ", idUsers=" + idUsers +
                ", messages=" + messages +
                '}';
    }
}
