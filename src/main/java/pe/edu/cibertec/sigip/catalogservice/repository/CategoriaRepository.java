package pe.edu.cibertec.sigip.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.sigip.catalogservice.model.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    boolean existsByNombreIgnoreCase(String nombre);
    Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
