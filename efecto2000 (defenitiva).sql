-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-11-2016 a las 10:53:59
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

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
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarLinea` (IN `idVenta` INT, IN `idProducto` INT, IN `cantidad` INT)  BEGIN
    INSERT INTO lineas (idventa,idproducto,cantidad) values (idVenta,idProducto,cantidad);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarVenta` (IN `fecha` TIMESTAMP, IN `nifCliente` VARCHAR(10), IN `total` FLOAT)  BEGIN
    INSERT INTO ventas (fechaventa,cliente,total) values (fecha,nifCliente,total);
END$$

--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `cambioStock` (`idProducto` INT, `unidadesVendidas` INT) RETURNS INT(11) BEGIN
    
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL,
  `fechaventa` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cliente` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
  MODIFY `idventa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
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
