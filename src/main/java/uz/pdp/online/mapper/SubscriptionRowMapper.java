package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.constants.ColumnConstants.*;

public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong(ID);
        Long userId = resultSet.getLong(USER_ID);
        Long cityId = resultSet.getLong(CITY_ID);

        return Subscription.builder()
                .id(id)
                .userId(userId)
                .cityId(cityId)
                .build();
    }
}