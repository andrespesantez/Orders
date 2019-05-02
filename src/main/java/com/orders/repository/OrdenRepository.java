package com.orders.repository;

import com.orders.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query(
            value = "SELECT o FROM Orden o WHERE o.codigo = :codigo")
    Orden getByCodigo(@Param("codigo") String codigo);

}
