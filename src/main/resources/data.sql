---- PERFIS ----
INSERT IGNORE INTO perfil (nome) values ('Dono');
INSERT IGNORE INTO perfil (nome) values ('Cliente');

---- USUARIO 1 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'São Gonçalo', 'Nova Cidade', 'Rio de Janeiro', 'Rua Aquidabã', '79', 'Casa 8', 1, '24455450');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0001', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Thiago Motta', 'thiago@fiapfood.com', 1, current_timestamp, null, 1,
    (SELECT max(ID) from endereco WHERE cidade = 'São Gonçalo'),
    (SELECT max(ID) FROM login WHERE matricula = 'us0001'));

---- USUARIO 2 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302', 0, '22021001');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0002', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Carla Rodrigues', 'carla.rodrigues@fiapfood.com', 1, current_timestamp, null, 2,
    (SELECT max(ID) from endereco WHERE cidade = 'Rio de Janeiro'),
    (SELECT max(ID) FROM login WHERE matricula = 'us0002'));

---- USUARIO 3 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'São Paulo', 'Pinheiros', 'SP', 'Rua dos Pinheiros', '1340', 'Conjunto 25', 0, '05422002');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0003', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Rafael Santos', 'rafael.santos@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT max(ID) from endereco WHERE cidade = 'São Paulo'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0003'));

---- USUARIO 4 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Belo Horizonte', 'Savassi', 'MG', 'Rua Pernambuco', '1322', null, 0, '30130151');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0004', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Juliana Mendes', 'juliana.mendes@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Belo Horizonte'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0004'));

---- USUARIO 5 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Brasília', 'Asa Sul', 'DF', 'SQS 308', null, 'Bloco C Apto 303', 1, '70355530');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0005', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Marcelo Alves', 'marcelo.alves@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT max(ID) from endereco WHERE cidade = 'Brasília'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0005'));

---- USUARIO 6 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Curitiba', 'Batel', 'PR', 'Alameda Dr. Carlos de Carvalho', '555', 'Sala 1201', 0, '80430180');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0006', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Amanda Costa', 'amanda.costa@fiapfood.com', 1, current_timestamp, null, 1,
        (SELECT max(ID) from endereco WHERE cidade = 'Curitiba'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0006'));

---- USUARIO 7 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Salvador', 'Barra', 'BA', 'Avenida Oceânica', '2135', null, 0, '40140130');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0007', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Bruno Oliveira', 'bruno.oliveira@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
        (SELECT max(ID) from endereco WHERE cidade = 'Salvador'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0007'));

---- USUARIO 8 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Recife', 'Boa Viagem', 'PE', 'Avenida Boa Viagem', '3320', 'Apto 1802', 0, '51030300');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0008', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Patricia Lima', 'patricia.lima@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Recife'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0008'));

---- USUARIO 9 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Fortaleza', 'Meireles', 'CE', 'Avenida Beira Mar', '850', null, 0, '60165121');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0009', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Fernando Gomes', 'fernando.gomes@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Fortaleza'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0009'));

---- USUARIO 10 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Porto Alegre', 'Moinhos de Vento', 'RS', 'Rua Padre Chagas', '342', 'Casa', 0, '90570080');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0010', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Daniela Pereira', 'daniela.pereira@fiapfood.com', 1, current_timestamp, null, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Porto Alegre'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0010'));

---- USUARIO 11 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Manaus', 'Adrianópolis', 'AM', 'Avenida André Araújo', null, 'Condomínio Tulipas', 1, '69057025');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0011', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Ricardo Souza', 'ricardo.souza@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Manaus'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0011'));

---- USUARIO 12 ----
INSERT IGNORE INTO endereco (id, cidade, bairro, estado, endereco, numero, complemento, sem_numero, cep)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Florianópolis', 'Jurerê Internacional', 'SC', 'Avenida dos Búzios', '1780', 'Casa 15', 0, '88053300');

INSERT IGNORE INTO login (id, matricula, senha) values (UNHEX(REPLACE(UUID(), '-', '')) , 'us0012', '123');

INSERT IGNORE INTO usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (UNHEX(REPLACE(UUID(), '-', '')) , 'Luciana Ferreira', 'luciana.ferreira@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
        (SELECT max(ID) from endereco WHERE cidade = 'Florianópolis'),
        (SELECT max(ID) FROM login WHERE matricula = 'us0012'));

----- CARDAPIO ----
INSERT IGNORE INTO cardapio (id, nome,descricao,preco,disponivel_Apenas_Restaurante,foto_Prato) VALUES
(UNHEX(REPLACE(UUID(), '-', '')) , 'Feijoada', 'Feijoada completa com acompanhamentos', 39.90, TRUE, '/var/lib/docker/volumes/images/_datafeijoada.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Lasanha', 'Lasanha de carne com queijo', 29.50, TRUE, '/var/lib/docker/volumes/images/_datalasanha.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Salada Caesar', 'Salada Caesar tradicional', 19.90, FALSE, '/var/lib/docker/volumes/images/_datacaesar.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Pizza Margherita', 'Pizza com tomate, mussarela e manjericão', 42.00, TRUE, '/var/lib/docker/volumes/images/_datamargherita.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Hambúrguer Artesanal', 'Hambúrguer com batata frita', 27.90, TRUE, '/var/lib/docker/volumes/images/_datahamburguer.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Risoto de Camarão', 'Risoto cremoso de camarão', 49.90, FALSE, 'risoto.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Strogonoff de Frango', 'Strogonoff com arroz e batata palha', 25.00, TRUE, '/var/lib/docker/volumes/images/_datastrogonoff.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Sushi Combo', 'Combo de sushi variado', 59.90, FALSE, '/var/lib/docker/volumes/images/_datasushi.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Tapioca', 'Tapioca recheada com queijo e coco', 12.00, TRUE, '/var/lib/docker/volumes/images/_datatapioca.jpg'),
(UNHEX(REPLACE(UUID(), '-', '')) , 'Bolo de Chocolate', 'Bolo de chocolate com cobertura', 15.00, FALSE, '/var/lib/docker/volumes/images/_databolo.jpg');

----- RESTAURANTE ----

INSERT IGNORE INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')) , 'Restaurante Sabor Brasil', (SELECT max(ID) from endereco WHERE cidade = 'Manaus'), 'Brasileira', '2024-06-01 11:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0003'));

INSERT IGNORE INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')) , 'La Bella Pasta', (SELECT max(ID) from endereco WHERE cidade = 'Belo Horizonte'), 'Italiana', '2024-06-01 12:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0004'));

INSERT IGNORE INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')) , 'Sushi House', (SELECT max(ID) from endereco WHERE cidade = 'São Paulo'), 'Japonesa', '2024-06-01 18:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0005'));

INSERT IGNORE INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')) , 'Churrascaria Gaúcha', (SELECT max(ID) from endereco WHERE cidade = 'Curitiba'), 'Churrasco', '2024-06-01 10:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0006'));

INSERT IGNORE INTO restaurante (id, nome, id_endereco, tipo_cozinha, horario_funcionamento, id_dono_restaurante) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')) , 'Veggie Life', (SELECT max(ID) from endereco WHERE cidade = 'Brasília'), 'Vegetariana', '2024-06-01 09:00:00', (SELECT u.id FROM usuario u , login l WHERE u.id_login = l.id and l.matricula = 'us0001'));
