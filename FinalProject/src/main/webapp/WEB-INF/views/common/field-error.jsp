<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    JSP fragment for displaying field-specific validation errors.
    Shows error message directly below a form field.
    
    Usage:
    <jsp:include page="/WEB-INF/views/common/field-error.jsp">
        <jsp:param name="fieldName" value="email" />
        <jsp:param name="bindingResult" value="${bindingResult}" />
    </jsp:include>
--%>

<c:set var="fieldName" value="${param.fieldName}" />
<c:set var="hasFieldError" value="${not empty bindingResult and bindingResult.hasFieldErrors(fieldName)}" />

<c:if test="${hasFieldError}">
    <div class="invalid-feedback d-block">
        <c:forEach items="${bindingResult.getFieldErrors(fieldName)}" var="error">
            <div class="text-danger small">
                <i class="fas fa-exclamation-circle me-1"></i>
                <spring:message message="${error}" />
            </div>
        </c:forEach>
    </div>
</c:if>

<%-- Add CSS class to make field appear invalid --%>
<c:if test="${hasFieldError}">
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var field = document.querySelector('[name="${fieldName}"]');
            if (field) {
                field.classList.add('is-invalid');
            }
        });
    </script>
</c:if>