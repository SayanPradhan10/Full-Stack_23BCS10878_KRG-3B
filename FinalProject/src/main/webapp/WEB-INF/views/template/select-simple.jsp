<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Template - Resume Builder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .template-card {
            border: 2px solid #dee2e6;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            cursor: pointer;
            transition: all 0.3s;
        }
        .template-card:hover {
            border-color: #0d6efd;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .template-card.selected {
            border-color: #0d6efd;
            background-color: #f8f9ff;
        }
        .template-icon {
            font-size: 3rem;
            text-align: center;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8 mx-auto">
                <h1 class="text-center mb-4">Choose Your Template</h1>
                
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>
                
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                
                <c:if test="${not empty resumeId}">
                    <div class="alert alert-info">
                        <strong>Applying template to Resume ID:</strong> ${resumeId}
                    </div>
                </c:if>
                
                <form id="templateForm" method="post" action="/template/apply">
                    <c:if test="${not empty resumeId}">
                        <input type="hidden" name="resumeId" value="${resumeId}">
                    </c:if>
                    <input type="hidden" name="templateType" id="selectedTemplate">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    
                    <div class="row">
                        <c:forEach var="template" items="${templates}">
                            <div class="col-md-4 mb-3">
                                <div class="template-card template-card-${template.name().toLowerCase()}" data-template="${template}" onclick="selectTemplate('${template}')">
                                    <div class="template-preview-mini">
                                        <c:choose>
                                            <c:when test="${template == 'CLASSIC'}">
                                                <div class="mini-classic">
                                                    <div class="mini-header">
                                                        <div class="mini-name"></div>
                                                        <div class="mini-contact"></div>
                                                    </div>
                                                    <div class="mini-section"></div>
                                                    <div class="mini-section"></div>
                                                </div>
                                            </c:when>
                                            <c:when test="${template == 'MODERN'}">
                                                <div class="mini-modern">
                                                    <div class="mini-sidebar">
                                                        <div class="mini-avatar"></div>
                                                        <div class="mini-contact-info"></div>
                                                    </div>
                                                    <div class="mini-main">
                                                        <div class="mini-section"></div>
                                                        <div class="mini-section"></div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:when test="${template == 'CREATIVE'}">
                                                <div class="mini-creative">
                                                    <div class="mini-header-creative">
                                                        <div class="mini-name-creative"></div>
                                                    </div>
                                                    <div class="mini-content-creative">
                                                        <div class="mini-section-creative"></div>
                                                        <div class="mini-section-creative"></div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="template-icon">📄</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <h4 class="text-center">${template.displayName}</h4>
                                    <p class="text-center text-muted">
                                        <c:choose>
                                            <c:when test="${template == 'CLASSIC'}">
                                                Clean and professional layout perfect for traditional industries. ATS-friendly design.
                                            </c:when>
                                            <c:when test="${template == 'MODERN'}">
                                                Contemporary design with sidebar layout. Great for tech professionals.
                                            </c:when>
                                            <c:when test="${template == 'CREATIVE'}">
                                                Eye-catching design with colors and icons. Perfect for creative fields.
                                            </c:when>
                                            <c:otherwise>Professional resume template</c:otherwise>
                                        </c:choose>
                                    </p>
                                    <div class="text-center">
                                        <span class="badge bg-primary">Select This Template</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="text-center mt-4">
                        <c:choose>
                            <c:when test="${not empty resumeId}">
                                <button type="submit" id="applyBtn" class="btn btn-primary btn-lg" disabled>
                                    Apply Selected Template
                                </button>
                                <a href="/resume/${resumeId}" class="btn btn-secondary btn-lg">Cancel</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/user/dashboard" class="btn btn-secondary btn-lg">Back to Dashboard</a>
                                <a href="/resume/create" class="btn btn-primary btn-lg">Create New Resume</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
                
                <div class="mt-5">
                    <h3 class="text-center mb-4">Template Comparison</h3>
                    <div class="row">
                        <div class="col-md-4 text-center">
                            <div class="p-3 bg-light rounded">
                                <h5>🏢 Traditional Industries</h5>
                                <p class="small">Banking, Law, Healthcare, Government</p>
                                <strong class="text-primary">→ Choose Classic</strong>
                            </div>
                        </div>
                        <div class="col-md-4 text-center">
                            <div class="p-3 bg-light rounded">
                                <h5>💻 Tech & Startups</h5>
                                <p class="small">Software, Engineering, Consulting</p>
                                <strong class="text-primary">→ Choose Modern</strong>
                            </div>
                        </div>
                        <div class="col-md-4 text-center">
                            <div class="p-3 bg-light rounded">
                                <h5>🎨 Creative Fields</h5>
                                <p class="small">Design, Marketing, Media, Arts</p>
                                <strong class="text-primary">→ Choose Creative</strong>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        let selectedTemplateType = null;
        
        function selectTemplate(templateType) {
            // Remove selection from all cards
            document.querySelectorAll('.template-card').forEach(card => {
                card.classList.remove('selected');
            });
            
            // Add selection to clicked card
            document.querySelector(`[data-template="${templateType}"]`).classList.add('selected');
            
            // Update hidden input
            document.getElementById('selectedTemplate').value = templateType;
            selectedTemplateType = templateType;
            
            // Enable apply button
            const applyBtn = document.getElementById('applyBtn');
            if (applyBtn) {
                applyBtn.disabled = false;
                applyBtn.textContent = `Apply ${templateType} Template`;
            }
        }
        
        // Form submission
        document.getElementById('templateForm').addEventListener('submit', function(e) {
            if (!selectedTemplateType) {
                e.preventDefault();
                alert('Please select a template first.');
                return false;
            }
            
            if (confirm(`Are you sure you want to apply the ${selectedTemplateType} template?`)) {
                return true;
            } else {
                e.preventDefault();
                return false;
            }
        });
    </script>
</body>
</html>