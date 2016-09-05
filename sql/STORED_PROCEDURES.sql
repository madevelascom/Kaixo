

USE kaixo;

DELIMITER //
DROP PROCEDURE IF EXISTS loadMedicinas;
CREATE PROCEDURE loadMedicinas()
	BEGIN
	
	SELECT nombre, concentracion, presentacion
	FROM medicinas;
	
	END
	
//

DROP PROCEDURE IF EXISTS loadDistribuidor;
CREATE PROCEDURE loadDistribuidor()
	BEGIN 
	
	SELECT nombre, direccion, telefono
	FROM distribuidores;
	
	END
//


DROP PROCEDURE IF EXISTS loginSession;
CREATE PROCEDURE loginSession (
IN user1 VARCHAR(50), IN pass VARCHAR(80))

	BEGIN
	
	SELECT username, password
	FROM users
	WHERE BINARY username = BINARY user1 AND BINARY password = BINARY pass;

	END
//






DROP PROCEDURE IF EXISTS userLevel;
CREATE PROCEDURE userLevel(
IN user1 VARCHAR(50))

	BEGIN
	
	SELECT level
	FROM users
	WHERE BINARY users.username = BINARY user1;
	
	END
//



DROP PROCEDURE IF EXISTS errorMsg;
CREATE PROCEDURE errorMsg(
IN id INTEGER)

	BEGIN
	
	SELECT error
	FROM errores
	WHERE errores.id = id;
	
	END
	
//

DROP PROCEDURE IF EXISTS searchPaxNom;
CREATE PROCEDURE searchPaxNom(
IN pax VARCHAR(200))

	BEGIN
	
	SELECT *
	FROM paciente
	WHERE paciente.CI IN ( SELECT CI FROM paciente WHERE BINARY CONCAT(nombres, ' ', apellidos) = BINARY pax);
							
	
	
	END
//

DROP PROCEDURE IF EXISTS searchPaxCI;
CREATE PROCEDURE searchPaxCI(
IN pax VARCHAR(10))

	BEGIN
	
	SELECT *
	FROM paciente
	WHERE BINARY CI = BINARY pax;
							
	
	
	END
//

DROP PROCEDURE IF EXISTS insertNewPax;
CREATE PROCEDURE insertNewPax(
IN CI VARCHAR(10), 
IN nombres VARCHAR(100),
IN apellidos VARCHAR(100),
IN fechanacimiento DATE,
IN tiposangre VARCHAR(5),
IN numcelular VARCHAR(10),
IN numcasa VARCHAR(10),
IN dircasa TEXT,
IN email VARCHAR(100))

	BEGIN
	
	INSERT INTO paciente 
	VALUES(CI, nombres, apellidos, fechanacimiento, tiposangre, numcelular, numcasa, dircasa, email);
							
	
	
	END
//

DROP PROCEDURE IF EXISTS selectPaxNameConcat;
CREATE PROCEDURE selectPaxNameConcat(
IN id VARCHAR(10))

	BEGIN
	
	SELECT CONCAT(paciente.nombres, ' ', paciente.apellidos) AS 'paciente'
	FROM paciente
	WHERE BINARY CI = BINARY id;
	
		END
//


DROP PROCEDURE IF EXISTS selectPaxName;
CREATE PROCEDURE selectPaxName(
IN id VARCHAR(10))

	BEGIN
	
	SELECT paciente.nombres
	FROM paciente
	WHERE BINARY CI = BINARY id;
	
	END
//

DROP PROCEDURE IF EXISTS selectPaxLastName;
CREATE PROCEDURE selectPaxLastName(
IN id VARCHAR(10))

	BEGIN
	
	SELECT paciente.apellidos
	FROM paciente
	WHERE BINARY CI = BINARY id;
	
	END
//

DROP PROCEDURE IF EXISTS updatePax;
CREATE PROCEDURE updatePax(
IN CI VARCHAR(10), 
IN nombres VARCHAR(100),
IN apellidos VARCHAR(100),
IN fechanacimiento DATE,
IN tiposangre VARCHAR(5),
IN numcelular VARCHAR(10),
IN numcasa VARCHAR(10),
IN dircasa TEXT,
IN email VARCHAR(100))

	BEGIN
	
	UPDATE paciente p SET
	p.nombres = nombres ,
	p.apellidos = apellidos ,
	p.fechanacimiento = fechanacimiento ,
	p.tiposangre = tiposangre ,
	p.numcelular = numcelular ,
	p.numcasa = numcasa ,
	p.dircasa = dircasa , 
	p.email = email
	WHERE BINARY p.CI = BINARY CI;
	

	END
