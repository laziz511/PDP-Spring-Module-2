package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.dto.WeatherDataWithCityNameDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.constants.ColumnConstants.*;

public class WeatherDataWithCityNameRowMapper implements RowMapper<WeatherDataWithCityNameDTO> {
    @Override
    public WeatherDataWithCityNameDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WeatherDataWithCityNameDTO.builder()
                .id(rs.getLong(ID))
                .cityId(rs.getLong(CITY_ID))
                .day(rs.getObject(DAY, java.time.LocalDate.class))
                .temperature(rs.getDouble(TEMPERATURE))
                .humidity(rs.getDouble(HUMIDITY))
                .cityName(rs.getString(CITY_NAME))
                .build();
    }
}