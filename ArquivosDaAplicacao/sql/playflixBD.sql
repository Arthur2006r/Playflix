DROP DATABASE IF EXISTS playflixpoo;

CREATE DATABASE playflixpoo;

USE playflixpoo;

-- Tabela de Usuários  
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    avatar VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255),
    senha VARCHAR(255) NOT NULL,
    tipoUsuario ENUM('Jogador', 'Repositor','Administrador') NOT NULL
);

-- Tabela de Jogos 
CREATE TABLE Jogo (
    idJogo INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    faixaetaria ENUM('Livre', '+12', '+14', '+16', '+18') NOT NULL,
    datapublicacao DATE NOT NULL,
    codigofonte TEXT NOT NULL
);

-- Tabela de Gêneros 
CREATE TABLE Genero (
	idGenero INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL
);

-- Tabela de Fóruns
CREATE TABLE Forum (
    idForum INT PRIMARY KEY AUTO_INCREMENT,
	idUsuario INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    faixaetaria ENUM('Livre', '+12', '+14', '+16', '+18') NOT NULL
);

-- Tabela de Comentários 
CREATE TABLE Comentario (
    idComentario INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idJogo INT NOT NULL,
    descricao TEXT NOT NULL
);

-- Tabela de Conversas 
CREATE TABLE Conversa (
    idConversa INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idForum INT NOT NULL,
    descricao TEXT NOT NULL
);

-- Tabela de Denúncias de Comentários
CREATE TABLE DenunciaComentario (
    idDenunciaComentario INT AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idComentario INT NOT NULL,
    descricao TEXT NOT NULL,
    
    PRIMARY KEY(idDenunciaComentario, idUsuario, idComentario)	
);

-- Tabela de Análise de Comentários
CREATE TABLE AnaliseComentario (
    idUsuario INT NOT NULL,
    idComentario INT NOT NULL,
    resultado ENUM('aprovada', 'negada') NOT NULL,
    
    PRIMARY KEY(idUsuario, idComentario)
);

-- Tabela de Denúncias de Conversas
CREATE TABLE DenunciaConversa (
    idDenunciaConversa INT AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idConversa INT NOT NULL,
    descricao TEXT NOT NULL,
    
    PRIMARY KEY(idDenunciaConversa, idUsuario, idConversa)
);

-- Tabela de Análise de Conversas
CREATE TABLE AnaliseConversa (
    idUsuario INT NOT NULL,
    idConversa INT NOT NULL,
    resultado ENUM('aprovada', 'negada') NOT NULL,
    
    PRIMARY KEY(idUsuario, idConversa)
);

-- Tabela de Relacionamento entre Usuario e Jogo - Gameplay
CREATE TABLE Gameplay (
    dataHoraAcesso DATETIME,
    idUsuario INT NOT NULL,
    idJogo INT NOT NULL,
    
    PRIMARY KEY(dataHoraAcesso, idUsuario, idJogo)
);

-- Tabela de Relacionamento entre Usuario e Jogo - Jogo Favorito
CREATE TABLE JogoFavorito (
    idUsuario INT NOT NULL,
    idJogo INT NOT NULL,
    
    PRIMARY KEY(idUsuario, idJogo)
);

-- Tabela de Relacionamento entre Usuario e Fórum
CREATE TABLE UsuarioForum (
    idUsuario INT NOT NULL,
    idForum INT NOT NULL,
    
    PRIMARY KEY(idUsuario, idForum)
);

-- Tabela de Relacionamento entre Gênero e Fórum
CREATE TABLE GeneroForum (
    idGenero INT NOT NULL,
    idForum INT NOT NULL,
    
    PRIMARY KEY(idGenero, idForum)
);

-- Tabela de Relacionamento entre Gênero e Jogo
CREATE TABLE GeneroJogo (
    idGenero INT NOT NULL,
    idJogo INT NOT NULL,
    
    PRIMARY KEY(idJogo, idGenero)
);

