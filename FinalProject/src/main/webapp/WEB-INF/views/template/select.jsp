<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Template - Resume Builder</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body data-resume-id="${resumeId}">
    <!-- Header -->
    <header class="header">
        <div class="container">
            <div class="header-content">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/user/dashboard" style="color: white; text-decoration: none;">
                        Resume Builder
                    </a>
                </div>
                <nav>
                    <ul class="nav-menu">
                        <li><a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/resume/create">Create Resume</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/user/logout" style="display: inline;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-secondary btn-sm">Logout</button>
                            </form>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
        <div class="container">
            <!-- Success/Error Messages -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">
                    ${successMessage}
                </div>
            </c:if>
            
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Page Header -->
            <div class="dashboard-header">
                <div>
                    <h1 class="dashboard-title">Choose Your Template</h1>
                    <p style="color: #6c757d; margin-top: 0.5rem;">
                        Select a professional template that matches your style and industry
                        <c:if test="${not empty resumeId}">
                            <br><small>Applying template to Resume ID: ${resumeId}</small>
                        </c:if>
                    </p>
                </div>
                <div>
                    <c:if test="${not empty resumeId}">
                        <a href="${pageContext.request.contextPath}/resume/${resumeId}" class="btn btn-secondary">
                            ← Back to Resume
                        </a>
                    </c:if>
                </div>
            </div>

            <!-- Template Controls -->
            <div class="template-controls">
                <div class="template-filters">
                    <div class="template-filter">
                        <label for="industry-filter">Industry:</label>
                        <select id="industry-filter">
                            <option value="">All Industries</option>
                            <option value="tech">Technology</option>
                            <option value="business">Business</option>
                            <option value="creative">Creative</option>
                            <option value="academic">Academic</option>
                        </select>
                    </div>
                    <div class="template-filter">
                        <label for="style-filter">Style:</label>
                        <select id="style-filter">
                            <option value="">All Styles</option>
                            <option value="professional">Professional</option>
                            <option value="modern">Modern</option>
                            <option value="creative">Creative</option>
                        </select>
                    </div>
                </div>
                <div class="template-view-toggle">
                    <button type="button" class="view-toggle-btn active" data-view="grid">
                        ⊞ Grid
                    </button>
                    <button type="button" class="view-toggle-btn" data-view="list">
                        ☰ List
                    </button>
                </div>
            </div>

            <!-- Template Gallery -->
            <div class="template-gallery" id="template-gallery">
                <c:forEach var="template" items="${templates}">
                    <div class="template-card" 
                         data-template="${template}" 
                         data-industry="${template == 'CLASSIC' ? 'business academic' : template == 'MODERN' ? 'tech business' : 'creative'}"
                         data-style="${template == 'CLASSIC' ? 'professional' : template == 'MODERN' ? 'modern' : 'creative'}">
                        
                        <!-- Template Preview -->
                        <div class="template-preview-container">
                            <c:choose>
                                <c:when test="${template == 'CLASSIC'}">
                                    <div class="template-preview-placeholder">📄</div>
                                </c:when>
                                <c:when test="${template == 'MODERN'}">
                                    <div class="template-preview-placeholder">🎨</div>
                                </c:when>
                                <c:when test="${template == 'CREATIVE'}">
                                    <div class="template-preview-placeholder">✨</div>
                                </c:when>
                            </c:choose>
                        </div>

                        <!-- Template Info -->
                        <div class="template-info">
                            <h3 class="template-name">${template.displayName}</h3>
                            <p class="template-description">
                                <c:choose>
                                    <c:when test="${template == 'CLASSIC'}">
                                        Clean and professional layout perfect for traditional industries. 
                                        ATS-friendly design ensures your resume passes automated screening systems.
                                    </c:when>
                                    <c:when test="${template == 'MODERN'}">
                                        Contemporary design with sidebar layout and visual elements. 
                                        Great for tech professionals and modern companies.
                                    </c:when>
                                    <c:when test="${template == 'CREATIVE'}">
                                        Eye-catching design with colors and icons. 
                                        Perfect for creative fields like design, marketing, and media.
                                    </c:when>
                                </c:choose>
                            </p>

                            <!-- Template Features -->
                            <div class="template-features">
                                <h5>Key Features:</h5>
                                <ul>
                                    <c:choose>
                                        <c:when test="${template == 'CLASSIC'}">
                                            <li>ATS-friendly format</li>
                                            <li>Traditional layout</li>
                                            <li>Professional typography</li>
                                            <li>Industry standard</li>
                                        </c:when>
                                        <c:when test="${template == 'MODERN'}">
                                            <li>Sidebar design</li>
                                            <li>Visual skill bars</li>
                                            <li>Modern typography</li>
                                            <li>Color accents</li>
                                        </c:when>
                                        <c:when test="${template == 'CREATIVE'}">
                                            <li>Colorful design</li>
                                            <li>Icon integration</li>
                                            <li>Unique layout</li>
                                            <li>Creative elements</li>
                                        </c:when>
                                    </c:choose>
                                </ul>
                            </div>
                        </div>

                        <!-- Template Actions -->
                        <div class="template-actions">
                            <button type="button" 
                                    class="btn btn-secondary btn-sm btn-preview" 
                                    data-template="${template}">
                                👁 Preview
                            </button>
                            <c:if test="${not empty resumeId}">
                                <button type="button" 
                                        class="btn btn-primary btn-sm btn-apply" 
                                        data-template="${template}">
                                    Apply Template
                                </button>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Real-time Preview (if resume ID is provided) -->
            <c:if test="${not empty resumeId}">
                <div class="realtime-preview-container">
                    <div id="realtime-preview">
                        <div class="preview-header">
                            <h4>Live Preview</h4>
                            <p style="color: #6c757d; font-size: 0.9rem; margin: 0;">
                                Select a template above to see how your resume will look
                            </p>
                        </div>
                        <div class="preview-content">
                            <div style="display: flex; align-items: center; justify-content: center; height: 200px; color: #6c757d;">
                                <p>Select a template to see preview</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Action Buttons -->
            <div style="display: flex; justify-content: center; gap: 1rem; margin-top: 2rem; padding-top: 2rem; border-top: 1px solid #e9ecef;">
                <c:choose>
                    <c:when test="${not empty resumeId}">
                        <a href="${pageContext.request.contextPath}/resume/${resumeId}" class="btn btn-secondary">
                            Cancel
                        </a>
                        <button type="button" id="apply-template-btn" class="btn btn-primary" disabled>
                            Select a Template
                        </button>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary">
                            Back to Dashboard
                        </a>
                        <a href="${pageContext.request.contextPath}/resume/create" class="btn btn-primary">
                            Create New Resume
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Template Comparison Section -->
            <div style="margin-top: 3rem; padding-top: 2rem; border-top: 1px solid #e9ecef;">
                <h3 style="text-align: center; color: #2c3e50; margin-bottom: 1rem;">
                    Need Help Choosing?
                </h3>
                <div style="text-align: center; color: #6c757d; margin-bottom: 2rem;">
                    <p>Here's a quick guide to help you select the right template:</p>
                </div>
                
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 2rem;">
                    <div style="text-align: center; padding: 1.5rem; background-color: #f8f9fa; border-radius: 8px;">
                        <div style="font-size: 2rem; margin-bottom: 1rem;">🏢</div>
                        <h4 style="color: #2c3e50; margin-bottom: 0.5rem;">Traditional Industries</h4>
                        <p style="color: #6c757d; font-size: 0.9rem;">
                            Banking, Law, Healthcare, Government
                        </p>
                        <p style="font-weight: 600; color: #3498db;">→ Choose Classic</p>
                    </div>
                    
                    <div style="text-align: center; padding: 1.5rem; background-color: #f8f9fa; border-radius: 8px;">
                        <div style="font-size: 2rem; margin-bottom: 1rem;">💻</div>
                        <h4 style="color: #2c3e50; margin-bottom: 0.5rem;">Tech & Startups</h4>
                        <p style="color: #6c757d; font-size: 0.9rem;">
                            Software, Engineering, Consulting, Finance
                        </p>
                        <p style="font-weight: 600; color: #3498db;">→ Choose Modern</p>
                    </div>
                    
                    <div style="text-align: center; padding: 1.5rem; background-color: #f8f9fa; border-radius: 8px;">
                        <div style="font-size: 2rem; margin-bottom: 1rem;">🎨</div>
                        <h4 style="color: #2c3e50; margin-bottom: 0.5rem;">Creative Fields</h4>
                        <p style="color: #6c757d; font-size: 0.9rem;">
                            Design, Marketing, Media, Arts
                        </p>
                        <p style="font-weight: 600; color: #3498db;">→ Choose Creative</p>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer style="background-color: #2c3e50; color: white; text-align: center; padding: 1rem 0; margin-top: 3rem;">
        <div class="container">
            <p>&copy; 2024 Resume Builder. Build your future, one resume at a time.</p>
        </div>
    </footer>

    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/js/template-selection.js"></script>
    <script>
        // Additional template selection functionality
        document.addEventListener('DOMContentLoaded', function() {
            // Initialize filters
            initializeFilters();
            
            // Initialize view toggle
            initializeViewToggle();
        });

        /**
         * Initialize template filters
         */
        function initializeFilters() {
            const industryFilter = document.getElementById('industry-filter');
            const styleFilter = document.getElementById('style-filter');
            
            if (industryFilter) {
                industryFilter.addEventListener('change', filterTemplates);
            }
            
            if (styleFilter) {
                styleFilter.addEventListener('change', filterTemplates);
            }
        }

        /**
         * Filter templates based on selected criteria
         */
        function filterTemplates() {
            const industryFilter = document.getElementById('industry-filter').value;
            const styleFilter = document.getElementById('style-filter').value;
            const templateCards = document.querySelectorAll('.template-card');
            
            templateCards.forEach(card => {
                const cardIndustries = card.dataset.industry.split(' ');
                const cardStyle = card.dataset.style;
                
                let showCard = true;
                
                if (industryFilter && !cardIndustries.includes(industryFilter)) {
                    showCard = false;
                }
                
                if (styleFilter && cardStyle !== styleFilter) {
                    showCard = false;
                }
                
                card.style.display = showCard ? 'block' : 'none';
            });
        }

        /**
         * Initialize view toggle functionality
         */
        function initializeViewToggle() {
            const toggleButtons = document.querySelectorAll('.view-toggle-btn');
            const gallery = document.getElementById('template-gallery');
            
            toggleButtons.forEach(button => {
                button.addEventListener('click', function() {
                    // Remove active class from all buttons
                    toggleButtons.forEach(btn => btn.classList.remove('active'));
                    
                    // Add active class to clicked button
                    this.classList.add('active');
                    
                    // Update gallery view
                    const view = this.dataset.view;
                    if (view === 'list') {
                        gallery.style.gridTemplateColumns = '1fr';
                        gallery.style.gap = '1rem';
                    } else {
                        gallery.style.gridTemplateColumns = 'repeat(auto-fit, minmax(300px, 1fr))';
                        gallery.style.gap = '2rem';
                    }
                });
            });
        }
    </script>
</body>
</html>