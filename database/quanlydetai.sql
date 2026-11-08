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


-- Dumping database structure for quanlydetai
CREATE DATABASE IF NOT EXISTS `quanlydetai` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `quanlydetai`;

-- Dumping structure for table quanlydetai.detai
CREATE TABLE IF NOT EXISTS `detai` (
  `MADETAI` int(11) NOT NULL AUTO_INCREMENT,
  `TENDETAI` varchar(200) DEFAULT NULL,
  `MOTA` text DEFAULT NULL,
  `NAM` int(11) DEFAULT NULL,
  `MADANGKY` varchar(20) DEFAULT NULL,
  `MAGV` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MADETAI`),
  KEY `FK_detai_giangvien` (`MAGV`),
  CONSTRAINT `FK_detai_giangvien` FOREIGN KEY (`MAGV`) REFERENCES `giangvien` (`MAGV`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table quanlydetai.detai: ~17 rows (approximately)
DELETE FROM `detai`;
INSERT INTO `detai` (`MADETAI`, `TENDETAI`, `MOTA`, `NAM`, `MADANGKY`, `MAGV`) VALUES
	(1, 'Malware Analysis', 'Analyze and detect malware in systems.', 2025, 'CS-001', 'GV002'),
	(2, 'Personal Data Security', 'Solutions to protect user information.', 2024, 'IT-781', 'GV002'),
	(3, 'Cross-platform Flutter Application', 'Developing mobile apps using Flutter.', 2022, 'DS-003', 'GV003'),
	(4, 'Software Project Management', 'Building a system for project management.', 2026, 'SE-113', 'GV003'),
	(5, 'IoT Device Control', 'Designing a system to control devices over the network.', 2023, 'QT-114', 'GV004'),
	(6, 'Embedded Monitoring System', 'Environmental monitoring using embedded systems.', 2025, 'AB-115', 'GV004'),
	(7, 'Big Data Analytics', 'Processing and analyzing Big Data.', 2024, 'IS-453', 'GV001'),
	(8, 'Big Data Analytics', 'Processing and analyzing large datasets', 2024, 'IS-453', 'GV009'),
	(9, 'Cloud Native Architecture', 'Designing scalable cloud applications', 2025, 'CN-902', 'GV009'),
	(10, 'Malware Analysis', 'Analyze and detect malicious software', 2025, 'CS-001', 'GV006'),
	(11, 'Personal Data Security', 'Solutions to protect user privacy', 2024, 'IT-781', 'GV006'),
	(12, 'Cross-platform Frameworks', 'Developing mobile apps efficiently', 2022, 'DS-003', 'GV007'),
	(13, 'Software Project Management', 'Building a system for tracking tasks', 2026, 'SE-113', 'GV007'),
	(14, 'IoT Device Control', 'Designing a system for smart homes', 2023, 'QT-114', 'GV008'),
	(15, 'Embedded Monitoring', 'Environmental data acquisition', 2025, 'AB-115', 'GV008'),
	(16, 'Deep Learning Vision', 'Advanced computer vision models', 2024, 'DL-334', 'GV008'),
	(17, 'Natural Language Processing', 'Text mining and sentiment analysis', 2026, 'NLP-556', 'GV008');

-- Dumping structure for table quanlydetai.giangvien
CREATE TABLE IF NOT EXISTS `giangvien` (
  `MAGV` varchar(10) NOT NULL,
  `TENGV` varchar(100) DEFAULT NULL,
  `LINHVUCNGHIENCUU` varchar(100) DEFAULT NULL,
  `DIENTHOAI` varchar(15) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MAGV`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table quanlydetai.giangvien: ~10 rows (approximately)
DELETE FROM `giangvien`;
INSERT INTO `giangvien` (`MAGV`, `TENGV`, `LINHVUCNGHIENCUU`, `DIENTHOAI`, `EMAIL`) VALUES
	('GV001', 'Andrew Paker', 'Artificial Intelligence', '171-901234567', 'andrew@gmail.com'),
	('GV002', 'Bella Tommy', 'Information Security', '217-902345678', 'bella@gmail.com'),
	('GV003', 'Charles Liam', 'Software Development', '311-903456789', 'charles@gmail.com'),
	('GV004', 'Diana Rose', 'Embedded Systems', '213-904567890', 'diana@gmail.com'),
	('GV005', 'Edward John', 'Data Science', '171-905678901', 'edward@gmail.com'),
	('GV006', 'Frank Miller', 'Computer Vision', '415-906789012', 'frank@gmail.com'),
	('GV007', 'Grace Hopper', 'Cloud Computing', '510-907890123', 'grace@gmail.com'),
	('GV008', 'Henry Cavill', 'Machine Learning', '617-908901234', 'henry@gmail.com'),
	('GV009', 'Iris West', 'Web Development', '718-909012345', 'iris@gmail.com'),
	('GV010', 'Jack Ryan', 'Cyber Security', '819-900123456', 'jack@gmail.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
