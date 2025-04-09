-- ----------------------------------------
--  DCL
-- ----------------------------------------

-- creacion base de datos
CREATE DATABASE IF NOT EXISTS db_crud_user;


-- cracion usuario para la base de datos
CREATE USER 'example1JSP'@'localhost' IDENTIFIED BY 'example123';

GRANT ALL PRIVILEGES ON db_crud_user.* TO 'example1JSP'@'localhost';

FLUSH PRIVILEGES;


-- ----------------------------------------
--  DDL
-- ----------------------------------------

-- uso de base de datos
use db_crud_user;

-- cracion tablas
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `email` VARCHAR(150) UNIQUE NOT NULL,
  `address` TEXT NOT NULL,
  `dpi` VARCHAR(15) UNIQUE NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role_id` INT NOT NULL,
  `state` ENUM('ENABLED', 'DISABLED', 'DELETED') NOT NULL DEFAULT 'ENABLED',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `role_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(250)
);

ALTER TABLE `user` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);


-- ----------------------------------------
--  DML
-- ----------------------------------------

-- inserts roles

INSERT INTO role(role_name, description) VALUE ('ENCARGADO DE ENSAMBLAJE', 'DESCRIPCION.... DE FABRICA'),
('ENCARGADO DE VENTA', 'DESCRIPCION.... DE VENTA'), ('ADMINISTRADO', 'DESCRIPCION.... DE ADMINISTRADOR');


SELECT * FROM role;

-- INSERT USUARIOS

INSERT INTO  user (name, email, address, dpi, password, role_id) VALUE ('Juan Perez', 'juanPerez@email.com', '151551515', 'zona 15 mixco', 'juan123',3);

SELECT * FROM user;
