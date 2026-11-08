package fit.iuh.jdbc.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table("TINTUC")
public class TinTuc {
    @Id
    @Column("maTT")
    private Integer maTT;
    @Column("tieuDe")
    private String tieuDe;
    @Column("noiDungTT")
    private String noiDungTT;
    @Column("lienKet")
    private String lienKet;
    @Column("ngayDang")
    private LocalDate ngayDang;
    @Column("maDM")
    private Integer maDm;
}
