CREATE TABLE auth_user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(10)
);


CREATE TABLE cities
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE subscriptions
(
    id      SERIAL PRIMARY KEY,
    user_id BIGINT,
    city_id BIGINT
);

CREATE TABLE weather_data
(
    id          SERIAL PRIMARY KEY,
    city_id     BIGINT           NOT NULL,
    day         DATE             NOT NULL,
    temperature DOUBLE PRECISION NOT NULL,
    humidity    DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities (id) ON DELETE CASCADE
);
