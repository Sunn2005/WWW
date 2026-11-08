package fit.iuh.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeSearchCriteria {
    private Integer id;
    private String name;
    private Integer minAge;
    private Integer maxAge;
    private Double minSalary;
    private Integer positionId;

    public boolean hasAny() {
        return id != null
            || (name != null && !name.isBlank())
            || minAge != null || maxAge != null
            || minSalary != null || positionId != null;
    }
}
