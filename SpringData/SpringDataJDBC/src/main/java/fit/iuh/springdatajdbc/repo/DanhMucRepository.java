package fit.iuh.springdatajdbc.repo;

import fit.iuh.springdatajdbc.dto.ThongKeDanhMuc;
import fit.iuh.springdatajdbc.enitity.DanhMuc;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DanhMucRepository extends CrudRepository<DanhMuc, Integer> {
    List<DanhMuc> findByTenDMContainingIgnoreCase(String ten);

    // Bài 5 cũ: LEFT JOIN để danh mục 0 tin vẫn hiện, COUNT(t.maTT) chứ không COUNT(*)
    @Query("SELECT d.maDM AS maDM, d.tenDanhMuc AS tenDanhMuc, COUNT(t.maTT) AS soLuong " +
           "FROM DANHMUC d LEFT JOIN TINTUC t ON t.maDM = d.maDM " +
           "GROUP BY d.maDM, d.tenDanhMuc")
    List<ThongKeDanhMuc> demTinTheoDanhMuc();

    // Bài 6 cũ: HAVING, không dùng WHERE với COUNT
    @Query("SELECT d.maDM AS maDM, d.tenDanhMuc AS tenDanhMuc, COUNT(t.maTT) AS soLuong " +
           "FROM DANHMUC d LEFT JOIN TINTUC t ON t.maDM = d.maDM " +
           "GROUP BY d.maDM, d.tenDanhMuc HAVING COUNT(t.maTT) >= :n")
    List<ThongKeDanhMuc> danhMucTuNTinTroLen(@Param("n") int n);

    // Bài 7 cũ: danh mục nhiều tin nhất, subquery phải có alias AS x
    @Query("SELECT d.maDM AS maDM, d.tenDanhMuc AS tenDanhMuc, COUNT(t.maTT) AS soLuong " +
           "FROM DANHMUC d LEFT JOIN TINTUC t ON t.maDM = d.maDM " +
           "GROUP BY d.maDM, d.tenDanhMuc " +
           "HAVING COUNT(t.maTT) = (SELECT MAX(cnt) FROM (SELECT COUNT(*) AS cnt FROM TINTUC GROUP BY maDM) AS x)")
    List<ThongKeDanhMuc> danhMucNhieuTinNhat();

    // Phòng trống, giống bài departments-empty bên Employee
    @Query("SELECT * FROM DANHMUC WHERE maDM NOT IN (SELECT DISTINCT maDM FROM TINTUC WHERE maDM IS NOT NULL)")
    List<DanhMuc> findDanhMucTrong();
}
