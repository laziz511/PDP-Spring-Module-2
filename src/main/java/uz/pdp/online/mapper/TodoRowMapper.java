package uz.pdp.online.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.online.model.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static uz.pdp.online.repository.ColumnConstants.*;

public class TodoRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(ID);
        String title = resultSet.getString(TITLE);
        String priority = resultSet.getString(PRIORITY);
        LocalDateTime createdAt = resultSet.getTimestamp(CREATED_AT).toLocalDateTime();
        Long userId = resultSet.getLong(USER_ID);

        return new Todo(id, title, priority, createdAt, userId);
    }
}
