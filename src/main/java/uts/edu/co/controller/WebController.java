package uts.edu.co.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.co.entity.Usuario;
import uts.edu.co.repository.CategoriaRepository;
import uts.edu.co.repository.UsuarioRepository;
import uts.edu.co.repository.VideojuegoRepository;

import java.security.Principal;

@Controller
public class WebController {

    private final VideojuegoRepository videojuegoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public WebController(VideojuegoRepository videojuegoRepository,
                         CategoriaRepository categoriaRepository,
                         UsuarioRepository usuarioRepository,
                         PasswordEncoder passwordEncoder) {
        this.videojuegoRepository = videojuegoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/tienda")
    public String tienda(@RequestParam(required = false) Integer categoria, Model model) {
        if (categoria != null) {
            model.addAttribute("videojuegos", videojuegoRepository.findByCategoriaIdCategoriaOrderByTituloAsc(categoria));
            model.addAttribute("categoriaActiva", categoria);
        } else {
            model.addAttribute("videojuegos", videojuegoRepository.findAllByOrderByTituloAsc());
        }
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "pixel-store";
    }

    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           RedirectAttributes redirect) {
        if (!password.equals(confirmPassword)) {
            redirect.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }
        if (password.length() < 4) {
            redirect.addFlashAttribute("error", "La contraseña debe tener al menos 4 caracteres");
            return "redirect:/registro";
        }
        if (usuarioRepository.existsByUsername(username)) {
            redirect.addFlashAttribute("error", "El nombre de usuario ya existe");
            return "redirect:/registro";
        }
        if (usuarioRepository.existsByEmail(email)) {
            redirect.addFlashAttribute("error", "El correo electrónico ya está registrado");
            return "redirect:/registro";
        }
        try {
            Usuario nuevo = new Usuario(username, passwordEncoder.encode(password), email, "CLIENTE");
            usuarioRepository.save(nuevo);
            redirect.addFlashAttribute("registroExitoso", "Cuenta creada correctamente. Inicia sesión.");
            return "redirect:/login";
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error", "Error al crear la cuenta. Verifica los datos.");
            return "redirect:/registro";
        }
    }

    @GetMapping("/juego/{id}")
    public String detalleJuego(@PathVariable Integer id, Model model) {
        var juego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));
        model.addAttribute("juego", juego);
        return "juego-detalle";
    }

    @GetMapping("/cuenta")
    public String cuenta(Principal principal, Model model) {
        var usuario = usuarioRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "cuenta";
    }
}
