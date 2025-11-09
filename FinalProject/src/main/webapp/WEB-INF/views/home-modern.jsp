<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resume Builder - Build Your Professional Future</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
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
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="#home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#features">Features</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#templates">Templates</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#pricing">Pricing</a>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/login">
                            <i class="fas fa-sign-in-alt me-1"></i>Sign In
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white ms-2 px-3" href="${pageContext.request.contextPath}/user/register">
                            <i class="fas fa-user-plus me-1"></i>Get Started
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section id="home" class="min-vh-100 d-flex align-items-center" style="margin-top: 80px;">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6 mb-5 mb-lg-0">
                    <div class="fade-in">
                        <h1 class="display-3 fw-bold text-primary mb-4">
                            Build Your <span class="text-gradient">Professional Future</span>
                        </h1>
                        <p class="lead text-muted mb-4">
                            Create stunning, ATS-friendly resumes in minutes with our modern resume builder. 
                            Choose from professional templates designed to get you noticed by employers.
                        </p>
                        <div class="d-flex flex-wrap gap-3 mb-4">
                            <a href="${pageContext.request.contextPath}/user/register" class="btn btn-primary btn-lg">
                                <i class="fas fa-rocket me-2"></i>Start Building Free
                            </a>
                            <a href="#templates" class="btn btn-outline-primary btn-lg">
                                <i class="fas fa-eye me-2"></i>View Templates
                            </a>
                        </div>
                        <div class="d-flex align-items-center text-muted">
                            <i class="fas fa-check-circle text-success me-2"></i>
                            <small>No credit card required • Free forever plan available</small>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="slide-up" style="animation-delay: 0.3s;">
                        <div class="position-relative">
                            <div class="card border-0 shadow-lg">
                                <div class="card-body p-4">
                                    <div class="d-flex align-items-center mb-3">
                                        <div class="bg-primary rounded-circle p-2 me-3">
                                            <i class="fas fa-user text-white"></i>
                                        </div>
                                        <div>
                                            <h6 class="mb-0">John Doe</h6>
                                            <small class="text-muted">Software Developer</small>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <div class="bg-light rounded p-2 mb-2">
                                            <div class="d-flex justify-content-between">
                                                <small class="text-muted">Experience</small>
                                                <small class="text-success">5+ years</small>
                                            </div>
                                        </div>
                                        <div class="bg-light rounded p-2 mb-2">
                                            <div class="d-flex justify-content-between">
                                                <small class="text-muted">Skills</small>
                                                <small class="text-primary">React, Node.js, Python</small>
                                            </div>
                                        </div>
                                        <div class="bg-light rounded p-2">
                                            <div class="d-flex justify-content-between">
                                                <small class="text-muted">Education</small>
                                                <small class="text-warning">Computer Science</small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <span class="badge bg-success">ATS-Friendly</span>
                                        <span class="badge bg-primary ms-1">Professional</span>
                                    </div>
                                </div>
                            </div>
                            <!-- Floating elements -->
                            <div class="position-absolute top-0 end-0 translate-middle">
                                <div class="bg-success rounded-circle p-3 shadow">
                                    <i class="fas fa-check text-white"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-5 bg-light">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="display-5 fw-bold text-primary mb-3">Why Choose Our Resume Builder?</h2>
                <p class="lead text-muted">Everything you need to create a professional resume that gets results</p>
            </div>
            
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="stats-card bounce-in floating">
                        <div class="display-4 text-primary mb-3 pulse">
                            <i class="fas fa-palette"></i>
                        </div>
                        <h5 class="fw-bold mb-3">Professional Templates</h5>
                        <p class="text-muted">
                            Choose from a variety of modern, ATS-friendly templates designed by professionals 
                            to help you stand out from the competition.
                        </p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 border-0 shadow-sm slide-up" style="animation-delay: 0.1s;">
                        <div class="card-body text-center p-4">
                            <div class="display-4 text-success mb-3">
                                <i class="fas fa-robot"></i>
                            </div>
                            <h5 class="fw-bold mb-3">ATS-Optimized</h5>
                            <p class="text-muted">
                                All our templates are optimized for Applicant Tracking Systems (ATS) 
                                to ensure your resume gets past automated screening.
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 border-0 shadow-sm slide-up" style="animation-delay: 0.2s;">
                        <div class="card-body text-center p-4">
                            <div class="display-4 text-warning mb-3">
                                <i class="fas fa-download"></i>
                            </div>
                            <h5 class="fw-bold mb-3">Multiple Formats</h5>
                            <p class="text-muted">
                                Export your resume in PDF, XML, or other formats. Perfect for online 
                                applications, email submissions, or printing.
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 border-0 shadow-sm slide-up" style="animation-delay: 0.3s;">
                        <div class="card-body text-center p-4">
                            <div class="display-4 text-info mb-3">
                                <i class="fas fa-edit"></i>
                            </div>
                            <h5 class="fw-bold mb-3">Easy Editing</h5>
                            <p class="text-muted">
                                Intuitive drag-and-drop interface makes it easy to customize your resume. 
                                Add, remove, or rearrange sections with just a few clicks.
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 border-0 shadow-sm slide-up" style="animation-delay: 0.4s;">
                        <div class="card-body text-center p-4">
                            <div class="display-4 text-danger mb-3">
                                <i class="fas fa-cloud"></i>
                            </div>
                            <h5 class="fw-bold mb-3">Cloud Storage</h5>
                            <p class="text-muted">
                                Your resumes are automatically saved to the cloud. Access them from 
                                anywhere, anytime, on any device.
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 border-0 shadow-sm slide-up" style="animation-delay: 0.5s;">
                        <div class="card-body text-center p-4">
                            <div class="display-4 text-secondary mb-3">
                                <i class="fas fa-shield-alt"></i>
                            </div>
                            <h5 class="fw-bold mb-3">Privacy Focused</h5>
                            <p class="text-muted">
                                Your personal information is secure with us. We never share your data 
                                with third parties and use industry-standard encryption.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Templates Preview Section -->
    <section id="templates" class="py-5">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="display-5 fw-bold text-primary mb-3">Professional Templates</h2>
                <p class="lead text-muted">Choose from our collection of modern, industry-specific templates</p>
            </div>
            
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card template-preview h-100 slide-up">
                        <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 250px;">
                            <div class="text-center">
                                <i class="fas fa-briefcase display-1 text-primary mb-3"></i>
                                <h5 class="text-primary">Classic Professional</h5>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h6 class="fw-bold">Classic Template</h6>
                            <p class="text-muted small">Perfect for traditional industries</p>
                            <span class="badge bg-primary">Professional</span>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card template-preview h-100 slide-up" style="animation-delay: 0.1s;">
                        <div class="card-img-top d-flex align-items-center justify-content-center" style="height: 250px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                            <div class="text-center text-white">
                                <i class="fas fa-rocket display-1 mb-3"></i>
                                <h5>Modern Professional</h5>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h6 class="fw-bold">Modern Template</h6>
                            <p class="text-muted small">Great for tech and startups</p>
                            <span class="badge bg-success">Popular</span>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card template-preview h-100 slide-up" style="animation-delay: 0.2s;">
                        <div class="card-img-top d-flex align-items-center justify-content-center" style="height: 250px; background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                            <div class="text-center text-white">
                                <i class="fas fa-paint-brush display-1 mb-3"></i>
                                <h5>Creative Professional</h5>
                            </div>
                        </div>
                        <div class="card-body text-center">
                            <h6 class="fw-bold">Creative Template</h6>
                            <p class="text-muted small">Perfect for creative fields</p>
                            <span class="badge bg-warning">Creative</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/template/select" class="btn btn-primary btn-lg">
                    <i class="fas fa-eye me-2"></i>View All Templates
                </a>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="py-5 bg-primary text-white">
        <div class="container text-center">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h2 class="display-5 fw-bold mb-3">Ready to Build Your Resume?</h2>
                    <p class="lead mb-4">
                        Join thousands of professionals who have successfully landed their dream jobs 
                        using our resume builder. Start creating your professional resume today!
                    </p>
                    <div class="d-flex justify-content-center gap-3 flex-wrap">
                        <a href="${pageContext.request.contextPath}/user/register" class="btn btn-light btn-lg">
                            <i class="fas fa-rocket me-2"></i>Get Started Free
                        </a>
                        <a href="${pageContext.request.contextPath}/user/login" class="btn btn-outline-light btn-lg">
                            <i class="fas fa-sign-in-alt me-2"></i>Sign In
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="py-5 bg-dark text-white">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 mb-4">
                    <h5 class="fw-bold mb-3">
                        <i class="fas fa-file-alt me-2"></i>Resume Builder
                    </h5>
                    <p class="text-muted">
                        Build professional resumes that get you hired. Modern templates, 
                        ATS-friendly designs, and easy-to-use tools.
                    </p>
                    <div class="d-flex gap-3">
                        <a href="#" class="text-white"><i class="fab fa-facebook fa-lg"></i></a>
                        <a href="#" class="text-white"><i class="fab fa-twitter fa-lg"></i></a>
                        <a href="#" class="text-white"><i class="fab fa-linkedin fa-lg"></i></a>
                        <a href="#" class="text-white"><i class="fab fa-instagram fa-lg"></i></a>
                    </div>
                </div>
                
                <div class="col-lg-2 col-md-6 mb-4">
                    <h6 class="fw-bold mb-3">Product</h6>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-muted text-decoration-none">Templates</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Features</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Pricing</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Examples</a></li>
                    </ul>
                </div>
                
                <div class="col-lg-2 col-md-6 mb-4">
                    <h6 class="fw-bold mb-3">Support</h6>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-muted text-decoration-none">Help Center</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Contact Us</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">FAQ</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Blog</a></li>
                    </ul>
                </div>
                
                <div class="col-lg-2 col-md-6 mb-4">
                    <h6 class="fw-bold mb-3">Company</h6>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-muted text-decoration-none">About Us</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Careers</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Press</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Partners</a></li>
                    </ul>
                </div>
                
                <div class="col-lg-2 col-md-6 mb-4">
                    <h6 class="fw-bold mb-3">Legal</h6>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-muted text-decoration-none">Privacy Policy</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Terms of Service</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">Cookie Policy</a></li>
                        <li><a href="#" class="text-muted text-decoration-none">GDPR</a></li>
                    </ul>
                </div>
            </div>
            
            <hr class="my-4">
            
            <div class="row align-items-center">
                <div class="col-md-6">
                    <p class="mb-0 text-muted">&copy; 2024 Resume Builder. All rights reserved.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0 text-muted">Made with <i class="fas fa-heart text-danger"></i> for job seekers worldwide</p>
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

            for (let i = 0; i < 60; i++) {
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
        });

        // Smooth scrolling for navigation links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });

        // Navbar background on scroll
        window.addEventListener('scroll', function() {
            const navbar = document.querySelector('.navbar');
            if (window.scrollY > 50) {
                navbar.style.background = 'rgba(255, 255, 255, 0.98)';
                navbar.style.backdropFilter = 'blur(10px)';
            } else {
                navbar.style.background = 'rgba(255, 255, 255, 0.95)';
            }
        });

        // Animate elements on scroll
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver(function(entries) {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, observerOptions);

        // Observe all slide-up elements
        document.querySelectorAll('.slide-up').forEach(el => {
            el.style.opacity = '0';
            el.style.transform = 'translateY(30px)';
            el.style.transition = 'all 0.6s ease';
            observer.observe(el);
        });
    </script>
</body>
</html>