package com.orders.repository;

import com.orders.entity.Orden;
import com.orders.entity.OrdenDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDetalleRepository extends JpaRepository<OrdenDetalle, Long> {
}
