package pe.sigip.catalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sigip.catalog.model.Categoria;
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> { boolean existsByNombreIgnoreCase(String nombre); }