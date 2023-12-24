package uz.pdp.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.config.security.CustomUserDetails;
import uz.pdp.online.dto.WeatherDataWithCityNameDTO;
import uz.pdp.online.exception.CityNotFoundException;
import uz.pdp.online.model.City;
import uz.pdp.online.model.Subscription;
import uz.pdp.online.repository.CityRepository;
import uz.pdp.online.repository.SubscriptionRepository;
import uz.pdp.online.repository.WeatherDataRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final CityRepository cityRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WeatherDataRepository weatherDataRepository;

    @GetMapping("/city/show")
    public String showCities(Model model) {

        List<City> cities = cityRepository.findAll();
        model.addAttribute("cities", cities);

        return "user/city/show";
    }

    @PostMapping("/city/subscribe/{id}")
    public String subscribe(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getAuthUser().getId();

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found with id: '%s'".formatted(id), "/user/city/show"));

        Subscription subscription = Subscription.builder()
                .userId(userId)
                .cityId(city.getId())
                .build();

        subscriptionRepository.save(subscription);
        return "redirect:/user/city/show";
    }

    @GetMapping("/weather/show")
    public String showWeatherList(Model model) {
        model.addAttribute("forecasts", weatherDataRepository.findAllWithCityNames());
        return "user/weather/show";
    }


    @GetMapping("/weather/subscribed")
    public String showSubscribedWeatherList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getAuthUser().getId();

        List<City> subscribedCities = subscriptionRepository.findSubscribedCitiesByUserId(userId);

        List<WeatherDataWithCityNameDTO> subscribedForecasts = weatherDataRepository.findAllWithCityNames(subscribedCities);

        model.addAttribute("forecasts", subscribedForecasts);
        return "user/weather/subscription";
    }

}
