<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="totalPageCount" required="true" %>
<body>
<c:set var="p" value="${param.pageNumber}"/>
<c:set var="l" value="9"/>
<c:set var="r" value="${l / 2}"/>
<c:set var="t" value="${totalPageCount}"/>

<c:choose>
    <c:when test="${l > totalPageCount * 1}">
        <c:set var="l" value="${totalPageCount / 2}"/>
    </c:when>
</c:choose>
<c:set var="begin" value="${((p - r) > 0 ? ((p - r) < (t - l + 1) ? (p - r) : (t - l)) : 0) + 1}"/>

<c:set var="end" value="${(p + r) < t ? ((p + r) > l ? (p + r) : l) : t}"/>

<nav aria-label="...">
    <ul class="pagination">

        <c:if test="${(pageNumber > 1)}">
            <a class="page-link"
               href="${pageContext.servletContext.contextPath}/productList?pageNumber=${pageNumber-1}&sort=${param.sort}&order=${param.order}&query=${param.query}"
               aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </c:if>
        <c:forEach var="i" begin="${begin}" end="${end}">

            <li class="page-item <c:if test="${i eq pageNumber}">
                                        active
                                     </c:if>">
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/productList?pageNumber=${i}&sort=${param.sort}&order=${param.order}&query=${param.query}">
                        ${i}
                </a>
            </li>
        </c:forEach>
        <c:if test="${pageNumber < totalPageCount - 0}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/productList?pageNumber=${pageNumber+1}&sort=${param.sort}&order=${param.order}&query=${param.query}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </c:if>
    </ul>
</nav>
</body>

