package com.orders.rest;

import com.orders.entity.Producto;
import com.orders.service.ProductoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/producto")
public class ProductoRest {

    @Autowired
    private ProductoService productoService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Producto creada exitosamente"),
            @ApiResponse(code = 201, message = "Producto creada exitosamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error de petición"),
            @ApiResponse(code = 404, message = "Producto no creada") })
    @PostMapping("/create") // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Producto> create(@RequestBody @Valid Producto producto) {
        Producto newProducto = productoService.create(producto);
        return newProducto == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.ok(newProducto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Producto obtained correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Producto not found") })
    @GetMapping("/find/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Producto> read(@PathVariable("id") Long id) {
        Producto producto = productoService.get(id);
        return producto == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(producto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Producto actualizado correctamente"),
            @ApiResponse(code = 201, message = "Producto actualizado correctamente"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición"),
            @ApiResponse(code = 404, message = "Producto no encontrado") })
    @PutMapping("/update/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Producto> update(@PathVariable("id") Long id, @RequestBody @Valid Producto producto) {
        Producto updatedProducto = productoService.update(id, producto);
        return updatedProducto == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.ok(updatedProducto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Producto eliminado correctamente"),
            @ApiResponse(code = 204, message = "Producto no encontrado"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Error en petición") })
    @DeleteMapping("/delete/{id}") // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Producto> delete(@PathVariable("id") Long id) {
        Producto producto = productoService.delete(id);
        return producto == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(producto);
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
