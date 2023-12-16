package uz.pdp.online.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import uz.pdp.online.mapper.TodoRowMapper;
import uz.pdp.online.model.Todo;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepository {

    private final SimpleJdbcCall findAllCall;
    private final SimpleJdbcCall findByIdCall;
    private final SimpleJdbcCall saveCall;
    private final SimpleJdbcCall updateCall;
    private final SimpleJdbcCall deleteCall;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.findAllCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("find_all_todos")
                .returningResultSet("todos", new TodoRowMapper());

        this.findByIdCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("find_todo_by_id")
                .declareParameters(new SqlParameter("todo_id", Types.INTEGER))
                .returningResultSet("todo", new TodoRowMapper());

        this.saveCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("create_todo")
                .declareParameters(new SqlParameter("title", Types.VARCHAR),
                        new SqlParameter("priority", Types.VARCHAR),
                        new SqlParameter("created_at", Types.TIMESTAMP),
                        new SqlParameter("id", Types.INTEGER));

        this.updateCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("update_todo")
                .declareParameters(new SqlParameter("todo_id", Types.INTEGER),
                        new SqlParameter("title", Types.VARCHAR),
                        new SqlParameter("priority", Types.VARCHAR));

        this.deleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("delete_todo")
                .declareParameters(new SqlParameter("todo_id", Types.INTEGER));
    }

    public List<Todo> findAll() {
        Map<String, Object> result = findAllCall.execute();
        return (List<Todo>) result.get("todos");
    }

    public Todo findById(int id) {
        Map<String, Object> inParams = Map.of("todo_id", id);
        Map<String, Object> result = findByIdCall.execute(inParams);
        List<Todo> todos = (List<Todo>) result.get("todo");
        return todos.isEmpty() ? null : todos.get(0);
    }

    public void save(Todo todo) {
        Map<String, Object> inParams = Map.of(
                "title", todo.getTitle(),
                "priority", todo.getPriority(),
                "created_at", todo.getCreatedAt(),
                "id", null);

        Map<String, Object> result = saveCall.execute(inParams);
        todo.setId((Integer) result.get("id"));
    }

    public void update(Todo todo) {
        Map<String, Object> inParams = Map.of(
                "todo_id", todo.getId(),
                "title", todo.getTitle(),
                "priority", todo.getPriority());

        updateCall.execute(inParams);
    }

    public void delete(int id) {
        Map<String, Object> inParams = Map.of("todo_id", id);
        deleteCall.execute(inParams);
    }
}
