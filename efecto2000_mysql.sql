DROP DATABASE efecto2000;
CREATE DATABASE efecto2000;
USE efecto2000;

CREATE TABLE clientes (
 nif   		VARCHAR(10) PRIMARY KEY,
 nombre 	VARCHAR(50) NOT NULL, 
 direccion  	VARCHAR(50),
 poblacion  	VARCHAR(50),
 telefono 	VARCHAR(20)
);

CREATE TABLE productos (
 id		INT	PRIMARY KEY,
 descripcion 	VARCHAR(50) NOT NULL, 
 stockactual  	INT,
 stockminimo  	INT,
 pvp 		FLOAT
);

CREATE TABLE ventas (
 idventa	INT	PRIMARY KEY AUTO_INCREMENT,
 fechaventa 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 cliente  	VARCHAR(10),
 FOREIGN KEY(cliente) REFERENCES clientes(nif)
);

CREATE TABLE lineas (
 idventa  	INT	PRIMARY KEY,
 idproducto  	INT,
 cantidad 	INT,
 FOREIGN KEY(idventa) REFERENCES ventas(idventa),
 FOREIGN KEY(idproducto) REFERENCES productos(id)
);
