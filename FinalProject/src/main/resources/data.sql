-- Sample data for Resume Builder application
-- This script inserts initial data for development and testing

-- Insert sample users (only if they don't exist)
INSERT IGNORE INTO users (email, name, phone, address) VALUES
('john.doe@email.com', 'John Doe', '+1-555-0123', '123 Main St, Anytown, ST 12345'),
('jane.smith@email.com', 'Jane Smith', '+1-555-0456', '456 Oak Ave, Somewhere, ST 67890');

-- Note: Sample resumes and related data will be created through the application
-- to ensure proper XML content generation and entity relationships