package fit.iuh.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {
    private Integer id;
    @NotBlank(message = "Tên không được để trống")
    private String name;
    private String role;
    @Min(value = 0, message = "Tuổi phải >= 0")
    private Integer age;
    @PositiveOrZero(message = "Lương phải >= 0")
    private Double salary;
    private Integer departmentId;
    private Integer positionId;
}
