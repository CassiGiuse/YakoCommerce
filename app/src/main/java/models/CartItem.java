package models;

import java.util.Optional;
import cache.ArticoloCache;

public class CartItem {
  private Articolo articolo;
  private int requestedQuantity;

  public CartItem(int articleId) {
    this(articleId, 0);
  }

  public CartItem(int articleId, int quantity) throws IllegalArgumentException {
    Optional<Articolo> artOpt = ArticoloCache.getArticolo(articleId);
    if (!artOpt.isPresent())
      throw new IllegalArgumentException("Impossibile aggiungere item selezionato");

    this.articolo = artOpt.get();
    this.requestedQuantity = quantity;
  }

  public Articolo getArticolo() {
    return articolo;
  }

  public int getArticleId() {
    return articolo.getId();
  }

  public double getPrice() {
    return articolo.getPrezzo();
  }

  public int getRequestedQuantity() {
    return requestedQuantity;
  }

  public void increaseQuantity(int amount) {
    this.requestedQuantity += amount;
  }

  public void decreaseQuantity() {
    if (this.requestedQuantity > 0)
      this.requestedQuantity--;
  }

  @Override
  public String toString() {
    return "CartItem{" +
        "articleId=" + articolo.getId() +
        ", title=" + articolo.getTitoloDef() +
        ", price=" + articolo.getPrezzo() +
        ", quantity=" + requestedQuantity +
        '}';
  }
}