alter table Jogo add constraint fk_Jogo_Usuario foreign key(idUsuario) references Usuario(idUsuario);

alter table Forum add constraint fk_Forum_Usuario foreign key(idUsuario) references Usuario(idUsuario);

alter table Comentario add constraint fk_Comentario_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table Comentario add constraint fk_Comentario_Jogo foreign key(idJogo) references Jogo(idJogo);

alter table Conversa add constraint fk_Conversa_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table Conversa add constraint fk_Conversa_Forum foreign key(idForum) references Forum(idForum);

alter table DenunciaComentario add constraint fk_DenunciaComentario_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table DenunciaComentario add constraint fk_DenunciaComentario_Comentario foreign key(idComentario) references Comentario(idComentario);

alter table DenunciaConversa add constraint fk_DenunciaConversa_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table DenunciaConversa add constraint fk_DenunciaConversa_Conversa foreign key(idConversa) references Conversa(idConversa);

alter table Gameplay add constraint fk_Gameplay_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table Gameplay add constraint fk_Gameplay_Jogo foreign key(idJogo) references Jogo(idJogo);

alter table UsuarioForum add constraint fk_UsuarioForum_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table UsuarioForum add constraint fk_UsuarioForum_Forum foreign key(idForum) references Forum(idForum);

alter table JogoFavorito add constraint fk_JogoFavorito_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table JogoFavorito add constraint fk_JogoFavorito_Jogo foreign key(idJogo) references Jogo(idJogo);

alter table AnaliseComentario add constraint fk_AnaliseComentario_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table AnaliseComentario add constraint fk_AnaliseComentario_Comentario foreign key(idComentario) references Comentario(idComentario);

alter table AnaliseConversa add constraint fk_AnaliseConversa_Usuario foreign key(idUsuario) references Usuario(idUsuario);
alter table AnaliseConversa add constraint fk_AnaliseConversa_Conversa foreign key(idConversa) references Conversa(idConversa);

alter table GeneroForum add constraint fk_GeneroForum_Usuario foreign key(idGenero) references Genero(idGenero);
alter table GeneroForum add constraint fk_GeneroForum_Forum foreign key(idForum) references Forum(idForum);

alter table GeneroJogo add constraint fk_GeneroJogo_Usuario foreign key(idGenero) references Genero(idGenero);
alter table GeneroJogo add constraint fk_GeneroJogo_Jogo foreign key(idJogo) references Jogo(idJogo);

-- Gatilhos 
DELIMITER $$
CREATE TRIGGER ControleDesenvolverJogo
BEFORE INSERT ON Jogo
FOR EACH ROW
BEGIN
    DECLARE jogador INT;
    
    SELECT COUNT(Usuario.idUsuario)
    INTO jogador
    FROM Usuario
    WHERE Usuario.idUsuario = NEW.idUsuario AND Usuario.tipoUsuario = 'Jogador';
    
    IF (jogador >= 1) THEN
        SIGNAL SQLSTATE "45001"
        SET MESSAGE_TEXT = "Não é permitido um usuário que não seja um repositor ou um administrador desenvolver um jogo!";
    END IF;
END$$;
DELIMITER ;

DELIMITER $$
CREATE TRIGGER ControleEditarUsuario
BEFORE UPDATE ON Usuario
FOR EACH ROW
BEGIN
    DECLARE jogador INT;

    SELECT COUNT(Usuario.idUsuario)
    INTO jogador
    FROM Usuario
    WHERE Usuario.idUsuario = NEW.idUsuario AND Usuario.tipoUsuario = 'Jogador';

    IF (jogador >= 1) THEN
        SIGNAL SQLSTATE "45001"
        SET MESSAGE_TEXT = "Não é permitido um usuário que não seja um repositor ou um administrador ter um nome!";
    END IF;
END$$;
DELIMITER ;

