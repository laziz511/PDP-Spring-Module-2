package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uz.pdp.online.exception.KeyRetrievalException;
import uz.pdp.online.exception.UserNotFoundException;
import uz.pdp.online.mapper.AuthUserRowMapper;
import uz.pdp.online.model.AuthUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static uz.pdp.online.constants.ColumnConstants.*;

@Repository
@RequiredArgsConstructor
public class AuthUserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE_SQL = "INSERT INTO auth_user (username, password, role) VALUES (:username, :password, :role) RETURNING id";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM auth_user WHERE id = :id";
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM auth_user WHERE username = :username";
    private static final String FIND_ALL_SQL = "SELECT * FROM auth_user WHERE role = 'USER'";
    private static final String UPDATE_SQL = "UPDATE auth_user SET username = :username, password = :password, role = :role WHERE id = :id";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM auth_user WHERE id = :id";

    public AuthUser save(AuthUser user) {
        SqlParameterSource parameters = buildParameterSource(user);

        try {
            Long generatedId = namedParameterJdbcTemplate.queryForObject(SAVE_SQL, parameters, Long.class);

            if (generatedId != null) {
                user.setId(generatedId);
                return user;
            } else {
                throw new KeyRetrievalException("Failed to retrieve the generated key after insert");
            }
        } catch (DataAccessException e) {
            throw new KeyRetrievalException("Error saving AuthUser", e);
        }
    }

    public List<AuthUser> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_SQL, new AuthUserRowMapper());
    }

    public Optional<AuthUser> findById(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, paramSource, new AuthUserRowMapper()));
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(USERNAME, username);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_USERNAME_SQL, paramSource, new AuthUserRowMapper()));
    }

    public void update(AuthUser user) {
        namedParameterJdbcTemplate.update(UPDATE_SQL, buildParameterMap(user));
    }

    public void delete(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        try {
            namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL, paramSource);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User not found with id: '%s'".formatted(id), "/admin/users/show");
        }
    }

    private SqlParameterSource buildParameterSource(AuthUser user) {
        return new MapSqlParameterSource()
                .addValue(USERNAME, user.getUsername())
                .addValue(PASSWORD, user.getPassword())
                .addValue(ROLE, user.getRole());
    }

    private Map<String, Object> buildParameterMap(AuthUser user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ID, user.getId());
        parameters.put(USERNAME, user.getUsername());
        parameters.put(PASSWORD, user.getPassword());
        parameters.put(ROLE, user.getRole());
        return parameters;
    }
}
