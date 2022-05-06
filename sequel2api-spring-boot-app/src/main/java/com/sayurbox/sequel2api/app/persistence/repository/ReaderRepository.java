package com.sayurbox.sequel2api.app.persistence.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReaderRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReaderRepository.class);

    @Autowired
    @Qualifier("sqlJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<String, Object> get(String sql, MapSqlParameterSource arguments) {
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, arguments, new ColumnMapRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("get data not found {}", e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> list(String sql, MapSqlParameterSource arguments) {
        return namedParameterJdbcTemplate.query(sql, arguments, new ColumnMapRowMapper());
    }

}
