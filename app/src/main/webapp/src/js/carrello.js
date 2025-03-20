import { updateBadge, buttonQuantityListeners } from "./utils.js";

function main() {
  buttonQuantityListeners();
  updateBadge();

  const removeBtns = document.getElementsByClassName("remove-item");

  Array.from(removeBtns).forEach((btn) => {
    btn.addEventListener("click", (e) => {
      const id = e.target.getAttribute("data-art-id");

      const data = {
        actionType: "REM",
        itemID: id,
      };

      fetch(`/app/api/cart`, { method: "POST", body: JSON.stringify(data) })
        .then((response) => response.json())
        .then((data) => {
          if (data.status) {
            document.querySelector(`div[data-art-id="${id}"]`).remove();
            window.location.reload();
          }
        });
    });
  });
}

window.onload = main;
