<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<tags:master pageTitle="QuickAdd">
    <body>
    <div class="container mt-4">

        <a href="${pageContext.request.contextPath}/productList">
            <input type="button" class="btn btn-outline-primary" value="Back to product list">
        </a>

        <p class="text-success">${successMsg}</p>

        <table class="table">
            <thead>
            <tr>
                <td>
                    Model
                </td>
                <td>
                    QTY
                </td>
            </tr>
            </thead>

            <form:form method="post"
                       id="quickAdd"
                       modelAttribute="quickAddDto"
                       action="${pageContext.request.contextPath}/quickAdd">
                <c:set var="end">${rowsCount - 1}</c:set>

                <c:forEach begin="0" end="${end}" varStatus="status">
                    <c:set var="ind" value="${status.index}"/>
                    <tr>
                        <td>
                            <form:input path="items[${ind}].model"/>
                            <div class="row">
                                <form:errors cssStyle="color: red" path="items[${ind}].model"/>
                            </div>
                        </td>
                        <td>
                            <form:input path="items[${ind}].quantity"/>
                            <div class="row">
                                <form:errors cssStyle="color: red" path="items[${ind}].quantity"/>
                            </div>
                        </td>
                    </tr>

                </c:forEach>
            </form:form>
        </table>

        <p>
            <button type="submit" form="quickAdd" class="btn btn-success mt-2">Add to cart</button>
        </p>

        </form>
    </div>
    </body>
</tags:master>
