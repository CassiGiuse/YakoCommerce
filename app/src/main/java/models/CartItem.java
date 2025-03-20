package models;

import java.util.Optional;

import cache.ArticoloCache;

public class CartItem {
  private int articleId;
  private double price;
  private int quantity;
  private Articolo art;

  public CartItem(int articleId, int quantity) throws IllegalArgumentException {

    final Optional<Articolo> artOpt = ArticoloCache.getArticolo(articleId);

    if (!artOpt.isPresent())
      throw new IllegalArgumentException("Impossibile aggiugere item selezionato");

    final Articolo art = artOpt.get();
    this.art = art;
    this.articleId = articleId;
    this.price = this.art.getPrezzo();
    this.quantity = quantity;
  }

  public int getArticleId() {
    return articleId;
  }

  public double getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void increaseQuantity(int amount) {
    this.quantity += amount;
  }

  public void decreaseQuantity() {
    if (this.quantity > 0)
      this.quantity--;
  }

  @Override
  public String toString() {
    return "CartItem{" +
        "articleId=" + articleId +
        ", price=" + price +
        ", quantity=" + quantity +
        '}';
  }
}
