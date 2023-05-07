<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<tags:master pageTitle="Order overview">
    <body>
    <div class="container mt-4">

        <p class="text-success">Thank you for your order!)</p>

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

            <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
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
                            ${orderItem.quantity}
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
                        ${order.subtotal} $
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
                        ${order.deliveryPrice} $
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
                        ${order.totalPrice} $
                </td>
            </tr>

        </table>

        <p class="font-weight-bold">First name: ${order.firstName}</p>
        <p class="font-weight-bold">Last name: ${order.lastName}</p>
        <p class="font-weight-bold">Address: ${order.deliveryAddress}</p>
        <p class="font-weight-bold">Phone: ${order.contactPhoneNo}</p>
        <p class="font-weight-bold">Info: ${order.additionalInfo}</p>

        <a href="${pageContext.request.contextPath}/productList">
            <input type="button" class="btn btn-outline-primary" value="Back shopping">
        </a>
    </div>
    </body>
</tags:master>
