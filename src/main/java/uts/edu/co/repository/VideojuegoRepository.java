package uts.edu.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.co.entity.Videojuego;
import java.util.List;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {
    List<Videojuego> findAllByOrderByTituloAsc();
    List<Videojuego> findByCategoriaIdCategoriaOrderByTituloAsc(Integer idCategoria);
}
