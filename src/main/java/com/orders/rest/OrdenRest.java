package com.orders.rest;

import com.orders.currency.FixerAPI;
import com.orders.entity.Orden;
import com.orders.enums.MonedaEnum;
import com.orders.service.OrdenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrdenRest {

    @Autowired
    private OrdenService orderService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden creada exitosamente"),
            @ApiResponse(code = 201, message = "Orden creada exitosamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error de petición"),
            @ApiResponse(code = 404, message = "Orden no creada") })
    @PostMapping() // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Orden> create(@RequestBody @Valid Orden order) {
        Orden newOrden = orderService.create(order);
        newOrden.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        return newOrden == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.ok(newOrden);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden obtenida correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Orden no encontrada") })
    @GetMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orden> read(@PathVariable("id") Long id) {
        Orden order = orderService.get(id);
        order.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        return order == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(order);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden obtenida correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Orden no encontrada") })
    @GetMapping("/code/{codigo}") // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orden> getByCode(@PathVariable("codigo") String code) {
        Orden order = orderService.getByCodigo(code);
        order.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        return order == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(order);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden transformada correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Orden no encontrada") })
    @GetMapping("/currency/{codigo}/{moneda}") // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Orden> getCurrency(@PathVariable("codigo") String codigo, @PathVariable("moneda") String moneda) {
        FixerAPI api = new FixerAPI();
        Double valor = null;
        try {
            valor = api.requestAPI(MonedaEnum.valueOf(moneda));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        if (valor == null)
            valor = 1.0;

        Orden order = orderService.getByCodigo(codigo);
        order.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        order.setMoneda(MonedaEnum.valueOf(moneda));
        order.cambiarCurrency(valor);

        return order == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(order);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden actualizada correctamente"),
            @ApiResponse(code = 201, message = "Orden actualizada correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Orden no encontrada") })
    @PutMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Orden> update(@PathVariable("id") Long id, @RequestBody @Valid Orden order) {
        Orden updatedOrden = orderService.update(id, order);
        updatedOrden.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        return updatedOrden == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.ok(updatedOrden);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Orden eliminada correctamente"),
            @ApiResponse(code = 204, message = "No existe orden"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición") })
    @DeleteMapping("/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Orden> delete(@PathVariable("id") Long id) {
        Orden order = orderService.delete(id);
        order.getDetalles().forEach(ordenDetalle -> {
            ordenDetalle.setOrden(null);
        });
        return order == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(order);
    }

    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    public void handleBadRequest() {
        //TODO Error response with ApiError
    }

    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { EntityNotFoundException.class })
    public void handleNotFound() {
        //TODO Error response with ApiError
    }
}
