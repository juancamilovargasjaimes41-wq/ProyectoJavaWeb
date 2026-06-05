package uts.edu.co.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uts.edu.co.entity.Usuario;
import uts.edu.co.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void crearOActualizarUsuario(String username, String rawPassword, String email, String rol) {
        var opt = usuarioRepository.findByUsername(username);
        if (opt.isPresent()) {
            Usuario u = opt.get();
            if (!u.getPassword().startsWith("$2a$")) {
                u.setPassword(passwordEncoder.encode(rawPassword));
                u.setEmail(email);
                u.setRol(rol);
                usuarioRepository.save(u);
                System.out.println("Password actualizado para: " + username);
            }
        } else {
            Usuario nuevo = new Usuario(username, passwordEncoder.encode(rawPassword), email, rol);
            usuarioRepository.save(nuevo);
            System.out.println("Usuario creado: " + username);
        }
    }

    @Override
    public void run(String... args) {
        crearOActualizarUsuario("admin_store", "admin123", "admin@pixelforge.com", "ADMIN");
        crearOActualizarUsuario("juan_perez", "user456", "juan@email.com", "CLIENTE");
    }
}
