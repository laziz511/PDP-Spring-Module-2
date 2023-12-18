package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.AuthPermission;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.repository.ColumnConstants.ID;
import static uz.pdp.online.repository.ColumnConstants.NAME;

public class AuthPermissionRowMapper implements RowMapper<AuthPermission> {

    @Override
    public AuthPermission mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);

        return AuthPermission.builder()
                .id(id)
                .name(name)
                .build();
    }
}

