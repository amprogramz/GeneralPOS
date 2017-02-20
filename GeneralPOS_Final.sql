drop database if exists general_pos;

create database general_pos;
use general_pos;

create table Employee(
employee_id 	int 	not null	primary key 	auto_increment,
employee_first 	varchar(20) 	not null,
employee_last 	varchar(20) 	not null,
street		varchar(20)		not null,
city		varchar(10) 	not null,
state 		varchar(11) 	not null,
zip_code	int(10)			not null,
date_start	datetime	 
);

create table sale(
sale_id 	int		not null	primary key		auto_increment,
employee_id int,

constraint sale__employee_fk
	foreign key (employee_id)
	references employee(employee_id)
);

create table items(
item_id 	int		not null	primary key 	auto_increment,
item_name 	varchar(50) not null,
item_price	double(4,2)		not null,
item_description varchar(100)


/*constraint items__inventory_fk
	foreign key (storage_id)
    references	inventory(storage_id)*/
);

create table make_transaction(
transacton_id 	int	 	not null	primary key 	auto_increment,
sale_id		int,
item_id		int,
quantity	int		not null,


constraint make_transaction__sale_fk
	foreign key (sale_id)
    references sale (sale_id),
constraint make_transaction__item_fk
	foreign key (item_id)
    references items (item_id)
);



create table inventory(
storeage_id 	int 	not null	primary key 	auto_increment,
item_id int,
on_hands	 int,

constraint inventory_item_fk
	foreign key (item_id)
    references items(item_id)
);






-- ( , '', '', '', '', '', ,'')
insert into Employee(employee_id, employee_first, employee_last, street, city, state, zip_code, date_start) values
(1, 'John', 'Smith', '2500 S. Main St.', 'Aurora', 'CO', 80013, now()),
(2, 'Hana', 'Monana', '5500 E. Fake St.', 'Aurora', 'CO', 80017,now()),
(3, 'Jimmy', 'john', '1234 Sub St.', 'Aurora', 'CO', 80014, now())
;

insert into sale(sale_id, employee_id) values
(1,1)
;

-- ( , '', , '')
insert into items(item_id, item_name, item_price, item_description) values
(1, 'Icecream Sandwich', 2.50, 'A delicious and nutricious Icecream Sandwiched between two chocolate cookies.'),
(2, 'Candy Bar', 0.80 , 'Chocolate nugety Candy Bar.'),
(3, 'Emoji Keychain', 3.00, 'Emoji plush keychain.'),
(4, 'Chapstic', 1.00, 'Keep lips soft')
;

insert into make_transaction(transacton_id , sale_id, item_id, quantity) values
(1,1,1,1),
(2,1,2,2)
;



insert into inventory(storeage_id, item_id, on_hands) values
(1,1,20),
(2,2,50),
(3,3,44),
(4,4,55)
;


-- grant user
grant select, update, insert, delete
on *
to dave
identified by 'Hall9000';
