package com;

import java.io.IOException;

import cache.CartCache;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;

@WebServlet(urlPatterns = "/carrello")
public class CartServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    final HttpSession session = request.getSession();
    final String cartId = session.getId();

    Cart cart = (Cart) session.getAttribute("currCart");

    if (cart == null) {
      cart = CartCache.getCart(cartId);
      session.setAttribute("currCart", cart);
    }
    request.setAttribute("currCart", cart);

    RequestDispatcher dispatcher = request.getRequestDispatcher("/carrello.jsp");
    dispatcher.forward(request, response);
  }
}