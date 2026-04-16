<script setup>
import { ui } from '../../stores/ui'
</script>

<template>
  <div v-if="ui.loading" class="overlay">
    <div class="spinner"></div>
  </div>
</template>

<style scoped>
.overlay{
  position:fixed;
  inset:0;
  background:rgba(0,0,0,.75);
  display:flex;
  align-items:center;
  justify-content:center;
  z-index:9999;
}

.spinner{
  width:60px;
  height:60px;
  border:5px solid #333;
  border-top:5px solid #16c784;
  border-radius:50%;
  animation:spin 1s linear infinite;
}

@keyframes spin{
  to{transform:rotate(360deg);}
}
</style>