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

# 테스트용 질문 2개 생성
INSERT INTO question
SET create_date = NOW(),
`subject` = 'sbb가 무엇인가요?',
content = 'sbb에 대해서 알고 싶습니다.';

INSERT INTO question
SET create_date = NOW(),
`subject` = '스프링부트 모델 질문입니다.',
content = 'id는 자동으로 생성되나요?';

SELECT * FROM question;

## 답변 테이블 생성
CREATE TABLE answer (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    create_date DATETIME NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,
    content TEXT NOT NULL
);

SELECT * FROM answer;

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

# 답변 테이블에 author_id 칼럼 추가
ALTER TABLE answer ADD COLUMN author_id BIGINT UNSIGNED NOT NULL;

# 기존 질문은 1번 회원을 작성자로 일괄지정
UPDATE question
SET author_id = 1;

# 질문 테이블에 modify_date 칼럼 추가
ALTER TABLE question ADD COLUMN modify_date DATETIME AFTER create_date;

# 답변 테이블에 modify_date 칼럼 추가
ALTER TABLE answer ADD COLUMN modify_date DATETIME AFTER create_date;

# 테스트용 답변 2개 생성
INSERT INTO answer
SET create_date = NOW(),
content = 'sbb는 스프링부트에 관련된 QnA 서비스를 제공합니다.',
question_id = 1,
author_id = 2;

INSERT INTO answer
SET create_date = NOW(),
content = '네, 자동으로 생성됩니다.',
question_id = 2,
author_id = 2;

# 질문추천인 테이블 추가
CREATE TABLE question_voter (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT UNSIGNED NOT NULL,
    voter_id BIGINT UNSIGNED NOT NULL
);

# 답변추천인 테이블 추가
CREATE TABLE answer_voter (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    answer_id BIGINT UNSIGNED NOT NULL,
    voter_id BIGINT UNSIGNED NOT NULL
);