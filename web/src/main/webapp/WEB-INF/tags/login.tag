<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<s:authorize access="isAnonymous()">
  <a href="${pageContext.servletContext.contextPath}/login">Login</a>
</s:authorize>

<s:authorize access="isAuthenticated()">
  <s:authentication property="principal.username"/>
  <a href="${pageContext.servletContext.contextPath}/logout">Logout</a>
</s:authorize>

<s:authorize access="hasRole('ROLE_ADMIN')">
  <a href="${pageContext.servletContext.contextPath}/admin/orders">Orders</a>
</s:authorize>