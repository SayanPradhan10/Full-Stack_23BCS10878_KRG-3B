<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server Error - Resume Builder</title>
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
                <div class="col-md-6">
                    <div class="main-container fade-in text-center">
                        <div class="display-1 mb-4 pulse" style="background: linear-gradient(135deg, var(--danger-color), var(--warning-color)); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                        <h1 class="display-4 fw-bold mb-3" style="font-family: 'Poppins', sans-serif; color: var(--text-primary);">Server Error</h1>
                        <p class="lead mb-4" style="color: var(--text-secondary);">
                            Something went wrong on our end. We're working to fix this issue.
                        </p>
                        
                        <div class="alert alert-danger">
                            <h6><i class="fas fa-info-circle me-2"></i>What happened?</h6>
                            <p class="small mb-0">
                                There was an internal server error while processing your request. 
                                This might be due to a temporary issue or missing data.
                            </p>
                        </div>
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-primary">
                                <i class="fas fa-home me-2"></i>Go to Dashboard
                            </a>
                            <button onclick="history.back()" class="btn btn-outline-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Go Back
                            </button>
                        </div>
                        
                        <div class="mt-4">
                            <small class="text-muted">
                                Error Code: 500 | Time: <%= new java.util.Date() %>
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>