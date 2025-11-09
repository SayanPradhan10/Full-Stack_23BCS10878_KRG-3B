<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Resume - Resume Builder</title>
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
                    <a class="nav-link active" href="/resume/create">
                        <i class="bi bi-plus-circle me-1"></i>Create Resume
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <div class="main-container" style="margin-top: 100px;">
        <div class="container py-4">
            <div class="text-center mb-5">
                <h1 class="display-5 fw-bold mb-3">
                    <i class="bi bi-file-earmark-plus text-primary me-3"></i>Create Your Resume
                </h1>
                <p class="lead text-muted">Fill in your information to create a professional resume</p>
            </div>
            
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle me-2"></i>${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle me-2"></i>${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            
            <form:form method="post" modelAttribute="resume" action="/resume/create">
                <div class="row">
                    <div class="col-lg-8">
                        <!-- Personal Information -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <h5><i class="bi bi-person me-2"></i>Personal Information</h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Full Name *</label>
                                        <form:input path="fullName" class="form-control" placeholder="Enter your full name" required="true"/>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Email Address *</label>
                                        <form:input path="email" type="email" class="form-control" placeholder="your.email@example.com" required="true"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Phone Number</label>
                                        <form:input path="phone" class="form-control" placeholder="+1 (555) 123-4567"/>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">LinkedIn Profile</label>
                                        <form:input path="linkedIn" class="form-control" placeholder="linkedin.com/in/yourprofile"/>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Address</label>
                                    <form:input path="address" class="form-control" placeholder="City, State, Country"/>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Professional Summary</label>
                                    <form:textarea path="summary" class="form-control" rows="4" placeholder="Write a brief summary of your professional background and career goals..."/>
                                </div>
                            </div>
                        </div>

                        <!-- Education Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-mortarboard me-2"></i>Education</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addEducation()">
                                    <i class="bi bi-plus me-1"></i>Add Education
                                </button>
                            </div>
                            <div class="card-body" id="education-section">
                                <div class="education-item border rounded p-3 mb-3">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Degree</label>
                                            <input name="educations[0].degree" class="form-control" placeholder="e.g., Bachelor of Science"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Institution</label>
                                            <input name="educations[0].institution" class="form-control" placeholder="University name"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Field of Study</label>
                                            <input name="educations[0].fieldOfStudy" class="form-control" placeholder="e.g., Computer Science"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">GPA (Optional)</label>
                                            <input name="educations[0].gpa" type="number" step="0.01" class="form-control" placeholder="3.8"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Start Date</label>
                                            <input name="educations[0].startDate" type="date" class="form-control"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">End Date</label>
                                            <input name="educations[0].endDate" type="date" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-outline-primary" onclick="addEducation()">
                                    <i class="bi bi-plus me-1"></i>Add Another Education
                                </button>
                            </div>
                        </div>

                        <!-- Experience Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-briefcase me-2"></i>Work Experience</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addExperience()">
                                    <i class="bi bi-plus me-1"></i>Add Experience
                                </button>
                            </div>
                            <div class="card-body" id="experience-section">
                                <div class="experience-item border rounded p-3 mb-3">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Job Title</label>
                                            <input name="experiences[0].jobTitle" class="form-control" placeholder="e.g., Software Developer"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Company</label>
                                            <input name="experiences[0].company" class="form-control" placeholder="Company name"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Start Date</label>
                                            <input name="experiences[0].startDate" type="date" class="form-control"/>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">End Date</label>
                                            <input name="experiences[0].endDate" type="date" class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Description</label>
                                        <textarea name="experiences[0].description" class="form-control" rows="3" placeholder="Describe your responsibilities and achievements..."></textarea>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-outline-primary" onclick="addExperience()">
                                    <i class="bi bi-plus me-1"></i>Add Another Experience
                                </button>
                            </div>
                        </div>

                        <!-- Skills Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-gear me-2"></i>Skills</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addSkill()">
                                    <i class="bi bi-plus me-1"></i>Add Skill
                                </button>
                            </div>
                            <div class="card-body" id="skills-section">
                                <div class="skill-item d-flex align-items-center mb-2">
                                    <input name="skills[0].name" class="form-control me-2" placeholder="e.g., Java, Python, Leadership"/>
                                    <select name="skills[0].level" class="form-select me-2" style="max-width: 150px;">
                                        <option value="">Level</option>
                                        <option value="BEGINNER">Beginner</option>
                                        <option value="INTERMEDIATE">Intermediate</option>
                                        <option value="ADVANCED">Advanced</option>
                                        <option value="EXPERT">Expert</option>
                                    </select>
                                </div>
                                <button type="button" class="btn btn-outline-primary" onclick="addSkill()">
                                    <i class="bi bi-plus me-1"></i>Add Another Skill
                                </button>
                            </div>
                        </div>

                        <!-- Projects Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-code-square me-2"></i>Projects</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addProject()">
                                    <i class="bi bi-plus me-1"></i>Add Project
                                </button>
                            </div>
                            <div class="card-body" id="projects-section">
                                <button type="button" class="btn btn-outline-primary" onclick="addProject()">
                                    <i class="bi bi-plus me-1"></i>Add Project
                                </button>
                            </div>
                        </div>

                        <!-- Certifications Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-award me-2"></i>Certifications</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addCertification()">
                                    <i class="bi bi-plus me-1"></i>Add Certification
                                </button>
                            </div>
                            <div class="card-body" id="certifications-section">
                                <button type="button" class="btn btn-outline-primary" onclick="addCertification()">
                                    <i class="bi bi-plus me-1"></i>Add Certification
                                </button>
                            </div>
                        </div>

                        <!-- Interests Section -->
                        <div class="card mb-4">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h5><i class="bi bi-heart me-2"></i>Interests</h5>
                                <button type="button" class="btn btn-outline-primary btn-sm" onclick="addInterest()">
                                    <i class="bi bi-plus me-1"></i>Add Interest
                                </button>
                            </div>
                            <div class="card-body" id="interests-section">
                                <button type="button" class="btn btn-outline-primary" onclick="addInterest()">
                                    <i class="bi bi-plus me-1"></i>Add Interest
                                </button>
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end mb-4">
                            <a href="/resumes" class="btn btn-outline-secondary btn-lg me-md-2">
                                <i class="bi bi-arrow-left me-1"></i>Cancel
                            </a>
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="bi bi-check-circle me-1"></i>Create Resume
                            </button>
                        </div>
                    </div>
            
                    <div class="col-lg-4">
                        <div class="card sticky-top" style="top: 120px;">
                            <div class="card-header">
                                <h5><i class="bi bi-lightbulb me-2"></i>Tips & Guidelines</h5>
                            </div>
                            <div class="card-body">
                                <div class="alert alert-info">
                                    <h6><i class="bi bi-file-text me-1"></i>Professional Summary</h6>
                                    <p class="small mb-0">Write 2-3 sentences highlighting your key qualifications and career goals.</p>
                                </div>
                                <div class="alert alert-success">
                                    <h6><i class="bi bi-rocket me-1"></i>Projects</h6>
                                    <p class="small mb-0">Include your best projects that demonstrate your skills and experience.</p>
                                </div>
                                <div class="alert alert-warning">
                                    <h6><i class="bi bi-trophy me-1"></i>Certifications</h6>
                                    <p class="small mb-0">Add relevant certifications to boost your credibility.</p>
                                </div>
                                <div class="alert alert-primary">
                                    <h6><i class="bi bi-heart me-1"></i>Interests</h6>
                                    <p class="small mb-0">Include interests that show your personality and cultural fit.</p>
                                </div>
                                
                                <div class="mt-4">
                                    <h6 class="fw-bold">Quick Tips:</h6>
                                    <ul class="small text-muted">
                                        <li>Use action verbs in descriptions</li>
                                        <li>Quantify achievements when possible</li>
                                        <li>Keep it concise and relevant</li>
                                        <li>Proofread for errors</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let educationIndex = 1;
        let experienceIndex = 1;
        let skillIndex = 1;
        let projectIndex = 1;
        let certificationIndex = 1;
        let interestIndex = 1;
        
        function addEducation() {
            const section = document.getElementById('education-section');
            const button = section.querySelector('button');
            const html = `
                <div class="education-item border rounded p-3 mb-3">
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
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash me-1"></i>Remove
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            educationIndex++;
        }
        
        function addExperience() {
            const section = document.getElementById('experience-section');
            const button = section.querySelector('button');
            const html = `
                <div class="experience-item border rounded p-3 mb-3">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Job Title</label>
                            <input name="experiences[${experienceIndex}].jobTitle" class="form-control" placeholder="e.g., Software Developer"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Company</label>
                            <input name="experiences[${experienceIndex}].company" class="form-control" placeholder="Company name"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Start Date</label>
                            <input name="experiences[${experienceIndex}].startDate" type="date" class="form-control"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">End Date</label>
                            <input name="experiences[${experienceIndex}].endDate" type="date" class="form-control"/>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea name="experiences[${experienceIndex}].description" class="form-control" rows="3" placeholder="Describe your responsibilities and achievements..."></textarea>
                    </div>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash me-1"></i>Remove
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            experienceIndex++;
        }
        
        function addSkill() {
            const section = document.getElementById('skills-section');
            const button = section.querySelector('button');
            const html = `
                <div class="skill-item d-flex align-items-center mb-2">
                    <input name="skills[${skillIndex}].name" class="form-control me-2" placeholder="e.g., Java, Python, Leadership"/>
                    <select name="skills[${skillIndex}].level" class="form-select me-2" style="max-width: 150px;">
                        <option value="">Level</option>
                        <option value="BEGINNER">Beginner</option>
                        <option value="INTERMEDIATE">Intermediate</option>
                        <option value="ADVANCED">Advanced</option>
                        <option value="EXPERT">Expert</option>
                    </select>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            skillIndex++;
        }
        
        function addProject() {
            const section = document.getElementById('projects-section');
            const button = section.querySelector('button');
            const html = `
                <div class="project-item border rounded p-3 mb-3">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Project Name</label>
                            <input name="projects[${projectIndex}].name" class="form-control" placeholder="e.g., E-commerce Website"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Technologies Used</label>
                            <input name="projects[${projectIndex}].technologies" class="form-control" placeholder="e.g., Java, Spring Boot, MySQL"/>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea name="projects[${projectIndex}].description" class="form-control" rows="3" placeholder="Describe the project and your role..."></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Project URL (Optional)</label>
                        <input name="projects[${projectIndex}].url" type="url" class="form-control" placeholder="https://github.com/username/project"/>
                    </div>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash me-1"></i>Remove
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            projectIndex++;
        }
        
        function addCertification() {
            const section = document.getElementById('certifications-section');
            const button = section.querySelector('button');
            const html = `
                <div class="certification-item border rounded p-3 mb-3">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Certification Name</label>
                            <input name="certifications[${certificationIndex}].name" class="form-control" placeholder="e.g., AWS Certified Developer"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Issuing Organization</label>
                            <input name="certifications[${certificationIndex}].issuingOrganization" class="form-control" placeholder="e.g., Amazon Web Services"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Issue Date</label>
                            <input name="certifications[${certificationIndex}].issueDate" type="date" class="form-control"/>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Expiry Date (Optional)</label>
                            <input name="certifications[${certificationIndex}].expiryDate" type="date" class="form-control"/>
                        </div>
                    </div>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash me-1"></i>Remove
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            certificationIndex++;
        }
        
        function addInterest() {
            const section = document.getElementById('interests-section');
            const button = section.querySelector('button');
            const html = `
                <div class="interest-item d-flex align-items-center mb-2">
                    <input name="interests[${interestIndex}].name" class="form-control me-2" placeholder="e.g., Photography, Hiking, Open Source"/>
                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="removeItem(this)">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            `;
            button.insertAdjacentHTML('beforebegin', html);
            interestIndex++;
        }
        
        function removeItem(button) {
            const item = button.closest('.education-item, .experience-item, .skill-item, .project-item, .certification-item, .interest-item');
            if (item) {
                item.style.transition = 'all 0.3s ease';
                item.style.opacity = '0';
                item.style.transform = 'translateX(-20px)';
                setTimeout(() => item.remove(), 300);
            }
        }

        // Add form validation and loading states
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const submitBtn = form.querySelector('button[type="submit"]');
            
            form.addEventListener('submit', function() {
                submitBtn.classList.add('loading');
                submitBtn.disabled = true;
            });
        });
    </script>
</body>
</html>