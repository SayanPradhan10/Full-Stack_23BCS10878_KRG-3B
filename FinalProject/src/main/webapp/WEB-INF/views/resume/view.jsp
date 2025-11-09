<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resume Preview - Resume Builder</title>
    
    <!-- Base Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    
    <!-- Template-specific Fonts -->
    <c:choose>
        <c:when test="${resume.templateType == 'CLASSIC'}">
            <link href="https://fonts.googleapis.com/css2?family=Times+New+Roman&family=Georgia:wght@400;700&display=swap" rel="stylesheet">
        </c:when>
        <c:when test="${resume.templateType == 'MODERN'}">
            <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&family=Open+Sans:wght@300;400;600;700&display=swap" rel="stylesheet">
        </c:when>
        <c:when test="${resume.templateType == 'CREATIVE'}">
            <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700;800;900&family=Playfair+Display:wght@400;500;600;700;800;900&display=swap" rel="stylesheet">
        </c:when>
    </c:choose>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
    
    <style>
        /* Base Template Styling */
        .resume-preview {
            backdrop-filter: var(--backdrop-blur);
            -webkit-backdrop-filter: var(--backdrop-blur);
            border-radius: var(--radius-lg);
            box-shadow: var(--shadow-medium);
            padding: 3rem;
            margin: 2rem 0;
            position: relative;
            overflow: hidden;
            animation: fadeSlideUp 0.6s ease;
        }
        
        /* CLASSIC Template Styling */
        <c:if test="${resume.templateType == 'CLASSIC'}">
        .resume-preview {
            background: #ffffff;
            border: 2px solid #333333;
            color: #000000;
            font-family: 'Georgia', 'Times New Roman', serif;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        
        .resume-preview::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: #333333;
        }
        
        .resume-header {
            text-align: center;
            margin-bottom: 2rem;
            padding-bottom: 1.5rem;
            border-bottom: 2px solid #333333;
        }
        
        .resume-name {
            font-family: 'Georgia', serif;
            font-weight: 700;
            font-size: 2.2rem;
            color: #000000;
            margin-bottom: 1rem;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        
        .resume-contact {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1.5rem;
            color: #333333;
        }
        
        .contact-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-weight: 400;
            font-size: 0.95rem;
        }
        
        .contact-item i {
            color: #333333;
            width: 16px;
            text-align: center;
        }
        
        .section-title {
            font-family: 'Georgia', serif;
            font-weight: 700;
            font-size: 1.3rem;
            color: #000000;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #333333;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .section-title i {
            color: #333333;
            font-size: 1.1rem;
            margin-right: 0.5rem;
        }
        
        .resume-entry {
            background: transparent;
            border: none;
            border-left: 3px solid #333333;
            padding: 1rem 0 1rem 1rem;
            margin-bottom: 1.5rem;
        }
        
        .resume-entry:hover {
            transform: none;
            box-shadow: none;
            background: #f8f9fa;
        }
        
        .entry-title {
            font-family: 'Georgia', serif;
            font-weight: 700;
            font-size: 1.1rem;
            color: #000000;
            margin-bottom: 0.3rem;
        }
        
        .entry-subtitle {
            font-weight: 600;
            color: #333333;
            margin-bottom: 0.5rem;
            font-size: 1rem;
            font-style: italic;
        }
        
        .entry-description {
            color: #444444;
            line-height: 1.6;
            margin-top: 0.5rem;
            font-size: 0.95rem;
        }
        
        .date-range {
            color: #666666;
            font-size: 0.9rem;
            font-weight: 400;
            background: transparent;
            padding: 0;
            border: none;
            font-style: italic;
        }
        
        .skill-item {
            background: #333333;
            color: #ffffff;
            padding: 0.4rem 0.8rem;
            border-radius: 0;
            font-weight: 400;
            font-size: 0.9rem;
            margin: 0.2rem 0.3rem 0.2rem 0;
            border: 1px solid #333333;
        }
        
        .skill-item:hover {
            transform: none;
            box-shadow: none;
            background: #555555;
        }
        </c:if>
        
        /* MODERN Template Styling */
        <c:if test="${resume.templateType == 'MODERN'}">
        .resume-preview {
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            border: 1px solid #e9ecef;
            color: #212529;
            font-family: 'Roboto', 'Open Sans', sans-serif;
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
        }
        
        .resume-preview::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #007bff, #0056b3);
        }
        
        .resume-header {
            text-align: center;
            margin-bottom: 2.5rem;
            padding-bottom: 2rem;
            border-bottom: 2px solid #007bff;
            background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
            margin: -3rem -3rem 2.5rem -3rem;
            padding: 3rem 3rem 2rem 3rem;
        }
        
        .resume-name {
            font-family: 'Roboto', sans-serif;
            font-weight: 700;
            font-size: 2.5rem;
            color: #007bff;
            margin-bottom: 1rem;
            letter-spacing: -0.5px;
        }
        
        .resume-contact {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 2rem;
            color: #6c757d;
        }
        
        .contact-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
            padding: 0.5rem 1rem;
            border-radius: 25px;
            background: rgba(0,123,255,0.1);
        }
        
        .contact-item:hover {
            color: #007bff;
            transform: translateY(-2px);
            background: rgba(0,123,255,0.2);
        }
        
        .contact-item i {
            color: #007bff;
            width: 16px;
            text-align: center;
        }
        
        .section-title {
            font-family: 'Roboto', sans-serif;
            font-weight: 600;
            font-size: 1.4rem;
            color: #007bff;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 2px solid #007bff;
            position: relative;
        }
        
        .section-title::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 50px;
            height: 2px;
            background: #0056b3;
        }
        
        .section-title i {
            color: #007bff;
            font-size: 1.2rem;
            margin-right: 0.75rem;
        }
        
        .resume-entry {
            background: #ffffff;
            border: 1px solid #e9ecef;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            transition: all 0.3s ease;
            border-left: 4px solid #007bff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }
        
        .resume-entry:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 24px rgba(0,123,255,0.15);
            border-left-color: #0056b3;
        }
        
        .entry-title {
            font-family: 'Roboto', sans-serif;
            font-weight: 600;
            font-size: 1.2rem;
            color: #212529;
            margin-bottom: 0.5rem;
        }
        
        .entry-subtitle {
            font-weight: 600;
            color: #007bff;
            margin-bottom: 0.75rem;
            font-size: 1.05rem;
        }
        
        .entry-description {
            color: #6c757d;
            line-height: 1.6;
            margin-top: 1rem;
        }
        
        .date-range {
            color: #6c757d;
            font-size: 0.9rem;
            font-weight: 500;
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: #ffffff;
            padding: 0.4rem 1rem;
            border-radius: 20px;
            border: none;
        }
        
        .skill-item {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: #ffffff;
            padding: 0.6rem 1.2rem;
            border-radius: 25px;
            font-weight: 500;
            font-size: 0.9rem;
            transition: all 0.3s ease;
            border: none;
            box-shadow: 0 2px 8px rgba(0,123,255,0.3);
        }
        
        .skill-item:hover {
            transform: translateY(-3px) scale(1.05);
            box-shadow: 0 4px 16px rgba(0,123,255,0.4);
        }
        </c:if>
        
        /* CREATIVE Template Styling */
        <c:if test="${resume.templateType == 'CREATIVE'}">
        .resume-preview {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
            border: none;
            color: #ffffff;
            font-family: 'Montserrat', sans-serif;
            box-shadow: 0 20px 40px rgba(102,126,234,0.3);
            position: relative;
        }
        
        .resume-preview::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0,0,0,0.1);
            z-index: 1;
        }
        
        .resume-preview > * {
            position: relative;
            z-index: 2;
        }
        
        .resume-header {
            text-align: center;
            margin-bottom: 3rem;
            padding-bottom: 2rem;
            border-bottom: 3px solid rgba(255,255,255,0.3);
            background: rgba(255,255,255,0.1);
            backdrop-filter: blur(10px);
            margin: -3rem -3rem 3rem -3rem;
            padding: 3rem 3rem 2rem 3rem;
        }
        
        .resume-name {
            font-family: 'Playfair Display', serif;
            font-weight: 800;
            font-size: 3rem;
            color: #ffffff;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
            letter-spacing: -1px;
        }
        
        .resume-contact {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 2rem;
            color: rgba(255,255,255,0.9);
        }
        
        .contact-item {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-weight: 500;
            transition: all 0.3s ease;
            padding: 0.75rem 1.5rem;
            border-radius: 30px;
            background: rgba(255,255,255,0.2);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255,255,255,0.3);
        }
        
        .contact-item:hover {
            color: #ffffff;
            transform: translateY(-3px) scale(1.05);
            background: rgba(255,255,255,0.3);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
        
        .contact-item i {
            color: #ffffff;
            width: 16px;
            text-align: center;
        }
        
        .section-title {
            font-family: 'Playfair Display', serif;
            font-weight: 700;
            font-size: 1.6rem;
            color: #ffffff;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid rgba(255,255,255,0.4);
            position: relative;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
        }
        
        .section-title::after {
            content: '';
            position: absolute;
            bottom: -3px;
            left: 0;
            width: 60px;
            height: 3px;
            background: linear-gradient(90deg, #f093fb, #f5576c);
            border-radius: 2px;
        }
        
        .section-title i {
            color: #f093fb;
            font-size: 1.3rem;
            margin-right: 0.75rem;
            text-shadow: none;
        }
        
        .resume-entry {
            background: rgba(255,255,255,0.15);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255,255,255,0.2);
            border-radius: 20px;
            padding: 2rem;
            margin-bottom: 2rem;
            transition: all 0.3s ease;
            border-left: 5px solid #f093fb;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }
        
        .resume-entry:hover {
            transform: translateY(-5px) scale(1.02);
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            background: rgba(255,255,255,0.25);
            border-left-color: #f5576c;
        }
        
        .entry-title {
            font-family: 'Playfair Display', serif;
            font-weight: 700;
            font-size: 1.4rem;
            color: #ffffff;
            margin-bottom: 0.5rem;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
        }
        
        .entry-subtitle {
            font-weight: 600;
            color: #f093fb;
            margin-bottom: 0.75rem;
            font-size: 1.1rem;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
        }
        
        .entry-description {
            color: rgba(255,255,255,0.9);
            line-height: 1.7;
            margin-top: 1rem;
        }
        
        .date-range {
            color: #ffffff;
            font-size: 0.9rem;
            font-weight: 600;
            background: linear-gradient(135deg, #f093fb, #f5576c);
            padding: 0.5rem 1.2rem;
            border-radius: 25px;
            border: 1px solid rgba(255,255,255,0.3);
            text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
        }
        
        .skill-item {
            background: linear-gradient(135deg, #f093fb, #f5576c, #4facfe);
            color: #ffffff;
            padding: 0.7rem 1.4rem;
            border-radius: 30px;
            font-weight: 600;
            font-size: 0.9rem;
            transition: all 0.3s ease;
            border: 1px solid rgba(255,255,255,0.3);
            box-shadow: 0 4px 15px rgba(240,147,251,0.4);
            text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
        }
        
        .skill-item:hover {
            transform: translateY(-4px) scale(1.1) rotate(2deg);
            box-shadow: 0 8px 25px rgba(240,147,251,0.6);
        }
        </c:if>
        
        /* Common Styles */
        .resume-section {
            margin-bottom: 3rem;
            animation: fadeSlideUp 0.8s ease var(--delay, 0.6s) both;
        }
        
        .skills-container {
            display: flex;
            flex-wrap: wrap;
            gap: 0.75rem;
        }
        
        .empty-state {
            text-align: center;
            padding: 4rem 2rem;
        }
        
        .empty-state i {
            font-size: 4rem;
            margin-bottom: 1.5rem;
            opacity: 0.5;
        }
        
        .empty-state h3 {
            margin-bottom: 1rem;
        }
        
        .action-buttons {
            background: var(--bg-glass);
            backdrop-filter: var(--backdrop-blur);
            -webkit-backdrop-filter: var(--backdrop-blur);
            border: 1px solid var(--glass-border);
            border-radius: var(--radius-lg);
            padding: 1.5rem;
            margin-bottom: 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 1rem;
        }
        
        .action-title {
            font-family: 'Poppins', sans-serif;
            font-weight: 600;
            color: var(--text-primary);
            margin: 0;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        
        .action-title i {
            color: var(--primary-color);
        }
        
        .template-badge {
            background: rgba(0, 212, 255, 0.1);
            color: var(--primary-color);
            border: 1px solid rgba(0, 212, 255, 0.3);
            padding: 0.25rem 0.75rem;
            border-radius: var(--radius-sm);
            font-size: 0.85rem;
            font-weight: 500;
        }
        
        @keyframes fadeSlideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        /* Print styles */
        @media print {
            body {
                background: white !important;
                color: black !important;
            }
            .navbar, .action-buttons {
                display: none !important;
            }
            .resume-preview {
                background: white !important;
                box-shadow: none !important;
                border: 1px solid #ccc !important;
                margin: 0 !important;
                padding: 1rem !important;
            }
            .resume-preview::before {
                display: none !important;
            }
            .resume-name, .section-title, .entry-subtitle {
                color: #333 !important;
            }
            .skill-item {
                background: #333 !important;
                color: white !important;
            }
            .contact-item, .resume-entry {
                background: transparent !important;
                border: 1px solid #ddd !important;
            }
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .resume-preview {
                padding: 2rem 1.5rem;
                margin: 1rem 0;
            }
            
            .resume-name {
                font-size: 2rem;
            }
            
            .resume-contact {
                flex-direction: column;
                gap: 1rem;
            }
            
            .action-buttons {
                flex-direction: column;
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <!-- Glassmorphism Navigation -->
    <nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
        <div class="container">
            <a class="navbar-brand floating" href="${pageContext.request.contextPath}/user/dashboard">
                <i class="fas fa-file-alt me-2"></i>Resume Builder
            </a>
            <div class="navbar-nav ms-auto">
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-tools me-1"></i>Actions
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resume/${resume.id}/edit">
                            <i class="fas fa-edit me-2"></i>Edit Resume
                        </a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/template/${resume.id}/download/pdf">
                            <i class="fas fa-file-pdf me-2"></i>Download PDF
                        </a></li>
                        <li><a class="dropdown-item" href="javascript:window.print()">
                            <i class="fas fa-print me-2"></i>Print Resume
                        </a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/dashboard">
                            <i class="fas fa-arrow-left me-2"></i>Back to Dashboard
                        </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container" style="margin-top: 100px;">
        <!-- Action Buttons -->
        <div class="action-buttons">
            <div>
                <h1 class="action-title">
                    <i class="fas fa-eye pulse"></i>Resume Preview
                </h1>
                <small class="template-badge">
                    Template: ${resume.templateType.displayName}
                </small>
            </div>
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/resume/${resume.id}/edit" class="btn btn-outline-primary">
                    <i class="fas fa-edit me-1"></i>Edit
                </a>
                <a href="${pageContext.request.contextPath}/template/${resume.id}/download/pdf" class="btn btn-primary">
                    <i class="fas fa-download me-1"></i>PDF
                </a>
                <button onclick="window.print()" class="btn btn-outline-secondary">
                    <i class="fas fa-print me-1"></i>Print
                </button>
            </div>
        </div>

        <!-- Resume Content -->
        <div class="resume-preview">
            <!-- Personal Information Header -->
            <c:if test="${not empty resumeData.personalInfo}">
                <div class="resume-header">
                    <h1 class="resume-name">${resumeData.personalInfo.name}</h1>
                    <div class="resume-contact">
                        <c:if test="${not empty resumeData.personalInfo.email}">
                            <div class="contact-item">
                                <i class="fas fa-envelope"></i>
                                <span>${resumeData.personalInfo.email}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty resumeData.personalInfo.phone}">
                            <div class="contact-item">
                                <i class="fas fa-phone"></i>
                                <span>${resumeData.personalInfo.phone}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty resumeData.personalInfo.address}">
                            <div class="contact-item">
                                <i class="fas fa-map-marker-alt"></i>
                                <span>${resumeData.personalInfo.address}</span>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:if>

            <!-- Work Experience -->
            <c:if test="${not empty resumeData.workExperiences}">
                <div class="resume-section" style="--delay: 0.8s;">
                    <h3 class="section-title">
                        <i class="fas fa-briefcase"></i>Professional Experience
                    </h3>
                    <c:forEach var="experience" items="${resumeData.workExperiences}">
                        <div class="resume-entry">
                            <div class="row">
                                <div class="col-md-8">
                                    <h4 class="entry-title">${experience.position}</h4>
                                    <h5 class="entry-subtitle">${experience.company}</h5>
                                    <c:if test="${not empty experience.description}">
                                        <p class="entry-description">${experience.description}</p>
                                    </c:if>
                                </div>
                                <div class="col-md-4 text-end">
                                    <div class="date-range">
                                        ${experience.startDate} - ${experience.endDate}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Education -->
            <c:if test="${not empty resumeData.educations}">
                <div class="resume-section" style="--delay: 1.0s;">
                    <h3 class="section-title">
                        <i class="fas fa-graduation-cap"></i>Education
                    </h3>
                    <c:forEach var="education" items="${resumeData.educations}">
                        <div class="resume-entry">
                            <div class="row">
                                <div class="col-md-8">
                                    <h4 class="entry-title">${education.degree}</h4>
                                    <h5 class="entry-subtitle">${education.institution}</h5>
                                    <c:if test="${not empty education.fieldOfStudy}">
                                        <p class="entry-meta">Field of Study: ${education.fieldOfStudy}</p>
                                    </c:if>
                                    <c:if test="${not empty education.gpa}">
                                        <p class="entry-meta">GPA: ${education.gpa}</p>
                                    </c:if>
                                </div>
                                <div class="col-md-4 text-end">
                                    <div class="date-range">
                                        ${education.startDate} - ${education.endDate}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Skills -->
            <c:if test="${not empty resumeData.skills}">
                <div class="resume-section" style="--delay: 1.2s;">
                    <h3 class="section-title">
                        <i class="fas fa-cogs"></i>Skills & Expertise
                    </h3>
                    <div class="skills-container">
                        <c:forEach var="skill" items="${resumeData.skills}">
                            <span class="skill-item">${skill.name}</span>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <!-- Empty State -->
            <c:if test="${empty resumeData.workExperiences and empty resumeData.educations and empty resumeData.skills}">
                <div class="empty-state">
                    <i class="fas fa-file-alt"></i>
                    <h3>Resume is Empty</h3>
                    <p>Start building your resume by adding your professional information.</p>
                    <a href="${pageContext.request.contextPath}/resume/${resume.id}/edit" class="btn btn-primary mt-3">
                        <i class="fas fa-edit me-2"></i>Edit Resume
                    </a>
                </div>
            </c:if>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Enhanced Effects -->
    <script>
        // Navbar scroll effect
        window.addEventListener('scroll', function() {
            const navbar = document.getElementById('mainNavbar');
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
        
        // Initialize enhanced effects
        document.addEventListener('DOMContentLoaded', function() {
            // Staggered animation delays for sections
            const sections = document.querySelectorAll('.resume-section');
            sections.forEach((section, index) => {
                section.style.setProperty('--delay', `${0.8 + (index * 0.2)}s`);
            });
            
            // Template-specific hover effects
            const templateType = '${resume.templateType}';
            
            document.querySelectorAll('.resume-entry').forEach(entry => {
                entry.addEventListener('mouseenter', function() {
                    if (templateType === 'CLASSIC') {
                        // Minimal hover for classic
                        this.style.backgroundColor = '#f8f9fa';
                    } else if (templateType === 'MODERN') {
                        // Smooth lift for modern
                        this.style.transform = 'translateY(-4px)';
                    } else if (templateType === 'CREATIVE') {
                        // Dynamic transform for creative
                        this.style.transform = 'translateY(-5px) scale(1.02)';
                    }
                });
                
                entry.addEventListener('mouseleave', function() {
                    if (templateType === 'CLASSIC') {
                        this.style.backgroundColor = 'transparent';
                    } else {
                        this.style.transform = 'translateY(0) scale(1)';
                    }
                });
            });
            
            // Pulse animation for action title icon
            const pulseIcon = document.querySelector('.pulse');
            if (pulseIcon) {
                setInterval(() => {
                    pulseIcon.style.transform = 'scale(1.1)';
                    setTimeout(() => {
                        pulseIcon.style.transform = 'scale(1)';
                    }, 200);
                }, 2000);
            }
        });
    </script>
</body>
</html>