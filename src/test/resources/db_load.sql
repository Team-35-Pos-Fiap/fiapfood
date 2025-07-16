---- PERFIS ----
insert into perfil (id,nome) values (1,'Dono');
insert into perfil (id,nome) values (2,'Cliente');

---- USUARIO 1 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('de6762a9-e373-4a05-a6bb-d345a759b26f', 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8',  '24455450');

insert into login (id, matricula, senha) values ('c303266f-9d32-4dde-8f4c-d8ee13b24ae9', 'us0001', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('cf05db14-7993-4564-bff9-c258b5c7387c', 'Thiago Motta', 'thiago@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT id FROM endereco WHERE cidade = 'São Gonçalo'),
        (SELECT id FROM login WHERE matricula = 'us0001'));

---- USUARIO 2 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('229f4bff-c6be-4008-8911-bef0c79735e2', 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302',  '22021001');

insert into login (id, matricula, senha) values ('dbc8695f-fe37-4741-9c6d-7bf5e96dfe6d', 'us0002', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('b48bc2dc-fd87-462d-a8a6-6e74674d0338', 'Carla Rodrigues', 'carla.rodrigues@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT id FROM endereco WHERE cidade = 'Rio de Janeiro'),
        (SELECT id FROM login WHERE matricula = 'us0002'));

---- USUARIO 3 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('78c76348-d821-4808-ac67-a5e599191b23', 'São Paulo', 'Pinheiros', 'SP', 'Rua dos Pinheiros', '1340', 'Conjunto 25',  '05422002');

insert into login (id, matricula, senha) values ('2b84cf36-9333-42af-b013-eccec84a2270', 'us0003', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('602a4056-68d0-44f0-8284-14b0cf7a75b6', 'Rafael Santos', 'rafael.santos@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT id FROM endereco WHERE cidade = 'São Paulo'),
        (SELECT id FROM login WHERE matricula = 'us0003'));

---- USUARIO 4 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('1524d4a4-3f99-46d2-92cb-890f5e690f74', 'Belo Horizonte', 'Savassi', 'MG', 'Rua Pernambuco', '1322', null,  '30130151');

insert into login (id, matricula, senha) values ('2de80d8c-3665-4beb-858a-d5f242b822be', 'us0004', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values ('60127300-b56a-4394-a208-d9ef8eb864c7', 'Juliana Mendes', 'juliana.mendes@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Belo Horizonte'),
        (SELECT id FROM login WHERE matricula = 'us0004'));

----- CARDAPIO ----
INSERT INTO cardapio (id, nome,descricao,preco,disponivel_Apenas_Restaurante,foto_Prato) VALUES
('2d2e0bad-347d-40c6-824f-b7d7bbc65449' , 'Feijoada', 'Feijoada completa com acompanhamentos', 39.90, TRUE, '/var/lib/docker/volumes/images/_datafeijoada.jpg'),
('49dc02ae-4dea-4b34-923d-ae9e066b9c27' , 'Lasanha', 'Lasanha de carne com queijo', 29.50, TRUE, '/var/lib/docker/volumes/images/_datalasanha.jpg'),
('38bcd71b-62db-4923-a8a9-3803849de2c4' , 'Salada Caesar', 'Salada Caesar tradicional', 19.90, FALSE, '/var/lib/docker/volumes/images/_datacaesar.jpg'),
('7ebf35e5-0799-4ba0-8834-58af21a34da6' , 'Pizza Margherita', 'Pizza com tomate, mussarela e manjericão', 42.00, TRUE, '/var/lib/docker/volumes/images/_datamargherita.jpg'),
('545d57f2-0d46-4a12-bdc7-dce9a9c52e95', 'Hambúrguer Artesanal', 'Hambúrguer com batata frita', 27.90, TRUE, '/var/lib/docker/volumes/images/_datahamburguer.jpg'),
('ba6f987b-3811-4f41-ae88-c9a6b8869fca' , 'Risoto de Camarão', 'Risoto cremoso de camarão', 49.90, FALSE, 'risoto.jpg'),
('70d2d875-243b-4314-9963-29bb18fb4a19', 'Strogonoff de Frango', 'Strogonoff com arroz e batata palha', 25.00, TRUE, '/var/lib/docker/volumes/images/_datastrogonoff.jpg'),
('c0c9c7c2-e5e7-4a2d-8603-1cc6c3c1ae6c' , 'Sushi Combo', 'Combo de sushi variado', 59.90, FALSE, '/var/lib/docker/volumes/images/_datasushi.jpg'),
('a5a27a5b-b8d5-4831-afec-bf56d8980de4' , 'Tapioca', 'Tapioca recheada com queijo e coco', 12.00, TRUE, '/var/lib/docker/volumes/images/_datatapioca.jpg'),
('f2c4ffa7-bb28-4f0a-a028-e5cae3c97b1f' , 'Bolo de Chocolate', 'Bolo de chocolate com cobertura', 15.00, FALSE, '/var/lib/docker/volumes/images/_databolo.jpg');

----- RESTAURANTE 1----
insert into endereco (id, cidade, bairro, estado, endereco, cep)
values ('0f324a42-4e15-4b40-8001-f531c6b306dc', 'Manaus', 'Educandos', 'AM', 'Beco das Rosas',  '69070061');

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    ('40d5955e-c0bd-41da-b434-e46fa69bda14' , 'Restaurante Sabor Brasil', (SELECT max(ID) from endereco WHERE cidade = 'Manaus'), 'Brasileira', '2024-06-01 11:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0003'));

----- RESTAURANTE 2----
insert into endereco (id, cidade, bairro, estado, endereco,  cep)
values ('4cbd1ae7-163f-4f85-9c66-665f9f665840', 'Belo Horizonte', 'Ouro Preto', 'MG', 'Rua Conceição do Mato Dentro',  '31310240');

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    ('fc8a9535-d6be-465f-8bf1-d9885e91c91d' , 'La Bella Pasta', '4cbd1ae7-163f-4f85-9c66-665f9f665840', 'Italiana', '2024-06-01 12:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0004'));

----- RESTAURANTE 3----
insert into endereco (id, cidade, bairro, estado, endereco,  cep)
values ('f8ff8bb3-c028-4ac1-91e2-70fd3c6b0115', 'São Paulo', 'Jardim Castelo', 'SP', 'Rua Filipe Nicoletti',  '03728240');

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    ('a72181a6-7699-4686-a5ec-1a0431764e62', 'Sushi House', 'f8ff8bb3-c028-4ac1-91e2-70fd3c6b0115', 'Japonesa', '2024-06-01 18:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0005'));

----- RESTAURANTE 4----
insert into endereco (id, cidade, bairro, estado, endereco,   cep)
values ('5a4ff077-8399-42a4-a281-2c0642cbfbe1', 'Curitiba', 'Tatuquara', 'PR', 'Rua Paulo Vilimavicius',    '81480133');

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    ('21adec7d-d4f7-4999-a4dd-eaf0c242b3bd', 'Churrascaria Gaúcha', '5a4ff077-8399-42a4-a281-2c0642cbfbe1', 'Churrasco', '2024-06-01 10:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0006'));

----- RESTAURANTE 5----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('10914a8c-059e-4953-a1f5-909dffc2b316', 'Brasília', 'Santa Maria', 'DF', 'Quadra CL', '410', 'Bloco E',  '72510245');

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    ('9a7f3e70-8343-47e9-8fb0-2253cb03575f', 'Veggie Life', '10914a8c-059e-4953-a1f5-909dffc2b316', 'Vegetariana', '2024-06-01 09:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0001'));
