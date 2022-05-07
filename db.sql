DROP DATABASE IF EXISTS sbb;
CREATE DATABASE sbb;
USE sbb;

## 질문 테이블 생성
CREATE TABLE question (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    create_date DATETIME NOT NULL,
    `subject` VARCHAR(200) NOT NULL,
    content TEXT NOT NULL
);

SELECT * FROM question;

## 답변 테이블 생성
CREATE TABLE answer (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    create_date DATETIME NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,
    content TEXT NOT NULL
);

SELECT * FROM answer;

DROP TABLE site_user;
## 회원 테이블 생성
CREATE TABLE site_user (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username CHAR(100) NOT NULL UNIQUE,
    `password` CHAR(100) NOT NULL,
    email CHAR(100) NOT NULL UNIQUE
);

SELECT * FROM site_user;

# 비밀번호 : sbs1234 => bcrypt 적용
# 테스트용 사용자 3명 생성(1명은 관리자)
INSERT INTO site_user
SET username = 'admin',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'admin@test.com';

INSERT INTO site_user
SET username = 'user1',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'user1@test.com';

INSERT INTO site_user
SET username = 'user2',
`password` = '$2a$10$ECLIahn9UgeZxUxmca6HyeYTwOAspolhesV1qXFQNF5zyR20FfmB6',
email = 'user2@test.com';

# 질문 테이블에 author_id 칼럼 추가
ALTER TABLE question ADD COLUMN author_id BIGINT UNSIGNED NOT NULL;

# 기존 질문은 1번 회원을 작성자로 일괄지정
UPDATE question
SET author_id = 1;

# 답변 테이블에 author_id 칼럼 추가
ALTER TABLE answer ADD COLUMN author_id BIGINT UNSIGNED NOT NULL;

# 질문 테이블에 modify_date 칼럼 추가
ALTER TABLE question ADD COLUMN modify_date DATETIME AFTER create_date;

# 답변 테이블에 modify_date 칼럼 추가
ALTER TABLE answer ADD COLUMN modify_date DATETIME AFTER create_date;