-- Resume Builder Database Schema
-- This script creates the database schema for the Resume Builder application

-- Create database if it doesn't exist (handled by connection URL parameter)
-- CREATE DATABASE IF NOT EXISTS resume_builder;
-- USE resume_builder;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_email (email)
);

-- Resumes table
CREATE TABLE IF NOT EXISTS resumes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    template_type ENUM('CLASSIC', 'MODERN', 'CREATIVE') NOT NULL,
    xml_content LONGTEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_resume_user_id (user_id),
    INDEX idx_resume_template_type (template_type),
    INDEX idx_resume_created_date (created_date)
);

-- Education table
CREATE TABLE IF NOT EXISTS education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resume_id BIGINT NOT NULL,
    institution VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    field_of_study VARCHAR(255),
    start_date DATE,
    end_date DATE,
    gpa DECIMAL(3,2),
    FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
    INDEX idx_education_resume_id (resume_id)
);

-- Work Experience table
CREATE TABLE IF NOT EXISTS work_experience (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resume_id BIGINT NOT NULL,
    company VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    description TEXT,
    is_internship BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
    INDEX idx_work_experience_resume_id (resume_id)
);

-- Skills table
CREATE TABLE IF NOT EXISTS skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resume_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    type ENUM('TECHNICAL', 'SOFT_SKILL', 'LANGUAGE') NOT NULL,
    proficiency_level VARCHAR(50),
    FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
    INDEX idx_skills_resume_id (resume_id),
    INDEX idx_skills_type (type)
);