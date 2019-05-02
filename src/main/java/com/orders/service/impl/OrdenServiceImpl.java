package com.orders.service.impl;

import com.orders.entity.Orden;
import com.orders.entity.Producto;
import com.orders.repository.OrdenDetalleRepository;
import com.orders.repository.OrdenRepository;
import com.orders.repository.ProductoRepository;
import com.orders.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenDetalleRepository ordenDetalleRepository;

    @Override
    @Transactional()
    public Orden create(Orden orden) {

        orden.getDetalles().forEach(detalle -> {
            detalle.setOrden(orden);
            ordenDetalleRepository.save(detalle);

            Producto producto = productoRepository.getOne(detalle.getProducto().getId());
            updateStock(producto, detalle.getCantidad());
            productoRepository.save(producto);
        });

        return ordenRepository.save(orden);
    }

    private void updateStock(Producto producto, Long cantidad){
        Long stock = producto.getStock() - cantidad;
        producto.setStock(stock);
    }

    @Override
    public Orden get(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }

    @Override
    public Orden getByCodigo(String codigo) {
        return ordenRepository.getByCodigo(codigo);
    }

    @Override
    @Transactional()
    public Orden update(Long id, Orden orden) {
        Orden persist = ordenRepository.findById(id).orElse(null);
        if (persist == null)
            return null;

        persist.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.getOne(detalle.getProducto().getId());
            updateStock(producto, detalle.getCantidad() * -1);
            productoRepository.save(producto);
            persist._getDetalles().remove(detalle);
            ordenDetalleRepository.delete(detalle);
        }); // Elimina los productos
        orden.getDetalles().forEach(detalle -> {
            Producto producto = productoRepository.getOne(detalle.getProducto().getId());
            updateStock(producto, detalle.getCantidad());
            productoRepository.save(producto);
            detalle.setOrden(persist);
            persist._getDetalles().add(detalle);
            ordenDetalleRepository.save(detalle);
        }); // Añade los productos
        persist.calcularTotales(); // Calcula los totales
        return ordenRepository.save(persist);
    }

    @Override
    @Transactional()
    public Orden delete(Long id) {
        Orden orden = ordenRepository.findById(id).orElse(null);
        if (orden != null) {
            orden.getDetalles().forEach(orderDetalle -> {
                orden._getDetalles().remove(orderDetalle);
                ordenDetalleRepository.delete(orderDetalle);
            });
            ordenRepository.delete(orden);
        }
        return orden;
    }
}
