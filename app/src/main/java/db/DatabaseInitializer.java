package db;

import java.util.Random;

import cache.ArticoloCache;
import dao.articolo.ArticoloManager;
import models.Articolo;
import net.sandrohc.jikan.Jikan;
import net.sandrohc.jikan.exception.JikanException;
import net.sandrohc.jikan.model.manga.*;
import static utils.SharedConsts.MANGAS_TO_FETCH;

public class DatabaseInitializer {

  private DatabaseInitializer() {
  }

  public static void init() throws RuntimeException {
    Jikan jikan = new Jikan();
    ArticoloManager articoloManager = new ArticoloManager();

    for (int i = 0; i < MANGAS_TO_FETCH; i++) {
      try {
        int mangaId = new Random().nextInt(180000) + 1;

        Manga manga = jikan.query().manga().get(mangaId).execute().block();

        if (manga == null) {
          i--;
          continue;
        }

        final Articolo art = new Articolo(
            mangaId,
            manga.titleEnglish,
            manga.titleJapanese,
            manga.title,
            manga.images.jpg.imageUrl,
            manga.synopsis,
            manga.background,
            generateRandomPrice(5.0, 12.0),
            generateRandomQuantity(20, 200));

        articoloManager.save(art);
        ArticoloCache.putArticolo(art);

      } catch (JikanException e) {
        throw new RuntimeException("Errore durante l'elaborazione del manga: " + e.getMessage());
      }
    }
  }

  private static double generateRandomPrice(double min, double max) {
    Random random = new Random();
    return min + (max - min) * random.nextDouble();
  }

  private static int generateRandomQuantity(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
  }
}