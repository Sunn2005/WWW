package fit.iuh.springdatajdbc.enitity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TINTUC")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Column("maDM")
    private Integer maDM;
}
