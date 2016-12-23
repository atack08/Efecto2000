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
 total          FLOAT NOT NULL,
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

INSERT INTO `clientes` (`nif`, `nombre`, `direccion`, `poblacion`, `telefono`) VALUES
('42598326-M', 'Javier Serrano Graziati', 'Avda Iruña Veleia 1', 'Vitoria', '945060205'),
('44458997-M', 'Marta Rojas Fernandez', 'Cl Madeo Lopez', 'Madrid', '913652250'),
('45896321-N', 'Lucia Mariñas Paolin', 'Avda Mateo Renzi 1', 'Madrid', '917356922'),
('47210124-C', 'Daniel Pedrosa Ramal', 'Cl Repsol Honda', 'Madrid', '917325566'),
('47285559-B', 'Beatriz Barragán Sultán', 'Cl Zuazo 23', 'Vitoria', '945222356'),
('47523669-H', 'Fernando Delgado Butron', 'CL Martin 66', 'Vitoria', '945061232'),
('47598213-V', 'Estefan Lugotz Prajl', 'CL Marco Polo 12', 'Barcelona', '934569823'),
('48025698-B', 'Rosa Ramos Mala', 'Avda Pedro Santos 1', 'Castellon', '921657845'),
('48069598-K', 'Rosa Pereira Malagón', 'Avda Pedro Santos 12', 'Castellon', '921657812'),
('48255596-M', 'Mariano Rajoy Brey', 'Avda Pontevedra 1', 'Pontevedra', '914652036'),
('48555598-B', 'Marcos Pereira Malagón', 'Avda Pedro Santos 12', 'Castellon', '921657812'),
('51489711-G', 'Francisco Perez Rubio', 'Avda Marques Yullc', 'Barcelona', '934589633'),
('51689544-K', 'Marcial Martinez Geres', 'Cl Madrid 193', 'Madrid', '945098258'),
('51698790-J', 'Maria Sevilla Perez', 'CL Mateo Moraza 33', 'Vitoria', '945065987'),
('55269847-N', 'Pedro Apaolaza Palencia', 'CL Madrid', 'Vitoria', '945060512'),
('75584896-H', 'Marcos Aparicio Cortes', 'CL Segura 66', 'Madrid', '915896422'),
('77589563-V', 'Marta Campillo Segura', 'Avda Poblado 1', 'Madrid', '916589622'),
('77896326-B', 'esteban Muriel Lopez', 'CL Esterai 22', 'Madrid', '917345689');

INSERT INTO `productos` (`id`, `descripcion`, `stockactual`, `stockminimo`, `pvp`) VALUES
(115544, 'Samsung Galaxy Note 7', 4, 2, 599.8),
(122122, 'Apple Iphone 7 Plus', 14, 2, 769.9),
(122187, 'Apple Iphone 6S Plus', 8, 2, 699.99),
(122228, 'Nvidia GTX 1060', 12, 2, 360),
(122547, 'CPU Intel i5-6600K 4.00Gh', 11, 2, 210),
(122555, 'Portátil HP Pavilion 1150', 3, 1, 850.9),
(122634, 'CPU Intel i7-6600 3.80Gh', 1, 2, 220),
(122659, 'CPU Intel i7-6700K 4.00Ghz', 15, 2, 350),
(122994, '2x8GB RAM DDR4 2133Mhz', 32, 10, 89.99),
(123123, 'Monitor Samsung 4k', 8, 1, 490),
(123659, 'Nvidia GTX 1080', 16, 1, 650),
(123748, 'Nvidia GTX 1070', 5, 1, 520),
(123999, 'Placa Asus Z150-VE', 6, 1, 110.5),
(142855, 'Placa Base Asus Z170', 5, 1, 125.9),
(222145, 'Nvidia GTX 980 4GB', 10, 2, 340.2),
(325611, 'Intel i5 6500 3.00Ghz', 4, 2, 215.99),
(325623, 'Intel i5 6600k 4.00Ghz', 7, 2, 250.99);



