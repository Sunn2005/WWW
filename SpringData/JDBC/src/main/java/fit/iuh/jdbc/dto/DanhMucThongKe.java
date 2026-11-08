package fit.iuh.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DanhMucThongKe {
    private String tenDanhMuc;
    private Long soLuong;
}
