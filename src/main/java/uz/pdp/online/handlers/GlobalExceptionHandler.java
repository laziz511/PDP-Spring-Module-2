package uz.pdp.online.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.online.exception.TodoNotFoundException;

@ControllerAdvice("uz.pdp.online")
public class GlobalExceptionHandler {

    @ExceptionHandler({TodoNotFoundException.class})
    public ModelAndView error_404(HttpServletRequest request, TodoNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("back_path", e.getPath());

        return modelAndView;
    }


}