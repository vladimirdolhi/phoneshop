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
                <c:forEach var="cartItem" items="${cartItems}" varStatus="status">
                    <c:set var="ind" value="${status.index}"/>
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.phone.imageUrl}">
                        </td>
                        <td>${cartItem.phone.brand}</td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${cartItem.phone.id}">
                                    ${cartItem.phone.model}
                        </td>
                        <td>
                            <c:forEach var="color" items="${cartItem.phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                        <td>$ ${cartItem.phone.price}</td>
                        <td>
                            <form:hidden path="items[${ind}].phoneId"/>
                            <form:input path="items[${ind}].quantity"/>
                            <c:set var="id">items[${ind}].phoneId</c:set>
                            <p style="color: red">${errors[id]}</p>
                        </td>
                        <td>
                            <input form="deleteCartItem"
                                   formaction="${pageContext.servletContext.contextPath}/cart/delete/${cartItem.phone.id}"
                                   type="submit" value="Delete">
                        </td>
                    </tr>
                </c:forEach>
            </form:form>
        </table>
        <p>
            <button type="submit" form="updateForm" class="btn btn-success mt-2">Update</button>
        </p>
        <form id="deleteCartItem" method="post">

        </form>
    </div>
    </body>
</tags:master>
