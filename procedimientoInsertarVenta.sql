DELIMITER $
CREATE PROCEDURE insertarVenta (IN nifCliente  VARCHAR(10), IN total FLOAT)

BEGIN
    INSERT INTO ventas (cliente,total) values (nifCliente,total);
END $
DELIMITER ;

