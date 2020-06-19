CREATE TABLE IF NOT EXISTS autor (
  autorId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome varchar(256) NOT NULL,
  dataDeNascimento datetime NOT NULL
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS livros (
  livroId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  autorId INT,
  titulo varchar(256) NOT NULL,
  dataDePublicacao datetime,
  FOREIGN KEY (autorId) REFERENCES autor(autorId)
)CHARSET=utf8;

INSERT INTO autor (autorId, nome, dataDeNascimento) VALUES
(1, 'William Shakespeare', '1564-04-26 00:00:00'),
(2, 'Agatha Christie', '1890-09-15'),
(3, 'Barbara Cartland', '1901-07-09'),
(4, 'Danielle Steel', '1947-08-14');


INSERT INTO livros (livroId, autorId, titulo, dataDePublicacao) VALUES
(1, 1, 'Trabalhos de Amores Perdidos', '1590-01-01 00:00:00'),
(2, 1, 'A Comédia dos Erros', '1591-01-01 00:00:00'),
(3, 1, 'Os Dois Cavalheiros de Verona', '1591-01-01 00:00:00'),
(4, 2, 'A Murder is Announced', '1950-01-01 00:00:00'),
(5, 2, 'The Hollow', '1946-01-01 00:00:00'),
(6, 3, 'Blood Money', '1926-01-01 00:00:00'),
(7, 4, 'Max Runs Away', '1990-01-01 00:00:00'),
(8, 4, 'Max and Grandma and Grampa Winky', '1991-01-01 00:00:00');