create table categoria (
	id bigint not null primary key auto_increment,
	titulo varchar(50) not null,
	cor varchar(12) not null	
);

insert into categoria (titulo, cor) values ('LIVRE', '#FFFFFF');