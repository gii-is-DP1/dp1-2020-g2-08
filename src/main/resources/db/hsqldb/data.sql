-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('adminShelter','admin',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'adminShelter','adminShelter');

-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('adminShop','admin',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'adminShop','adminShop');


-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- One owner user, named migdurgon with passwor m1gdurg0n
INSERT INTO users(username,password,enabled) VALUES ('migdurgon','m1gdurg0n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'migdurgon','owner');

-- One owner user, named alvgomper1 with password alvgomper1
INSERT INTO users(username,password,enabled) VALUES ('alvgomper1','alvgomper1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'alvgomper1','owner');

-- One owner user, named isamunval with password isamunval
INSERT INTO users(username,password,enabled) VALUES ('isamunval','isamunval',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'isamunval','owner');


-- One owner user, named mangarmar17 with password mangarmar17
INSERT INTO users(username,password,enabled) VALUES ('mangarmar17','mangarmar17',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'mangarmar17','owner');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Juan', 'Casa', '265 Valparaiso', 'Waunakee', '6663332222', 'migdurgon');
INSERT INTO owners VALUES (12, 'Alvaro', 'Gomez', ' Plaza España', 'Sevilla', '654321098', 'alvgomper1');
INSERT INTO owners VALUES (13, 'Manuel', 'Garcia', 'Domingo Molina', 'Sevilla', '654321022', 'mangarmar17');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Paco', '2011-02-10', 2, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Pepe', '2013-02-10', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Lolito', '2013-02-11', 2, 13);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO hotel(id,aforo,ocupadas,city) VALUES (1, 4, 0, 'Sevilla');
INSERT INTO hotel(id,aforo,ocupadas,city) VALUES (2, 5, 0, 'Cordoba');
INSERT INTO hotel(id,aforo,ocupadas,city) VALUES (3, 10, 0, 'Malaga');

INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (1, '2020-12-10', '2020-12-11',1,1,1);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (2, '2020-12-10', '2020-12-12',1,2,2);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (3, '2020-12-10', '2020-12-13',1,3,3);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (4, '2020-12-10', '2020-12-14',1,3,3);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (5, '2020-12-10', '2020-12-15',1,3,3);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (6, '2020-12-10', '2020-12-16',1,3,3);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (7, '2020-12-10', '2020-12-17',1,3,3);
INSERT INTO bookings(id,start_date,end_date,hotel_id,pet_id,owner_id) VALUES (8, '2020-12-10', '2020-12-17',1,3,3);




INSERT INTO products(id, category, in_offer, name, price) VALUES (1, 'Pets', 'No', 'Clown Fish', '6.0');
INSERT INTO products(id, category, in_offer, name, price) VALUES (2, 'Toys', 'Yes', 'Dog´s Ball', '8.75');
INSERT INTO products(id, category, in_offer, name, price) VALUES (3, 'Food', 'Yes', 'Pipes for birds', '5.0');
INSERT INTO products(id, category, in_offer, name, price) VALUES (4, 'Accessories', 'No', 'Dog´s belt', '12.0');



INSERT INTO reviews(id,description,review_date,stars,tittle,hotel_id,owner_id) VALUES (1, 'prueba descripcion 1','2020-02-10',5,'prueba de titulo 1',1,12);
INSERT INTO reviews(id,description,review_date,stars,tittle,hotel_id,owner_id) VALUES (2, 'prueba descripcion 2','2020-03-10',4,'prueba de titulo 2',2,13);
INSERT INTO reviews(id,description,review_date,stars,tittle,hotel_id,owner_id) VALUES (3, 'prueba descripcion 3','2020-04-10',1,'prueba de titulo 3',3,11);

INSERT INTO users(username,password,enabled) VALUES ('mangarmar','mangarmar',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'mangarmar','client');
INSERT INTO clients VALUES (1, 'Manuel', 'Garcia', 'Domingo Molina', 'Sevilla','gmail@gmail.com','mangarmar','1111111','mangarmar','654321022','mangarmar');

--



INSERT INTO coupons VALUES(1,50,'2021-12-10');
INSERT INTO coupons VALUES(2,60,'2022-10-10');
INSERT INTO coupons VALUES(3,70,'2023-07-10');
INSERT INTO coupons VALUES(4,80,'2024-03-10');

INSERT INTO clients_coupons VALUES(1,1);
INSERT INTO clients_coupons VALUES(1,2);

INSERT INTO orders VALUES(1, 'Prim', 'Sevilla', 'España', '2020-12-10','2020-12-11','41410','12','In progress', 1, null);
INSERT INTO producto_vendido VALUES(1, 1, 'Dog´s Ball','8.75',1);
INSERT INTO producto_vendido VALUES(2, 1, 'Pipes for birds','5.0',1);
INSERT INTO producto_vendido VALUES(3, 1, 'Dog´s Ball','8.75',1);
INSERT INTO producto_vendido VALUES(4, 1, 'Pipes for birds','5.0',1);
INSERT INTO producto_vendido VALUES(5, 1, 'Clown Fish','8.75',1);
INSERT INTO producto_vendido VALUES(6, 1, 'Clown Fish','5.0',1);
INSERT INTO producto_vendido VALUES(7, 1, 'Dog´s belt','8.75',1);
INSERT INTO producto_vendido VALUES(8, 1, 'Dog´s belt','5.0',1);


INSERT INTO product_reviews VALUES(1, 4, 1, 2, 1);
INSERT INTO product_reviews VALUES(2, 2, 1, 3, 2);
INSERT INTO product_reviews VALUES(3, 3, 1, 2, 3);
INSERT INTO product_reviews VALUES(4, 4, 1, 3, 4);
INSERT INTO product_reviews VALUES(5, 5, 1, 1, 5);
INSERT INTO product_reviews VALUES(6, 4, 1, 1, 6);
INSERT INTO product_reviews VALUES(7, 5, 1, 4, 7);
INSERT INTO product_reviews VALUES(8, 5, 1, 4, 8);


INSERT INTO shelter(id,aforo,city) VALUES (1, 4,  'Sevilla');
INSERT INTO shelter(id,aforo,city) VALUES (2, 4,  'Malaga');
INSERT INTO shelter(id,aforo,city) VALUES (3, 4,  'Cordoba');

INSERT INTO animals(id,name,birth_date,type_id,shelter_id,shelter_date, description, sex,image_url,state) VALUES (1, 'Sterling', '2013-02-11', 2, 1,'2020-02-11','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.','male','https://www.losanimales.org/wp-content/uploads/2018/03/Perros.jpg', 'avaible');
INSERT INTO animals(id,name,birth_date,type_id,shelter_id,shelter_date, description, sex,image_url,state) VALUES (2, 'Kanté', '2013-02-11', 2, 1,'2020-02-15','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.','female','https://static.wikia.nocookie.net/religion-del-celio/images/8/8d/Que-comen-los-perros.jpg/revision/latest?cb=20180128073558&path-prefix=es', 'avaible');
INSERT INTO animals(id,name,birth_date,type_id,shelter_id,shelter_date, description, sex,image_url,state) VALUES (3, 'Van dijk', '2013-02-11', 2, 1,'2020-02-18','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.','female','https://www.wikipets.es/wp-content/uploads/sites/default/files/library/dogo_argentino_-_razas_de_perro-320x214.jpg', 'avaible');

