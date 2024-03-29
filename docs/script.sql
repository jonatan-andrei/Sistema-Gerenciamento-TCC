-- Criação do banco de dados tcc
CREATE DATABASE `ssi_tcc` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ssi_tcc`;

-- Criação da tabela Aluno

CREATE TABLE `Aluno` (
  `id_aluno` integer PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `matricula` varchar(15) NOT NULL,
  `telefone` varchar(15),
  `ativo` varchar(1) DEFAULT 'S'
) ENGINE = innodb;

-- Criação da tabela Professor

CREATE TABLE `Professor` (
  `id_professor` integer PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `carga_trabalho_semestre` integer DEFAULT 0,
  `ativo` varchar(1) DEFAULT 'S'
) ENGINE = innodb;

-- Criação da tabela Area

CREATE TABLE `Area` (
  `id_area` integer PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(200) NOT NULL
) ENGINE = innodb;

-- Criação da tabela Professor_Area

CREATE TABLE `Professor_Area` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `id_area` integer NOT NULL,
  `id_professor` integer NOT NULL
) ENGINE = innodb;

ALTER TABLE `Professor_Area` ADD CONSTRAINT `fk_pa_area` FOREIGN KEY ( `id_area` ) REFERENCES `Area` ( `id_area` ) ;
ALTER TABLE `Professor_Area` ADD CONSTRAINT `fk_pa_professor` FOREIGN KEY ( `id_professor` ) REFERENCES `Professor` ( `id_professor` ) ;

-- Criação da tabela Projeto_Pesquisa

CREATE TABLE `Projeto_Pesquisa` (
  `id_projeto_pesquisa` integer PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(200) NOT NULL
) ENGINE = innodb;

-- Criação da tabela Sugestao_TCC

CREATE TABLE `Sugestao_TCC` (
  `id_sugestao_tcc` integer PRIMARY KEY AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `id_professor_criador` integer NOT NULL,
  `id_projeto_pesquisa` integer,
  `escolhida` varchar(1) default 'N'
) ENGINE = innodb;

ALTER TABLE `Sugestao_TCC` ADD CONSTRAINT `fk_sugestao_professor` FOREIGN KEY ( `id_professor_criador` ) REFERENCES `Professor` ( `id_professor` ) ;
ALTER TABLE `Sugestao_TCC` ADD CONSTRAINT `fk_sugestao_projeto` FOREIGN KEY ( `id_projeto_pesquisa` ) REFERENCES `Projeto_Pesquisa` ( `id_projeto_pesquisa` ) ;

-- Criação da tabela Proposta TCC
CREATE TABLE `Proposta_TCC` (
  `id_proposta_tcc` integer PRIMARY KEY AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `artigo` varchar(100),
  `id_aluno_autor` integer NOT NULL,
  `id_professor_orientador` integer NOT NULL,
  `id_professor_avaliador_primeiro` integer,
  `id_professor_avaliador_segundo` integer,
  `id_sugestao_origem` integer,
  `id_area` integer NOT NULL,
  `ativo` varchar(1) DEFAULT 'S'
) ENGINE = innodb;

ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_area` FOREIGN KEY ( `id_area` ) REFERENCES `Area` ( `id_area` ) ;
ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_sugestao` FOREIGN KEY ( `id_sugestao_origem` ) REFERENCES `Sugestao_TCC` ( `id_sugestao_tcc` ) ;
ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_professor` FOREIGN KEY ( `id_professor_orientador` ) REFERENCES `Professor` ( `id_professor` ) ;
ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_professor_avaliador_primeiro` FOREIGN KEY ( `id_professor_avaliador_primeiro` ) REFERENCES `Professor` ( `id_professor` ) ;
ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_professor_avaliador_segundo` FOREIGN KEY ( `id_professor_avaliador_segundo` ) REFERENCES `Professor` ( `id_professor` ) ;
ALTER TABLE `Proposta_TCC` ADD CONSTRAINT `fk_proposta_aluno` FOREIGN KEY ( `id_aluno_autor` ) REFERENCES `Aluno` ( `id_aluno` ) ;

-- Criação da tabela Avaliacao

CREATE TABLE `Avaliacao` (
  `id_avaliacao` integer PRIMARY KEY AUTO_INCREMENT,
  `nota_final` float(4,2) NOT NULL,
  `aprovado` varchar(1) NOT NULL,
  `id_professor_avaliador` integer NOT NULL,
  `parecer` varchar(100) NOT NULL,
  `id_proposta_tcc` integer NOT NULL
) ENGINE = innodb;

ALTER TABLE `Avaliacao` ADD CONSTRAINT `fk_avaliacao_professor` FOREIGN KEY ( `id_professor_avaliador` ) REFERENCES `Professor` ( `id_professor` ) ;
ALTER TABLE `Avaliacao` ADD CONSTRAINT `fk_avaliacao_proposta` FOREIGN KEY ( `id_proposta_tcc` ) REFERENCES `Proposta_TCC` ( `id_proposta_tcc` ) ;

-- Criação da tabela Avaliacao_Criterio

CREATE TABLE `Avaliacao_Criterio` (
  `id_avaliacao_criterio` integer PRIMARY KEY AUTO_INCREMENT,
  `id_avaliacao` integer NOT NULL,
  `criterio` varchar(100) NOT NULL,
  `observacao` varchar(100) NOT NULL
) ENGINE = innodb;

ALTER TABLE `Avaliacao_Criterio` ADD CONSTRAINT `fk_avaliacao_criterio` FOREIGN KEY ( `id_avaliacao` ) REFERENCES `Avaliacao` ( `id_avaliacao` ) ;

INSERT INTO Area(nome) VALUES ('Desenvolvimento Java');
INSERT INTO Area(nome) VALUES ('Desenvolvimento Javascript');
INSERT INTO Area(nome) VALUES ('Desenvolvimento PHP');
INSERT INTO Area(nome) VALUES ('Desenvolvimento Python');
INSERT INTO Area(nome) VALUES ('Desenvolvimento Android');
INSERT INTO Area(nome) VALUES ('Desenvolvimento IOS');
INSERT INTO Area(nome) VALUES ('Desenvolvimento Desktop');
INSERT INTO Area(nome) VALUES ('IHC');
INSERT INTO Area(nome) VALUES ('Estrutura de Dados');
INSERT INTO Area(nome) VALUES ('Web Design');
INSERT INTO Area(nome) VALUES ('Engenharia de Software');
INSERT INTO Area(nome) VALUES ('Banco de Dados');
INSERT INTO Area(nome) VALUES ('Redes de Computadores');
INSERT INTO Area(nome) VALUES ('Teste de Software');
INSERT INTO Area(nome) VALUES ('Segurança e Auditoria');
INSERT INTO Area(nome) VALUES ('Gestão de TI');
INSERT INTO Area(nome) VALUES ('Inteligência Artificial');
INSERT INTO Area(nome) VALUES ('Ciência de Dados');
INSERT INTO Area(nome) VALUES ('Internet das coisas');
commit;