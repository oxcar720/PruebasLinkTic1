<script setup>
import { ref,onMounted } from 'vue'
import {
showLoading,
hideLoading,
mostrar_mensaje
} from '../stores/ui'

import {
getInventory,
syncInventory,
updateInventory,
purchaseInventory
} from '../services/inventory'

import InventoryStockModal from '../components/Inventory/InventoryStockModal.vue'
import PurchaseModal from '../components/Inventory/PurchaseModal.vue'

const rows = ref([])
const page = ref(0)
const totalPages = ref(1)

const stockModal = ref(false)
const purchaseModal = ref(false)

const current = ref(null)

async function loadData(){
  try{
    showLoading()

    const data = await getInventory({
      page:page.value,
      size:20
    })

    rows.value = data.content
    totalPages.value = data.totalPages

  }finally{
    hideLoading()
  }
}

async function sincronizar(){
  showLoading()
  await syncInventory()
  mostrar_mensaje('Productos sincronizados',0)
  await loadData()
}

function editar(row){
  current.value = row
  stockModal.value = true
}

function comprar(row){
  current.value = row
  purchaseModal.value = true
}

async function guardarStock(data){
  showLoading()

  await updateInventory(
    current.value.productId,
    data
  )

  stockModal.value=false
  mostrar_mensaje('Stock actualizado',0)
  await loadData()
}

async function confirmarCompra(data){
  try{
    showLoading()

    await purchaseInventory(
      data.key,
      {
        productId: current.value.productId,
        quantity: data.quantity
      }
    )

    purchaseModal.value = false
    mostrar_mensaje('Compra realizada',0)

    await loadData()

  }catch(e){

    mostrar_mensaje(
      e?.response?.data?.errors?.[0]?.detail || 'Compra rechazada',
      2
    )

  }finally{
    hideLoading()
  }
}

onMounted(loadData)
</script>

<template>
<div>

<h2 class="section-title">Inventario</h2>

<div class="card">

<div style="margin-bottom:20px;">
<button class="btn btn-green" @click="sincronizar">
Sincronizar productos
</button>
</div>

<table>
<thead>
<tr>
<th>Producto ID</th>
<th>Disponible</th>
<th>Reservado</th>
<th>Acciones</th>
</tr>
</thead>

<tbody>
<tr v-for="r in rows" :key="r.productId">

<td>{{ r.productId }}</td>
<td>{{ r.available }}</td>
<td>{{ r.reserved }}</td>

<td style="display:flex;gap:8px;">
<button class="btn btn-gray" @click="editar(r)">
Editar
</button>

<button class="btn btn-green" @click="comprar(r)">
Comprar
</button>
</td>

</tr>
</tbody>
</table>

<div style="margin-top:20px;display:flex;gap:10px;align-items:center;">

<button class="btn btn-gray"
:disabled="page===0"
@click="page--;loadData()">
Anterior
</button>

<span>
Página {{ page+1 }} de {{ totalPages }}
</span>

<button class="btn btn-gray"
:disabled="page+1>=totalPages"
@click="page++;loadData()">
Siguiente
</button>

</div>

</div>
</div>
<InventoryStockModal
:show="stockModal"
:row="current"
@close="stockModal=false"
@save="guardarStock"
/>

<PurchaseModal
:show="purchaseModal"
:row="current"
@close="purchaseModal=false"
@buy="confirmarCompra"
/>
</template>