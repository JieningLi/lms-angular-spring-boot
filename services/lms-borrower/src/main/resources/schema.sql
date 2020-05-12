-- DROP ALL TABLES. ORDER IS (MOSTLY) IMPORTANT DUE TO FK CONSTRAINTS --
DROP TABLE IF EXISTS `tbl_book_genres`;
DROP TABLE IF EXISTS `tbl_book_copies`;
DROP TABLE IF EXISTS `tbl_book_authors`;
DROP TABLE IF EXISTS `tbl_library_branch`;
DROP TABLE IF EXISTS `tbl_author`;
DROP TABLE IF EXISTS `tbl_book`;
DROP TABLE IF EXISTS `tbl_publisher`;
DROP TABLE IF EXISTS `tbl_borrower`;
DROP TABLE IF EXISTS `tbl_genre`;
DROP TABLE IF EXISTS `tbl_book_loans`;
-- AUTHOR --
CREATE TABLE `tbl_author` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL
);
-- BORROWER --
CREATE TABLE `tbl_borrower` (
  `card_no` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) DEFAULT NULL,
  `address` VARCHAR(45) DEFAULT NULL,
  `phone` VARCHAR(45) DEFAULT NULL
);
-- GENRE TABLE --
CREATE TABLE `tbl_genre` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) DEFAULT NULL
);
-- LIBRARY BRANCHES --
CREATE TABLE `tbl_library_branch` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) DEFAULT NULL,
  `address` VARCHAR(45) DEFAULT NULL
);
-- PUBLISHER TABLE --
CREATE TABLE `tbl_publisher` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) DEFAULT NULL,
  `phone` VARCHAR(45) DEFAULT NULL
);
-- BOOK TABLE --
CREATE TABLE `tbl_book` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(45) NOT NULL,
  `publisher_id` BIGINT(11) DEFAULT NULL,
  FOREIGN KEY (`publisher_id`)
    REFERENCES `tbl_publisher` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
);
-- BOOK AUTHORS TABLE --
CREATE TABLE `tbl_book_authors` (
  `book_id` BIGINT(11) NOT NULL,
  `author_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`book_id`,`author_id`),
  FOREIGN KEY (`author_id`)
    REFERENCES `tbl_author` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`book_id`)
    REFERENCES `tbl_book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
-- BOOK COPIES TABLE --
CREATE TABLE `tbl_book_copies` (
  `book_id` BIGINT(11) NOT NULL,
  `branch_id` BIGINT(11) NOT NULL,
  `no_of_copies` BIGINT(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`, `branch_id`),
  FOREIGN KEY (`book_id`)
    REFERENCES `tbl_book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`branch_id`)
    REFERENCES `tbl_library_branch` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
-- BOOK GENRES TABLE --
CREATE TABLE `tbl_book_genres` (
  `genre_id` BIGINT(11) NOT NULL,
  `book_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`genre_id`, `book_id`),
  FOREIGN KEY (`book_id`)
    REFERENCES `tbl_book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`genre_id`)
    REFERENCES `tbl_genre` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
-- BOOK LOANS TABLE --
CREATE TABLE `tbl_book_loans` (
	`book_id` BIGINT(11) NOT NULL,
	`branch_id` BIGINT(11) NOT NULL,
	`card_no` BIGINT(11) NOT NULL,
	`date_out` DATETIME NOT NULL,
	`date_due` DATETIME NOT NULL,
	`date_in` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`book_id`, `branch_id`, `card_no`),
	FOREIGN KEY (`book_id`)
		REFERENCES `tbl_book` (`id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (`branch_id`)
		REFERENCES `tbl_library_branch` (`id`)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (`card_no`)
		REFERENCES `tbl_borrower` (`card_no`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);