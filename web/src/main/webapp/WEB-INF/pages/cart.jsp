<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<tags:master pageTitle="Cart">
    <body>
    <div class="container mt-4">
        <a href="${pageContext.request.contextPath}/productList">
            <input type="button" class="btn btn-outline-primary" value="Back to product list">
        </a>
        <c:if test="${cart.items.size() != 0}">
            <table class="table">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>
                        Brand
                    </td>
                    <td>
                        Model
                    </td>
                    <td>Color</td>
                    <td>
                        Price
                    </td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                </thead>

                <form:form method="post"
                           id="updateForm"
                           modelAttribute="cartDto"
                           action="${pageContext.request.contextPath}/cart/update">
                    <c:forEach var="orderItem" items="${cart.items}" varStatus="status">
                        <c:set var="ind" value="${status.index}"/>
                        <tr>
                            <td>
                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}">
                            </td>
                            <td>${orderItem.phone.brand}</td>
                            <td>
                                <a href="${pageContext.servletContext.contextPath}/products/${orderItem.phone.id}">
                                        ${orderItem.phone.model}
                            </td>
                            <td>
                                <c:forEach var="color" items="${orderItem.phone.colors}">
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td>$ ${orderItem.phone.price}</td>
                            <td>
                                <form:hidden path="items[${ind}].phoneId"/>
                                <form:input path="items[${ind}].quantity"/>
                                <c:set var="id">items[${ind}].phoneId</c:set>
                                <p style="color: red">${errors[id]}</p>
                            </td>
                            <td>
                                <input form="deleteCartItem"
                                       formaction="${pageContext.servletContext.contextPath}/cart/delete/${orderItem.phone.id}"
                                       type="submit" value="Delete">
                            </td>
                        </tr>
                    </c:forEach>
                </form:form>
            </table>
            <p>
                <button type="submit" form="updateForm" class="btn btn-success mt-2">Update</button>
            </p>

            <a href="${pageContext.request.contextPath}/order">
                <input type="button" class="btn btn-outline-primary" value="Place order">
            </a>
        </c:if>

        <c:if test="${cart.items.size() == 0}">
            <p class="font-weight-light mt-4">Cart is empty</p>
        </c:if>

        <form id="deleteCartItem" method="post">

        </form>
    </div>
    </body>
</tags:master>
