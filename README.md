# SIGIP Catalog Service

Microservicio del catálogo de SIGIP: categorías y servicios de impresión.

## Requisitos
- JDK 21 o compatible.
- Maven 3.9+.
- MySQL.
- SIGIP-Common instalado en el repositorio local de Maven.
- Eureka Server (opcional para probar el servicio aislado).

## Preparar SIGIP-Common
Desde el repositorio local de `SIGIP-Common`, ejecutar:

```powershell
mvn clean install
```

## Compilar el catálogo
Después de actualizar este repositorio:

```powershell
mvn clean verify
```

## Configuración local
Por defecto usa la base de datos `sigip_catalog` en `localhost:3306`, usuario `root` y contraseña vacía. Se puede configurar mediante `DB_URL`, `DB_USER`, `DB_PASSWORD`, `EUREKA_URL` y `JWT_SECRET`.

La clave JWT incluida por defecto es solo para desarrollo local. En cualquier entorno compartido o desplegado, configura `JWT_SECRET` con una clave segura.

## Endpoints
- `GET /api/v1/categorias`
- `GET /api/v1/categorias/{id}`
- `POST /api/v1/categorias` (rol ADMIN)
- `PUT /api/v1/categorias/{id}` (rol ADMIN)
- `DELETE /api/v1/categorias/{id}` (rol ADMIN)
- `GET /api/v1/servicios`
- `GET /api/v1/servicios/activos`
- `GET /api/v1/servicios/{id}`
- `POST /api/v1/servicios` (rol ADMIN)
- `PUT /api/v1/servicios/{id}` (rol ADMIN)
- `DELETE /api/v1/servicios/{id}` (rol ADMIN)

Las lecturas del catálogo utilizan caché Caffeine y las consultas de servicios recuperan su categoría mediante `JOIN FETCH` para reducir consultas N+1.
