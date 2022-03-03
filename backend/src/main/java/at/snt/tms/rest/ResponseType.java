package at.snt.tms.rest;

/**
 * Class {@code ResponseType.java}
 * <p>
 * Taken from https://tomd.xyz/camel-rest/
 *
 */


public class ResponseType {

    private String message;

    public ResponseType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

