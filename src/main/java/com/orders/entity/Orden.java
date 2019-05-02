package com.orders.entity;

import com.orders.enums.MonedaEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
public class Orden implements Serializable {

    private static  final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_orden", sequenceName = "seq_orden", initialValue = 3, allocationSize = 100)
    @GeneratedValue(generator = "seq_orden")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String codigo;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(5) default 'EUR'")
    private MonedaEnum moneda = MonedaEnum.EUR;

    @NotNull
    @Column(nullable = false)
    private Double total = 0.0;

    @OneToMany(mappedBy = "orden", fetch = FetchType.EAGER)
    private List<OrdenDetalle> detalles = new ArrayList<>();

    public Orden() {
    }

    public Orden(String codigo) {
        this.codigo = codigo;
    }

    public void calcularTotales(){
        Double total = 0.0;
        for (OrdenDetalle detalle : detalles) {
            total += detalle.getProducto().getPrecio();
        }

        this.total = total;
    }

    public void cambiarCurrency(Double valor){
        Double total = 0.0;
        for (OrdenDetalle detalle : detalles) {
            total += detalle.getProducto().getPrecio();
        }

        this.total = total * valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orden order = (Orden) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public MonedaEnum getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaEnum moneda) {
        this.moneda = moneda;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrdenDetalle> getDetalles() {
        return new ArrayList<>( detalles);
    }

    public void setDetalles(List<OrdenDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<OrdenDetalle> _getDetalles() {
        return this.detalles;
    }
}
