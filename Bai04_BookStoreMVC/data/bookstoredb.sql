-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.4.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for bookstoredb
CREATE DATABASE IF NOT EXISTS `bookstoredb` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_uca1400_vietnamese_ai_ci */;
USE `bookstoredb`;

-- Dumping structure for table bookstoredb.books
CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tittle` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `imgbook` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf16 COLLATE=utf16_uca1400_vietnamese_ai_ci;

-- Dumping data for table bookstoredb.books: ~20 rows (approximately)
DELETE FROM `books`;
INSERT INTO `books` (`id`, `tittle`, `author`, `imgbook`) VALUES
	(1, 'Nhà Giả Kim', 'Paulo Coelho', 'nha_gia_kim.jpg'),
	(2, 'Đắc Nhân Tâm', 'Dale Carnegie', 'dac_nhan_tam.jpg'),
	(3, 'Tuổi Trẻ Đáng Giá Bao Nhiêu', 'Rosie Nguyễn', 'tuoi_tre_dang_gia.jpg'),
	(4, 'Hạt Giống Tâm Hồn', 'Jack Canfield', 'hat_giong_tam_hon.jpg'),
	(5, 'Mắt Biếc', 'Nguyễn Nhật Ánh', 'mat_biec.jpg'),
	(6, 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Nguyễn Nhật Ánh', 'cho_toi_xin_mot_ve.jpg'),
	(7, 'Người Xa Lạ', 'Albert Camus', 'nguoi_xa_la.jpg'),
	(8, 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Nguyễn Nhật Ánh', 'hoa_vang_co_xanh.jpg'),
	(9, 'Sapiens: Lược Sử Loài Người', 'Yuval Noah Harari', 'sapiens.jpg'),
	(10, 'Đường Xưa Mây Trắng', 'Thích Nhất Hạnh', 'duong_xua_may_trang.jpg'),
	(11, 'Cây Cam Ngọt Của Tôi', 'J. M. Vasconcelos', 'cay_cam_ngot.jpg'),
	(12, 'Nhà Lãnh Đạo Không Chức Danh', 'Robin Sharma', 'nha_lanh_dao.jpg'),
	(13, 'Tư Duy Nhanh Và Chậm', 'Daniel Kahneman', 'tu_duy_nhanh_cham.jpg'),
	(14, 'Hiểu Về Trái Tim', 'Thích Nhất Hạnh', 'hieu_ve_trai_tim.jpg'),
	(15, 'Bắt Trẻ Đồng Xanh', 'J. D. Salinger', 'bat_tre_dong_xanh.jpg'),
	(16, 'Giết Con Chim Nháy', 'Harper Lee', 'giet_con_chim_nhay.jpg'),
	(17, 'Chiến Binh Cầu Vồng', 'Andrea Hirata', 'chien_binh_cau_vong.jpg'),
	(18, 'Không Gia Đình', 'Hector Malot', 'khong_gia_dinh.jpg'),
	(19, 'Hoàng Tử Bé', 'Antoine de Saint-Exupéry', 'hoang_tu_be.jpg'),
	(20, 'Sherlock Holmes', 'Arthur Conan Doyle', 'sherlock_holmes.jpg');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
