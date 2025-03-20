package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cache.CartCache;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;
import models.CartNotFoundException;
import utils.CartActionType;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;;

@WebServlet("/api/cart")
public class CartAPI extends HttpServlet {
    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configResponse(response);

        final HttpSession session = request.getSession();
        final String cartId = session.getId();

        JsonObject jsonResponse = new JsonObject();

        try (BufferedReader reader = request.getReader()) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (!json.has("actionType")) {
                throw new IllegalArgumentException("Campo obbligatorio mancante: actionType");
            }

            final Cart cart = CartCache.getCart(cartId);
            CartActionType actionType = CartActionType.fromString(json.get("actionType").getAsString());

            if (actionType == CartActionType.GET_ITEMS_COUNT) {
                int totalItems = cart.getTotalItemsCount();
                jsonResponse.addProperty("totalItems", totalItems);
                handleResponse(response, jsonResponse, true, SC_OK, "Operazione avvennuta con successo!");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            if (!json.has("itemID")) {
                throw new IllegalArgumentException("Campo obbligatorio mancante: itemID");
            }

            final int itemID = json.get("itemID").getAsInt();

            switch (actionType) {
                case ADD -> {
                    if (!json.has("quantity"))
                        throw new IllegalArgumentException("Campo 'quantity' mancante.");

                    final int quantity = json.get("quantity").getAsInt();
                    cart.addItem(itemID, quantity);
                }
                case REMOVE -> cart.removeItem(itemID);
                case CLEAR -> CartCache.removeCart(cartId);
                default -> throw new IllegalArgumentException("Azione non supportata: " + actionType);
            }

            CartCache.updateCart(cartId, cart);

            Cart oldCart = (Cart) session.getAttribute("currCart");
            if (oldCart == null || !oldCart.equals(cart)) {
                session.setAttribute("currCart", cart);
            }

            handleResponse(response, jsonResponse, true, SC_OK, "Operazione completata con successo.");
        } catch (IllegalArgumentException e) {
            handleResponse(response, jsonResponse, false, SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            handleResponse(response, jsonResponse, false, SC_INTERNAL_SERVER_ERROR,
                    "Errore interno del server.");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configResponse(resp);

        final HttpSession session = req.getSession();
        final String cartId = session.getId();
        JsonObject jsonResponse = new JsonObject();

        try {
            final Cart cart = CartCache.getCart(cartId);

            List<Map<String, Object>> items = cart.getAllItems();
            jsonResponse.add("items", gson.toJsonTree(items));

        } catch (CartNotFoundException e) {
            handleResponse(resp, jsonResponse, false, SC_BAD_REQUEST,
                    e.getMessage());
        }

        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    private void configResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void handleResponse(HttpServletResponse response, JsonObject jsonResponse, boolean status, int statusCode,
            String errorMsg) {
        jsonResponse.addProperty("status", status);
        jsonResponse.addProperty("msg", errorMsg);
        response.setStatus(statusCode);
    }
}