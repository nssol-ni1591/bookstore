set schema bookstore;

drop table t_order_detail;
drop table t_order;
drop table t_book;
drop table t_customer;

create table t_customer(
	id integer generated always as identity
			--default nextval( 'hibernate_sequence' )
			primary key
			not null
			--unique
	--, uid varchar(16) not null unique
	, username varchar(16) not null unique
	, passwordmd5 varchar(32) not null
	, name varchar(64) not null
	, email varchar(64) not null
	--, primary key (id)
);

create table t_book(
	id integer generated always as identity
		--default nextval( 'hibernate_sequence' )
		primary key
		--unique
		not null
	, isbn varchar(32) not null unique
	, title varchar(128) not null
	, author varchar(32) not null
	, publisher varchar(32) not null
	, price integer not null
);

create table t_order(
	id integer generated always as identity
		--default nextval( 'hibernate_sequence' )
		primary key
		--unique
		not null
	, customer_id_fk integer not null
		constraint order_customer_id_constraint
		references t_customer(id)
	, orderday timestamp
		--with time zone
		not null
);

create table t_order_detail(
	id integer generated always as identity
		--default nextval( 'hibernate_sequence' )
		primary key
		--unique
		not null
	, order_id_fk integer not null
		constraint detail_order_id_constraint
		references t_order(id)
	, book_id_fk integer not null
		constraint defail_book_id_constraint
		references t_book(id)
);
