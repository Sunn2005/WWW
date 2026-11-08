package fit.iuh.jdbctemplate.dao;


import fit.iuh.jdbctemplate.dto.ThongKeDanhMuc;
import fit.iuh.jdbctemplate.dto.TinTucVoiTen;
import fit.iuh.jdbctemplate.model.TinTuc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TinTucDao {
    private final JdbcTemplate jdbc;
    public TinTucDao(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final String SQL_BASE =
            "SELECT maTT, tieuDe, noiDungTT, lienKet, maDM, ngayDang FROM TINTUC ";
    private static final String SQL_JOIN =
            "SELECT t.maTT, t.tieuDe, t.noiDungTT, t.lienKet, t.ngayDang, d.tenDanhMuc " +
                    "FROM TINTUC t INNER JOIN DANHMUC d ON t.maDM = d.maDM ";

    private static final RowMapper<TinTuc> MAPPER =
            BeanPropertyRowMapper.newInstance(TinTuc.class);
    private static final RowMapper<TinTucVoiTen> JOIN_MAPPER =
            BeanPropertyRowMapper.newInstance(TinTucVoiTen.class);

    public List<TinTuc> findAll() {
        return jdbc.query(SQL_BASE + "ORDER BY maTT", MAPPER);
    }

    public TinTuc findById(Integer id) {
        return jdbc.query(SQL_BASE + "WHERE maTT = ?", MAPPER, id)
                .stream().findFirst().orElse(null);
    }

    public List<TinTuc> findByTieuDeContaining(String tuKhoa) {
        return jdbc.query(SQL_BASE + "WHERE tieuDe LIKE ?", MAPPER, "%" + tuKhoa + "%");
    }

    public List<TinTuc> findByMaDM(Integer maDM) {
        return jdbc.query(SQL_BASE + "WHERE maDM = ? ORDER BY maTT DESC", MAPPER, maDM);
    }

    public List<TinTucVoiTen> findKemTen() {
        return jdbc.query(SQL_JOIN + "ORDER BY t.maTT", JOIN_MAPPER);
    }

    public List<TinTucVoiTen> findByTenDanhMuc(String ten) {
        return jdbc.query(SQL_JOIN + "WHERE d.tenDanhMuc = ? ORDER BY t.maTT", JOIN_MAPPER, ten);
    }

    //http://localhost:8080/api/tintuc/trong-khoang-ngay?tu=2025-01-18T00:00:00&den=2026-01-18T00:00:00
    public List<TinTuc> findTrongKhoangNgay(LocalDateTime tu, LocalDateTime den) {
        return jdbc.query(SQL_BASE + "WHERE ngayDang BETWEEN ? AND ? ORDER BY ngayDang", MAPPER, tu, den);
    }

    public List<TinTuc> findMoiNhat(int limit) {
        return jdbc.query(SQL_BASE + "ORDER BY ngayDang DESC LIMIT ?", MAPPER, limit);
    }

    public TinTuc insert(TinTuc t) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO TINTUC(tieuDe, noiDungTT, lienKet, maDM, ngayDang) VALUES(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getTieuDe());
            ps.setString(2, t.getNoiDungTT());
            ps.setString(3, t.getLienKet());
            ps.setObject(4, t.getMaDM());
            ps.setObject(5, t.getNgayDang());
            return ps;
        }, kh);
        t.setMaTT(kh.getKey().intValue());
        return t;
    }

    public boolean update(Integer id, TinTuc t) {
        return jdbc.update("UPDATE TINTUC SET tieuDe = ?, noiDungTT = ?, lienKet = ?, maDM = ?, ngayDang = ? WHERE maTT = ?",
                t.getTieuDe(), t.getNoiDungTT(), t.getLienKet(), t.getMaDM(), t.getNgayDang(), id) > 0;
    }

    public boolean delete(Integer id) {
        return jdbc.update("DELETE FROM TINTUC WHERE maTT = ?", id) > 0;
    }
}
