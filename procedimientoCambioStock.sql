DELIMITER $$
CREATE FUNCTION cambioStock (idProducto INT, unidadesVendidas INT) RETURNS INT
BEGIN
    
    DECLARE dif int;
   
    UPDATE productos SET stockactual = (stockactual - unidadesVendidas) where id = idProducto;
    SELECT (stockactual - stockminimo) into dif from productos  where id = idProducto;
   
    IF dif < 0 THEN
        RETURN 1;
    END IF;
   
    RETURN 0; 
END 
$$
DELIMITER ;

