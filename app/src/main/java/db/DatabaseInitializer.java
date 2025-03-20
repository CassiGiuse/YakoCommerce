package db;

import java.util.List;
import java.util.Random;

import cache.ArticoloCache;
import dao.articolo.ArticoloManager;
import models.Articolo;
import net.sandrohc.jikan.Jikan;
import net.sandrohc.jikan.exception.JikanException;
import net.sandrohc.jikan.model.manga.Manga;

import static utils.SharedConsts.MANGAS_TO_FETCH;

public class DatabaseInitializer {

  // private static final List<Genre> BANNED_GENRES_LIST = List.of(Genre.HENTAI, Genre.ADULT_CAST, Genre.ECCHI);

  private DatabaseInitializer() {
  }

  public static void init() {
    Jikan jikan = new Jikan();
    ArticoloManager articoloManager = new ArticoloManager();

    try {
      List<Manga> mangaList = jikan.query()
          .manga()
          .top()
          .limit(MANGAS_TO_FETCH)
          .execute()
          .collectList()
          .block();

      if (mangaList == null || mangaList.isEmpty()) {
        System.out.println("[DatabaseInitializer] Nessun manga trovato.");
        return;
      }

      mangaList.stream()
          .map(manga -> new Articolo(
              manga.malId,
              manga.titleEnglish,
              manga.titleJapanese,
              manga.title,
              manga.images.jpg.imageUrl,
              manga.synopsis,
              manga.background,
              generateRandomPrice(5.0, 12.0),
              generateRandomQuantity(20, 200)))
          .forEach(articolo -> {
            articoloManager.save(articolo);
            ArticoloCache.putArticolo(articolo);
          });

      System.out.println("[DatabaseInitializer] Caricamento completato con successo.");

    } catch (JikanException e) {
      System.err.println("[DatabaseInitializer] Errore durante il caricamento dei manga: " + e.getMessage());
    }
  }

  private static double generateRandomPrice(double min, double max) {
    return min + (max - min) * new Random().nextDouble();
  }

  private static int generateRandomQuantity(int min, int max) {
    return new Random().nextInt(max - min + 1) + min;
  }
}
