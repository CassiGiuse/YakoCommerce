package cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dao.CartDAO;
import models.Cart;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class CartCache {
  private static final int DELETE_INTERVAL = 20;

  private static final CartDAO cartDAO = new CartDAO();

  private static final Cache<String, Cart> cache = Caffeine.newBuilder()
      .expireAfterAccess(Duration.ofMinutes(10))
      .maximumSize(1000)
      .build();

  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

  static {
    scheduler.scheduleAtFixedRate(() -> {
      cache.asMap().forEach((cartId, cart) -> cartDAO.saveCartToDatabase(cart));
    }, 0, 5, TimeUnit.MINUTES);

    scheduler.scheduleAtFixedRate(() -> {
      removeCartsWithNullUserId();
    }, 0, 10, TimeUnit.MINUTES);
  }

  private static void removeCartsWithNullUserId() {
    LocalDateTime now = LocalDateTime.now();

    cache.asMap().entrySet().removeIf(entry -> {
      Cart cart = entry.getValue();
      boolean isOld = cart.getLastAccessTime().isBefore(now.minusMinutes(DELETE_INTERVAL));
      if (cart.getUserId() == null && isOld) {
        cartDAO.deleteCart(cart.getId());
        return true;
      }
      return false;
    });

    cartDAO.deleteOldAnonymousCarts(DELETE_INTERVAL);
  }

  public static Cart getCart(String cartId) {
    Cart cart = cache.get(cartId, id -> new Cart(id, null));
    cart.updateLastAccessTime();
    return cart;
  }

  public static Cart getExistingCart(String cartId) throws models.CartNotFoundException {
    Cart cart = cache.getIfPresent(cartId);
    if (cart == null) {
      throw new models.CartNotFoundException("Carrello non trovato!");
    }
    cart.updateLastAccessTime();
    return cart;
  }

  public static void updateCart(String cartId, Cart cart) {
    cart.updateLastAccessTime();
    cache.put(cartId, cart);
  }

  public static void removeCart(String cartId) {
    cache.invalidate(cartId);
    cartDAO.deleteCart(cartId);
  }
}