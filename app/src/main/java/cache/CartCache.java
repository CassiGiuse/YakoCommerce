package cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dao.CartDAO;
import models.Cart;

import java.time.Duration;
import java.util.concurrent.*;

public class CartCache {
  private static final CartDAO cartDAO = new CartDAO();

  private static final Cache<String, Cart> cache = Caffeine.newBuilder()
      .expireAfterWrite(Duration.ofMinutes(10))
      .maximumSize(1000)
      .build();

  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  static {
    scheduler.scheduleAtFixedRate(() -> {
      cache.asMap().forEach((cartId, cart) -> cartDAO.saveCartToDatabase(cart));
    }, 0, 5, TimeUnit.MINUTES);
  }

  public static Cart getCart(String cartId) {
    return cache.get(cartId, id -> new Cart(id, null));
  }

  public static Cart getExistingCart(String cartId) throws models.CartNotFoundException {
    Cart cart = cache.getIfPresent(cartId);
    if (cart == null) {
      throw new models.CartNotFoundException("Carrello non trovato!");
    }
    return cart;
  }

  public static void updateCart(String cartId, Cart cart) {
    cache.put(cartId, cart);
  }

  public static void removeCart(String cartId) {
    cache.invalidate(cartId);
    cartDAO.deleteCart(cartId);
  }
}
