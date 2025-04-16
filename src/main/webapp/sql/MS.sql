    CREATE TABLE friend (
    friend_num NUMBER NOT NULL,                  -- 친구번호 (Primary Key, Foreign Key)
    created_at TIMESTAMP DEFAULT SYSDATE,        -- 친구 관계가 설정된 시간
    user_num NUMBER NOT NULL,
    receiver_num number not null,-- 유저 번호 (Foreign Key)
    status VARCHAR2(10) NOT NULL,                 -- 친구 수락 상태 표시 ('요청1', '수락2', '거절3')
    CONSTRAINT friend_pk PRIMARY KEY (friend_num),  -- 친구번호를 기본키로 설정
    CONSTRAINT friend_user_fk FOREIGN KEY (user_num) REFERENCES SUSER_detail(user_num),
    CONSTRAINT friend_receiver_fk FOREIGN KEY (receiver_num) REFERENCES SUSER_detail(user_num)-- 유저 번호에 대한 외래키 설정
);



CREATE TABLE Messages (
    message_num NUMBER NOT NULL,                 -- 메시지 아이디 (Primary Key)
    sender_num NUMBER NOT NULL,                  -- 메시지 보낸 사람 아이디 (Foreign Key)
    receiver_num NUMBER NOT NULL,                -- 메시지 받는 사람 아이디 (Foreign Key)
    message_text CLOB NOT NULL,                  -- 메시지 내용
    is_read CHAR(1) DEFAULT 'N',                 -- 메시지를 읽었나? (기본값 'N')
    message_date date default sysdate NOT NULL,                  -- 메시지 시간
    CONSTRAINT messages_pk PRIMARY KEY (message_num),  -- 메시지 아이디를 기본키로 설정
    CONSTRAINT messages_sender_fk FOREIGN KEY (sender_num) REFERENCES suser_detail(user_num),  -- 발신자 아이디 외래키
    CONSTRAINT messages_receiver_fk FOREIGN KEY (receiver_num) REFERENCES suser_detail(user_num)  -- 수신자 아이디 외래키
);

CREATE SEQUENCE seq_friend
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
    
    CREATE SEQUENCE seq_messages
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
    
    
    
  CREATE TABLE membershipOrder (
    order_num INT PRIMARY KEY,
    user_num INT,
    typeId INT,
    receive_phone VARCHAR2(15),
    price INT,
    order_date date
);

-- order_num 컬럼을 자동 증가하도록 SEQUENCE와 트리거 설정
CREATE SEQUENCE order_num_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;