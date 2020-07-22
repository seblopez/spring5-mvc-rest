package guru.springfamework.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ResourceNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String errorMessage, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(errorMessage, cause, enableSuppression, writeableStackTrace);
    }

}
