
drop table t_order_detail;
drop table t_order;
drop table t_book;
drop table t_customer;
drop sequence hibernate_sequence;


create sequence hibernate_sequence;

create table t_customer(
	id number primary key,
	username varchar2(256) not null unique,
	passwordmd5 varchar2(256) not null,
	name varchar2(256) not null,
	email varchar2(256) not null );


create table t_book(
	id number primary key,
	isbn varchar2(256) not null unique,
	title varchar2(256) not null,
	author varchar2(256) not null,
	publisher varchar2(256) not null,
	price number not null );



create table t_order(
	id number primary key,
	customer_id_fk number not null
		constraint order_customer_id_constraint
			references t_customer(id),
	orderday timestamp  not null );
	


create table t_order_detail(
	id number primary key,
	order_id_fk	number not null
		constraint detail_order_id_constraint
			references t_order(id),
	book_id_fk number not null
		constraint defail_book_id_constraint
			references t_book(id) );
