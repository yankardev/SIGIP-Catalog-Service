package pe.sigip.catalog.controller;

import jakarta.validation.Valid;
import pe.sigip.catalog.model.Producto;
import pe.sigip.catalog.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/catalogo/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    private final ProductoRepository repository;

    public ProductoController(ProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String categoria,
                                 @RequestParam(required = false) Boolean disponibles) {
        if (categoria != null && !categoria.isBlank()) {
            return repository.findByCategoriaIgnoreCase(categoria);
        }
        if (Boolean.TRUE.equals(disponibles)) {
            return repository.findByDisponibleTrue();
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody Producto producto) {
        producto.setId(null);
        return repository.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto datos) {
        Producto actual = buscar(id);
        actual.setNombre(datos.getNombre());
        actual.setCategoria(datos.getCategoria());
        actual.setDescripcion(datos.getDescripcion());
        actual.setPrecioBase(datos.getPrecioBase());
        actual.setDisponible(datos.isDisponible());
        return repository.save(actual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        repository.deleteById(id);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("servicio", "SIGIP Catalog Service", "estado", "activo");
    }
}