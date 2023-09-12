-- liquibase formatted sql

-- changeset Student: 1
CREATE INDEX id_student_name ON student(name);

-- changeset Student: 2
CREATE INDEX id_faculty_name_color ON faculty(name,color);
