package com.orders.service.impl;

import com.orders.entity.Producto;
import com.orders.repository.ProductoRepository;
import com.orders.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRespository;

    @Override
    @Transactional()
    public Producto create(Producto producto) {
        return productoRespository.save(producto);
    }

    @Override
    public Producto get(Long id) {
        return productoRespository.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Producto update(Long id, Producto producto) {
        Producto persist = productoRespository.getOne(id);
        persist.setMoneda(producto.getMoneda());
        persist.setNombre(producto.getNombre());
        persist.setSKU(producto.getSKU());
        persist.setPrecio(producto.getPrecio());
        persist.setStock(producto.getStock());

        return productoRespository.save(persist);
    }

    @Override
    @Transactional()
    public Producto delete(Long id) {
        Producto producto = productoRespository.getOne(id);
        if (producto != null) productoRespository.delete(producto);
        return producto;
    }
}
