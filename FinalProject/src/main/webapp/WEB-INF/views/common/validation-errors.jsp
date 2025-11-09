<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    Common JSP fragment for displaying validation errors.
    Can be included in any form to show field-specific and global errors.
    
    Usage:
    <jsp:include page="/WEB-INF/views/common/validation-errors.jsp">
        <jsp:param name="bindingResult" value="${bindingResult}" />
        <jsp:param name="objectName" value="user" />
    </jsp:include>
--%>

<c:if test="${not empty bindingResult and bindingResult.hasErrors()}">
    <div class="alert alert-danger" role="alert">
        <h6 class="alert-heading">
            <i class="fas fa-exclamation-triangle me-2"></i>
            <spring:message code="form.validation.error" default="Please correct the errors below and try again" />
        </h6>
        
        <%-- Global errors (not tied to specific fields) --%>
        <c:if test="${bindingResult.hasGlobalErrors()}">
            <ul class="mb-2">
                <c:forEach items="${bindingResult.globalErrors}" var="error">
                    <li><spring:message message="${error}" /></li>
                </c:forEach>
            </ul>
        </c:if>
        
        <%-- Field errors --%>
        <c:if test="${bindingResult.hasFieldErrors()}">
            <ul class="mb-0">
                <c:forEach items="${bindingResult.fieldErrors}" var="error">
                    <li>
                        <strong><spring:message code="${error.field}" default="${error.field}" />:</strong>
                        <spring:message message="${error}" />
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</c:if>

<%-- Success messages from redirect attributes --%>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="fas fa-check-circle me-2"></i>
        <c:out value="${successMessage}" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<%-- Error messages from redirect attributes --%>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i>
        <c:out value="${errorMessage}" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>