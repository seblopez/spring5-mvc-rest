package guru.springfamework.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
