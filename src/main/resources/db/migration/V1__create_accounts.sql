DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    owner varchar(255) NOT NULL,
    number int NOT NULL,
    balance int NOT NULL
 );

