create table perfil (
	id int not null auto_increment,
	nome varchar(50) not null,
	data_criacao date not null,
	data_inativacao date null,
	primary key(id)
);

create table endereco (
    id varchar(36) not null,
    cidade varchar(100) not null,
    bairro varchar(100) not null,
    estado varchar(100) not null,
    endereco varchar(150) not null,
    numero int null,
    complemento varchar(150) null,
    cep varchar(100) not null,
    primary key(id)
);

create table login (
    id varchar(36) not null,
    matricula varchar(6) not null,
    senha varchar(10) not null,
    primary key(id)
);

create table usuario (
    id varchar(36) not null,
	nome varchar(150) not null,
	email varchar(70) not null,
	ativo tinyint(1) not null default 1,
	data_criacao datetime not null,
	data_atualizacao datetime null,
	id_perfil int not null,
    id_endereco varchar(36) not null,
    id_login varchar(36) not null,
	primary key(id),
	foreign key(id_perfil) references perfil(id),
	foreign key(id_endereco) references endereco(id),
	foreign key(id_login) references login(id)
);

create table tipo_culinaria (
	id int not null auto_increment,
	nome varchar(50) not null,
	primary key(id)
);

create table restaurante (
    id varchar(36) not null,
    nome varchar(255) not null,
	ativo tinyint(1) not null default 1,
    id_endereco varchar(36) not null,
    id_usuario varchar(36) not null,
    id_tipo_culinaria int not null,
    primary key (id),
    foreign key(id_endereco) references endereco(id),
	foreign key(id_usuario) references usuario(id),
	foreign key(id_tipo_culinaria) references tipo_culinaria(id)
);

create table atendimento (
	id_atendimento varchar(36) not null,
	dia enum('SEGUNDA_FEIRA', 'TERÇA_FEIRA', 'QUARTA_FEIRA', 'QUINTA_FEIRA', 'SEXTA_FEIRA', 'SÁBADO', 'DOMINGO'),
	inicio_atendimento time not null,
	termino_atendimento time not null,
	id_restaurante varchar(36) not null,	  
	primary key (id_atendimento),
	foreign key(id_restaurante) references restaurante(id)
);

create table imagem (
	id uuid not null,
	nome varchar(50) not null,
	tipo varchar(20) not null,
	conteudo blob not null,
	primary key(id)
);

create table item (
	id varchar(36) not null,
	nome varchar(255) not null,
    descricao varchar(255) null,
 	preco decimal(9, 2) not null,
 	disponivel_consumo_presencial tinyint(1) not null default 0,
	disponivel tinyint(1) not null default 1,
	id_imagem varchar(36) not null,
	id_restaurante varchar(36) not null,	  
	primary key (id),
    foreign key(id_imagem) references imagem(id),
	foreign key(id_restaurante) references restaurante(id)
);