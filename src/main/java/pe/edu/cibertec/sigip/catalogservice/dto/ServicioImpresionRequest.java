package pe.edu.cibertec.sigip.catalogservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ServicioImpresionRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
        String descripcion,

        @NotNull(message = "El precio base es obligatorio")
        @DecimalMin(value = "0.00", message = "El precio base no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El precio admite como máximo 2 decimales")
        BigDecimal precioBase,

        @NotNull(message = "Debe indicar si requiere cotización")
        Boolean requiereCotizacion,

        @NotNull(message = "La categoría es obligatoria")
        Integer idcategoria,

        /** Opcional: permite desactivar un servicio sin borrarlo. Si no se envía, se conserva. */
        Boolean activo,

        /** Si es true, el precio final se calcula con opciones configurables. */
        Boolean personalizable,

        @Size(max = 300, message = "La ruta de imagen no puede superar 300 caracteres")
        String imagenUrl
) {
    /** Mantiene compatibilidad con las pruebas y clientes que usan el contrato anterior. */
    public ServicioImpresionRequest(String nombre, String descripcion, BigDecimal precioBase,
                                    Boolean requiereCotizacion, Integer idcategoria, Boolean activo) {
        this(nombre, descripcion, precioBase, requiereCotizacion, idcategoria, activo, false, null);
    }
}
