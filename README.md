> 🚧 En desarrollo

# Microservicio de Órdenes de Trabajo

## Descripción
Este microservicio se encarga de la gestión del ciclo de vida de las órdenes de trabajo, desde su creación hasta su cierre. Proporciona funcionalidades para la creación, consulta, actualización y eliminación de órdenes de trabajo, así como la gestión de los estados y prioridades asociados.

## Funcionalidades Principales
- Creación y edición de órdenes de trabajo
- Consulta y filtrado de órdenes por múltiples criterios
- Gestión de estados básicos del ciclo de vida
- Validación de datos de órdenes de trabajo
- Exportación de datos en múltiples formatos

## Estructura de Base de Datos

### Tabla: `orden`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_orden | INT | Identificador único (PK) |
| id_cliente | INT | Cliente asociado (FK) |
| id_tipo | INT | Tipo de orden (FK) |
| id_zona | INT | Zona geográfica (FK) |
| id_prioridad | INT | Nivel de prioridad (FK) |
| codigo | VARCHAR(50) | Código único de la orden |
| descripcion | TEXT | Descripción detallada del problema |
| fecha_registro | DATETIME | Fecha de creación de la orden |
| estado_actual | VARCHAR(50) | Estado actual de la orden |

### Tabla: `tipo_orden`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_tipo | INT | Identificador único (PK) |
| nombre | VARCHAR(100) | Nombre del tipo de orden |
| descripcion | TEXT | Descripción del tipo de orden |

### Tabla: `cliente`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_cliente | INT | Identificador único (PK) |
| nombre | VARCHAR(100) | Nombre del cliente |
| direccion | VARCHAR(255) | Dirección física del cliente |
| telefono | VARCHAR(20) | Teléfono de contacto |
| email | VARCHAR(100) | Correo electrónico |
| codigo_cliente | VARCHAR(50) | Código único del cliente |

### Tabla: `zona`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_zona | INT | Identificador único (PK) |
| nombre | VARCHAR(100) | Nombre de la zona |
| codigo | VARCHAR(20) | Código de la zona |
| descripcion | TEXT | Descripción de la zona geográfica |

### Tabla: `prioridad`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_prioridad | INT | Identificador único (PK) |
| nombre | VARCHAR(50) | Nombre del nivel de prioridad |
| descripcion | TEXT | Descripción del nivel |
| tiempo_objetivo | INT | Tiempo objetivo de resolución (minutos) |
| color_prioridad | VARCHAR(20) | Código de color para interfaz |

## Endpoints API

### Órdenes de Trabajo
- `GET /api/ordenes` - Listar órdenes de trabajo
- `GET /api/ordenes/{id}` - Obtener orden por ID
- `POST /api/ordenes` - Crear nueva orden
- `PUT /api/ordenes/{id}` - Actualizar orden
- `DELETE /api/ordenes/{id}` - Eliminar orden
- `PATCH /api/ordenes/{id}/estado` - Actualizar estado de la orden
- `GET /api/ordenes/filtro` - Filtrar órdenes por criterios
- `GET /api/ordenes/export/{formato}` - Exportar órdenes (CSV, PDF, Excel)

### Clientes
- `GET /api/clientes` - Listar clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `GET /api/clientes/{id}/ordenes` - Listar órdenes de un cliente

### Tipos de Orden
- `GET /api/tipos-orden` - Listar tipos de orden
- `POST /api/tipos-orden` - Crear nuevo tipo
- `PUT /api/tipos-orden/{id}` - Actualizar tipo

### Zonas
- `GET /api/zonas` - Listar zonas
- `GET /api/zonas/{id}/ordenes` - Listar órdenes por zona

### Prioridades
- `GET /api/prioridades` - Listar niveles de prioridad
- `GET /api/prioridades/{id}/ordenes` - Listar órdenes por prioridad


## Integración con GraphQL

Este microservicio también soporta GraphQL para consultas y mutaciones más flexibles. GraphQL permite a los clientes especificar exactamente qué datos necesitan, reduciendo la sobrecarga de datos innecesarios.

### Endpoint de GraphQL
- `POST /graphql` - Punto de entrada para todas las consultas y mutaciones de GraphQL.

