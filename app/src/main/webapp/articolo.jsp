<%@ page language="java" contentType="text/html;charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="models.Articolo" %>

<!DOCTYPE html>
<html lang="it" data-bs-theme="dark">
  <head>
    <title>${initParam.APP_NAME}</title>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />

    <%@ include file="src/templates/sweetAlertCDN.jsp" %>
  </head>
  <body>
    <%@ include file="src/templates/header.jsp" %>

    <main class="p-4 min-vh-100">
      <div class="container">
        <div class="row gy-4">
          <!-- Colonna principale (card orizzontale) -->
          <div class="col-md-8">
            <div class="card mb-4 shadow-sm">
              <div class="row g-0">
                <div
                  class="col-md-4 d-flex align-items-center justify-content-center"
                >
                  <img
                    src="${currArticolo.urlImmagine}"
                    class="img-fluid rounded-start"
                    alt="${currArticolo.titoloDef}"
                    style="object-fit: contain; max-height: 100%; width: 100%"
                  />
                </div>
                <div class="col-md-8">
                  <div class="card-body">
                    <h1 class="display-6">${currArticolo.titoloDef}</h1>
                    <p class="text-muted">
                      Titolo giapponese: ${currArticolo.titoloJap}
                    </p>
                    <c:if test="${not empty currArticolo.titoloEng}">
                      <p class="text-muted">
                        Titolo inglese: ${currArticolo.titoloEng}
                      </p>
                    </c:if>
                    <hr />
                    <h4>Trama</h4>
                    <c:choose>
                      <c:when test="${not empty currArticolo.trama}">
                        <p>${currArticolo.trama}</p>
                      </c:when>
                      <c:otherwise>
                        <p>
                          Spiacenti, nessuna trama disponibile per questo manga!
                        </p>
                      </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty currArticolo.background}">
                      <h4>Background</h4>
                      <p>${currArticolo.background}</p>
                    </c:if>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Colonna laterale (prezzo, consegna, bottone) -->
          <div class="col-md-4">
            <div class="card p-3 sticky-top" style="top: 1rem">
              <p class="h1 text-light fw-bold">
                <fmt:formatNumber
                  value="${currArticolo.prezzo}"
                  type="currency"
                  currencySymbol="€"
                  minFractionDigits="2"
                  maxFractionDigits="2"
                />
                <span class="text-muted fs-5" style="opacity: 0.6"
                  >IVA 22%</span
                >
              </p>
              <p class="text-success">Consegna garantita in 5 giorni</p>

              <div class="d-flex align-items-center mb-3">
                <button
                  class="btn btn-outline-secondary d-flex align-items-center justify-content-center"
                  id="decreaseQty"
                  style="height: 48px; width: 48px"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16"
                    height="16"
                    fill="currentColor"
                    class="bi bi-dash-lg"
                    viewBox="0 0 16 16"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M2 8a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11A.5.5 0 0 1 2 8"
                    />
                  </svg>
                </button>
                <input
                  type="text"
                  class="form-control text-center mx-2 fs-3 border-0"
                  id="quantity"
                  value="1"
                  style="height: 48px; background-color: transparent"
                  readonly
                />
                <button
                  class="btn btn-outline-secondary d-flex align-items-center justify-content-center"
                  id="increaseQty"
                  style="height: 48px; width: 48px"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16"
                    height="16"
                    fill="currentColor"
                    class="bi bi-plus-lg"
                    viewBox="0 0 16 16"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2"
                    />
                  </svg>
                </button>
              </div>

              <button class="btn btn-primary btn-lg w-100 cart-button" data-art-id="${currArticolo.id}">
                Aggiungi al carrello
              </button>
            </div>
          </div>
        </div>
      </div>
    </main>

    <%@ include file="src/templates/footer.jsp" %>

    <script type="module" src="/app/src/js/utils.js"></script>
    <script type="module" src="/app/src/js/WebSocketClient.js"></script>
    <script type="module" src="/app/src/js/articolo.js"></script>

    <script
      src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
      integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
      integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
