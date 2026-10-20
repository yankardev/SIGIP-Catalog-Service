# SIGIP Catalog Service

Microservicio de catálogo de productos de impresión para SIGIP. Utiliza Java 25, Spring Boot 3.5.6, Spring Data JPA, MySQL y Eureka.

## Requisitos

- JDK 25 configurado en IntelliJ IDEA.
- MySQL disponible en localhost:3306.
- Discovery Server disponible en localhost:8761 (recomendado para la integración completa).

## Configuración local

Revisa `src/main/resources/application.properties` y adapta estas propiedades a tu MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sigip_catalogo?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Lima
spring.datasource.username=root
spring.datasource.password=
```

La aplicación escucha en el puerto **8094**. Hibernate crea/actualiza las tablas con `spring.jpa.hibernate.ddl-auto=update`. En una base vacía se insertan cuatro productos de demostración; si ya hay productos, no se sobrescriben.

## Ejecutar en IntelliJ IDEA

1. Abre la carpeta `SIGIP-Catalog-Service`.
2. Espera a que IntelliJ importe el proyecto Maven y descargue las dependencias.
3. En **Project Structure**, selecciona JDK 25 para el proyecto y para Maven.
4. Confirma las credenciales de MySQL en `application.properties`.
5. Ejecuta `CatalogServiceApplication`.
6. Comprueba `http://localhost:8094/api/catalogo/productos/health`.

Si Eureka no está levantado, el servicio puede mostrar advertencias de conexión al registro. Para la prueba local del CRUD, asegúrate primero de que MySQL esté activo.

## Endpoints REST

| Método | Ruta | Acción |
|---|---|---|
| GET | `/api/catalogo/productos` | Listar todos los productos |
| GET | `/api/catalogo/productos?disponibles=true` | Listar disponibles |
| GET | `/api/catalogo/productos?categoria=Tarjetas` | Filtrar por categoría |
| GET | `/api/catalogo/productos/{id}` | Consultar un producto |
| POST | `/api/catalogo/productos` | Crear producto |
| PUT | `/api/catalogo/productos/{id}` | Actualizar producto |
| DELETE | `/api/catalogo/productos/{id}` | Eliminar producto |
| GET | `/api/catalogo/productos/health` | Verificar estado |

Ejemplo de JSON para crear un producto:

```json
{
  "nombre": "Tarjetas de presentación",
  "categoria": "Tarjetas",
  "descripcion": "Tarjetas impresas a color",
  "precioBase": 35.00,
  "disponible": true
}
```

## Pruebas

Desde IntelliJ, ejecuta la clase `ProductoControllerTest`. Las pruebas de controlador verifican el endpoint de salud y el rechazo de nombre vacío y precio negativo.

También puedes probar con curl:

```bash
curl http://localhost:8094/api/catalogo/productos/health
curl http://localhost:8094/api/catalogo/productos
```

## Alcance y seguridad

El CRUD y la validación de campos están implementados. El control de permisos para operaciones administrativas debe aplicarse al integrar el servicio con la autenticación y el API Gateway; no debe considerarse listo para producción aislado.
