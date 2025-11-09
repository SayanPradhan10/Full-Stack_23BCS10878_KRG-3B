<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Resume Builder</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Dashboard</h1>
        
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        
        <div class="row">
            <div class="col-md-8">
                <h2>Welcome, ${user.name}!</h2>
                <p>Email: ${user.email}</p>
                <p>Total Resumes: ${resumeCount}</p>
                
                <a href="/resume/create" class="btn btn-primary">Create New Resume</a>
                <a href="/user/profile" class="btn btn-secondary">Edit Profile</a>
                
                <h3 class="mt-4">Your Resumes</h3>
                
                <c:choose>
                    <c:when test="${not empty resumes}">
                        <div class="row">
                            <c:forEach var="resume" items="${resumes}">
                                <div class="col-md-6 mb-3">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title">${resume.title}</h5>
                                            <p class="card-text">Template: ${resume.templateType}</p>
                                            <a href="/resume/${resume.id}" class="btn btn-primary btn-sm">View</a>
                                            <a href="/resume/${resume.id}/edit" class="btn btn-secondary btn-sm">Edit</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            <h4>No Resumes Yet</h4>
                            <p>Get started by creating your first resume!</p>
                            <a href="/resume/create" class="btn btn-primary">Create Your First Resume</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
        <form method="post" action="/user/logout" class="mt-4">
            <button type="submit" class="btn btn-outline-danger">Logout</button>
        </form>
    </div>
</body>
</html>