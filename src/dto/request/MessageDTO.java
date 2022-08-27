package dto.request;

public class MessageDTO {

    private String content;
    private int idTo;

    public MessageDTO() {
    }

    public MessageDTO(String content, int idTo) {
        this.content = content;
        this.idTo = idTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }
}
