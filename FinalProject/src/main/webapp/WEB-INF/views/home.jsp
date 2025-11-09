<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resume Builder - Create Professional Resumes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/modern-style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/">
                <i class="bi bi-file-earmark-text me-2"></i>Resume Builder
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="/resumes">
                        <i class="bi bi-folder me-1"></i>My Resumes
                    </a>
                    <a class="nav-link" href="/resume/create">
                        <i class="bi bi-plus-circle me-1"></i>Create Resume
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <section class="hero-section">
        <div class="container">
            <div class="hero-content fade-in-up">
                <h1 class="hero-title">Build Your Dream Career</h1>
                <p class="hero-subtitle">Create stunning, professional resumes that get you noticed by top employers</p>
                <div class="d-grid gap-3 d-md-flex justify-content-md-center">
                    <a href="/resume/create" class="btn btn-light btn-lg px-4 py-3">
                        <i class="bi bi-plus-circle me-2"></i>Create New Resume
                    </a>
                    <a href="/resumes" class="btn btn-outline-light btn-lg px-4 py-3">
                        <i class="bi bi-folder me-2"></i>View My Resumes
                    </a>
                </div>
            </div>
        </div>
    </section>

    <div class="main-container">
        <div class="container py-5">
            <div class="row text-center mb-5">
                <div class="col-12">
                    <h2 class="display-5 fw-bold mb-3">Why Choose Our Resume Builder?</h2>
                    <p class="lead text-muted">Everything you need to create a professional resume that stands out</p>
                </div>
            </div>

            <div class="row g-4">
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-lightning-charge"></i>
                        </div>
                        <h5 class="card-title">Lightning Fast</h5>
                        <p class="card-text">Create professional resumes in minutes with our intuitive form-based interface. No design skills required.</p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-palette"></i>
                        </div>
                        <h5 class="card-title">Beautiful Templates</h5>
                        <p class="card-text">Choose from professionally designed templates that make your resume stand out to hiring managers.</p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-download"></i>
                        </div>
                        <h5 class="card-title">Export Ready</h5>
                        <p class="card-text">Download your resume in PDF format, perfectly formatted for job applications and ATS systems.</p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-shield-check"></i>
                        </div>
                        <h5 class="card-title">ATS Optimized</h5>
                        <p class="card-text">Our templates are designed to pass through Applicant Tracking Systems used by most companies.</p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-phone"></i>
                        </div>
                        <h5 class="card-title">Mobile Friendly</h5>
                        <p class="card-text">Create and edit your resumes on any device. Our responsive design works perfectly on mobile and desktop.</p>
                    </div>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <div class="card feature-card h-100">
                        <div class="feature-icon">
                            <i class="bi bi-arrow-repeat"></i>
                        </div>
                        <h5 class="card-title">Easy Updates</h5>
                        <p class="card-text">Keep your resume current with easy editing tools. Update your experience and skills as you grow.</p>
                    </div>
                </div>
            </div>

            <div class="row mt-5">
                <div class="col-12 text-center">
                    <div class="card">
                        <div class="card-body py-5">
                            <h3 class="mb-4">Ready to Get Started?</h3>
                            <p class="lead mb-4">Join thousands of professionals who have landed their dream jobs with our resume builder.</p>
                            <a href="/resume/create" class="btn btn-primary btn-lg px-5 py-3">
                                <i class="bi bi-rocket-takeoff me-2"></i>Start Building Now
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Add smooth scrolling and animations
        document.addEventListener('DOMContentLoaded', function() {
            // Add fade-in animation to feature cards
            const cards = document.querySelectorAll('.feature-card');
            const observer = new IntersectionObserver((entries) => {
                entries.forEach((entry, index) => {
                    if (entry.isIntersecting) {
                        setTimeout(() => {
                            entry.target.style.opacity = '1';
                            entry.target.style.transform = 'translateY(0)';
                        }, index * 100);
                    }
                });
            });

            cards.forEach(card => {
                card.style.opacity = '0';
                card.style.transform = 'translateY(30px)';
                card.style.transition = 'all 0.6s ease';
                observer.observe(card);
            });
        });
    </script>
</body>
</html>