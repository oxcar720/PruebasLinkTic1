<script setup>
import { ref,onMounted } from 'vue'
import { showLoading, hideLoading, mostrar_mensaje } from '../stores/ui'
import { getProducts, createProduct, updateProduct, deleteProduct } from '../services/products'

import ProductModal from '../components/Products/ProductModal.vue'
import ConfirmDelete from '../components/Products/ConfirmDelete.vue'

const rows = ref([])
const page = ref(0)
const totalPages = ref(1)

const search = ref('')
const status = ref('')
const sort = ref('createdAt,desc')

const modal = ref(false)
const editData = ref(null)

const deleteModal = ref(false)
const deleteId = ref(null)

async function loadData(){
  try{
    showLoading()

    const params = {
        page: page.value,
        size: 20,
        sort: sort.value
    }

    if (search.value?.trim()) {
        params.search = search.value.trim()
    }

    if (status.value?.trim()) {
        params.status = status.value
    }

    const data = await getProducts(params);
    rows.value = data.content;
    totalPages.value = data.totalPages;
    mostrar_mensaje('Productos cargados correctamente',0);
    
  }catch(e){
    console.log("Error: ", e);
    mostrar_mensaje('Error cargando productos',2)
  }finally{
    hideLoading()
  }
}

function nuevo(){
  editData.value = null
  modal.value = true
}

function editar(row){
  editData.value = row
  modal.value = true
}

async function guardar(data){
  try{
    showLoading()

    if(editData.value){
      await updateProduct(editData.value.id,data)
      mostrar_mensaje('Producto actualizado',0)
    }else{
      await createProduct(data)
      mostrar_mensaje('Producto creado',0)
    }

    modal.value = false
    loadData()

  }catch(e){
    mostrar_mensaje('Error guardando producto',2)
  }finally{
    hideLoading()
  }
}

function askDelete(id){
  deleteId.value = id
  deleteModal.value = true
}

async function eliminar(){
  try{
    showLoading()
    await deleteProduct(deleteId.value)
    mostrar_mensaje('Producto eliminado',0)
    deleteModal.value=false
    loadData()
  }catch(e){
    mostrar_mensaje('Error eliminando',2)
  }finally{
    hideLoading()
  }
}

onMounted(loadData)
</script>

<template>
<div>

<h2 class="section-title">Gestión de Productos</h2>

<div class="card">

<div style="display:flex;gap:10px;margin-bottom:20px;flex-wrap:wrap;">

<input v-model="search" placeholder="Buscar..." style="max-width:220px"/>

<select v-model="status" style="max-width:180px">
<option value="">Todos</option>
<option value="ACTIVE">ACTIVE</option>
<option value="INACTIVE">INACTIVE</option>
</select>

<select v-model="sort" style="max-width:220px">
<option value="price,asc">Precio ASC</option>
<option value="price,desc">Precio DESC</option>
<option value="createdAt,desc">Recientes</option>
</select>

<button class="btn btn-gray" @click="loadData">Buscar</button>

<button class="btn btn-green" @click="nuevo">
+ Crear
</button>

</div>

<table>
<thead>
<tr>
<th>SKU</th>
<th>Nombre</th>
<th>Precio</th>
<th>Status</th>
<th>Acciones</th>
</tr>
</thead>

<tbody>

<tr v-for="r in rows" :key="r.id">
<td>{{ r.sku }}</td>
<td>{{ r.name }}</td>
<td>{{ r.price }}</td>
<td>{{ r.status }}</td>
<td style="display:flex;gap:8px;">
<button class="btn btn-gray" @click="editar(r)">Editar</button>
<button class="btn btn-red" @click="askDelete(r.id)">Eliminar</button>
</td>
</tr>

</tbody>
</table>

<div style="margin-top:20px;display:flex;gap:10px;align-items:center;">
<button class="btn btn-gray" :disabled="page===0" @click="page--;loadData()">Anterior</button>

<span>Página {{ page + 1 }} de {{ totalPages }}</span>

<button class="btn btn-gray" :disabled="page+1>=totalPages" @click="page++;loadData()">Siguiente</button>
</div>

</div>

<ProductModal
:show="modal"
:editData="editData"
@close="modal=false"
@save="guardar"
/>

<ConfirmDelete
:show="deleteModal"
@close="deleteModal=false"
@confirm="eliminar"
/>

</div>
</template>