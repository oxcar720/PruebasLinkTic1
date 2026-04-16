<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  show:Boolean,
  row:Object
})

const emit = defineEmits(['close','save'])

const form = reactive({
  available:0
})

watch(()=>props.row,(val)=>{
  if(val){
    form.available = val.available
  }
},{immediate:true})

function guardar(){
  emit('save',{
    available:Number(form.available)
  })
}
</script>

<template>
<div v-if="show" class="modal-bg">

<div class="modal card">

<h3>Actualizar Stock</h3>

<p class="sub">{{ row?.productId }}</p>

<div class="grid">

<div class="field">
<label>Nuevo stock disponible</label>
<input
v-model.number="form.available"
type="number"
/>
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
align-items:center;
justify-content:center;
z-index:5000;
}
.modal{width:500px;}
.grid{display:grid;gap:14px;margin-top:20px;}
.actions{
display:flex;
justify-content:flex-end;
gap:10px;
margin-top:20px;
}
.sub{
font-size:12px;
opacity:.7;
margin-top:8px;
word-break:break-all;
}
</style>