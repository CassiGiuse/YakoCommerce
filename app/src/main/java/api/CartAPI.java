package api;

import java.io.BufferedReader;
import java.io.IOException;

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
import utils.CartActionType;

@WebServlet("/api/cart")
public class CartAPI extends HttpServlet {
    private static final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String cartId = session.getId();

        BufferedReader reader = request.getReader();
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        JsonObject jsonResponse = new JsonObject();

        try {
            CartActionType actionType = CartActionType.fromString(json.get("actionType").getAsString());
            int elementID = json.get("elementID").getAsInt();
            double price = json.get("price").getAsDouble();
            int qt = json.get("qt").getAsInt();

            Cart cart = CartCache.getCart(cartId);

            switch (actionType) {
                case ADD -> cart.addItem(elementID, price, qt);
                case REMOVE -> cart.removeItem(elementID);
                case CLEAR -> CartCache.removeCart(cartId);
                case GET_ITEMS -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }
                default -> throw new IllegalArgumentException("Azione non supportata: " + actionType);
            }

            CartCache.updateCart(cartId, cart);

            jsonResponse.addProperty("status", "success");
            jsonResponse.addProperty("msg", "Operazione completata con successo.");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (IllegalArgumentException e) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("msg", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }
}
