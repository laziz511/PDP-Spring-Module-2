package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.online.dto.UserRegisterDTO;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.repository.AuthUserRepository;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMessage", error);
        return modelAndView;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDTO dto) {
        AuthUser authUser = new AuthUser(null, dto.username(), passwordEncoder.encode(dto.password()));
        authUserRepository.save(authUser);
        return "redirect:/auth/login";
    }
}
