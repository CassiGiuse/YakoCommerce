function main() {
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
}

window.onload = main;
