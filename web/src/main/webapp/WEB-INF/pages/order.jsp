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
                </tr>
                </thead>

                <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
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
                                ${cartItem.quantity}
                        </td>

                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        Subtotal
                    </td>
                    <td>
                            ${cart.totalCost} $
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        Delivery
                    </td>
                    <td>
                            ${deliveryPrice} $
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        Total
                    </td>
                    <td>
                            ${total} $
                    </td>
                </tr>

            </table>
            <a href="${pageContext.request.contextPath}/productList">
                <input type="button" class="btn btn-outline-primary" value="Place order">
            </a>
        </c:if>

        <c:if test="${cart.items.size() == 0}">
            <p class="font-weight-light mt-4">Cart is empty</p>
        </c:if>

    </div>
    </body>
</tags:master>
