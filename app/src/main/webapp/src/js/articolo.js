import {
  addCartButtonListeners,
  addArtToCart,
  updateBadge,
  goToCartListener,
  buttonQuantityListeners,
} from "./utils.js";

function main() {
  buttonQuantityListeners();

  document.getElementById("navbar").classList.remove("sticky-top");

  updateBadge();

  addCartButtonListeners((e) => {
    const id = e.target.getAttribute("data-art-id");
    const qtInput = document.getElementById("quantity");
    const quantity = parseInt(qtInput.value);
    addArtToCart(id, quantity);
  });
  goToCartListener();
}

window.onload = main;
