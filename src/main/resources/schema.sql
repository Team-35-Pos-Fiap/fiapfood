create table perfil (
	id int not null auto_increment,
	nome varchar(50) not null,
	primary key(id)
);

create table usuario (
	id int not null auto_increment,
	nome varchar(150) not null,
	email varchar(70) not null,
	ativo tinyint(1) not null default 1,
	data_criacao datetime not null,
	data_atualizacao datetime null,
	id_perfil int not null,
	primary key(id),
	foreign key(id_perfil) references perfil(id)
);

create table endereco (
	id_usuario int not null,
	cidade varchar(100) not null,
	bairro varchar(100) not null,
	estado varchar(100) not null,
	endereco varchar(150) not null,
	numero int null,
	complemento varchar(150) null,
	sem_numero tinyint(1) not null default 1,
	cep varchar(100) not null,
	primary key(id_usuario),
	foreign key(id_usuario) references usuario(id)
);

create table login (
	id_usuario int not null,
	matricula varchar(6) not null,
	senha varchar(10) not null,
	primary key(id_usuario),
	foreign key(id_usuario) references usuario(id)
);