//

DROP PROCEDURE IF EXISTS existsPax;
CREATE PROCEDURE existsPax(
IN CI VARCHAR(10))

	BEGIN
	
	SELECT *
	FROM paciente
	WHERE BINARY paciente.CI = BINARY CI;
	
	END
//

DELIMITER //

DROP PROCEDURE IF EXISTS insertConsulta;
CREATE PROCEDURE insertConsulta(
IN fecha TIMESTAMP, 
IN patient VARCHAR(10),
IN estado VARCHAR(50))

	BEGIN
	
	INSERT INTO consultas(fecha,paciente,estado) 
	VALUES(fecha,patient,estado);
							
	
	
	END
//

DROP PROCEDURE IF EXISTS updateConsulta;
CREATE PROCEDURE updateConsulta(
IN nuevafecha TIMESTAMP, 
IN nuevoestado VARCHAR(10),
IN viejopaciente VARCHAR(10), 
IN viejafecha TIMESTAMP)

	BEGIN
	
	UPDATE consultas c SET
	c.fecha = nuevafecha ,
	c.estado = nuevoestado 
	WHERE BINARY c.paciente = BINARY viejopaciente AND BINARY c.fecha = BINARY viejafecha;
							
	
	
	END
//


DROP PROCEDURE IF EXISTS loadConsultasHoy;
CREATE PROCEDURE loadConsultasHoy()

	BEGIN
	
	SELECT DATE_FORMAT(`fecha`, '%Y-%m-%d %H:%i') AS 'fecha', paciente, estado FROM consultas
   WHERE  DATE(consultas.fecha) = DATE(NOW());
							
	
	
	END
//

DROP PROCEDURE IF EXISTS loadConsultasPasadas;
CREATE PROCEDURE loadConsultasPasadas(
IN CI VARCHAR(10))

	BEGIN
	SELECT c.paciente, c.fecha, c.estado FROM consultas c 
   WHERE BINARY DATE(c.fecha) < BINARY CURDATE() AND BINARY c.paciente =  CI ;
							
	
	
	END
//


DROP PROCEDURE IF EXISTS updateEstado;
CREATE PROCEDURE updateEstado(
IN paciente VARCHAR(10),
IN fecha TIMESTAMP,
IN estado VARCHAR(50))

	BEGIN
	
	UPDATE consultas SET consultas.estado = estado
   WHERE BINARY consultas.fecha = BINARY fecha AND BINARY consultas.paciente = BINARY paciente ;
							
	
	
	END
//


DROP PROCEDURE IF EXISTS existsPaxName;
CREATE PROCEDURE existsPaxName(
IN nombres VARCHAR(100),
IN apellidos VARCHAR(100))

	BEGIN
	
	SELECT * FROM paciente p WHERE 
   BINARY p.nombres = BINARY nombres AND 
   BINARY p.apellidos = BINARY apellidos;
							
	
	
	END

//

DROP PROCEDURE IF EXISTS getCIByName;
CREATE PROCEDURE getCIByName(
IN nombres VARCHAR(100),
IN apellidos VARCHAR(100))

	BEGIN
	
	SELECT p.CI FROM paciente p WHERE 
   BINARY p.nombres = BINARY nombres AND 
   BINARY p.apellidos = BINARY apellidos;
							
	
	
	END

//

DROP PROCEDURE IF EXISTS updateDialog;
CREATE PROCEDURE updateDialog(
IN paciente VARCHAR(10),
IN fecha TIMESTAMP,
IN diagnostico TEXT)

	BEGIN
	
	UPDATE consultas SET consultas.diagnostico = diagnostico
	WHERE BINARY consultas.fecha = BINARY fecha AND BINARY consultas.paciente = BINARY paciente;
	
							
	
	
	END

//

DROP PROCEDURE IF EXISTS existsCons;
CREATE PROCEDURE existsCons(
IN fecha TIMESTAMP)

	BEGIN
	
	SELECT *
	FROM consultas
	WHERE BINARY consultas.fecha = BINARY fecha;
	
	
							
	
	
	END

//

DROP PROCEDURE IF EXISTS medExists;
CREATE PROCEDURE medExists(
IN nombre VARCHAR(100),
IN concentracion VARCHAR(50) ,
IN presentacion VARCHAR(100))

	BEGIN
	
	SELECT *
	FROM medicinas
	WHERE BINARY medicinas.nombre = BINARY nombre AND BINARY medicinas.concentracion = BINARY concentracion 
	AND BINARY medicinas.presentacion = BINARY presentacion;
	
	
	END

