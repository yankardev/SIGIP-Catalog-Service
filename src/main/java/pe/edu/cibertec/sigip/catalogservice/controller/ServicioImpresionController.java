package pe.edu.cibertec.sigip.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioImpresionRequest;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioImpresionResponse;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioOpcionResponse;
import pe.edu.cibertec.sigip.catalogservice.service.ServicioImpresionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicios")
@RequiredArgsConstructor
public class ServicioImpresionController {
    private final ServicioImpresionService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioImpresionResponse>> listar() { return ResponseEntity.ok(servicioService.listar()); }

    /** Servicios vigentes disponibles para registrar pedidos. */
    @GetMapping("/activos")
    public ResponseEntity<List<ServicioImpresionResponse>> listarActivos() { return ResponseEntity.ok(servicioService.listarActivos()); }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioImpresionResponse> obtener(@PathVariable Integer id) { return ResponseEntity.ok(servicioService.obtener(id)); }

    /** Opciones de precio para servicios personalizables: tamaño, material, acabado, etc. */
    @GetMapping("/{id}/opciones")
    public ResponseEntity<List<ServicioOpcionResponse>> opciones(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.listarOpciones(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    public ResponseEntity<ServicioImpresionResponse> registrar(@Valid @RequestBody ServicioImpresionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.registrar(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    public ResponseEntity<ServicioImpresionResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody ServicioImpresionRequest request) {
        return ResponseEntity.ok(servicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VENTAS')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
