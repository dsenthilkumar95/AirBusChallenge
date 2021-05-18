DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS USER;
DROP SEQUENCE IF EXISTS PRODUCT_SEQUENCE;

CREATE TABLE USER (
    id BIGINT default USER_SEQUENCE.nextval PRIMARY KEY,
    USERNAME VARCHAR(250) NOT NULL,
    PASSWORD VARCHAR(250) NOT NULL
);

CREATE SEQUENCE PRODUCT_SEQUENCE START 1 INCREMENT 1;

CREATE TABLE PRODUCT (
                              id BIGINT default PRODUCT_SEQUENCE.nextval PRIMARY KEY,
                              category VARCHAR(250) NOT NULL,
                              name VARCHAR(250) NOT NULL,
                              description VARCHAR(250) DEFAULT NULL,
                              units INT NOT NULL
);
INSERT INTO PRODUCT (category, name, description, units) VALUES
('Commercial', 'A320', 'Passenger aircraft family', 2),
('Commercial', 'A380', 'Passenger aircraft family', 3),
('Space', 'Sentinel', 'Satellite family', 1),
('Helicopter', 'H135', 'Light twin', 2),
('Helicopter', 'H125', 'Intermediate Single', 3);
