package api;

import java.io.IOException;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static utils.SharedConsts.CLIENT_ID_KEY_NAME;

@WebServlet(urlPatterns = "/api/getID")
public class ClientIDAPI extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    final HttpSession session = request.getSession(true);
    String uuid = (String) session.getAttribute(CLIENT_ID_KEY_NAME);

    if (uuid == null) {
      uuid = UUID.randomUUID().toString();
      session.setAttribute(CLIENT_ID_KEY_NAME, uuid);
    }

    final Map<String, String> jsonResponse = new HashMap<>();
    jsonResponse.put(CLIENT_ID_KEY_NAME, uuid);

    final Gson gson = new Gson();
    final String json = gson.toJson(jsonResponse);
    response.getWriter().write(json);
  }
}
