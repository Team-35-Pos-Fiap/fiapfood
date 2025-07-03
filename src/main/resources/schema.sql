CREATE TABLE IF NOT EXISTS `cardapio` (
                            `disponivel_apenas_restaurante` tinyint(1) NOT NULL,
                            `preco` double NOT NULL,
                            `id` binary(16) NOT NULL,
                            `descricao` varchar(255) NOT NULL,
                            `foto_prato` varchar(255) NOT NULL,
                            `nome` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `endereco` (
                            `numero` int DEFAULT NULL,
                            `sem_numero` tinyint DEFAULT NULL,
                            `id` binary(16) NOT NULL,
                            `bairro` varchar(255) DEFAULT NULL,
                            `cep` varchar(255) DEFAULT NULL,
                            `cidade` varchar(255) DEFAULT NULL,
                            `complemento` varchar(255) DEFAULT NULL,
                            `endereco` varchar(255) DEFAULT NULL,
                            `estado` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `login` (
                         `id` binary(16) NOT NULL,
                         `matricula` varchar(255) DEFAULT NULL,
                         `senha` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_matricula` (`matricula`)
);

CREATE TABLE IF NOT EXISTS `perfil` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `nome` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `usuario` (
                           `ativo` tinyint DEFAULT NULL,
                           `id_perfil` int DEFAULT NULL,
                           `data_atualizacao` datetime(6) DEFAULT NULL,
                           `data_criacao` datetime(6) NOT NULL,
                           `id` binary(16) NOT NULL,
                           `id_endereco` binary(16) DEFAULT NULL,
                           `id_login` binary(16) DEFAULT NULL,
                           `email` varchar(255) DEFAULT NULL,
                           `nome` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_endereco` (`id_endereco`),
                           UNIQUE KEY `UK_login` (`id_login`),
                           UNIQUE KEY `UK_email` (`email`),
                           KEY `FK_perfil` (`id_perfil`),
                           CONSTRAINT `FK_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id`),
                           CONSTRAINT `FK_login` FOREIGN KEY (`id_login`) REFERENCES `login` (`id`),
                           CONSTRAINT `FK_endereco` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id`)
);

CREATE TABLE IF NOT EXISTS `restaurante` (
                                             `horario_funcionamento` datetime(6) NOT NULL,
    `id` binary(16) NOT NULL,
    `id_dono_restaurante` binary(16) DEFAULT NULL,
    `id_endereco` binary(16) DEFAULT NULL,
    `nome` varchar(255) NOT NULL,
    `tipo_cozinha` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_usuario_restaurante` (`id_dono_restaurante`),
    KEY `FK_endereco_restaurante` (`id_endereco`),
    CONSTRAINT `FK_endereco_restaurante` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id`),
    CONSTRAINT `FK_usuario_restaurante` FOREIGN KEY (`id_dono_restaurante`) REFERENCES `usuario` (`id`)
    );