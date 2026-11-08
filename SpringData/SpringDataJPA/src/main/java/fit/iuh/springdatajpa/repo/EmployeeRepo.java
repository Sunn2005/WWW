package fit.iuh.springdatajpa.repo;

import fit.iuh.springdatajpa.model.Employee;
import fit.iuh.springdatajpa.dto.EmployeeSummary;
import fit.iuh.springdatajpa.dto.EmployeeGroupStats;
import fit.iuh.springdatajpa.dto.HireMonthStats;
import fit.iuh.springdatajpa.dto.HireYearStats;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    // Tìm nhân viên có tên chứa từ khóa, không phân biệt chữ hoa/chữ thường.
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Tìm nhân viên có tuổi trong khoảng min đến max, bao gồm cả hai đầu.
    List<Employee> findByAgeBetween(Integer min, Integer max);

    // Tìm nhân viên có lương lớn hơn mức truyền vào, không bao gồm mức bằng nhau.
    List<Employee> findBySalaryGreaterThan(Double salary);

    // Tìm tất cả nhân viên thuộc chức vụ có ID được truyền vào.
    List<Employee> findByPositionId(Integer positionId);

    // Tìm nhân viên đồng thời thuộc phòng ban và chức vụ được chỉ định.
    List<Employee> findByDepartmentIdAndPositionId(Integer departmentId, Integer positionId);

    // Tìm nhân viên trong phòng ban, sắp xếp lương giảm dần.
    // Pageable giới hạn số kết quả, ví dụ PageRequest.of(0, 5) lấy 5 người lương cao nhất.
    List<Employee> findByDepartmentIdOrderBySalaryDesc(Integer departmentId, Pageable pageable);

    // Thống kê theo phòng ban có nhân viên; mỗi Object[] lần lượt chứa:
    // ID phòng ban, tên phòng ban, số nhân viên, lương trung bình, tổng lương.
    @Query("SELECT e.department.id, e.department.name, COUNT(e), AVG(e.salary), SUM(e.salary) FROM Employee e GROUP BY e.department.id, e.department.name")
    List<Object[]> statsByDepartment();

    // Thống kê toàn bộ nhân viên: số lượng, lương trung bình, tổng lương, lương nhỏ nhất/lớn nhất.
    // SELECT new tạo DTO từ kết quả JPQL; constructor DTO đổi tổng lương null thành 0.
    @Query("""
            SELECT new fit.iuh.springdatajpa.dto.EmployeeSummary(
                COUNT(e), AVG(e.salary), SUM(e.salary), MIN(e.salary), MAX(e.salary))
            FROM Employee e
            """)
    EmployeeSummary summarizeEmployees();

    // Thống kê số nhân viên và các mức lương theo từng phòng ban, kể cả phòng ban trống.
    // GROUP BY gom nhóm; HAVING chỉ giữ nhóm có ít nhất minEmployees nhân viên.
    // Kết quả sắp xếp theo ID phòng ban tăng dần.
    @Query("""
            SELECT new fit.iuh.springdatajpa.dto.EmployeeGroupStats(
                d.id, d.name, COUNT(e), AVG(e.salary), SUM(e.salary),
                MIN(e.salary), MAX(e.salary))
            FROM Department d LEFT JOIN d.employees e
            GROUP BY d.id, d.name
            HAVING COUNT(e) >= :minEmployees
            ORDER BY d.id
            """)
    List<EmployeeGroupStats> departmentStatistics(@Param("minEmployees") long minEmployees);

    // Thống kê số nhân viên, lương trung bình/tổng/nhỏ nhất/lớn nhất theo từng chức vụ.
    // LEFT JOIN giữ cả chức vụ chưa có nhân viên; sắp xếp theo ID chức vụ tăng dần.
    @Query("""
            SELECT new fit.iuh.springdatajpa.dto.EmployeeGroupStats(
                p.id, p.title, COUNT(e), AVG(e.salary), SUM(e.salary),
                MIN(e.salary), MAX(e.salary))
            FROM Position p LEFT JOIN p.employees e
            GROUP BY p.id, p.title
            ORDER BY p.id
            """)
    List<EmployeeGroupStats> positionStatistics();

    // Thống kê số nhân viên vào làm và tổng lương hiện tại của họ theo từng tháng/năm.
    // Chỉ xét ngày vào làm trong khoảng startDate đến endDate, bao gồm cả hai đầu.
    // Chỉ trả tháng có dữ liệu, sắp xếp theo năm rồi tháng tăng dần.
    @Query("""
            SELECT new fit.iuh.springdatajpa.dto.HireMonthStats(
                YEAR(e.hireDate), MONTH(e.hireDate), COUNT(e), SUM(e.salary))
            FROM Employee e
            WHERE e.hireDate BETWEEN :startDate AND :endDate
            GROUP BY YEAR(e.hireDate), MONTH(e.hireDate)
            ORDER BY YEAR(e.hireDate), MONTH(e.hireDate)
            """)
    List<HireMonthStats> monthlyHireStatistics(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    // Thống kê số nhân viên vào làm và tổng lương hiện tại của họ theo từng năm.
    // Bỏ qua nhân viên chưa có ngày vào làm; chỉ trả năm có dữ liệu, sắp xếp tăng dần.
    @Query("""
            SELECT new fit.iuh.springdatajpa.dto.HireYearStats(
                YEAR(e.hireDate), COUNT(e), SUM(e.salary))
            FROM Employee e
            WHERE e.hireDate IS NOT NULL
            GROUP BY YEAR(e.hireDate)
            ORDER BY YEAR(e.hireDate)
            """)
    List<HireYearStats> yearlyHireStatistics();

    // Tìm nhân viên có ngày vào làm đúng bằng ngày truyền vào, ví dụ 2024-02-29.
    List<Employee> findByHireDate(LocalDate date);

    // Tìm nhân viên vào làm trước ngày truyền vào, không bao gồm chính ngày đó.
    List<Employee> findByHireDateBefore(LocalDate date);

    // Tìm nhân viên vào làm sau ngày truyền vào, không bao gồm chính ngày đó.
    List<Employee> findByHireDateAfter(LocalDate date);

    // Tìm nhân viên vào làm trong khoảng startDate đến endDate, bao gồm cả hai đầu.
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);

    // Tìm nhân viên vào làm đúng tháng và năm được chỉ định, ví dụ tháng 2 năm 2024.
    @Query("SELECT e FROM Employee e WHERE MONTH(e.hireDate) = :month AND YEAR(e.hireDate) = :year")
    List<Employee> findByHireMonthAndYear(@Param("month") int month, @Param("year") int year);
}
