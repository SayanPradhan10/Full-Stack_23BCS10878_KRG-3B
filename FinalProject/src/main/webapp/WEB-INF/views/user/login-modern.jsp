<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
</head>
<body>
    <div class="min-vh-100 d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-5">
                    <div class="main-container fade-in floating">
                        <!-- Logo and Header -->
                        <div class="text-center mb-4">
                            <div class="display-4 mb-3 pulse">
                                <i class="fas fa-file-alt"></i>
                            </div>
                            <h1 class="h2 fw-bold mb-2">Resume Builder</h1>
                            <p class="lead">Sign in to your account to continue building your professional future</p>
                        </div>

                        <!-- Error Messages -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger slide-up">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <c:choose>
                                    <c:when test="${error == 'invalid'}">
                                        Invalid email or password. Please try again.
                                    </c:when>
                                    <c:when test="${error == 'expired'}">
                                        Your session has expired. Please sign in again.
                                    </c:when>
                                    <c:otherwise>
                                        ${error}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>

                        <c:if test="${not empty message}">
                            <div class="alert alert-success slide-up">
                                <i class="fas fa-check-circle me-2"></i>${message}
                            </div>
                        </c:if>

                        <!-- Login Form -->
                        <form method="post" action="${pageContext.request.contextPath}/user/login" class="slide-up" style="animation-delay: 0.2s;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            
                            <div class="mb-4">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope me-2"></i>Email Address
                                </label>
                                <input type="email" 
                                       id="email" 
                                       name="email" 
                                       class="form-control form-control-lg" 
                                       placeholder="Enter your email address"
                                       value="${param.email}"
                                       required>
                            </div>

                            <div class="mb-4">
                                <label for="password" class="form-label">
                                    <i class="fas fa-lock me-2"></i>Password
                                </label>
                                <div class="input-group">
                                    <input type="password" 
                                           id="password" 
                                           name="password" 
                                           class="form-control form-control-lg" 
                                           placeholder="Enter your password"
                                           required>
                                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                            </div>

                            <div class="mb-4">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="rememberMe" name="remember-me">
                                    <label class="form-check-label" for="rememberMe">
                                        Remember me for 30 days
                                    </label>
                                </div>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-sign-in-alt me-2"></i>Sign In
                                </button>
                            </div>
                        </form>

                        <!-- Divider -->
                        <div class="text-center mb-4">
                            <div class="position-relative">
                                <hr class="my-4">
                                <span class="position-absolute top-50 start-50 translate-middle bg-white px-3 text-muted">
                                    or
                                </span>
                            </div>
                        </div>

                        <!-- Social Login (Optional) -->
                        <div class="d-grid gap-2 mb-4">
                            <button type="button" class="btn btn-outline-primary">
                                <i class="fab fa-google me-2"></i>Continue with Google
                            </button>
                            <button type="button" class="btn btn-outline-secondary">
                                <i class="fab fa-linkedin me-2"></i>Continue with LinkedIn
                            </button>
                        </div>

                        <!-- Register Link -->
                        <div class="text-center">
                            <p class="text-muted mb-0">
                                Don't have an account? 
                                <a href="${pageContext.request.contextPath}/user/register" class="text-primary fw-semibold text-decoration-none">
                                    Create one now
                                </a>
                            </p>
                        </div>

                        <!-- Features Preview -->
                        <div class="mt-5 pt-4 border-top">
                            <h6 class="text-center text-muted mb-3">Why Choose Resume Builder?</h6>
                            <div class="row text-center">
                                <div class="col-4">
                                    <div class="text-primary mb-2">
                                        <i class="fas fa-palette"></i>
                                    </div>
                                    <small class="text-muted">Professional Templates</small>
                                </div>
                                <div class="col-4">
                                    <div class="text-success mb-2">
                                        <i class="fas fa-robot"></i>
                                    </div>
                                    <small class="text-muted">ATS-Friendly</small>
                                </div>
                                <div class="col-4">
                                    <div class="text-warning mb-2">
                                        <i class="fas fa-download"></i>
                                    </div>
                                    <small class="text-muted">PDF & XML Export</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Glassmorphism Effects & Custom JavaScript -->
    <script>
        // Particle effect
        function createParticles() {
            const particlesContainer = document.createElement('div');
            particlesContainer.className = 'particles';
            document.body.appendChild(particlesContainer);

            for (let i = 0; i < 40; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 6 + 's';
                particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
                particlesContainer.appendChild(particle);
            }
        }

        // Initialize effects
        document.addEventListener('DOMContentLoaded', function() {
            createParticles();
            
            // Add glassmorphism hover effects to form elements
            document.querySelectorAll('.form-control').forEach(element => {
                element.addEventListener('focus', function() {
                    this.parentElement.style.transform = 'translateY(-2px)';
                });
                
                element.addEventListener('blur', function() {
                    this.parentElement.style.transform = 'translateY(0)';
                });
            });
        });

        // Password toggle functionality
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            const icon = this.querySelector('i');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });

        // Form validation
        document.querySelector('form').addEventListener('submit', function(e) {
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            if (!email || !password) {
                e.preventDefault();
                alert('Please fill in all required fields.');
                return false;
            }
            
            // Email validation
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                e.preventDefault();
                alert('Please enter a valid email address.');
                return false;
            }
            
            // Show loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Signing In...';
            submitBtn.disabled = true;
        });

        // Auto-focus on email field
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('email').focus();
        });
    </script>
</body>
</html>