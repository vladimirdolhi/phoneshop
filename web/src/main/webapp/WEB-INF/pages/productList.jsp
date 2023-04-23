<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Phones">
    <header>
        </header>
    <body>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" class="form-inline">
                <input name="query" value="${param.query}" type="text" placeholder="Search" class="form-control">
                <button class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    Found <c:out value="${phones.size()}"/> phones.
    <table class="table">
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Brand
                <tags:sort sort="BRAND" order="ASC"/>
                <tags:sort sort="BRAND" order="DESC"/>
            </td>
            <td>
                Model
                <tags:sort sort="MODEL" order="ASC"/>
                <tags:sort sort="MODEL" order="DESC"/>
            </td>
            <td>Color</td>
            <td>
                Price
                <tags:sort sort="PRICE" order="ASC"/>
                <tags:sort sort="PRICE" order="DESC"/>
            </td>
            <td>Quantity</td>
            <td>Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${phones}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </td>
                <td>${phone.brand}</td>
                <td>${phone.model}</td>
                <td>
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code}
                    </c:forEach>
                </td>
                <td>$ ${phone.price}</td>
                <td>
                    <input id="phoneQuantity-${phone.id}" class="form-check-inline" value="1">
                    <input type="hidden" id="phoneId-${phone.id}" value="${phone.id}">
                    <div class="text-success" id="success-message-${phone.id}"></div>
                    <div class="text-danger" id="error-message-${phone.id}"></div>
                </td>
                <td>
                    <input type="button" onclick="doAjaxAddToCart(${phone.id})" value="Add to cart">
                </td>
            </tr>
        </c:forEach>
    </table>
    <tags:pagination pageNumber="${pageNumber}" totalPageCount="${count}"></tags:pagination>
   <body/>
</tags:master>
