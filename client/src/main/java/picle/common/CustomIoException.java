package picle.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomIoException extends RuntimeException {

    @JsonProperty
    private String type;
    @JsonProperty
    private String message;

    public CustomIoException() {
        super();
    }

    /**
     * Constructor.
     * @param type Type of error.
     * @param message Message shown to the user.
     */
    public CustomIoException(String type, String message) {
        super(message);
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
