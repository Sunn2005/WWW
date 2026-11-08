package fit.iuh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    private String role;
    private Integer age;
    private Double salary;
    private Integer departmentId;
    private Integer positionId;
    // Chỉ để hiển thị, không lưu DB
    private String departmentName;
    private String positionTitle;
}