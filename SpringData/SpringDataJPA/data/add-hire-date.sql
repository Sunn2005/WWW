-- Chạy trên database hiện có nếu muốn thêm cột thủ công.
-- Hibernate ddl-auto=update cũng tự thêm cột này khi khởi động ứng dụng.
USE `employeedb`;

ALTER TABLE `employees`
    ADD COLUMN IF NOT EXISTS `hire_date` DATE DEFAULT NULL;

-- Có thể cập nhật ngày vào làm thật sau khi thêm cột, ví dụ:
-- UPDATE `employees` SET `hire_date` = '2024-02-29' WHERE `id` = 1;
