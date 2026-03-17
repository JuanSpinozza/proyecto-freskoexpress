package com.freskoexpress.api.domain.inventario;

import com.freskoexpress.api.domain.proveedor.Proveedor;
import com.freskoexpress.api.shared.enums.CategoriaProducto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProducto categoria;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @Column(name = "temp_min_c", precision = 5, scale = 2)
    private BigDecimal tempMinC;

    @Column(name = "temp_max_c", precision = 5, scale = 2)
    private BigDecimal tempMaxC;

    @Column(name = "stock_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockMinimo = BigDecimal.ZERO;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private Boolean activo = true;

    // ── Constructores ─────────────────────────────────────────────────
    protected Producto() {}   // requerido por JPA

    private Producto(Builder builder) {
        this.proveedor      = builder.proveedor;
        this.nombre         = builder.nombre;
        this.categoria      = builder.categoria;
        this.unidadMedida   = builder.unidadMedida;
        this.tempMinC       = builder.tempMinC;
        this.tempMaxC       = builder.tempMaxC;
        this.stockMinimo    = builder.stockMinimo;
        this.precioUnitario = builder.precioUnitario;
        this.activo         = builder.activo;
    }

    // ── Builder ───────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Proveedor        proveedor;
        private String           nombre;
        private CategoriaProducto categoria;
        private String           unidadMedida;
        private BigDecimal       tempMinC;
        private BigDecimal       tempMaxC;
        private BigDecimal       stockMinimo    = BigDecimal.ZERO;
        private BigDecimal       precioUnitario;
        private Boolean          activo         = true;

        public Builder proveedor(Proveedor proveedor) {
            this.proveedor = proveedor;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder categoria(CategoriaProducto categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder unidadMedida(String unidadMedida) {
            this.unidadMedida = unidadMedida;
            return this;
        }

        public Builder tempMinC(BigDecimal tempMinC) {
            this.tempMinC = tempMinC;
            return this;
        }

        public Builder tempMaxC(BigDecimal tempMaxC) {
            this.tempMaxC = tempMaxC;
            return this;
        }

        public Builder stockMinimo(BigDecimal stockMinimo) {
            this.stockMinimo = stockMinimo;
            return this;
        }

        public Builder precioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
            return this;
        }

        public Builder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }

        public Producto build() {
            if (proveedor == null) {
                throw new IllegalStateException("El proveedor es obligatorio");
            }
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalStateException("El nombre es obligatorio");
            }
            if (categoria == null) {
                throw new IllegalStateException("La categoría es obligatoria");
            }
            if (unidadMedida == null || unidadMedida.isBlank()) {
                throw new IllegalStateException("La unidad de medida es obligatoria");
            }
            if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("El precio unitario debe ser mayor o igual a 0");
            }
            if (tempMinC != null && tempMaxC != null
                    && tempMinC.compareTo(tempMaxC) >= 0) {
                throw new IllegalStateException("temp_min_c debe ser menor que temp_max_c");
            }
            return new Producto(this);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────
    public Integer          getIdProducto()    { return idProducto; }
    public Proveedor        getProveedor()     { return proveedor; }
    public String           getNombre()        { return nombre; }
    public CategoriaProducto getCategoria()   { return categoria; }
    public String           getUnidadMedida() { return unidadMedida; }
    public BigDecimal       getTempMinC()      { return tempMinC; }
    public BigDecimal       getTempMaxC()      { return tempMaxC; }
    public BigDecimal       getStockMinimo()   { return stockMinimo; }
    public BigDecimal       getPrecioUnitario(){ return precioUnitario; }
    public Boolean          getActivo()        { return activo; }

    // ── Setters ───────────────────────────────────────────────────────
    public void setIdProducto(Integer idProducto)           { this.idProducto = idProducto; }
    public void setProveedor(Proveedor proveedor)           { this.proveedor = proveedor; }
    public void setNombre(String nombre)                    { this.nombre = nombre; }
    public void setCategoria(CategoriaProducto categoria)   { this.categoria = categoria; }
    public void setUnidadMedida(String unidadMedida)        { this.unidadMedida = unidadMedida; }
    public void setTempMinC(BigDecimal tempMinC)            { this.tempMinC = tempMinC; }
    public void setTempMaxC(BigDecimal tempMaxC)            { this.tempMaxC = tempMaxC; }
    public void setStockMinimo(BigDecimal stockMinimo)      { this.stockMinimo = stockMinimo; }
    public void setPrecioUnitario(BigDecimal precioUnitario){ this.precioUnitario = precioUnitario; }
    public void setActivo(Boolean activo)                   { this.activo = activo; }
}