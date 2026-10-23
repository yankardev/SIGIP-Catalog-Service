package pe.edu.cibertec.sigip.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.sigip.catalogservice.model.ServicioOpcion;

import java.util.List;

public interface ServicioOpcionRepository extends JpaRepository<ServicioOpcion, Integer> {
    List<ServicioOpcion> findByServicioIdservicioAndActivoTrueOrderByGrupoAscOrdenAscNombreAsc(Integer idservicio);
    boolean existsByServicioIdservicioAndGrupoAndNombre(Integer idservicio, String grupo, String nombre);
}
