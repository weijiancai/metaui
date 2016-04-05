package com.metaui.db;

import com.metaui.core.datasource.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装Spring JdbcTemplate，进行扩展
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/6.
 */
public class MyJdbcTemplate {
    @Autowired
    private JdbcTemplate template;

    public List<DataMap> queryForList(String sql) {
        return queryForList(sql, new Object[]{});
    }

    public List<DataMap> queryForList(String sql, Object... args) throws DataAccessException {
        return template.query(sql, args, new MyRowMapper());
    }

    public <T> T queryForObject(String sql, Class<T> requiredType) {
        return template.queryForObject(sql, requiredType);
    }

    public int update(String sql, Object... args) throws DataAccessException {
        return template.update(sql, args);
    }

    private class MyRowMapper implements RowMapper<DataMap> {
        @Override
        public DataMap mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataMap map = new DataMap();

            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
            }

            return map;
        }
    }

}
