package fit.iuh.jdbctemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeDanhMuc {
    private Integer maDM;
    private String tenDanhMuc;
    private Long soLuong;
}
