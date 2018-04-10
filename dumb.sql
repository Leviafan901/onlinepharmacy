USE pharmacy;
INSERT INTO user (name, lastname, login, password, role) VALUES ('Alexei', 'Sosenkov', 'client', MD5('client'), 'client');
INSERT INTO user (name, lastname, login, password, role) VALUES ('Alexei', 'Sosenkov', 'admin', MD5('admin'), 'admin');
INSERT INTO user (name, lastname, login, password, role) VALUES ('Alexei', 'Sosenkov', 'doctor', MD5('doctor'), 'doctor');

INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Ацицеф', 5, 4, 300, true, 10, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Азикар', 3, 2, 500, true, 15, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Азитроцин', 10, 4, 250, false, 5, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Аксетин', 1, 2, 750, false, 7, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Амикацин', 0, 6, 250, false, 4, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Амоксикар', 19, 3, 250, false, 6, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Аспаркам', 5, 7, 50, false, 5.3, false);
INSERT INTO medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES ('Фауказол', 3, 7, 150, false, 5.8, false);

INSERT INTO prescription (doctor_id, user_id, medicine_id, creation_date, expiration_date, comment) VALUES (2, 1, 1, "2018-03-10", "2018-04-10", 'For Alexei Sosenkov');
