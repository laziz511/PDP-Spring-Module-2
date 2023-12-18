package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.AuthPermissionRowMapper;
import uz.pdp.online.model.AuthPermission;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static uz.pdp.online.repository.ColumnConstants.ROLE_ID;

@Repository
@RequiredArgsConstructor
public class AuthPermissionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_ALL_BY_ROLE_ID_SQL = """
            SELECT ap.* FROM auth_permission ap
            JOIN role_permission rp ON ap.id = rp.permission_id
            WHERE rp.role_id = :role_id
            """;

    public List<AuthPermission> findAllByRoleId(Long roleId) {
        Map<String, Object> parameters = Collections.singletonMap(ROLE_ID, roleId);
        return namedParameterJdbcTemplate.query(FIND_ALL_BY_ROLE_ID_SQL, parameters, new AuthPermissionRowMapper());
    }
}
