---- PERFIS ----
insert into perfil (id,nome) values (1,'Dono');
insert into perfil (id,nome) values (2,'Cliente');

---- USUARIO 1 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values ('de6762a9-e373-4a05-a6bb-d345a759b26f', 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');

insert into login (id, matricula, senha) values ('c303266f-9d32-4dde-8f4c-d8ee13b24ae9', 'us0001', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('cf05db14-7993-4564-bff9-c258b5c7387c', 'Thiago Motta', 'thiago@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT id FROM endereco WHERE cidade = 'São Gonçalo'),
        (SELECT id FROM login WHERE matricula = 'us0001'));

---- USUARIO 2 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values ('229f4bff-c6be-4008-8911-bef0c79735e2', 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302', 0, '22021001');

insert into login (id, matricula, senha) values ('dbc8695f-fe37-4741-9c6d-7bf5e96dfe6d', 'us0002', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('b48bc2dc-fd87-462d-a8a6-6e74674d0338', 'Carla Rodrigues', 'carla.rodrigues@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT id FROM endereco WHERE cidade = 'Rio de Janeiro'),
        (SELECT id FROM login WHERE matricula = 'us0002'));

---- USUARIO 3 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values ('78c76348-d821-4808-ac67-a5e599191b23', 'São Paulo', 'Pinheiros', 'SP', 'Rua dos Pinheiros', '1340', 'Conjunto 25', 0, '05422002');

insert into login (id, matricula, senha) values ('2b84cf36-9333-42af-b013-eccec84a2270', 'us0003', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('602a4056-68d0-44f0-8284-14b0cf7a75b6', 'Rafael Santos', 'rafael.santos@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT id FROM endereco WHERE cidade = 'São Paulo'),
        (SELECT id FROM login WHERE matricula = 'us0003'));

---- USUARIO 4 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values ('1524d4a4-3f99-46d2-92cb-890f5e690f74', 'Belo Horizonte', 'Savassi', 'MG', 'Rua Pernambuco', '1322', null, 0, '30130151');

insert into login (id, matricula, senha) values ('2de80d8c-3665-4beb-858a-d5f242b822be', 'us0004', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('60127300-b56a-4394-a208-d9ef8eb864c7', 'Juliana Mendes', 'juliana.mendes@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Belo Horizonte'),
        (SELECT id FROM login WHERE matricula = 'us0004'));