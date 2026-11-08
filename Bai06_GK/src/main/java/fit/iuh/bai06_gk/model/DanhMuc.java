package fit.iuh.bai06_gk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "DANHMUC")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MADM")
    private Integer id;

    @Column(name = "TENDANHMUC")
    private String tenDM;

    @Column(name = "NGUOIQUANLY")
    private String nguoiQuanLy;

    @Column(name = "GHICHU")
    private String ghiChu;

    @OneToMany(mappedBy = "danhMuc")
    @ToString.Exclude
    private List<TinTuc> tinTucs;
}
