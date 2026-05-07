

-- Copiando estrutura do banco de dados para databasespring
CREATE DATABASE IF NOT EXISTS `databasespring` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `databasespring`;

-- Copiando estrutura para tabela databasespring.person
CREATE TABLE IF NOT EXISTS `person` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(80) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    `address` varchar(80) NOT NULL,
    `gender` varchar(6) NOT NULL,
    `birthday` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
    );
