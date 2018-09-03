drop sequence hibernate_sequence;

drop TRIGGER t_customer_ID_TRIGGER
drop TRIGGER t_book_ID_TRIGGER
drop TRIGGER t_order_ID_TRIGGER
drop TRIGGER t_order_detail_ID_TRIGGER

drop table t_order_detail cascade constraints PURGE;
drop table t_order cascade constraints PURGE;
drop table t_book cascade constraints PURGE;
drop table t_customer cascade constraints PURGE;


create sequence hibernate_sequence;

create table t_customer(
	id number primary key,
	username varchar2(256) not null unique,	--uidÇÕó\ñÒå„Ç»ÇÃÇ≈usernameÇ…ïœçXÇ∑ÇÈ
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


CREATE or REPLACE TRIGGER t_customer_ID_TRIGGER
BEFORE INSERT
ON t_customer
FOR EACH ROW 
BEGIN
  IF :NEW.ID IS NULL THEN
    SELECT hibernate_sequence.NEXTVAL INTO :NEW.ID FROM DUAL;
  END IF;
END;
/

CREATE or REPLACE TRIGGER t_book_ID_TRIGGER
BEFORE INSERT
ON t_book
FOR EACH ROW 
BEGIN
  IF :NEW.ID IS NULL THEN
    SELECT hibernate_sequence.NEXTVAL INTO :NEW.ID FROM DUAL;
  END IF;
END;
/

CREATE or REPLACE TRIGGER t_order_ID_TRIGGER
BEFORE INSERT
ON t_order
FOR EACH ROW 
BEGIN
  IF :NEW.ID IS NULL THEN
    SELECT hibernate_sequence.NEXTVAL INTO :NEW.ID FROM DUAL;
  END IF;
END;
/

CREATE or REPLACE TRIGGER t_order_detail_ID_TRIGGER
BEFORE INSERT
ON t_order_detail
FOR EACH ROW 
BEGIN
  IF :NEW.ID IS NULL THEN
    SELECT hibernate_sequence.NEXTVAL INTO :NEW.ID FROM DUAL;
  END IF;
END;
/