DELIMITER $$
CREATE TRIGGER ControleAnaliseComentario
BEFORE INSERT ON AnaliseComentario
FOR EACH ROW
BEGIN
    DECLARE administrador INT;

    SELECT COUNT(Usuario.idUsuario)
    INTO administrador
    FROM Usuario
    WHERE Usuario.idUsuario = NEW.idUsuario AND Usuario.tipoUsuario = 'Administrador';

    IF (administrador < 1) THEN
        SIGNAL SQLSTATE "45001"
        SET MESSAGE_TEXT = "Não é permitido um usuário que não seja um administrador analisar um comentário!";
    END IF;
END$$;
DELIMITER ;

DELIMITER $$
CREATE TRIGGER ControleAnaliseConversa
BEFORE INSERT ON AnaliseConversa
FOR EACH ROW
BEGIN
    DECLARE administrador INT;

    SELECT COUNT(Usuario.idUsuario)
    INTO administrador
    FROM Usuario
    WHERE Usuario.idUsuario = NEW.idUsuario AND Usuario.tipoUsuario = 'Administrador';

    IF (administrador < 1) THEN
        SIGNAL SQLSTATE "45001"
        SET MESSAGE_TEXT = "Não é permitido um usuário que não seja um administrador analisar uma conversa!";
    END IF;
END$$;
DELIMITER ;

-- Povoamento

INSERT INTO Usuario (nickname, email, senha, tipoUsuario) VALUES
    ('Arthur', 'emailArthur', '123', 'Administrador'),
    ('Ian', 'emailIan', '321', 'Repositor'),
    ('Nicolas', 'emailNicolas', '575', 'Jogador'),
     ('Sandra', 'emailSandra', '789', 'Administrador'),
    ('Lucas', 'emailLucas', '456', 'Repositor'),
    ('Camila', 'emailCamila', '654', 'Jogador'),
    ('Fernando', 'emailFernando', '987', 'Administrador'),
    ('Ana', 'emailAna', '321', 'Repositor'),
    ('Gabriel', 'emailGabriel', '234', 'Jogador'),
    ('Isabela', 'emailIsabela', '567', 'Administrador');

INSERT INTO Jogo (idUsuario, descricao, faixaetaria, datapublicacao, codigofonte) VALUES
    (1, 'Resident Evil', 'Livre', '2023-01-01', 'Código A'),
    (2, 'Call of Duty', '+12', '2023-02-15', 'Código B'),
    (7, 'Shadow of the Colossus', '+16', '2023-03-20', 'Código C'),
    (1, 'Scooby Doo', '+14', '2023-04-10', 'Código D'),
    (2, 'Maior Kart', '+18', '2023-05-25', 'Código E'),
    (8, 'Lego Star Wars', 'Livre', '2023-06-30', 'Código F'),
    (1, 'Valorant', '+16', '2023-07-15', 'Código G'),
    (2, 'Silent Hill', '+14', '2023-08-05', 'Código H'),
    (10, 'The Last of Us', '+12', '2023-09-20', 'Código I'),
    (1, 'God o War', 'Livre', '2023-10-10', 'Código J');
    
INSERT INTO Genero (nome, descricao) VALUES
    ('Ação', 'Jogos com foco em ação e combate'),
    ('Aventura', 'Jogos com narrativas envolventes'),
    ('Estratégia', 'Jogos que exigem planejamento e estratégia'),
    ('Esporte', 'Jogos baseados em esportes'),
    ('RPG', 'Jogos de interpretação de personagens'),
    ('Simulação', 'Jogos que simulam situações da vida real'),
    ('Quebra-cabeça', 'Jogos que desafiam o raciocínio'),
    ('FPS', 'Jogos de tiro em primeira pessoa'),
    ('Corrida', 'Jogos de corrida de veículos'),
    ('Indie', 'Jogos desenvolvidos por estúdios independentes');

