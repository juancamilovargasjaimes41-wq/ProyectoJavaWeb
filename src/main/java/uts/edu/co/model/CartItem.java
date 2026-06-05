package uts.edu.co.model;

import java.math.BigDecimal;

public class CartItem {

    private Integer idVideojuego;
    private String titulo;
    private BigDecimal precio;
    private String imagenUrl;
    private Integer cantidad;

    public CartItem() {}

    public CartItem(Integer idVideojuego, String titulo, BigDecimal precio, String imagenUrl, Integer cantidad) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.cantidad = cantidad;
    }

    public Integer getIdVideojuego() { return idVideojuego; }
    public void setIdVideojuego(Integer idVideojuego) { this.idVideojuego = idVideojuego; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getSubtotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
