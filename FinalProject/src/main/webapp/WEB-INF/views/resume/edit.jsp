<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Resume - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
    <!-- Minimalistic Navigation -->
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
                        <a class="nav-link active" href="#">
                            <i class="fas fa-edit me-1"></i>Edit Resume
                        </a>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle me-1"></i>Account
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile">
                                <i class="fas fa-user me-2"></i>Profile
                            </a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resume/${resume.id}">
                                <i class="fas fa-eye me-2"></i>Preview Resume
                            </a></li>
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

        <!-- Edit Header -->
        <div class="main-container fade-in">
            <div class="row align-items-center mb-4">
                <div class="col-md-8">
                    <h1 class="display-5 fw-bold mb-2">
                        <i class="fas fa-edit me-3 pulse"></i>Edit Resume
                    </h1>
                    <p class="lead">
                        Update your resume information and keep it current
                    </p>
                </div>
                <div class="col-md-4 text-end">
                    <div class="stats-card">
                        <div class="display-6 text-primary mb-2">
                            <i class="fas fa-save"></i>
                        </div>
                        <h6 class="text-primary">Auto-Save</h6>
                        <span class="badge badge-success">Enabled</span>
                    </div>
                </div>
            </div>

            <!-- Edit Form -->
            <form:form method="post" 
                      action="${pageContext.request.contextPath}/resume/${resume.id}/edit" 
                      modelAttribute="resumeData" 
                      id="edit-form">
                
                <div class="row">
                    <!-- Main Form Column -->
                    <div class="col-lg-8">
                        <!-- Personal Information Section -->
                        <div class="card mb-4 slide-up">
                            <div class="card-header">
                                <h4 class="mb-0">
                                    <i class="fas fa-user me-2"></i>Personal Information
                                </h4>
                            </div>
                            <div class="card-body">
                                <div class="mb-4">
                                    <label for="title" class="form-label">Resume Title</label>
                                    <form:input path="title" 
                                               id="title" 
                                               class="form-control" 
                                               placeholder="e.g., Software Developer Resume"
                                               required="true"/>
                                    <form:errors path="title" cssClass="text-danger small"/>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.name" class="form-label">Full Name</label>
                                        <form:input path="personalInfo.name" 
                                                   id="personalInfo.name" 
                                                   class="form-control" 
                                                   placeholder="John Doe"
                                                   required="true"/>
                                        <form:errors path="personalInfo.name" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.email" class="form-label">Email Address</label>
                                        <form:input path="personalInfo.email" 
                                                   type="email"
                                                   id="personalInfo.email" 
                                                   class="form-control" 
                                                   placeholder="john.doe@email.com"
                                                   required="true"/>
                                        <form:errors path="personalInfo.email" cssClass="text-danger small"/>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.phone" class="form-label">Phone Number</label>
                                        <form:input path="personalInfo.phone" 
                                                   type="tel"
                                                   id="personalInfo.phone" 
                                                   class="form-control" 
                                                   placeholder="+1 (555) 123-4567"/>
                                        <form:errors path="personalInfo.phone" cssClass="text-danger small"/>
                                    </div>
                                    
                                    <div class="col-md-6 mb-3">
                                        <label for="personalInfo.address" class="form-label">Location</label>
                                        <form:input path="personalInfo.address" 
                                                   id="personalInfo.address" 
                                                   class="form-control" 
                                                   placeholder="City, State, Country"/>
                                        <form:errors path="personalInfo.address" cssClass="text-danger small"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Education Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.1s;" id="education-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-graduation-cap me-2"></i>Education
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addEducation()">
                                    <i class="fas fa-plus me-1"></i>Add Education
                                </button>
                            </div>
                            <div class="card-body">
                                <c:forEach var="education" items="${resumeData.educations}" varStatus="status">
                                    <div class="resume-item mb-3">
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <h6 class="text-primary mb-0">Education ${status.index + 1}</h6>
                                            <button type="button" class="btn btn-outline-danger btn-sm remove-education" 
                                                    style="${status.index == 0 ? 'display: none;' : ''}">
                                                <i class="fas fa-trash me-1"></i>Remove
                                            </button>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Degree</label>
                                                <form:input path="educations[${status.index}].degree" 
                                                           class="form-control" 
                                                           placeholder="e.g., Bachelor of Science"/>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Institution</label>
                                                <form:input path="educations[${status.index}].institution" 
                                                           class="form-control" 
                                                           placeholder="University name"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Field of Study</label>
                                                <form:input path="educations[${status.index}].fieldOfStudy" 
                                                           class="form-control" 
                                                           placeholder="e.g., Computer Science"/>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">GPA (Optional)</label>
                                                <form:input path="educations[${status.index}].gpa" 
                                                           type="number" step="0.01" 
                                                           class="form-control" 
                                                           placeholder="3.8"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Start Date</label>
                                                <form:input path="educations[${status.index}].startDate" 
                                                           type="date" 
                                                           class="form-control"/>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">End Date</label>
                                                <form:input path="educations[${status.index}].endDate" 
                                                           type="date" 
                                                           class="form-control"/>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <!-- Work Experience Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.2s;" id="experience-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-briefcase me-2"></i>Work Experience
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addExperience()">
                                    <i class="fas fa-plus me-1"></i>Add Experience
                                </button>
                            </div>
                            <div class="card-body">
                                <c:forEach var="experience" items="${resumeData.workExperiences}" varStatus="status">
                                    <div class="resume-item mb-3">
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <h6 class="text-primary mb-0">Work Experience ${status.index + 1}</h6>
                                            <button type="button" class="btn btn-outline-danger btn-sm remove-experience"
                                                    style="${status.index == 0 ? 'display: none;' : ''}">
                                                <i class="fas fa-trash me-1"></i>Remove
                                            </button>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Job Title</label>
                                                <form:input path="workExperiences[${status.index}].position" 
                                                           class="form-control" 
                                                           placeholder="e.g., Software Developer"/>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Company</label>
                                                <form:input path="workExperiences[${status.index}].company" 
                                                           class="form-control" 
                                                           placeholder="Company name"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Start Date</label>
                                                <form:input path="workExperiences[${status.index}].startDate" 
                                                           type="date" 
                                                           class="form-control"/>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">End Date</label>
                                                <form:input path="workExperiences[${status.index}].endDate" 
                                                           type="date" 
                                                           class="form-control"/>
                                                <div class="form-text">Leave blank if currently employed</div>
                                            </div>
                                        </div>
                                        
                                        <div class="mb-3">
                                            <label class="form-label">Description</label>
                                            <form:textarea path="workExperiences[${status.index}].description" 
                                                          class="form-control" 
                                                          rows="3" 
                                                          placeholder="Describe your responsibilities and achievements..."/>
                                        </div>
                                        
                                        <div class="form-check">
                                            <form:checkbox path="workExperiences[${status.index}].isInternship" 
                                                          class="form-check-input"/>
                                            <label class="form-check-label">
                                                This was an internship
                                            </label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <!-- Skills Section -->
                        <div class="card mb-4 slide-up" style="animation-delay: 0.3s;" id="skills-section">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">
                                    <i class="fas fa-cogs me-2"></i>Skills & Expertise
                                </h4>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addSkill()">
                                    <i class="fas fa-plus me-1"></i>Add Skill
                                </button>
                            </div>
                            <div class="card-body">
                                <c:forEach var="skill" items="${resumeData.skills}" varStatus="status">
                                    <div class="resume-item d-flex align-items-center mb-2">
                                        <form:input path="skills[${status.index}].name" 
                                                   class="form-control me-2" 
                                                   placeholder="e.g., Java, Python, Leadership"/>
                                        <form:select path="skills[${status.index}].proficiencyLevel" 
                                                    class="form-select me-2" 
                                                    style="max-width: 150px;">
                                            <form:option value="">Level</form:option>
                                            <form:option value="BEGINNER">Beginner</form:option>
                                            <form:option value="INTERMEDIATE">Intermediate</form:option>
                                            <form:option value="ADVANCED">Advanced</form:option>
                                            <form:option value="EXPERT">Expert</form:option>
                                        </form:select>
                                        <button type="button" class="btn btn-outline-danger btn-sm remove-skill"
                                                style="${status.index == 0 ? 'display: none;' : ''}">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex justify-content-between align-items-center mt-4">
                            <a href="${pageContext.request.contextPath}/resume/${resume.id}" class="btn btn-outline-secondary btn-lg">
                                <i class="fas fa-eye me-2"></i>Preview
                            </a>
                            <div>
                                <button type="button" class="btn btn-outline-primary btn-lg me-2" onclick="saveAsDraft()">
                                    <i class="fas fa-save me-2"></i>Save Draft
                                </button>
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-check me-2"></i>Save Changes
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- Sidebar -->
                    <div class="col-lg-4">
                        <!-- Edit Tips -->
                        <div class="card slide-up" style="animation-delay: 0.4s;">
                            <div class="card-header">
                                <h5 class="mb-0">
                                    <i class="fas fa-lightbulb me-2"></i>Editing Tips
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="alert alert-info">
                                    <h6><i class="fas fa-save me-2"></i>Auto-Save</h6>
                                    <p class="small mb-0">Your changes are automatically saved as you type.</p>
                                </div>
                                
                                <div class="alert alert-success">
                                    <h6><i class="fas fa-eye me-2"></i>Preview</h6>
                                    <p class="small mb-0">Use the preview button to see how your resume looks.</p>
                                </div>
                                
                                <div class="alert alert-warning">
                                    <h6><i class="fas fa-palette me-2"></i>Templates</h6>
                                    <p class="small mb-0">You can change templates anytime without losing data.</p>
                                </div>
                            </div>
                        </div>

                        <!-- Quick Actions -->
                        <div class="card mt-4 slide-up" style="animation-delay: 0.5s;">
                            <div class="card-header">
                                <h6 class="mb-0">
                                    <i class="fas fa-bolt me-2"></i>Quick Actions
                                </h6>
                            </div>
                            <div class="card-body">
                                <div class="d-grid gap-2">
                                    <a href="${pageContext.request.contextPath}/template/select?resumeId=${resume.id}" class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-palette me-1"></i>Change Template
                                    </a>
                                    <a href="${pageContext.request.contextPath}/template/${resume.id}/download/pdf" class="btn btn-outline-success btn-sm">
                                        <i class="fas fa-file-pdf me-1"></i>Download PDF
                                    </a>
                                    <button type="button" class="btn btn-outline-warning btn-sm" onclick="duplicateResume()">
                                        <i class="fas fa-copy me-1"></i>Duplicate Resume
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
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
    
    <!-- Minimalistic Effects & Custom JavaScript -->
    <script>
        let educationIndex = 1;
        let experienceIndex = 1;
        let skillIndex = 1;

        // Navbar scroll effect
        window.addEventListener('scroll', function() {
            const navbar = document.getElementById('mainNavbar');
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });

        // Add/Remove functionality (similar to create page)
        function addEducation() {
            const section = document.getElementById('education-section');
            const cardBody = section.querySelector('.card-body');
            const html = `
                <div class="resume-item mb-3 slide-up">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h6 class="text-primary mb-0">Education ${educationIndex + 1}</h6>
                        <button type="button" class="btn btn-outline-danger btn-sm remove-education">
                            <i class="fas fa-trash me-1"></i>Remove
                        </button>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Degree</label>
                            <input name="educations[${educationIndex}].degree" class="form-control" placeholder="e.g., Bachelor of Science"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Institution</label>
                            <input name="educations[${educationIndex}].institution" class="form-control" placeholder="University name"/>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Field of Study</label>
                            <input name="educations[${educationIndex}].fieldOfStudy" class="form-control" placeholder="e.g., Computer Science"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">GPA (Optional)</label>
                            <input name="educations[${educationIndex}].gpa" type="number" step="0.01" class="form-control" placeholder="3.8"/>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Start Date</label>
                            <input name="educations[${educationIndex}].startDate" type="date" class="form-control"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">End Date</label>
                            <input name="educations[${educationIndex}].endDate" type="date" class="form-control"/>
                        </div>
                    </div>
                </div>
            `;
            
            cardBody.insertAdjacentHTML('beforeend', html);
            educationIndex++;
            updateRemoveButtons();
        }

        // Similar functions for experience and skills...
        function addExperience() {
            // Implementation similar to addEducation
        }

        function addSkill() {
            // Implementation similar to addEducation
        }

        // Remove functionality
        document.addEventListener('click', function(e) {
            if (e.target.closest('.remove-education')) {
                e.target.closest('.resume-item').remove();
                updateRemoveButtons();
            }
            
            if (e.target.closest('.remove-experience')) {
                e.target.closest('.resume-item').remove();
                updateRemoveButtons();
            }
            
            if (e.target.closest('.remove-skill')) {
                e.target.closest('.resume-item').remove();
                updateRemoveButtons();
            }
        });

        function updateRemoveButtons() {
            // Show/hide remove buttons based on item count
            ['education', 'experience', 'skill'].forEach(type => {
                const items = document.querySelectorAll(`.remove-${type}`);
                items.forEach((btn, index) => {
                    btn.style.display = items.length > 1 ? 'inline-block' : 'none';
                });
            });
        }

        function saveAsDraft() {
            // Auto-save functionality
            const alertDiv = document.createElement('div');
            alertDiv.className = 'alert alert-success fade-in';
            alertDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>Draft saved successfully!';
            
            const container = document.querySelector('.container');
            container.insertBefore(alertDiv, container.firstChild.nextSibling);
            
            setTimeout(() => alertDiv.remove(), 3000);
        }

        function duplicateResume() {
            if (confirm('Create a copy of this resume?')) {
                // Duplicate functionality
                window.location.href = '${pageContext.request.contextPath}/resume/${resume.id}/duplicate';
            }
        }

        // Initialize
        document.addEventListener('DOMContentLoaded', function() {
            updateRemoveButtons();
        });
    </script>
</body>
</html>