package uts.edu.co.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.co.entity.Pedido;
import uts.edu.co.entity.DetallePedido;
import uts.edu.co.entity.Videojuego;
import uts.edu.co.model.CartItem;
import uts.edu.co.repository.PedidoRepository;
import uts.edu.co.repository.UsuarioRepository;
import uts.edu.co.repository.VideojuegoRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final VideojuegoRepository videojuegoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public CarritoController(VideojuegoRepository videojuegoRepository,
                             UsuarioRepository usuarioRepository,
                             PedidoRepository pedidoRepository) {
        this.videojuegoRepository = videojuegoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/agregar/{id}")
    public String agregar(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {
        Videojuego juego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        List<CartItem> cart = getCart(session);

        for (CartItem item : cart) {
            if (item.getIdVideojuego().equals(id)) {
                if (item.getCantidad() < juego.getStock()) {
                    item.setCantidad(item.getCantidad() + 1);
                } else {
                    redirect.addFlashAttribute("error", "Stock insuficiente para " + juego.getTitulo());
                }
                return "redirect:/juego/" + id;
            }
        }

        if (juego.getStock() > 0) {
            cart.add(new CartItem(id, juego.getTitulo(), juego.getPrecio(), juego.getImagenUrl(), 1));
        } else {
            redirect.addFlashAttribute("error", juego.getTitulo() + " está agotado");
        }

        return "redirect:/juego/" + id;
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);
        BigDecimal total = cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "carrito";
    }

    @PostMapping("/eliminar/{index}")
    public String eliminar(@PathVariable int index, HttpSession session) {
        List<CartItem> cart = getCart(session);
        if (index >= 0 && index < cart.size()) {
            cart.remove(index);
        }
        return "redirect:/carrito";
    }

    @PostMapping("/pagar")
    public String pagar(HttpSession session, Principal principal, RedirectAttributes redirect) {
        List<CartItem> cart = getCart(session);

        if (cart.isEmpty()) {
            redirect.addFlashAttribute("error", "El carrito está vacío");
            return "redirect:/carrito";
        }

        var usuario = usuarioRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (CartItem item : cart) {
            Videojuego juego = videojuegoRepository.findById(item.getIdVideojuego()).orElse(null);
            if (juego == null || juego.getStock() < item.getCantidad()) {
                redirect.addFlashAttribute("error",
                        "Stock insuficiente para " + item.getTitulo() + ". Disponible: " + (juego != null ? juego.getStock() : 0));
                return "redirect:/carrito";
            }
        }

        BigDecimal total = cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setTotal(total);
        pedido.setDetalles(new ArrayList<>());
        pedido = pedidoRepository.save(pedido);

        Pedido finalPedido = pedido;
        for (CartItem item : cart) {
            Videojuego juego = videojuegoRepository.findById(item.getIdVideojuego()).orElseThrow();
            DetallePedido detalle = new DetallePedido(finalPedido, juego, item.getCantidad(), juego.getPrecio());
            finalPedido.getDetalles().add(detalle);

            juego.setStock(juego.getStock() - item.getCantidad());
            videojuegoRepository.save(juego);
        }

        pedidoRepository.save(finalPedido);

        session.removeAttribute("cart");

        return "redirect:/carrito/recibo/" + finalPedido.getIdPedido();
    }

    @GetMapping("/recibo/{id}")
    public String recibo(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        model.addAttribute("pedido", pedido);
        return "recibo";
    }
}
