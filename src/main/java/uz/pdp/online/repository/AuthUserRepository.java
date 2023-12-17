package uz.pdp.online.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.AuthUserRowMapper;
import uz.pdp.online.model.AuthUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthUserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(AuthUser user) {
        String sql = "INSERT INTO users (username, password) VALUES (:username, :password)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        var sql = "SELECT * FROM users WHERE username = :username";
        var paramSource = new MapSqlParameterSource().addValue("username", username);
        try {
            AuthUser user = namedParameterJdbcTemplate.queryForObject(sql, paramSource, new AuthUserRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
