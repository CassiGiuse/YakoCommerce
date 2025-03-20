import {
  addCartButtonListeners,
  addArtToCart,
  updateBadge,
  goToCartListener,
} from "./utils.js";

const cardsContainer = document.getElementById("cardsContainer");

function buildArticolo(articolo) {
  const { id, titoloDef, urlImmagine, quantita, prezzo } = articolo;

  const p = prezzo.toFixed(2);

  const html = `
    <div class="col-lg-4 d-flex justify-content-center align-items-stretch mb-4">
      <div class="card shadow-sm" style="width: 18rem;">
        <img src="${urlImmagine}" class="card-img-top" alt="${titoloDef}">
        <div class="card-body d-flex flex-column">
          <h5 class="card-title">${titoloDef}</h5>
          <div class="mt-auto">
            <p class="card-text">🛒 Quantità: <strong>${quantita}</strong></p>
            <p class="card-text">💰 Prezzo: <strong>€${p}</strong></p>
            <button class="btn btn-success w-100 mb-2 add-cart-button" data-art-id="${id}">Aggiugni al carrello!</button>
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
    });
}

function main() {
  caricaArticoli();
  updateBadge();
  addCartButtonListeners((e) => {
    const id = e.target.getAttribute("data-art-id");
    addArtToCart(id);
  });
  goToCartListener();
}

window.onload = main;
