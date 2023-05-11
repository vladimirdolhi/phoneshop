<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="type" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="errors" required="true" type="org.springframework.validation.BindingResult" %>

<tr>
    <c:if test="${type eq 'input'}">
        <div class="form-group">
            <label for="${name}">${label}</label>
            <spring:input path="${name}" class="form-control" id="${name}"></spring:input>
            <spring:errors class="font-weight-bold" path="${name}" cssStyle="color: red"/>
        </div>
    </c:if>
    <c:if test="${type eq 'textArea'}">
        <div class="form-group">
            <label for="${name}">${label}</label>
            <spring:textarea path="${name}" class="form-control" id="${name}" rows="3"></spring:textarea>
            <spring:errors class="font-weight-bold" path="${name}" cssStyle="color: red"/>
        </div>
    </c:if>
</tr>