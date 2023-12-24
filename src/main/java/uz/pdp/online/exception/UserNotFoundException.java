package uz.pdp.online.exception;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message, String path) {
        super(message, path);
    }
}