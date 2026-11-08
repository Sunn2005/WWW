package fit.iuh.bai06_gk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "TINTUC")
public class TinTuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATT")
    private Integer id;

    @Column(name = "TIEUDE")
    private String tieuDe;

    @Column(name = "NOIDUNGTT")
    private String ndTT;

    @Column(name = "LIENKET")
    private String lienKet;

     @ManyToOne
     @JoinColumn(name = "MADM")
     @ToString.Exclude
    private DanhMuc danhMuc;

}
