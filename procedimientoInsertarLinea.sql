DELIMITER $
CREATE PROCEDURE insertarLinea (IN idVenta INT, IN idProducto INT, IN cantidad INT)
BEGIN
    INSERT INTO lineas (idventa,idproducto,cantidad) values (idVenta,idProducto,cantidad);
END $
DELIMITER ;

