package api;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import cache.ArticoloCache;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Articolo;

import static utils.SharedConsts.MANGAS_TO_FETCH;

@WebServlet(urlPatterns = "/api/articoli")
public class ArticoliAPI extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    List<Articolo> articoli = ArticoloCache.getAllArticoli(MANGAS_TO_FETCH);

    Gson gson = new Gson();
    String json = gson.toJson(articoli);

    response.getWriter().write(json);
  }
}
