package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.WeatherData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static uz.pdp.online.constants.ColumnConstants.*;

public class WeatherDataRowMapper implements RowMapper<WeatherData> {

    @Override
    public WeatherData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return WeatherData.builder()
                .id(resultSet.getLong(ID))
                .cityId(resultSet.getLong(CITY_ID))
                .day(resultSet.getObject(DAY, LocalDate.class))
                .temperature(resultSet.getDouble(TEMPERATURE))
                .humidity(resultSet.getDouble(HUMIDITY))
                .build();
    }
}
