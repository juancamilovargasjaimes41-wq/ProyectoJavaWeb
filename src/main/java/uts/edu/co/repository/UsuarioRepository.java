package uts.edu.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.co.entity.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
