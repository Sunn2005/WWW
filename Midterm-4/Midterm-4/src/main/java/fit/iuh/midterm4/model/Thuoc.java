package fit.iuh.midterm4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "THUOC")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Thuoc {

    @Column(name = "TENTHUOC")
    private String tenThuoc;

    @Column(name = "GIA")
    private String gia;

    @Column(name = "NAMSX")
    private String namSX;

    @ManyToOne
    @JoinColumn(name = "MALOAI")
    private LoaiThuoc maLoai;
}
