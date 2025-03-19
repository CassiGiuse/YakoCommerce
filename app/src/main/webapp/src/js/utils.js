function updateCart(ws, elID) {
  ws.send({
    action: "updateCart",
    artID: elID,
  });
}

export function addCartListener(ws) {
  const cartButtons = document.getElementsByClassName("cart-button");

  Array.from(cartButtons).forEach((button) => {
    button.addEventListener("click", (e) => {
      const artID = e.target.getAttribute("data-art-id");
      updateCart(ws, artID);
    });
  });
}
