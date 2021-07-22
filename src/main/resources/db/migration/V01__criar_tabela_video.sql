create table video (
	id bigint not null primary key auto_increment,
	titulo varchar(50) not null,
	descricao varchar(500) not null, 
	url varchar(255) not null
);