# Employee JdbcTemplate — REST API / Postman

Ứng dụng dùng Spring Boot, JdbcTemplate và MariaDB employeedb. Không có Thymeleaf hoặc giao diện HTML.

## Chạy

1. Nếu chưa có database, import `data/employeedb.sql` trong công cụ quản trị MariaDB. File này xóa và nạp lại dữ liệu ba bảng, nên không import lại nếu muốn giữ dữ liệu hiện có.
2. Cấu hình datasource trong `src/main/resources/application.properties` theo máy của bạn.
3. Dùng JDK 21 trở lên, chạy `./mvnw spring-boot:run` trong thư mục project.
4. Import `Employee_JdbcTemplate.postman_collection.json` vào Postman. Biến `baseUrl` mặc định là `http://localhost:8080`.

## Đề bài

- CRUD nhân viên; tên không rỗng, tuổi và lương không âm.
- JOIN nhân viên với phòng ban và chức vụ để trả tên tương ứng.
- Tìm tên gần đúng, khoảng tuổi, lương lớn hơn một mức, chức vụ; kết hợp các điều kiện.
- Lọc nhân viên theo phòng ban và chức vụ.
- Thống kê từng phòng: COUNT nhân viên, AVG và SUM lương, giữ cả phòng trống.
- Top N lương cao nhất trong phòng; tìm phòng chưa có nhân viên.

## API

Tất cả URL bên dưới có tiền tố `/api/employees`.

| Method | Path | Chức năng |
| --- | --- | --- |
| GET | / | Danh sách (gọi URL không có dấu / cuối: /api/employees) |
| GET | /{id} | Chi tiết |
| POST | / | Thêm (gọi /api/employees) |
| PUT | /{id} | Sửa |
| DELETE | /{id} | Xóa |
| GET | /departments | Danh sách phòng ban |
| GET | /positions | Danh sách chức vụ |
| GET | /search?name=Nguy&minAge=25&maxAge=35&minSalary=15000000&positionId=1 | Lọc tùy chọn |
| GET | /by-position?positionId=1 | Theo chức vụ |
| GET | /by-department-position?departmentId=1&positionId=1 | Theo phòng và chức vụ |
| GET | /stats/department | Thống kê phòng |
| GET | /stats/departments-empty | Phòng trống |
| GET | /stats/top-salary?departmentId=1&n=5 | Top lương; n >= 1 |

Body POST/PUT:

```json
{
  "name": "Nhan vien Postman",
  "role": "Lap trinh vien",
  "age": 25,
  "salary": 18000000,
  "departmentId": 1,
  "positionId": 1
}
```

Trong Postman, chọn Body → raw → JSON. Dùng phòng ban/chức vụ tồn tại. Request thêm tự lưu ID vào biến employeeId; chạy tiếp xem, sửa, xóa và kiểm tra 404 theo thứ tự trong collection. Chỉ xóa nhân viên vừa thêm.

HTTP: thêm 201; đọc/sửa 200; xóa 204; ID không tồn tại 404; validation hoặc n không hợp lệ 400; vi phạm ràng buộc dữ liệu 409.

## Đọc từng class

1. `model/Employee`: dữ liệu Java, ID phòng ban/chức vụ và tên lấy từ JOIN.
2. `dto/EmployeeForm`: body và validation; `EmployeeSearchCriteria`: điều kiện tìm; `DepartmentStats`: thống kê.
3. `dao/EmployeeDao`: hợp đồng; `dao/impl/EmployeeDaoImpl`: SQL, RowMapper, tham số ?, GeneratedKeyHolder.
4. `dao/impl/DepartmentDaoImpl`: LEFT JOIN, COUNT, AVG, SUM; phòng trống.
5. `service/EmployeeService`: điều phối nghiệp vụ và DAO.
6. `controller/EmployeeRestController`: endpoint JSON; `ApiExceptionHandler`: lỗi 404/409.

Luồng: Postman → Controller → Service → DAO → JdbcTemplate → MariaDB.

## Ghi nhớ

- query(sql, mapper, args...) trả List; queryForObject yêu cầu đúng một dòng.
- update(sql, args...) dùng cho INSERT/UPDATE/DELETE, trả số dòng tác động.
- Tên method không tự sinh SQL; không dùng @Query hoặc CrudRepository.
- Alias AS tự đặt; rs.getXxx("alias") đọc đúng alias đó.
- Dấu ? khớp thứ tự tham số; không nối dữ liệu người dùng trực tiếp vào SQL.
- Cột số nullable: getObject("age", Integer.class) giữ null.
- COUNT(e.id) trả 0 cho phòng trống; COUNT(*) vẫn đếm dòng phòng.
- AVG/SUM có thể null ở phòng trống; COALESCE khi đề yêu cầu 0.
