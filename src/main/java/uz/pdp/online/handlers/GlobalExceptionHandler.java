package uz.pdp.online.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.online.exception.CityNotFoundException;
import uz.pdp.online.exception.UserNotFoundException;
import uz.pdp.online.exception.WeatherDataNotFoundException;

@ControllerAdvice("uz.pdp.online")
public class GlobalExceptionHandler {
    private static final String ERROR_PAGE_ADDRESS = "error/404";
    private static final String MESSAGE = "message";
    private static final String BACK_PATH = "back_path";

    @ExceptionHandler({WeatherDataNotFoundException.class,
            CityNotFoundException.class,
            UserNotFoundException.class})
    public ModelAndView error_404(HttpServletRequest request, WeatherDataNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(ERROR_PAGE_ADDRESS);
        modelAndView.addObject(MESSAGE, e.getMessage());
        modelAndView.addObject(BACK_PATH, e.getPath());

        return modelAndView;
    }
}