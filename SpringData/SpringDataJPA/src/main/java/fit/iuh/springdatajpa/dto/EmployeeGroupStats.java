package fit.iuh.springdatajpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeGroupStats {
    private Integer id;
    private String name;
    private Long employeeCount;
    private Double averageSalary;
    private Double totalSalary;
    private Double minSalary;
    private Double maxSalary;

    public EmployeeGroupStats(Integer id, String name, Long employeeCount, Double averageSalary, Double totalSalary, Double minSalary, Double maxSalary) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.totalSalary = totalSalary == null ? 0.0 : totalSalary;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }
}
