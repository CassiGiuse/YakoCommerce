function commonHandler(data) {
  Swal.fire({
    icon: data.status ? "success" : "error",
    text: data.msg,
  });
  updateBadge();
}

export function addArtToCart(id, qt = 1, callback = commonHandler) {
  const data = {
    actionType: "ADD",
    itemID: id,
    quantity: qt,
  };

  fetch(`/app/api/cart`, { method: "POST", body: JSON.stringify(data) })
    .then((response) => response.json())
    .then((data) => {
      callback(data);
    });
}

export function updateBadge() {
  const badge = document.getElementById("cart-items-counter");

  fetch("/app/api/cart", {
    method: "POST",
    body: JSON.stringify({
      actionType: "GET_ITEMS_COUNT",
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.status) badge.innerText = data.totalItems || "";
    });
}

export function addCartButtonListeners(callback) {
  document.body.addEventListener("click", (e) => {
    if (e.target.classList.contains("add-cart-button")) {
      callback(e);
    }
  });
}

export function goToCartListener() {
  document.getElementById("cart-button").addEventListener("click", (_) => {
    window.location.href = "/app/carrello";
  });
}

export function buttonQuantityListeners() {
  document.addEventListener("click", function (event) {
    const target = event.target;

    if (
      target.classList.contains("increase-qty") ||
      target.classList.contains("decrease-qty")
    ) {
      const button = target;
      const id = button.getAttribute("data-art-id");
      const input = document.querySelector(`input[data-art-id="${id}"]`);

      if (input) {
        let currentValue = parseInt(input.value);

        if (button.classList.contains("increase-qty")) {
          input.value = currentValue + 1;
        } else if (
          button.classList.contains("decrease-qty") &&
          currentValue > 1
        ) {
          input.value = currentValue - 1;
        }
      }
    }
  });
}
