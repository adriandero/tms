package at.snt.tms.model.dtos.response;

/**
 * Class {@code MessageResponse}
 *
 * @author Oliver Sommer
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
