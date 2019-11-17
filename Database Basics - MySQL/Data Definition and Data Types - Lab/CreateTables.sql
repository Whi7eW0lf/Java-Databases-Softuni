use gamebar;

create table employees
(
	id int auto_increment,
	first_name nvarchar(45) not null,
	last_name varchar(45) not null,
	constraint employees_pk
		primary key (id)
);
create table categories
(
	id int auto_increment,
	name varchar(45) not null,
	constraint categories_pk
		primary key (id)
);

create table products
(
	id int auto_increment,
	name varchar(45) not null,
	category_id int not null,
	constraint products_pk
		primary key (id)
);


