package uz.pdp.online.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Todo Not Found With Given ID")
public class TodoNotFoundException extends RuntimeException {
    private final String path;

    public TodoNotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }

}


