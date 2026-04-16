<script setup>
import { computed } from 'vue'

import HeaderBar from './components/Layout/HeaderBar.vue'
import Sidebar from './components/Layout/Sidebar.vue'
import SpinnerOverlay from './components/UI/SpinnerOverlay.vue'
import ToastMessage from './components/UI/ToastMessage.vue'

import ProductsView from './views/ProductsView.vue'
import InventoryView from './views/InventoryView.vue'
import DocumentationView from './views/DocumentationView.vue'

import { currentView } from './stores/ui'

const view = computed(() => currentView.value)
</script>

<template>
<div class="app-shell">

<SpinnerOverlay />
<ToastMessage />

<HeaderBar />

<div class="main-layout">

<Sidebar />

<main class="content-area">

<ProductsView v-if="view==='products'" />
<InventoryView v-if="view==='inventory'" />
<DocumentationView v-if="view==='docs'" />

</main>

</div>
</div>
</template>