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
    private LocalDateTime lastAccessTime;
    private Map<Integer, CartItem> items;

    public Cart(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
        this.items = new HashMap<>();
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
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
            itemDetails.put("quantity", item.getRequestedQuantity());
            itemDetails.put("price", item.getPrice() * item.getRequestedQuantity());
            itemList.add(itemDetails);
        }
        updateLastAccessTime();

        return itemList;
    }

    public int getTotalItemsCount() {
        updateLastAccessTime();
        return items.values().stream()
                .mapToInt(CartItem::getRequestedQuantity)
                .sum();
    }

    public void addItem(int articleId, int quantity) throws IllegalArgumentException {
        updateLastAccessTime();
        items.putIfAbsent(articleId, new CartItem(articleId));
        items.get(articleId).increaseQuantity(quantity);
    }

    public void removeItem(int articleId) {
        updateLastAccessTime();
        items.remove(articleId);
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

    public LocalDateTime getLastAccessTime() {
        return this.lastAccessTime;
    }
}
