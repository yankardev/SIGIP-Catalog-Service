package pe.sigip.catalog.service;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.sigip.catalog.config.CacheNombres;
import pe.sigip.catalog.dto.*;
import pe.sigip.catalog.model.Categoria;
import pe.sigip.catalog.repository.*;
import pe.edu.cibertec.sigip.common.error.RecursoNoEncontradoException;
import pe.edu.cibertec.sigip.common.error.ReglaNegocioException;
import java.util.List;
@Service @RequiredArgsConstructor
public class CategoriaService {
 private final CategoriaRepository categorias; private final ServicioImpresionRepository servicios;
 @Transactional(readOnly=true) @Cacheable(value=CacheNombres.CATEGORIAS,key="'todas'")
 public List<CategoriaResponse> listar(){return categorias.findAll().stream().map(CategoriaResponse::from).toList();}
 @Transactional(readOnly=true) public CategoriaResponse obtener(Integer id){return CategoriaResponse.from(buscar(id));}
 @Transactional @CacheEvict(value=CacheNombres.CATEGORIAS,allEntries=true)
 public CategoriaResponse registrar(CategoriaRequest r){if(categorias.existsByNombreIgnoreCase(r.nombre()))throw new ReglaNegocioException("Ya existe una categoría con ese nombre");return CategoriaResponse.from(categorias.save(Categoria.builder().nombre(r.nombre()).descripcion(r.descripcion()).activo(true).build()));}
 @Transactional @Caching(evict={@CacheEvict(value=CacheNombres.CATEGORIAS,allEntries=true),@CacheEvict(value={CacheNombres.SERVICIOS,CacheNombres.SERVICIO},allEntries=true)})
 public CategoriaResponse actualizar(Integer id,CategoriaRequest r){Categoria c=buscar(id);if(!c.getNombre().equalsIgnoreCase(r.nombre())&&categorias.existsByNombreIgnoreCase(r.nombre()))throw new ReglaNegocioException("Ya existe una categoría con ese nombre");c.setNombre(r.nombre());c.setDescripcion(r.descripcion());return CategoriaResponse.from(categorias.save(c));}
 @Transactional @CacheEvict(value=CacheNombres.CATEGORIAS,allEntries=true)
 public void eliminar(Integer id){Categoria c=buscar(id);if(servicios.existsByCategoriaIdcategoria(id))throw new ReglaNegocioException("No se puede eliminar: la categoría tiene servicios asociados");categorias.delete(c);}
 private Categoria buscar(Integer id){return categorias.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Categoría no encontrada"));}
}