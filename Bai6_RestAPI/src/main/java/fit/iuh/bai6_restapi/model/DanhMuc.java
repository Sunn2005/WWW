package fit.iuh.bai6_restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "MADD")
    private Long id;
    @Column(name = "TENDANHMUC")
    private String tenDM;
    @Column(name = "NGUOIQUANLY")
    private String nguoiQuanLy;
    @Column(name = "GHICHU")
    private String ghiChu;

    @OneToMany(mappedBy = "danhMuc")
    @JsonIgnore
    @ToString.Exclude
    private List<TinTuc> tinTucs;
}
