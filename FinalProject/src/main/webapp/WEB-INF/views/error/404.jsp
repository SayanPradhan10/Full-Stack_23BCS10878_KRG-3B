<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Not Found - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
</head>
<body>
    <!-- Glassmorphism Navigation -->
    <nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
        <div class="container">
            <a class="navbar-brand floating" href="${pageContext.request.contextPath}/">
                <i class="fas fa-file-alt me-2"></i>Resume Builder
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/">
                    <i class="fas fa-home me-1"></i>Home
                </a>
            </div>
        </div>
    </nav>

    <!-- Error Content -->
    <div class="container" style="margin-top: 100px;">
        <div class="main-container fade-in">
            <div class="text-center py-5">
                <div class="display-1 mb-4" style="background: linear-gradient(135deg, var(--primary-color), var(--accent-color)); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
                    <i class="fas fa-search"></i>
                </div>
                <h1 class="display-4 fw-bold mb-3" style="font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, var(--primary-color), var(--accent-color)); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">404</h1>
                <h2 class="h3 mb-4" style="color: var(--text-primary);">
                    <c:choose>
                        <c:when test="${not empty errorTitle}">
                            ${errorTitle}
                        </c:when>
                        <c:otherwise>
                            Page Not Found
                        </c:otherwise>
                    </c:choose>
                </h2>
                <p class="lead mb-5" style="color: var(--text-secondary);">
                    <c:choose>
                        <c:when test="${not empty errorMessage}">
                            ${errorMessage}
                        </c:when>
                        <c:otherwise>
                            The page you're looking for doesn't exist or has been moved.
                        </c:otherwise>
                    </c:choose>
                </p>
                
                <div class="d-flex justify-content-center gap-3">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                        <i class="fas fa-home me-2"></i>Go Home
                    </a>
                    <a href="javascript:history.back()" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>Go Back
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
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
    
    <!-- Navbar scroll effect -->
    <script>
        window.addEventListener('scroll', function() {
            const navbar = document.getElementById('mainNavbar');
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
    </script>
</body>
</html>