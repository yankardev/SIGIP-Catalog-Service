package pe.sigip.catalog.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ServicioImpresionRequest(
 @NotBlank(message="El nombre es obligatorio") @Size(max=150) String nombre,
 @Size(max=500) String descripcion,
 @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) BigDecimal precioBase,
 @NotNull Boolean requiereCotizacion,
 @NotNull Integer idcategoria,
 Boolean activo) {}