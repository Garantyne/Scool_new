CREATE TABLE cars
(
    id BIGSERIAL PRIMARY KEY ,
    brand VARCHAR(15) NOT NULL ,
    model VARCHAR(15) NOT NULL ,
    price INT CHECK(price>0)NOT NULL
);

CREATE TABLE owners
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(20) NOT NULL ,
    age INT CHECK ( age>17 ) NOT NULL,
    has_drive_license BOOLEAN DEFAULT true NOT NULL,
    cars_id BIGINT REFERENCES cars(id) NOT NULL
);
insert into cars(brand, model, price)
VALUES ('lada','vesta', 120000);
insert into owners(name, age, cars_id)
VALUES ('Petro',25,1),
       ('Voti',22,1);