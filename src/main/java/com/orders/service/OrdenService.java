package com.orders.service;

import com.orders.entity.Orden;

public interface OrdenService {

    public Orden create(Orden orden);
    public Orden get(Long id);
    public Orden getByCodigo(String codigo);
    public Orden update(Long id, Orden orden);
    public Orden delete(Long id);

}
