DROP DATABASE efecto2000;
CREATE DATABASE efecto2000;
USE efecto2000;

CREATE TABLE clientes (
 nif   		VARCHAR(10) PRIMARY KEY,
 nombre 		VARCHAR(50) NOT NULL, 
 direccion  	VARCHAR(50),
 poblacion  	VARCHAR(50),
 telefono 		VARCHAR(20)
)DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


CREATE TABLE productos (
 id			INT	PRIMARY KEY,
 descripcion 	VARCHAR(50) NOT NULL, 
 stockactual  	INT,
 stockminimo  	INT,
 pvp 			FLOAT
)DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE TABLE ventas (
 idventa		INT AUTO_INCREMENT PRIMARY KEY,
 fechaventa 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 cliente  		VARCHAR(10),
 FOREIGN KEY(cliente) REFERENCES clientes(nif)
)DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE TABLE lineas (
 idventa  		INT,
 idproducto  	INT,
 cantidad 		INT,
 PRIMARY KEY (idventa,idproducto),
 FOREIGN KEY(idventa) REFERENCES ventas(idventa),
 FOREIGN KEY(idproducto) REFERENCES productos(id)
)DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
