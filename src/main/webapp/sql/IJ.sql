--상품
create table goods(
 goods_num number not null,
 goods_name varchar2(60) not null,
 goods_price number(7) not null,
 goods_info clob null,
 goods_category varchar2(45) not null,
 goods_img1 varchar2(400) not null,
 goods_img2 varchar2(400) not null,
 goods_date date default sysdate not null,
 goods_mdate date,
 goods_quantity number(7) not null,
 goods_status number(1) not null, --표시여부(판매 가능 여부) 1:미표시 2:표시
 constraint goods_pk primary key (goods_num)
);
create sequence goods_seq;

--리뷰
create table goods_review(
 re_num number not null,
 re_content varchar2(900) not null,
 re_rating number(1) not null,
 re_date date default sysdate not null,
 re_mdate date,
 re_ip varchar2(40) not null,
 goods_num number not null,
 user_num number not null,
 constraint review_pk primary key (re_num),
 constraint review_fk1 foreign key (goods_num) references goods (goods_num),
 constraint review_fk2 foreign key (user_num) references suser_detail (user_num)
);
create sequence review_seq;

--좋아요
create table goods_like(
 goods_num number not null,
 user_num number not null,
 constraint goods_like_fk1 foreign key (goods_num) references goods (goods_num),
 constraint goods_like_fk2 foreign key (user_num) references suser (user_num)
);

--세일
create table sale(
 goods_num number not null,
 sale_discount number(2) not null,
 sale_startdate date not null,
 sale_enddate date not null,
 sale_status number(1) not null,
 constraint sale_fk1 foreign key (goods_num) references goods (goods_num)
);

--회원권
create table membership(
 mem_num number not null,
 user_num number not null,
 payment_status number(1) not null,
 mem_type number(1) not null,
 mem_startdate date not null,
 mem_enddate date not null,
 mem_status number(1) not null,
 mem_price number(7) not null,
 constraint membership_pk primary key (mem_num),
 constraint membership_fk1 foreign key (user_num) references suser (user_num)
);
create sequence membership_seq;
 
--PT
create table pt(
 pt_num number not null,
 mem_num number not null,
 payment_status number(1) not null,
 pt_reservedate date not null,
 pt_status number(1) not null,
 pt_price number(7) not null,
 constraint pt_pk primary key (pt_num),
 constraint pt_fk1 foreign key (mem_num) references membership (mem_num)
);
create sequence pt_seq;

-- 장바구니
create table cart(
 cart_num number not null,
 goods_num number not null,
 user_num number not null,
 order_quantity number not null,
 constraint cart_pk primary key (cart_num),
 constraint goods_num_fk1 foreign key (goods_num) references goods (goods_num),
 constraint user_num_fk2 foreign key (user_num) references suser (user_num)
);
create sequence cart_seq;

--주문
/*create table orders(
 order_num number not null,
 user_num number not null,
 goods_num number not null,
 cart_num number not null,
 order_date date default sysdate not null,
 order_status number not null,
 payment_status number(1) not null,
 order_address varchar2(90) not null,
 order_quantity number(3) not null,
 constraint order_pk primary key (order_num),
 constraint order_fk1 foreign key (user_num) references suser (user_num),
 constraint order_fk2 foreign key (goods_num) references goods (goods_num),
 constraint order_fk3 foreign key (cart_num) references cart (cart_num)
);
create sequence order_seq; */

--주문
create table orders(
 order_num number not null,
 order_total number(9) not null,
 payment number(1) not null, -- 결제 방식
 status number(1) default 1 not null, -- 배송 상태
 receive_name varchar2(30) not null,
 receive_post varchar2(5) not null,
 receive_address1 varchar2(90) not null,
 receive_address2 varchar2(90) not null,
 receive_phone varchar2(15) not null,
 notice varchar2(4000),
 reg_date date default sysdate not null,
 modify_date date,
 user_num number not null,
 constraint orders_pk primary key (order_num),
 constraint orders_fk1 foreign key (user_num)
                        references suser (user_num)
);
create sequence order_seq;

create table order_detail(
 detail_num number not null,
 goods_num number not null,
 goods_name varchar2(60) not null,
 goods_price number(9) not null,
 goods_total number(9) not null,
 order_quantity number(7) not null,
 order_num number not null,
 constraint order_detail_pk primary key (detail_num),
 constraint order_detail_fk1 foreign key (order_num) 
                           references orders (order_num)
);
create sequence order_detail_seq;





