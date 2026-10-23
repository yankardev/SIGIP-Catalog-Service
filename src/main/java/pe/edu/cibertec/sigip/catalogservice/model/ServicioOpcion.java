package pe.edu.cibertec.sigip.catalogservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicio_opcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idopcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idservicio", nullable = false)
    private ServicioImpresion servicio;

    @Column(nullable = false, length = 80)
    private String grupo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal incremento = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Integer orden = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
