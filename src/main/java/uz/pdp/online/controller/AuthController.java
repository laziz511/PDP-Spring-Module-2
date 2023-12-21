package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.online.dto.UserRegisterDTO;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.repository.AuthUserRepository;

import static uz.pdp.online.constants.Role.USER;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    @Transactional
    public String register(UserRegisterDTO dto) {
        AuthUser authUser = buildAuthUser(dto);
        authUserRepository.save(authUser);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("auth/login");
        modelAndView.addObject("errorMessage", error);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    private AuthUser buildAuthUser(UserRegisterDTO dto) {
        return AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(USER)
                .build();
    }
}
