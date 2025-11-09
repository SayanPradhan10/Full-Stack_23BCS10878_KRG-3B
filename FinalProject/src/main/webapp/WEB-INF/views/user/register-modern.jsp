<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
</head>
<body>
    <div class="min-vh-100 d-flex align-items-center py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="main-container fade-in floating">
                        <!-- Logo and Header -->
                        <div class="text-center mb-4">
                            <div class="display-4 mb-3 pulse">
                                <i class="fas fa-file-alt"></i>
                            </div>
                            <h1 class="h2 fw-bold mb-2">Join Resume Builder</h1>
                            <p class="lead">Create your account and start building professional resumes in minutes</p>
                        </div>

                        <!-- Error Messages -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger slide-up">
                                <i class="fas fa-exclamation-circle me-2"></i>${error}
                            </div>
                        </c:if>

                        <c:if test="${not empty message}">
                            <div class="alert alert-success slide-up">
                                <i class="fas fa-check-circle me-2"></i>${message}
                            </div>
                        </c:if>

                        <!-- Registration Form -->
                        <form:form method="post" 
                                   action="${pageContext.request.contextPath}/user/register" 
                                   modelAttribute="user" 
                                   class="slide-up" 
                                   style="animation-delay: 0.2s;">
                            
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <label for="name" class="form-label">
                                        <i class="fas fa-user me-2"></i>Full Name
                                    </label>
                                    <form:input path="name" 
                                               id="name"
                                               class="form-control form-control-lg" 
                                               placeholder="Enter your full name"
                                               required="true"/>
                                    <form:errors path="name" cssClass="text-danger small mt-1 d-block"/>
                                </div>

                                <div class="col-md-6 mb-4">
                                    <label for="email" class="form-label">
                                        <i class="fas fa-envelope me-2"></i>Email Address
                                    </label>
                                    <form:input path="email" 
                                               type="email"
                                               id="email"
                                               class="form-control form-control-lg" 
                                               placeholder="Enter your email address"
                                               required="true"/>
                                    <form:errors path="email" cssClass="text-danger small mt-1 d-block"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <label for="phone" class="form-label">
                                        <i class="fas fa-phone me-2"></i>Phone Number
                                    </label>
                                    <form:input path="phone" 
                                               id="phone"
                                               class="form-control form-control-lg" 
                                               placeholder="Enter your phone number"/>
                                    <form:errors path="phone" cssClass="text-danger small mt-1 d-block"/>
                                </div>

                                <div class="col-md-6 mb-4">
                                    <label for="address" class="form-label">
                                        <i class="fas fa-map-marker-alt me-2"></i>Location
                                    </label>
                                    <form:input path="address" 
                                               id="address"
                                               class="form-control form-control-lg" 
                                               placeholder="City, State/Country"/>
                                    <form:errors path="address" cssClass="text-danger small mt-1 d-block"/>
                                </div>
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
                                           placeholder="Create a strong password"
                                           required>
                                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="form-text">
                                    <small class="text-muted">
                                        Password must be at least 8 characters long and contain uppercase, lowercase, and numbers.
                                    </small>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="confirmPassword" class="form-label">
                                    <i class="fas fa-lock me-2"></i>Confirm Password
                                </label>
                                <input type="password" 
                                       id="confirmPassword" 
                                       name="confirmPassword" 
                                       class="form-control form-control-lg" 
                                       placeholder="Confirm your password"
                                       required>
                            </div>

                            <!-- Password Strength Indicator -->
                            <div class="mb-4">
                                <div class="password-strength">
                                    <div class="progress" style="height: 4px;">
                                        <div class="progress-bar" id="passwordStrength" style="width: 0%;"></div>
                                    </div>
                                    <small class="text-muted mt-1 d-block" id="passwordStrengthText">Password strength</small>
                                </div>
                            </div>

                            <div class="mb-4">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="agreeTerms" required>
                                    <label class="form-check-label" for="agreeTerms">
                                        I agree to the 
                                        <a href="#" class="text-primary text-decoration-none">Terms of Service</a> 
                                        and 
                                        <a href="#" class="text-primary text-decoration-none">Privacy Policy</a>
                                    </label>
                                </div>
                            </div>

                            <div class="mb-4">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="newsletter">
                                    <label class="form-check-label" for="newsletter">
                                        Send me tips and updates about resume building (optional)
                                    </label>
                                </div>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-user-plus me-2"></i>Create Account
                                </button>
                            </div>
                        </form:form>

                        <!-- Divider -->
                        <div class="text-center mb-4">
                            <div class="position-relative">
                                <hr class="my-4">
                                <span class="position-absolute top-50 start-50 translate-middle bg-white px-3 text-muted">
                                    or
                                </span>
                            </div>
                        </div>

                        <!-- Social Registration -->
                        <div class="d-grid gap-2 mb-4">
                            <button type="button" class="btn btn-outline-primary">
                                <i class="fab fa-google me-2"></i>Sign up with Google
                            </button>
                            <button type="button" class="btn btn-outline-secondary">
                                <i class="fab fa-linkedin me-2"></i>Sign up with LinkedIn
                            </button>
                        </div>

                        <!-- Login Link -->
                        <div class="text-center">
                            <p class="text-muted mb-0">
                                Already have an account? 
                                <a href="${pageContext.request.contextPath}/user/login" class="text-primary fw-semibold text-decoration-none">
                                    Sign in here
                                </a>
                            </p>
                        </div>

                        <!-- Benefits Section -->
                        <div class="mt-5 pt-4 border-top">
                            <h6 class="text-center text-muted mb-4">What you'll get with Resume Builder</h6>
                            <div class="row">
                                <div class="col-md-4 text-center mb-3">
                                    <div class="text-primary mb-2">
                                        <i class="fas fa-palette fa-2x"></i>
                                    </div>
                                    <h6 class="fw-semibold">Professional Templates</h6>
                                    <small class="text-muted">Choose from modern, ATS-friendly templates</small>
                                </div>
                                <div class="col-md-4 text-center mb-3">
                                    <div class="text-success mb-2">
                                        <i class="fas fa-download fa-2x"></i>
                                    </div>
                                    <h6 class="fw-semibold">Multiple Formats</h6>
                                    <small class="text-muted">Export as PDF, XML, or print-ready formats</small>
                                </div>
                                <div class="col-md-4 text-center mb-3">
                                    <div class="text-warning mb-2">
                                        <i class="fas fa-cloud fa-2x"></i>
                                    </div>
                                    <h6 class="fw-semibold">Cloud Storage</h6>
                                    <small class="text-muted">Access your resumes from anywhere, anytime</small>
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
    
    <!-- Custom JavaScript -->
    <script>
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

        // Password strength checker
        document.getElementById('password').addEventListener('input', function() {
            const password = this.value;
            const strengthBar = document.getElementById('passwordStrength');
            const strengthText = document.getElementById('passwordStrengthText');
            
            let strength = 0;
            let feedback = '';
            
            // Length check
            if (password.length >= 8) strength += 25;
            
            // Uppercase check
            if (/[A-Z]/.test(password)) strength += 25;
            
            // Lowercase check
            if (/[a-z]/.test(password)) strength += 25;
            
            // Number check
            if (/[0-9]/.test(password)) strength += 25;
            
            // Update progress bar
            strengthBar.style.width = strength + '%';
            
            // Update color and text
            if (strength < 50) {
                strengthBar.className = 'progress-bar bg-danger';
                feedback = 'Weak password';
            } else if (strength < 75) {
                strengthBar.className = 'progress-bar bg-warning';
                feedback = 'Fair password';
            } else if (strength < 100) {
                strengthBar.className = 'progress-bar bg-info';
                feedback = 'Good password';
            } else {
                strengthBar.className = 'progress-bar bg-success';
                feedback = 'Strong password';
            }
            
            strengthText.textContent = feedback;
        });

        // Form validation
        document.querySelector('form').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const agreeTerms = document.getElementById('agreeTerms').checked;
            
            // Password match check
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Passwords do not match. Please try again.');
                return false;
            }
            
            // Terms agreement check
            if (!agreeTerms) {
                e.preventDefault();
                alert('Please agree to the Terms of Service and Privacy Policy.');
                return false;
            }
            
            // Password strength check
            if (password.length < 8) {
                e.preventDefault();
                alert('Password must be at least 8 characters long.');
                return false;
            }
            
            // Show loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Creating Account...';
            submitBtn.disabled = true;
        });

        // Real-time password confirmation
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('password').value;
            const confirmPassword = this.value;
            
            if (confirmPassword && password !== confirmPassword) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });

        // Auto-focus on name field
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('name').focus();
        });
    </script>
</body>
</html>