<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit ${resume.title} - Resume Builder</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-style.css" rel="stylesheet">
</head>
<body>
    <!-- Minimalistic Navigation -->
    <nav class="navbar navbar-expand-lg fixed-top" id="mainNavbar">
        <div class="container">
            <a class="navbar-brand floating" href="${pageContext.request.contextPath}/user/dashboard">
                <i class="fas fa-file-alt me-2"></i>Resume Builder
            </a>
            
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/resume/${resume.id}">
                    <i class="fas fa-eye me-1"></i>Preview
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/user/dashboard">
                    <i class="fas fa-arrow-left me-1"></i>Dashboard
                </a>
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

        <div class="main-container fade-in">
            <!-- Header -->
            <div class="text-center mb-4">
                <h1 class="display-6 fw-bold mb-2">
                    <i class="fas fa-edit me-2 pulse"></i>Edit Resume
                </h1>
                <p class="lead">${resume.title}</p>
                <span class="badge badge-primary">${resume.templateType.displayName} Template</span>
            </div>

            <!-- Edit Form -->
            <form:form method="post" 
                      action="${pageContext.request.contextPath}/resume/${resume.id}/edit" 
                      modelAttribute="resumeData" 
                      id="simple-edit-form">
                
                <!-- Personal Information -->
                <div class="card mb-4 slide-up">
                    <div class="card-header">
                        <h4 class="mb-0">
                            <i class="fas fa-user me-2"></i>Personal Information
                        </h4>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Resume Title</label>
                                <form:input path="title" class="form-control" placeholder="Resume title"/>
                                <form:errors path="title" cssClass="text-danger small"/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Full Name</label>
                                <form:input path="personalInfo.name" class="form-control" placeholder="Your full name"/>
                                <form:errors path="personalInfo.name" cssClass="text-danger small"/>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Email Address</label>
                                <form:input path="personalInfo.email" type="email" class="form-control" placeholder="your.email@example.com"/>
                                <form:errors path="personalInfo.email" cssClass="text-danger small"/>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Phone Number</label>
                                <form:input path="personalInfo.phone" class="form-control" placeholder="+1 (555) 123-4567"/>
                                <form:errors path="personalInfo.phone" cssClass="text-danger small"/>
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <form:input path="personalInfo.address" class="form-control" placeholder="City, State, Country"/>
                            <form:errors path="personalInfo.address" cssClass="text-danger small"/>
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
                            <i class="fas fa-plus me-1"></i>Add
                        </button>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty resumeData.educations}">
                                <c:forEach var="education" items="${resumeData.educations}" varStatus="status">
                                    <div class="resume-item mb-3">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <h6 class="text-primary mb-0">Education ${status.index + 1}</h6>
                                            <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)"
                                                    style="${status.index == 0 ? 'display: none;' : ''}">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].degree" 
                                                       value="${education.degree}" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="Degree"/>
                                            </div>
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].institution" 
                                                       value="${education.institution}" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="Institution"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].fieldOfStudy" 
                                                       value="${education.fieldOfStudy}" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="Field of Study"/>
                                            </div>
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].gpa" 
                                                       value="${education.gpa}" 
                                                       type="number" step="0.01" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="GPA"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].startDate" 
                                                       value="${education.startDate}" 
                                                       type="date" 
                                                       class="form-control form-control-sm"/>
                                            </div>
                                            <div class="col-md-6 mb-2">
                                                <input name="educations[${status.index}].endDate" 
                                                       value="${education.endDate}" 
                                                       type="date" 
                                                       class="form-control form-control-sm"/>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="resume-item mb-3">
                                    <h6 class="text-primary mb-2">Education 1</h6>
                                    <div class="row">
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].degree" class="form-control form-control-sm" placeholder="Degree"/>
                                        </div>
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].institution" class="form-control form-control-sm" placeholder="Institution"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].fieldOfStudy" class="form-control form-control-sm" placeholder="Field of Study"/>
                                        </div>
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].gpa" type="number" step="0.01" class="form-control form-control-sm" placeholder="GPA"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].startDate" type="date" class="form-control form-control-sm"/>
                                        </div>
                                        <div class="col-md-6 mb-2">
                                            <input name="educations[0].endDate" type="date" class="form-control form-control-sm"/>
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Work Experience Section -->
                <div class="card mb-4 slide-up" style="animation-delay: 0.2s;" id="experience-section">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">
                            <i class="fas fa-briefcase me-2"></i>Work Experience
                        </h4>
                        <button type="button" class="btn btn-outline-primary btn-sm" onclick="addExperience()">
                            <i class="fas fa-plus me-1"></i>Add
                        </button>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty resumeData.workExperiences}">
                                <c:forEach var="experience" items="${resumeData.workExperiences}" varStatus="status">
                                    <div class="resume-item mb-3">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <h6 class="text-primary mb-0">Experience ${status.index + 1}</h6>
                                            <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)"
                                                    style="${status.index == 0 ? 'display: none;' : ''}">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-2">
                                                <input name="workExperiences[${status.index}].position" 
                                                       value="${experience.position}" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="Job Title"/>
                                            </div>
                                            <div class="col-md-6 mb-2">
                                                <input name="workExperiences[${status.index}].company" 
                                                       value="${experience.company}" 
                                                       class="form-control form-control-sm" 
                                                       placeholder="Company"/>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6 mb-2">
                                                <input name="workExperiences[${status.index}].startDate" 
                                                       value="${experience.startDate}" 
                                                       type="date" 
                                                       class="form-control form-control-sm"/>
                                            </div>
                                            <div class="col-md-6 mb-2">
                                                <input name="workExperiences[${status.index}].endDate" 
                                                       value="${experience.endDate}" 
                                                       type="date" 
                                                       class="form-control form-control-sm"/>
                                            </div>
                                        </div>
                                        
                                        <div class="mb-2">
                                            <textarea name="workExperiences[${status.index}].description" 
                                                      class="form-control form-control-sm" 
                                                      rows="2" 
                                                      placeholder="Job description...">${experience.description}</textarea>
                                        </div>
                                        
                                        <div class="form-check">
                                            <input name="workExperiences[${status.index}].isInternship" 
                                                   type="checkbox" 
                                                   class="form-check-input" 
                                                   ${experience.isInternship ? 'checked' : ''}/>
                                            <label class="form-check-label">Internship</label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="resume-item mb-3">
                                    <h6 class="text-primary mb-2">Experience 1</h6>
                                    <div class="row">
                                        <div class="col-md-6 mb-2">
                                            <input name="workExperiences[0].position" class="form-control form-control-sm" placeholder="Job Title"/>
                                        </div>
                                        <div class="col-md-6 mb-2">
                                            <input name="workExperiences[0].company" class="form-control form-control-sm" placeholder="Company"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-2">
                                            <input name="workExperiences[0].startDate" type="date" class="form-control form-control-sm"/>
                                        </div>
                                        <div class="col-md-6 mb-2">
                                            <input name="workExperiences[0].endDate" type="date" class="form-control form-control-sm"/>
                                        </div>
                                    </div>
                                    <div class="mb-2">
                                        <textarea name="workExperiences[0].description" class="form-control form-control-sm" rows="2" placeholder="Job description..."></textarea>
                                    </div>
                                    <div class="form-check">
                                        <input name="workExperiences[0].isInternship" type="checkbox" class="form-check-input"/>
                                        <label class="form-check-label">Internship</label>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Skills Section -->
                <div class="card mb-4 slide-up" style="animation-delay: 0.3s;" id="skills-section">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">
                            <i class="fas fa-cogs me-2"></i>Skills
                        </h4>
                        <button type="button" class="btn btn-outline-primary btn-sm" onclick="addSkill()">
                            <i class="fas fa-plus me-1"></i>Add
                        </button>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty resumeData.skills}">
                                <c:forEach var="skill" items="${resumeData.skills}" varStatus="status">
                                    <div class="resume-item d-flex align-items-center mb-2">
                                        <input name="skills[${status.index}].name" 
                                               value="${skill.name}" 
                                               class="form-control form-control-sm me-2" 
                                               placeholder="Skill name"/>
                                        <select name="skills[${status.index}].proficiencyLevel" 
                                                class="form-select form-select-sm me-2" 
                                                style="max-width: 120px;">
                                            <option value="">Level</option>
                                            <option value="BEGINNER" ${skill.proficiencyLevel == 'BEGINNER' ? 'selected' : ''}>Beginner</option>
                                            <option value="INTERMEDIATE" ${skill.proficiencyLevel == 'INTERMEDIATE' ? 'selected' : ''}>Intermediate</option>
                                            <option value="ADVANCED" ${skill.proficiencyLevel == 'ADVANCED' ? 'selected' : ''}>Advanced</option>
                                            <option value="EXPERT" ${skill.proficiencyLevel == 'EXPERT' ? 'selected' : ''}>Expert</option>
                                        </select>
                                        <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)"
                                                style="${status.index == 0 ? 'display: none;' : ''}">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="resume-item d-flex align-items-center mb-2">
                                    <input name="skills[0].name" class="form-control form-control-sm me-2" placeholder="Skill name"/>
                                    <select name="skills[0].proficiencyLevel" class="form-select form-select-sm me-2" style="max-width: 120px;">
                                        <option value="">Level</option>
                                        <option value="BEGINNER">Beginner</option>
                                        <option value="INTERMEDIATE">Intermediate</option>
                                        <option value="ADVANCED">Advanced</option>
                                        <option value="EXPERT">Expert</option>
                                    </select>
                                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)" style="display: none;">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <a href="${pageContext.request.contextPath}/resume/${resume.id}" class="btn btn-outline-secondary">
                            <i class="fas fa-eye me-2"></i>Preview
                        </a>
                        <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-primary ms-2">
                            <i class="fas fa-arrow-left me-2"></i>Dashboard
                        </a>
                    </div>
                    <div>
                        <button type="button" class="btn btn-outline-success me-2" onclick="saveAsDraft()">
                            <i class="fas fa-save me-2"></i>Save Draft
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-check me-2"></i>Save Changes
                        </button>
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
    
    <!-- Simple Edit JavaScript -->
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

        function addEducation() {
            const section = document.getElementById('education-section').querySelector('.card-body');
            const html = `
                <div class="resume-item mb-3 slide-up">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <h6 class="text-primary mb-0">Education ${educationIndex + 1}</h6>
                        <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].degree" class="form-control form-control-sm" placeholder="Degree"/>
                        </div>
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].institution" class="form-control form-control-sm" placeholder="Institution"/>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].fieldOfStudy" class="form-control form-control-sm" placeholder="Field of Study"/>
                        </div>
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].gpa" type="number" step="0.01" class="form-control form-control-sm" placeholder="GPA"/>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].startDate" type="date" class="form-control form-control-sm"/>
                        </div>
                        <div class="col-md-6 mb-2">
                            <input name="educations[${educationIndex}].endDate" type="date" class="form-control form-control-sm"/>
                        </div>
                    </div>
                </div>
            `;
            section.insertAdjacentHTML('beforeend', html);
            educationIndex++;
            updateRemoveButtons();
        }

        function addExperience() {
            const section = document.getElementById('experience-section').querySelector('.card-body');
            const html = `
                <div class="resume-item mb-3 slide-up">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <h6 class="text-primary mb-0">Experience ${experienceIndex + 1}</h6>
                        <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-2">
                            <input name="workExperiences[${experienceIndex}].position" class="form-control form-control-sm" placeholder="Job Title"/>
                        </div>
                        <div class="col-md-6 mb-2">
                            <input name="workExperiences[${experienceIndex}].company" class="form-control form-control-sm" placeholder="Company"/>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-2">
                            <input name="workExperiences[${experienceIndex}].startDate" type="date" class="form-control form-control-sm"/>
                        </div>
                        <div class="col-md-6 mb-2">
                            <input name="workExperiences[${experienceIndex}].endDate" type="date" class="form-control form-control-sm"/>
                        </div>
                    </div>
                    
                    <div class="mb-2">
                        <textarea name="workExperiences[${experienceIndex}].description" class="form-control form-control-sm" rows="2" placeholder="Job description..."></textarea>
                    </div>
                    
                    <div class="form-check">
                        <input name="workExperiences[${experienceIndex}].isInternship" type="checkbox" class="form-check-input"/>
                        <label class="form-check-label">Internship</label>
                    </div>
                </div>
            `;
            section.insertAdjacentHTML('beforeend', html);
            experienceIndex++;
            updateRemoveButtons();
        }

        function addSkill() {
            const section = document.getElementById('skills-section').querySelector('.card-body');
            const html = `
                <div class="resume-item d-flex align-items-center mb-2 slide-up">
                    <input name="skills[${skillIndex}].name" class="form-control form-control-sm me-2" placeholder="Skill name"/>
                    <select name="skills[${skillIndex}].proficiencyLevel" class="form-select form-select-sm me-2" style="max-width: 120px;">
                        <option value="">Level</option>
                        <option value="BEGINNER">Beginner</option>
                        <option value="INTERMEDIATE">Intermediate</option>
                        <option value="ADVANCED">Advanced</option>
                        <option value="EXPERT">Expert</option>
                    </select>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            `;
            section.insertAdjacentHTML('beforeend', html);
            skillIndex++;
            updateRemoveButtons();
        }

        function removeItem(button) {
            button.closest('.resume-item').remove();
            updateRemoveButtons();
        }

        function updateRemoveButtons() {
            ['education', 'experience', 'skills'].forEach(type => {
                const section = document.getElementById(`${type}-section`);
                const items = section.querySelectorAll('.resume-item');
                items.forEach((item, index) => {
                    const removeBtn = item.querySelector('.btn-outline-danger');
                    if (removeBtn) {
                        removeBtn.style.display = items.length > 1 ? 'inline-block' : 'none';
                    }
                });
            });
        }

        function saveAsDraft() {
            const alertDiv = document.createElement('div');
            alertDiv.className = 'alert alert-success fade-in';
            alertDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>Draft saved successfully!';
            
            const container = document.querySelector('.container');
            container.insertBefore(alertDiv, container.firstChild.nextSibling);
            
            setTimeout(() => alertDiv.remove(), 3000);
        }

        // Initialize
        document.addEventListener('DOMContentLoaded', function() {
            updateRemoveButtons();
        });
    </script>
</body>
</html>