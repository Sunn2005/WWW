package fit.iuh.jdbc.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table("DANHMUC")
public class DanhMuc {
    @Id
    @Column("maDM")
    private Integer maDm;
    @Column("tenDanhMuc")
    private String tenDm;
    @Column("nguoiQuanLy")
    private String nguoiQuanLy;
    @Column("ghiChu")
    private String ghiChu;

}
