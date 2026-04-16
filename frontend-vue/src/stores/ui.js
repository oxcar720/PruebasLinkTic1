import { reactive, ref } from 'vue'
export const currentView = ref('products')
export const ui = reactive({
  loading:false,

  toast:{
    show:false,
    message:'',
    type:0
  }
})

export function showLoading(){
  ui.loading = true
}

export function hideLoading(){
  ui.loading = false
}

export function mostrar_mensaje(message, type = 0){
  ui.toast.message = message
  ui.toast.type = type
  ui.toast.show = true

  setTimeout(()=>{
    ui.toast.show = false
  },3000)
}