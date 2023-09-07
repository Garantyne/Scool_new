-- liquibase formatted sql

-- changeset Student: 1
CREATE TABLE student (
    name   TEXT,
    id  INT PRIMARY KEY
);
-- changeset Student: 2
CREATE INDEX id_student_name ON student(name);

-- changeset Student: 3
CREATE TABLE faculty (
      name   TEXT,
      color   TEXT,
      id  INT PRIMARY KEY
);
-- changeset Student: 4
CREATE INDEX id_faculty_name_color ON faculty(name,color);
