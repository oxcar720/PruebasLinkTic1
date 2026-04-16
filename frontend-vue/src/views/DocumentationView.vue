<template>
<div>

<h2 class="section-title">Documentación Técnica</h2>

<div class="card section-block">
    <h3>Autor</h3>
    <p>Oscar Alejandro Rodríguez Contreras<br>
    Desarrollador FullStack</p>
    <p>Pruebas de Desarrollo para LinkTick</p>
    <img src="https://linktic.com/wp-content/uploads/2025/06/LogoLinktic-header.svg" style="background-color: white; width: 350px;">
</div>

<div class="card section-block">
    <h3>Resumen del Proyecto</h3>

    <p>Aplicación FullStack basada en microservicios, este sistema implementa idempotencia en compras para evitar duplicidad
    de transacciones en escenarios de reintento.</p>
    <br>

    <ul class="doc-list">
    <li><b>products-service</b> → CRUD de productos</li>
    <li><b>inventory-service</b> → stock, compras e idempotencia</li>
    <li><b>frontend-vue</b> → panel grafico, administración y visualización</li>
    <li><b>PostgreSQL</b> → base de datos</li>
    </ul>
</div>

<div class="card section-block">
    <h3>Tecnologías Utilizadas</h3>

    <div class="grid-2">
        <div>
            <h4>Backend</h4>
            <ul class="doc-list">
                <li>Java 17</li>
                <li>Spring Boot</li>
                <li>Spring Data JPA</li>
                <li>Gradle</li>
                <li>REST API</li>
                <li>PostgreSQL</li>
            </ul>
        </div>

        <div>
            <h4>Frontend</h4>
            <ul class="doc-list">
                <li>Vue 3</li>
                <li>Vite</li>
                <li>Axios</li>
                <li>SPA</li>
                <li>Responsive UI</li>
            </ul>
        </div>

    </div>
</div>

