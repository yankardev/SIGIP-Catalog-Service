package pe.sigip.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        String detalles = exception.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return respuesta(HttpStatus.BAD_REQUEST, "Datos inválidos", detalles, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(
            HttpMessageNotReadableException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.BAD_REQUEST, "JSON inválido",
            "Revisa los tipos y el formato de los campos enviados.", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridad(
            DataIntegrityViolationException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.CONFLICT, "Conflicto de datos",
            "La operación no puede completarse por una restricción de la base de datos.", request);
    }

    private ResponseEntity<Map<String, Object>> respuesta(
            HttpStatus status, String error, String mensaje, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("fecha", OffsetDateTime.now(ZoneId.of("America/Lima")).toString());
        body.put("estado", status.value());
        body.put("error", error);
        body.put("mensaje", mensaje == null || mensaje.isBlank() ? error : mensaje);
        body.put("ruta", request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
