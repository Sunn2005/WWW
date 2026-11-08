package fit.iuh.springdatajpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HireYearStats {
    private Integer year;
    private Long employeeCount;
    private Double totalSalary;

    public HireYearStats(Integer year, Long employeeCount, Double totalSalary) {
        this.year = year;
        this.employeeCount = employeeCount;
        this.totalSalary = totalSalary == null ? 0.0 : totalSalary;
    }
}
