import { WebSocketClient } from "./WebSocketClient.js";
import { addCartListener } from "./utils.js";

function main() {
  const ws = new WebSocketClient();

  document.getElementById("increaseQty").addEventListener("click", function () {
    let qty = document.getElementById("quantity");
    qty.value = parseInt(qty.value) + 1;
  });

  document.getElementById("decreaseQty").addEventListener("click", function () {
    let qty = document.getElementById("quantity");
    if (parseInt(qty.value) > 1) {
      qty.value = parseInt(qty.value) - 1;
    }
  });

  document.getElementById("navbar").classList.remove("sticky-top");

  addCartListener(ws);
}

window.onload = main;
