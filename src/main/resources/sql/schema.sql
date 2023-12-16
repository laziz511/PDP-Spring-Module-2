CREATE TABLE todos
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    priority   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL
);



CREATE OR REPLACE FUNCTION find_all_todos()
    RETURNS TABLE
            (
                id         INT,
                title      VARCHAR(255),
                priority   VARCHAR(255),
                created_at TIMESTAMP
            )
AS
$$
BEGIN
    RETURN QUERY SELECT * FROM todos ORDER BY created_at DESC;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION find_todo_by_id(todo_id INT)
    RETURNS TABLE
            (
                id         INT,
                title      VARCHAR(255),
                priority   VARCHAR(255),
                created_at TIMESTAMP
            )
AS
$$
BEGIN
    RETURN QUERY SELECT * FROM todos WHERE id = todo_id;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION create_todo(
    title VARCHAR(255),
    priority VARCHAR(255),
    created_at TIMESTAMP,
    OUT new_id INT
)
AS
$$
BEGIN
    INSERT INTO todos (title, priority, created_at)
    VALUES (title, priority, created_at)
    RETURNING id INTO new_id;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION update_todo(
    todo_id INT,
    new_title VARCHAR(255),
    new_priority VARCHAR(255)
)
AS
$$
BEGIN
    UPDATE todos
    SET title    = new_title,
        priority = new_priority
    WHERE id = todo_id;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION delete_todo(todo_id INT)
AS
$$
BEGIN
    DELETE FROM todos WHERE id = todo_id;
END;
$$ LANGUAGE plpgsql;
