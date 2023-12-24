package uz.pdp.online.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.model.WeatherData;
import uz.pdp.online.repository.WeatherDataRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/weather")
@PreAuthorize("hasRole('ADMIN')")
public class WeatherController {

    private final WeatherDataRepository weatherDataRepository;

    @GetMapping("/show")
    public String showWeatherList(Model model) {
        model.addAttribute("forecasts", weatherDataRepository.findAllWithCityNames());
        return "admin/weather/show";
    }

    @GetMapping("/add/{cityId}")
    public String showAddWeatherForm(@PathVariable Long cityId, Model model) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCityId(cityId);
        model.addAttribute("weatherData", weatherData);
        return "admin/weather/add";
    }

    @PostMapping("/add")
    public String addWeather(@ModelAttribute WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
        return "redirect:/admin/weather/show";
    }

}
