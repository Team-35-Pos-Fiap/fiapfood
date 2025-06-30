---- PERFIS ----
insert into perfil (nome) values ('Dono');
insert into perfil (nome) values ('Cliente');

---- USUARIO 1 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0001', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Thiago Motta', 'thiago@fiapfood.com', 1, current_timestamp, null, 1,
    (SELECT id FROM endereco WHERE cidade = 'São Gonçalo'),
    (SELECT id FROM login WHERE matricula = 'us0001'));

---- USUARIO 2 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302', 0, '22021001');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0002', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Carla Rodrigues', 'carla.rodrigues@fiapfood.com', 1, current_timestamp, null, 2,
    (SELECT id FROM endereco WHERE cidade = 'Rio de Janeiro'),
    (SELECT id FROM login WHERE matricula = 'us0002'));

---- USUARIO 3 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'São Paulo', 'Pinheiros', 'SP', 'Rua dos Pinheiros', '1340', 'Conjunto 25', 0, '05422002');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0003', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Rafael Santos', 'rafael.santos@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT id FROM endereco WHERE cidade = 'São Paulo'),
        (SELECT id FROM login WHERE matricula = 'us0003'));

---- USUARIO 4 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Belo Horizonte', 'Savassi', 'MG', 'Rua Pernambuco', '1322', null, 0, '30130151');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0004', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Juliana Mendes', 'juliana.mendes@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Belo Horizonte'),
        (SELECT id FROM login WHERE matricula = 'us0004'));

---- USUARIO 5 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Brasília', 'Asa Sul', 'DF', 'SQS 308', null, 'Bloco C Apto 303', 1, '70355530');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0005', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Marcelo Alves', 'marcelo.alves@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT id FROM endereco WHERE cidade = 'Brasília'),
        (SELECT id FROM login WHERE matricula = 'us0005'));

---- USUARIO 6 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Curitiba', 'Batel', 'PR', 'Alameda Dr. Carlos de Carvalho', '555', 'Sala 1201', 0, '80430180');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0006', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Amanda Costa', 'amanda.costa@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT id FROM endereco WHERE cidade = 'Curitiba'),
        (SELECT id FROM login WHERE matricula = 'us0006'));

---- USUARIO 7 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Salvador', 'Barra', 'BA', 'Avenida Oceânica', '2135', null, 0, '40140130');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0007', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Bruno Oliveira', 'bruno.oliveira@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT id FROM endereco WHERE cidade = 'Salvador'),
        (SELECT id FROM login WHERE matricula = 'us0007'));

---- USUARIO 8 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Recife', 'Boa Viagem', 'PE', 'Avenida Boa Viagem', '3320', 'Apto 1802', 0, '51030300');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0008', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Patricia Lima', 'patricia.lima@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Recife'),
        (SELECT id FROM login WHERE matricula = 'us0008'));

---- USUARIO 9 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Fortaleza', 'Meireles', 'CE', 'Avenida Beira Mar', '850', null, 0, '60165121');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0009', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Fernando Gomes', 'fernando.gomes@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT id FROM endereco WHERE cidade = 'Fortaleza'),
        (SELECT id FROM login WHERE matricula = 'us0009'));

---- USUARIO 10 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Porto Alegre', 'Moinhos de Vento', 'RS', 'Rua Padre Chagas', '342', 'Casa', 0, '90570080');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0010', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Daniela Pereira', 'daniela.pereira@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT id FROM endereco WHERE cidade = 'Porto Alegre'),
        (SELECT id FROM login WHERE matricula = 'us0010'));

---- USUARIO 11 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Manaus', 'Adrianópolis', 'AM', 'Avenida André Araújo', null, 'Condomínio Tulipas', 1, '69057025');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0011', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Ricardo Souza', 'ricardo.souza@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Manaus'),
        (SELECT id FROM login WHERE matricula = 'us0011'));

---- USUARIO 12 ----
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (RANDOM_UUID(), 'Florianópolis', 'Jurerê Internacional', 'SC', 'Avenida dos Búzios', '1780', 'Casa 15', 0, '88053300');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0012', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Luciana Ferreira', 'luciana.ferreira@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT id FROM endereco WHERE cidade = 'Florianópolis'),
        (SELECT id FROM login WHERE matricula = 'us0012'));

----- CARDAPIO ----
INSERT INTO cardapio VALUES
(RANDOM_UUID(), 'Feijoada', 'Feijoada completa com acompanhamentos', 39.90, TRUE, '/var/lib/docker/volumes/images/_datafeijoada.jpg'),
(RANDOM_UUID(), 'Lasanha', 'Lasanha de carne com queijo', 29.50, TRUE, '/var/lib/docker/volumes/images/_datalasanha.jpg'),
(RANDOM_UUID(), 'Salada Caesar', 'Salada Caesar tradicional', 19.90, FALSE, '/var/lib/docker/volumes/images/_datacaesar.jpg'),
(RANDOM_UUID(), 'Pizza Margherita', 'Pizza com tomate, mussarela e manjericão', 42.00, TRUE, '/var/lib/docker/volumes/images/_datamargherita.jpg'),
(RANDOM_UUID(), 'Hambúrguer Artesanal', 'Hambúrguer com batata frita', 27.90, TRUE, '/var/lib/docker/volumes/images/_datahamburguer.jpg'),
(RANDOM_UUID(), 'Risoto de Camarão', 'Risoto cremoso de camarão', 49.90, FALSE, 'risoto.jpg'),
(RANDOM_UUID(), 'Strogonoff de Frango', 'Strogonoff com arroz e batata palha', 25.00, TRUE, '/var/lib/docker/volumes/images/_datastrogonoff.jpg'),
(RANDOM_UUID(), 'Sushi Combo', 'Combo de sushi variado', 59.90, FALSE, '/var/lib/docker/volumes/images/_datasushi.jpg'),
(RANDOM_UUID(), 'Tapioca', 'Tapioca recheada com queijo e coco', 12.00, TRUE, '/var/lib/docker/volumes/images/_datatapioca.jpg'),
(RANDOM_UUID(), 'Bolo de Chocolate', 'Bolo de chocolate com cobertura', 15.00, FALSE, '/var/lib/docker/volumes/images/_databolo.jpg');

----- RESTAURANTE ----

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (RANDOM_UUID(), 'Restaurante Sabor Brasil', (SELECT id FROM endereco WHERE cidade = 'Manaus'), 'Brasileira', '2024-06-01 11:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0003'));

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (RANDOM_UUID(), 'La Bella Pasta', (SELECT id FROM endereco WHERE cidade = 'Belo Horizonte'), 'Italiana', '2024-06-01 12:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0004'));

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (RANDOM_UUID(), 'Sushi House', (SELECT id FROM endereco WHERE cidade = 'São Paulo'), 'Japonesa', '2024-06-01 18:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0005'));

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (RANDOM_UUID(), 'Churrascaria Gaúcha', (SELECT id FROM endereco WHERE cidade = 'Curitiba'), 'Churrasco', '2024-06-01 10:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0006'));

INSERT INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (RANDOM_UUID(), 'Veggie Life', (SELECT id FROM endereco WHERE cidade = 'Brasília'), 'Vegetariana', '2024-06-01 09:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0001'));