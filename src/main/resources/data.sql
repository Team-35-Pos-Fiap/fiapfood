insert into perfil (nome) values ('Dono');
insert into perfil (nome) values ('Cliente');

insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Thiago Motta', 'thiago@fiapfood.com', 1, current_timestamp, null, 1);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 2', 'usuario2@fiapfood.com', 1, current_timestamp, null, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 3', 'usuario3@fiapfood.com', 0, current_timestamp, current_timestamp, 1);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 4', 'usuario4@fiapfood.com', 0, current_timestamp, current_timestamp, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 5', 'usuario5@fiapfood.com', 1, current_timestamp, null, 1);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 6', 'usuario6@fiapfood.com', 1, current_timestamp, null, 1);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 7', 'usuario7@fiapfood.com', 0, current_timestamp, current_timestamp, 1);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 8', 'usuario8@fiapfood.com', 0, current_timestamp, current_timestamp, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 9', 'usuario9@fiapfood.com', 1, current_timestamp, null, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 10', 'usuario10@fiapfood.com', 1, current_timestamp, null, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 11', 'usuario11@fiapfood.com', 0, current_timestamp, current_timestamp, 2);
insert into usuario (nome, email, ativo, data_criacao, data_atualizacao, id_perfil) values ('Usuario 12', 'usuario12@fiapfood.com', 0, current_timestamp, current_timestamp, 2);

insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (1, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (2, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (3, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (4, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (5, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (6, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (7, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (8, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (9, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (10, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (11, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');
insert into endereco (id_usuario, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep) 
values (12, 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', null, null, 0, '24455450');

insert into login (id_usuario, matricula, senha) values (1, 'us0001', '123');
insert into login (id_usuario, matricula, senha) values (2, 'us0002', '123');
insert into login (id_usuario, matricula, senha) values (3, 'us0003', '123');
insert into login (id_usuario, matricula, senha) values (4, 'us0004', '123');
insert into login (id_usuario, matricula, senha) values (5, 'us0005', '123');
insert into login (id_usuario, matricula, senha) values (6, 'us0006', '123');
insert into login (id_usuario, matricula, senha) values (7, 'us0007', '123');
insert into login (id_usuario, matricula, senha) values (8, 'us0008', '123');
insert into login (id_usuario, matricula, senha) values (9, 'us0009', '123');
insert into login (id_usuario, matricula, senha) values (10, 'us0010', '123');
insert into login (id_usuario, matricula, senha) values (11, 'us0011', '123');
insert into login (id_usuario, matricula, senha) values (12, 'us0012', '123');