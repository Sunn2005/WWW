package fit.iuh.jdbc.repo;

import fit.iuh.jdbc.dto.DanhMucThongKe;
import fit.iuh.jdbc.dto.TinTucKemTenDMDto;
import fit.iuh.jdbc.enitity.TinTuc;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TinTucRepo extends CrudRepository<TinTuc, Integer> {
    List<TinTuc> findTinTucsByTieuDeContainingIgnoreCase(String tieuDe);

    List<TinTuc> findTinTucsByMaDmOrderByNgayDangDesc(Integer maDm);

    @Query("""
    SELECT tt.maTT AS ma_tt,
           tt.tieuDe AS tieu_de,
           tt.noiDungTT AS noi_dung_tt,
           tt.lienKet AS lien_ket,
           tt.ngayDang AS ngay_dang,
           dm.tenDanhMuc AS ten_dm
    FROM TinTuc tt
    INNER JOIN DanhMuc dm ON tt.maDm = dm.maDm
    """)
    List<TinTucKemTenDMDto> findTinTucKemTenDM();

    @Query("""
    SELECT d.tenDanhMuc AS ten_danh_muc, COUNT(t.maTT) AS so_luong
    FROM DANHMUC d
    LEFT JOIN TINTUC t ON t.maDM = d.maDM
    GROUP BY d.maDM, d.tenDanhMuc
    """)
    List<DanhMucThongKe> staticsDanhMucByTinTuc();

    @Query("SELECT d.maDM AS ma_dm, d.tenDanhMuc AS ten_danh_muc, COUNT(t.maTT) AS so_luong " +
            "FROM DANHMUC d LEFT JOIN TINTUC t " +
            "  ON t.maDM = d.maDM " +
            " AND MONTH(t.ngayDang) = :thang " +
            " AND YEAR(t.ngayDang) = :nam " +
            "GROUP BY d.maDM, d.tenDanhMuc")
    List<DanhMucThongKe> thongKeTheoThang(@Param("thang") int thang,
                                          @Param("nam") int nam);
}
