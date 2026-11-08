package fit.iuh.jdbc.repo;

import fit.iuh.jdbc.enitity.DanhMuc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public interface DanhMucRepo extends CrudRepository<DanhMuc, Integer> {



}
