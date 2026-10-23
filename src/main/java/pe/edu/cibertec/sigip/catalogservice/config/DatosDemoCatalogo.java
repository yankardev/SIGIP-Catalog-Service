package pe.edu.cibertec.sigip.catalogservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.sigip.catalogservice.model.Categoria;
import pe.edu.cibertec.sigip.catalogservice.model.ServicioImpresion;
import pe.edu.cibertec.sigip.catalogservice.model.ServicioOpcion;
import pe.edu.cibertec.sigip.catalogservice.repository.CategoriaRepository;
import pe.edu.cibertec.sigip.catalogservice.repository.ServicioImpresionRepository;
import pe.edu.cibertec.sigip.catalogservice.repository.ServicioOpcionRepository;

import java.math.BigDecimal;

/**
 * Catálogo inicial idempotente para demo. Se puede ejecutar sobre una base existente:
 * completa servicios/imagenes/opciones sin duplicar registros conocidos.
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "sigip.catalogo.datos-demo", havingValue = "true", matchIfMissing = true)
public class DatosDemoCatalogo implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ServicioImpresionRepository servicioRepository;
    private final ServicioOpcionRepository opcionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Categoria digital = categoria("Impresión digital", "Trabajos de pequeño formato en papel y adhesivos");
        Categoria granFormato = categoria("Gran formato", "Banners, gigantografías y viniles");
        Categoria especiales = categoria("Trabajos especiales", "Empaques y productos configurables");
        Categoria merchandising = categoria("Merchandising", "Productos promocionales personalizados");

        servicio("Volantes A5 (millar)", "Papel couché 115 g, full color", "95.00", false,
                "/assets/servicios/volantes.svg", digital);
        servicio("Tarjetas personales (millar)", "Cartulina 300 g, plastificado mate", "60.00", false,
                "/assets/servicios/tarjetas.svg", digital);
        ServicioImpresion stickers = servicio("Stickers troquelados (ciento)", "Vinil adhesivo con corte", "45.00", true,
                "/assets/servicios/stickers.svg", digital);
        servicio("Banner 1 x 2 m", "Lona 13 oz con ojales", "70.00", false,
                "/assets/servicios/banner.svg", granFormato);
        ServicioImpresion gigantografia = servicio("Gigantografía m2", "Impresión de gran formato por metro cuadrado", "28.00", true,
                "/assets/servicios/gigantografia.svg", granFormato);
        ServicioImpresion packaging = servicio("Packaging personalizado", "Cajas y empaques a medida por unidad", "4.50", true,
                "/assets/servicios/packaging.svg", especiales);
        ServicioImpresion polos = servicio("Polos personalizados", "Polo de algodón con impresión DTF", "25.00", true,
                "/assets/servicios/polos.svg", merchandising);
        servicio("Tazas personalizadas", "Taza cerámica sublimada", "18.00", false,
                "/assets/servicios/tazas.svg", merchandising);

        // Gigantografía
        opcion(gigantografia, "Material", "Lona frontlit", "0.00", 1);
        opcion(gigantografia, "Material", "Lona blackout", "8.00", 2);
        opcion(gigantografia, "Material", "Vinil adhesivo", "15.00", 3);
        opcion(gigantografia, "Acabado", "Sin acabado adicional", "0.00", 1);
        opcion(gigantografia, "Acabado", "Ojales reforzados", "4.00", 2);
        opcion(gigantografia, "Acabado", "Bastidor", "20.00", 3);

        // Packaging
        opcion(packaging, "Tamaño", "Pequeño", "0.00", 1);
        opcion(packaging, "Tamaño", "Mediano", "0.80", 2);
        opcion(packaging, "Tamaño", "Grande", "1.80", 3);
        opcion(packaging, "Material", "Cartulina SBS", "0.00", 1);
        opcion(packaging, "Material", "Kraft", "1.00", 2);
        opcion(packaging, "Material", "Microcorrugado", "1.50", 3);
        opcion(packaging, "Acabado", "Sin laminado", "0.00", 1);
        opcion(packaging, "Acabado", "Laminado mate", "0.90", 2);
        opcion(packaging, "Acabado", "Barniz UV", "1.20", 3);

        // Stickers
        opcion(stickers, "Material", "Vinil blanco", "0.00", 1);
        opcion(stickers, "Material", "Vinil transparente", "8.00", 2);
        opcion(stickers, "Material", "Vinil reflectivo", "15.00", 3);
        opcion(stickers, "Corte", "Corte estándar", "0.00", 1);
        opcion(stickers, "Corte", "Troquelado especial", "10.00", 2);
        opcion(stickers, "Laminado", "Sin laminado", "0.00", 1);
        opcion(stickers, "Laminado", "Laminado mate", "6.00", 2);
        opcion(stickers, "Laminado", "Laminado brillante", "6.00", 3);

        // Polos
        opcion(polos, "Talla", "S", "0.00", 1);
        opcion(polos, "Talla", "M", "0.00", 2);
        opcion(polos, "Talla", "L", "1.50", 3);
        opcion(polos, "Talla", "XL", "2.50", 4);
        opcion(polos, "Color", "Blanco", "0.00", 1);
        opcion(polos, "Color", "Negro", "1.50", 2);
        opcion(polos, "Color", "Azul marino", "1.50", 3);
        opcion(polos, "Impresión", "Pecho", "0.00", 1);
        opcion(polos, "Impresión", "Pecho + espalda", "8.00", 2);
    }

    private Categoria categoria(String nombre, String descripcion) {
        Categoria categoria = categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseGet(() -> Categoria.builder().nombre(nombre).activo(true).build());
        categoria.setDescripcion(descripcion);
        categoria.setActivo(true);
        return categoriaRepository.save(categoria);
    }

    private ServicioImpresion servicio(String nombre, String descripcion, String precio, boolean personalizable,
                                       String imagenUrl, Categoria categoria) {
        ServicioImpresion servicio = servicioRepository.findByNombre(nombre)
                .orElseGet(() -> ServicioImpresion.builder().nombre(nombre).build());
        servicio.setDescripcion(descripcion);
        servicio.setPrecioBase(new BigDecimal(precio));
        servicio.setRequiereCotizacion(false);
        servicio.setPersonalizable(personalizable);
        servicio.setImagenUrl(imagenUrl);
        servicio.setActivo(true);
        servicio.setCategoria(categoria);
        return servicioRepository.save(servicio);
    }

    private void opcion(ServicioImpresion servicio, String grupo, String nombre, String incremento, int orden) {
        if (opcionRepository.existsByServicioIdservicioAndGrupoAndNombre(servicio.getIdservicio(), grupo, nombre)) return;
        opcionRepository.save(ServicioOpcion.builder()
                .servicio(servicio)
                .grupo(grupo)
                .nombre(nombre)
                .incremento(new BigDecimal(incremento))
                .orden(orden)
                .activo(true)
                .build());
    }
}
