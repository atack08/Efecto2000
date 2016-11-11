CREATE TABLE clientes (
 nif   		VARCHAR(10) PRIMARY KEY,
 nombre 		VARCHAR(50) NOT NULL, 
 direccion  	VARCHAR(50),
 poblacion  	VARCHAR(50),
 telefono 		VARCHAR(20)
);

CREATE TABLE productos (
 id			INTEGER	PRIMARY KEY,
 descripcion 	VARCHAR(50) NOT NULL, 
 stockactual  	INTEGER,
 stockminimo  	INTEGER,
 pvp 			FLOAT
);

CREATE TABLE ventas (
 idventa		INTEGER	PRIMARY KEY AUTOINCREMENT,
 fechaventa 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
 cliente  		VARCHAR(10) NOT NULL,
 FOREIGN KEY(cliente) REFERENCES clientes(nif)
);

CREATE TABLE lineas (
 idventa  		INTEGER,
 idproducto  	INTEGER,
 cantidad 		INTEGER,
 PRIMARY KEY (idventa,idproducto),
 FOREIGN KEY(idventa) REFERENCES ventas(idventa),
 FOREIGN KEY(idproducto) REFERENCES productos(id)
);