INSERT INTO Forum (idUsuario, nome, descricao, faixaetaria) VALUES
    (1, 'Fórum A', 'Discussão sobre jogos do tipo A', 'Livre'),
    (2, 'Fórum B', 'Troca de informações sobre jogos do tipo B', '+12'),
    (3, 'Fórum C', 'Compartilhamento de dicas para jogos do tipo C', '+16'),
    (1, 'Fórum D', 'Debates sobre jogos do tipo D', '+14'),
    (2, 'Fórum E', 'Discussão sobre jogos do tipo E', '+18'),
    (3, 'Fórum F', 'Compartilhamento de experiências em jogos do tipo F', 'Livre'),
    (1, 'Fórum G', 'Troca de informações sobre jogos do tipo G', '+16'),
    (2, 'Fórum H', 'Debates sobre jogos do tipo H', '+14'),
    (3, 'Fórum I', 'Compartilhamento de dicas para jogos do tipo I', '+12'),
    (1, 'Fórum J', 'Discussão sobre jogos do tipo J', 'Livre');

INSERT INTO Comentario (idUsuario, idJogo, descricao) VALUES
    (1, 1, 'Ótimo jogo, recomendo!'),
    (2, 3, 'Achei o enredo muito interessante.'),
    (3, 2, 'Gráficos incríveis!'),
    (1, 4, 'Poderia ter mais opções de customização.'),
    (2, 5, 'Jogo muito difícil, desafio aceito!'),
    (3, 6, 'História envolvente, mal posso esperar pela continuação.'),
    (1, 7, 'Ótimo fórum para discutir estratégias.'),
    (2, 8, 'Comunidade muito ativa, parabéns!'),
    (3, 9, 'Dicas valiosas para iniciantes.'),
    (1, 10, 'Discussões interessantes, estou aprendendo muito.');

INSERT INTO Conversa (idUsuario, idForum, descricao) VALUES
    (1, 1, 'Conversa sobre o Jogo A'),
    (2, 3, 'Debates sobre estratégias no Jogo C'),
    (3, 2, 'Troca de informações sobre o Jogo B'),
    (1, 4, 'Conversa sobre o Jogo D'),
    (2, 5, 'Discussão sobre o Jogo E'),
    (3, 6, 'Conversa sobre o Jogo F'),
    (1, 7, 'Estratégias para o Jogo G'),
    (2, 8, 'Debates sobre o Jogo H'),
    (3, 9, 'Troca de informações sobre o Jogo I'),
    (1, 10, 'Discussão sobre o Jogo J');

-- Tabela de Denúncias de Comentários
INSERT INTO DenunciaComentario (idUsuario, idComentario, descricao) VALUES
    (2, 1, 'Conteúdo ofensivo'),
    (3, 4, 'Spam no comentário'),
    (1, 7, 'Comentário inapropriado'),
    (2, 9, 'Desrespeito às regras do fórum'),
    (3, 2, 'Conteúdo impróprio'),
    (1, 5, 'Denúncia de linguagem inadequada'),
    (2, 8, 'Conteúdo irrelevante'),
    (3, 3, 'Comentário contendo spoilers'),
    (1, 6, 'Denúncia de discurso de ódio'),
    (2, 10, 'Alegações falsas');

-- Tabela de Análise de Comentários
INSERT INTO AnaliseComentario (idUsuario, idComentario, resultado) VALUES
    (10, 1, 'aprovada'),
    (1, 4, 'negada'),
    (10, 7, 'aprovada'),
    (7, 2, 'negada'),
    (1, 5, 'aprovada'),
    (4, 8, 'negada'),
    (4, 3, 'aprovada'),
    (1, 6, 'negada'),
    (10, 9, 'aprovada'),
    (7, 10, 'negada');

