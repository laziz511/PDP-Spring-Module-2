CREATE TABLE todos
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    priority   VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL
);
