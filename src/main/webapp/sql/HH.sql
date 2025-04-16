-- USER 테이블 생성 1 
CREATE TABLE SUSER (
    user_num NUMBER NOT NULL,                       -- 사용자 고유 ID (Primary Key)
    login_id VARCHAR2(30) NOT NULL,                 -- 사용자 로그인 ID (Unique Key)
    status NUMBER(1) NOT NULL,                      -- 계정의 상태 (0: 탈퇴, 1: 일반 사용자, 2: 트레이너, 3:사무직 관리자, 4: 마스터 관리자, 5: 정지)
    CONSTRAINT user_pk PRIMARY KEY (user_num),
    CONSTRAINT user_login_id_uk UNIQUE (login_id)
);

CREATE TABLE SUSER_DETAIL (
    user_num NUMBER NOT NULL,                       -- 사용자 고유 ID (Primary Key, Foreign Key from USER)
    nick_name VARCHAR2(30) NOT NULL,                -- 사용자의 별명 (Unique Key)
    name VARCHAR2(50) NOT NULL,                     -- 사용자의 이름
    email VARCHAR2(20) NOT NULL,                    -- 사용자의 이메일 주소 (Unique Key)
    password VARCHAR2(50) NOT NULL,                 -- 사용자 ID의 비밀번호
    phone VARCHAR2(15) NOT NULL,                    -- 사용자의 전화번호 (Unique Key)
    registration_date DATE DEFAULT SYSDATE NOT NULL,-- 사용자의 가입일자
    birth_date VARCHAR2(8) NOT NULL,                -- 사용자의 생년월일
    modify_date DATE,                               -- 수정일
    center_num NUMBER(1) NOT NULL,                  -- 센터번호 (1: 강남점, 2: 강북점)
    photo VARCHAR2(255) DEFAULT 'default_user_photo.png', -- 프로필 사진 
    CONSTRAINT user_detail_pk PRIMARY KEY (user_num),
    CONSTRAINT user_detail_user_fk FOREIGN KEY (user_num) REFERENCES SUSER(user_num),
    CONSTRAINT user_detail_nick_name_uk UNIQUE (nick_name),
    CONSTRAINT user_detail_email_uk UNIQUE (email),
    CONSTRAINT user_detail_phone_uk UNIQUE (phone)
);
-- USER 테이블용 시퀀스 생성 (필요시)
CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;
    
--회원 입장시간 체크용 테이블 ( 회원 출입 관련 항목 신규 생성 )
    CREATE TABLE entry_logs (
    entry_id NUMBER PRIMARY KEY,          -- 고유 ID
    user_num NUMBER NOT NULL,             -- 회원 번호 (suser_detail.user_num FK)
    phone_number VARCHAR2(15) NOT NULL,   -- 핸드폰 번호 (suser_detail.phone FK)
    entry_time TIMESTAMP DEFAULT SYSDATE  -- 입장 시간
);

CREATE SEQUENCE entry_logs_seq START WITH 1 INCREMENT BY 1;