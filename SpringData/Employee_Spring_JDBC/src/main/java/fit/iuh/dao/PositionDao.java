package fit.iuh.dao;


import fit.iuh.model.Position;

import java.util.List;

public interface PositionDao {
    List<Position> findAll();
}
