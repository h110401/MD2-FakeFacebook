package dto.response;

public class ResponseMessenger {

    private String message;

    public ResponseMessenger() {
    }

    public ResponseMessenger(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
