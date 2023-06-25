-- drop tabl customers;
CREATE TABLE customers (
	id BIGINT auto_increment,
	name varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	phone varchar(30),
	address varchar(250),
	CONSTRAINT customers_PK PRIMARY KEY (id)
);
