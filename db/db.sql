DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

# article 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`subject` CHAR(100) NOT NULL,
	content TEXT NOT NULL
);

# member 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(50) NOT NULL UNIQUE,
	loginPw CHAR(100) NOT NULL,
	`name` CHAR(50) NOT NULL
);

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# 회원 테스트 데이터
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = '1234',
`name` = 'Paul';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = '1234',
`name` = 'Bob';

# 게시물 테이블 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL AFTER content;

# 게시물 테스트 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
`subject` = '제목1',
content = '내용1',
hit = 10;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
`subject` = '제목2',
content = '내용2',
hit = 5;