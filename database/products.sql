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


-- Dumping database structure for productdb
CREATE DATABASE IF NOT EXISTS `productdb` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `productdb`;

-- Dumping structure for table productdb.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table productdb.categories: ~5 rows (approximately)
DELETE FROM `categories`;
INSERT INTO `categories` (`id`, `name`) VALUES
	(1, 'Electronics'),
	(2, 'Clothing'),
	(3, 'Books'),
	(4, 'Toys'),
	(5, 'Home Appliances');

-- Dumping structure for table productdb.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `product_condition` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table productdb.products: ~12 rows (approximately)
DELETE FROM `products`;
INSERT INTO `products` (`id`, `name`, `price`, `description`, `available`, `product_condition`, `image`, `category_id`) VALUES
	(1, 'Laptop', 1500, 'Gaming laptop', b'0', 'New', 'laptop.jpg', 1),
	(2, 'T-shirtAAAAA', 20, 'Cotton shirt', b'0', 'Used', '3723f647-d58d-4bcf-bf3e-54a68cf8ec2c_flower.png', 3),
	(3, 'Novel', 10, 'Bestseller', b'1', 'New', 'book.jpg', 3),
	(4, 'Action Figure', 35, 'Marvel toy', b'1', 'New', 'toy.jpg', 4),
	(5, 'Microwave', 200, 'For home use', b'1', 'Used', 'microwave.jpg', 5),
	(6, 'Smartphone', 1000, 'Android phone', b'1', 'New', 'phone.jpg', 1),
	(7, 'Jeans', 40, 'Denim', b'1', 'New', 'jeans.jpg', 2),
	(8, 'Cookbook', 25, 'Recipes', b'1', 'New', 'cookbook.jpg', 3),
	(9, 'Puzzle', 15, '1000 pieces', b'0', 'New', 'puzzle.jpg', 4),
	(10, 'Vacuum Cleaner', 180, 'Powerful suction', b'1', 'Used', 'vacuum.jpg', 5),
	(13, 'AAAA', 9999, 'AAAAAAAAA', b'0', 'Used', 'book.jpg', 3),
	(14, 'Coca', 4000, 'aaa', b'1', 'New', '39c5c858-bcda-4ac5-a81b-3e31c93ce5ca_flower.png', 1);

-- Dumping structure for table productdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table productdb.user: ~4 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`, `password`) VALUES
	(9, 'admin1', '123'),
	(10, 'admin2', '123'),
	(11, 'cus1', '111'),
	(12, 'cus2', '111');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
