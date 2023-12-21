package uz.pdp.online.exception;


public class WeatherDataNotFoundException extends NotFoundException {

    public WeatherDataNotFoundException(String message, String path) {
        super(message, path);
    }
}
