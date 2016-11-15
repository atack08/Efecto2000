delimiter |
 CREATE TRIGGER triggerStockMinimo AFTER UPDATE ON productos
  FOR EACH ROW 
DECLARE
    DECLARE result int(10);
    DECLARE stockM int(10);
BEGIN
    select stockminimo into stockM from productos


    IF NEW.stockactual < stockminimo THEN  
        SET result = sys_exec('java -jar /Users/atack08/Desktop/basico.jar');
     -- other kind of works and checks...
    END IF;
END;
|