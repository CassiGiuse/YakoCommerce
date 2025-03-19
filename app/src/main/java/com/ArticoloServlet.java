package com;

import java.io.IOException;
import java.util.Optional;

import cache.ArticoloCache;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Articolo;

@WebServlet(urlPatterns = "/articolo/*")
public class ArticoloServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    String idStr = pathInfo.substring(1);
    if (!idStr.matches("\\d+")) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    final int artID = Integer.parseInt(idStr);
    final Optional<Articolo> artOptional = ArticoloCache.getArticolo(artID);

    if (artOptional.isEmpty()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    final Articolo art = artOptional.get();

    request.setAttribute("currArticolo", art);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/articolo.jsp");
    dispatcher.forward(request, response);
  }
}
