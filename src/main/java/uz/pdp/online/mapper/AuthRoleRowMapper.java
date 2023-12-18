package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.AuthRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import static uz.pdp.online.repository.ColumnConstants.ID;
import static uz.pdp.online.repository.ColumnConstants.NAME;

public class AuthRoleRowMapper implements RowMapper<AuthRole> {

    @Override
    public AuthRole mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);

        return AuthRole.builder()
                .id(id)
                .name(name)
                .build();
    }
}
