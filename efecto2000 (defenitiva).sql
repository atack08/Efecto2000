-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Servidor: localhost:8889
-- Tiempo de generación: 29-12-2016 a las 20:23:05
-- Versión del servidor: 5.5.42
-- Versión de PHP: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de datos: `efecto2000`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarLinea`(IN `idVenta` INT, IN `idProducto` INT, IN `cantidad` INT)
BEGIN
    INSERT INTO lineas (idventa,idproducto,cantidad) values (idVenta,idProducto,cantidad);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarVenta`(IN fecha TIMESTAMP, IN nifCliente  VARCHAR(10), IN total FLOAT)
BEGIN
    INSERT INTO ventas (fechaventa,cliente,total) values (fecha,nifCliente,total);
END$$

--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `cambioStock`(`idProducto` INT, `unidadesVendidas` INT) RETURNS int(11)
BEGIN
    
    DECLARE dif int;
   
    UPDATE productos SET stockactual = (stockactual - unidadesVendidas) where id = idProducto;
    SELECT (stockactual - stockminimo) into dif from productos  where id = idProducto;
   
    IF dif < 0 THEN
        RETURN 1;
    END IF;
   
    RETURN 0; 
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `nif` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `poblacion` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `telefono` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`nif`, `nombre`, `direccion`, `poblacion`, `telefono`) VALUES
('00000000-0', 'Anonimo', NULL, NULL, NULL),
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineas`
--

CREATE TABLE `lineas` (
  `idventa` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lineas`
--

INSERT INTO `lineas` (`idventa`, `idproducto`, `cantidad`) VALUES
(22, 122122, 1),
(22, 122228, 3),
(23, 122634, 11),
(24, 122634, 11),
(25, 115544, 6),
(25, 122228, 2),
(25, 122634, 1),
(25, 123123, 1),
(26, 122122, 2),
(26, 122228, 1),
(26, 122555, 1),
(26, 122659, 2),
(27, 122187, 1),
(27, 122228, 1),
(27, 122555, 1),
(27, 142855, 1),
(28, 122122, 1),
(28, 122555, 1),
(28, 122634, 1),
(28, 122994, 3),
(29, 122122, 1),
(29, 122634, 1),
(29, 222145, 1),
(30, 123123, 6),
(30, 142855, 4),
(30, 325611, 4),
(30, 325623, 6),
(31, 122547, 3),
(32, 122634, 1),
(33, 122122, 3),
(34, 122122, 3),
(34, 122228, 2),
(34, 122634, 1),
(35, 122187, 1),
(35, 122994, 2),
(36, 122122, 2),
(36, 522166, 2),
(37, 122122, 2),
(38, 122122, 2),
(38, 123659, 2),
(39, 122122, 2),
(39, 325623, 4),
(40, 122122, 1),
(41, 122122, 1),
(41, 122547, 3),
(42, 122122, 1),
(42, 122634, 1),
(43, 122122, 7),
(44, 122122, 2),
(45, 122122, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `stockactual` int(11) DEFAULT NULL,
  `stockminimo` int(11) DEFAULT NULL,
  `pvp` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `descripcion`, `stockactual`, `stockminimo`, `pvp`) VALUES
(115544, 'Samsung Galaxy Note 7', 16, 2, 599.8),
(122122, 'Apple Iphone 7 Plus', 20, 2, 769.9),
(122187, 'Apple Iphone 6S Plus', 15, 2, 699.99),
(122228, 'Nvidia GTX 1060', 15, 2, 360),
(122547, 'CPU Intel i5-6600K 4.00Gh', 10, 2, 210),
(122555, 'Portátil HP Pavilion 1150', 16, 1, 850.9),
(122634, 'CPU Intel i7-6600 3.80Gh', 20, 2, 220),
(122659, 'CPU Intel i7-6700K 4.00Ghz', 13, 2, 350),
(122994, '2x8GB RAM DDR4 2133Mhz', 27, 10, 89.99),
(123123, 'Monitor Samsung 4k', 12, 1, 490),
(123659, 'Nvidia GTX 1080', 14, 1, 650),
(123748, 'Nvidia GTX 1070', 25, 1, 520),
(123999, 'Placa Asus Z150-VE', 16, 1, 110.5),
(142855, 'Placa Base Asus Z170', 25, 1, 125.9),
(222145, 'Nvidia GTX 980 4GB', 19, 2, 340.2),
(325611, 'Intel i5 6500 3.00Ghz', 18, 2, 215.99),
(325623, 'Intel i5 6600k 4.00Ghz', 16, 2, 250.99),
(522166, 'Caja Atx AeroCool Z450', 10, 2, 120);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL,
  `fechaventa` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cliente` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`idventa`, `fechaventa`, `cliente`, `total`) VALUES
(22, '2016-11-19 11:48:36', '42598326-M', 1849.9),
(23, '2016-11-19 19:00:52', '48025698-B', 650.55),
(24, '2016-11-20 08:11:19', '48025698-B', 650.55),
(25, '2016-11-20 08:14:49', '47285559-B', 5028.8),
(26, '2016-11-20 08:18:46', '47210124-C', 3450.7),
(27, '2016-11-20 08:22:18', '42598326-M', 2036.79),
(28, '2016-11-20 08:24:13', '47523669-H', 2110.77),
(29, '2016-11-20 08:25:48', '47598213-V', 1330.1),
(30, '2016-11-20 08:38:35', '42598326-M', 5813.5),
(31, '2016-11-20 09:24:56', '77589563-V', 630),
(32, '2016-11-20 13:41:08', '47523669-H', 220),
(33, '2016-11-20 19:03:51', '42598326-M', 2309.7),
(34, '2016-11-20 19:04:38', '47210124-C', 3249.7),
(35, '2016-11-20 19:19:49', '77589563-V', 879.97),
(36, '2016-11-20 19:24:35', '44458997-M', 1779.8),
(37, '2016-12-22 23:36:37', '00000000-0', 3079.6),
(38, '2016-12-22 23:39:47', '00000000-0', 2839.8),
(39, '2016-12-22 23:42:19', '00000000-0', 2543.76),
(40, '2016-12-22 23:45:55', '00000000-0', 1539.8),
(41, '2016-12-23 00:04:18', '00000000-0', 1399.9),
(42, '2016-12-23 09:45:28', '00000000-0', 989.9),
(43, '2016-12-27 19:34:31', '00000000-0', 5389.3),
(44, '2016-12-28 19:37:49', '00000000-0', 1539.8),
(45, '2016-12-29 18:39:02', '00000000-0', 769.9);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`nif`);

--
-- Indices de la tabla `lineas`
--
ALTER TABLE `lineas`
  ADD PRIMARY KEY (`idventa`,`idproducto`),
  ADD KEY `idproducto` (`idproducto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`idventa`),
  ADD KEY `cliente` (`cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `idventa` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=46;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `lineas`
--
ALTER TABLE `lineas`
  ADD CONSTRAINT `lineas_ibfk_1` FOREIGN KEY (`idventa`) REFERENCES `ventas` (`idventa`),
  ADD CONSTRAINT `lineas_ibfk_2` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`nif`);
