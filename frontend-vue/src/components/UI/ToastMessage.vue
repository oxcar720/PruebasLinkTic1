<script setup>
import { ui } from '../../stores/ui'
</script>

<template>
  <div
    v-if="ui.toast.show"
    class="toast"
    :class="{
      success: ui.toast.type===0,
      warning: ui.toast.type===1,
      error: ui.toast.type===2
    }"
  >
    {{ ui.toast.message }}
  </div>
</template>

<style scoped>
.toast{
  position:fixed;
  top:20px;
  right:20px;
  padding:16px 20px;
  border-radius:12px;
  font-weight:700;
  z-index:10000;
  min-width:260px;
}

.success{ background:#16c784; color:white; }
.warning{ background:#f1c40f; color:black; }
.error{ background:#e74c3c; color:white; }
</style>