package uz.pdp.online.exception;

public class CityNotFoundException extends NotFoundException {

    public CityNotFoundException(String message, String path) {
        super(message, path);
    }
}