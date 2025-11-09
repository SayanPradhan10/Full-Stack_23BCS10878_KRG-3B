<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Data Processing Error - Resume Builder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-body text-center p-5">
                        <div class="mb-4">
                            <i class="fas fa-code text-warning" style="font-size: 4rem;"></i>
                        </div>
                        <h1 class="h2 mb-3 text-dark">Data Processing Error</h1>
                        <p class="lead text-muted mb-4">
                            <c:out value="${errorMessage}" default="We encountered an issue processing your resume data. Please try again." />
                        </p>
                        <div class="alert alert-info" role="alert">
                            <i class="fas fa-info-circle me-2"></i>
                            Your resume data may contain invalid characters or formatting. Please check your input and try again.
                        </div>
                        <div class="mt-4">
                            <button onclick="history.back()" class="btn btn-primary me-3">
                                <i class="fas fa-arrow-left me-2"></i>Go Back and Edit
                            </button>
                            <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-outline-secondary">
                                <i class="fas fa-tachometer-alt me-2"></i>Go to Dashboard
                            </a>
                        </div>
                        <c:if test="${not empty technicalDetails}">
                            <div class="mt-4">
                                <button class="btn btn-link btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#technicalDetails">
                                    <i class="fas fa-cog me-1"></i>Show Technical Details
                                </button>
                                <div class="collapse mt-2" id="technicalDetails">
                                    <div class="alert alert-secondary text-start">
                                        <small><c:out value="${technicalDetails}" /></small>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty errorCode}">
                            <div class="mt-4">
                                <small class="text-muted">Error Code: <c:out value="${errorCode}" /></small>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>