//

DROP PROCEDURE IF EXISTS insertNewMed;
CREATE PROCEDURE insertNewMed(
IN nombre VARCHAR(100),
IN concentracion VARCHAR(50) ,
IN presentacion VARCHAR(100))

	BEGIN
	
	INSERT INTO medicinas(nombre,concentracion,presentacion) VALUES (nombre,concentracion,presentacion);


	END

//

DROP PROCEDURE IF EXISTS deleteMed;
CREATE PROCEDURE deleteMed(
IN nombre VARCHAR(100),
IN concentracion VARCHAR(50) ,
IN presentacion VARCHAR(100))

	BEGIN
	
	DELETE FROM medicinas
	WHERE BINARY medicinas.nombre = BINARY nombre AND BINARY medicinas.concentracion = BINARY concentracion 
	AND BINARY medicinas.presentacion = BINARY presentacion;


	END

//

DROP PROCEDURE IF EXISTS updateMed;
CREATE PROCEDURE updateMed(
IN nombre_a VARCHAR(100),
IN concentracion_a VARCHAR(50) ,
IN presentacion_a VARCHAR(100),
IN nombre_n VARCHAR(100),
IN concentracion_n VARCHAR(50) ,
IN presentacion_n VARCHAR(100))

	BEGIN
	
	UPDATE medicinas
	SET medicinas.nombre = nombre_n , medicinas.concentracion = concentracion_n , medicinas.presentacion = presentacion_n
	WHERE BINARY medicinas.nombre = BINARY nombre_a AND BINARY medicinas.concentracion = BINARY concentracion_a 
	AND BINARY medicinas.presentacion = BINARY presentacion;


END

//






DROP PROCEDURE IF EXISTS distExists;
CREATE PROCEDURE distExists(
IN nombre VARCHAR(200),
IN direccion TEXT ,
IN telefono VARCHAR(50))

	BEGIN
	
	SELECT *
	FROM distribuidores d
	WHERE BINARY d.nombre = BINARY nombre AND BINARY d.direccion = BINARY direccion 
	AND BINARY d.telefono = BINARY telefono;
	
	
							
	
	
	END

//

DROP PROCEDURE IF EXISTS insertNewDist;
CREATE PROCEDURE insertNewDist(
IN nombre VARCHAR(200),
IN direccion TEXT ,
IN telefono VARCHAR(50))

	BEGIN
	
	INSERT INTO distribuidores(nombre,direccion,telefono) VALUES (nombre,direccion,telefono);


	END

//

DROP PROCEDURE IF EXISTS deleteDist;
CREATE PROCEDURE deleteDist(
IN nombre VARCHAR(200),
IN direccion TEXT ,
IN telefono VARCHAR(50))

	BEGIN
	
	DELETE FROM distribuidores 
	WHERE BINARY distribuidores.nombre = BINARY nombre AND BINARY distribuidores.direccion = BINARY direccion 
	AND BINARY distribuidores.telefono = BINARY telefono;


	END

//

DROP PROCEDURE IF EXISTS updateDist;
CREATE PROCEDURE updateDist(
IN nombre_a VARCHAR(200),
IN direccion_a TEXT ,
IN telefono_a VARCHAR(50),
IN nombre_n VARCHAR(200),
IN direccion_n TEXT ,
IN telefono_n VARCHAR(50))

	BEGIN
	
	UPDATE distribuidores
	SET distribuidores.nombre = nombre_n , distribuidores.direccion = direccion_n , distribuidores.telefono = telefono_n
	WHERE BINARY distribuidores.nombre = BINARY nombre_a AND BINARY distribuidores.direccion = BINARY direccion_a 
	AND BINARY distribuidores.telefono = BINARY telefono_a;


END

//



DROP PROCEDURE IF EXISTS insertValoracion;
CREATE PROCEDURE insertValoracion(
IN presion INT(11),
IN glucosa INT(11) ,
IN peso FLOAT,
IN fecha TIMESTAMP)



	BEGIN
	
	INSERT INTO paciente_valoracion(presion,glucosa,peso) VALUES (presion,glucosa,peso);
	
	UPDATE consultas SET id_valoracion = (SELECT MAX(id) FROM paciente_valoracion) 
	WHERE BINARY consultas.fecha = BINARY fecha;


	END

//

DROP PROCEDURE IF EXISTS loadMedicinasName;
CREATE PROCEDURE loadMedicinasName()

	BEGIN
	
	SELECT nombre
	FROM medicinas;
	
	END
	
//




DELIMITER ;



