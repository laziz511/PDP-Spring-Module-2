-- DDL for todos
CREATE TABLE IF NOT EXISTS todos
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    priority   VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    user_id    BIGINT REFERENCES auth_user (id)
);


-- DDL for AuthPermission
CREATE TABLE IF NOT EXISTS auth_permission
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- DDL for AuthRole
CREATE TABLE IF NOT EXISTS auth_role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Junction table for many-to-many relationship between auth_permission and auth_role
CREATE TABLE IF NOT EXISTS role_permission
(
    role_id       BIGINT REFERENCES auth_role (id),
    permission_id BIGINT REFERENCES auth_permission (id),
    PRIMARY KEY (role_id, permission_id)
);

-- DDL for AuthUser
CREATE TABLE IF NOT EXISTS auth_user
(
    id                 SERIAL PRIMARY KEY,
    username           VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    blocked            BOOLEAN      NOT NULL,
    profile_photo_path VARCHAR(255)
);


-- Many-to-Many relationship table between auth_user and auth_role
CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT REFERENCES auth_user (id),
    role_id BIGINT REFERENCES auth_role (id),
    PRIMARY KEY (user_id, role_id)
);


insert into user_role(user_id, role_id
