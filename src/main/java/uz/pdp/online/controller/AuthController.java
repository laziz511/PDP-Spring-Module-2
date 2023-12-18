package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.online.config.security.CustomUserDetails;
import uz.pdp.online.dto.UserRegisterDTO;
import uz.pdp.online.model.AuthRole;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.repository.AuthRoleRepository;
import uz.pdp.online.repository.AuthUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static uz.pdp.online.model.Role.USER;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMessage", error);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logoutPage(Model model) {
        return "auth/logout";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "/auth/register";
    }

    @PostMapping("/register")
    @Transactional
    public String register(@ModelAttribute UserRegisterDTO dto) {
        AuthUser authUser = new AuthUser(null, dto.username(), passwordEncoder.encode(dto.password()), Collections.emptyList(), false);
        AuthUser savedUser = authUserRepository.save(authUser);

        AuthRole userAuthRole = authRoleRepository.findByName(USER);

        authRoleRepository.assignRole(savedUser.getId(), userAuthRole.getId());

        return "redirect:/auth/login";
    }



    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAllUsers(Model model,
                               @AuthenticationPrincipal CustomUserDetails adminDetails) {
        List<AuthUser> users = authUserRepository.findAll();
        model.addAttribute("users", users);

        return "userList";
    }

    @PostMapping("/block/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String blockUser(@PathVariable Long userId,
                            @AuthenticationPrincipal CustomUserDetails adminDetails) {
        System.out.println("blockUser works");
        Optional<AuthUser> userToBlock = authUserRepository.findById(userId);
        AuthUser user = userToBlock.orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        if (user != null) {
            user.setBlocked(true);
            authUserRepository.update(user);
        }

        return "redirect:/auth/users";
    }

}
