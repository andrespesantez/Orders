INSERT INTO producto(id, sku, moneda, nombre, precio, stock) VALUES (1,'A1','EUR','Samsung TV24',150, 5);
INSERT INTO producto(id, sku, moneda, nombre, precio, stock) VALUES (2,'A2','EUR','Samsung TV32',300, 5);
INSERT INTO producto(id, sku, moneda, nombre, precio, stock) VALUES (3,'A3','EUR','Samsung TV40',650, 5);
INSERT INTO producto(id, sku, moneda, nombre, precio, stock) VALUES (4,'A4','EUR','Samsung TV55',1200, 5);

INSERT INTO orden(id, codigo, total, moneda) VALUES (1,'O1', 1100, 'EUR');
INSERT INTO orden(id, codigo, total, moneda) VALUES (2,'O2', 1850, 'EUR');

INSERT INTO orden_detalle(id, cantidad, id_orden, id_producto) VALUES (1,1,1,1);
INSERT INTO orden_detalle(id, cantidad, id_orden, id_producto) VALUES (2,1,1,2);
INSERT INTO orden_detalle(id, cantidad, id_orden, id_producto) VALUES (3,1,1,3);

INSERT INTO orden_detalle(id, cantidad, id_orden, id_producto) VALUES (4,1,2,3);
INSERT INTO orden_detalle(id, cantidad, id_orden, id_producto) VALUES (5,1,2,4);