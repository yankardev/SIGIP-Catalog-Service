package pe.sigip.catalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.sigip.catalog.model.ServicioImpresion;
import java.util.List;
import java.util.Optional;
public interface ServicioImpresionRepository extends JpaRepository<ServicioImpresion,Integer> {
 @Query("select s from ServicioImpresion s join fetch s.categoria order by s.nombre") List<ServicioImpresion> listarConCategoria();
 @Query("select s from ServicioImpresion s join fetch s.categoria where s.activo = true order by s.nombre") List<ServicioImpresion> listarActivosConCategoria();
 @Query("select s from ServicioImpresion s join fetch s.categoria where s.idservicio = :id") Optional<ServicioImpresion> buscarConCategoria(Integer id);
 List<ServicioImpresion> findByActivoTrueOrderByNombreAsc();
 boolean existsByCategoriaIdcategoria(Integer idcategoria);
}