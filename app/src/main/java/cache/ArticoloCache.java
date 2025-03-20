package cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import dao.articolo.ArticoloManager;
import models.Articolo;

public class ArticoloCache {
  private static final Cache<Integer, Articolo> cache = Caffeine.newBuilder()
      .expireAfterWrite(10, TimeUnit.MINUTES)
      .maximumSize(100)
      .build();

  private static final ArticoloManager articoloManager = new ArticoloManager();

  private ArticoloCache() {
  }

  public static Optional<Articolo> getArticolo(final int id) {
    Articolo articolo = cache.getIfPresent(id);

    if (articolo == null) {
      Optional<Articolo> articoloOpt = articoloManager.findById(id);
      if (articoloOpt.isPresent()) {
        articolo = articoloOpt.get();
        cache.put(id, articolo);
      } else {
        return Optional.empty();
      }
    }

    return Optional.of(articolo);
  }

  public static List<Articolo> getAllArticoli() {
    return getAllArticoli(200);
  }

  public static List<Articolo> getAllArticoli(int limit) {
    List<Integer> ids = articoloManager.findAllIDs(limit);
    List<Articolo> articoli = new ArrayList<>();

    for (int id : ids) {
      Optional<Articolo> articoloOpt = getArticolo(id);
      articoloOpt.ifPresent(articoli::add);
    }

    return articoli;
  }

  public static void putArticolo(final Articolo articolo) {
    cache.put(articolo.getId(), articolo);
  }

  public static void invalidateCache(final int id) {
    cache.invalidate(id);
  }

  public static void clearCache() {
    cache.invalidateAll();
  }
}
