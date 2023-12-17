package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthUserRowMapper implements RowMapper<AuthUser> {

    @Override
    public AuthUser mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        return new AuthUser(id, username, password);
    }
}
