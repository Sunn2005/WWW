package fit.iuh.springdatajdbc.repo;

import fit.iuh.springdatajdbc.dto.TinTucVoiTen;
import fit.iuh.springdatajdbc.enitity.TinTuc;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TinTucRepository extends CrudRepository<TinTuc, Integer> {
    // Bài 1 cũ: tìm theo tiêu đề chứa từ khóa -> derived là đủ
    List<TinTuc> findByTieuDeContainingIgnoreCase(String tuKhoa);

    // Bài 2 cũ: tin theo danh mục, mới nhất trước -> viết @Query cho chắc
    // vì tên field là maDM, tách tên derived rất dễ nhầm
    @Query("SELECT * FROM TINTUC WHERE maDM = :maDM ORDER BY maTT DESC")
    List<TinTuc> findByMaDM(@Param("maDM") Integer maDM);

    // Bài 3 cũ: tin kèm tên danh mục -> JOIN, trả về interface projection
    @Query("SELECT t.maTT AS ma_tt, t.tieuDe AS tieu_de, t.noiDungTT AS noi_dung_tt, " +
           "t.lienKet AS lien_ket, d.tenDanhMuc AS ten_danh_muc " +
           "FROM TINTUC t INNER JOIN DANHMUC d ON t.maDM = d.maDM ORDER BY t.maTT")
    List<TinTucVoiTen> findAllVoiTenDanhMuc();

    // Bài 4 cũ: JOIN + lọc theo tên danh mục
    @Query("SELECT t.maTT AS maTT, t.tieuDe AS tieuDe, t.noiDungTT AS noiDungTT, " +
           "t.lienKet AS lienKet, d.tenDanhMuc AS tenDanhMuc " +
           "FROM TINTUC t INNER JOIN DANHMUC d ON t.maDM = d.maDM " +
           "WHERE d.tenDanhMuc = :ten ORDER BY t.maTT")
    List<TinTucVoiTen> findByTenDanhMuc(@Param("ten") String ten);
}

