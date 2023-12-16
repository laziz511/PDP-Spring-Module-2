package uz.pdp.online.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    public void save(Todo todo) {
        simpleJdbcInsert.withTableName("todos").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", todo.getTitle());
        parameters.put("priority", todo.getPriority());
        parameters.put("created_at", todo.getCreatedAt());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        todo.setId(newId.intValue());
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM todos ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new TodoRowMapper());
    }

    public Todo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TodoRowMapper());
    }

    public void update(Todo todo) {
        String sql = "UPDATE todos SET title = ?, priority = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority(), todo.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
