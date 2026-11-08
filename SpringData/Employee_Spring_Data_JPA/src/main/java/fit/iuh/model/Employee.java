package fit.iuh.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String role;
    private Integer age;
    private Double salary;
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(name = "position_id")
    private Integer positionId;
    // Không phải cột DB, chỉ để hiển thị
    @Transient
    private String departmentName;
    @Transient
    private String positionTitle;
}
