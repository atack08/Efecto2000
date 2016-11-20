-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Servidor: localhost:8889
-- Tiempo de generación: 19-11-2016 a las 20:03:15
-- Versión del servidor: 5.5.42
-- Versión de PHP: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

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
(13, 123123, 2),
(13, 123659, 10),
(14, 122122, 10),
(14, 122187, 1),
(14, 122547, 4),
(15, 122122, 10),
(15, 122187, 1),
(15, 122547, 4),
(16, 122122, 10),
(16, 122187, 1),
(16, 122547, 4),
(17, 122122, 10),
(17, 122187, 1),
(17, 122547, 4),
(18, 122122, 10),
(18, 122187, 1),
(18, 122547, 4),
(19, 122122, 10),
(19, 122187, 1),
(19, 122547, 4),
(20, 122122, 10),
(20, 122187, 1),
(20, 122547, 4),
(21, 122122, 10),
(21, 122187, 15),
(21, 122547, 4),
(22, 122122, 1),
(22, 122228, 3),
(23, 122634, 11);

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
(115544, 'Samsung Galaxy Note 7', 6, 2, 599.8),
(122122, 'Apple Iphone 7 Plus', 15, 2, 769.9),
(122187, 'Apple Iphone 6S Plus', 8, 2, 699.99),
(122228, 'Nvidia GTX 1060', 13, 2, 360),
(122547, 'CPU Intel i5-6600K 4.00Gh', 12, 2, 210),
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL,
  `fechaventa` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cliente` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`idventa`, `fechaventa`, `cliente`, `total`) VALUES
(3, '2016-11-16 09:30:45', '45896321-N', 125.36),
(4, '2016-11-16 09:39:34', '48025698-B', 134.36),
(5, '2016-11-16 09:41:54', '48025698-B', 134.36),
(6, '2016-11-16 09:41:58', '48025698-B', 134.36),
(7, '2016-11-16 09:41:59', '48025698-B', 134.36),
(8, '2016-11-16 09:42:00', '48025698-B', 134.36),
(9, '2016-11-16 09:42:01', '48025698-B', 134.36),
(10, '2016-11-16 09:42:03', '48025698-B', 134.36),
(11, '2016-11-16 09:43:28', '48025698-B', 134.36),
(12, '2016-11-16 09:43:41', '48025698-B', 134.36),
(13, '2016-11-16 10:20:22', '48025698-B', 650.55),
(14, '2016-11-16 10:24:06', '48025698-B', 650.55),
(15, '2016-11-16 11:53:32', '48025698-B', 650.55),
(16, '2016-11-16 11:53:42', '48025698-B', 650.55),
(17, '2016-11-16 11:53:45', '48025698-B', 650.55),
(18, '2016-11-16 11:53:46', '48025698-B', 650.55),
(19, '2016-11-16 11:53:47', '48025698-B', 650.55),
(20, '2016-11-16 11:53:48', '48025698-B', 650.55),
(21, '2016-11-16 11:53:56', '48025698-B', 650.55),
(22, '2016-11-19 11:48:36', '42598326-M', 1849.9),
(23, '2016-11-19 19:00:52', '48025698-B', 650.55);

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
  MODIFY `idventa` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
