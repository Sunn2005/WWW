package fit.iuh.midterm4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LOAITHUOC")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class LoaiThuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MALOAI")
    private Long maLoai;

    @Column(name = "TENLOAI")
    private String tenLoai;
}
