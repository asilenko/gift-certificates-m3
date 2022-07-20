package com.epam.esm.rowmappers;

import com.epam.esm.domain.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides method to map result set from database to Tag.
 *
 * @see Tag
 */
public class TagRowMapper implements RowMapper<Tag> {

    /**
     * Maps result set from database to Tag.
     *
     * @param rs the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return Tag
     * @throws SQLException
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Tag tag = new Tag();
        tag.setId(rs.getLong("ID"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
