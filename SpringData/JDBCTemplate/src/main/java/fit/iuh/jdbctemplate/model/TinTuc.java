package fit.iuh.jdbctemplate.model;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class TinTuc {
    private Integer maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private Integer maDM;
    private LocalDateTime ngayDang;
}
