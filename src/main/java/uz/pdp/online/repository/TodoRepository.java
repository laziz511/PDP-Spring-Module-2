package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uz.pdp.online.repository.ColumnConstants.*;
import static uz.pdp.online.repository.ColumnConstants.PRIORITY;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE_SQL = """
            INSERT INTO todos (title, priority, created_at, user_id)
            VALUES (:title, :priority, :created_at, :user_id)
            """;

    private static final String FIND_BY_USER_ID_SQL = """
            SELECT * FROM todos WHERE user_id = :user_id ORDER BY created_at DESC
            """;

    private static final String FIND_BY_ID_AND_USER_ID_SQL = """
            SELECT * FROM todos WHERE id = :id AND user_id = :user_id
            """;

    private static final String UPDATE_SQL = """
            UPDATE todos SET title = :title, priority = :priority WHERE id = :id AND user_id = :user_id
            """;

    private static final String DELETE_SQL = """
            DELETE FROM todos WHERE id = :id AND user_id = :user_id
            """;

    public void save(Todo todo) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TITLE, todo.getTitle());
        parameters.put(PRIORITY, todo.getPriority());
        parameters.put(CREATED_AT, todo.getCreatedAt());
        parameters.put(USER_ID, todo.getUserId());

        namedParameterJdbcTemplate.update(SAVE_SQL, parameters);
    }

    public List<Todo> findByUserId(Long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_ID, userId);

        return namedParameterJdbcTemplate.query(FIND_BY_USER_ID_SQL, parameters, new TodoRowMapper());
    }

    public Todo findByIdAndUserId(Long id, Long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ID, id);
        parameters.put(USER_ID, userId);

        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_AND_USER_ID_SQL, parameters, new TodoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Todo todo) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TITLE, todo.getTitle());
        parameters.put(PRIORITY, todo.getPriority());
        parameters.put(ID, todo.getId());
        parameters.put(USER_ID, todo.getUserId());

        namedParameterJdbcTemplate.update(UPDATE_SQL, parameters);
    }

    public void delete(Long id, Long userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ID, id);
        parameters.put(USER_ID, userId);

        namedParameterJdbcTemplate.update(DELETE_SQL, parameters);
    }
}
