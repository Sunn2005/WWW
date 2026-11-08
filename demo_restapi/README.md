# Product REST API

REST API CRUD sản phẩm đơn giản dùng Jakarta REST (Jersey), CDI (Weld) và dữ liệu in-memory. Dữ liệu sẽ trở về mặc định mỗi khi server khởi động lại.

## Chạy dự án

Build file WAR:

```bash
./mvnw clean package
```

Deploy `target/demo_restapi-1.0-SNAPSHOT.war` lên Tomcat 11. Base URL mặc định:

```text
http://localhost:8080/demo_restapi-1.0-SNAPSHOT/api/products
```

## Test bằng Postman

Có thể import trực tiếp file `postman/Product-CRUD.postman_collection.json` vào Postman, hoặc tự tạo các request sau:

| Chức năng | Method | URL | Body |
|---|---|---|---|
| Lấy tất cả | GET | `/api/products` | Không |
| Lấy theo ID | GET | `/api/products/1` | Không |
| Thêm mới | POST | `/api/products` | JSON |
| Cập nhật | PUT | `/api/products/1` | JSON |
| Xóa | DELETE | `/api/products/1` | Không |

Với POST và PUT, chọn **Body → raw → JSON** rồi nhập:

```json
{
  "name": "Mechanical Keyboard",
  "price": 89.99
}
```

Các status code chính:

- `200 OK`: lấy, cập nhật hoặc xóa thành công.
- `201 Created`: tạo sản phẩm thành công.
- `404 Not Found`: không tìm thấy ID.

## Chạy test

```bash
./mvnw test
```
