<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<jsp:useBean id="cart" type="com.es.core.model.cart.Cart" scope="session"/>

<html>
<head>
  <title>${pageTitle}</title>

  <%--Bootstrap--%>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%--<script type="text/javascript" src="${pageContext.servletContext.contextPath}/WEB-INF/js/addToCart.js"></script>--%>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>

  <script><%@ include file="/WEB-INF/js/addToCart.js"%></script>
</head>

  <header>
    <nav class="navbar navbar-light bg-light">
      <a class="navbar-brand logo">
        Phonify
      </a>
      <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/cart">
        <button class="btn btn-outline-info my-2 my-sm-0" type="submit" id="minicart">
          <span id = "totalQuantity">Cart: ${cart.totalQuantity} items</span>
          <span id = "totalCost">${cart.totalCost} $</span>
        </button>
      </form>
    </nav>
  </header>
  <main>
    <div class="container-fluid">
      <jsp:doBody/>
    </div>
  </main>

  <section class="d-flex flex-column min-vh-100">
    <footer class="text-center text-white mt-auto" style="background-color: #0a4275;">
      <div class="container p-4">
        <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
          (c) Expert-Soft 2023 Copyright:
          <a class="text-white" href="https://expert-soft.net/">Expert-soft.net</a>
        </div>
      </div>
    </footer>
  </section>
</html>