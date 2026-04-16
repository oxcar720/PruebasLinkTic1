<script setup>
import { currentView } from '../../stores/ui'
</script>

<template>
<aside class="sidebar">

<div
class="menu"
:class="{ active: currentView==='products' }"
@click="currentView='products'"
>
📦 Productos
</div>

<div
class="menu"
:class="{ active: currentView==='inventory' }"
@click="currentView='inventory'"
>
🏪 Inventario
</div>

<div
class="menu"
:class="{ active: currentView==='docs' }"
@click="currentView='docs'"
>
📄 Documentación
</div>

</aside>
</template>

<style scoped>
.sidebar{
width:230px;
background:#111418;
border-right:1px solid #2b313a;
min-height:calc(100vh - 80px);
padding:20px;
}

.menu{
padding:14px;
border-radius:12px;
margin-bottom:10px;
cursor:pointer;
color:#aaa;
transition:.2s;
user-select:none;
}

.menu:hover{
background:#181c22;
color:#fff;
}

.active{
background:#16c784;
color:#000;
font-weight:700;
}
</style>