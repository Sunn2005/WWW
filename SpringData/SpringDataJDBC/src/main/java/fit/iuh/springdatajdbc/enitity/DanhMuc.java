package fit.iuh.springdatajdbc.enitity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "DANHMUC")
public class DanhMuc {
    @Id
    @Column("MADM")
    private Integer maDM;
    @Column("TENDANHMUC")
    private String tenDM;
    @Column("NGUOIQUANLY")
    private String nguoiQuanLy;
    @Column("GHICHU")
    private String ghiChu;
}
