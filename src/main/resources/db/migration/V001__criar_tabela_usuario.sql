CREATE TABLE usuario (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   nome varchar(255) NOT NULL,
   idade integer NOT NULL,
   cpf varchar(15) NOT NULL UNIQUE
);