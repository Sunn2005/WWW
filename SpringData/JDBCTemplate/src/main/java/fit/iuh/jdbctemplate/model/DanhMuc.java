package fit.iuh.jdbctemplate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhMuc {
    private Integer maDM;
    private String tenDanhMuc;
    private String nguoiQuanLy;
    private String ghiChu;
}
