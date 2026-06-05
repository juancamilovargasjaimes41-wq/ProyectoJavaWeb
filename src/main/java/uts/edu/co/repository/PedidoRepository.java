package uts.edu.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.co.entity.Pedido;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioIdUsuarioOrderByFechaDesc(Integer idUsuario);
}
