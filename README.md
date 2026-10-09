# Proyecto de microservicios : microservicio de gestión de transporte y pedidos.

> En este repositoro se guarda los archivos de una api desarrollada con Springboot y Java, esta api pertenece a un sistema de micro servicios del siguiente repositorio : https://github.com/gabriel1-du/Optativo-Desarollo-Java-Springboot-Cloud

## Tecnologías Utilizadas

* **Lenguaje:** Java
* **Frameworks / Librerías:** Springboot
* **Base de datos:** MySQL 
* **Otras herramientas:** Git, Lombok

---

## Diagrama de la base de datos 

El script de la base de datos se encuentra en `Base de datos/transporte_dba.sql` y contiene las siguientes tablas:

* **EMPRESA_TRANSPORTE**: Empresas de transporte registradas.
* **TRANSPORTISTAS**: Transportistas asociados a una empresa (FK a EMPRESA_TRANSPORTE).
* **PEDIDOS**: Pedidos asignados a un transportista, con referencias lógicas externas a usuarios y boletas.

![Diagrama MER](<Base de datos/MerTransportes.png>)

---

## Arquitectura y Flujo de Datos

El proyecto implementa una arquitectura en capas basada en separación de responsabilidades:

```
[ Cliente / Frontend ]
         │ ▲
 (HTTP)   ▼ │ (JSON / DTO)
   ┌─────────────┐
   │ Controller  │ ──► Expone los endpoints REST y gestiona la entrada/salida HTTP
   └─────────────┘
         │ ▲
  (DTOs)  ▼ │
   ┌─────────────┐
   │   Service   │ ──► Interfaz: define el contrato de la lógica de negocio
   └─────────────┘
         │ ▲
         ▼ │
   ┌─────────────┐
   │ ServiceImpl │ ──► Implementación: ejecuta reglas de negocio, validaciones y mapeo DTO ◄─► Modelo
   └─────────────┘
         │ ▲
 (Model)  ▼ │
   ┌─────────────┐
   │ Repository  │ ──► Interfaz Spring Data JPA: acceso y operaciones sobre la base de datos
   └─────────────┘
         │ ▲
 (SQL)    ▼ │
   ┌─────────────┐
   │   Database  │
   └─────────────┘
```

* DTO: Objeto transversal utilizado para transferir datos limpios entre capas sin exponer el Modelo o facilitar el cuerpo en las peticiones.

---

## Endpoints

### Empresa de Transporte (`/api/EmpresaTransporteApi`)

| Método | Path | Descripción |
|--------|------|-------------|
| GET | `/` | Lista todas las empresas |
| GET | `/{id_empresa}` | Empresa por id |
| POST | `/` | Crea una empresa |
| PUT | `/{id_empresa}` | Actualiza una empresa |
| DELETE | `/{id_empresa}` | Elimina una empresa |

### Transportistas (`/api/TransportistasApi`)

| Método | Path | Descripción |
|--------|------|-------------|
| GET | `/` | Lista todos los transportistas |
| GET | `/{id_transportista}` | Transportista por id |
| POST | `/` | Crea un transportista |
| PUT | `/{id_transportista}` | Actualiza un transportista |
| DELETE | `/{id_transportista}` | Elimina un transportista |

### Pedidos (`/api/PedidosApi`)

| Método | Path | Descripción |
|--------|------|-------------|
| GET | `/` | Lista todos los pedidos |
| GET | `/{id_pedido}` | Pedido por id |
| POST | `/` | Crea un pedido |
| PUT | `/{id_pedido}` | Actualiza estado de entrega de un pedido |
| DELETE | `/{id_pedido}` | Elimina un pedido |

---

## Automatización de fechas y atributos a través de DTO y Mapper

El microservicio utiliza la combinación de **DTOs** y **Mappers** para automatizar la gestión de fechas, atributos calculados y validaciones externas sin ensuciar la lógica del servicio.

### ¿Cómo funciona?

1. **Entrada simplificada (savePedidoDTO):** El cliente no necesita enviar fechas complejas ni objetos anidados. Para crear un pedido, basta con enviar los componentes de la fecha por separado:

   ```json
   {
       "id_usuario": 1,
       "id_boleta": 1,
       "id_transportista": 2,
       "anio": 2026,
       "mes": 10,
       "dia": 10,
       "hora": 14
   }
   ```

2. **El Mapper construye la entidad:** `PedidoMapper.EntitytoSaveDTO()` toma esos valores simples, valida sus rangos (hora 0–23, mes 1–12) y construye automáticamente el `LocalDateTime fecha_de_envio` junto con `entregado = false` por defecto. La entidad se crea internamente sin que el cliente deba formatear nada.

3. **Corroboración automática de entidades externas:** Antes de guardar, el Mapper consulta automáticamente las APIs externas (usuario vía `UsuarioClient`, boleta vía `RestClient`) para verificar que los IDs existan. Si no existen, se lanza una excepción con mensaje descriptivo.

4. **Salida enriquecida (getPedidosDTO):** Al consultar, el mismo Mapper combina datos de varias fuentes:
   - Formatea `LocalDateTime` → `String` legible (`dd/MM/yyyy HH:mm`).
   - Construye `nombres` y `apellidos` concatenando los campos del usuario externo.
   - Construye `rut_completo` desde la API de usuarios.
   - Si `fecha_de_entrega` es null, devuelve `"Pendiente"` en lugar de un valor vacío.

5. **PUT inteligente (putPedidoDTO):** Para marcar un pedido como entregado, el cliente envía únicamente:

   ```json
   { "entregado": 1 }
   ```

   El Mapper detecta el estado y **setea automáticamente `fecha_de_entrega` con la fecha y hora actuales** (`LocalDateTime.now()`) en el momento en que se genera el PUT. No es necesario enviar la fecha manualmente.

### Beneficios

* El cliente nunca formatea fechas ni construye objetos anidados para FKs.
* El servicio permanece limpio: toda la transformación y validación vive en el Mapper.
* Los aliases de JSON (`@JsonAlias`) permiten aceptar variantes de nombres sin romper la API.
* La lógica de "si no viene en el JSON, no se modifica" en el PUT evita sobrescribir datos con valores nulos.

---

# Dependencias (pom)

* Spring Boot Starter Data JPA
* Spring Boot Starter WebMVC
* MySQL Connector/J
* Project Lombok
* Jackson (incluido en WebMVC — usado para `@JsonAlias` y `@JsonPropertyOrder`)
* Spring Boot Starter Data JPA Test
* Spring Boot Starter WebMVC Test
