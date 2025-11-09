<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Template - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/resume/create">
                            <i class="fas fa-plus me-1"></i>Create Resume
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/template/select">
                            <i class="fas fa-palette me-1"></i>Templates
                        </a>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/dashboard">
                            <i class="fas fa-arrow-left me-1"></i>Back to Dashboard
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container" style="margin-top: 100px;">
        <div class="main-container fade-in">
            <!-- Header Section -->
            <div class="text-center mb-5">
                <h1 class="display-4 fw-bold mb-3">
                    <i class="fas fa-palette me-3 pulse"></i>Choose Your Template
                </h1>
                <p class="lead">
                    Select a professional template that best represents your style and industry.
                    All templates are ATS-friendly and optimized for modern hiring practices.
                </p>
            </div>

            <!-- Template Categories -->
            <div class="row mb-4">
                <div class="col-12">
                    <div class="d-flex justify-content-center flex-wrap gap-2 mb-4">
                        <button class="btn btn-outline-primary active" data-filter="all">
                            <i class="fas fa-th me-2"></i>All Templates
                        </button>
                        <button class="btn btn-outline-primary" data-filter="classic">
                            <i class="fas fa-briefcase me-2"></i>Classic
                        </button>
                        <button class="btn btn-outline-primary" data-filter="modern">
                            <i class="fas fa-rocket me-2"></i>Modern
                        </button>
                        <button class="btn btn-outline-primary" data-filter="creative">
                            <i class="fas fa-paint-brush me-2"></i>Creative
                        </button>
                    </div>
                </div>
            </div>

            <!-- Template Grid -->
            <div class="row" id="template-grid">
                <!-- Classic Template -->
                <div class="col-lg-4 col-md-6 mb-4 template-item slide-up" data-category="classic">
                    <div class="card template-preview h-100" data-template="CLASSIC">
                        <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 300px;">
                            <div class="text-center">
                                <i class="fas fa-briefcase display-1 text-primary mb-3"></i>
                                <h4 class="text-primary">Classic Professional</h4>
                                <p class="text-muted">Traditional, clean, and professional</p>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title fw-bold">Classic Template</h5>
                            <p class="card-text text-muted">
                                Perfect for traditional industries like finance, law, and consulting. 
                                Clean layout with emphasis on content and readability.
                            </p>
                            <div class="mb-3">
                                <span class="badge bg-primary me-1">Professional</span>
                                <span class="badge bg-success me-1">ATS-Friendly</span>
                                <span class="badge bg-info">Traditional</span>
                            </div>
                            <button class="btn btn-primary w-100 select-template" data-template="CLASSIC">
                                <i class="fas fa-check me-2"></i>Select This Template
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Modern Template -->
                <div class="col-lg-4 col-md-6 mb-4 template-item slide-up" data-category="modern" style="animation-delay: 0.1s;">
                    <div class="card template-preview h-100" data-template="MODERN">
                        <div class="card-img-top bg-gradient d-flex align-items-center justify-content-center" style="height: 300px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                            <div class="text-center text-white">
                                <i class="fas fa-rocket display-1 mb-3"></i>
                                <h4>Modern Professional</h4>
                                <p>Contemporary design with visual appeal</p>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title fw-bold">Modern Template</h5>
                            <p class="card-text text-muted">
                                Contemporary design perfect for tech, startups, and creative industries. 
                                Balanced visual hierarchy with modern typography.
                            </p>
                            <div class="mb-3">
                                <span class="badge bg-primary me-1">Contemporary</span>
                                <span class="badge bg-success me-1">Visual Appeal</span>
                                <span class="badge bg-warning">Tech-Friendly</span>
                            </div>
                            <button class="btn btn-primary w-100 select-template" data-template="MODERN">
                                <i class="fas fa-check me-2"></i>Select This Template
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Creative Template -->
                <div class="col-lg-4 col-md-6 mb-4 template-item slide-up" data-category="creative" style="animation-delay: 0.2s;">
                    <div class="card template-preview h-100" data-template="CREATIVE">
                        <div class="card-img-top d-flex align-items-center justify-content-center" style="height: 300px; background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                            <div class="text-center text-white">
                                <i class="fas fa-paint-brush display-1 mb-3"></i>
                                <h4>Creative Professional</h4>
                                <p>Bold design for creative professionals</p>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title fw-bold">Creative Template</h5>
                            <p class="card-text text-muted">
                                Eye-catching design for creative professionals in design, marketing, 
                                and media. Showcases personality while maintaining professionalism.
                            </p>
                            <div class="mb-3">
                                <span class="badge bg-danger me-1">Creative</span>
                                <span class="badge bg-warning me-1">Eye-Catching</span>
                                <span class="badge bg-info">Design-Forward</span>
                            </div>
                            <button class="btn btn-primary w-100 select-template" data-template="CREATIVE">
                                <i class="fas fa-check me-2"></i>Select This Template
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Template Comparison -->
            <div class="row mt-5">
                <div class="col-12">
                    <div class="card border-0 bg-light">
                        <div class="card-body">
                            <h4 class="text-center mb-4">
                                <i class="fas fa-chart-bar me-2"></i>Template Comparison
                            </h4>
                            <div class="table-responsive">
                                <table class="table table-borderless">
                                    <thead>
                                        <tr>
                                            <th>Feature</th>
                                            <th class="text-center">Classic</th>
                                            <th class="text-center">Modern</th>
                                            <th class="text-center">Creative</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><i class="fas fa-robot me-2"></i>ATS Compatibility</td>
                                            <td class="text-center"><i class="fas fa-check text-success"></i></td>
                                            <td class="text-center"><i class="fas fa-check text-success"></i></td>
                                            <td class="text-center"><i class="fas fa-check text-success"></i></td>
                                        </tr>
                                        <tr>
                                            <td><i class="fas fa-eye me-2"></i>Visual Appeal</td>
                                            <td class="text-center"><span class="badge bg-secondary">Standard</span></td>
                                            <td class="text-center"><span class="badge bg-primary">High</span></td>
                                            <td class="text-center"><span class="badge bg-success">Very High</span></td>
                                        </tr>
                                        <tr>
                                            <td><i class="fas fa-building me-2"></i>Best For Industries</td>
                                            <td class="text-center"><small>Finance, Law, Consulting</small></td>
                                            <td class="text-center"><small>Tech, Startups, Business</small></td>
                                            <td class="text-center"><small>Design, Marketing, Media</small></td>
                                        </tr>
                                        <tr>
                                            <td><i class="fas fa-palette me-2"></i>Customization</td>
                                            <td class="text-center"><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star text-muted"></i></td>
                                            <td class="text-center"><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i></td>
                                            <td class="text-center"><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i><i class="fas fa-star text-warning"></i></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-secondary btn-lg me-3">
                    <i class="fas fa-arrow-left me-2"></i>Back to Dashboard
                </a>
                <c:if test="${not empty resumeId}">
                    <a href="${pageContext.request.contextPath}/resume/${resumeId}" class="btn btn-outline-primary btn-lg">
                        <i class="fas fa-eye me-2"></i>Preview Resume
                    </a>
                </c:if>
            </div>
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
                    <p class="mb-0 opacity-75">Professional templates for every career.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0 opacity-75">&copy; 2024 Resume Builder. All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script>
        // Template filtering
        document.querySelectorAll('[data-filter]').forEach(button => {
            button.addEventListener('click', function() {
                const filter = this.dataset.filter;
                
                // Update active button
                document.querySelectorAll('[data-filter]').forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');
                
                // Filter templates
                document.querySelectorAll('.template-item').forEach(item => {
                    if (filter === 'all' || item.dataset.category === filter) {
                        item.style.display = 'block';
                    } else {
                        item.style.display = 'none';
                    }
                });
            });
        });

        // Template selection
        document.querySelectorAll('.select-template').forEach(button => {
            button.addEventListener('click', function() {
                const template = this.dataset.template;
                const resumeId = '${resumeId}';
                
                // Remove previous selections
                document.querySelectorAll('.template-preview').forEach(preview => {
                    preview.classList.remove('selected');
                });
                
                // Mark as selected
                this.closest('.template-preview').classList.add('selected');
                
                // Create form and submit
                const form = document.createElement('form');
                form.method = 'POST';
                
                if (resumeId) {
                    form.action = `${pageContext.request.contextPath}/template/apply`;
                    
                    const resumeInput = document.createElement('input');
                    resumeInput.type = 'hidden';
                    resumeInput.name = 'resumeId';
                    resumeInput.value = resumeId;
                    form.appendChild(resumeInput);
                } else {
                    form.action = `${pageContext.request.contextPath}/resume/create`;
                }
                
                const templateInput = document.createElement('input');
                templateInput.type = 'hidden';
                templateInput.name = 'templateType';
                templateInput.value = template;
                
                const csrfInput = document.createElement('input');
                csrfInput.type = 'hidden';
                csrfInput.name = '${_csrf.parameterName}';
                csrfInput.value = '${_csrf.token}';
                
                form.appendChild(templateInput);
                form.appendChild(csrfInput);
                document.body.appendChild(form);
                form.submit();
            });
        });

        // Template preview hover effects
        document.querySelectorAll('.template-preview').forEach(preview => {
            preview.addEventListener('mouseenter', function() {
                this.style.transform = 'scale(1.02)';
            });
            
            preview.addEventListener('mouseleave', function() {
                this.style.transform = 'scale(1)';
            });
        });
    </script>
</body>
</html>