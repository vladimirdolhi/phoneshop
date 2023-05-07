<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<tags:master pageTitle="Order">
    <body>
    <div class="container mt-4">
        <a href="${pageContext.request.contextPath}/productList">
            <input type="button" class="btn btn-outline-primary" value="Back to product list">
        </a>

        <c:if test="${not empty errorMsg}">
            <p class="text-danger">${errorMsg}</p>
        </c:if>

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
        </c:if>

        <c:if test="${cart.items.size() == 0}">
            <p class="font-weight-light mt-4">Cart is empty. Add items to cart.</p>
        </c:if>

        <c:if test="${cart.items.size() != 0}">
            <form:form method="post" action="${pageContext.request.contextPath}/order" modelAttribute="orderDataDto">
                <tags:orderFormRow type ="input" name="firstName" label="First name" errors="${errors}"></tags:orderFormRow>
                <tags:orderFormRow type ="input" name="lastName" label="Last name" errors="${errors}"></tags:orderFormRow>
                <tags:orderFormRow type ="input" name="phone" label="Phone" errors="${errors}"></tags:orderFormRow>
                <tags:orderFormRow type ="input" name="address" label="Delivery address" errors="${errors}"></tags:orderFormRow>

                <tags:orderFormRow type ="textArea" name="additionalInfo" label="Additional info" errors="${errors}"></tags:orderFormRow>

                <button type="submit" class="btn btn-outline-primary">Order</button>
            </form:form>
        </c:if>

    </div>
    </body>
</tags:master>
