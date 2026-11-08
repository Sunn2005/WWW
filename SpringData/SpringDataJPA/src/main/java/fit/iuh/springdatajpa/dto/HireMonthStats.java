package fit.iuh.springdatajpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HireMonthStats {
    private Integer year;
    private Integer month;
    private Long employeeCount;
    private Double totalSalary;

    public HireMonthStats(Integer year, Integer month, Long employeeCount, Double totalSalary) {
        this.year = year;
        this.month = month;
        this.employeeCount = employeeCount;
        this.totalSalary = totalSalary == null ? 0.0 : totalSalary;
    }
}
