package fit.iuh.repo;

import fit.iuh.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepository extends MongoRepository<Position, Integer> {
}
