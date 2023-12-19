package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uz.pdp.online.exception.KeyRetrievalException;
import uz.pdp.online.mapper.AuthUserRowMapper;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.model.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static uz.pdp.online.repository.ColumnConstants.*;

@Repository
@RequiredArgsConstructor
public class AuthUserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AuthRoleRepository authRoleRepository;

    private static final String FIND_BY_ID_SQL = "SELECT * FROM auth_user WHERE id = :id";
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM auth_user WHERE username = :username";
    private static final String FIND_ALL_SQL = """
            SELECT * FROM auth_user
            WHERE blocked = false AND id NOT IN (SELECT user_id FROM user_role WHERE role_id = :adminRoleId)
            """;
    private static final String SAVE_SQL = """
            INSERT INTO auth_user (username, password, blocked, profile_photo_path)
            VALUES (:username, :password, :blocked, :profilePhotoPath) RETURNING id
            """;
    private static final String UPDATE_SQL = """
            UPDATE auth_user 
            SET username = :username, password = :password, blocked = :blocked
            WHERE id = :id
            """;

    public AuthUser save(AuthUser user) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(USERNAME, user.getUsername())
                .addValue(PASSWORD, user.getPassword())
                .addValue(BLOCKED, user.isBlocked())
                .addValue(PROFILE_PHOTO_PATH, user.getProfilePhotoPath());

        Long generatedId = namedParameterJdbcTemplate.queryForObject(SAVE_SQL, parameters, Long.class);

        if (generatedId != null) {
            user.setId(generatedId);
            return user;
        } else {
            throw new KeyRetrievalException("Failed to retrieve the generated key after insert");
        }
    }


    public List<AuthUser> findAll() {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(ADMIN_ROLE_ID, authRoleRepository.findByName(Role.ADMIN).getId());
        return namedParameterJdbcTemplate.query(FIND_ALL_SQL, parameters, new AuthUserRowMapper());
    }

    public Optional<AuthUser> findById(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        try {
            AuthUser user = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, paramSource, new AuthUserRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(USERNAME, username);
        try {
            AuthUser user = namedParameterJdbcTemplate.queryForObject(FIND_BY_USERNAME_SQL, paramSource, new AuthUserRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void update(AuthUser user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ID, user.getId());
        parameters.put(USERNAME, user.getUsername());
        parameters.put(PASSWORD, user.getPassword());
        parameters.put(BLOCKED, user.isBlocked());

        namedParameterJdbcTemplate.update(UPDATE_SQL, parameters);
    }
}
