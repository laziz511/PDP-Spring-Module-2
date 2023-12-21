package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.CityRowMapper;
import uz.pdp.online.mapper.SubscriptionRowMapper;
import uz.pdp.online.model.City;
import uz.pdp.online.model.Subscription;

import java.util.List;

import static uz.pdp.online.constants.ColumnConstants.CITY_ID;
import static uz.pdp.online.constants.ColumnConstants.USER_ID;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM subscriptions WHERE user_id = :user_id";
    private static final String SAVE_SQL = "INSERT INTO subscriptions (user_id, city_id) VALUES (:user_id, :city_id)";

    private static final String FIND_SUBSCRIBED_CITIES_BY_USER_ID_SQL = """
             SELECT cities.* FROM cities
             JOIN subscriptions ON cities.id = subscriptions.city_id
             WHERE subscriptions.user_id = :user_id
            """;

    public void save(Subscription subscription) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(USER_ID, subscription.getUserId())
                .addValue(CITY_ID, subscription.getCityId());
        namedParameterJdbcTemplate.update(SAVE_SQL, parameters);
    }

    public List<Subscription> findByUserId(Long userId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(FIND_BY_USER_ID_SQL, paramSource, new SubscriptionRowMapper());
    }

    public List<City> findSubscribedCitiesByUserId(Long userId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(FIND_SUBSCRIBED_CITIES_BY_USER_ID_SQL, paramSource, new CityRowMapper());
    }
}
