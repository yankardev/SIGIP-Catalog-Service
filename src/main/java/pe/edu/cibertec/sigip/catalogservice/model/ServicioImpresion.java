package pe.edu.cibertec.sigip.catalogservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicio_impresion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioImpresion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idservicio;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioBase;

    @Column(nullable = false)
    @Builder.Default
    private Boolean requiereCotizacion = false;

    /**
     * Indica que el precio final se obtiene sumando al precio base las opciones
     * elegidas (material, tamaño, acabado, etc.).
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean personalizable = false;

    /** Ruta pública utilizada por Angular para representar visualmente el servicio. */
    @Column(name = "imagen_url", length = 300)
    private String imagenUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcategoria", nullable = false)
    private Categoria categoria;
}
