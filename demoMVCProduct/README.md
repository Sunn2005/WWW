# Quản lý sản phẩm bằng Servlet/JSP và JDBC

Bài tập minh họa mô hình MVC 2 với 4 chức năng CRUD cho sản phẩm gồm `id`, `name`, `price`.
Mã sản phẩm là chuỗi tối đa 20 ký tự, ví dụ `P001`.

## Công nghệ

- Jakarta Servlet 6.0, JSP 3.1, JSTL 3.0
- JDBC DataSource/JNDI
- MariaDB
- Maven, đóng gói dạng WAR

## Chuẩn bị cơ sở dữ liệu

1. Khởi động MariaDB.
2. Chạy file `database.sql` để tạo database `productdb`, bảng `products` và dữ liệu mẫu.
3. Sửa `username` và `password` trong `src/main/webapp/META-INF/context.xml` nếu tài khoản MariaDB trên máy khác cấu hình mẫu.

## Chạy dự án

1. Dùng JDK 22 và Tomcat 10.1 trở lên.
2. Chạy `./mvnw clean package`.
3. Deploy file `target/demoMVCProduct-1.0-SNAPSHOT.war` lên Tomcat.
4. Mở `/demoMVCProduct-1.0-SNAPSHOT/` hoặc `/demoMVCProduct-1.0-SNAPSHOT/products`.

## Luồng MVC

- Model: `Product`
- Truy cập dữ liệu: `ProductDao`, `DBUtil`
- Controller: `ProductServlet`, URL `/products`
- View: các file JSP trong `src/main/webapp/views`

Các action của controller gồm `list`, `new`, `insert`, `details`, `edit`, `update` và `delete`.
