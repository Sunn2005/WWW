package fit.iuh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("employees")
public class Employee {
    @Id
    private Integer id;
    private String name;
    private String role;
    private Integer age;
    private Double salary;
    @Column("department_id")
    private Integer departmentId;
    @Column("position_id")
    private Integer positionId;
    // Không phải cột DB, chỉ để hiển thị
    @Transient
    private String departmentName;
    @Transient
    private String positionTitle;
}
