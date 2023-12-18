package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(Todo todo) {
        String sql = "INSERT INTO todos (title, priority, created_at, user_id) " +
                "VALUES (:title, :priority, :created_at, :user_id)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority());
        parameters.put("created_at", todo.getCreatedAt());
        parameters.put("user_id", todo.getUserId());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public List<Todo> findByUserId(Long userId) {
        String sql = "SELECT * FROM todos WHERE user_id = :user_id ORDER BY created_at DESC";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, new TodoRowMapper());
    }

    public Todo findByIdAndUserId(Long id, Long userId) {
        String sql = "SELECT * FROM todos WHERE id = :id AND user_id = :user_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("user_id", userId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new TodoRowMapper());
    }

    public void update(Todo todo) {
        String sql = "UPDATE todos SET title = :title, priority = :priority WHERE id = :id AND user_id = :user_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority());
        parameters.put("id", todo.getId());
        parameters.put("user_id", todo.getUserId());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void delete(Long id, Long userId) {
        String sql = "DELETE FROM todos WHERE id = :id AND user_id = :user_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("user_id", userId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
