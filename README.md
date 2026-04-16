## SPRING BOOT + VUE FULLSTACK (MICROSERVICES)

## Descripción

Proyecto FullStack basado en arquitectura de microservicios utilizando Spring Boot para el backend y Vue 3 para el frontend.

El sistema permite la gestión de productos, control de inventario desacoplado y flujo de compra con manejo de stock e idempotencia.

---

## Arquitectura

El sistema está compuesto por los siguientes módulos:

* products-service    : Gestión de productos (CRUD)
* inventory-service  : Manejo de inventario y compras
* frontend-vue       : Interfaz de usuario
* PostgreSQL         : Base de datos

Relación:

Frontend (Vue)
|
v
Products Service (8081)
|
v
Inventory Service (8082)
|
v
PostgreSQL

---

## Tecnologías Utilizadas

Backend:

* Java 17
* Spring Boot
* Spring Data JPA
* Gradle
* REST API
* PostgreSQL
* Resilience4j

Frontend:

* Vue 3
* Vite
* Axios
* SPA (Single Page Application)
* Diseño responsive

DevOps:

* Docker
* Docker Compose

---

## Funcionalidades Implementadas

Productos:

* CRUD completo
* Paginación
* Búsqueda
* Manejo de errores (409, 422)

Inventario:

* Stock desacoplado
* Consulta por producto
* Validación contra servicio de productos

Compra:

* Disminución de stock disponible
* Incremento de stock reservado
* Validación de disponibilidad

Idempotencia:

* Prevención de operaciones duplicadas mediante Idempotency-Key

Comunicación:

* Integración entre microservicios vía HTTP

Frontend:

* Interfaz moderna
* Spinner global reutilizable
* Notificaciones tipo toast
* Diseño responsive

---

## Ejecución con Docker

1. Construcción de artefactos

Ejecutar en cada servicio:

cd products-service
gradlew clean build -x test

cd ../inventory-service
gradlew clean build -x test

---

2. Variables de entorno

Archivo .env en la raíz del proyecto:

DB_USER=oxcar720_db
DB_PASSWORD=area_restringida2427DB

---

3. Ejecución del sistema

Desde la raíz del proyecto:

docker-compose up --build

---

## Endpoints

Products Service (puerto 8081):

GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}

---

Inventory Service (puerto 8082):

GET  /inventory/{productId}
POST /inventory/purchase

Ejemplo de request:

{
"productId": "UUID",
"quantity": 2
}

---

## Base de Datos

Se crean automáticamente las siguientes bases de datos:

* products_db
* inventory_db

Definidas en el archivo:

init.sql

#Proyecto desarrollado como prueba técnica FullStack.#
