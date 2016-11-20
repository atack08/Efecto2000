DELIMITER $
CREATE PROCEDURE insertarVenta (IN fecha TIMESTAMP, IN nifCliente  VARCHAR(10), IN total FLOAT)

BEGIN
    INSERT INTO ventas (fechaventa,cliente,total) values (fecha,nifCliente,total);
END $
DELIMITER ;

