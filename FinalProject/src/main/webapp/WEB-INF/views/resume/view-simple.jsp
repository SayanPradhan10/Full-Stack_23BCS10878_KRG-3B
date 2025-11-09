<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${resume.title} - Resume Preview</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
    <style>
        /* Print-specific styles */
        @media print {
            body { background: white !important; }
            .navbar, .action-buttons, footer { display: none !important; }
            .main-container { 
                background: white !important; 
                box-shadow: none !important; 
                border: none !important;
                margin: 0 !important;
                padding: 1rem !important;
            }
            .resume-preview { 
                background: white !important; 
                box-shadow: none !important; 
                border: none !important;
            }
            .resume-section { 
                background: white !important; 
                border: 1px solid #ddd !important;
            }
        }
    </style>
</head>
<body>
    <!-- Minimalistic Navigation -->
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
        <div class="main-container fade-in">
            <!-- Action Buttons (Hidden in print) -->
            <div class="action-buttons d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1 class="h4 fw-bold mb-1">
                        <i class="fas fa-eye me-2 pulse"></i>Resume Preview
                    </h1>
                    <small class="text-muted">Template: ${resume.templateType.displayName}</small>
                </div>
                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/resume/${resume.id}/edit" class="btn btn-outline-primary btn-sm">
                        <i class="fas fa-edit me-1"></i>Edit
                    </a>
                    <a href="${pageContext.request.contextPath}/template/${resume.id}/download/pdf" class="btn btn-primary btn-sm">
                        <i class="fas fa-download me-1"></i>PDF
                    </a>
                    <button onclick="window.print()" class="btn btn-outline-secondary btn-sm">
                        <i class="fas fa-print me-1"></i>Print
                    </button>
                </div>
            </div>

            <!-- Resume Content -->
            <div class="resume-preview">
                <!-- Personal Information Header -->
                <div class="text-center mb-5 slide-up">
                    <h1 class="display-4 fw-bold text-primary mb-3">${resume.personalInfo.name}</h1>
                    <div class="row justify-content-center text-muted">
                        <div class="col-auto d-flex align-items-center">
                            <i class="fas fa-envelope me-2"></i>
                            <span>${resume.personalInfo.email}</span>
                        </div>
                        <c:if test="${not empty resume.personalInfo.phone}">
                            <div class="col-auto d-flex align-items-center">
                                <i class="fas fa-phone me-2"></i>
                                <span>${resume.personalInfo.phone}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty resume.personalInfo.address}">
                            <div class="col-auto d-flex align-items-center">
                                <i class="fas fa-map-marker-alt me-2"></i>
                                <span>${resume.personalInfo.address}</span>
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- Professional Summary (if available) -->
                <c:if test="${not empty resume.summary}">
                    <div class="resume-section slide-up" style="animation-delay: 0.1s;">
                        <h3 class="section-title">
                            <i class="fas fa-user me-2"></i>Professional Summary
                        </h3>
                        <p class="lead">${resume.summary}</p>
                    </div>
                </c:if>

                <!-- Work Experience Section -->
                <c:if test="${not empty resume.workExperiences}">
                    <div class="resume-section slide-up" style="animation-delay: 0.2s;">
                        <h3 class="section-title">
                            <i class="fas fa-briefcase me-2"></i>Professional Experience
                        </h3>
                        <c:forEach var="experience" items="${resume.workExperiences}" varStatus="status">
                            <div class="resume-entry ${status.last ? '' : 'mb-4'}">
                                <div class="row align-items-start">
                                    <div class="col-md-8">
                                        <h4 class="job-title">${experience.position}</h4>
                                        <h5 class="company-name text-primary">${experience.company}</h5>
                                        <c:if test="${experience.isInternship}">
                                            <span class="badge badge-success mb-2">Internship</span>
                                        </c:if>
                                        <c:if test="${not empty experience.description}">
                                            <div class="job-description mt-2">
                                                ${experience.description}
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="col-md-4 text-md-end">
                                        <div class="date-range text-muted">
                                            <c:if test="${not empty experience.startDate}">
                                                <fmt:formatDate value="${experience.startDate}" pattern="MMM yyyy"/>
                                            </c:if>
                                            <c:if test="${not empty experience.startDate and not empty experience.endDate}"> - </c:if>
                                            <c:choose>
                                                <c:when test="${not empty experience.endDate}">
                                                    <fmt:formatDate value="${experience.endDate}" pattern="MMM yyyy"/>
                                                </c:when>
                                                <c:when test="${not empty experience.startDate}">
                                                    Present
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- Education Section -->
                <c:if test="${not empty resume.educations}">
                    <div class="resume-section slide-up" style="animation-delay: 0.3s;">
                        <h3 class="section-title">
                            <i class="fas fa-graduation-cap me-2"></i>Education
                        </h3>
                        <c:forEach var="education" items="${resume.educations}" varStatus="status">
                            <div class="resume-entry ${status.last ? '' : 'mb-4'}">
                                <div class="row align-items-start">
                                    <div class="col-md-8">
                                        <h4 class="degree-title">${education.degree}</h4>
                                        <h5 class="institution-name text-primary">${education.institution}</h5>
                                        <c:if test="${not empty education.fieldOfStudy}">
                                            <div class="field-of-study text-secondary">${education.fieldOfStudy}</div>
                                        </c:if>
                                        <c:if test="${not empty education.gpa}">
                                            <div class="gpa text-muted">GPA: ${education.gpa}</div>
                                        </c:if>
                                    </div>
                                    <div class="col-md-4 text-md-end">
                                        <div class="date-range text-muted">
                                            <c:if test="${not empty education.startDate}">
                                                <fmt:formatDate value="${education.startDate}" pattern="MMM yyyy"/>
                                            </c:if>
                                            <c:if test="${not empty education.startDate and not empty education.endDate}"> - </c:if>
                                            <c:if test="${not empty education.endDate}">
                                                <fmt:formatDate value="${education.endDate}" pattern="MMM yyyy"/>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- Skills Section -->
                <c:if test="${not empty resume.skills}">
                    <div class="resume-section slide-up" style="animation-delay: 0.4s;">
                        <h3 class="section-title">
                            <i class="fas fa-cogs me-2"></i>Skills & Expertise
                        </h3>
                        

                        
                        <!-- Display skills in a simple format -->
                        <div class="skill-list">
                            <c:forEach var="skill" items="${resume.skills}" varStatus="status">
                                <div class="skill-item">
                                    ${skill.name}
                                    <c:if test="${not empty skill.proficiencyLevel}">
                                        <span class="text-muted"> - ${skill.proficiencyLevel}</span>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <!-- Projects Section (if available) -->
                <c:if test="${not empty resume.projects}">
                    <div class="resume-section slide-up" style="animation-delay: 0.5s;">
                        <h3 class="section-title">
                            <i class="fas fa-project-diagram me-2"></i>Projects
                        </h3>
                        <c:forEach var="project" items="${resume.projects}" varStatus="status">
                            <div class="resume-entry ${status.last ? '' : 'mb-4'}">
                                <h4 class="project-title">${project.name}</h4>
                                <c:if test="${not empty project.technologies}">
                                    <div class="project-technologies text-primary mb-2">
                                        <strong>Technologies:</strong> ${project.technologies}
                                    </div>
                                </c:if>
                                <c:if test="${not empty project.description}">
                                    <div class="project-description">${project.description}</div>
                                </c:if>
                                <c:if test="${not empty project.url}">
                                    <div class="project-url mt-2">
                                        <a href="${project.url}" target="_blank" class="text-primary">
                                            <i class="fas fa-external-link-alt me-1"></i>View Project
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- Certifications Section (if available) -->
                <c:if test="${not empty resume.certifications}">
                    <div class="resume-section slide-up" style="animation-delay: 0.6s;">
                        <h3 class="section-title">
                            <i class="fas fa-certificate me-2"></i>Certifications
                        </h3>
                        <c:forEach var="certification" items="${resume.certifications}" varStatus="status">
                            <div class="resume-entry ${status.last ? '' : 'mb-3'}">
                                <div class="row align-items-start">
                                    <div class="col-md-8">
                                        <h5 class="certification-name">${certification.name}</h5>
                                        <div class="issuing-organization text-primary">${certification.issuingOrganization}</div>
                                    </div>
                                    <div class="col-md-4 text-md-end">
                                        <div class="certification-date text-muted">
                                            <c:if test="${not empty certification.issueDate}">
                                                <fmt:formatDate value="${certification.issueDate}" pattern="MMM yyyy"/>
                                            </c:if>
                                            <c:if test="${not empty certification.expiryDate}">
                                                - <fmt:formatDate value="${certification.expiryDate}" pattern="MMM yyyy"/>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Modern Footer (Hidden in print) -->
    <footer class="mt-5 py-4">
        <div class="container text-center">
            <div class="row">
                <div class="col-md-6 text-md-start">
                    <h6 class="fw-bold mb-2">
                        <i class="fas fa-file-alt me-2"></i>Resume Builder
                    </h6>
                    <p class="mb-0 opacity-75">Professional resumes made simple.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0 opacity-75">&copy; 2024 Resume Builder. All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Minimalistic Effects -->
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

        // Initialize effects
        document.addEventListener('DOMContentLoaded', function() {
            // Add subtle hover effects to resume sections
            document.querySelectorAll('.resume-entry').forEach(entry => {
                entry.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateX(2px)';
                });
                
                entry.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateX(0)';
                });
            });
        });
    </script>
</body>
</html>