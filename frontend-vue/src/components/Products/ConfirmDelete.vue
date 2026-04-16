<script setup>
defineProps({
show:Boolean
})

defineEmits(['close','confirm'])
</script>

<template>
<div v-if="show" class="modal-bg">

<div class="card modal">
<h3>Eliminar producto</h3>
<p style="margin-top:10px;">¿Deseas eliminar este registro?</p>

<div class="actions">
<button class="btn btn-gray" @click="$emit('close')">Cancelar</button>
<button class="btn btn-red" @click="$emit('confirm')">Eliminar</button>
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
width:420px;
}

.actions{
display:flex;
justify-content:flex-end;
gap:10px;
margin-top:20px;
}
</style>