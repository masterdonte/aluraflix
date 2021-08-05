CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

CREATE TABLE perfil (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
);

CREATE TABLE usuarioperfil (
	usuarioid BIGINT(20) NOT NULL,
	perfilid BIGINT(20) NOT NULL,
	PRIMARY KEY (usuarioid, perfilid),
	FOREIGN KEY (usuarioid) REFERENCES usuario(id),
	FOREIGN KEY (perfilid) REFERENCES perfil(id)
);

INSERT INTO perfil (nome) values ('ADMIN');
INSERT INTO perfil (nome) values ('USER');

INSERT INTO usuario (nome, email, senha) values ('Jonathas', 'teste@gmail.com', '$2a$10$2dtuiadBc.glJ/ZriG1vyeiwQfaQnP0KSt7TpWaywBgbuEIGIhPnC');

INSERT INTO usuarioperfil (usuarioid, perfilid) values (1, 1);
INSERT INTO usuarioperfil (usuarioid, perfilid) values (1, 2);
