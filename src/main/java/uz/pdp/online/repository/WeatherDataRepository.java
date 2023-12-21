package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.dto.WeatherDataWithCityNameDTO;
import uz.pdp.online.exception.WeatherDataNotFoundException;
import uz.pdp.online.mapper.WeatherDataRowMapper;
import uz.pdp.online.mapper.WeatherDataWithCityNameRowMapper;
import uz.pdp.online.model.City;
import uz.pdp.online.model.WeatherData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.pdp.online.constants.ColumnConstants.*;

@Repository
@RequiredArgsConstructor
public class WeatherDataRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE_SQL = "INSERT INTO weather_data (city_id, day, temperature, humidity) VALUES (:city_id, :day, :temperature, :humidity)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM weather_data WHERE id = :id";
    private static final String FIND_ALL_SQL = "SELECT * FROM weather_data";
    private static final String UPDATE_BY_ID_SQL = "UPDATE weather_data SET city_id = :cityId, day = :day, temperature = :temperature, humidity = :humidity WHERE id = :id";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM weather_data WHERE id = :id";

    private static final String FIND_ALL_WITH_CITY_NAMES_SQL = """
            SELECT wd.id, wd.city_id, wd.day, wd.temperature, wd.humidity, c.name AS city_name
            FROM weather_data wd
            JOIN cities c ON wd.city_id = c.id
            """;

    private static final String FIND_ALL_WITH_SUBSCRIBED_CTY_NAMES_SQL = """
            SELECT wd.id, wd.city_id, wd.day, wd.temperature, wd.humidity, c.name AS city_name
            FROM weather_data wd
            JOIN cities c ON wd.city_id = c.id
            WHERE wd.city_id IN (:cityIds)
            """;


    public void save(WeatherData weatherData) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CITY_ID, weatherData.getCityId())
                .addValue(DAY, weatherData.getDay())
                .addValue(TEMPERATURE, weatherData.getTemperature())
                .addValue(HUMIDITY, weatherData.getHumidity());
        namedParameterJdbcTemplate.update(SAVE_SQL, parameters);
    }

    public List<WeatherDataWithCityNameDTO> findAllWithCityNames() {
        return namedParameterJdbcTemplate.query(FIND_ALL_WITH_CITY_NAMES_SQL, new WeatherDataWithCityNameRowMapper());
    }

    public List<WeatherDataWithCityNameDTO> findAllWithCityNames(List<City> subscribedCities) {
        List<Long> cityIds = subscribedCities.stream()
                .map(City::getId)
                .collect(Collectors.toList());

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CITY_IDS, cityIds);

        return namedParameterJdbcTemplate.query(FIND_ALL_WITH_SUBSCRIBED_CTY_NAMES_SQL, parameters, new WeatherDataWithCityNameRowMapper());
    }


    public Optional<WeatherData> findById(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, paramSource, new WeatherDataRowMapper()));
    }

    public List<WeatherData> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_SQL, new WeatherDataRowMapper());
    }

    public void update(WeatherData weatherData) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, weatherData.getId())
                .addValue(CITY_ID, weatherData.getCityId())
                .addValue(DAY, weatherData.getDay())
                .addValue(TEMPERATURE, weatherData.getTemperature())
                .addValue(HUMIDITY, weatherData.getHumidity());

        try {
            namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        } catch (EmptyResultDataAccessException e) {
            throw new WeatherDataNotFoundException("Weather data not found with id: '%s'".formatted(weatherData.getId()), "/admin/weather/show");
        }
    }

    public void delete(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        try {
            namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL, paramSource);
        } catch (EmptyResultDataAccessException e) {
            throw new WeatherDataNotFoundException("Weather data not found with id: '%s'".formatted(id), "/admin/weather/show");
        }
    }
}
