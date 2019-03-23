ALTER SEQUENCE jes_test.certificetes_id_seq RESTART WITH 1;
UPDATE jes_test.certificates SET id=nextval('jes_test.certificetes_id_seq');

ALTER SEQUENCE jes_test.tags_tag_id_seq RESTART WITH 1;
UPDATE jes_test.tags SET id=nextval('jes_test.tags_tag_id_seq');

INSERT INTO jes_test.tags(name) values ('TAG1');
INSERT INTO jes_test.tags(name) values ('TAG2');
INSERT INTO jes_test.tags(name) values ('TAG3');
INSERT INTO jes_test.tags(name) values ('TAG4');
INSERT INTO jes_test.tags(name) values ('TAG5');
INSERT INTO jes_test.tags(name) values ('TAG6');
INSERT INTO jes_test.tags(name) values ('TAG7');
INSERT INTO jes_test.tags(name) values ('TAG8');
INSERT INTO jes_test.tags(name) values ('TAG9');
INSERT INTO jes_test.tags(name) values ('TAG10');

INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME1', 'DESCRIPTION1', 20.0, '2012-02-01', NULL, 10);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME2', 'DESCRIPTION2', 21.0, '2013-02-02', NULL, 12);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME3', 'DESCRIPTION3', 22.0, '2011-02-21', NULL, 14);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME4', 'DESCRIPTION4', 23.0, '2012-02-22', NULL, 16);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME5', 'DESCRIPTION5', 24.0, '2012-02-02', NULL, 18);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME6', 'DESCRIPTION6', 25.0, '2012-02-02', NULL, 20);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME7', 'DESCRIPTION7', 26.0, '2015-02-23', NULL, 22);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME8', 'DESCRIPTION8', 27.0, '2016-02-12', NULL, 24);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME9', 'DESCRIPTION9', 28.0, '2012-02-14', NULL, 26);
INSERT INTO jes_test.certificates(name, description, price, creation_date, modification_date, duration) VALUES ('NAME10', 'DESCRIPTION10', 29.0, '2012-02-02', NULL, 28);
