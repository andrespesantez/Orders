package com.orders.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
public class OrdenDetalle implements Serializable {

    private static  final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_ordendetalle", sequenceName = "seq_ordendetalle", initialValue = 6, allocationSize = 100)
    @GeneratedValue(generator = "seq_ordendetalle")
    private Long id;

    private Long cantidad = 0L;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private Orden orden;

    public OrdenDetalle() {
    }

    public OrdenDetalle(Long cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
