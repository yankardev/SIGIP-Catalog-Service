package pe.sigip.catalog.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.sigip.catalog.model.Producto;
import pe.sigip.catalog.repository.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Inserta un catálogo inicial para facilitar la primera ejecución local.
 * No modifica los productos existentes.
 */
@Configuration
public class DataInitializer {

    @Bean
    ApplicationRunner cargarCatalogoInicial(ProductoRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.saveAll(List.of(
                producto("Tarjetas de presentación", "Tarjetas",
                    "Tarjetas couché a color, paquete referencial", "35.00"),
                producto("Volantes A5", "Volantes",
                    "Impresión a color por millar", "120.00"),
                producto("Afiches A3", "Afiches",
                    "Afiches impresos a color en papel couché", "4.50"),
                producto("Banners publicitarios", "Banners",
                    "Banner publicitario por metro cuadrado", "45.00")
            ));
        };
    }

    private Producto producto(String nombre, String categoria, String descripcion, String precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setDescripcion(descripcion);
        producto.setPrecioBase(new BigDecimal(precio));
        producto.setDisponible(true);
        return producto;
    }
}
