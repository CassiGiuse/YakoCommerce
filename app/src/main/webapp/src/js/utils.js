export function addArtToCart(id, qt = 1) {
  const data = {
    actionType: "ADD",
    itemID: id,
    quantity: qt,
  };

  fetch(`api/cart`, { method: "POST", body: JSON.stringify(data) })
    .then((response) => response.json())
    .then((data) => {
      Swall.fire({
        icon: data.status ? "success" : "error",
        text: data.msg,
      });
    });
}

export function addCartButtonListeners(callback) {
  Array.from(document.getElementsByClassName("add-cart-button")).forEach(
    (button) => {
      button.addEventListener("click", (event) => {
        callback(event);
      });
    }
  );
}
