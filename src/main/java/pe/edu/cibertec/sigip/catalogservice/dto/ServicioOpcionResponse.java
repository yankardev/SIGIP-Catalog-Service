package pe.edu.cibertec.sigip.catalogservice.dto;

import pe.edu.cibertec.sigip.catalogservice.model.ServicioOpcion;

import java.math.BigDecimal;

public record ServicioOpcionResponse(
        Integer idopcion,
        Integer idservicio,
        String grupo,
        String nombre,
        BigDecimal incremento,
        Integer orden
) {
    public static ServicioOpcionResponse from(ServicioOpcion opcion) {
        return new ServicioOpcionResponse(
                opcion.getIdopcion(),
                opcion.getServicio().getIdservicio(),
                opcion.getGrupo(),
                opcion.getNombre(),
                opcion.getIncremento(),
                opcion.getOrden()
        );
    }
}
