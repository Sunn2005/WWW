-- Dữ liệu mẫu để thử API ngày vào làm. Chạy lại không ghi đè ID đã có.
SET NAMES utf8mb4;
USE `employeedb`;

ALTER TABLE `employees`
    ADD COLUMN IF NOT EXISTS `hire_date` DATE DEFAULT NULL;

START TRANSACTION;

INSERT INTO `departments` (`id`, `name`)
SELECT 1, 'Công nghệ thông tin'
WHERE NOT EXISTS (SELECT 1 FROM `departments` WHERE `id` = 1);

INSERT INTO `positions` (`id`, `title`)
SELECT 1, 'Nhân viên'
WHERE NOT EXISTS (SELECT 1 FROM `positions` WHERE `id` = 1);

INSERT INTO `employees`
    (`id`, `name`, `role`, `age`, `salary`, `hire_date`, `department_id`, `position_id`)
SELECT sample.id, sample.name, sample.role, sample.age, sample.salary,
       sample.hire_date, 1, 1
FROM (
    SELECT 301 AS id, 'Nguyễn Văn An' AS name, 'Lập trình viên' AS role,
           25 AS age, 18000000 AS salary, CAST('2023-12-31' AS DATE) AS hire_date
    UNION ALL SELECT 302, 'Trần Thị Bình', 'Kiểm thử phần mềm', 26, 16000000, '2024-01-01'
    UNION ALL SELECT 303, 'Lê Minh Châu', 'Lập trình viên', 27, 22000000, '2024-02-01'
    UNION ALL SELECT 304, 'Phạm Thu Dung', 'Phân tích nghiệp vụ', 28, 24000000, '2024-02-15'
    UNION ALL SELECT 305, 'Hoàng Quốc Huy', 'DevOps', 29, 26000000, '2024-02-29'
    UNION ALL SELECT 306, 'Vũ Ngọc Lan', 'Kiểm thử phần mềm', 24, 15000000, '2024-03-01'
    UNION ALL SELECT 307, 'Đặng Minh Khoa', 'Lập trình viên', 30, 28000000, '2024-12-31'
    UNION ALL SELECT 308, 'Bùi Thanh Mai', 'Phân tích nghiệp vụ', 27, 23000000, '2025-01-01'
    UNION ALL SELECT 309, 'Ngô Đức Nam', 'Lập trình viên', 26, 20000000, '2025-02-01'
    UNION ALL SELECT 310, 'Đỗ Hải Yến', 'Kiểm thử phần mềm', 25, 17000000, '2026-11-02'
) AS sample
WHERE NOT EXISTS (SELECT 1 FROM `employees` e WHERE e.id = sample.id);

COMMIT;

SELECT `id`, `name`, `hire_date`
FROM `employees`
WHERE `id` BETWEEN 301 AND 310
ORDER BY `id`;
