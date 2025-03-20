let cardsContainer;

function buildArticolo(articolo) {
  const { id, titoloDef, urlImmagine, quantita, prezzo } = articolo;

  const html = `
    <div class="col-lg-4 d-flex justify-content-center align-items-stretch mb-4">
      <div class="card shadow-sm" style="width: 18rem;">
        <img src="${urlImmagine}" class="card-img-top" alt="${titoloDef}">
        <div class="card-body d-flex flex-column">
          <h5 class="card-title">${titoloDef}</h5>
          <div class="mt-auto">
            <p class="card-text">🛒 Quantità: <strong>${quantita}</strong></p>
            <p class="card-text">💰 Prezzo: <strong>€${prezzo.toFixed(
              2
            )}</strong></p>
            <button class="btn btn-success w-100 mb-2 cart-button" data-art-id="${id}">Aggiugni al carrello!</button>
            <a href="articolo/${id}" class="btn btn-outline-primary w-100">Visualizza dettagli</a>
          </div>
        </div>
      </div>
    </div>
  `;

  cardsContainer.insertAdjacentHTML("beforeend", html);
}

function caricaArticoli() {
  fetch("api/articoli")
    .then((response) => response.json())
    .then((data) => {
      data.forEach((articolo) => {
        buildArticolo(articolo);
      });
      addCartListener(ws);
    })
    .catch((error) => {
      console.error("Errore nel caricamento degli articoli:", error);
    });
}

function main() {

  cardsContainer = document.getElementById("cardsContainer");

  caricaArticoli();
}

window.onload = main;
