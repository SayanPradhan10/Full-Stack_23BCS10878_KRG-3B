<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Resume - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
    <!-- Glassmorphism Navigation -->
    <nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
        <div class="container">
            <a class="navbar-brand floating" href="${pageContext.request.contextPath}/user/dashboard">
                <i class="fas fa-file-alt me-2"></i>Resume Builder
            </a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/dashboard">
                            <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/resume/create">
                            <i class="fas fa-plus me-1"></i>Create Resume
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/template/select">
                            <i class="fas fa-palette me-1"></i>Templates
                        </a>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle me-1"></i>Account
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile">
                                <i class="fas fa-user me-2"></i>Profile
                            </a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form method="post" action="${pageContext.request.contextPath}/user/logout" class="d-inline">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="dropdown-item">
                                        <i class="fas fa-sign-out-alt me-2"></i>Logout
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container" style="margin-top: 100px;">
        <!-- Success/Error Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success fade-in">
                <i class="fas fa-check-circle me-2"></i>${successMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger fade-in">
                <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
            </div>
        </c:if>

        <!-- Dashboard Header -->
        <div class="main-container fade-in">
            <div class="row align-items-center mb-4">
                <div class="col-md-8">
                    <h1 class="display-5 fw-bold text-primary mb-2">
                        <i class="fas fa-plus-circle me-3"></i>Create New Resume
                    </h1>
                    <p class="lead text-muted">
                        Build your professional resume with our step-by-step form
                    </p>
                </div>
                <div class="col-md-4 text-end">
                    <div class="card border-0 bg-light">
                        <div class="card-body text-center py-3">
                            <h6 class="text-muted mb-1">Progress</h6>
                            <div class="progress mb-2" style="height: 8px;">
                                <div class="progress-bar" role="progressbar" style="width: 25%" id="form-progress"></div>
                            </div>
                            <small class="text-muted">Getting started...</small>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Glassmorphism Progress Cards -->
            <div class="row mb-4">
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating">
                        <div class="display-4 text-primary mb-3 pulse">
                            <i class="fas fa-user"></i>
                        </div>
                        <h3 class="fw-bold text-primary">Personal</h3>
                        <p class="text-muted mb-0">Basic Information</p>
                        <div class="progress mt-3">
                            <div class="progress-bar" id="personal-progress" style="width: 0%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating" style="animation-delay: 0.2s;">
                        <div class="display-4 text-success mb-3 pulse" style="animation-delay: 0.5s;">
                            <i class="fas fa-graduation-cap"></i>
                        </div>
                        <h5 class="fw-bold text-success">Education</h5>
                        <p class="text-muted mb-0">Academic Background</p>
                        <div class="progress mt-3">
                            <div class="progress-bar bg-success" id="education-progress" style="width: 0%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating" style="animation-delay: 0.4s;">
                        <div class="display-4 text-warning mb-3 pulse" style="animation-delay: 1s;">
                            <i class="fas fa-briefcase"></i>
                        </div>
                        <h5 class="fw-bold text-warning">Experience</h5>
                        <p class="text-muted mb-0">Work History</p>
                        <div class="progress mt-3">
                            <div class="progress-bar bg-warning" id="experience-progress" style="width: 0%"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Resume Creation Form -->
            <form:form method="post" 
                      action="${pageContext.request.contextPath}/resume/create" 
                      modelAttribute="resumeData" 
                      id="resume-form">
                
                <div class="row">
                    <!-- Main Form Column -->
                    <div class="col-lg-8">
                        <!-- Personal Information Section -->
                        <div class="card mb-4 slide-up">
                            <div class="card-header">
                                <h4 class="mb-0">
                                    <i class="fas fa-user me-2"></i>Personal Information
                                </h4>
                            </div>
                            <div class="card-body">
                                <div class="mb-4">
                                    <label for="title" class="form-label">Resume Title</label>
                                    <form:input path="title" 
                                               id="title" 
                                               class="form-control" 
                                               placeholder="e.g., Software Developer Resume"
                                               required="true"/>
                                    <div class="form-text">Give your resume a descriptive title</div>
                                    <form:errors path="title" cssClass="text-danger small"/>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.name" class="form-label">Full Name</label>
                                        <form:input path="personalInfo.name" 
                                                   id="personalInfo.name" 
                                                   class="form-control" 
                                                   placeholder="John Doe"
                                                   required="true"/>
                                        <form:errors path="personalInfo.name" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.email" class="form-label">Email Address</label>
                                        <form:input path="personalInfo.email" 
                                                   type="email"
                                                   id="personalInfo.email" 
                                                   class="form-control" 
                                                   placeholder="john.doe@email.com"
                                                   required="true"/>
                                        <form:errors path="personalInfo.email" cssClass="text-danger small"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.phone" class="form-label">Phone Number</label>
                                        <form:input path="personalInfo.phone" 
                                                   type="tel"
                                                   id="personalInfo.phone" 
                                                   class="form-control" 
                                                   placeholder="+1 (555) 123-4567"/>
                                        <form:errors path="personalInfo.phone" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.address" class="form-label">Location</label>
                                        <form:input path="personalInfo.address" 
                                                   id="personalInfo.address" 
                                                   class="form-control" 
                                                   placeholder="City, State, Country"/>
                                        <form:errors path="personalInfo.address" cssClass="text-danger small"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Education Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.1s;" id="education-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-graduation-cap me-2"></i>Education
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addEducation()">
                                    <i class="fas fa-plus me-1"></i>Add Education
                                </button>
                            </div>
                            <div class="card-body">
                                <!-- Default education entry -->
                                <div class="education-item border rounded p-3 mb-3">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Degree</label>
                                            <input name="educations[0].degree" class="form-control" placeholder="e.g., Bachelor of Science"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Institution</label>
                                            <input name="educations[0].institution" class="form-control" placeholder="University name"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Field of Study</label>
                                            <input name="educations[0].fieldOfStudy" class="form-control" placeholder="e.g., Computer Science"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">GPA (Optional)</label>
                                            <input name="educations[0].gpa" type="number" step="0.01" class="form-control" placeholder="3.8"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Start Date</label>
                                            <input name="educations[0].startDate" type="date" class="form-control"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">End Date</label>
                                            <input name="educations[0].endDate" type="date" class="form-control"/>
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)" style="display: none;">Remove</button>
                                </div>
                            </div>
                        </div>

                        <!-- Work Experience Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.2s;" id="experience-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-briefcase me-2"></i>Work Experience
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addExperience()">
                                    <i class="fas fa-plus me-1"></i>Add Experience
                                </button>
                            </div>
                            <div class="card-body">
                                <!-- Default experience entry -->
                                <div class="experience-item border rounded p-3 mb-3">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Job Title</label>
                                            <input name="workExperiences[0].position" class="form-control" placeholder="e.g., Software Developer"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Company</label>
                                            <input name="workExperiences[0].company" class="form-control" placeholder="Company name"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Start Date</label>
                                            <input name="workExperiences[0].startDate" type="date" class="form-control"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">End Date</label>
                                            <input name="workExperiences[0].endDate" type="date" class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Description</label>
                                        <textarea name="workExperiences[0].description" class="form-control" rows="3" placeholder="Describe your responsibilities and achievements..."></textarea>
                                    </div>
                                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)" style="display: none;">Remove</button>
                                </div>
                            </div>
                        </div>

                        <!-- Skills Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.3s;" id="skills-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-cogs me-2"></i>Skills & Expertise
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addSkill()">
                                    <i class="fas fa-plus me-1"></i>Add Skill
                                </button>
                            </div>
                            <div class="card-body">
                                <!-- Default skill entry -->
                                <div class="skill-item d-flex align-items-center mb-2">
                                    <input name="skills[0].name" class="form-control me-2" placeholder="e.g., Java, Python, Leadership"/>
                                    <select name="skills[0].type" class="form-select me-2" style="max-width: 150px;">
                                        <option value="">Type</option>
                                        <option value="TECHNICAL">Technical</option>
                                        <option value="SOFT_SKILL">Soft Skill</option>
                                        <option value="LANGUAGE">Language</option>
                                    </select>
                                    <select name="skills[0].proficiencyLevel" class="form-select me-2" style="max-width: 150px;">
                                        <option value="">Level</option>
                                        <option value="BEGINNER">Beginner</option>
                                        <option value="INTERMEDIATE">Intermediate</option>
                                        <option value="ADVANCED">Advanced</option>
                                        <option value="EXPERT">Expert</option>
                                    </select>
                                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)" style="display: none;">Remove</button>
                                </div>
                            </div>
                        </div>

                        <!-- Template Selection Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.4s;">
                            <div class="card-header">
                                <h4 class="mb-0">
                                    <i class="fas fa-palette me-2"></i>Choose Template
                                </h4>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <div class="template-preview card h-100 text-center" onclick="selectTemplate('CLASSIC')">
                                            <div class="card-body">
                                                <div class="display-4 text-primary mb-3">
                                                    <i class="fas fa-briefcase"></i>
                                                </div>
                                                <h6 class="fw-bold">Classic</h6>
                                                <p class="text-muted small">Professional and traditional</p>
                                                <form:radiobutton path="templateType" value="CLASSIC" class="form-check-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-4 mb-3">
                                        <div class="template-preview card h-100 text-center" onclick="selectTemplate('MODERN')">
                                            <div class="card-body">
                                                <div class="display-4 text-success mb-3">
                                                    <i class="fas fa-rocket"></i>
                                                </div>
                                                <h6 class="fw-bold">Modern</h6>
                                                <p class="text-muted small">Contemporary and stylish</p>
                                                <form:radiobutton path="templateType" value="MODERN" class="form-check-input"/>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-4 mb-3">
                                        <div class="template-preview card h-100 text-center" onclick="selectTemplate('CREATIVE')">
                                            <div class="card-body">
                                                <div class="display-4 text-warning mb-3">
                                                    <i class="fas fa-paint-brush"></i>
                                                </div>
                                                <h6 class="fw-bold">Creative</h6>
                                                <p class="text-muted small">Bold and eye-catching</p>
                                                <form:radiobutton path="templateType" value="CREATIVE" class="form-check-input"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <form:errors path="templateType" cssClass="text-danger small"/>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex justify-content-between align-items-center mt-4">
                            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-secondary btn-lg">
                                <i class="fas fa-arrow-left me-2"></i>Cancel
                            </a>
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus me-2"></i>Create Resume
                            </button>
                        </div>
                    </div>

                    <!-- Sidebar with Tips -->
                    <div class="col-lg-4">
                        <div class="card border-0 slide-up" style="animation-delay: 0.5s;">
                            <div class="card-header">
                                <h5 class="mb-0">
                                    <i class="fas fa-lightbulb me-2"></i>Resume Tips
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="alert alert-info">
                                    <h6><i class="fas fa-user me-2"></i>Personal Information</h6>
                                    <p class="small mb-0">Use a professional email address and include your location for local opportunities.</p>
                                </div>
                                
                                <div class="alert alert-success">
                                    <h6><i class="fas fa-graduation-cap me-2"></i>Education</h6>
                                    <p class="small mb-0">List your most recent education first. Include relevant coursework and honors.</p>
                                </div>
                                
                                <div class="alert alert-warning">
                                    <h6><i class="fas fa-briefcase me-2"></i>Experience</h6>
                                    <p class="small mb-0">Focus on achievements and quantifiable results. Use action verbs to describe your responsibilities.</p>
                                </div>
                                
                                <div class="alert alert-primary">
                                    <h6><i class="fas fa-cogs me-2"></i>Skills</h6>
                                    <p class="small mb-0">Include both technical and soft skills. Be honest about your proficiency levels.</p>
                                </div>
                            </div>
                        </div>

                        <!-- Quick Actions -->
                        <div class="card border-0 mt-4 slide-up" style="animation-delay: 0.6s;">
                            <div class="card-header">
                                <h6 class="mb-0">
                                    <i class="fas fa-rocket me-2"></i>Quick Actions
                                </h6>
                            </div>
                            <div class="card-body">
                                <div class="d-grid gap-2">
                                    <a href="${pageContext.request.contextPath}/template/select" class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-eye me-1"></i>Preview Templates
                                    </a>
                                    <button type="button" class="btn btn-outline-success btn-sm" onclick="saveAsDraft()">
                                        <i class="fas fa-save me-1"></i>Save as Draft
                                    </button>
                                    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-secondary btn-sm">
                                        <i class="fas fa-list me-1"></i>My Resumes
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

    <!-- Modern Footer -->
    <footer class="mt-5 py-4" style="background: linear-gradient(135deg, var(--primary-color), var(--primary-dark)); color: white;">
        <div class="container text-center">
            <div class="row">
                <div class="col-md-6 text-md-start">
                    <h6 class="fw-bold mb-2">
                        <i class="fas fa-file-alt me-2"></i>Resume Builder
                    </h6>
                    <p class="mb-0 opacity-75">Build your future, one resume at a time.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0 opacity-75">&copy; 2024 Resume Builder. All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Glassmorphism Effects & Custom JavaScript -->
    <script>
        let educationIndex = 1;
        let experienceIndex = 1;
        let skillIndex = 1;

        // Navbar scroll effect
        window.addEventListener('scroll', function() {
            const navbar = document.getElementById('mainNavbar');
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // Particle effect
        function createParticles() {
            const particlesContainer = document.createElement('div');
            particlesContainer.className = 'particles';
            document.body.appendChild(particlesContainer);

            for (let i = 0; i < 30; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 6 + 's';
                particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
                particlesContainer.appendChild(particle);
            }
        }

        // Progress tracking with animations
        function updateProgress() {
            const form = document.getElementById('resume-form');
            
            // Personal info progress
            const title = form.querySelector('#title').value;
            const name = form.querySelector('#personalInfo\\.name').value;
            const email = form.querySelector('#personalInfo\\.email').value;
            const personalProgress = (title && name && email) ? 100 : 
                                   (title || name || email) ? 50 : 0;
            
            // Education progress
            const institution = form.querySelector('input[name="educations[0].institution"]').value;
            const degree = form.querySelector('input[name="educations[0].degree"]').value;
            const educationProgress = (institution && degree) ? 100 : 
                                    (institution || degree) ? 50 : 0;
            
            // Experience progress
            const company = form.querySelector('input[name="workExperiences[0].company"]').value;
            const position = form.querySelector('input[name="workExperiences[0].position"]').value;
            const experienceProgress = (company && position) ? 100 : 
                                     (company || position) ? 50 : 0;
            
            // Animate progress bars
            animateProgressBar('personal-progress', personalProgress);
            animateProgressBar('education-progress', educationProgress);
            animateProgressBar('experience-progress', experienceProgress);
            
            // Update main progress
            const overallProgress = (personalProgress + educationProgress + experienceProgress) / 3;
            animateProgressBar('form-progress', overallProgress);
        }

        function animateProgressBar(id, targetWidth) {
            const progressBar = document.getElementById(id);
            if (progressBar) {
                progressBar.style.width = targetWidth + '%';
            }
        }

        function addEducation() {
            const section = document.getElementById('education-section');
            const cardBody = section.querySelector('.card-body');
            const html = `
                <div class="education-item border rounded p-3 mb-3">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Degree</label>
                            <input name="educations[${educationIndex}].degree" class="form-control" placeholder="e.g., Bachelor of Science"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Institution</label>
                            <input name="educations[${educationIndex}].institution" class="form-control" placeholder="University name"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Field of Study</label>
                            <input name="educations[${educationIndex}].fieldOfStudy" class="form-control" placeholder="e.g., Computer Science"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">GPA (Optional)</label>
                            <input name="educations[${educationIndex}].gpa" type="number" step="0.01" class="form-control" placeholder="3.8"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Start Date</label>
                            <input name="educations[${educationIndex}].startDate" type="date" class="form-control"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">End Date</label>
                            <input name="educations[${educationIndex}].endDate" type="date" class="form-control"/>
                        </div>
                    </div>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">Remove</button>
                </div>
            `;
            cardBody.insertAdjacentHTML('beforeend', html);
            educationIndex++;
            updateRemoveButtons();
        }

        function addExperience() {
            const section = document.getElementById('experience-section');
            const cardBody = section.querySelector('.card-body');
            const html = `
                <div class="experience-item border rounded p-3 mb-3">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Job Title</label>
                            <input name="workExperiences[${experienceIndex}].position" class="form-control" placeholder="e.g., Software Developer"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Company</label>
                            <input name="workExperiences[${experienceIndex}].company" class="form-control" placeholder="Company name"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Start Date</label>
                            <input name="workExperiences[${experienceIndex}].startDate" type="date" class="form-control"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">End Date</label>
                            <input name="workExperiences[${experienceIndex}].endDate" type="date" class="form-control"/>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea name="workExperiences[${experienceIndex}].description" class="form-control" rows="3" placeholder="Describe your responsibilities and achievements..."></textarea>
                    </div>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">Remove</button>
                </div>
            `;
            cardBody.insertAdjacentHTML('beforeend', html);
            experienceIndex++;
            updateRemoveButtons();
        }

        function addSkill() {
            const section = document.getElementById('skills-section');
            const cardBody = section.querySelector('.card-body');
            const html = `
                <div class="skill-item d-flex align-items-center mb-2">
                    <input name="skills[${skillIndex}].name" class="form-control me-2" placeholder="e.g., Java, Python, Leadership"/>
                    <select name="skills[${skillIndex}].type" class="form-select me-2" style="max-width: 150px;">
                        <option value="">Type</option>
                        <option value="TECHNICAL">Technical</option>
                        <option value="SOFT_SKILL">Soft Skill</option>
                        <option value="LANGUAGE">Language</option>
                    </select>
                    <select name="skills[${skillIndex}].proficiencyLevel" class="form-select me-2" style="max-width: 150px;">
                        <option value="">Level</option>
                        <option value="BEGINNER">Beginner</option>
                        <option value="INTERMEDIATE">Intermediate</option>
                        <option value="ADVANCED">Advanced</option>
                        <option value="EXPERT">Expert</option>
                    </select>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">Remove</button>
                </div>
            `;
            cardBody.insertAdjacentHTML('beforeend', html);
            skillIndex++;
            updateRemoveButtons();
        }

        function removeItem(button) {
            const item = button.closest('.education-item, .experience-item, .skill-item');
            if (item) {
                item.remove();
                reindexArrays();
                updateRemoveButtons();
            }
        }

        function reindexArrays() {
            // Reindex education items
            const educationItems = document.querySelectorAll('.education-item');
            educationItems.forEach((item, index) => {
                const inputs = item.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    const name = input.getAttribute('name');
                    if (name && name.startsWith('educations[')) {
                        input.setAttribute('name', name.replace(/educations\[\d+\]/, `educations[${index}]`));
                    }
                });
            });
            educationIndex = educationItems.length;

            // Reindex experience items
            const experienceItems = document.querySelectorAll('.experience-item');
            experienceItems.forEach((item, index) => {
                const inputs = item.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    const name = input.getAttribute('name');
                    if (name && name.startsWith('workExperiences[')) {
                        input.setAttribute('name', name.replace(/workExperiences\[\d+\]/, `workExperiences[${index}]`));
                    }
                });
            });
            experienceIndex = experienceItems.length;

            // Reindex skill items
            const skillItems = document.querySelectorAll('.skill-item');
            skillItems.forEach((item, index) => {
                const inputs = item.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    const name = input.getAttribute('name');
                    if (name && name.startsWith('skills[')) {
                        input.setAttribute('name', name.replace(/skills\[\d+\]/, `skills[${index}]`));
                    }
                });
            });
            skillIndex = skillItems.length;
        }

        function updateRemoveButtons() {
            // Show/hide remove buttons based on item count
            const educationItems = document.querySelectorAll('.education-item');
            const experienceItems = document.querySelectorAll('.experience-item');
            const skillItems = document.querySelectorAll('.skill-item');

            educationItems.forEach((item, index) => {
                const removeBtn = item.querySelector('.btn-outline-danger');
                if (removeBtn) {
                    removeBtn.style.display = educationItems.length > 1 ? 'inline-block' : 'none';
                }
            });

            experienceItems.forEach((item, index) => {
                const removeBtn = item.querySelector('.btn-outline-danger');
                if (removeBtn) {
                    removeBtn.style.display = experienceItems.length > 1 ? 'inline-block' : 'none';
                }
            });

            skillItems.forEach((item, index) => {
                const removeBtn = item.querySelector('.btn-outline-danger');
                if (removeBtn) {
                    removeBtn.style.display = skillItems.length > 1 ? 'inline-block' : 'none';
                }
            });
        }

        function selectTemplate(templateType) {
            // Remove selected class from all templates
            document.querySelectorAll('.template-preview').forEach(t => t.classList.remove('selected'));
            
            // Add selected class to clicked template
            event.currentTarget.classList.add('selected');
            
            // Check the radio button
            const radio = event.currentTarget.querySelector('input[type="radio"]');
            if (radio) {
                radio.checked = true;
            }
        }

        function saveAsDraft() {
            // Show success message
            const alertDiv = document.createElement('div');
            alertDiv.className = 'alert alert-success fade-in';
            alertDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>Draft saved successfully!';
            
            const container = document.querySelector('.container');
            container.insertBefore(alertDiv, container.firstChild.nextSibling);
            
            // Remove after 3 seconds
            setTimeout(() => {
                alertDiv.remove();
            }, 3000);
        }

        // Initialize effects and functionality
        document.addEventListener('DOMContentLoaded', function() {
            updateRemoveButtons();
            createParticles();
            updateProgress();
            
            // Add input listeners for progress tracking
            document.getElementById('resume-form').addEventListener('input', updateProgress);
            document.getElementById('resume-form').addEventListener('change', updateProgress);
            
            // Add form submission handler to clean up arrays
            document.getElementById('resume-form').addEventListener('submit', function(e) {
                reindexArrays();
                
                // Remove empty skill entries
                const skillItems = document.querySelectorAll('.skill-item');
                skillItems.forEach(item => {
                    const nameInput = item.querySelector('input[name*="skills"][name*=".name"]');
                    if (nameInput && !nameInput.value.trim()) {
                        item.remove();
                    }
                });
                
                // Reindex again after cleanup
                reindexArrays();
            });
            
            // Add glassmorphism hover effects to form elements
            document.querySelectorAll('.form-control, .form-select').forEach(element => {
                element.addEventListener('focus', function() {
                    this.parentElement.style.transform = 'translateY(-2px)';
                });
                
                element.addEventListener('blur', function() {
                    this.parentElement.style.transform = 'translateY(0)';
                });
            });
            
            // Enhanced template selection with animations
            document.querySelectorAll('.template-preview').forEach(template => {
                template.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-10px) rotateY(5deg) scale(1.05)';
                });
                
                template.addEventListener('mouseleave', function() {
                    if (!this.classList.contains('selected')) {
                        this.style.transform = 'translateY(0) rotateY(0) scale(1)';
                    }
                });
            });
        });
    </script>
</body>
</html>