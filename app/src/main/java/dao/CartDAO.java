package dao;

import models.Cart;
import models.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import db.DatabaseManager;

public class CartDAO {

    public boolean saveCartToDatabase(Cart cart) {
        String insertCartSQL = "INSERT INTO carrello (id, id_utente, data_creazione) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE id_utente = ?, data_creazione = ?";
        String insertItemsSQL = "INSERT INTO elementi_carrello (id_carrello, id_articolo, quantita, prezzo_unitario) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantita = ?, prezzo_unitario = ?";

        try (Connection conn = DatabaseManager.getDataSource().getConnection();
                PreparedStatement cartStmt = conn.prepareStatement(insertCartSQL);
                PreparedStatement itemsStmt = conn.prepareStatement(insertItemsSQL)) {

            cartStmt.setString(1, cart.getId());
            cartStmt.setString(2, cart.getUserId());
            cartStmt.setTimestamp(3, java.sql.Timestamp.valueOf(cart.getCreatedAt()));
            cartStmt.setString(4, cart.getUserId());
            cartStmt.setTimestamp(5, java.sql.Timestamp.valueOf(cart.getCreatedAt()));
            cartStmt.executeUpdate();
            
            for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                CartItem item = entry.getValue();
                itemsStmt.setString(1, cart.getId());
                itemsStmt.setInt(2, item.getArticleId());
                itemsStmt.setInt(3, item.getQuantity());
                itemsStmt.setDouble(4, item.getPrice());
                itemsStmt.setInt(5, item.getQuantity());
                itemsStmt.setDouble(6, item.getPrice());
                itemsStmt.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCart(String cartId) {
        String sql = "DELETE FROM carrello WHERE id = ?";

        try (Connection conn = DatabaseManager.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
