package uz.pdp.online.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TodoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Todo todo) {
        String sql = "INSERT INTO todos (title, priority, created_at) VALUES (:title, :priority, :created_at)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority());
        parameters.put("created_at", todo.getCreatedAt());

        namedParameterJdbcTemplate.update(sql, parameters);
    }


    public List<Todo> findAll() {
        String sql = "SELECT * FROM todos ORDER BY created_at DESC";
        return namedParameterJdbcTemplate.query(sql, new TodoRowMapper());
    }

    public Todo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, new TodoRowMapper());
    }

    public void update(Todo todo) {
        String sql = "UPDATE todos SET title = :title, priority = :priority WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority());
        parameters.put("id", todo.getId());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
