package fit.iuh.jdbctemplate.dao;

import fit.iuh.jdbctemplate.dto.ThongKeDanhMuc;
import fit.iuh.jdbctemplate.model.DanhMuc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DanhMucDao {
    private final JdbcTemplate jdbc;

    public DanhMucDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_BASE =
            "SELECT maDM, tenDanhMuc, nguoiQuanLy, ghiChu FROM DANHMUC ";

    private static final String SQL_THONGKE =
            "SELECT d.maDM AS maDM, d.tenDanhMuc AS tenDanhMuc, COUNT(t.maTT) AS soLuong " +
                    "FROM DANHMUC d LEFT JOIN TINTUC t ON t.maDM = d.maDM ";

    private static final String SQL_THONGKE_THANG =
            "SELECT d.maDM AS maDM, d.tenDanhMuc AS tenDanhMuc, COUNT(t.maTT) AS soLuong " +
                    "FROM DANHMUC d LEFT JOIN TINTUC t " +
                    "  ON t.maDM = d.maDM " +
                    " AND MONTH(t.ngayDang) = ? " +
                    " AND YEAR(t.ngayDang) = ? ";

    private static final RowMapper<DanhMuc> rowMapper =
            BeanPropertyRowMapper.newInstance(DanhMuc.class);

    private static final RowMapper<ThongKeDanhMuc> THONGKE_MAPPER =
            BeanPropertyRowMapper.newInstance(ThongKeDanhMuc.class);

    public List<DanhMuc> findAll() {
        return jdbc.query(SQL_BASE + "ORDER BY maDM", rowMapper);
    }

    public DanhMuc findById(Integer id) {
        return jdbc.query(SQL_BASE + "WHERE maDM = ?", rowMapper, id)
                .stream().findFirst().orElse(null);
    }

    public boolean existsById(Integer id) {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM DANHMUC WHERE maDM = ?", Integer.class, id);
        return c != null && c > 0;
    }

    public DanhMuc insert(DanhMuc d) {
        String sql = "INSERT INTO DANHMUC(tenDanhMuc, nguoiQuanLy, ghiChu) VALUES(?, ?, ?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getTenDanhMuc());
            ps.setString(2, d.getNguoiQuanLy());
            ps.setString(3, d.getGhiChu());
            return ps;
        }, kh);
        d.setMaDM(kh.getKey().intValue());
        return d;
    }

    public boolean update(Integer id, DanhMuc d) {
        return jdbc.update("UPDATE DANHMUC SET tenDanhMuc = ?, nguoiQuanLy = ?, ghiChu = ? WHERE maDM = ?",
                d.getTenDanhMuc(), d.getNguoiQuanLy(), d.getGhiChu(), id) > 0;
    }

    public boolean delete(Integer id) {
        return jdbc.update("DELETE FROM DANHMUC WHERE maDM = ?", id) > 0;
    }

    public List<ThongKeDanhMuc> thongKe() {
        return jdbc.query(SQL_THONGKE + "GROUP BY d.maDM, d.tenDanhMuc ORDER BY d.maDM", THONGKE_MAPPER);
    }

    //http://localhost:8080/api/danhmuc/thong-ke-thang?thang=5&nam=2025
    public List<ThongKeDanhMuc> thongKeThang(int thang, int nam) {
        return jdbc.query(SQL_THONGKE_THANG + "GROUP BY d.maDM, d.tenDanhMuc ORDER BY d.maDM",
                THONGKE_MAPPER, thang, nam);
    }

    public List<DanhMuc> findEmpty() {
        return jdbc.query(SQL_BASE + "WHERE maDM NOT IN (SELECT DISTINCT maDM FROM TINTUC WHERE maDM IS NOT NULL) ORDER BY maDM",
                rowMapper);
    }
}
