package com.orders.entity;

import com.orders.enums.MonedaEnum;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"SKU"}))
public class Producto implements Serializable {

    private static  final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_producto", sequenceName = "seq_producto", initialValue = 5, allocationSize = 100)
    @GeneratedValue(generator = "seq_producto")
    private Long id;

    @NotBlank(message = "SKU es requerido")
    private String SKU;

    @NotBlank(message = "Nombre es requerido")
    private String nombre;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(5) default 'USD'")
    private MonedaEnum moneda = MonedaEnum.USD;

    @DecimalMin(value = "0.01", message = "Precio minimo 0.01")
    @NotNull
    private Double precio = 0.0;

    @Min(value = 0, message = "Stock minimo 0")
    @NotNull
    private Long stock = 0L;

    public Producto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id.equals(producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, SKU);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MonedaEnum getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaEnum moneda) {
        this.moneda = moneda;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
