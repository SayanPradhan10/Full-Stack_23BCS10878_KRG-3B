<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - Resume Builder</title>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/template/select">
                            <i class="fas fa-palette me-1"></i>Templates
                        </a>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle me-1"></i>Profile
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/user/profile">
                                <i class="fas fa-user me-2"></i>Profile Settings
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

        <!-- Profile Header -->
        <div class="main-container fade-in">
            <div class="row align-items-center mb-5">
                <div class="col-md-8">
                    <h1 class="display-5 fw-bold mb-2">
                        <i class="fas fa-user-cog me-3 pulse"></i>Profile Settings
                    </h1>
                    <p class="lead">
                        Manage your account information and preferences
                    </p>
                </div>
                <div class="col-md-4 text-end">
                    <div class="stats-card">
                        <div class="display-4 text-primary mb-3 pulse">
                            <i class="fas fa-shield-alt"></i>
                        </div>
                        <h6 class="text-primary">Account Status</h6>
                        <span class="badge badge-success">Active</span>
                    </div>
                </div>
            </div>

            <div class="row">
                <!-- Profile Form -->
                <div class="col-lg-8">
                    <div class="card mb-4 slide-up">
                        <div class="card-header">
                            <h4 class="mb-0">
                                <i class="fas fa-user me-2"></i>Personal Information
                            </h4>
                        </div>
                        <div class="card-body">
                            <form:form method="post" 
                                      action="${pageContext.request.contextPath}/user/profile" 
                                      modelAttribute="user" 
                                      id="profile-form">
                                
                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <label for="name" class="form-label">
                                            <i class="fas fa-user me-1"></i>Full Name
                                        </label>
                                        <form:input path="name" 
                                                   id="name" 
                                                   class="form-control" 
                                                   placeholder="Enter your full name"
                                                   required="true"/>
                                        <form:errors path="name" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-4">
                                        <label for="email" class="form-label">
                                            <i class="fas fa-envelope me-1"></i>Email Address
                                        </label>
                                        <form:input path="email" 
                                                   type="email"
                                                   id="email" 
                                                   class="form-control" 
                                                   placeholder="your.email@example.com"
                                                   required="true"/>
                                        <form:errors path="email" cssClass="text-danger small"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <label for="phone" class="form-label">
                                            <i class="fas fa-phone me-1"></i>Phone Number
                                        </label>
                                        <form:input path="phone" 
                                                   type="tel"
                                                   id="phone" 
                                                   class="form-control" 
                                                   placeholder="+1 (555) 123-4567"/>
                                        <form:errors path="phone" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-4">
                                        <label for="address" class="form-label">
                                            <i class="fas fa-map-marker-alt me-1"></i>Address
                                        </label>
                                        <form:input path="address" 
                                                   id="address" 
                                                   class="form-control" 
                                                   placeholder="City, State, Country"/>
                                        <form:errors path="address" cssClass="text-danger small"/>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between align-items-center mt-4">
                                    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-primary">
                                        <i class="fas fa-arrow-left me-2"></i>Back to Dashboard
                                    </a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save me-2"></i>Save Changes
                                    </button>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <!-- Security Settings -->
                    <div class="card mb-4 slide-up" style="animation-delay: 0.2s;">
                        <div class="card-header">
                            <h4 class="mb-0">
                                <i class="fas fa-lock me-2"></i>Security Settings
                            </h4>
                        </div>
                        <div class="card-body">
                            <form id="password-form">
                                <div class="mb-4">
                                    <label for="currentPassword" class="form-label">
                                        <i class="fas fa-key me-1"></i>Current Password
                                    </label>
                                    <div class="input-group">
                                        <input type="password" 
                                               id="currentPassword" 
                                               name="currentPassword" 
                                               class="form-control" 
                                               placeholder="Enter current password">
                                        <button class="btn btn-outline-secondary" type="button" onclick="togglePassword('currentPassword')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <label for="newPassword" class="form-label">
                                        <i class="fas fa-lock me-1"></i>New Password
                                    </label>
                                    <div class="input-group">
                                        <input type="password" 
                                               id="newPassword" 
                                               name="newPassword" 
                                               class="form-control" 
                                               placeholder="Enter new password">
                                        <button class="btn btn-outline-secondary" type="button" onclick="togglePassword('newPassword')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                    <div class="form-text">
                                        Password must be at least 8 characters with uppercase, lowercase, and numbers.
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <label for="confirmPassword" class="form-label">
                                        <i class="fas fa-lock me-1"></i>Confirm New Password
                                    </label>
                                    <input type="password" 
                                           id="confirmPassword" 
                                           name="confirmPassword" 
                                           class="form-control" 
                                           placeholder="Confirm new password">
                                </div>

                                <!-- Password Strength Indicator -->
                                <div class="mb-4">
                                    <div class="password-strength">
                                        <div class="progress">
                                            <div class="progress-bar" id="passwordStrength" style="width: 0%;"></div>
                                        </div>
                                        <small class="text-muted mt-1 d-block" id="passwordStrengthText">Password strength</small>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-warning">
                                    <i class="fas fa-shield-alt me-2"></i>Update Password
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Profile Sidebar -->
                <div class="col-lg-4">
                    <!-- Account Overview -->
                    <div class="card mb-4 slide-up" style="animation-delay: 0.3s;">
                        <div class="card-header">
                            <h5 class="mb-0">
                                <i class="fas fa-chart-line me-2"></i>Account Overview
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-3">
                                <div class="flex-shrink-0">
                                    <div class="bg-primary rounded-circle p-3 text-white">
                                        <i class="fas fa-file-alt"></i>
                                    </div>
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h6 class="mb-1">Total Resumes</h6>
                                    <span class="badge badge-primary">${resumeCount > 0 ? resumeCount : 0}</span>
                                </div>
                            </div>

                            <div class="d-flex align-items-center mb-3">
                                <div class="flex-shrink-0">
                                    <div class="bg-success rounded-circle p-3 text-white">
                                        <i class="fas fa-calendar-alt"></i>
                                    </div>
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h6 class="mb-1">Member Since</h6>
                                    <small class="text-muted">${user.createdDate != null ? user.createdDate : 'Recently'}</small>
                                </div>
                            </div>

                            <div class="d-flex align-items-center">
                                <div class="flex-shrink-0">
                                    <div class="bg-warning rounded-circle p-3 text-white">
                                        <i class="fas fa-star"></i>
                                    </div>
                                </div>
                                <div class="flex-grow-1 ms-3">
                                    <h6 class="mb-1">Account Type</h6>
                                    <span class="badge badge-success">Premium</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Quick Actions -->
                    <div class="card mb-4 slide-up" style="animation-delay: 0.4s;">
                        <div class="card-header">
                            <h6 class="mb-0">
                                <i class="fas fa-bolt me-2"></i>Quick Actions
                            </h6>
                        </div>
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/resume/create" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-plus me-1"></i>Create New Resume
                                </a>
                                <a href="${pageContext.request.contextPath}/template/select" class="btn btn-outline-success btn-sm">
                                    <i class="fas fa-palette me-1"></i>Browse Templates
                                </a>
                                <button type="button" class="btn btn-outline-warning btn-sm" onclick="exportData()">
                                    <i class="fas fa-download me-1"></i>Export Data
                                </button>
                                <button type="button" class="btn btn-outline-danger btn-sm" onclick="deleteAccount()">
                                    <i class="fas fa-trash me-1"></i>Delete Account
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Privacy Settings -->
                    <div class="card slide-up" style="animation-delay: 0.5s;">
                        <div class="card-header">
                            <h6 class="mb-0">
                                <i class="fas fa-user-shield me-2"></i>Privacy Settings
                            </h6>
                        </div>
                        <div class="card-body">
                            <div class="form-check form-switch mb-3">
                                <input class="form-check-input" type="checkbox" id="emailNotifications" checked>
                                <label class="form-check-label" for="emailNotifications">
                                    Email Notifications
                                </label>
                            </div>
                            <div class="form-check form-switch mb-3">
                                <input class="form-check-input" type="checkbox" id="profileVisibility">
                                <label class="form-check-label" for="profileVisibility">
                                    Public Profile
                                </label>
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="dataSharing">
                                <label class="form-check-label" for="dataSharing">
                                    Analytics Data Sharing
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modern Footer -->
    <footer class="mt-5 py-4">
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

            for (let i = 0; i < 40; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 6 + 's';
                particle.style.animationDuration = (Math.random() * 3 + 3) + 's';
                particlesContainer.appendChild(particle);
            }
        }

        // Password toggle functionality
        function togglePassword(fieldId) {
            const field = document.getElementById(fieldId);
            const button = field.nextElementSibling.querySelector('i');
            
            if (field.type === 'password') {
                field.type = 'text';
                button.classList.remove('fa-eye');
                button.classList.add('fa-eye-slash');
            } else {
                field.type = 'password';
                button.classList.remove('fa-eye-slash');
                button.classList.add('fa-eye');
            }
        }

        // Password strength checker
        document.getElementById('newPassword').addEventListener('input', function() {
            const password = this.value;
            const strengthBar = document.getElementById('passwordStrength');
            const strengthText = document.getElementById('passwordStrengthText');
            
            let strength = 0;
            let feedback = '';
            
            if (password.length >= 8) strength += 25;
            if (/[A-Z]/.test(password)) strength += 25;
            if (/[a-z]/.test(password)) strength += 25;
            if (/[0-9]/.test(password)) strength += 25;
            
            strengthBar.style.width = strength + '%';
            
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
        document.getElementById('password-form').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                alert('Passwords do not match!');
                return;
            }
            
            if (newPassword.length < 8) {
                alert('Password must be at least 8 characters long!');
                return;
            }
            
            // Show success message
            const alertDiv = document.createElement('div');
            alertDiv.className = 'alert alert-success fade-in';
            alertDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>Password updated successfully!';
            
            const container = document.querySelector('.container');
            container.insertBefore(alertDiv, container.firstChild.nextSibling);
            
            // Clear form
            this.reset();
            
            // Remove alert after 3 seconds
            setTimeout(() => {
                alertDiv.remove();
            }, 3000);
        });

        // Quick actions
        function exportData() {
            alert('Data export feature coming soon!');
        }

        function deleteAccount() {
            if (confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
                alert('Account deletion feature coming soon!');
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
    </script>
</body>
</html>