package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.AuthRoleRowMapper;
import uz.pdp.online.model.AuthRole;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static uz.pdp.online.repository.ColumnConstants.*;

@Repository
@RequiredArgsConstructor
public class AuthRoleRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_ALL_BY_USER_ID_SQL = """
            SELECT ar.* FROM auth_role ar
            JOIN user_role ur ON ar.id = ur.role_id
            WHERE ur.user_id = :user_id
            """;

    private static final String ASSIGN_ROLE_SQL = """
            INSERT INTO user_role (user_id, role_id) VALUES (:user_id, :role_id)
            """;

    private static final String FIND_BY_NAME_SQL = "SELECT * FROM auth_role WHERE name = :name";

    public List<AuthRole> findAllByUserId(Long userId) {
        Map<String, Object> parameters = Collections.singletonMap(USER_ID, userId);
        return namedParameterJdbcTemplate.query(FIND_ALL_BY_USER_ID_SQL, parameters, new AuthRoleRowMapper());
    }

    public void assignRole(Long userId, Long roleId) {
        Map<String, Object> parameters = new MapSqlParameterSource()
                .addValue(USER_ID, userId)
                .addValue(ROLE_ID, roleId)
                .getValues();

        namedParameterJdbcTemplate.update(ASSIGN_ROLE_SQL, parameters);
    }

    public AuthRole findByName(String name) {
        Map<String, Object> parameters = Collections.singletonMap(NAME, name);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_NAME_SQL, parameters, new AuthRoleRowMapper());
    }
}
