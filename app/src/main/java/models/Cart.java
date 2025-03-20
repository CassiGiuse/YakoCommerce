package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private String id;
    private String userId;
    private LocalDateTime createdAt;
    private Map<Integer, CartItem> items;

    public Cart(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.items = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public List<Map<String, Object>> getAllItems() {
        List<Map<String, Object>> itemList = new ArrayList<>();

        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            CartItem item = entry.getValue();
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("id", item.getArticleId());
            itemDetails.put("price", item.getPrice());
            itemDetails.put("quantity", item.getQuantity());
            itemDetails.put("totalPrice", item.getPrice() * item.getQuantity());
            itemList.add(itemDetails);
        }

        return itemList;
    }

    public void addItem(int articleId, double price, int quantity) {
        items.putIfAbsent(articleId, new CartItem(articleId, price, quantity));
        items.get(articleId).increaseQuantity(quantity);
    }

    public void removeItem(int articleId) {
        if (items.containsKey(articleId)) {
            items.get(articleId).decreaseQuantity();
            if (items.get(articleId).getQuantity() <= 0) {
                items.remove(articleId);
            }
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", items=" + items +
                '}';
    }
}
