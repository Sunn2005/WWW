package fit.iuh.bai5_jdbc.dto;

import java.math.BigDecimal;

public class DepartmentSalary {
    private int departmentId;
    private String departmentName;
    private BigDecimal averageSalary;

    public DepartmentSalary() {}

    public DepartmentSalary(int departmentId, String departmentName, BigDecimal averageSalary) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public BigDecimal getAverageSalary() { return averageSalary; }
    public void setAverageSalary(BigDecimal averageSalary) { this.averageSalary = averageSalary; }
}
