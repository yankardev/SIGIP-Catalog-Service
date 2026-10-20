package pe.sigip.catalog.service;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.sigip.catalog.config.CacheNombres;
import pe.sigip.catalog.dto.*;
import pe.sigip.catalog.model.*;
import pe.sigip.catalog.repository.*;
import pe.sigip.common.error.RecursoNoEncontradoException;
import java.util.List;
@Service @RequiredArgsConstructor
public class ServicioImpresionService {
 private final ServicioImpresionRepository servicios; private final CategoriaRepository categorias;
 @Transactional(readOnly=true) @Cacheable(value=CacheNombres.SERVICIOS,key="'todos'") public List<ServicioImpresionResponse> listar(){return servicios.listarConCategoria().stream().map(ServicioImpresionResponse::from).toList();}
 @Transactional(readOnly=true) @Cacheable(value=CacheNombres.SERVICIOS,key="'activos'") public List<ServicioImpresionResponse> listarActivos(){return servicios.listarActivosConCategoria().stream().map(ServicioImpresionResponse::from).toList();}
 @Transactional(readOnly=true) @Cacheable(value=CacheNombres.SERVICIO,key="#id") public ServicioImpresionResponse obtener(Integer id){return ServicioImpresionResponse.from(servicios.buscarConCategoria(id).orElseThrow(()->new RecursoNoEncontradoException("Servicio de impresión no encontrado")));}
 @Transactional @CacheEvict(value=CacheNombres.SERVICIOS,allEntries=true) public ServicioImpresionResponse registrar(ServicioImpresionRequest r){ServicioImpresion s=ServicioImpresion.builder().nombre(r.nombre()).descripcion(r.descripcion()).precioBase(r.precioBase()).requiereCotizacion(r.requiereCotizacion()).activo(r.activo()==null||r.activo()).categoria(buscarCategoria(r.idcategoria())).build();return ServicioImpresionResponse.from(servicios.save(s));}
 @Transactional @Caching(evict={@CacheEvict(value=CacheNombres.SERVICIOS,allEntries=true),@CacheEvict(value=CacheNombres.SERVICIO,key="#id")}) public ServicioImpresionResponse actualizar(Integer id,ServicioImpresionRequest r){ServicioImpresion s=buscar(id);s.setNombre(r.nombre());s.setDescripcion(r.descripcion());s.setPrecioBase(r.precioBase());s.setRequiereCotizacion(r.requiereCotizacion());s.setCategoria(buscarCategoria(r.idcategoria()));if(r.activo()!=null)s.setActivo(r.activo());return ServicioImpresionResponse.from(servicios.save(s));}
 @Transactional @Caching(evict={@CacheEvict(value=CacheNombres.SERVICIOS,allEntries=true),@CacheEvict(value=CacheNombres.SERVICIO,key="#id")}) public void eliminar(Integer id){servicios.delete(buscar(id));}
 private ServicioImpresion buscar(Integer id){return servicios.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Servicio de impresión no encontrado"));}
 private Categoria buscarCategoria(Integer id){return categorias.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Categoría no encontrada"));}
}