package pe.sigip.catalog.dto;

import pe.sigip.catalog.model.Categoria;

public record CategoriaResponse(
        Integer idcategoria,
        String nombre,
        String descripcion,
        Boolean activo
) {

    public static CategoriaResponse from(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getIdcategoria(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getActivo()
        );
    }
}
