package fit.iuh.jdbctemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinTucVoiTen {
    private Integer maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private LocalDateTime ngayDang;
    private String tenDanhMuc;
}
