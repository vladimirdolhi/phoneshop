<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="totalPageCount" required="true" %>

<c:set var="pagesPerSide" value="5"/>
<c:set var="totalPages" value="${pagesPerSide * 2 + 1}"/>

<c:set var="leftBound" value="${pageNumber > pagesPerSide ?
       (totalPageCount - pageNumber < pagesPerSide ? totalPageCount - 10 : pageNumber - pagesPerSide) : 1}"/>

<c:set var="rightBound" value="${pageNumber + pagesPerSide < totalPageCount ?
(pageNumber + pagesPerSide < 11 ?  11 : pageNumber + pagesPerSide) :
       totalPageCount}"/>
<body>
<nav aria-label="...">
    <ul class="pagination">
        <c:forEach var="i" begin="${leftBound}" end="${rightBound}">
            <c:if test="${i <= totalPageCount}">
                <li class="page-item <c:if test="${i eq pageNumber}">
                                        active
                                     </c:if>">
                    <a class="page-link"
                       href="/productList?pageNumber=${i}&sort=${param.sort}&order=${param.order}&query=${param.query}">
                            ${i}
                    </a>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</nav>
</body>

