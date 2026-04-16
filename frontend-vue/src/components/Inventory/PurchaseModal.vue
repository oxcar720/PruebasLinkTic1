<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  show: Boolean,
  row: Object
})

const emit = defineEmits(['close','buy'])

const form = reactive({
  quantity: 1,
  key: ''
})

watch(() => props.show, (val) => {
  if (val) {
    form.quantity = 1
    form.key = crypto.randomUUID()
  }
}, { immediate: true })

function comprar() {
  emit('buy', {
    quantity: Number(form.quantity),
    key: form.key
  })
}
</script>

<template>
<div v-if="show" class="modal-bg">

  <div class="modal card">

    <h3>Comprar Producto</h3>

    <p class="sub">{{ row?.productId }}</p>

    <div class="grid">

      <div class="field">
        <label>Cantidad</label>
        <input
          v-model.number="form.quantity"
          type="number"
          min="1"
        />
      </div>

      <div class="field">
        <label>Idempotency-Key</label>
        <input v-model="form.key"/>
      </div>

    </div>

    <div class="actions">
      <button class="btn btn-gray" @click="$emit('close')">
        Cancelar
      </button>

      <button class="btn btn-green" @click="comprar">
        Comprar
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
.modal{width:520px;}
.grid{display:grid;gap:16px;margin-top:20px;}
.field{display:grid;gap:6px;}
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
.sub{
  font-size:12px;
  opacity:.7;
  margin-top:8px;
  word-break:break-all;
}
</style>