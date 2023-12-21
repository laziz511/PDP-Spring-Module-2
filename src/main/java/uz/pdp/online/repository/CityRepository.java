package uz.pdp.online.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uz.pdp.online.exception.CityNotFoundException;
import uz.pdp.online.mapper.CityRowMapper;
import uz.pdp.online.model.City;

import java.util.List;
import java.util.Optional;

import static uz.pdp.online.constants.ColumnConstants.ID;
import static uz.pdp.online.constants.ColumnConstants.NAME;

@Repository
@RequiredArgsConstructor
public class CityRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE_SQL = "INSERT INTO cities (name) VALUES (:name)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM cities WHERE id = :id";
    private static final String FIND_ALL_SQL = "SELECT * FROM cities";
    private static final String UPDATE_BY_ID_SQL = "UPDATE cities SET name = :name WHERE id = :id";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM cities WHERE id = :id";

    public void save(City city) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME, city.getName());
        namedParameterJdbcTemplate.update(SAVE_SQL, parameters);
    }

    public Optional<City> findById(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, paramSource, new CityRowMapper()));
    }

    public List<City> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_SQL, new CityRowMapper());
    }

    public void update(City city) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, city.getId()).addValue(NAME, city.getName());

        try {
            namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException("City not found with id: '%s'".formatted(city.getId()), "/admin/city/show");
        }
    }

    public void delete(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(ID, id);
        try {
            namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL, paramSource);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException("City not found with id: '%s'".formatted(id), "/admin/city/show");
        }
    }
}
