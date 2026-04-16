import { inventoryApi } from './api'

export async function getInventory(params){
  const { data } = await inventoryApi.get('/inventory', { params })
  return data
}

export async function syncInventory(){
  const { data } = await inventoryApi.post('/inventory/sync')
  return data
}

export async function updateInventory(id,payload){
  const { data } = await inventoryApi.put(`/inventory/${id}`, payload)
  return data
}

export async function purchaseInventory(key,payload){
  const { data } = await inventoryApi.post(
    '/inventory/purchase',
    payload,
    {
      headers:{
        'Idempotency-Key': key
      }
    }
  )

  return data
}