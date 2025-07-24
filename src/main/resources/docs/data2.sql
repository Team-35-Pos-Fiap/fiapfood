---- PERFIS ----

insert into perfil (nome, data_criacao, data_inativacao) values ('Dono', current_date(), null);
insert into perfil (nome, data_criacao, data_inativacao) values ('Cliente', current_date(), null);

---- USUARIO 1 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'São Gonçalo', 'Centro', 'Rio de Janeiro', 'Rua 1', '10', 'Casa 25', '24455486');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0001', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'João Motta', 'joao@fiapfood.com', 1, current_timestamp, null, 1,
  (select id from endereco where cidade = 'São Gonçalo'),
  (select id from login where matricula = 'us0001'));

---- USUARIO 2 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302', '22021001');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0002', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Carla Rodrigues', 'carla.rodrigues@fiapfood.com', 1, current_timestamp, null, 2,
  (select id from endereco where cidade = 'Rio de Janeiro'),
  (select id from login where matricula = 'us0002'));

---- USUARIO 3 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'São Paulo', 'Pinheiros', 'SP', 'Rua dos Pinheiros', '1340', 'Conjunto 25', '05422002');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0003', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Rafael Santos', 'rafael.santos@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
    (select id from endereco where cidade = 'São Paulo'),
    (select id from login where matricula = 'us0003'));

---- USUARIO 4 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Belo Horizonte', 'Savassi', 'MG', 'Rua Pernambuco', '1322', null, '30130151');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0004', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Juliana Mendes', 'juliana.mendes@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
    (select id from endereco where cidade = 'Belo Horizonte'),
    (select id from login where matricula = 'us0004'));

---- USUARIO 5 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Brasília', 'Asa Sul', 'DF', 'SQS 308', null, 'Bloco C Apto 303', '70355530');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0005', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Marcelo Alves', 'marcelo.alves@fiapfood.com', 1, current_timestamp, null, 1,
    (select id from endereco where cidade = 'Brasília'),
    (select id from login where matricula = 'us0005'));

---- USUARIO 6 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Curitiba', 'Batel', 'PR', 'Alameda Dr. Carlos de Carvalho', '555', 'Sala 1201', '80430180');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0006', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Amanda Costa', 'amanda.costa@fiapfood.com', 1, current_timestamp, null, 1,
    (select id from endereco where cidade = 'Curitiba'),
    (select id from login where matricula = 'us0006'));

---- USUARIO 7 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Salvador', 'Barra', 'BA', 'Avenida Oceânica', '2135', null, '40140130');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0007', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Bruno Oliveira', 'bruno.oliveira@fiapfood.com', 0, current_timestamp, current_timestamp, 1,
    (select id from endereco where cidade = 'Salvador'),
    (select id from login where matricula = 'us0007'));

---- USUARIO 8 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Recife', 'Boa Viagem', 'PE', 'Avenida Boa Viagem', '3320', 'Apto 1802', '51030300');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0008', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Patricia Lima', 'patricia.lima@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
    (select id from endereco where cidade = 'Recife'),
    (select id from login where matricula = 'us0008'));

---- USUARIO 9 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Fortaleza', 'Meireles', 'CE', 'Avenida Beira Mar', '850', null, '60165121');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0009', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Fernando Gomes', 'fernando.gomes@fiapfood.com', 1, current_timestamp, null, 2,
    (select id from endereco where cidade = 'Fortaleza'),
    (select id from login where matricula = 'us0009'));

---- USUARIO 10 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Porto Alegre', 'Moinhos de Vento', 'RS', 'Rua Padre Chagas', '342', 'Casa', '90570080');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0010', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Daniela Pereira', 'daniela.pereira@fiapfood.com', 1, current_timestamp, null, 2,
    (select id from endereco where cidade = 'Porto Alegre'),
    (select id from login where matricula = 'us0010'));

---- USUARIO 11 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Manaus', 'Adrianópolis', 'AM', 'Avenida André Araújo', null, 'Condomínio Tulipas', '69057025');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0011', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Ricardo Souza', 'ricardo.souza@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
    (select id from endereco where cidade = 'Manaus'),
    (select id from login where matricula = 'us0011'));

---- USUARIO 12 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values (RANDOM_UUID(), 'Florianópolis', 'Jurerê Internacional', 'SC', 'Avenida dos Búzios', '1780', 'Casa 15', '88053300');

insert into login (id, matricula, senha) values (RANDOM_UUID(), 'us0012', '123');

insert into usuario (id, nome, email, ativo, data_criacao, data_atualizacao, id_perfil, id_endereco, id_login)
values (RANDOM_UUID(), 'Luciana Ferreira', 'luciana.ferreira@fiapfood.com', 0, current_timestamp, current_timestamp, 2,
    (select id from endereco where cidade = 'Florianópolis'),
    (select id from login where matricula = 'us0012'));

insert into tipo_culinaria (nome) values ('Brasileira');
insert into tipo_culinaria (nome) values ('Mexicana');
insert into tipo_culinaria (nome) values ('Japonesa');

---- RESTAURANTE 1 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('99013b6d-bda0-4325-a066-c887c0d63af5', 'Rio de Janeiro', 'Copacabana', 'RJ', 'Avenida Atlântica', '1500', 'Apto 302', '22021001');
   
insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'Restaurante Sabor Brasil', 1,
	  '99013b6d-bda0-4325-a066-c887c0d63af5', 
	  (select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0001'), 2);

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Sabor Brasil'));

---- RESTAURANTE 2 ----    

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('6528fab3-a54b-4ece-9aea-ed6cd779666b', 'Rio de Janeiro', 'Centro', 'RJ', 'Avenida Rio Branco 1', '1', null, '22021002');

insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'La Bella Pasta', 1, 
		'6528fab3-a54b-4ece-9aea-ed6cd779666b', 
		(select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0001'), 3);
		
insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'La Bella Pasta'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'La Bella Pasta'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'La Bella Pasta'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'La Bella Pasta'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'La Bella Pasta'));		

---- RESTAURANTE 3 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('9dc35dd6-1a98-4777-9612-0f389508de4c', 'Rio de Janeiro', 'Tijuca', 'RJ', 'Avenida Domingos Damasceno', '7', 'Casa 1', '22021234');

insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'Casa de carnes', 0, 
		'9dc35dd6-1a98-4777-9612-0f389508de4c', 
		(select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0005'), 1);
			
insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Casa de carnes'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Casa de carnes'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Casa de carnes'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Casa de carnes'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Casa de carnes'));
		
---- RESTAURANTE 4 ----

insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('be301a06-6f84-4059-88a9-df8a41332b93', 'Rio de Janeiro', 'Meier', 'RJ', 'Avenida Maricá', '234', 'Lote 1', '22021000');

insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'Restaurante Cantinho do Sushi', 1, 
		'be301a06-6f84-4059-88a9-df8a41332b93', 
		(select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0005'), 3);

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));
		
---- RESTAURANTE 5 ---- 
   
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('053cbd14-287a-4e6e-b46b-17e9314637d3', 'Rio de Janeiro', 'Botafogo', 'RJ', 'Avenida Botafogo', '15', null, '22021007');

insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'Restaurante dos Nachos', 0, 
		'053cbd14-287a-4e6e-b46b-17e9314637d3', 
		(select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0007'), 2);
	
insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante dos Nachos'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante dos Nachos'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante dos Nachos'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante dos Nachos'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante dos Nachos'));
		
---- RESTAURANTE 6 ---- 
   
insert into endereco (id, cidade, bairro, estado, endereco, numero, complemento, cep)
values ('71208530-0b42-44f2-8d24-79ca01bf3f3a', 'Rio de Janeiro', 'Flamengo', 'RJ', 'Avenida Flamengo', '10', null, '22021021');

insert into restaurante (id, nome, ativo, id_endereco, id_usuario, id_tipo_culinaria) 
values (RANDOM_UUID(), 'Restaurante Boi na Brasa', 1, 
		'71208530-0b42-44f2-8d24-79ca01bf3f3a', 
		(select u.id from usuario u , login l where u.id_login = l.id and l.matricula = 'us0007'), 1);

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEGUNDA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Boi na Brasa'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'TERÇA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Boi na Brasa'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUARTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Boi na Brasa'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'QUINTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Boi na Brasa'));

insert into atendimento(id_atendimento, dia, inicio_atendimento, termino_atendimento, id_restaurante) 
values (RANDOM_UUID(), 'SEXTA_FEIRA', '10:00:00', '15:00:00', (select id from restaurante where nome = 'Restaurante Boi na Brasa'));
		
---- ITEM 1 ---- 

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'feijoada.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante)
values (RANDOM_UUID(), 'Feijoada', 'Feijoada completa com acompanhamentos', 39.90, 1, 1,
		(select id from imagem where nome = 'feijoada.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'lasanha.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Lasanha', 'Lasanha de carne com queijo', 29.50, 1, 0,
		(select id from imagem where nome = 'lasanha.png'),
		(select id from restaurante where nome = 'La Bella Pasta'));
		
insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'salada.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Salada Caesar', 'Salada Caesar tradicional', 19.90, 0, 1,
		(select id from imagem where nome = 'salada.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'pizza.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Pizza Margherita', 'Pizza com tomate, mussarela e manjericão', 42.00, 1, 0,
		(select id from imagem where nome = 'pizza.png'),
		(select id from restaurante where nome = 'La Bella Pasta'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'combo_hamburguer.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Hambúrguer Artesanal', 'Hambúrguer com batata frita', 27.90, 1, 0,
		(select id from imagem where nome = 'combo_hamburguer.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'risoto.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Risoto de Camarão', 'Risoto cremoso de camarão', 49.90, 0, 0,
		(select id from imagem where nome = 'risoto.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'strogonoff.png', 'image/png', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante)
values (RANDOM_UUID(), 'Strogonoff de Frango', 'Strogonoff com arroz e batata palha', 25.00, 1, 1,
		(select id from imagem where nome = 'strogonoff.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'combo_sushi.jpeg', 'image/jpeg', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Sushi Combo', 'Combo de sushi variado', 59.90, 0, 1,
		(select id from imagem where nome = 'combo_sushi.jpeg'),
		(select id from restaurante where nome = 'Restaurante Cantinho do Sushi'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'tapioca.jpg', 'image/jpg', 'a');

insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Tapioca', 'Tapioca recheada com queijo e coco', 12.00, 1, 0,
		(select id from imagem where nome = 'tapioca.jpg'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));

insert into imagem(id, nome, tipo, conteudo) values (RANDOM_UUID(), 'bolo_chocolate.png', 'image/png', 'a');
 
insert into item(id, nome, descricao, preco, disponivel_consumo_presencial, disponivel, id_imagem, id_restaurante) 
values (RANDOM_UUID(), 'Bolo de Chocolate', 'Bolo de chocolate com cobertura', 15.00, 0, 1,
		(select id from imagem where nome = 'bolo_chocolate.png'),
		(select id from restaurante where nome = 'Restaurante Sabor Brasil'));