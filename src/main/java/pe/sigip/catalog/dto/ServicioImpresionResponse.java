package pe.sigip.catalog.dto;
import pe.sigip.catalog.model.ServicioImpresion;
import java.math.BigDecimal;
public record ServicioImpresionResponse(Integer idservicio,String nombre,String descripcion,BigDecimal precioBase,Boolean requiereCotizacion,Boolean activo,Integer idcategoria,String categoria) {
 public static ServicioImpresionResponse from(ServicioImpresion s){return new ServicioImpresionResponse(s.getIdservicio(),s.getNombre(),s.getDescripcion(),s.getPrecioBase(),s.getRequiereCotizacion(),s.getActivo(),s.getCategoria().getIdcategoria(),s.getCategoria().getNombre());}
}