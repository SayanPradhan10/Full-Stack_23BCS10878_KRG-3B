CREATE DATABASE IF NOT EXISTS fre;
USE fre;

CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(255),
    phone BIGINT,
    about TEXT,
    skills TEXT,
    profile_id BIGINT,
    registration TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS project (...);
CREATE TABLE IF NOT EXISTS bid (...);
CREATE TABLE IF NOT EXISTS attachments (...);
