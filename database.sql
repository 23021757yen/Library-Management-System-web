-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 19, 2024 lúc 12:59 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Create the database
-- CREATE DATABASE IF NOT EXISTS library;
USE library;

-- Ensure settings are properly configured
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
SET NAMES utf8mb4;

-- Create custom function
DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `generateRandomID` (`len` INT) RETURNS VARCHAR(255) CHARSET utf8mb4 DETERMINISTIC
BEGIN
    DECLARE chars VARCHAR(62) DEFAULT 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    DECLARE result VARCHAR(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;
    WHILE i < len DO
            SET result = CONCAT(result, SUBSTRING(chars, FLOOR(1 + RAND() * CHAR_LENGTH(chars)), 1));
            SET i = i + 1;
        END WHILE;
    RETURN result;
END$$
DELIMITER ;

-- Create tables
CREATE TABLE IF NOT EXISTS `admin` (
                                       `admin_ID` VARCHAR(10) NOT NULL DEFAULT '',
                                       `password` VARCHAR(100) NOT NULL DEFAULT 'root',
                                       PRIMARY KEY (`admin_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user` (
                                      `User_ID` VARCHAR(20) NOT NULL DEFAULT '',
                                      `email` VARCHAR(50) NOT NULL DEFAULT '',
                                      `name` VARCHAR(100) DEFAULT NULL,
                                      `password` VARCHAR(100) NOT NULL DEFAULT 'amen',
                                      `phone` VARCHAR(20) NOT NULL DEFAULT '0000000000',
                                      `profile_image` LONGBLOB,
                                      PRIMARY KEY (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `author` (
                                        `books` TEXT DEFAULT NULL,
                                        `name` VARCHAR(255) NOT NULL DEFAULT '',
                                        `autobiography` TEXT DEFAULT NULL,
                                        PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `publisher` (
                                           `name` VARCHAR(255) NOT NULL DEFAULT '',
                                           `address` VARCHAR(255) DEFAULT NULL,
                                           `contact` VARCHAR(255) NOT NULL DEFAULT '',
                                           PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `books` (
                                       `book_ID` VARCHAR(20) NOT NULL, -- Assuming book_ID from Google Books API is a string
                                       `title` VARCHAR(255) DEFAULT NULL,
                                       `amount` INT DEFAULT 100,
                                       `description` TEXT DEFAULT NULL,
                                       `location` VARCHAR(255) DEFAULT NULL,
                                       `yearPublic` YEAR DEFAULT NULL,
                                       `price` INT DEFAULT NULL,
                                       `kind` VARCHAR(50) DEFAULT NULL,
                                       `author` VARCHAR(255) NOT NULL,
                                       `image` VARCHAR(255) NOT NULL,
                                       `addDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `viewCount` INT DEFAULT 0,
                                       PRIMARY KEY (`book_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `borrow` (
                                        `book_ID` VARCHAR(20) NOT NULL,
                                        `endDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `dueDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `User_ID` VARCHAR(20) NOT NULL,
                                        `status` VARCHAR(20) DEFAULT NULL,
                                        `overTime` VARCHAR(10) DEFAULT NULL,
                                        PRIMARY KEY (`book_ID`, `User_ID`),
                                        FOREIGN KEY (`book_ID`) REFERENCES `books` (`book_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `fine` (
                                      `fine_ID` INT NOT NULL AUTO_INCREMENT,
                                      `dueDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `endDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `reason` VARCHAR(255) DEFAULT NULL,
                                      `amount` INT DEFAULT NULL,
                                      `status` VARCHAR(20) NOT NULL DEFAULT 'done',
                                      `User_ID` VARCHAR(20) DEFAULT NULL,
                                      PRIMARY KEY (`fine_ID`),
                                      FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `highlightbook` (
                                               `user_ID` VARCHAR(20) NOT NULL,
                                               `book_ID` VARCHAR(20) NOT NULL,
                                               PRIMARY KEY (`user_ID`, `book_ID`),
                                               FOREIGN KEY (`book_ID`) REFERENCES `books` (`book_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
                                               FOREIGN KEY (`user_ID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `recentbooks` (
                                             `user_ID` VARCHAR(20) NOT NULL,
                                             `book_ID` VARCHAR(20) NOT NULL,
                                             `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             PRIMARY KEY (`user_ID`, `book_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Commit the transaction
COMMIT;