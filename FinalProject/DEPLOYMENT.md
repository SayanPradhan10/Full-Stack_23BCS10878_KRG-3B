# Resume Builder Deployment Guide

## Prerequisites

### Database Setup
1. **MySQL 8.0 Installation**
   - Install MySQL 8.0 (MySQL80 service)
   - Create a database user with appropriate permissions
   - Ensure MySQL service is running

2. **Database Configuration**
   ```sql
   CREATE DATABASE resume_builder;
   CREATE DATABASE resume_builder_dev;
   CREATE DATABASE resume_builder_prod;
   
   CREATE USER 'resumebuilder'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON resume_builder*.* TO 'resumebuilder'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Java Environment
- Java 11 or higher
- Maven 3.6 or higher

## Configuration Profiles

### Development Environment
```bash
# Run with development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or set environment variable
export SPRING_PROFILES_ACTIVE=dev
java -jar target/resume-builder-0.0.1-SNAPSHOT.jar
```

### Production Environment
```bash
# Set environment variables
export SPRING_PROFILES_ACTIVE=prod
export DB_USERNAME=resumebuilder
export DB_PASSWORD=your_secure_password
export FILE_UPLOAD_DIR=/var/app/uploads
export LOG_FILE=/var/log/resume-builder/resume-builder.log

# Run application
java -jar target/resume-builder-0.0.1-SNAPSHOT.jar
```

## Environment Variables

### Database Configuration
- `DB_USERNAME`: Database username (default: root)
- `DB_PASSWORD`: Database password (default: password)

### File Storage Configuration
- `FILE_UPLOAD_DIR`: Base upload directory (default: ./uploads)
- `FILE_PDF_DIR`: PDF storage directory (default: ./uploads/pdfs)
- `FILE_XML_DIR`: XML storage directory (default: ./uploads/xml)
- `FILE_TEMP_DIR`: Temporary files directory (default: ./uploads/temp)

### Server Configuration
- `SERVER_PORT`: Application port (default: 8080)
- `LOG_FILE`: Log file location (default: ./logs/resume-builder.log)

## Build and Deployment

### Local Development
```bash
# Clone repository
git clone <repository-url>
cd resume-builder

# Build application
mvn clean compile

# Run tests
mvn test

# Run application in development mode
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Build
```bash
# Build production JAR
mvn clean package -Pprod

# Create necessary directories
sudo mkdir -p /var/app/uploads/{pdfs,xml,temp}
sudo mkdir -p /var/log/resume-builder

# Set permissions
sudo chown -R app-user:app-group /var/app/uploads
sudo chown -R app-user:app-group /var/log/resume-builder

# Deploy JAR file
sudo cp target/resume-builder-0.0.1-SNAPSHOT.jar /opt/resume-builder/
```

### Systemd Service (Linux)
Create `/etc/systemd/system/resume-builder.service`:
```ini
[Unit]
Description=Resume Builder Application
After=network.target mysql.service

[Service]
Type=simple
User=app-user
Group=app-group
WorkingDirectory=/opt/resume-builder
ExecStart=/usr/bin/java -jar resume-builder-0.0.1-SNAPSHOT.jar
Environment=SPRING_PROFILES_ACTIVE=prod
Environment=DB_USERNAME=resumebuilder
Environment=DB_PASSWORD=your_secure_password
Environment=FILE_UPLOAD_DIR=/var/app/uploads
Environment=LOG_FILE=/var/log/resume-builder/resume-builder.log
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Enable and start service:
```bash
sudo systemctl daemon-reload
sudo systemctl enable resume-builder
sudo systemctl start resume-builder
sudo systemctl status resume-builder
```

## Directory Structure

### Application Directories
```
/opt/resume-builder/          # Application JAR location
/var/app/uploads/             # File storage
├── pdfs/                     # Generated PDF files
├── xml/                      # Generated XML files
└── temp/                     # Temporary files
/var/log/resume-builder/      # Application logs
```

### Development Directories
```
./dev-uploads/                # Development file storage
├── pdfs/
├── xml/
└── temp/
./dev-logs/                   # Development logs
```

## Monitoring and Maintenance

### Log Files
- Application logs: `/var/log/resume-builder/resume-builder.log`
- Error logs: `/var/log/resume-builder/resume-builder-error.log`
- Development logs: `./dev-logs/resume-builder.log`

### Health Checks
```bash
# Check application status
curl http://localhost:8080/actuator/health

# Check database connectivity
curl http://localhost:8080/actuator/health/db
```

### Backup Considerations
1. **Database Backup**
   ```bash
   mysqldump -u resumebuilder -p resume_builder_prod > backup_$(date +%Y%m%d).sql
   ```

2. **File Storage Backup**
   ```bash
   tar -czf uploads_backup_$(date +%Y%m%d).tar.gz /var/app/uploads/
   ```

## Troubleshooting

### Common Issues
1. **Database Connection Failed**
   - Verify MySQL service is running
   - Check database credentials
   - Ensure database exists

2. **File Permission Errors**
   - Check directory permissions
   - Verify application user has write access

3. **PDF Generation Timeout**
   - Check available disk space
   - Monitor system resources
   - Review PDF generation logs

### Performance Tuning
1. **Database Optimization**
   - Monitor connection pool usage
   - Adjust Hibernate batch sizes
   - Review query performance

2. **File Storage Optimization**
   - Implement file cleanup policies
   - Monitor disk usage
   - Consider file compression

3. **Memory Management**
   - Adjust JVM heap size: `-Xmx2g -Xms1g`
   - Monitor garbage collection
   - Profile memory usage