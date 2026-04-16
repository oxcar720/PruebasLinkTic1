<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  show:Boolean,
  editData:Object
})

const emit = defineEmits(['close','save'])

const form = reactive({
  sku:'',
  name:'',
  price:0,
  status:'ACTIVE'
})

watch(()=>props.editData,(val)=>{
  if(val){
    form.sku = val.sku || ''
    form.name = val.name || ''
    form.price = val.price || 0
    form.status = val.status || 'ACTIVE'
  }else{
    form.sku=''
    form.name=''
    form.price=0
    form.status='ACTIVE'
  }
},{immediate:true})

function guardar(){
  emit('save',{...form})
}
</script>

<template>
<div v-if="show" class="modal-bg">
<div class="modal card">

<h3>{{ editData ? 'Editar Producto' : 'Crear Producto' }}</h3>

<div class="grid">

<div class="field">
<label>SKU</label>
<input
v-model="form.sku"
:disabled="editData"
/>
</div>

<div class="field">
<label>Nombre</label>
<input v-model="form.name"/>
</div>

<div class="field">
<label>Precio</label>
<input
v-model.number="form.price"
type="number"
/>
</div>

<div class="field">
<label>Estado</label>
<select v-model="form.status">
<option value="ACTIVE">ACTIVE</option>
<option value="INACTIVE">INACTIVE</option>
</select>
</div>

</div>

<div class="actions">
<button class="btn btn-gray" @click="$emit('close')">
Cancelar
</button>

<button class="btn btn-green" @click="guardar">
Guardar
</button>
</div>

</div>
</div>
</template>

<style scoped>
.modal-bg{
position:fixed;
inset:0;
background:rgba(0,0,0,.75);
display:flex;
justify-content:center;
align-items:center;
z-index:5000;
}

.modal{
width:520px;
}

.grid{
display:grid;
gap:16px;
margin-top:20px;
}

.field{
display:grid;
gap:6px;
}

label{
font-size:13px;
font-weight:600;
color:#8df5a6;
}

.actions{
display:flex;
justify-content:flex-end;
gap:10px;
margin-top:20px;
}
</style>