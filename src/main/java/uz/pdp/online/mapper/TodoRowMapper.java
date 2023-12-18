package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TodoRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String priority = resultSet.getString("priority");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        Long userId = resultSet.getLong("user_id");

        return new Todo(id, title, priority, createdAt, userId);
    }
}
