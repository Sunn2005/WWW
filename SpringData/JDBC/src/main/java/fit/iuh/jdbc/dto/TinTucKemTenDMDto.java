package fit.iuh.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TinTucKemTenDMDto {
    private Integer maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private LocalDate ngayDang;
    private String tenDm;
}
