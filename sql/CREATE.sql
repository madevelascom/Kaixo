CREATE DATABASE `kaixo` /*!40100 COLLATE 'utf16_swedish_ci' */

USE `kaixo`;

CREATE TABLE `alergias` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`nombrealergia` VARCHAR(50) NOT NULL DEFAULT 'alergia' COLLATE 'utf16_spanish2_ci',
	PRIMARY KEY (`id`)
)
COMMENT='Kaixo - Alergias existentes'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `consultas` (
	`id` INT(11) NOT NULL,
	`fecha` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`paciente` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Identificacion del paciente' COLLATE 'utf16_spanish2_ci',
	`estado` VARCHAR(50) NOT NULL DEFAULT 'Por asistir' COLLATE 'utf16_spanish2_ci',
	`diagnostico` TEXT NULL COLLATE 'utf16_spanish2_ci',
	`id_valoracion` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_paciente` (`paciente`),
	INDEX `FK_valoracion` (`id_valoracion`),
	CONSTRAINT `FK_paciente` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`CI`),
	CONSTRAINT `FK_valoracion` FOREIGN KEY (`id_valoracion`) REFERENCES `paciente_valoracion` (`id`)
)
COMMENT='Kaixo - Registro de consultas'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `consulta_medicina` (
	`id_consulta` INT(11) NOT NULL,
	`id_medicina` INT(11) NOT NULL,
	`frecuencia` TEXT NULL COLLATE 'utf16_spanish2_ci',
	PRIMARY KEY (`id_consulta`, `id_medicina`),
	INDEX `FK_consulta_medicina_medicinas` (`id_medicina`),
	CONSTRAINT `FK_consulta` FOREIGN KEY (`id_consulta`) REFERENCES `consultas` (`id`),
	CONSTRAINT `FK_consulta_medicina_medicinas` FOREIGN KEY (`id_medicina`) REFERENCES `medicinas` (`id`)
)
COMMENT='Kaixo - Receta del paciente tras atender a la consulta'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `distribuidores` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(200) NOT NULL COLLATE 'utf16_spanish2_ci',
	`direccion` TEXT NOT NULL COLLATE 'utf16_spanish2_ci',
	`telefono` VARCHAR(50) NOT NULL COMMENT 'Telefono principal del distribuidor de medicinas' COLLATE 'utf16_spanish2_ci',
	PRIMARY KEY (`id`)
)
COMMENT='Kaixo - Distribuidores de medicina'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `medicinas` (
	`id` INT(11) NOT NULL,
	`nombre` VARCHAR(100) NULL COLLATE 'utf16_spanish2_ci',
	`concentracion` VARCHAR(50) NULL COLLATE 'utf16_spanish2_ci',
	PRIMARY KEY (`id`)
)
COMMENT='Kaixo - Medicinas'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `medicina_dist` (
	`id_medicina` INT(11) NOT NULL,
	`id_distribuidor` INT(11) NOT NULL,
	PRIMARY KEY (`id_medicina`, `id_distribuidor`),
	INDEX `FK_distribuidor` (`id_distribuidor`),
	CONSTRAINT `FK_distribuidor` FOREIGN KEY (`id_distribuidor`) REFERENCES `distribuidores` (`id`),
	CONSTRAINT `FK_medicina` FOREIGN KEY (`id_medicina`) REFERENCES `medicinas` (`id`)
)
COMMENT='Kaixo - Disponibilidad de las medicinas en los distribuidores'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `paciente` (
	`CI` VARCHAR(10) NOT NULL DEFAULT '9999999999' COMMENT 'Cedula de identidad' COLLATE 'utf16_spanish2_ci',
	`nombres` VARCHAR(100) NOT NULL COLLATE 'utf16_spanish2_ci',
	`apellidos` VARCHAR(100) NOT NULL COLLATE 'utf16_spanish2_ci',
	`fechanacimiento` DATE NOT NULL DEFAULT '0000-00-00',
	`tiposangre` VARCHAR(5) NOT NULL COLLATE 'utf16_spanish2_ci',
	`numcelular` VARCHAR(10) NOT NULL DEFAULT '0999999999' COLLATE 'utf16_spanish2_ci',
	`numcasa` VARCHAR(10) NOT NULL DEFAULT '042999999' COLLATE 'utf16_spanish2_ci',
	`dircasa` TEXT NOT NULL COMMENT 'Direccion de la casa del paciente' COLLATE 'utf16_spanish2_ci',
	`email` VARCHAR(100) NOT NULL DEFAULT 'kaixo@gmail.com' COMMENT 'Email donde le llegara la informacion' COLLATE 'utf16_spanish2_ci',
	PRIMARY KEY (`CI`)
)
COMMENT='Kaixo - Informacion de pacientes'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `paciente_alergias` (
	`id_paciente` VARCHAR(10) NOT NULL COLLATE 'utf16_spanish2_ci',
	`id_alergia` INT(11) NOT NULL,
	PRIMARY KEY (`id_paciente`, `id_alergia`),
	INDEX `FK_alergia` (`id_alergia`),
	CONSTRAINT `FK_alergia` FOREIGN KEY (`id_alergia`) REFERENCES `alergias` (`id`),
	CONSTRAINT `FK_pacientes` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`CI`)
)
COMMENT='Kaixo - Alergias de los pacientes'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `paciente_valoracion` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`presion` INT(11) NOT NULL,
	`glucosa` INT(11) NOT NULL,
	`peso` FLOAT NOT NULL COMMENT 'En kilogramos',
	PRIMARY KEY (`id`)
)
COMMENT='Kaixo - Valoracion de los pacientes'
COLLATE='utf16_spanish2_ci'
ENGINE=InnoDB
;

CREATE TABLE `users` (
	`username` VARCHAR(50) NOT NULL DEFAULT 'test' COLLATE 'utf16_spanish2_ci',
	`password` VARCHAR(80) NOT NULL COLLATE 'utf16_spanish2_ci',
	`level` TINYINT(4) NOT NULL,
	PRIMARY KEY (`username`)
)
COMMENT='Kaixo - login'
COLLATE='utf16_spanish2_ci'
ENGINE=MyISAM
;
