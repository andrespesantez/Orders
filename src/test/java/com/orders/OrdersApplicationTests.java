package com.orders;

import com.orders.entity.Orden;
import com.orders.entity.OrdenDetalle;
import com.orders.entity.Producto;
import com.orders.service.OrdenService;
import com.orders.service.ProductoService;
import com.orders.service.impl.OrdenServiceImpl;
import com.orders.service.impl.ProductoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersApplicationTests {

    @TestConfiguration
    static class ServicesImplTestContextConfiguration {

        @Bean
        public ProductoService productoService() {
            return new ProductoServiceImpl();
        }

        @Bean
        public OrdenService orderService() {
            return new OrdenServiceImpl();
        }

    }

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OrdenService orderService;


    /* Testing application services */

    /* Producto service */
    @Test
    public void findProducto() {
        Producto a = productoService.get(1L);
        assertEquals("A1", a.getSKU());

        Producto b = productoService.get(100L);
        assertNull(b);
    }

    /* Orden service */
    @Test
    @Rollback(true)
    public void createOrder() {
        Producto a = productoService.get(1L);
        Producto b = productoService.get(2L);
        Producto c = productoService.get(3L);

        OrdenDetalle d1 = new OrdenDetalle(1L, a);
        OrdenDetalle d2 = new OrdenDetalle(1L, b);
        OrdenDetalle d3 = new OrdenDetalle(1L, c);

        List<OrdenDetalle> detalles = Arrays.asList(d1, d2, d3);

        Orden o = new Orden("O3");
        detalles.forEach(detalle -> o.getDetalles().add(detalle));

        o.calcularTotales();

        Orden result = orderService.create(o);
        assertEquals(o, result);

        result = orderService.get(result.getId());
        assertEquals(o, result);
        assertTrue(result.getDetalles().size() == 3);
    }

    @Test
    public void readOrder() {
        Orden a = orderService.get(1L);
        assertEquals("O1", a.getCodigo());

        Orden b = orderService.get(100L);
        assertNull(b);
    }

    @Test
    @Rollback(true)
    public void updateOrder() {
        Producto a = productoService.get(1L);
        Producto b = productoService.get(2L);
        Producto c = productoService.get(3L);

        OrdenDetalle d1 = new OrdenDetalle(1L, a);
        OrdenDetalle d2 = new OrdenDetalle(1L, b);
        OrdenDetalle d3 = new OrdenDetalle(1L, c);

        List<OrdenDetalle> detalles = Arrays.asList(d1, d2, d3);

        Orden o = new Orden("O3");
        detalles.forEach(detalle -> o._getDetalles().add(detalle));

        o.calcularTotales();

        Orden result = orderService.create(o);
        result = orderService.get(result.getId());
        assertTrue(result.getDetalles().size() == 3);

        Orden persist = new Orden("O3");
        persist._getDetalles().add(d3);

        o.calcularTotales();

        result = orderService.update(result.getId(), persist);
        assertEquals(o, result);

        result = orderService.get(result.getId());
        assertEquals(o, result);
        assertTrue(result.getDetalles().size() == 1);
    }

    @Test
    @Rollback(true)
    public void deleteOrder() {
        Orden a = orderService.get(1L);
        assertEquals("O1", a.getCodigo());

        a = orderService.delete(1L);
        assertEquals("O1", a.getCodigo());

        a = orderService.get(1L);
        assertNull(a);

        Orden b = orderService.delete(100L);
        assertNull(b);
    }

    /* Final application service test */

}
