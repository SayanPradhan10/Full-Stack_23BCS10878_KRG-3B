<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Resume Builder</title>
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/user/dashboard">
                            <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/resume/create">
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
                            <i class="fas fa-user-circle me-1"></i>${user.name}
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
                        <i class="fas fa-briefcase me-3"></i>My Resume Portfolio
                    </h1>
                    <p class="lead text-muted">
                        Craft your professional story with our modern resume builder
                    </p>
                </div>
                <div class="col-md-4 text-end">
                    <div class="card border-0 bg-light">
                        <div class="card-body text-center py-3">
                            <h6 class="text-muted mb-1">Welcome back,</h6>
                            <h5 class="fw-bold text-primary mb-1">${user.name}</h5>
                            <small class="text-muted">${user.email}</small>
                            <c:if test="${not empty loadTime}">
                                <div class="mt-2">
                                    <span class="badge badge-primary">
                                        <i class="fas fa-clock me-1"></i>Loaded in ${loadTime}ms
                                    </span>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>        
    <!-- Glassmorphism Statistics Cards -->
            <div class="row mb-4">
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating">
                        <div class="display-4 text-primary mb-3 pulse">
                            <i class="fas fa-file-alt"></i>
                        </div>
                        <h3 class="fw-bold text-primary">${resumeCount}</h3>
                        <p class="text-muted mb-0">Total Resumes</p>
                        <div class="progress mt-3">
                            <div class="progress-bar" style="width: ${resumeCount > 0 ? (resumeCount * 20) : 10}%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating" style="animation-delay: 0.2s;">
                        <div class="display-4 text-success mb-3 pulse" style="animation-delay: 0.5s;">
                            <i class="fas fa-palette"></i>
                        </div>
                        <h5 class="fw-bold text-success">
                            <c:choose>
                                <c:when test="${not empty resumes}">
                                    ${resumes[0].templateType.displayName}
                                </c:when>
                                <c:otherwise>No Template</c:otherwise>
                            </c:choose>
                        </h5>
                        <p class="text-muted mb-0">Latest Template</p>
                        <div class="progress mt-3">
                            <div class="progress-bar bg-success" style="width: 75%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="stats-card bounce-in floating" style="animation-delay: 0.4s;">
                        <div class="display-4 text-warning mb-3 pulse" style="animation-delay: 1s;">
                            <i class="fas fa-clock"></i>
                        </div>
                        <h5 class="fw-bold text-warning">
                            <c:choose>
                                <c:when test="${not empty resumes}">Recent</c:when>
                                <c:otherwise>Never</c:otherwise>
                            </c:choose>
                        </h5>
                        <p class="text-muted mb-0">Last Activity</p>
                        <div class="progress mt-3">
                            <div class="progress-bar bg-warning" style="width: 60%"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Action Bar -->
            <div class="row align-items-center mb-4">
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/resume/create" class="btn btn-primary btn-lg me-3">
                        <i class="fas fa-plus me-2"></i>Create New Resume
                    </a>
                    <a href="${pageContext.request.contextPath}/template/select" class="btn btn-outline-primary">
                        <i class="fas fa-palette me-2"></i>Browse Templates
                    </a>
                </div>
                <div class="col-md-6">
                    <div class="row g-2">
                        <div class="col-md-8">
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-search"></i>
                                </span>
                                <input type="text" id="resume-search" class="form-control" placeholder="Search your resumes...">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <select id="resume-sort" class="form-select">
                                <option value="date">Sort by Date</option>
                                <option value="title">Sort by Title</option>
                                <option value="template">Sort by Template</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Resume Grid -->
            <c:choose>
                <c:when test="${not empty resumes}">
                    <div class="row" id="resume-grid">
                        <c:forEach var="resume" items="${resumes}" varStatus="status">
                            <div class="col-lg-6 col-xl-4 mb-4 resume-item-wrapper slide-up" 
                                 style="animation-delay: ${status.index * 0.1}s;"
                                 data-title="${resume.title}" 
                                 data-template="${resume.templateType.name()}"
                                 data-date="${resume.createdDate}">
                                <div class="card h-100 resume-item">
                                    <div class="card-header bg-gradient text-white">
                                        <div class="d-flex justify-content-between align-items-start">
                                            <div>
                                                <h5 class="card-title mb-1">${resume.title}</h5>
                                                <span class="badge bg-light text-dark">
                                                    ${resume.templateType.displayName}
                                                </span>
                                            </div>
                                            <div class="dropdown">
                                                <button class="btn btn-sm btn-outline-light" type="button" data-bs-toggle="dropdown">
                                                    <i class="fas fa-ellipsis-v"></i>
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resume/${resume.id}">
                                                        <i class="fas fa-eye me-2"></i>Preview
                                                    </a></li>
                                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resume/${resume.id}/edit">
                                                        <i class="fas fa-edit me-2"></i>Edit
                                                    </a></li>
                                                    <li><hr class="dropdown-divider"></li>
                                                    <li><button class="dropdown-item btn-duplicate-resume" data-resume-id="${resume.id}">
                                                        <i class="fas fa-copy me-2"></i>Duplicate
                                                    </button></li>
                                                    <li><button class="dropdown-item text-danger btn-delete-resume" data-resume-id="${resume.id}">
                                                        <i class="fas fa-trash me-2"></i>Delete
                                                    </button></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="card-body">
                                        <div class="row text-center mb-3">
                                            <div class="col-6">
                                                <small class="text-muted d-block">Created</small>
                                                <strong class="text-primary">${resume.createdDate}</strong>
                                            </div>
                                            <div class="col-6">
                                                <small class="text-muted d-block">Modified</small>
                                                <strong class="text-success">${resume.lastModified}</strong>
                                            </div>
                                        </div>
                                        
                                        <div class="d-grid gap-2">
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/resume/${resume.id}" 
                                                   class="btn btn-outline-primary btn-sm">
                                                    <i class="fas fa-eye me-1"></i>Preview
                                                </a>
                                                <a href="${pageContext.request.contextPath}/resume/${resume.id}/edit" 
                                                   class="btn btn-outline-secondary btn-sm">
                                                    <i class="fas fa-edit me-1"></i>Edit
                                                </a>
                                            </div>
                                            
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/template/${resume.id}/download/pdf" 
                                                   class="btn btn-success btn-sm">
                                                    <i class="fas fa-file-pdf me-1"></i>PDF
                                                </a>
                                                <a href="${pageContext.request.contextPath}/template/${resume.id}/download/xml" 
                                                   class="btn btn-success btn-sm">
                                                    <i class="fas fa-code me-1"></i>XML
                                                </a>
                                                <a href="${pageContext.request.contextPath}/template/select?resumeId=${resume.id}" 
                                                   class="btn btn-warning btn-sm">
                                                    <i class="fas fa-palette me-1"></i>Template
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Empty State -->
                    <div class="text-center py-5 fade-in">
                        <div class="display-1 text-muted mb-4">
                            <i class="fas fa-file-alt"></i>
                        </div>
                        <h2 class="fw-bold text-primary mb-3">Ready to Build Your Future?</h2>
                        <p class="lead text-muted mb-4">
                            Create your first professional resume and take the next step in your career journey.
                            Choose from our collection of modern, ATS-friendly templates.
                        </p>
                        <div class="d-flex justify-content-center gap-3 flex-wrap">
                            <a href="${pageContext.request.contextPath}/resume/create" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus me-2"></i>Create Your First Resume
                            </a>
                            <a href="${pageContext.request.contextPath}/template/select" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-palette me-2"></i>Browse Templates
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>    <!-- M
odern Footer -->
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
    
    <!-- Glassmorphism Effects -->
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

        // Particle effect
        function createParticles() {
            const particlesContainer = document.createElement('div');
            particlesContainer.className = 'particles';
            document.body.appendChild(particlesContainer);

            for (let i = 0; i < 50; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 6 + 's';
                particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
                particlesContainer.appendChild(particle);
            }
        }

        // Scroll reveal animation
        function scrollReveal() {
            const reveals = document.querySelectorAll('.scroll-reveal');
            
            for (let i = 0; i < reveals.length; i++) {
                const windowHeight = window.innerHeight;
                const elementTop = reveals[i].getBoundingClientRect().top;
                const elementVisible = 150;
                
                if (elementTop < windowHeight - elementVisible) {
                    reveals[i].classList.add('revealed');
                }
            }
        }

        // Initialize effects
        document.addEventListener('DOMContentLoaded', function() {
            createParticles();
            scrollReveal();
            
            // Add scroll reveal class to cards
            document.querySelectorAll('.card, .stats-card').forEach((el, index) => {
                el.classList.add('scroll-reveal');
                el.style.animationDelay = (index * 0.1) + 's';
            });
        });

        window.addEventListener('scroll', scrollReveal);

        // Search functionality
        document.getElementById('resume-search').addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const resumeItems = document.querySelectorAll('.resume-item-wrapper');
            
            resumeItems.forEach(item => {
                const title = item.dataset.title.toLowerCase();
                const template = item.dataset.template.toLowerCase();
                
                if (title.includes(searchTerm) || template.includes(searchTerm)) {
                    item.style.display = 'block';
                } else {
                    item.style.display = 'none';
                }
            });
        });

        // Sort functionality
        document.getElementById('resume-sort').addEventListener('change', function(e) {
            const sortBy = e.target.value;
            const container = document.getElementById('resume-grid');
            const items = Array.from(container.children);
            
            items.sort((a, b) => {
                switch(sortBy) {
                    case 'title':
                        return a.dataset.title.localeCompare(b.dataset.title);
                    case 'template':
                        return a.dataset.template.localeCompare(b.dataset.template);
                    case 'date':
                    default:
                        return new Date(b.dataset.date) - new Date(a.dataset.date);
                }
            });
            
            items.forEach(item => container.appendChild(item));
        });

        // Delete resume functionality
        document.querySelectorAll('.btn-delete-resume').forEach(button => {
            button.addEventListener('click', function() {
                const resumeId = this.dataset.resumeId;
                const resumeTitle = this.closest('.resume-item-wrapper').dataset.title;
                
                if (confirm(`Are you sure you want to delete "${resumeTitle}"? This action cannot be undone.`)) {
                    // Create form and submit
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = `${pageContext.request.contextPath}/resume/${resumeId}/delete`;
                    
                    const csrfInput = document.createElement('input');
                    csrfInput.type = 'hidden';
                    csrfInput.name = '${_csrf.parameterName}';
                    csrfInput.value = '${_csrf.token}';
                    
                    form.appendChild(csrfInput);
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });

        // Duplicate resume functionality
        document.querySelectorAll('.btn-duplicate-resume').forEach(button => {
            button.addEventListener('click', function() {
                const resumeId = this.dataset.resumeId;
                const resumeTitle = this.closest('.resume-item-wrapper').dataset.title;
                
                if (confirm(`Create a copy of "${resumeTitle}"?`)) {
                    // Create form and submit
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = `${pageContext.request.contextPath}/resume/${resumeId}/duplicate`;
                    
                    const csrfInput = document.createElement('input');
                    csrfInput.type = 'hidden';
                    csrfInput.name = '${_csrf.parameterName}';
                    csrfInput.value = '${_csrf.token}';
                    
                    form.appendChild(csrfInput);
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    </script>
</body>
</html>