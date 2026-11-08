# Đề luyện Product REST API - 30 phút

Không sử dụng database. Dữ liệu lưu trong bộ nhớ.

## Yêu cầu bắt buộc

1. Tạo `RestApplication`, đặt REST base path là `/api`.
2. Tạo model `Product` gồm `id`, `name`, `price`.
3. Tạo `ProductCatalog` chứa ít nhất ba sản phẩm mẫu.
4. Tạo `ProductResource` và hoàn thành:
   - `GET /api/products`: lấy tất cả sản phẩm.
   - `GET /api/products/{id}`: lấy sản phẩm theo ID.
   - ID không tồn tại phải trả HTTP 404.
5. Dữ liệu trả về có định dạng JSON.
6. Build WAR và kiểm tra bằng Postman.

## Phần làm thêm nếu còn thời gian

- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

## Thời gian gợi ý

- 0-5 phút: `RestApplication` và `Product`.
- 5-10 phút: `ProductCatalog`.
- 10-22 phút: `ProductResource`.
- 22-30 phút: build, deploy và kiểm tra Postman.

Không xem project mẫu trong lúc làm.
