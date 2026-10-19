# SIGIP Catalog Service

Microservicio de catálogo de productos de impresión para SIGIP. Java 25, Spring Boot, Spring Data JPA, MySQL y Eureka.

## Configuración
Ajusta usuario y contraseña de MySQL en `src/main/resources/application.properties`.
Puerto: 8094.

## Endpoints
- `GET /api/catalogo/productos`
- `GET /api/catalogo/productos/{id}`
- `POST /api/catalogo/productos`
- `PUT /api/catalogo/productos/{id}`
- `DELETE /api/catalogo/productos/{id}`
- `GET /api/catalogo/productos/health`

Ejemplo para crear producto:
```json
{
  "nombre": "Tarjetas de presentación",
  "categoria": "Tarjetas",
  "descripcion": "Tarjetas impresas a color",
  "precioBase": 35.00,
  "disponible": true
}
```

Requiere MySQL y Discovery Server activo en el puerto 8761. Este servicio expone el CRUD para el catálogo; el control de acceso administrativo debe integrarse con la seguridad común de SIGIP antes de producción.
