package pe.sigip.catalog.repository;

import pe.sigip.catalog.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByDisponibleTrue();
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}