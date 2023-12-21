package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.City;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.constants.ColumnConstants.ID;
import static uz.pdp.online.constants.ColumnConstants.NAME;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);

        return City.builder()
                .id(id)
                .name(name)
                .build();
    }
}
