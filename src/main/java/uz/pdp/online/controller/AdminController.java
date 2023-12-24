package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.UserInfoDTO;
import uz.pdp.online.exception.CityNotFoundException;
import uz.pdp.online.exception.UserNotFoundException;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.model.City;
import uz.pdp.online.model.Subscription;
import uz.pdp.online.repository.AuthUserRepository;
import uz.pdp.online.repository.CityRepository;
import uz.pdp.online.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final SubscriptionRepository subscriptionRepository;
    private final AuthUserRepository authUserRepository;
    private final CityRepository cityRepository;

    @GetMapping("/city/show")
    public String showCities(Model model) {

        List<City> cities = cityRepository.findAll();
        model.addAttribute("cities", cities);

        return "admin/city/show";
    }

    @GetMapping("/city/add")
    public String showCityAdd() {
        return "admin/city/add";
    }

    @PostMapping("/city/add")
    public String addCity(City city) {
        cityRepository.save(city);
        return "redirect:/admin/city/show";
    }

    @GetMapping("/city/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found with id: '%s'".formatted(id), "/admin/city/show"));

        model.addAttribute("city", city);
        return "admin/city/edit";
    }

    @PostMapping("/city/update")
    public String updateCity(@ModelAttribute City updatedCity) {
        City existingCity = cityRepository.findById(updatedCity.getId())
                .orElseThrow(() -> new CityNotFoundException("City not found with id: '%s'".formatted(updatedCity.getId()), "/admin/city/show"));

        existingCity.setName(updatedCity.getName());
        cityRepository.update(existingCity);
        return "redirect:/admin/city/show";
    }

    @PostMapping("/city/delete")
    public String deleteCity(@RequestParam Long id) {
        cityRepository.delete(id);
        return "redirect:/admin/city/show";
    }


    @GetMapping("/users/show")
    public String showAllUsers(Model model) {

        List<AuthUser> users = authUserRepository.findAll();
        List<UserInfoDTO> userInfoList = new ArrayList<>();

        populateUserInfoList(users, userInfoList);

        model.addAttribute("users", userInfoList);
        return "admin/user/show";
    }

    @GetMapping("/users/edit/{id}")
    public String showUserEditPage(@PathVariable Long id, Model model) {
        AuthUser user = authUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: '%s'".formatted(id), "/admin/users/show"));

        model.addAttribute("user", user);
        return "admin/user/edit";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute AuthUser authUser) {
        AuthUser user = authUserRepository.findById(authUser.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: '%s'".formatted(authUser.getId()), "/admin/users/show"));

        user.setUsername(authUser.getUsername());
        authUserRepository.update(user);
        return "redirect:/admin/users/show";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        authUserRepository.delete(id);
        return "redirect:/admin/users/show";
    }


    private void populateUserInfoList(List<AuthUser> users, List<UserInfoDTO> userInfoList) {
        for (AuthUser user : users) {
            UserInfoDTO dto = buildUserInfoDTO(user);
            userInfoList.add(dto);

            List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());
            List<City> userSubscriptions = new ArrayList<>();

            for (Subscription subscription : subscriptions) {
                Optional<City> optionalCity = cityRepository.findById(subscription.getCityId());
                optionalCity.ifPresent(userSubscriptions::add);
            }

            dto.setSubscriptions(userSubscriptions);

        }
    }

    private static UserInfoDTO buildUserInfoDTO(AuthUser user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
