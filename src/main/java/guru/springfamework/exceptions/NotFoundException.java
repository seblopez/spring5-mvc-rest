package guru.springfamework.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
