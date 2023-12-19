package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.repository.ColumnConstants.*;

public class AuthUserRowMapper implements RowMapper<AuthUser> {

    @Override
    public AuthUser mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong(ID);
        String username = resultSet.getString(USERNAME);
        String password = resultSet.getString(PASSWORD);
        boolean blocked = resultSet.getBoolean(BLOCKED);
        String profilePhotoPath = resultSet.getString("profile_photo_path");

        return AuthUser.builder()
                .id(id)
                .username(username)
                .password(password)
                .blocked(blocked)
                .profilePhotoPath(profilePhotoPath)
                .build();
    }
}
