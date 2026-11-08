package fit.iuh.springdatajdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinTucVoiTen {
    private Integer maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private String tenDanhMuc;
}