<div class="card section-block">
    <h3>API Productos</h3>

    <table>
        <thead>
            <tr>
                <th>Método</th>
                <th>Ruta</th>
                <th>Descripción</th>
            </tr>
        </thead>
    <tbody>

        <tr>
            <td>GET</td>
            <td>/products</td>
            <td>Listado paginado</td>
        </tr>
        <tr>
            <td colspan="3">
                <details>
                    <summary>Ejemplo</summary>
                    <pre>
    GET /products?search=lap&status=ACTIVE

    Respuesta:
    {
    "content": 
        [
            {
                "id": "2c0d2614-847a-4441-a02c-8d1c019c0974",
                "sku": "SKU-6",
                "name": "Laptop25H2",
                "price": 800000.00,
                "status": "ACTIVE"
            },...
        ],
        "empty": false,
        "first": true,
        "last": true,
        "number": 0,
        "numberOfElements": 2,
        "pageable": {
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "unpaged": false
        },
        "size": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "totalElements": 2,
        "totalPages": 1
    }
                    </pre>
                </details>
            </td>
        </tr>

        <tr>
            <td>POST</td>
            <td>/products</td>
            <td>Crear producto</td>
        </tr>
        <tr>
            <td colspan="3">
            <details>
            <summary>Ejemplo</summary>
            <pre>
    POST /products
    {
        "sku": "ABCD-720",
        "name": "Procesador intel core i7",
        "price": 1200
    }

    Respuesta:
    {
        "id": "0f454d24-f29d-4f90-9f06-d825946609c3",
        "sku": "ABCD-720",
        "name": "Procesador intel core i7",
        "price": 1200,
        "status": "ACTIVE"
    }
            </pre>
            </details>
            </td>
        </tr>

        <tr>
            <td>PUT</td>
            <td>/products/{id}</td>
            <td>Actualizar producto</td>
        </tr>
        <tr>
            <td colspan="3">
                <details>
                    <summary>Ejemplo</summary>
                    <pre>
    PUT /products/{id}
    {
        "name": "Laptop",
        "price": 1200,
        "status": "ACTIVE"
    }

    Respuesta:
    {
        "id": "d866bef9-ae57-4f54-b895-bedeb2cf49bf",
        "sku": "ABC1234",
        "name": "Laptop",
        "price": 1200,
        "status": "ACTIVE"
    }
                    </pre>
                </details>
            </td>
        </tr>

        <tr>
            <td>DELETE</td>
            <td>/products/{id}</td>
            <td>Eliminar producto</td>
        </tr>
        <tr>
            <td colspan="3">
                <details>
                    <summary>Ejemplo</summary>
                    <pre>
    DELETE /products/{id}

    Respuesta: status 200
                    </pre>
                </details>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<div class="card section-block">
    <h3>API Inventario</h3>

    <table>
        <thead>
            <tr>
                <th>Método</th>
                <th>Ruta</th>
                <th>Descripción</th>
            </tr>
        </thead>

        <tbody>

            <tr>
                <td>GET</td>
                <td>/inventory</td>
                <td>Listado de stock</td>
            </tr>
            <tr>
                <td colspan="3">
                    <details>
                        <summary>Ejemplo</summary>
                        <pre>
    GET /inventory?page=0&size=20
    Respuesta:
    {
        "content": [
            {
                "productId": "44444444-4444-4444-4444-444444444444",
                "available": 5,
                "reserved": 0
            },
            ...
        ]
        "empty": false,
        "first": true,
        "last": true,
        "number": 0,
        "numberOfElements": 6,
        "pageable": {
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 20,
            "paged": true,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "unpaged": false
        },
        "size": 20,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "totalElements": 6,
        "totalPages": 1
    }
                        </pre>
                    </details>
                </td>
            </tr>

            <tr>
                <td>PUT</td>
                <td>/inventory/{id}</td>
                <td>Actualizar stock</td>
            </tr>

            <tr>
                <td colspan="3">
                    <details>
                        <summary>Ejemplo</summary>
                        <pre>
    PUT /inventory/{id}
    Respuesta:
    {
        "available":10
    }
                        </pre>
                    </details>
                </td>
            </tr>

            <tr>
                <td>POST</td>
                <td>/inventory/purchase</td>
                <td>Comprar producto</td>
            </tr>

            <tr>
                <td colspan="3">
                    <details>
                        <summary>Ejemplo</summary>
                        <pre>
    POST /inventory/purchase
    Header:
    Idempotency-Key: (clave random como: abc-123)
    {
        "productId":"uuid",
        "quantity":2
    }
    Respuesta:
    {
        "productId": "uuid",
        "available": 3,
        "reserved": 2
    }
    
                        </pre>
                    </details>
                </td>
            </tr>

            <tr>
                <td>POST</td>
                <td>/inventory/sync</td>
                <td>Sincronizar productos</td>
            </tr>
            <tr>
                <td colspan="3">
                    <details>
                        <summary>Ejemplo</summary>
                        <pre>
    POST /inventory/sync

    Respuesta: Sincronización completada. Nuevos: 1 | Eliminados: 0
                        </pre>
                    </details>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<!-- FUNCIONALIDADES -->
<div class="card section-block">
<h3>Funcionalidades Implementadas</h3>

<ul class="doc-list">
<li>CRUD completo productos</li>
<li>Paginación y búsqueda</li>
<li>Stock desacoplado</li>
<li>Compra con Idempotency-Key</li>
<li>Sincronización entre servicios</li>
<li>Spinner global reutilizable</li>
<li>Mensajería toast</li>
<li>UI moderna responsive</li>
</ul>
</div>

</div>
</template>

<style scoped>
.section-block{
margin-bottom:24px;
}

.grid-2{
display:grid;
grid-template-columns:1fr 1fr;
gap:28px;
}

.doc-list{
padding-left:18px;
display:grid;
gap:8px;
}

details{
margin:8px 0;
}

summary{
cursor:pointer;
color:#16c784;
font-weight:700;
}

pre{
background:#0c0f13;
padding:14px;
border-radius:12px;
overflow:auto;
font-size:13px;
color:#ddd;
white-space:pre-wrap;
}

h4{
margin-bottom:10px;
color:#16c784;
}

@media(max-width:900px){
.grid-2{
grid-template-columns:1fr;
}
}
</style>