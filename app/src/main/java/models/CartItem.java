package models;

public class CartItem {
  private int articleId;
  private double price;
  private int quantity;

  public CartItem(int articleId, double price, int quantity) {
    this.articleId = articleId;
    this.price = price;
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
