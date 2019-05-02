package com.orders.service;

import com.orders.entity.Producto;

public interface ProductoService {

    public Producto create(Producto order);
    public Producto get(Long id);
    public Producto update(Long id, Producto order);
    public Producto delete(Long id);
}
