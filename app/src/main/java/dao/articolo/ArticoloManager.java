package dao.articolo;

import models.Articolo;
import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticoloManager implements ArticoloDAO {
    @Override
    public void save(Articolo articolo) {
        String sql = "INSERT INTO Articolo (ID, Titotlo_Eng, Titotlo_Jap, Titotlo_Def, URL_Immagine, Trama, Background, Prezzo, Quantita) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, articolo.getId());
            statement.setString(2, articolo.getTitoloEng());
            statement.setString(3, articolo.getTitoloJap());
            statement.setString(4, articolo.getTitoloDef());
            statement.setString(5, articolo.getUrlImmagine());
            statement.setString(6, articolo.getTrama());
            statement.setString(7, articolo.getBackground());
            statement.setDouble(8, articolo.getPrezzo());
            statement.setInt(9, articolo.getQuantita());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il salvataggio dell'articolo", e);
        }
    }

    @Override
    public Optional<Articolo> findById(int id) {
        String sql = "SELECT * FROM Articolo WHERE ID = ?";
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToArticolo(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero dell'articolo", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Articolo> findAll() {
        List<Articolo> articoli = new ArrayList<>();
        String sql = "SELECT * FROM Articolo";

        try (Connection connection = DatabaseManager.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                articoli.add(mapResultSetToArticolo(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero degli articoli", e);
        }
        return articoli;
    }

    @Override
    public void update(Articolo articolo) {
        String sql = "UPDATE Articolo SET Titotlo_Eng = ?, Titotlo_Jap = ?, Titotlo_Def = ?, URL_Immagine = ?, Trama = ?, Background = ?, Prezzo = ?, Quantita = ? "
                +
                "WHERE ID = ?";

        try (Connection connection = DatabaseManager.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, articolo.getTitoloEng());
            statement.setString(2, articolo.getTitoloJap());
            statement.setString(3, articolo.getTitoloDef());
            statement.setString(4, articolo.getUrlImmagine());
            statement.setString(5, articolo.getTrama());
            statement.setString(6, articolo.getBackground());
            statement.setDouble(7, articolo.getPrezzo());
            statement.setInt(8, articolo.getQuantita());
            statement.setInt(9, articolo.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'aggiornamento dell'articolo", e);
        }
    }

    private Articolo mapResultSetToArticolo(ResultSet resultSet) throws SQLException {
        return new Articolo(
                resultSet.getInt("ID"),
                resultSet.getString("Titotlo_Eng"),
                resultSet.getString("Titotlo_Jap"),
                resultSet.getString("Titotlo_Def"),
                resultSet.getString("URL_Immagine"),
                resultSet.getString("Trama"),
                resultSet.getString("Background"),
                resultSet.getDouble("Prezzo"),
                resultSet.getInt("Quantita"));
    }

    @Override
    public List<Integer> findAllIDs(int limit) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT ID FROM Articolo LIMIT ?";

        try (Connection connection = DatabaseManager.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, limit);
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ids.add(resultSet.getInt("ID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero degli ID degli articoli", e);
        }
        return ids;
    }

    public List<Integer> findAllIDs() {
        return findAllIDs(6);
    }
}