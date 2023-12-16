package uz.pdp.online.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.util.List;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM todos ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new TodoRowMapper());
    }

    public Todo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TodoRowMapper());
    }

    public void save(Todo todo) {
        String sql = "INSERT INTO todos (title, priority, created_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority(), todo.getCreatedAt());
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
