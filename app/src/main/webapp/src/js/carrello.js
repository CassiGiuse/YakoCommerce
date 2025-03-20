function main() {
  fetch(`api/cart`, {
    method: "POST",
    body: JSON.stringify({
      actionType: "GET",
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("Articolo aggiunto al carrello:", data);
    })
    .catch((error) => {
      console.error("Errore nell'aggiunta dell'articolo al carrello:", error);
    });
}

window.onload = main;
