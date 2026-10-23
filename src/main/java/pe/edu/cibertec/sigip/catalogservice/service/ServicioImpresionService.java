package pe.edu.cibertec.sigip.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.sigip.catalogservice.config.CacheNombres;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioImpresionRequest;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioImpresionResponse;
import pe.edu.cibertec.sigip.catalogservice.dto.ServicioOpcionResponse;
import pe.edu.cibertec.sigip.catalogservice.model.Categoria;
import pe.edu.cibertec.sigip.catalogservice.model.ServicioImpresion;
import pe.edu.cibertec.sigip.catalogservice.repository.CategoriaRepository;
import pe.edu.cibertec.sigip.catalogservice.repository.ServicioImpresionRepository;
import pe.edu.cibertec.sigip.catalogservice.repository.ServicioOpcionRepository;
import pe.edu.cibertec.sigip.common.error.RecursoNoEncontradoException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioImpresionService {

    private final ServicioImpresionRepository servicioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ServicioOpcionRepository opcionRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = CacheNombres.SERVICIOS, key = "'todos'")
    public List<ServicioImpresionResponse> listar() {
        return servicioRepository.listarConCategoria().stream().map(ServicioImpresionResponse::from).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CacheNombres.SERVICIOS, key = "'activos'")
    public List<ServicioImpresionResponse> listarActivos() {
        return servicioRepository.listarActivosConCategoria().stream().map(ServicioImpresionResponse::from).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CacheNombres.SERVICIO, key = "#id")
    public ServicioImpresionResponse obtener(Integer id) {
        return ServicioImpresionResponse.from(servicioRepository.buscarConCategoria(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio de impresión no encontrado")));
    }

    @Transactional(readOnly = true)
    public List<ServicioOpcionResponse> listarOpciones(Integer idservicio) {
        ServicioImpresion servicio = buscar(idservicio);
        if (!Boolean.TRUE.equals(servicio.getPersonalizable())) return List.of();
        return opcionRepository.findByServicioIdservicioAndActivoTrueOrderByGrupoAscOrdenAscNombreAsc(idservicio)
                .stream().map(ServicioOpcionResponse::from).toList();
    }

    @Transactional
    @CacheEvict(value = CacheNombres.SERVICIOS, allEntries = true)
    public ServicioImpresionResponse registrar(ServicioImpresionRequest request) {
        ServicioImpresion servicio = ServicioImpresion.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .precioBase(request.precioBase())
                .requiereCotizacion(request.requiereCotizacion())
                .personalizable(Boolean.TRUE.equals(request.personalizable()))
                .imagenUrl(request.imagenUrl())
                .activo(request.activo() == null || request.activo())
                .categoria(buscarCategoria(request.idcategoria()))
                .build();
        return ServicioImpresionResponse.from(servicioRepository.save(servicio));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNombres.SERVICIOS, allEntries = true),
            @CacheEvict(value = CacheNombres.SERVICIO, key = "#id")
    })
    public ServicioImpresionResponse actualizar(Integer id, ServicioImpresionRequest request) {
        ServicioImpresion servicio = buscar(id);
        servicio.setNombre(request.nombre());
        servicio.setDescripcion(request.descripcion());
        servicio.setPrecioBase(request.precioBase());
        servicio.setRequiereCotizacion(request.requiereCotizacion());
        servicio.setCategoria(buscarCategoria(request.idcategoria()));
        if (request.personalizable() != null) servicio.setPersonalizable(request.personalizable());
        if (request.imagenUrl() != null && !request.imagenUrl().isBlank()) servicio.setImagenUrl(request.imagenUrl());
        if (request.activo() != null) servicio.setActivo(request.activo());
        return ServicioImpresionResponse.from(servicioRepository.save(servicio));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNombres.SERVICIOS, allEntries = true),
            @CacheEvict(value = CacheNombres.SERVICIO, key = "#id")
    })
    public void eliminar(Integer id) {
        servicioRepository.delete(buscar(id));
    }

    private ServicioImpresion buscar(Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio de impresión no encontrado"));
    }

    private Categoria buscarCategoria(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
    }
}
