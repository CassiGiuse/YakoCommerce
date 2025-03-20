<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="models.CartItem" %>

<!DOCTYPE html>
<html lang="it" data-bs-theme="dark">
<head>
    <title>${initParam.APP_NAME} - Carrello</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />

    <%@ include file="src/templates/sweetAlertCDN.jsp" %>
</head>
<body>
    <%@ include file="src/templates/header.jsp" %>

    <main class="container py-4 min-vh-100">
        <h1 class="mb-4">🛒 Il tuo carrello</h1>
        <c:choose>
            <c:when test="${empty currCart.items}">
                <div class="alert alert-warning text-center" role="alert">
                    Il tuo carrello è vuoto! 🛍️ <br>
                    <a href="index.jsp" class="btn btn-primary mt-3">🏠 Torna alla Homepage</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-2 g-4">
                    <c:forEach var="item" items="${currCart.items}">
                        <div class="col" data-art-id="${item.value.articolo.id}">
                            <div class="card mb-3 shadow-sm" style="max-width: 540px;">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="${item.value.articolo.urlImmagine}" class="img-fluid rounded-start" alt="${item.value.articolo.titoloDef}">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">${item.value.articolo.titoloDef}</h5>
                                            <p class="card-text">💰 Prezzo: <strong>€<fmt:formatNumber value="${item.value.articolo.prezzo}" type="currency"/></strong></p>
                                            <p class="card-text">🛒 Quantità: <strong>${item.value.requestedQuantity}</strong></p>

                                            <button
                                                class="btn btn-danger btn-sm remove-item"
                                                data-art-id="${item.value.articolo.id}"
                                            >
                                                Rimuovi
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <%@ include file="src/templates/footer.jsp" %>

    <script type="module" src="/app/src/js/utils.js"></script>
    <script type="module" src="/app/src/js/carrello.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
</body>
</html>
