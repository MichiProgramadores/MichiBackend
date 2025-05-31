package com.MichiSistema.dominio;
import com.MichiSistema.Enum.TipoProducto;
import com.MichiSistema.Enum.UnidadMedida;
/**
 *
 * @author Roberto Abel Chavez Javier
 */
public class Producto {
    private int producto_id;
    private String nombre;
    private double precio;
    private int edad_minima;
    private int stockActual;
    private int stockMinimo;
    private TipoProducto categoriaProducto;
    private double volumen;
    private String descripcion;
    private UnidadMedida unidadMedida;
    private char estado;
     // Constructor con parámetros

    public Producto(String nombre, double precio, int edad_minima,
            int stockActual, int stockMinimo, TipoProducto categoriaProducto, 
            double volumen, String descripcion, UnidadMedida unidadMedida) {
        this.nombre = nombre;
        this.precio = precio;
        this.edad_minima = edad_minima;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = 'A';
        this.categoriaProducto = categoriaProducto;
        this.volumen = volumen;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
    }
 

    // Constructor sin parámetros (con valores predeterminados)
    public Producto() {
        this.nombre = "";                 // Valor predeterminado para nombre
        this.precio = 0.0;                // Valor predeterminado para precio
        this.edad_minima = 0;             // Valor predeterminado para edad mínima
        this.stockActual = 0;                   // Valor predeterminado para stock
        this.stockMinimo = 0; 
        this.estado = 'A';                // Valor predeterminado para estado ('A' para activo)
        this.categoriaProducto = null; // Valor predeterminado para categoría (suponiendo que 'GENERAL' es un valor del enum TipoProducto)
        this.volumen = 0.0;               // Valor predeterminado para volumen
        this.descripcion =""; // Lista vacía de materiales
    }

    /**
     * @return the producto_id
     */
    public int getProducto_id() {
        return producto_id;
    }

    /**
     * @param producto_id the producto_id to set
     */
    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the edad_minima
     */
    public int getEdad_minima() {
        return edad_minima;
    }

    /**
     * @param edad_minima the edad_minima to set
     */
    public void setEdad_minima(int edad_minima) {
        this.edad_minima = edad_minima;
    }

  

    /**
     * @return the estado
     */
    public char getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(char estado) {
        this.estado = estado;
    }

    /**
     * @return the categoriaProducto
     */
    public TipoProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    /**
     * @param categoriaProducto the categoriaProducto to set
     */
    public void setCategoriaProducto(TipoProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    /**
     * @return the volumen
     */
    public double getVolumen() {
        return volumen;
    }

    /**
     * @param volumen the volumen to set
     */
    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    /**
    
}

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the unidadMedida
     */
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * @param unidadMedida the unidadMedida to set
     */
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * @return the stockActual
     */
    public int getStockActual() {
        return stockActual;
    }

    /**
     * @param stockActual the stockActual to set
     */
    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    /**
     * @return the stockMinimo
     */
    public int getStockMinimo() {
        return stockMinimo;
    }

    /**
     * @param stockMinimo the stockMinimo to set
     */
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
}