-- Tabela de Denúncias de Conversas
INSERT INTO DenunciaConversa (idUsuario, idConversa, descricao) VALUES
    (2, 1, 'Conteúdo ofensivo'),
    (3, 4, 'Spam na conversa'),
    (1, 7, 'Conversa inapropriada'),
    (2, 9, 'Desrespeito às regras do fórum'),
    (3, 2, 'Conteúdo impróprio'),
    (1, 5, 'Denúncia de linguagem inadequada'),
    (2, 8, 'Conteúdo irrelevante'),
    (3, 3, 'Conversa contendo spoilers'),
    (1, 6, 'Denúncia de discurso de ódio'),
    (2, 10, 'Alegações falsas');

-- Tabela de Análise de Conversas
INSERT INTO AnaliseConversa (idUsuario, idConversa, resultado) VALUES
    (10, 1, 'aprovada'),
    (1, 4, 'negada'),
    (4, 7, 'aprovada'),
    (7, 2, 'negada'),
    (1, 5, 'aprovada'),
    (7, 8, 'negada'),
    (4, 3, 'aprovada'),
    (1, 6, 'negada'),
    (10, 9, 'aprovada'),
    (10, 10, 'negada');

-- Tabela de Relacionamento entre Usuario e Jogo - Gameplay
INSERT INTO Gameplay (dataHoraAcesso, idUsuario, idJogo) VALUES
    ('2023-01-02 12:30:00', 1, 1),
    ('2023-02-03 14:45:00', 2, 1),
    ('2023-03-04 18:20:00', 3, 2),
    ('2023-04-05 21:10:00', 1, 3),
    ('2023-05-06 09:00:00', 2, 4),
    ('2023-06-07 16:30:00', 3, 5),
    ('2023-07-08 08:45:00', 1, 6),
    ('2023-08-09 11:55:00', 2, 7),
    ('2023-09-10 19:40:00', 3, 8),
    ('2023-10-11 23:15:00', 1, 9);

-- Tabela de Relacionamento entre Usuario e Jogo - Jogo Favorito
INSERT INTO JogoFavorito (idUsuario, idJogo) VALUES
    (1, 2),
    (2, 4),
    (3, 6),
    (1, 8),
    (2, 10),
    (3, 1),
    (1, 3),
    (2, 5),
    (3, 7),
    (1, 9);

-- Tabela de Relacionamento entre Usuario e Fórum
INSERT INTO UsuarioForum (idUsuario, idForum) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (1, 4),
    (2, 5),
    (3, 6),
    (1, 7),
    (2, 8),
    (3, 9),
    (1, 10);

-- Tabela de Relacionamento entre Gênero e Fórum
INSERT INTO GeneroForum (idGenero, idForum) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

-- Tabela de Relacionamento entre Gênero e Jogo
INSERT INTO GeneroJogo (idGenero, idJogo) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);
    
-- Teste dos gatilhos
-- GATILHO: ControleDesenvolverJogo
INSERT INTO Jogo (idUsuario, descricao, faixaetaria, datapublicacao, codigofonte) VALUES
    (3, 'Among Us', 'Livre', '2023-03-01', 'Código Z');
    
-- GATILHO: ControleEditarUsuario
UPDATE Usuario
SET nome = 'Arthur'
WHERE idUsuario = 1;

UPDATE Usuario
SET nome = 'Ian'
WHERE idUsuario = 2;

UPDATE Usuario
SET nome = 'Nicolas'
WHERE idUsuario = 3;

-- GATILHO: ControleAnaliseComentario
-- jogador
INSERT INTO AnaliseComentario (idUsuario, idComentario, resultado) VALUES
    (6, 1, 'aprovada');

-- repositor
INSERT INTO AnaliseComentario (idUsuario, idComentario, resultado) VALUES
    (8, 1, 'aprovada');
    
-- GATILHO: ControleAnaliseConversa
-- jogador
INSERT INTO AnaliseComentario (idUsuario, idComentario, resultado) VALUES
    (6, 1, 'aprovada');

-- repositor
INSERT INTO AnaliseComentario (idUsuario, idComentario, resultado) VALUES
    (8, 1, 'aprovada');