### Ejemplo de Esquema
```graphql
type Query {
    orden(id: Int!): Orden
    ordenes(filtro: OrdenFiltro): [Orden]
    clientes: [Cliente]
    zonas: [Zona]
}

type Mutation {
    crearOrden(input: CrearOrdenInput!): Orden
    actualizarOrden(id: Int!, input: ActualizarOrdenInput!): Orden
}

type Orden {
    id_orden: Int
    id_cliente: Int
    id_tipo: Int
    id_zona: Int
    id_prioridad: Int
    descripcion: String
    estado_actual: String
    fecha_registro: String
}

input CrearOrdenInput {
    id_cliente: Int!
    id_tipo: Int!
    id_zona: Int!
    id_prioridad: Int!
    descripcion: String!
}

input ActualizarOrdenInput {
    descripcion: String
    estado_actual: String
}
```

Ejemplo de consulta

```
query {
    orden(id: 1) {
        id_orden
        descripcion
        estado_actual
        fecha_registro
    }
}
```

Ejemplo de Mutación

```
mutation {
    crearOrden(input: {
        id_cliente: 1,
        id_tipo: 2,
        id_zona: 3,
        id_prioridad: 1,
        descripcion: "Falla en router principal"
    }) {
        id_orden
        descripcion
    }
}
```

## Configuración

### Variables de Entorno
```
MS_ORDENES_PORT=8081
MS_ORDENES_DB_HOST=localhost
MS_ORDENES_DB_PORT=3306
MS_ORDENES_DB_NAME=telconova_ordenes
MS_ORDENES_DB_USER=usuario
MS_ORDENES_DB_PASSWORD=contraseña
MS_USUARIOS_URL=http://ms-usuarios:8080
KAFKA_BOOTSTRAP_SERVERS=kafka:9092
SWAGGER_PRODUCTION_SERVER_URL=https://ejemplo.com
```

### Dependencias Principales
- Spring Boot
- Base de datos PostgreSQL
- Cliente RabbitMQ para mensajería
- Cliente HTTP para comunicación con otros microservicios

## Eventos Publicados
- `orden.created` - Cuando se crea una nueva orden
- `orden.updated` - Cuando se actualiza una orden
- `orden.estado.changed` - Cuando cambia el estado de una orden
- `orden.deleted` - Cuando se elimina una orden
- `orden.prioridad.changed` - Cuando cambia la prioridad

## Eventos Consumidos
- `cliente.created` - Para registrar nuevos clientes
- `tecnico.assigned` - Para actualizar asignación de técnicos
- `orden.completed` - Para marcar órdenes como completadas

## Métricas y Monitoreo
- Número de órdenes creadas por día
- Tiempo promedio de resolución
- Órdenes por estado/prioridad/zona
- Tasa de cumplimiento de SLA

## Procesos Batch
- Actualización automática de estados por tiempos vencidos
- Generación de reportes diarios/semanales
- Limpieza de órdenes antiguas

## Despliegue
```bash
# Construir la imagen Docker
docker build -t telconova/ms-ordenes:latest .

# Ejecutar el contenedor
docker run -d --name ms-ordenes \
  -p 8081:8081 \
  --env-file .env \
  telconova/ms-ordenes:latest
```

## Pruebas
```bash
# Ejecutar pruebas unitarias
./mvnw test

# Ejecutar pruebas de integración
./mvnw integration-test

# Ejemplo de creación de orden mediante curl
curl -X POST http://localhost:8081/api/ordenes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "id_cliente": 1,
    "id_tipo": 2,
    "id_zona": 3,
    "id_prioridad": 1,
    "descripcion": "Falla en router principal"
  }'
```

### Ejecución local

Una vez creado el archivo .env ejecutar el siguiente comando:

```bash
export $(cat .env | xargs)
```

Luego ejecute la instalacion de paquetes y corra el proyecto.

```bash
mvn clean install
mvn spring-boot:run
```
---

#### Docker en devcontainer

Construye la imagen de docker:

```bash
docker build -t telconova/ms-workorder:latest .
```

Corre el contenedor con tus variables de entorno:
```bash
docker run -d --name ms-workorder   --network=telconova-supportsuite-workorder-service_devcontainer_devcontainer-network   -p 8080:8080   --env-file .env   telconova/ms-workorder:latest
```

En caso de problemas algunos comandos utiles son:
```bash
docker logs ms-tracking
docker network ls
```