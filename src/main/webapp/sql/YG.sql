create table board(
	board_num number not null,
	board_category number(1) not null, --1:공지사항, 2:자유게시판, 3:오늘 운동 완료
	board_title varchar2(300) not null,
	board_content clob not null,
	board_attachment varchar2(400),
	board_regdate date default not null sysdate,
	board_modifydate date,
	board_count number(9) default 0 not null,	
	user_num number not null,
	constraint board_pk primary key (board_num),
	constraint board_fk foreign key (user_num) references suser (user_num)
);

create sequence board_seq;

create table application(
	appl_num number not null,
	field number(1) not null,
	appl_status number(1) default 1 not null, 
	appl_attachment varchar2(400),
	appl_regdate date default sysdate not null,
	appl_modifydate date,
	career number(1) not null,
	content varchar2(1500) not null,
	source varchar2(150),
	appl_center number(3) not null,
	user_num number not null,
	constraint application_pk primary key (appl_num),
	constraint application_fk foreign key (user_num) references suser (user_num)
);

create sequence appl_seq;

create table board_reply (
	re_num number not null,
	re_content varchar2(900) not null,
	re_regdate date default sysdate not null,
	re_modifydate date,
	user_num number not null, -- 댓글 작성자
	board_num number not null, -- 게시판 글번호
	constraint board_reply_pk primary key (re_num),
	constraint board_reply_fk2 foreign key (user_num) references suser (user_num),
	constraint board_reply_fk1 foreign key (board_num) references board (board_num)
);

create sequence bd_reply_seq;



