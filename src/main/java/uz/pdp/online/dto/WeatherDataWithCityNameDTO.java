package uz.pdp.online.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDataWithCityNameDTO {
    private Long id;
    private Long cityId;
    private LocalDate day;
    private Double temperature;
    private Double humidity;
    private String cityName;
}
