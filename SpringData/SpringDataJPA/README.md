# SpringDataJPA — truy vấn ngày vào làm

Luồng xử lý: `Employee` → `EmployeeRepo` → `EmployeeService` → `EmployeeController`.
Controller nhận request; service xử lý nghiệp vụ, kiểm tra tham số và quản lý transaction;
repository truy vấn database. API phòng ban trống đi qua `DepartmentService` → `DepartmentRepo`.

## Field LocalDate

`Employee.hireDate` là ngày vào làm, kiểu `java.time.LocalDate`, ánh xạ vào cột
`employees.hire_date` kiểu SQL `DATE`. Không dùng `@Temporal` cho `LocalDate`.
JSON và query parameter dùng định dạng **`yyyy-MM-dd`**, ví dụ `2024-02-29`.
Field cho phép `null` để tương thích với nhân viên cũ chưa có ngày vào làm;
những bản ghi này không xuất hiện trong kết quả tìm kiếm theo ngày.

Ứng dụng đang cấu hình `spring.jpa.hibernate.ddl-auto=update`, nên Hibernate sẽ
thêm cột khi khởi động với database hiện có. Có thể thêm cột thủ công bằng
`data/add-hire-date.sql`; script này không xóa dữ liệu.

`data/employeedb.sql` đã bổ sung cột và ngày vào làm giả lập. File dump này có
`DELETE FROM`, chỉ import khi muốn nạp lại bộ dữ liệu mẫu.

## API theo ngày tháng năm

Tiền tố: `http://localhost:8080/api/employees`. Tất cả API tìm kiếm bên dưới dùng `GET`
và trả về danh sách nhân viên (không có kết quả thì trả `[]`).

| Đường dẫn và tham số ví dụ | Ý nghĩa | Repository |
| --- | --- | --- |
| `/search/hire-date?date=2024-02-29` | Đúng ngày vào làm | `findByHireDate` |
| `/search/hired-before?date=2024-03-01` | Trước ngày, không gồm ngày được truyền | `findByHireDateBefore` |
| `/search/hired-after?date=2024-02-01` | Sau ngày, không gồm ngày được truyền | `findByHireDateAfter` |
| `/search/hire-date-between?startDate=2024-02-01&endDate=2024-02-29` | Khoảng ngày, gồm cả hai đầu | `findByHireDateBetween` |
| `/search/hire-month?month=2&year=2024` | Tháng 2 năm 2024 | JPQL `MONTH` và `YEAR` |
| `/search/hire-year?year=2024` | Toàn bộ năm 2024 | `findByHireDateBetween` từ 01/01 đến 31/12 |

Ví dụ:

```bash
curl 'http://localhost:8080/api/employees/search/hire-month?month=2&year=2024'
curl 'http://localhost:8080/api/employees/search/hire-date-between?startDate=2024-01-01&endDate=2024-12-31'
```

Trả HTTP `400` khi thiếu tham số bắt buộc, ngày không hợp lệ, tháng ngoài `1..12`,
năm ngoài `1..9999`, hoặc `startDate > endDate`.

## Truy vấn thống kê

Các API dưới đây dùng `GET`, cùng tiền tố `/api/employees` và đi qua
`EmployeeController` → `EmployeeService` → `EmployeeRepo`. Repository sử dụng
JPQL constructor projection để trả DTO có tên trường thay vì mảng `Object[]`.
Các DTO dùng `class`, có constructor tường minh khớp với `SELECT new` và getter/setter
do Lombok sinh. `COUNT` trả `Long`; các phép tổng hợp lương dùng `Double` để nhận
được `null` khi không có dữ liệu. Constructor đổi tổng lương `null` thành `0.0`.

