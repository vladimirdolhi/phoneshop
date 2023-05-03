<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="phone" type="com.es.core.model.phone.Phone" scope="request"/>

<tags:master pageTitle="Phone details">
    <body>
    <div class="container mt-4">
        <a href="${pageContext.request.contextPath}/productList">
            <input type="button" class="btn btn-outline-primary" value="Back to product list">
        </a>
        <div class="row">
            <div class="col">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                <div>${phone.description}</div>
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <h4 class="card-title">Price: ${phone.price}$</h4>
                        <div class="input-group mt-3">
                            <input id="phoneQuantity-${phone.id}" type="text" class="form-control"
                                   aria-label="Recipient's username" aria-describedby="basic-addon2" value="1"> <input
                                type="hidden" id="phoneId-${phone.id}" value="${phone.id}">
                            <div class="input-group-append">
                                <button onclick="doAjaxAddToCart(${phone.id})" class="btn btn-outline-success"
                                        type="button">Add
                                </button>
                            </div>
                        </div>
                        <div class="text-success" id="success-message-${phone.id}"></div>
                        <div class="text-danger" id="error-message-${phone.id}"></div>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <table class="table table-bordered">
                    <thead class="text">
                    <h5>Display</h5>
                    </thead>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            Size
                        </td>
                        <td>
                                ${phone.displaySizeInches}''
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Resolution
                        </td>
                        <td>
                                ${phone.displayResolution}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Technology
                        </td>
                        <td>
                                ${phone.displayTechnology}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Pixel density
                        </td>
                        <td>
                                ${phone.pixelDensity}
                        </td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-bordered">
                    <thead class="text">
                    <h5>Dimensions & weight</h5>
                    </thead>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            Length
                        </td>
                        <td>
                                ${phone.lengthMm}mm
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Width
                        </td>
                        <td>
                                ${phone.widthMm}mm
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Colors
                        </td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Weight
                        </td>
                        <td>
                                ${phone.weightGr}
                        </td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-bordered">
                    <thead class="text">
                    <h5>Camera</h5>
                    </thead>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            Front
                        </td>
                        <td>
                                ${phone.frontCameraMegapixels} megapixels
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Back
                        </td>
                        <td>
                                ${phone.backCameraMegapixels} megapixels
                        </td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-bordered">
                    <thead class="text">
                    <h5>Battery</h5>
                    </thead>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            Talk time
                        </td>
                        <td>
                                ${phone.talkTimeHours} hours
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Stand by time
                        </td>
                        <td>
                                ${phone.standByTimeHours} hours
                        </td>
                    </tr>
                    <td>
                        Battery capacity
                    </td>
                    <td>
                            ${phone.batteryCapacityMah}mah
                    </td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-bordered">
                    <thead class="text">
                    <h5>Other</h5>
                    </thead>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            Device type
                        </td>
                        <td>
                                ${phone.deviceType}
                        </td>
                    </tr>
                    <td>
                        Bluetooth
                    </td>
                    <td>
                            ${phone.bluetooth}
                    </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </body>
</tags:master>
