USE projeto_integrador;

INSERT INTO warehouse VALUES (NULL, 'Rua Almeida 259', 'Armazem A'),
                             (NULL, 'Rua Botanica 468', 'Armazem B'),
                             (NULL, 'Rua Contagem 1024', 'Armazem C');

INSERT INTO agent VALUES (NULL, 'Arnaldo@email.com', 'Arnaldo Pereira', '1111-1111', 1),
                         (NULL, 'Rodrigo@email.com', 'Rodrigo da Silva', '2222-2222', 2),
                         (NULL, 'Patricia@email.com', 'Patricia Ferreira', '3333-333', 3);

INSERT INTO section VALUES (NULL, 80, 100, 'Fresco', 1),
                           (NULL, 115, 200, 'Refrigerado', 1),
                           (NULL, 95, 300, 'Congelado', 1),
                           (NULL, 55, 800, 'Fresco', 2),
                           (NULL, 75, 200, 'Refrigerado', 2),
                           (NULL, 0, 150, 'Congelado', 2),
                           (NULL, 0, 600, 'Congelado', 3);

INSERT INTO product VALUES (NULL, 'Frutas', 'Maçã', 20.10, 'Fresco'),
                           (NULL, 'Iogurte', 'Laticínios', 20.10, 'Refrigerado'),
                           (NULL, 'Frango', 'Peito de Frango', 20.10, 'Congelado'),
                           (NULL, 'Frutas', 'Uva', 20.10,'Fresco'),
                           (NULL, 'Laticínios', 'Manteiga', 20.10, 'Refrigerado'),
                           (NULL, 'Processados', 'Nuggets', 20.10, 'Refrigerado'),
                           (NULL, 'Frutas', 'Morango', 20.10, 'Fresco'),
                           (NULL, 'Frios', 'Presunto', 20.10, 'Refrigerado'),
                           (NULL, 'Sobremesas', 'Sorvete', 20.10, 'Congelado');

INSERT INTO customer VALUES (NULL,'111.111.111-11','Alberto@email.com','Alberto','1111-1111'),
                            (NULL,'222.222.222-22','Bernado@email.com','Bernardo','2222-2222'),
                            (NULL,'333.333.333-33','Carolina@email.com','Carolina', '3333-3333');

INSERT INTO order_entry VALUES (NULL, "2022-08-08", 1),
                               (NULL, "2022-08-09", 2),
                               (NULL, "2022-08-10", 3),
                               (NULL, "2022-08-11", 4),
                               (NULL, "2022-08-12", 5);

INSERT INTO batch VALUES (NULL, 30, 17.0, "2022-09-30", 30, "2022-07-15", "12:06:50", 10.0, 1, 1),
                         (NULL, 50, 24.0, "2022-10-12", 50, "2022-07-15", "15:30:54", 20.0, 1, 4),
                         (NULL, 15, 8.0, "2022-12-31", 15, "2022-07-15", "11:12:30", 1.0, 2, 8),
                         (NULL, 100, 1.0, "2023-02-18", 100, "2022-07-15", "10:22:05", 0.0, 2, 2),
                         (NULL, 25, 10.0, "2022-11-25", 25, "2022-07-15", "16:19:20", 15.0, 3, 9),
                         (NULL, 70, 12.0, "2023-01-05", 98, "2022-07-15", "17:45:10", 8.0, 3, 3),
                         (NULL, 55, 9.0, "2022-08-30", 57, "2022-07-15", "14:39:40", 12.0, 4, 7),
                         (NULL, 60, 10.0, "2022-08-30", 60, "2022-07-15", "14:39:40", 15.0, 5, 8),
                         (NULL, 15, 1.0, "2023-01-15", 89, "2022-07-15", "14:39:40", 5.0, 5, 6);

INSERT INTO cart VALUES (NULL, "2022-08-08", "OPEN", 1),
                        (NULL, "2022-08-08", "OPEN", 1),
                        (NULL, "2022-08-08", "OPEN", 1),
                        (NULL, "2022-08-08", "OPEN", 1),
                        (NULL, "2022-08-08", "OPEN", 1);

INSERT INTO product_cart VALUES (NULL, 2, 1, 1),
                         (NULL, 3, 1, 3),
                         (NULL, 1, 2, 5),
                         (NULL, 5, 2, 7),
                         (NULL, 2, 3, 9),
                         (NULL, 3, 3, 2),
                         (NULL, 4, 4, 4),
                         (NULL, 1, 4, 6),
                         (NULL, 1, 5, 8),
                         (NULL, 2, 5, 1);