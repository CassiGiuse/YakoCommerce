package dao;

import java.util.List;
import java.util.Optional;

import models.Articolo;

public interface ArticoloDAO {
    void save(Articolo articolo);

    Optional<Articolo> findById(int id);

    List<Articolo> findAll();

    void update(Articolo articolo);

    List<Integer> findAllIDs(int limit);
}
