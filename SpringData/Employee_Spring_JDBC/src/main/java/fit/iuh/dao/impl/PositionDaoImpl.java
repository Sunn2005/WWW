package fit.iuh.dao.impl;


import java.util.List;

import fit.iuh.dao.PositionDao;
import fit.iuh.model.Position;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDaoImpl implements PositionDao {

    private final JdbcTemplate jdbcTemplate;

    public PositionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Position> findAll() {
        return jdbcTemplate.query("SELECT id, title FROM positions ORDER BY id",
            (rs, rowNum) -> new Position(rs.getInt("id"), rs.getString("title")));
    }
}