| Đường dẫn | Nội dung | Kỹ thuật truy vấn |
| --- | --- | --- |
| `/stats/summary` | Tổng nhân viên, lương trung bình, tổng lương, lương nhỏ nhất/lớn nhất | `COUNT`, `AVG`, `SUM`, `MIN`, `MAX` |
| `/stats/department-details?minEmployees=2` | Thống kê nhân viên và lương từng phòng ban có ít nhất 2 nhân viên | `LEFT JOIN`, `GROUP BY`, `HAVING` |
| `/stats/position` | Thống kê nhân viên và lương từng chức vụ | `LEFT JOIN`, `GROUP BY` |
| `/stats/hire-monthly?year=2024` | Số nhân viên vào làm theo từng tháng trong năm 2024 và tổng lương hiện tại của họ | `BETWEEN`, `YEAR`, `MONTH`, `GROUP BY` |
| `/stats/hire-yearly` | Số nhân viên vào làm theo từng năm và tổng lương hiện tại của họ | `YEAR`, `GROUP BY` |

Ví dụ JSON của `/stats/summary` (số liệu minh họa):

```json
{
  "employeeCount": 10,
  "averageSalary": 20900000.0,
  "totalSalary": 209000000.0,
  "minSalary": 15000000.0,
  "maxSalary": 28000000.0
}
```

`department-details` và `position` trả thêm `id`, `name` bên cạnh các trường thống kê
trên. `minEmployees` mặc định bằng `0`; giá trị âm trả `400`. Nhóm trống có
`employeeCount = 0`, `totalSalary = 0`, còn `averageSalary`, `minSalary`, `maxSalary`
là `null`. Thống kê toàn bộ khi không có nhân viên cũng dùng quy tắc này.
Nhân viên chưa gán phòng ban/chức vụ được tính trong summary, nhưng không thuộc
nhóm phòng ban/chức vụ nào.

Thống kê thời gian chỉ trả tháng/năm có dữ liệu, sắp xếp tăng dần và bỏ qua
nhân viên có `hireDate = null`. `totalSalary` là tổng lương hiện tại của nhóm nhân viên
vào làm trong kỳ, không phải chi phí lương lịch sử. `year` bắt buộc với API theo
tháng và phải trong `1..9999`.

API cũ `/stats/department` vẫn trả mảng theo thứ tự
`[departmentId, departmentName, employeeCount, averageSalary, totalSalary]`.

## Tạo và cập nhật nhân viên

`POST /api/employees` và `PUT /api/employees/{id}` nhận thêm `hireDate`:

```json
{
  "id": 300,
  "name": "Nguyễn Văn An",
  "role": "Lập trình viên",
  "age": 25,
  "salary": 18000000,
  "hireDate": "2024-02-29",
  "department": { "id": 1 },
  "position": { "id": 1 }
}
```

ID nhân viên vẫn được truyền thủ công theo model hiện tại. `department.id` và
`position.id` phải tồn tại nếu được truyền. Với `PUT`, ID lấy từ đường dẫn;
request thay thế các thuộc tính nên cần gửi đủ dữ liệu muốn giữ lại, gồm cả `hireDate`.
Đọc, cập nhật hoặc xóa ID không tồn tại trả HTTP `404`.

Các API CRUD, tìm kiếm theo tên/tuổi/lương/chức vụ và thống kê hiện có đã chuyển
sang gọi service. API top lương trả `400` nếu `n <= 0`.

## Chạy và kiểm thử

Chỉnh kết nối MariaDB trong `src/main/resources/application.properties`, sau đó:

```bash
./mvnw spring-boot:run
```

Chạy test:

```bash
./mvnw test
```

Test dùng H2 riêng trong bộ nhớ, kiểm tra luồng controller → service → repository,
ngày nhuận, biên khoảng ngày/năm, truy vấn tháng/năm, JSON ngày tháng, CRUD và lỗi đầu vào.
Test thống kê kiểm tra các phép tổng hợp, nhóm trống, điều kiện `HAVING`,
nhân viên chưa có phòng ban/ngày vào làm và trường hợp database chưa có nhân viên.
