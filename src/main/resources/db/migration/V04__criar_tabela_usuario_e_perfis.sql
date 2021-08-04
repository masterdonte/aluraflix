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

/*
INSERT INTO usuario (codigo, nome, email, senha) values ('Administrador', 'donte.master@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');
*/